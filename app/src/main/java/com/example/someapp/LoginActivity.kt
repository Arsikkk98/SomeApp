package com.example.someapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private val tag = LoginActivity::getLocalClassName.toString()

    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    private lateinit var biometricManager: BiometricManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val firstEditText =  findViewById<EditText>(R.id.first_digit)
        val secondEditText =  findViewById<EditText>(R.id.second_digit)
        val thirdEditText =  findViewById<EditText>(R.id.third_digit)
        val fourthEditText =  findViewById<EditText>(R.id.fourth_digit)

        firstEditText.addTextChangedListener(CustomTextWatcher(firstEditText, secondEditText))
        secondEditText.addTextChangedListener(CustomTextWatcher(secondEditText, thirdEditText))
        thirdEditText.addTextChangedListener(CustomTextWatcher(thirdEditText, fourthEditText))

        fourthEditText.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (firstEditText.text.toString() != "0")
                    return
                if (secondEditText.text.toString() != "0")
                    return
                if (thirdEditText.text.toString() != "0")
                    return
                if (fourthEditText.text.toString() != "0")
                    return
                goToMainActivity()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        //region Biometric
        biometricManager = BiometricManager.from(this)
        val executor = ContextCompat.getMainExecutor(this)

        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback(){
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    if (errString != getString(R.string.biometric_cancel))
                        showToast("Biometric authentification error:\n$errString")
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    goToMainActivity()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    showToast("Authentication failed")
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.biometric_title))
            .setDescription(getString(R.string.biometric_desc))
            .setNegativeButtonText(getString(R.string.biometric_cancel))
            .build()

        checkBiometricStatus(biometricManager)
        //endregion
        biometricPrompt.authenticate(promptInfo)

        login_btn.setOnClickListener{
            biometricPrompt.authenticate(promptInfo)
        }
    }

    private inner class CustomTextWatcher(private val thisEditText: EditText,
                                          private val nextEditText: EditText) : TextWatcher {

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            if (thisEditText.text.toString() == "")
                nextEditText.requestFocus()
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable) {}
    }

    private fun showToast(message : String){
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    private fun goToMainActivity(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
}

    private fun checkBiometricStatus(biometricManager: BiometricManager){
        when(biometricManager.canAuthenticate()){
            BiometricManager.BIOMETRIC_SUCCESS->
                Log.d(tag, "App can use")
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE->
                Log.d(tag, "No biometric features")
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE->
                Log.d(tag, "Currently unavailable")
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED->
                Log.d(tag, "No any biometric")
        }
    }
}
