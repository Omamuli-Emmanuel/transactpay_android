package com.transactpay.transactpay_android_sdk

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.transactpay.newtransactpay.R
import com.transactpay.transactpay_android.PayWithTransactpay

class MainActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            // Find views
            val firstName : EditText = findViewById(R.id.fname)
            val lastName : EditText = findViewById(R.id.lname)
            val phone : EditText = findViewById(R.id.phone)
            val amountEditText: EditText = findViewById(R.id.amountEditText)
            val emailEditText: EditText = findViewById(R.id.emailEditText)
            val apiKeyEditText: EditText = findViewById(R.id.apikeyEditText)
            val encryptionKey : EditText = findViewById(R.id.encryptionKey)
            val baseurl : EditText = findViewById(R.id.baseurl)
            val submitButton: Button = findViewById(R.id.submitButton)

            // Set up the submit button click listener
            // Set up the submit button click listener
            submitButton.setOnClickListener {
                val fname = firstName.text.toString()
                val lname = lastName.text.toString()
                val phoneNumber = phone.text.toString()
                val amount = amountEditText.text.toString()
                val email = emailEditText.text.toString()
                val refenrence = "*BB*BUHUBI(-DJK9J-009N(U9n9#IN0mm0)"

                val encryptKey : String = "NDA5NiE8UlNBS2V5VmFsdWU+PE1vZHVsdXM+MG12ei9FSElIazNEOGlMWnhrT3VQLzE5VktCYWVtOFZUTU1YM2ZkbE5kb013VC82V0NrVDFYT3hxSUZrakcvNnZqVVBTVm1KcWg0VTU2Qm1GS0tUREJxeEk2QXdmS2ZjTFBGTkI4MlliSHFJN2FoYnRya2F5bnFFN0V2QzRXWDRLZWN2SGZwenR2WC9wZ1krRWZFUW1BNkptYTVERElMK3hJNmxYS0JFMkdmb2FuWG9OdW5wUVRGVFVXV0g5dllleWNyVjZPSHYraUFTdXE4MHk0amt2bjhwTXJrTmxab1NTSHhDSXE2c1ROT1VJekdYNVR2QnlkdGNmOXJlYmFnYnNESi81dDcxejYyMlhGdUJTdWh1TlY5NFhzem1OMGhJejRFQ3RGcFQ1NytmSVpzWUxPOWVuSWZIYnZCemwzV1UvQi9abGdoOWpkelI1K2NLTGdOeHd4OCs2T1J5QWdDQk5zaldVdE5uRktzb0xTd2NTY2pBMCtrb3ZnM0tFTHlBVURCV3M1MFZ5eTFxUFNFR1VCSDd6RWhqZjV6R0tuVStmZ3Nic3lVeGQ4dkttT2pvRy9HOGs4M2Jmd2hnVlhtVXJMQldQb3ZkY1B5L3NRNTJQOTJwcWNoUWZ5WVNZU3Vnemo3djFFMEVsakJITTU1NGQ1UURHbU9ibGRSSHNJSTBxVkNUZHRqQ091bHh3emU0MlpJTW45NHFaTnpFUXVRamtJYlpQSXBEVWxBOFF5dmtuU3BkZW1oZUpTcklpK3BEVUF2RDIzdVNqUTNzQlVvcmFuOWZMc1ZGTXFtZlJKOGFSci9nQTVaaEJFT0NCMEE1Z28zaDBoYldDNFRocFdlUE9raGEwYUd6cWNMVm1JVWFpaUxBOUF6QU9FMHluSEFWUlgwQlZ6SEhDUzA9PC9Nb2R1bHVzPjxFeHBvbmVudD5BUUFCPC9FeHBvbmVudD48L1JTQUtleVZhbHVlPg=="
                val apiKey : String = "PGW-PUBLICKEY-43E1CE8C6A7149CE94A8A71D572AE414"
               // initiate pay with transct pay library

                val intent = PayWithTransactpay.PayWithTransactpayUtils.newIntent(
                    context = this@MainActivity,
                    firstName = fname,
                    lastName = lname,
                    phone = phoneNumber,
                    amount = amount,
                    email = email,
                    apiKey = apiKey,
                    encryptionKey = encryptKey,
                    initiatingActivityClass = MainActivity::class.java,
                    successClass = Success::class.java,
                    failureClass = Failed::class.java,
                    transactionRef = refenrence
                )
                startActivity(intent)
              //   Create an intent to start the next activity
            }
        }
}
