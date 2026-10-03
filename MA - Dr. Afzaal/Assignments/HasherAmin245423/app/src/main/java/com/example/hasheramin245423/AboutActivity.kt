package com.example.hasheramin245423

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class AboutActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        setupToolbar(true)

        findViewById<Button>(R.id.btn_email).setOnClickListener {
            sendEmail()
        }

        findViewById<Button>(R.id.btn_docs).setOnClickListener {
            openDocs()
        }
    }

    private fun sendEmail() {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:" + getString(R.string.email_address))
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject))
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, R.string.toast_no_email, Toast.LENGTH_SHORT).show()
        }
    }

    private fun openDocs() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://developer.android.com"))
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, R.string.toast_no_browser, Toast.LENGTH_SHORT).show()
        }
    }
}
