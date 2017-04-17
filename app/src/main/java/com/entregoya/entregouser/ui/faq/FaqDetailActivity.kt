package com.entregoya.entregouser.ui.faq

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.entregoya.entregouser.R
import kotlinx.android.synthetic.main.activity_faq_detail.*
import kotlinx.android.synthetic.main.navigation_toolbar.*


class FaqDetailActivity : AppCompatActivity() {

    companion object {
        val EXT_TITLE = "ext_title_faq"
        val EXT_MSG = "ext_msg_faq"
        val EXT_KEY_EMAIL_LINK = "ext_k_link"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq_detail)
    }

    override fun onStart() {
        super.onStart()
        nav_toolbar_back.setOnClickListener { onBackPressed() }
        if (intent != null) {
            val title = intent.getStringExtra(EXT_TITLE)
            val message = intent.getStringExtra(EXT_MSG)
            faq_det_title.text = title
            faq_det_body.text = message

            if (intent.hasExtra(EXT_KEY_EMAIL_LINK))
                faq_send_email_link.visibility = View.VISIBLE

        }

        faq_send_email_link.setOnClickListener {
            val i = Intent(Intent.ACTION_SEND)
            i.type = "message/rfc822"
            i.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.support_email)))
            i.putExtra(Intent.EXTRA_SUBJECT, faq_det_title.text.toString())
            try {
                startActivity(Intent.createChooser(i, getString(R.string.send_email)))
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show()
            }

        }
    }
}
