package com.zhuravlev.calculator.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

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
        Keyboard()
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
fun Keyboard() {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(Modifier.weight(1f, true)) {
            ButtonInput('C', modifier = Modifier.weight(1f))
            ButtonInput('÷', modifier = Modifier.weight(1f))
            ButtonInput('×', modifier = Modifier.weight(1f))
            ButtonInput('D', modifier = Modifier.weight(1f)) // TODO DELETE
        }
        Row(Modifier.weight(1f, true)) {
            ButtonInput('7', modifier = Modifier.weight(1f))
            ButtonInput('8', modifier = Modifier.weight(1f))
            ButtonInput('9', modifier = Modifier.weight(1f))
            ButtonInput('-', modifier = Modifier.weight(1f))
        }
        Row(Modifier.weight(1f, true)) {
            ButtonInput('4', modifier = androidx.compose.ui.Modifier.weight(1f))
            ButtonInput('5', modifier = androidx.compose.ui.Modifier.weight(1f))
            ButtonInput('6', modifier = androidx.compose.ui.Modifier.weight(1f))
            ButtonInput('+', modifier = androidx.compose.ui.Modifier.weight(1f))
        }
        Row(Modifier.weight(1f, true)) {
            ButtonInput('1', modifier = Modifier.weight(1f))
            ButtonInput('2', modifier = Modifier.weight(1f))
            ButtonInput('3', modifier = Modifier.weight(1f))
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {}
        }
        Row(Modifier.weight(1f, true)) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {}
            ButtonInput('0', modifier = Modifier.weight(1f))
            ButtonInput('.', modifier = Modifier.weight(1f))
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {}
        }
    }
}

@Composable
fun ButtonInput(c: Char, modifier: Modifier = Modifier) {
    OutlinedButton(
        modifier = modifier.fillMaxSize(), onClick = { },
        shape = RectangleShape
    ) {
        Text("$c")
    }
}

@Composable
fun OutputView(inputText: String, outputString: String) {
    Column() {
        Text(inputText)
        Text(outputString)
    }
}
