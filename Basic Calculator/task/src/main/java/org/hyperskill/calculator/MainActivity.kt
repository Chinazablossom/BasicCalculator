package org.hyperskill.calculator

import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import org.hyperskill.calculator.databinding.ActivityMainBinding
import org.hyperskill.calculator.models.ArithmeticExpression
import java.math.BigInteger
import kotlin.text.toBigDecimal

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val txt = StringBuilder()
    private lateinit var exp: ArithmeticExpression

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        exp = ArithmeticExpression()

        binding.apply {
            displayEditText.inputType = InputType.TYPE_NULL
            clearButton.setBackgroundColor(Color.GRAY)

            clearButton.setOnClickListener {
                txt.clear()
                displayEditText.text.clear()
                displayEditText.hint = "0"
                exp.varX = null
                exp.operation = null
                exp.varY = null
            }

            listOf(
                button0, button1, button2, button3, button4,
                button5, button6, button7, button8, button9
            ).also {
                it.forEach { btn ->
                    btn.setBackgroundColor(Color.DKGRAY)
                    btn.setOnClickListener {
                        inputNum(btn)
                    }
                }
            }
            dotButton.setBackgroundColor(Color.DKGRAY)
            dotButton.setOnClickListener {
                inputDecimalPoint()
            }
            listOf(addButton, multiplyButton, divideButton).also {
                it.forEach { btn ->
                    btn.setOnClickListener {
                        inputOpp(btn)
                    }
                }
            }
            equalButton.setOnClickListener {
                doCalculation()
            }
            subtractButton.setOnClickListener {
                inputMinus(it as Button)
            }

        }

    }

    private fun inputNum(btn: Button) {
        binding.apply {
            if (txt.isNotBlank() && txt[0] == '0' && !txt.contains(".") && btn == button0) return
            txt.append(btn.text).also {
                if (!txt.contains('.')) {
                    if (it[0] == '0' && btn.text != "0") {
                        txt.deleteCharAt(0)
                    } else if (txt[0] == '-' && txt[1] == '0') {
                        txt.deleteCharAt(1)
                    }
                }

            }
            displayEditText.setText(txt)
            if (exp.varX != null) exp.varY = txt.toString().toBigDecimal()
        }
    }

    private fun inputDecimalPoint() {
        binding.apply {
            if (!txt.contains(".")) {
                if (txt.isEmpty() || txt[0] == '-') {
                    txt.append("0.")
                    displayEditText.setText(txt)

                } else {
                    txt.append(".")
                    displayEditText.setText(txt)
                }
            }

        }

    }

    private fun inputMinus(btn: Button) {
        binding.apply {
            when {
                txt.isBlank() && exp.varX == null -> {
                    if (!txt.contains("-")) {
                        txt.append("-")
                        displayEditText.setText(txt)
                    }
                }

                exp.operation != null && txt.isBlank() -> {
                    txt.append("-")
                    displayEditText.setText(txt)
                }

                txt.isNotBlank() -> {
                    if (txt.toString() == "-") return
                    exp.varX = txt.toString().toBigDecimal().also {
                        displayEditText.hint = checkIfInt(it, txt)
                        displayEditText.text.clear()
                    }
                    txt.clear()
                    exp.operation = btn.text.toString()
                }
            }
        }
    }

    private fun inputOpp(btn: Button) {
        binding.apply {
            when {
                txt.isNotBlank() -> {
                    exp.varX = txt.toString().toBigDecimal().also {
                        displayEditText.hint = checkIfInt(it, txt)
                        displayEditText.text.clear()
                    }
                    txt.clear()
                    txt.append(btn.text)
                    exp.operation = txt.toString()
                    txt.clear()
                    exp.varY = null

                }

                else -> {
                    exp.varX = displayEditText.hint.toString().toBigDecimal()
                    txt.clear()
                    txt.append(btn.text)
                    exp.operation = txt.toString()
                    txt.clear()
                    exp.varY = null
                }
            }

        }
    }

    private fun doCalculation() {

        binding.apply {
            if (!exp.operation.isNullOrBlank()) {
                if (exp.varY == null) exp.varY = displayEditText.hint.toString().toBigDecimal()
                when (exp.operation) {
                    "+" -> {
                        txt.clear()
                        val value = exp.varX?.add(exp.varY!!)
                        txt.append(value).also { it ->
                            displayEditText.hint = checkIfInt(value, it)
                            displayEditText.text.clear()
                        }
                        exp.varX = txt.toString().toBigDecimal()
                    }

                    "*" -> {
                        txt.clear()
                        val value = exp.varX?.times(exp.varY!!)
                        txt.append(value).also {

                            displayEditText.hint = checkIfInt(value, it)
                            displayEditText.text.clear()
                        }
                        exp.varX = txt.toString().toBigDecimal()
                    }

                    "/" -> {
                        txt.clear()
                        val value = try {
                            exp.varX?.divide(exp.varY!!)
                        }catch (e: ArithmeticException){
                            0
                        }
                        txt.append(value).also {
                            displayEditText.hint = checkIfInt(value, it)
                            displayEditText.text.clear()
                        }
                        exp.varX = txt.toString().toBigDecimal()
                    }

                    "-" -> {
                        txt.clear()
                        val value = exp.varX?.minus(exp.varY!!)
                        txt.append(value).also {
                            displayEditText.hint = checkIfInt(value, it)
                            displayEditText.text.clear()
                        }
                        exp.varX = txt.toString().toBigDecimal()
                    }
                }
            }
        }

    }

    private fun checkIfInt(value: Any?, builder: StringBuilder): String {
        val finalVal = if (value.toString().substringAfter(".")
                .toBigInteger() > BigInteger.ZERO
        )
            builder.toString() else
            builder.toString().toBigDecimal().toBigInteger().toString()

        return finalVal
    }

}