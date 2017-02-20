package com.entrego.entregouser.ui.delivery.escort.chat

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.entrego.entregouser.R
import com.entrego.entregouser.mvp.view.BaseMvpActivity
import com.entrego.entregouser.util.ui.EntregoCancelableProgressDialog
import kotlinx.android.synthetic.main.activity_chat_messenger.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

class ChatMessengerActivity : BaseMvpActivity<ChatContract.View, ChatContract.Presenter>(),
        ChatContract.View {


    companion object {
        fun getIntent(ctx: Context): Intent {
            val intent = Intent(ctx, ChatMessengerActivity::class.java)
            return intent
        }
    }

    override var mPresenter: ChatContract.Presenter = ChatMessengerPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_messenger)
        mPresenter.attachView(this)
        setupLayouts()
    }

    fun setupLayouts() {
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }

        val layoutManager = LinearLayoutManager(this)
        chat_recycler.layoutManager = layoutManager
        chat_recycler.itemAnimator = DefaultItemAnimator()
//        val adapter = ChatThreadAdapter()
//        chat_recycler.setAdapter(adapter)
        chat_send.setOnClickListener {

            if (chat_user_message.text.toString().isNotEmpty()) {

                chat_user_message.setText("")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    var mProgress: EntregoCancelableProgressDialog? = null



    override fun getRootView(): View = activity_chat_messenger

}
