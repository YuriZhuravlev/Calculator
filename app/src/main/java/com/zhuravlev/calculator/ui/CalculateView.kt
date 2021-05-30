package com.zhuravlev.calculator.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.zhuravlev.calculator.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val fontSizeButton = 30f

@ExperimentalComposeApi
@Composable
fun CalculatorView(calculatorViewModel: CalculateViewModel) {
    val inputText by calculatorViewModel.inputState.collectAsState()
    val outputString by calculatorViewModel.outputState.collectAsState()

    Column() {
        OutputView(
            modifier = Modifier.fillMaxHeight(0.33f),
            inputText,
            outputString
        )
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

@ExperimentalComposeApi
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

@ExperimentalComposeApi
@Composable
fun ButtonInput(c: Char, modifier: Modifier = Modifier, onCLick: (char: Char) -> Job) {
    OutlinedButton(
        modifier = modifier.fillMaxSize(), onClick = { onCLick(c) },
        shape = RectangleShape
    ) {
        Text(text = "$c",
        fontSize = TextUnit(fontSizeButton, TextUnitType.Sp))
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

@ExperimentalComposeApi
@Composable
fun ButtonClear(modifier: Modifier = Modifier, onClear: () -> Job) {
    OutlinedButton(
        modifier = modifier.fillMaxSize(), onClick = { onClear() },
        shape = RectangleShape
    ) {
        Text(text = "C",
            fontSize = TextUnit(fontSizeButton, TextUnitType.Sp))
    }
}

@ExperimentalComposeApi
@Composable
fun OutputView(modifier: Modifier = Modifier, inputText: String, outputString: String) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(top = 40.dp),
            color = Color.Black,
            fontSize = TextUnit(50f, TextUnitType.Sp),
            textAlign = TextAlign.Right,
            text = inputText,
            maxLines = 1
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            color = Color.DarkGray,
            fontSize = TextUnit(40f, TextUnitType.Sp),
            textAlign = TextAlign.Right,
            text = if (inputText.isEmpty()) "" else outputString,
            maxLines = 1
        )
    }
}
