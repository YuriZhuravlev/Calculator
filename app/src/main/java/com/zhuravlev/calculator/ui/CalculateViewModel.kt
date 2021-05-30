package com.zhuravlev.calculator.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhuravlev.calculator.calc.Calculator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class CalculateViewModel : ViewModel() {
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
                    _outputState.emit(Calculator.calculate(
                        it.simpleCheckBrackets()
                    ))
                }
            }
        }
    }

    suspend fun input(char: Char) {
        mutexInput.withLock {
            val str = StringBuilder(inputState.value)
            if (str.isNotEmpty() && str.last() != '-' && str.last() in Calculator.operations) {
                str[str.lastIndex] = char
            } else {
                str.append(char)
            }
            _inputState.emit(str.toString())
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
                else -> {}
            }
        }
        if (i == 0) {
            return this
        }
        val str = StringBuilder(this)
        while (i-->0) {
            str.append(')')
        }
        return str.toString()
    }
}