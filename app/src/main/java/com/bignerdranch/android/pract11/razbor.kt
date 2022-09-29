package com.bignerdranch.android.pract11

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class razbor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_razbor)
        //11 13 15 17
        var tvNAME = findViewById<TextView>(R.id.textView11)
        var tvAUTHOR = findViewById<TextView>(R.id.textView13)
        var tvPAGES = findViewById<TextView>(R.id.textView15)
        var tvZHANR = findViewById<TextView>(R.id.textView17)



    }
}