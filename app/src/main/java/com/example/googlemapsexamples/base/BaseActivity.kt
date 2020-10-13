package com.example.googlemapsexamples.base

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.googlemapsexamples.R
import com.example.googlemapsexamples.utill.showSnackBar


abstract class BaseActivity : AppCompatActivity() {

    abstract fun setLayout(): Int

    abstract fun initView(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayout())
        initView(savedInstanceState)

    }

    fun showToast(message: String) {
        return Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun isNetworkConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null
    }

    fun noInternetError() {
        showMessage(getString(R.string.no_internet_connection))
    }

    private fun showMessage(message: String?) {
        message?.let {
            showSnackBar(it, this, R.color.red)
        }
    }
}