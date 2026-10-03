package com.example.hasheramin245423

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast

class TaskDetailActivity : BaseActivity() {

    private var position = -1
    private var title = ""
    private var course = ""
    private var priority = PRIORITY_MEDIUM
    private var dueDate = ""
    private var done = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)
        setupToolbar(true)

        title = intent.getStringExtra(Extras.TITLE) ?: ""
        course = intent.getStringExtra(Extras.COURSE) ?: ""
        priority = intent.getIntExtra(Extras.PRIORITY, PRIORITY_MEDIUM)
        dueDate = intent.getStringExtra(Extras.DUE_DATE) ?: ""
        done = intent.getBooleanExtra(Extras.DONE, false)
        position = intent.getIntExtra(Extras.POSITION, -1)

        findViewById<TextView>(R.id.tv_title).text = title
        findViewById<TextView>(R.id.tv_course).text = course.ifBlank { getString(R.string.not_set) }
        val tvPriority = findViewById<TextView>(R.id.tv_priority)
        tvPriority.text = priorityLabel(this, priority)
        tvPriority.setTextColor(priorityColor(this, priority))
        findViewById<TextView>(R.id.tv_due).text = dateOrFallback(this, dueDate)
        findViewById<TextView>(R.id.tv_status).setText(
            if (done) R.string.status_done else R.string.status_pending
        )

        val ivMore = findViewById<ImageView>(R.id.iv_more)
        ivMore.setOnClickListener {
            showPopup(ivMore)
        }
    }

    private fun showPopup(anchor: ImageView) {
        val popup = PopupMenu(this, anchor)
        popup.menuInflater.inflate(R.menu.menu_task_popup, popup.menu)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.pop_share -> {
                    shareTask()
                    true
                }
                R.id.pop_done -> {
                    returnAction(Extras.ACTION_DONE)
                    true
                }
                R.id.pop_delete -> {
                    returnAction(Extras.ACTION_DELETE)
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    private fun returnAction(action: String) {
        val result = Intent()
        result.putExtra(Extras.ACTION, action)
        result.putExtra(Extras.POSITION, position)
        setResult(RESULT_OK, result)
        finish()
    }

    private fun shareTask() {
        val status = getString(if (done) R.string.status_done else R.string.status_pending)
        val summary = getString(
            R.string.share_summary,
            title,
            course.ifBlank { getString(R.string.not_set) },
            priorityLabel(this, priority),
            dateOrFallback(this, dueDate),
            status
        )
        val send = Intent(Intent.ACTION_SEND)
        send.type = "text/plain"
        send.putExtra(Intent.EXTRA_TEXT, summary)
        try {
            startActivity(Intent.createChooser(send, getString(R.string.share_chooser)))
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, R.string.toast_no_share, Toast.LENGTH_SHORT).show()
        }
    }
}
