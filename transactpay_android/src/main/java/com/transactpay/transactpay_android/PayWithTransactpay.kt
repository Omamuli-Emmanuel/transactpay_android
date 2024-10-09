package com.transactpay.transactpay_android

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.transactpay.transactpay_android.BankTransfer.Companion
import com.transactpay.transactpay_android.BankTransfer.Companion.TAG
import com.transactpay.transactpay_android.EncryptionUtils
import com.transactpay.transactpay_android.R
import com.transactpay.transactpay_android.Transactpay_start
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.text.NumberFormat
import java.util.*

class PayWithTransactpay : AppCompatActivity() {

    private val TAG = "ProcessingPage"

    // Class-level variables to store the parameters from the intent
    private lateinit var fname: String
    private lateinit var lname: String
    private lateinit var mobile: String
    private lateinit var amountString: String
    private lateinit var email: String
    private lateinit var apiKey: String
    private lateinit var hashKey: String
    private lateinit var transactionRef: String
    private lateinit var initiatingClass: Class<*>
    private lateinit var successClass: Class<*>
    private lateinit var failureClass: Class<*>

    private val baseurl: String = "https://payment-api-service.transactpay.ai/payment"

    object PayWithTransactpayUtils {
        fun newIntent(
            context: Context,
            firstName: String,
            lastName: String,
            phone: String,
            amount: String,
            email: String,
            apiKey: String,
            encryptionKey: String,
            initiatingActivityClass: Class<*>,
            successClass: Class<*>,
            failureClass: Class<*>,
            transactionRef: String
        ): Intent {
            return Intent(context, PayWithTransactpay::class.java).apply {
                putExtra("Fname", firstName)
                putExtra("Lname", lastName)
                putExtra("Phone", phone)
                putExtra("AMOUNT", amount)
                putExtra("EMAIL", email)
                putExtra("API_KEY", apiKey)
                putExtra("XMLKEY", encryptionKey)
                putExtra("INITIATING_ACTIVITY_CLASS", initiatingActivityClass)
                putExtra("SUCCESS_CLASS", successClass)
                putExtra("FAILURE_CLASS", failureClass)
                putExtra("TransactionRef", transactionRef)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.processingpage)

        // Retrieve and assign the data from the intent to class-level variables
        fname = intent.getStringExtra("Fname") ?: ""
        lname = intent.getStringExtra("Lname") ?: ""
        mobile = intent.getStringExtra("Phone") ?: ""
        amountString = intent.getStringExtra("AMOUNT") ?: "0"
        email = intent.getStringExtra("EMAIL") ?: ""
        apiKey = intent.getStringExtra("API_KEY") ?: ""
        hashKey = intent.getStringExtra("XMLKEY") ?: ""
        transactionRef = intent.getStringExtra("TransactionRef") ?: ""
        initiatingClass = intent.getSerializableExtra("INITIATING_ACTIVITY_CLASS") as Class<*>
        successClass = intent.getSerializableExtra("SUCCESS_CLASS") as Class<*>
        failureClass = intent.getSerializableExtra("FAILURE_CLASS") as Class<*>

        Log.d(TAG, apiKey)

        // Format the amount
        val amount = amountString.toFloatOrNull()
        val formattedAmount = amount?.let {
            val numberFormat = NumberFormat.getNumberInstance(Locale.getDefault())
            numberFormat.maximumFractionDigits = 2
            numberFormat.minimumFractionDigits = 2
            val amountFormatted = numberFormat.format(it)
            "NGN $amountFormatted"
        } ?: "Invalid amount"

        // Generate a reference number if one is not provided
        var referenceNumber = transactionRef
        if (transactionRef.isEmpty()) {
            referenceNumber = generateReferenceNumber()
        }

        val rsaPublicKeyXml = EncryptionUtils.decodeBase64AndExtractKey(hashKey)
        val url = "$baseurl/order/create"

        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = postEncryptedPayload(
                    url, fname, lname, mobile, email, amount, referenceNumber, apiKey, rsaPublicKeyXml
                )

                Log.d(TAG, "HTTP Response Body: ${response.toString()}")

                val jsonResponse = JSONObject(response)

                Log.d(TAG, "HTTP Response Body: $jsonResponse")

                val status = jsonResponse.getString("status")

                if (jsonResponse == null) {
                    // Create an Intent to start the Failure Activity
                    val intent = Intent(this@PayWithTransactpay, failureClass).apply {
                        putExtra("json_data", "Zero data received, check internet connection") // Attach JSON String as an extra
                    }
                    startActivity(intent)
                } else {
                    if (status == "success") {
                        val data = jsonResponse.getJSONObject("data")
                        val order = data.getJSONObject("order")
                        val customer = data.getJSONObject("customer")
                        val subsidiary = data.getJSONObject("subsidiary")

                        Log.d(TAG, "First Reference $referenceNumber")

                        val intent = Intent(this@PayWithTransactpay, Transactpay_start::class.java).apply {
                            putExtra("Fname", customer.optString("firstName"))
                            putExtra("Lname", customer.optString("lastName"))
                            putExtra("Phone", customer.optString("mobile"))
                            putExtra("MERCHANT_NAME", subsidiary.optString("name"))
                            putExtra("AMOUNT", order.optString("amount"))
                            putExtra("EMAIL", customer.optString("email"))
                            putExtra("REF", referenceNumber)
                            putExtra("APIKEY", apiKey)
                            putExtra("BASEURL", baseurl)
                            putExtra("XMLKEY", hashKey)
                            putExtra("INITIATING_ACTIVITY_CLASS", initiatingClass)
                            putExtra("SUCCESS", successClass)
                            putExtra("FAILED", failureClass)
                        }
                        startActivity(intent)
                    } else {
                        withContext(Dispatchers.Main) {
                            // Create an Intent to start the Failure Activity
                            val intent = Intent(this@PayWithTransactpay, failureClass).apply {
                                putExtra("json_data", jsonResponse.toString()) // Attach JSON String as an extra
                            }
                            startActivity(intent)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, "This is the error $e")
        }
    }

    private suspend fun postEncryptedPayload(
        url: String,
        firstName: String?,
        lastName: String?,
        mobile: String?,
        email: String?,
        amount: Float?,
        ref: String,
        apiKey: String,
        publicKeyXml: String
    ): String? {
        return try {
            val payload = """
                {
                    "customer": {
                        "firstname": "$firstName",
                        "lastname": "$lastName",
                        "mobile": "$mobile",
                        "country": "NG",
                        "email": "$email"
                    },
                    "order": {
                        "amount": $amount,
                        "reference": "$ref",
                        "description": "Pay",
                        "currency": "NGN"
                    },
                    "payment": {
                        "RedirectUrl": ""
                    }
                }
            """.trimIndent()

            val encryptedData = EncryptionUtils.encryptPayloadRSA(payload, publicKeyXml)
                ?: throw Exception("Encryption failed")

            val json = """
                {
                    "data": "$encryptedData"
                }
            """.trimIndent()

            Log.d(TAG, "API KEY IS : $apiKey")
            Log.d(TAG, "Payload is : $payload")
            Log.d(TAG, "Payload is : $encryptedData")

            val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
            val requestBody: RequestBody = json.toRequestBody(mediaType)

            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("accept", "application/json")
                .addHeader("api-key", apiKey)
                .addHeader("content-type", "application/json")
                .build()

            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                response.body?.string()
            } else {
                response.body?.string()
            }
        } catch (e: Exception) {
            "Exception: ${e.message}"
        }
    }

    private fun generateReferenceNumber(): String {
        return "REF-${System.currentTimeMillis()}"
    }
}
