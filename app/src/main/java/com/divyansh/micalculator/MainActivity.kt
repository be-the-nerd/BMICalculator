package com.divyansh.micalculator

import android.app.Activity
import android.content.Context
import android.graphics.Color.red
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.divyansh.micalculator.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.mainLayout.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }

        binding.btnCalc.setOnClickListener{
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)

            val weight = binding.etWeight.text.toString()
            val height = binding.etHeight.text.toString()

            if (validateInput(weight, height)) {
                val bmi = weight.toFloat() / ((height.toFloat() / 100) * (height.toFloat() / 100))
                //get result with two decimal places
                val bmi2Digits = String.format("%.2f", bmi).toFloat()
                displayResult(bmi2Digits)
            }
        }

        binding.etWeight.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus){
                binding.etWeight.hint=""
            }else{
                binding.etWeight.hint="0"
            }
        }
        binding.etHeight.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus){
                binding.etHeight.hint=""
            }else{
                binding.etHeight.hint="0"
            }
        }
    }

    private fun validateInput(weight: String?, height: String?): Boolean{
        return when{
            weight.isNullOrEmpty() -> {
                Snackbar.make(binding.mainLayout, "Weight is empty", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(ContextCompat.getColor(this, R.color.warning))
                    .show()
                return false
            }
            height.isNullOrEmpty() -> {
                Snackbar.make(binding.mainLayout, "Height is empty", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(ContextCompat.getColor(this, R.color.warning))
                    .show()
                return false
            }
            else -> return true
        }
    }

    private fun displayResult(bmi:Float){
        binding.tvIndex.text = bmi.toString()
        binding.tvInfo.text = "(Normal range is 18.5 - 24.9)"

        var resultText=""
        var color=0

        when{
            bmi<18.50 -> {
                resultText = "Underweight"
                color = R.color.under_weight
            }
            bmi in 18.50..24.99 -> {
                resultText = "Healthy"
                color = R.color.normal
            }
            bmi in 25.00..29.99 -> {
                resultText = "Overweight"
                color = R.color.over_weight
            }
            bmi> 29.99 -> {
                resultText = "Obese"
                color = R.color.obese
            }
        }
        binding.tvResult.text=resultText
        binding.tvResult.setTextColor(ContextCompat.getColor(this,color))
    }
}