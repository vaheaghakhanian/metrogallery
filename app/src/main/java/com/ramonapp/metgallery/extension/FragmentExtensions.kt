package com.ramonapp.metgallery.extension

import android.os.Bundle
import android.util.Log
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

fun Fragment.safeNavigate(@IdRes resId: Int, args: Bundle? = null) {
    try {
        val navController = view?.findNavController()
        if (navController?.currentDestination?.id == resId) return

        navController?.navigate(resId, args)
    } catch (ignore: Exception) {
        Log.d("NavigationTest", ignore.toString())
    }
}