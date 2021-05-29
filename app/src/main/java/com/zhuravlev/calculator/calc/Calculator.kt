package com.zhuravlev.calculator.calc

import java.util.*

/**
 * Use '+', '-', '×', '÷'
 */
object Calculator {
    private fun add(p1: Double, p2: Double): Double = p2 + p1
    private fun sub(p1: Double, p2: Double): Double = p2 - p1
    private fun mul(p1: Double, p2: Double): Double = p2 * p1
    private fun div(p1: Double, p2: Double): Double {
        if (p1 == 0.0) {
            throw DivisionException()
        }
        return p2 / p1
    }

    private fun Char.parseDouble(): Double = (toDouble() - 48.0)
    private const val varSet = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKZXCVBNM"
    private const val numberSet = "0123456789."
    private val priority = mapOf<Char, Int>(
        '+' to 0,
        '-' to 0,
        '×' to 1,
        '÷' to 1
    )

    fun calculate(expression: String): String {
        val (exprRPN, map) = getRPN(expression)
        val stack = Stack<Double>()
        var i = -1

        return try {
            while (++i < exprRPN.length)
                when (exprRPN[i]) {
                    '+' -> stack.push(add(stack.pop(), stack.pop()))
                    '-' -> stack.push(sub(stack.pop(), stack.pop()))
                    '×' -> stack.push(mul(stack.pop(), stack.pop()))
                    '÷' -> stack.push(div(stack.pop(), stack.pop()))
                    in map.keys -> stack.push(map[exprRPN[i]])
                    else -> throw Exception("Error, received (${exprRPN[i]},$i) in expression $exprRPN\nMap of vars ${map.toString()}")
                }
            stack.pop().toString()
        } catch (e: DivisionException) {
            e.printStackTrace()
            "Infinity"
        } catch (e: Exception) {
            e.printStackTrace()
            "Error"
        }
    }


    private fun getRPN(expression: String): Pair<String, Map<Char, Double>> {
        val variables = mutableMapOf<Char, Double>()
        val str = StringBuilder()

        val stack = Stack<Char>()

        var i = -1
        while (++i < expression.length) {
            val token = expression[i]
            when (token) {
                '(' -> stack.push(token)
                ')' -> {
                    // удаление скобки соответствующей текущей закрывающей скобке
                    while (stack.isNotEmpty() && stack.peek() != '(') {
                        str.append(stack.pop())
                    }
                    if (stack.isEmpty()) {
                        throw Exception("Required '('")
                    }
                    stack.pop()
                }
                else -> {
                    if (token in numberSet) {
                        // если token является частью числа, то парсим и добавляем в map
                        var isWhole = expression[i] != '.'
                        var number = if (isWhole) expression[i++].parseDouble() else 0.0
                        while (i < expression.length && isWhole && expression[i] in numberSet) {
                            isWhole = expression[i] != '.'
                            if (isWhole) {
                                number = number * 10 + expression[i++].parseDouble()
                            }
                        }
                        if (i < expression.length && expression[i] == '.') i++
                        var power = 0.1
                        while (i < expression.length && expression[i].isDigit()) {
                            number += expression[i++].parseDouble() * power
                            power /= 10
                        }
                        if (variables.size >= varSet.length) throw Exception("Large expression")
                        val ch = varSet[variables.size]
                        str.append(ch)
                        variables[ch] = number
                        if (!(i == expression.lastIndex && expression[i] in numberSet)) --i
                    } else {
                        while (stack.size > 0 &&
                            priority.contains(stack.peek())
                            && priority[stack.peek()]!! >= priority[token]!!
                        ) {
                            str.append(stack.pop())
                        }
                        stack.push(token)
                    }
                }
            }
        }
        while (stack.isNotEmpty()) {
            str.append(stack.pop())
        }
        return Pair(str.toString(), variables)
    }
}

private class DivisionException: Exception() {
    override val message: String
        get() = "Division by zero!"
}