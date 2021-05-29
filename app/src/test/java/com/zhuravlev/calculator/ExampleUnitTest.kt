package com.zhuravlev.calculator

import com.zhuravlev.calculator.calc.Calculator
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun checkCalculator0() {
        assertEquals(Calculator.calculate("1+1"), 2.0.toString())
    }

    @Test
    fun checkCalculator1() {
        assertEquals(Calculator.calculate("94"), 94.0.toString())
    }

    @Test
    fun checkCalculator2() {
        assertEquals(Calculator.calculate("1000+0.01"), (1000 + 0.01).toString())
    }

    @Test
    fun checkCalculator3() {
        assertEquals(
            Calculator.calculate("85-49÷7+(48.4+9×(1+2-4))"),
            (85 - 49 / 7 + (48.4 + 9 * (1 + 2 - 4))).toString()
        )
    }

    @Test
    fun checkCalculator4() {
        assertEquals(Calculator.calculate("900-900"), 0.0.toString())
    }

    @Test
    fun checkCalculator5() {
        assertEquals(Calculator.calculate("845-100÷100"), 844.0.toString())
    }

    @Test
    fun checkCalculator6() {
        assertEquals(Calculator.calculate("(845-100)÷100"), 7.45.toString())
    }

    @Test
    fun checkCalculator7() {
        assertEquals(Calculator.calculate("ready"), "Error")
    }

    @Test
    fun checkCalculator8() {
        assertEquals(Calculator.calculate("(1+1"), "Error")
    }

    @Test
    fun checkCalculator9() {
        assertEquals(Calculator.calculate("9÷0"), "Infinity")
    }

    @Test
    fun checkCalculator10() {
        assertEquals(Calculator.calculate("(((((((1+1)))))))"), 2.0.toString())
    }
}