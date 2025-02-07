package org.hyperskill.calculator

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import org.hyperskill.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val txt = StringBuilder()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            displayEditText.inputType = InputType.TYPE_NULL
            clearButton.setOnClickListener {
                txt.clear()
                displayEditText.text.clear()
            }

            val digits = listOf(
                button0, button1, button2, button3,
                button4, button5, button6, button7,
                button8, button9,
            )
            for (btn in digits) {
                btn.addNum()
            }
            dotButton.addDec()

        }

    }


    fun Button.addNum() {
        binding.apply {
            this@addNum.setOnClickListener {
                if (txt.isNotBlank() && txt[0] == '0' && !txt.contains(".") && this@addNum.text == "0") return@setOnClickListener

                txt.append(this@addNum.text).also {
                    if (it[0] == '0' && !txt.contains(".") && this@addNum.text != "0") txt.deleteCharAt(
                        0
                    )
                }

                displayEditText.setText(txt)

            }

        }
    }

    fun Button.addDec() {
        binding.apply {
            this@addDec.setOnClickListener {
                if (!txt.contains(".")) {
                    if (txt.isEmpty()) {
                        txt.append("0.")
                        displayEditText.setText(txt)

                    } else {
                        txt.append(".")
                        displayEditText.setText(txt)
                    }
                }

            }

        }
    }


}