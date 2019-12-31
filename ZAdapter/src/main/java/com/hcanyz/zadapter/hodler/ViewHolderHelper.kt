package com.hcanyz.zadapter.hodler

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 * @author hcanyz
 */
open class ViewHolderHelper(private val fragment: Fragment? = null, private val fragmentActivity: FragmentActivity? = null) {

    fun requireFragment(): Fragment {
        return fragment
                ?: throw IllegalStateException("fragment is null.")
    }

    fun requireFragmentActivity(): FragmentActivity {
        return fragmentActivity
                ?: throw IllegalStateException("fragmentActivity is null.")
    }
}