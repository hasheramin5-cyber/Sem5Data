package com.example.hasheramin245423

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

open class BaseActivity : AppCompatActivity() {

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        val content = findViewById<View>(android.R.id.content)
        content.setBackgroundResource(R.color.primary_dark)
        ViewCompat.setOnApplyWindowInsetsListener(content) { v, insets ->
            val types = WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime()
            val bars = insets.getInsets(types)
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }
    }

    protected fun setupToolbar(showBack: Boolean) {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(showBack)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
