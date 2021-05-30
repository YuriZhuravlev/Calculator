package com.zhuravlev.calculator.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

@Composable
fun CalculatorView(calculatorViewModel: CalculateViewModel) {
    val inputText by calculatorViewModel.inputState.collectAsState()
    val outputString by calculatorViewModel.outputState.collectAsState()

    Column() {
        Surface(modifier = Modifier.fillMaxHeight(0.33f)) {
            OutputView(
                inputText,
                outputString
            )
        }
        Button(onClick = {
            calculatorViewModel.viewModelScope.launch {
                calculatorViewModel.input('0')
            }
        }) {
            Text(text = "0")
        }
    }
}

@Composable
fun OutputView(inputText: String, outputString: String) {
    Column() {
        Text(inputText)
        Text(outputString)
    }
}
