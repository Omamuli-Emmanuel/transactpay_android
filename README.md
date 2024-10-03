# Transactpay Android SDK Integration - v0.5.8-stable

The **Transactpay Android SDK** simplifies payment processing by providing a seamless way to initiate transactions with encryption support. This guide will walk you through the setup and usage of the SDK in your Android app.

## Table of Contents

1. [Prerequisites](#prerequisites)
2. [Installation](#installation)
3. [Usage](#usage)
   - [Initialization](#initialization)
   - [Starting a Payment Transaction](#starting-a-payment-transaction)
   - [Handling Responses](#handling-responses)
4. [License](#license)

---

## Prerequisites

Before integrating the SDK, ensure you meet the following requirements:

- **Android Studio** version 4.1 or higher.
- **Minimum SDK**: 21 (Android 5.0 Lollipop).
- **Kotlin**: The SDK is built using Kotlin, so your project should be configured for Kotlin.
- **Internet Permission**: Add the following permission to your `AndroidManifest.xml` file:
    ```xml
    <uses-permission android:name="android.permission.INTERNET" />
    ```

## Installation

To include the Transactpay Android SDK in your project, add the following dependency in your `build.gradle` (Module: app) file:

```gradle
dependencies {
    implementation("com.github.Omamuli-Emmanuel:transactpay_android:v0.5.8-stable")
}
```

Then, sync your project with Gradle to download the required libraries.

## Usage

Follow these steps to set up the payment flow in your application using the `PayWithTransactpay` class.

### Initialization

Ensure your main activity extends `AppCompatActivity`. In this activity, you will prepare the parameters needed for the transaction and start the payment process.

### Starting a Payment Transaction

Use the following code to start a payment transaction. The SDK requires several parameters that you will pass via `Intent`.

```kotlin
val intent = Intent(this@MainActivity, PayWithTransactpay::class.java).apply {
    putExtra("Fname", firstName)
    putExtra("Lname", lastName)
    putExtra("Phone", phoneNumber)
    putExtra("AMOUNT", amount)
    putExtra("EMAIL", email)
    putExtra("API_KEY", apiKey)
    putExtra("XMLKEY", encryptionKey)
    putExtra("INITIATING_ACTIVITY_CLASS", MainActivity::class.java)
    putExtra("SUCCESS_CLASS", Success::class.java)
    putExtra("FAILURE_CLASS", Failed::class.java)
    putExtra("TransactionRef", reference)
}
startActivity(intent)
```
## Parameters

- **Fname**: The customer's first name (String).
- **Lname**: The customer's last name (String).
- **Phone**: The customer's phone number (String).
- **AMOUNT**: The transaction amount (String).
- **EMAIL**: The customer's email address (String).
- **API_KEY**: The API key provided by Transactpay for authentication.
- **XMLKEY**: The encryption key used to secure the payment data.
- **INITIATING_ACTIVITY_CLASS**: The activity that starts the payment process.
- **SUCCESS_CLASS**: The activity to navigate to upon successful payment.
- **FAILURE_CLASS**: The activity to navigate to if the payment fails.
- **TransactionRef**: A unique reference for the transaction. If this is not provided, the SDK will generate a new reference number automatically.

## Handling Responses

After initiating the transaction, the SDK will return either a success or failure response. Upon response, the user will be redirected to either the Success or Failed activity:

- **Success Response**: If the transaction succeeds, the user will be redirected to the Success activity.
- **Failure Response**: If the transaction fails, the user will be redirected to the Failed activity. You can also retrieve additional details, such as error messages or JSON data from the failure response.

In the activities, you can handle the data sent back by the SDK.

### Example for Success Activity:


```
class Success : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)

        // Handle success data here
        val firstName = intent.getStringExtra("Fname")
        val amount = intent.getStringExtra("AMOUNT")
        // Further logic to display or handle data
    }
}
```
```
class Failed : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_failed)

        // Handle failure data here
        val errorMessage = intent.getStringExtra("json_data")
        // Further logic to display error or retry logic
    }
}
```
## License

This SDK is open-source and licensed under the MIT License. Feel free to modify and use it in your projects. For full details, refer to the LICENSE file in the repository.

```

This README provides clear instructions on how to integrate the Transactpay Android SDK, handle payment transactions, and respond to success or failure results in your Android app.
```
