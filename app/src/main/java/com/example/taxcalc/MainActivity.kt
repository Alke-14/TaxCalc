package com.example.taxcalc

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.taxcalc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Initialize View Binding first
        binding = ActivityMainBinding.inflate(layoutInflater)
        // 2. Set the content view using the binding's root
        setContentView(binding.root)

        // 4. Set up the listeners for your calculator logic
        setupListeners()
    }

    private fun setupListeners() {
        // Set up a listener for the SeekBar
        binding.seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Update the tip label to show the current percentage
                binding.textView.text = "Tip: $progress%"
                // When the slider moves, recalculate
                performCalculation()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Set up a listener for the EditText input
        binding.editTextNumber.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                // When the text changes, recalculate
                performCalculation()
            }
        })
    }

    private fun performCalculation() {
        // Get the text from the EditText
        val billAmountString = binding.editTextNumber.text.toString()

        // Check if the input field is empty to avoid a crash
        if (billAmountString.isEmpty()) {
            binding.TipTotal.text = "0.00"
            binding.TotalCheck.text = "0.00"
            return
        }

        // Convert the inputs to numbers
        val billAmount = billAmountString.toDouble()
        val tipPercentage = binding.seekBar.progress

        // Perform the calculations
        val tipAmount = billAmount * tipPercentage / 100.0
        val totalAmount = billAmount + tipAmount

        // Update the output TextViews (TipTotal and TotalCheck)
        // Formats the number to two decimal places
        binding.TipTotal.text = String.format("%.2f", tipAmount)
        binding.TotalCheck.text = String.format("%.2f", totalAmount)
    }
}