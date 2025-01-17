package org.hyperskill.calculator

import org.hyperskill.calculator.internals.BasicCalculatorUnitTest
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

// Version 2.0
@RunWith(RobolectricTestRunner::class)
class Stage1UnitTest : BasicCalculatorUnitTest<MainActivity>(MainActivity::class.java){


    @Test
    fun checkViews() {
        testActivity {
            calculatorView
        }
    }
}