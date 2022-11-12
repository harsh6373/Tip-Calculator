package com.harsh02.tipcalculator

import android.animation.ArgbEvaluator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat

private const val TAG = "MainActivity"
private const val initial_tip_percentage = 15
class MainActivity : AppCompatActivity() {
    private lateinit var etbaseamount: EditText
    private lateinit var seekbartip: SeekBar
    private lateinit var tvtippercentagelabel: TextView
    private lateinit var tvtipamount: TextView
    private lateinit var tvtotalamount: TextView
    private lateinit var tvtipdescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etbaseamount = findViewById(R.id.edt_base)
        seekbartip = findViewById(R.id.seekbar)
        tvtippercentagelabel = findViewById(R.id.tvpercentage_label)
        tvtipamount = findViewById(R.id.txt_tip)
        tvtotalamount = findViewById(R.id.txt_totalamount)
        tvtipdescription = findViewById(R.id.txttipdescription)

       seekbartip.progress = initial_tip_percentage
        tvtippercentagelabel.text = "$initial_tip_percentage%"
        updatetipdescription(initial_tip_percentage)
        seekbartip.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                //Log.i(TAG,"onProgresschange : $p1")
                tvtippercentagelabel.text = "$p1%"
                computetipandtotal()
                updatetipdescription(p1)

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })

        etbaseamount.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                  //Log.i(TAG,"text $p0")

                computetipandtotal()
            }



        })
    }

    private fun updatetipdescription(tippercentage: Int) {
        val tipdescription = when(tippercentage){
            in 0..9 -> "Poor"
            in 10..14 -> "Acceptable"
            in 15..19 -> "Good"
            in 20..24 -> "Great"
            else -> "Amazing"
        }
        tvtipdescription.text = tipdescription

        // update the color based on the tip percentage

        val color = ArgbEvaluator().evaluate(
            tippercentage.toFloat() / seekbartip.max,
            ContextCompat.getColor(this,R.color.color_wrost_tip),
            ContextCompat.getColor(this,R.color.color_best_tip)
        ) as Int
        tvtipdescription.setTextColor(color)
    }

    private fun computetipandtotal() {

        if (etbaseamount.text.isEmpty()){
            tvtipamount.text = ""
            tvtotalamount.text = ""
            return
        }
       val baseamount = etbaseamount.text.toString().toDouble()
        val tippercentage = seekbartip.progress


        val tipamount = baseamount * tippercentage / 100
        val totalamount = baseamount + tipamount

        tvtipamount.text = "%.2f".format(tipamount)
        tvtotalamount.text = "%.2f".format(totalamount)
    }

}

