package org.hyperskill.calculator

import android.os.Bundle
import android.text.InputType
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import org.hyperskill.calculator.databinding.ActivityMainBinding

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
                    btn.setOnClickListener {
                        inputNum(btn)
                    }
                }
            }
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
                if (!txt.contains('.')){
                    if (it[0] == '0' && btn.text != "0"){
                        txt.deleteCharAt(0)
                    }else if  (txt[0] == '-' && txt[1] == '0' ) {
                        txt.deleteCharAt(1)
                    }
                }

            }
            displayEditText.setText(txt)
            if (exp.varX != null) exp.varY = txt.toString().toDouble()

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
                    exp.varX = txt.toString().toDouble().also {
                        displayEditText.hint = it.toInt().toString()
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
                    exp.varX = txt.toString().toDouble().also {
                        displayEditText.hint = it.toInt().toString()
                        displayEditText.text.clear()
                    }
                    txt.clear()
                    txt.append(btn.text)
                    exp.operation = txt.toString()
                    txt.clear()

                }

                else -> {
                    exp.varX = displayEditText.hint.toString().toDouble()
                    txt.clear()
                    txt.append(btn.text)
                    exp.operation = txt.toString()
                    txt.clear()
                }
            }

        }
    }

    private fun doCalculation() {
        binding.apply {
            if (!exp.operation.isNullOrBlank()) {
                when (exp.operation) {
                    "+" -> {
                        txt.clear()
                        txt.append(exp.varX?.plus(exp.varY!!)).also {
                            displayEditText.hint = it
                            displayEditText.text.clear()
                        }
                        exp.varX = txt.toString().toDouble()
                    }

                    "*" -> {
                        txt.clear()
                        txt.append(exp.varX?.times(exp.varY!!)).also {
                            displayEditText.hint = it
                            displayEditText.text.clear()
                        }
                        exp.varX = txt.toString().toDouble()
                    }

                    "/" -> {
                        txt.clear()
                        txt.append(exp.varX?.div(exp.varY!!)).also {
                            displayEditText.hint = it
                            displayEditText.text.clear()
                        }
                        exp.varX = txt.toString().toDouble()
                    }

                    "-" -> {
                        txt.clear()
                        txt.append(exp.varX?.minus(exp.varY!!)).also {
                            displayEditText.hint = it
                            displayEditText.text.clear()
                        }
                        exp.varX = txt.toString().toDouble()
                    }
                }
            }
        }
    }

    private inner class ArithmeticExpression(
        var varX: Double? = null,
        var operation: String? = null,
        var varY: Double? = null,
    )

}



