package com.example.napiwki

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.SeekBar
import android.widget.TextView
import android.widget.RatingBar
class MainActivity : AppCompatActivity() {
    private lateinit var costText: EditText
    private lateinit var seekBar: SeekBar
    private lateinit var radioButton: RadioButton
    private lateinit var radioButton2: RadioButton
    private lateinit var radioButton3: RadioButton
    private lateinit var commitButton: Button
    private lateinit var tipResult: TextView
    private lateinit var satisfactionPercentage: TextView
    private lateinit var ratingBar: RatingBar
    override fun onCreate(savedInstanceState: Bundle?)
    {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        costText = findViewById(R.id.costText)
        seekBar = findViewById(R.id.seekBar)
        radioButton = findViewById(R.id.radioButton)
        radioButton2 = findViewById(R.id.radioButton2)
        radioButton3 = findViewById(R.id.radioButton3)
        commitButton = findViewById(R.id.commit_btn)
        tipResult = findViewById(R.id.tipResult)
        satisfactionPercentage = findViewById(R.id.textView4)
        ratingBar = findViewById(R.id.ratingBar)

        // Set initial satisfaction percentage text
        satisfactionPercentage.text = "${seekBar.progress}%"

        // Set listener for SeekBar
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Update satisfaction percentage text
                satisfactionPercentage.text = "$progress%"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        commitButton.setOnClickListener {
            calculateTip()
        }
    }
    private fun calculateTip() {
        val cost = costText.text.toString().toDoubleOrNull() ?: 0.0
        val quality = when {
            radioButton.isChecked -> 0.3 // Excellent
            radioButton3.isChecked -> 0.15 // Good
            else -> 0.1 // Average or "Don't know"
        }
        val satisfaction = seekBar.progress / 100.0
        val ratingPercent = ratingBar.rating / ratingBar.numStars // Convert rating to percentage
        val tip = cost * quality * satisfaction * (1 + ratingPercent) * 2 // Add rating percentage to the tip calculation
        val formattedTip = String.format("%.2f", tip)
        // Show the tip or do something with it
        tipResult.text = "Napiwek: $formattedTip"
    }
}
