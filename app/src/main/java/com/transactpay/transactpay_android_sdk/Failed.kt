package com.transactpay.transactpay_android_sdk

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import com.transactpay.newtransactpay.R

class Failed : AppCompatActivity() {

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.failed)

        val response = intent.getStringExtra("json_data")
        findViewById<TextView>(R.id.response).setText(response.toString())

        findViewById<Button>(R.id.BackToHome).setOnClickListener{
            var intent = Intent(this@Failed,MainActivity::class.java)
            startActivity(intent)
        }
    }
}
