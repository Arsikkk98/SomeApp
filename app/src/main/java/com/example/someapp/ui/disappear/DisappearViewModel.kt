package com.example.someapp.ui.disappear

import android.graphics.Color
import android.os.CountDownTimer
import androidx.core.graphics.alpha
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

class DisappearViewModel : ViewModel(){

    private val firstDigit = MutableLiveData<String>()
    private val secondDigit = MutableLiveData<String>()
    private val thirdDigit = MutableLiveData<String>()
    private val fourthDigit = MutableLiveData<String>()

    fun getDigitNow(digit: Int): LiveData<String> {
        when (digit) {
            1 -> {
                firstDigit.value = "0"
                startFirstTimer()
                return firstDigit
            }
            2 -> {
                secondDigit.value = "0"
                startSecondTimer()
                return secondDigit
            }
            3 -> {
                thirdDigit.value = "0"
                startThirdTimer()
                return thirdDigit
            }
            4 -> {
                fourthDigit.value = "0"
                startFourthTimer()
                return fourthDigit
            }
            else -> {
                return firstDigit
            }
        }
    }

    private fun startFirstTimer() {
        var timer = object: CountDownTimer(2000, 90){
            override fun onFinish() {
                firstDigit.value = "2"
            }

            override fun onTick(millisUntilFinished: Long) {
                var nowValue = firstDigit.value.toString().toInt()
                if (nowValue == 9)
                    nowValue = 0
                else
                    nowValue++
                firstDigit.value = nowValue.toString()
            }
        }.start()
    }

    private fun startSecondTimer() {
        var timer = object: CountDownTimer(3000, 80){
            override fun onFinish() {
                secondDigit.value = "0"
            }

            override fun onTick(millisUntilFinished: Long) {
                var nowValue = secondDigit.value.toString().toInt()
                if (nowValue == 9)
                    nowValue = 0
                else
                    nowValue++
                secondDigit.value = nowValue.toString()
            }
        }.start()
    }

    private fun startThirdTimer() {
        var timer = object: CountDownTimer(4000, 70){
            override fun onFinish() {
                val sdf = SimpleDateFormat("yyyy")
                val currentYear = sdf.format(Date()).toInt()
                thirdDigit.value = ((currentYear/10)%10).toString()
            }

            override fun onTick(millisUntilFinished: Long) {
                var nowValue = thirdDigit.value.toString().toInt()
                if (nowValue == 9)
                    nowValue = 0
                else
                    nowValue++
                thirdDigit.value = nowValue.toString()
            }
        }.start()
    }

    private fun startFourthTimer() {
        var timer = object: CountDownTimer(5000, 60){
            override fun onFinish() {
                val sdf = SimpleDateFormat("yyyy")
                val currentYear = sdf.format(Date()).toInt()
                fourthDigit.value = (currentYear%10).toString()
            }

            override fun onTick(millisUntilFinished: Long) {
                var nowValue = fourthDigit.value.toString().toInt()
                if (nowValue == 9)
                    nowValue = 0
                else
                    nowValue++
                fourthDigit.value = nowValue.toString()
            }
        }.start()
    }
}