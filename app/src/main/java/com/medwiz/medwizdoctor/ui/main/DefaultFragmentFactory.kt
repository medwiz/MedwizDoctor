package com.medwiz.medwizdoctor.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory


import javax.inject.Inject

class DefaultFragmentFactory @Inject constructor() : FragmentFactory() {

    override fun instantiate(
        classLoader: ClassLoader,
        className: String): Fragment {

        return when (className) {
            //LoginFragment::class.java.name-> LoginFragment()
            //SignUpFragment::class.java.name-> SignUpFragment()
            else -> super.instantiate(classLoader, className)
        }
    }
}