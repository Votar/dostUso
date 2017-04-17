package com.entrego.entregouser.ui.intro

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.entrego.entregouser.R
import com.github.paolorotolo.appintro.AppIntro


class IntroActivity : AppIntro() {
    companion object {
        fun getIntent(ctx: Context): Intent {
            val intent = Intent(ctx, IntroActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val titles = resources.getStringArray(R.array.itro_titles)
        val descriptions = resources.getStringArray(R.array.intro_descr)
        val introImages = listOf<Int>(
                R.drawable.introslider1,
                R.drawable.introslider2,
                R.drawable.introslider3,
                R.drawable.introslider4,
                R.drawable.introslider5,
                R.drawable.introslider6,
                R.drawable.introslider7,
                R.drawable.introslider8,
                R.drawable.introslider9,
                R.drawable.introslider10
        )

        addSlide(WelcomeFragment())
        titles.forEachIndexed { index, value ->
            addSlide(IntroFragment.newInstance(titles[index], descriptions[index], introImages[index]))
        }

        setBarColor(ContextCompat.getColor(this, R.color.colorTransparent))
        setSeparatorColor(ContextCompat.getColor(this, R.color.colorTransparent))

        setNextArrowColor(ContextCompat.getColor(this, R.color.colorDarkBlue))
        setColorDoneText(ContextCompat.getColor(this, R.color.colorDarkBlue))
        selectedIndicatorColor = ContextCompat.getColor(this, R.color.colorDarkBlue)
        unselectedIndicatorColor = ContextCompat.getColor(this, R.color.colorDarkGrey)

        showSkipButton(false)
        isProgressButtonEnabled = false
        showSkipButton(false)
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onSlideChanged(oldFragment: Fragment?, newFragment: Fragment?) {
        super.onSlideChanged(oldFragment, newFragment)

        when (pager.currentItem) {
            0 -> {
                isProgressButtonEnabled = false
                setNextArrowColor(ContextCompat.getColor(this, R.color.colorTransparent))
                setSeparatorColor(ContextCompat.getColor(this, R.color.colorTransparent))
            }
            else -> {
                setSeparatorColor(ContextCompat.getColor(this, R.color.colorDarkBlue))
                isProgressButtonEnabled = true
                setNextArrowColor(ContextCompat.getColor(this, R.color.colorDarkBlue))
            }
        }
    }

    override fun onDonePressed() {
        super.onDonePressed()
        pager.setCurrentItem(0, false)
    }
}
