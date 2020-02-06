package com.example.someapp.ui.disappear

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.someapp.R
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay


class DisappearFragment : Fragment(), View.OnTouchListener {

    private val disappearViewModel: DisappearViewModel by viewModels()
    private lateinit var root: View
    private lateinit var disappearLayer: ConstraintLayout
    private lateinit var imageView: ImageView

    private lateinit var swipeTextView: TextView

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_disappear, container, false)

        disappearLayer = root.findViewById(R.id.disappear_layer)
        disappearLayer.setOnTouchListener(this)

        val first = root.findViewById<TextView>(R.id.first)
        val second = root.findViewById<TextView>(R.id.second)
        val third = root.findViewById<TextView>(R.id.third)
        val fourth = root.findViewById<TextView>(R.id.fourth)
        swipeTextView = root.findViewById(R.id.swipe_textView)

        disappearViewModel.getDigitNow(1).observe(this, Observer<String>{ digit ->
            first.text = digit
        })
        disappearViewModel.getDigitNow(2).observe(this, Observer<String>{ digit ->
            second.text = digit
        })
        disappearViewModel.getDigitNow(3).observe(this, Observer<String>{ digit ->
            third.text = digit
        })
        disappearViewModel.getDigitNow(4).observe(this, Observer<String>{ digit ->
            fourth.text = digit
        })

        imageView = root.findViewById(R.id.under_image)

        val builder = Picasso.Builder(requireContext())
        builder.downloader(OkHttp3Downloader(context))
        builder.build().load("https://picsum.photos/1080/1920.jpg").placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder_error).into(imageView)

        val animation = AnimationUtils.loadAnimation(context, R.anim.anim_alpha)
        swipeTextView.startAnimation(animation)

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
        swipeTextView.clearAnimation()
        disappearLayer.removeAllViews()
        GlobalScope.async{
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