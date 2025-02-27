package com.example.lab1advand.ui

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab1advand.adapter.CalendarEventAdapter
import com.example.lab1advand.databinding.FragmentCalendarListBinding
import com.example.lab1advand.models.CalendarEvent

class CalendarListFragment : Fragment() {

    private var _binding: FragmentCalendarListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CalendarEventAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCalendarListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CalendarEventAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        requestPermission.launch(Manifest.permission.READ_CALENDAR)
    }

    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) {
            fetchCalendarEvents()
        } else {
            Toast.makeText(requireContext(), "Разрешение на доступ к календарю отклонено", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchCalendarEvents() {
        val events = mutableListOf<CalendarEvent>()
        val resolver: ContentResolver = requireContext().contentResolver
        val cursor: Cursor? = resolver.query(
            CalendarContract.Events.CONTENT_URI,
            arrayOf(CalendarContract.Events.TITLE, CalendarContract.Events.DTSTART),
            "${CalendarContract.Events.DTSTART} >= ?",
            arrayOf(System.currentTimeMillis().toString()),
            "${CalendarContract.Events.DTSTART} ASC"
        )

        cursor?.use {
            while (it.moveToNext()) {
                val title = it.getString(0) ?: "Без названия"
                val date = it.getLong(1)
                events.add(CalendarEvent(title, date))
            }
        }

        adapter.submitList(events)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
//dont even try to copy:))

