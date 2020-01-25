package com.example.someapp.ui.disappear

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.someapp.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay


class DisappearFragment : Fragment(), View.OnTouchListener {

    private lateinit var disappearViewModel: DisappearViewModel
    private lateinit var root: View
    private lateinit var disappearLayer: LinearLayout

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

    /*private fun twistFirstDigit() {
        var first = root.findViewById<TextView>(R.id.first)
        GlobalScope.async(){
            var counter = 20
            while (counter > 0){
                var nowValue = first.text.toString().toInt()
                if (nowValue == 9)
                    nowValue = 0
                else
                    nowValue++
                first.text = nowValue.toString()
                delay(100)
                counter--
            }
        }
    }*/
}