package com.example.hasheramin245423

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : BaseActivity() {

    private lateinit var tvPending: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar(false)

        tvPending = findViewById(R.id.tv_pending)

        findViewById<Button>(R.id.btn_my_tasks).setOnClickListener {
            val intent = Intent(this, TaskListActivity::class.java)
            intent.putExtra(Extras.STUDENT_NAME, getString(R.string.student_name))
            startActivity(intent)
        }

        findViewById<Button>(R.id.btn_about).setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        tvPending.text = getString(R.string.pending_tasks, TaskRepository.pendingCount())
    }
}
