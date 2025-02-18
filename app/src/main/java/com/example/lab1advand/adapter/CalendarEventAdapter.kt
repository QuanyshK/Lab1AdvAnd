package com.example.lab1advand.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1advand.databinding.ItemCalendarEventBinding
import com.example.lab1advand.models.CalendarEvent
import java.text.SimpleDateFormat
import java.util.*

class CalendarEventAdapter : ListAdapter<CalendarEvent, CalendarEventAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCalendarEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemCalendarEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: CalendarEvent) {
            binding.eventTitle.text = event.title
            binding.eventDate.text = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault()).format(Date(event.date))
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<CalendarEvent>() {
        override fun areItemsTheSame(oldItem: CalendarEvent, newItem: CalendarEvent) = oldItem.date == newItem.date
        override fun areContentsTheSame(oldItem: CalendarEvent, newItem: CalendarEvent) = oldItem == newItem
    }
}
