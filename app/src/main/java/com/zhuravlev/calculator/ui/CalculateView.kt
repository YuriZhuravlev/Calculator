package com.zhuravlev.calculator.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewModelScope
import com.zhuravlev.calculator.R
import kotlinx.coroutines.Job
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
        Keyboard(onInput = { char ->
            calculatorViewModel.viewModelScope.launch {
                calculatorViewModel.input(char)
            }
        }, onClear = {
            calculatorViewModel.viewModelScope.launch {
                calculatorViewModel.clear()
            }
        }, onBackspace = {
            calculatorViewModel.viewModelScope.launch {
                calculatorViewModel.backspace()
            }
        })
    }
}

@Composable
fun Keyboard(onInput: (char: Char) -> Job, onClear: () -> Job, onBackspace: () -> Job) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(Modifier.weight(1f, true)) {
            ButtonClear(modifier = Modifier.weight(1f), onClear)
            ButtonInput('÷', modifier = Modifier.weight(1f), onInput)
            ButtonInput('×', modifier = Modifier.weight(1f), onInput)
            ButtonBackspace(modifier = Modifier.weight(1f), onBackspace)
        }
        Row(Modifier.weight(1f, true)) {
            ButtonInput('7', modifier = Modifier.weight(1f), onInput)
            ButtonInput('8', modifier = Modifier.weight(1f), onInput)
            ButtonInput('9', modifier = Modifier.weight(1f), onInput)
            ButtonInput('-', modifier = Modifier.weight(1f), onInput)
        }
        Row(Modifier.weight(1f, true)) {
            ButtonInput('4', modifier = androidx.compose.ui.Modifier.weight(1f), onInput)
            ButtonInput('5', modifier = androidx.compose.ui.Modifier.weight(1f), onInput)
            ButtonInput('6', modifier = androidx.compose.ui.Modifier.weight(1f), onInput)
            ButtonInput('+', modifier = androidx.compose.ui.Modifier.weight(1f), onInput)
        }
        Row(Modifier.weight(1f, true)) {
            ButtonInput('1', modifier = Modifier.weight(1f), onInput)
            ButtonInput('2', modifier = Modifier.weight(1f), onInput)
            ButtonInput('3', modifier = Modifier.weight(1f), onInput)
            ButtonInput(c = '(', modifier = Modifier.weight(1f), onInput)
        }
        Row(Modifier.weight(1f, true)) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {}
            ButtonInput('0', modifier = Modifier.weight(1f), onInput)
            ButtonInput('.', modifier = Modifier.weight(1f), onInput)
            ButtonInput(c = ')', modifier = Modifier.weight(1f), onInput)
        }
    }
}

@Composable
fun ButtonInput(c: Char, modifier: Modifier = Modifier, onCLick: (char: Char) -> Job) {
    OutlinedButton(
        modifier = modifier.fillMaxSize(), onClick = { onCLick(c) },
        shape = RectangleShape
    ) {
        Text("$c")
    }
}

@Composable
fun ButtonBackspace(modifier: Modifier = Modifier, onBackspace: () -> Job) {
    OutlinedButton(
        modifier = modifier.fillMaxSize(), onClick = { onBackspace() },
        shape = RectangleShape
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_backspace),
            contentDescription = "backspace",
            colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
        )
    }
}

@Composable
fun ButtonClear(modifier: Modifier = Modifier, onClear: () -> Job) {
    OutlinedButton(
        modifier = modifier.fillMaxSize(), onClick = { onClear() },
        shape = RectangleShape
    ) {
        Text("C")
    }
}

@Composable
fun OutputView(inputText: String, outputString: String) {
    Column() {
        Text(inputText)
        Text(outputString)
    }
}
