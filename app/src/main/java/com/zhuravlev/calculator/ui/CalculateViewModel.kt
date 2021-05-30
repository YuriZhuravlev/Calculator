package com.zhuravlev.calculator.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhuravlev.calculator.calc.Calculator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

@HiltViewModel
class CalculateViewModel @Inject constructor() : ViewModel() {
    private val mutexInput = Mutex()
    private val mutexOutput = Mutex()

    private val _inputState = MutableStateFlow("")
    val inputState: StateFlow<String> = _inputState

    private val _outputState = MutableStateFlow("")
    val outputState: StateFlow<String> = _outputState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            mutexOutput.withLock {
                inputState.collect {
                    _outputState.emit(
                        Calculator.calculate(
                            it.simpleCheckBrackets()
                        )
                    )
                }
            }
        }
    }

    suspend fun input(char: Char) {
        mutexInput.withLock {
            val str = StringBuilder(inputState.value)
            if (char in Calculator.operations && (str.isEmpty() || str.last() in Calculator.operations)) {
                if (str.isEmpty()) str.append(char) else str[str.lastIndex] = char
            } else {
                str.append(char)
            }
            _inputState.emit(str.toString())
        }
    }

    suspend fun clear() {
        mutexInput.withLock {
            if (inputState.value.isNotEmpty()) {
                _inputState.emit("")
            }
        }
    }

    suspend fun backspace() {
        mutexInput.withLock {
            val str = inputState.value
            if (str.isNotEmpty()) {
                _inputState.emit(str.substring(0, str.lastIndex))
            }
        }
    }

    /**
     * Проверка на количество скобок, но не правильную скобочную последовательность
     * По умолчанию принимается, что все не написанные скобки - добавлены в конец
     */
    private fun String.simpleCheckBrackets(): String {
        var i = 0
        forEach {
            when (it) {
                '(' -> i++
                ')' -> i--
                else -> {
                }
            }
        }
        if (i == 0) {
            return this
        }
        val str = StringBuilder(this)
        while (i-- > 0) {
            str.append(')')
        }
        return str.toString()
    }
}