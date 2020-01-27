package com.example.someapp.ui.disappear

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.someapp.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class DisappearFragment : Fragment(), View.OnTouchListener {

    private lateinit var disappearViewModel: DisappearViewModel
    private lateinit var root: View
    private lateinit var disappearLayer: ConstraintLayout

    private var continueChangingDigits = true

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        disappearViewModel =
            ViewModelProviders.of(this).get(DisappearViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_disappear, container, false)

        disappearLayer = root.findViewById(R.id.disappear_layer)
        disappearLayer.setOnTouchListener(this)

        val first = root.findViewById<TextView>(R.id.first)
        val second = root.findViewById<TextView>(R.id.second)
        val third = root.findViewById<TextView>(R.id.third)
        val fourth = root.findViewById<TextView>(R.id.fourth)
        val viewModel by lazy {
            ViewModelProviders.of(this).get(DisappearViewModel::class.java)
        }
        viewModel.getDigitNow(1).observe(this, Observer<String>{ digit ->
            first.text = digit
        })
        viewModel.getDigitNow(2).observe(this, Observer<String>{ digit ->
            second.text = digit
        })
        viewModel.getDigitNow(3).observe(this, Observer<String>{ digit ->
            third.text = digit
        })
        viewModel.getDigitNow(4).observe(this, Observer<String>{ digit ->
            fourth.text = digit
        })

        return root
    }

    var yDown: Float = 0.toFloat()
    var yUp: Float = 0.toFloat()

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN
            -> {
                yDown = event.y
            }
            MotionEvent.ACTION_UP
            -> {
                yUp = event.y
                val delta: Float = yDown - yUp
                if (delta > 300) graduallyDisappearAsync()
            }
        }
        return true
    }

    private fun graduallyDisappearAsync() {
        disappearLayer.removeAllViews()
        GlobalScope.async(){
            val back = disappearLayer.background
            while (back.alpha > 20){
                back.alpha -= 20
                disappearLayer.background = back
                delay(70)
            }
            back.alpha = 0
            disappearLayer.background = back
        }
    }

    override fun onStop() {
        super.onStop()
        val back = disappearLayer.background
        back.alpha = 255
        disappearLayer.background = back
    }
}