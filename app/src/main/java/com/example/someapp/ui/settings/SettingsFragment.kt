package com.example.someapp.ui.settings

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.someapp.MainActivity
import com.example.someapp.R

class SettingsFragment : Fragment() {

    private lateinit var root : View
    private lateinit var nightModeCard : CardView
    private lateinit var settingsSwitch : Switch
    private lateinit var pref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_settings, container, false)
        pref = activity!!.getPreferences(Context.MODE_PRIVATE)

        nightModeCard = root.findViewById(R.id.night_mode_card)
        settingsSwitch = root.findViewById(R.id.night_switch)
        if (pref.getBoolean("NIGHT_MODE", false)){
            settingsSwitch.isChecked = true
        }

        settingsSwitch.setOnCheckedChangeListener { _, isChecked ->
            revealButton()
            if (isChecked) {
                pref.edit().putBoolean("NIGHT_MODE", true).apply()
            } else {
                pref.edit().putBoolean("NIGHT_MODE", false).apply()
            }

            Handler().postDelayed({
                startActivity(Intent(context, MainActivity::class.java))
                activity?.finish()
            }, 100L)


        }

        return root
    }

    private fun revealButton(){
        val revealView = root.findViewById<View>(R.id.revealView)
        nightModeCard.elevation = 0f
        revealView.visibility = View.VISIBLE

        val x = revealView.width
        val y = revealView.height

        val startX = 80 + settingsSwitch.x.toInt()
        val startY = 72 + settingsSwitch.y.toInt()

        val radius = x.coerceAtLeast(y) * 1.2f

        val reveal = ViewAnimationUtils.createCircularReveal(revealView, startX, startY, 10f, radius)
        reveal.duration = 500
        reveal.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)

                //activity?.finish()
            }
        })

        reveal.start()
    }
}