package com.entrego.entregouser.ui.comon

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.entrego.entregouser.R
import kotlinx.android.synthetic.main.activity_contract_us.*
import kotlinx.android.synthetic.main.navigation_toolbar.*


class ContactUsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contract_us)
        nav_toolbar_back.setOnClickListener { onBackPressed() }
        setupListeners()
    }

    fun setupListeners() {
        contact_us_email.setOnClickListener { sendEmail() }
        contact_us_fb_btn.setOnClickListener { openLink(getString(R.string.link_entrego_facebook)) }
        contact_us_insta_btn.setOnClickListener { openLink(getString(R.string.link_entrego_insta)) }
        contact_us_twitter_btn.setOnClickListener { openLink(getString(R.string.link_entrego_twitter)) }
        contact_us_youtube_btn.setOnClickListener { openLink(getString(R.string.link_entrego_youtube)) }
        contact_us_linkein_btn.setOnClickListener { openLink(getString(R.string.link_entrego_linkedin)) }
        contact_us_call_btn.setOnClickListener { }
        contact_us_chat_btn.setOnClickListener { }

    }

    fun openLink(link: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(browserIntent)
    }

    fun sendEmail() {
        val i = Intent(Intent.ACTION_SEND)
        i.type = "message/rfc822"
        i.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.support_email)))
        i.putExtra(Intent.EXTRA_SUBJECT, "Support")
        i.putExtra(Intent.EXTRA_TEXT, "")
        try {
            startActivity(Intent.createChooser(i, getString(R.string.send_email)))
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show()
        }
    }
}
