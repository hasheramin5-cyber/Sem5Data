package com.example.hasheramin245423

import android.content.Context
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.Locale

data class Task(
    var title: String,
    var course: String,
    var priority: Int,
    var dueDate: String,
    var done: Boolean = false
)

object TaskRepository {
    val tasks = mutableListOf<Task>()

    fun pendingCount(): Int {
        return tasks.count { !it.done }
    }
}

const val PRIORITY_HIGH = 0
const val PRIORITY_MEDIUM = 1
const val PRIORITY_LOW = 2

fun priorityLabel(context: Context, priority: Int): String {
    val res = when (priority) {
        PRIORITY_HIGH -> R.string.priority_high
        PRIORITY_MEDIUM -> R.string.priority_medium
        else -> R.string.priority_low
    }
    return context.getString(res)
}

fun priorityColor(context: Context, priority: Int): Int {
    val res = when (priority) {
        PRIORITY_HIGH -> R.color.priority_high
        PRIORITY_MEDIUM -> R.color.priority_medium
        else -> R.color.priority_low
    }
    return ContextCompat.getColor(context, res)
}

fun dateOrFallback(context: Context, date: String): String {
    return if (date.isBlank()) context.getString(R.string.not_set) else date
}

fun parseDate(date: String): Long {
    if (date.isBlank()) {
        return Long.MAX_VALUE
    }
    return try {
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(date)?.time ?: Long.MAX_VALUE
    } catch (e: Exception) {
        Long.MAX_VALUE
    }
}
