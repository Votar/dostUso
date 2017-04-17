package com.entregoya.entregouser.ui.intro

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.entregoya.entregouser.R
import kotlinx.android.synthetic.main.fragment_entego_intro.*

/**
 * Created by Admin on 17.01.2017.
 */
class IntroFragment : Fragment() {

    companion object {
        private val TITLE_KEY = "t_k"
        private val DESCRIPTION_KEY = "d_k"
        private val DRAWABLE_KEY = "dr_k"
        fun newInstance(title: String, description: String, idDrawable: Int): Fragment {
            val fragment = IntroFragment()
            val args = Bundle()
            args.putString(TITLE_KEY, title)
            args.putString(DESCRIPTION_KEY, description)
            args.putInt(DRAWABLE_KEY, idDrawable)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(R.layout.fragment_entego_intro, container, false)

        return view
    }

    override fun onStart() {
        super.onStart()
        if (arguments != null) {
            val drawableId = arguments.getInt(DRAWABLE_KEY)
            intro_drawable.setImageDrawable(ContextCompat.getDrawable(activity, drawableId))
            val title = arguments.getString(TITLE_KEY)
            intro_title.text = title
            val description = arguments.getString(DESCRIPTION_KEY)
            intro_description.text = description
        }
    }
}