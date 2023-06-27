package com.gorkem.whatzzup.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.gorkem.whatzzup.R
import com.gorkem.whatzzup.domain.model.Note

fun View.show(){
    this.visibility = View.VISIBLE
}

fun View.hide(){
    this.visibility = View.INVISIBLE
}

fun Fragment.isConnected(): Boolean {
    var status = false
    val conManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        if (conManager != null && conManager.activeNetwork != null && conManager.getNetworkCapabilities(
                conManager.activeNetwork
            ) != null
        ) {
            status = true
        }
    } else {
        if (conManager.activeNetwork != null) {
            status = true
        }
    }
    return status
}

fun Fragment.snackbar(message : String){
    view?.let { rootView ->
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show()
    }
}

