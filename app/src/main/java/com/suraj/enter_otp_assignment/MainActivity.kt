package com.suraj.enter_otp_assignment

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.widget.doOnTextChanged

class MainActivity : AppCompatActivity() {

    private lateinit var rbThree: RadioButton
    private lateinit var rbFour: RadioButton
    private lateinit var rbFive: RadioButton
    private lateinit var rbSix: RadioButton

    private lateinit var mainContainer: LinearLayout

    private lateinit var editTextReferenceList: ArrayList<EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        setOnClick()

    }


    fun initView() {

        mainContainer = findViewById(R.id.mainContainer)
        rbThree = findViewById(R.id.rbThree)
        rbFour = findViewById(R.id.rbFour)
        rbFive = findViewById(R.id.rbFive)
        rbSix = findViewById(R.id.rbSix)

        editTextReferenceList = ArrayList()
    }

    fun setOnClick() {


        rbThree.setOnClickListener { addEditText(3) }
        rbFour.setOnClickListener { addEditText(4) }
        rbFive.setOnClickListener { addEditText(5) }
        rbSix.setOnClickListener { addEditText(6) }


    }

    fun addEditText(count: Int) {

        mainContainer.removeAllViews()
        editTextReferenceList.clear()

        for (i in 1..count) {

            val editText = EditText(this)

            var layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1f
            )

            editText.layoutParams = layoutParams

            editText.inputType = InputType.TYPE_CLASS_NUMBER

            editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20f)

            editText.gravity = Gravity.CENTER

            editText.filters = arrayOf(InputFilter.LengthFilter(1))

            editTextReferenceList.add(editText)

            mainContainer.addView(editText)

            for (i in editTextReferenceList) {
                i.doOnTextChanged { text, start, before, count ->
                    if (count == 1) {
                        if (getNextEditTextIndexForFocus(i) == -1) {
                            i.clearFocus()
                            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.hideSoftInputFromWindow(i.windowToken, 0)
                        } else {
                            editTextReferenceList[getNextEditTextIndexForFocus(i)].requestFocus()
                        }
                    }
                }
            }
        }
    }


    fun getNextEditTextIndexForFocus(currentEditText: EditText): Int {

        for (i in 0 until editTextReferenceList.size - 1) {
            if (currentEditText === editTextReferenceList[editTextReferenceList.size - 1]) {
                Toast.makeText(applicationContext, "Enter OTP Done", Toast.LENGTH_SHORT).show()
                return -1
            }

            if (editTextReferenceList[i] === currentEditText) return i + 1
        }
        return 0
    }


}