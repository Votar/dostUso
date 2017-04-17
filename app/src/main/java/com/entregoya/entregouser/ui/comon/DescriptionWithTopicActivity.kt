package com.entregoya.entregouser.ui.comon

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.entregoya.entregouser.R
import kotlinx.android.synthetic.main.activity_description_with_topic.*
import kotlinx.android.synthetic.main.navigation_toolbar.*
import java.lang.IllegalArgumentException

class DescriptionWithTopicActivity : AppCompatActivity() {

    companion object {
        const val KEY_TITLE = "ext_k_title"
        const val KEY_MESSAGE = "ext_k_messange"
        fun getIntent(ctx: Context, title: String, message: String): Intent =
                Intent(ctx, DescriptionWithTopicActivity::class.java).apply {
                    putExtra(KEY_TITLE, title)
                    putExtra(KEY_MESSAGE, message)
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description_with_topic)
        deserializeIntent()
        nav_toolbar_back.setOnClickListener { onBackPressed() }
    }

    fun deserializeIntent() {
        if (intent.hasExtra(KEY_TITLE) && intent.hasExtra(KEY_MESSAGE)) {
            intent.apply {
                setupContent(getStringExtra(KEY_TITLE),
                        getStringExtra(KEY_MESSAGE))
            }
        } else IllegalArgumentException("No title or message in intent")
    }

    fun setupContent(title: String, message: String) {
        description_with_title.text = title
        description_with_text.text = message
    }


}
