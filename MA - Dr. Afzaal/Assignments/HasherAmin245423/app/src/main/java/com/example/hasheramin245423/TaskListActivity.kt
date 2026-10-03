package com.example.hasheramin245423

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog

class TaskListActivity : BaseActivity() {

    private lateinit var listView: ListView
    private lateinit var adapter: TaskAdapter

    private val addLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data = result.data
        if (result.resultCode == RESULT_OK && data != null) {
            val task = Task(
                title = data.getStringExtra(Extras.TITLE) ?: "",
                course = data.getStringExtra(Extras.COURSE) ?: "",
                priority = data.getIntExtra(Extras.PRIORITY, PRIORITY_MEDIUM),
                dueDate = data.getStringExtra(Extras.DUE_DATE) ?: ""
            )
            TaskRepository.tasks.add(task)
            adapter.notifyDataSetChanged()
            Toast.makeText(this, R.string.toast_added, Toast.LENGTH_SHORT).show()
        }
    }

    private val detailLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data = result.data
        if (result.resultCode == RESULT_OK && data != null) {
            val action = data.getStringExtra(Extras.ACTION)
            val position = data.getIntExtra(Extras.POSITION, -1)
            if (action == Extras.ACTION_DONE) {
                markDone(position)
            } else if (action == Extras.ACTION_DELETE) {
                deleteTask(position)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)
        setupToolbar(true)
        supportActionBar?.subtitle = intent.getStringExtra(Extras.STUDENT_NAME)

        listView = findViewById(R.id.list_view)
        adapter = TaskAdapter(this, TaskRepository.tasks)
        listView.adapter = adapter
        listView.emptyView = findViewById<TextView>(R.id.tv_empty)

        listView.setOnItemClickListener { _, _, position, _ ->
            openDetail(position)
        }
        registerForContextMenu(listView)

        findViewById<Button>(R.id.btn_add).setOnClickListener {
            openAdd()
        }
    }

    private fun openAdd() {
        addLauncher.launch(Intent(this, AddTaskActivity::class.java))
    }

    private fun openDetail(position: Int) {
        if (position !in TaskRepository.tasks.indices) {
            return
        }
        val task = TaskRepository.tasks[position]
        val intent = Intent(this, TaskDetailActivity::class.java)
        intent.putExtra(Extras.TITLE, task.title)
        intent.putExtra(Extras.COURSE, task.course)
        intent.putExtra(Extras.PRIORITY, task.priority)
        intent.putExtra(Extras.DUE_DATE, task.dueDate)
        intent.putExtra(Extras.DONE, task.done)
        intent.putExtra(Extras.POSITION, position)
        detailLauncher.launch(intent)
    }

    private fun markDone(position: Int) {
        if (position !in TaskRepository.tasks.indices) {
            return
        }
        TaskRepository.tasks[position].done = true
        adapter.notifyDataSetChanged()
        Toast.makeText(this, R.string.toast_done, Toast.LENGTH_SHORT).show()
    }

    private fun deleteTask(position: Int) {
        if (position !in TaskRepository.tasks.indices) {
            return
        }
        TaskRepository.tasks.removeAt(position)
        adapter.notifyDataSetChanged()
        Toast.makeText(this, R.string.toast_deleted, Toast.LENGTH_SHORT).show()
    }

    private fun confirmDelete(position: Int) {
        if (position !in TaskRepository.tasks.indices) {
            return
        }
        val task = TaskRepository.tasks[position]
        AlertDialog.Builder(this)
            .setTitle(R.string.dialog_delete_title)
            .setMessage(getString(R.string.dialog_delete_message, task.title))
            .setPositiveButton(R.string.menu_delete) { _, _ ->
                deleteTask(position)
            }
            .setNegativeButton(R.string.btn_cancel, null)
            .show()
    }

    // ---------------- Options Menu ----------------

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_task_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                openAdd()
                true
            }
            R.id.sort_priority -> {
                TaskRepository.tasks.sortBy { it.priority }
                sortDone()
                true
            }
            R.id.sort_due_date -> {
                TaskRepository.tasks.sortBy { parseDate(it.dueDate) }
                sortDone()
                true
            }
            R.id.sort_title -> {
                TaskRepository.tasks.sortBy { it.title.lowercase() }
                sortDone()
                true
            }
            R.id.action_about -> {
                startActivity(Intent(this, AboutActivity::class.java))
                true
            }
            R.id.action_exit -> {
                finishAffinity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun sortDone() {
        adapter.notifyDataSetChanged()
        Toast.makeText(this, R.string.toast_sorted, Toast.LENGTH_SHORT).show()
    }

    // ---------------- Context Menu ----------------

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        if (v?.id == R.id.list_view) {
            val info = menuInfo as AdapterView.AdapterContextMenuInfo
            menu?.setHeaderTitle(TaskRepository.tasks[info.position].title)
            menuInflater.inflate(R.menu.menu_task_context, menu)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as? AdapterView.AdapterContextMenuInfo
            ?: return super.onContextItemSelected(item)
        val position = info.position
        return when (item.itemId) {
            R.id.ctx_view -> {
                openDetail(position)
                true
            }
            R.id.ctx_done -> {
                markDone(position)
                true
            }
            R.id.ctx_delete -> {
                confirmDelete(position)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}
