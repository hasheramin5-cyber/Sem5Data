package com.example.hasheramin245423

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import java.util.Calendar
import java.util.Locale

class AddTaskActivity : BaseActivity() {

    private lateinit var etTitle: EditText
    private lateinit var etCourse: EditText
    private lateinit var etDueDate: EditText
    private lateinit var rgPriority: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        setupToolbar(true)

        etTitle = findViewById(R.id.et_title)
        etCourse = findViewById(R.id.et_course)
        etDueDate = findViewById(R.id.et_due_date)
        rgPriority = findViewById(R.id.rg_priority)

        etDueDate.setOnClickListener {
            showDatePicker()
        }

        findViewById<Button>(R.id.btn_save).setOnClickListener {
            saveTask()
        }

        findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    private fun showDatePicker() {
        val cal = Calendar.getInstance()
        val dialog = DatePickerDialog(
            this,
            { _, year, month, day ->
                etDueDate.setText(
                    String.format(Locale.getDefault(), "%02d/%02d/%04d", day, month + 1, year)
                )
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )
        dialog.show()
    }

    private fun saveTask() {
        val title = etTitle.text.toString().trim()
        if (title.isEmpty()) {
            etTitle.error = getString(R.string.error_title_required)
            etTitle.requestFocus()
            return
        }

        val priority = when (rgPriority.checkedRadioButtonId) {
            R.id.rb_high -> PRIORITY_HIGH
            R.id.rb_low -> PRIORITY_LOW
            else -> PRIORITY_MEDIUM
        }

        val result = Intent()
        result.putExtra(Extras.TITLE, title)
        result.putExtra(Extras.COURSE, etCourse.text.toString().trim())
        result.putExtra(Extras.PRIORITY, priority)
        result.putExtra(Extras.DUE_DATE, etDueDate.text.toString().trim())
        setResult(RESULT_OK, result)
        finish()
    }
}
