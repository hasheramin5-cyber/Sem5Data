package com.example.hasheramin245423

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class TaskAdapter(
    private val context: Context,
    private val tasks: List<Task>
) : BaseAdapter() {

    override fun getCount(): Int {
        return tasks.size
    }

    override fun getItem(position: Int): Any {
        return tasks[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_task, parent, false)
        val task = tasks[position]

        val tvTitle = view.findViewById<TextView>(R.id.item_title)
        val tvInfo = view.findViewById<TextView>(R.id.item_info)
        val tvPriority = view.findViewById<TextView>(R.id.item_priority)
        val tvStatus = view.findViewById<TextView>(R.id.item_status)

        tvTitle.text = task.title
        if (task.done) {
            tvTitle.paintFlags = tvTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            tvTitle.paintFlags = tvTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        tvInfo.text = context.getString(
            R.string.item_info,
            task.course,
            dateOrFallback(context, task.dueDate)
        )
        tvPriority.text = priorityLabel(context, task.priority)
        tvPriority.setTextColor(priorityColor(context, task.priority))
        tvStatus.setText(if (task.done) R.string.status_done else R.string.status_pending)
        return view
    }
}
