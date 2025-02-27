package com.example.lab1advand.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.lab1advand.R
import com.example.lab1advand.databinding.FragmentHomeBinding
import com.example.lab1advand.service.MusicService

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                Toast.makeText(requireContext(), "Permission Agreed", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Permission DENIEEDDDDD", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (requireContext().checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED) {
            permissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }

        binding.btnMusicStart.setOnClickListener {
            val intent = Intent(requireContext(), MusicService::class.java).apply {
                action = MusicService.ACTION_PLAY
            }
            requireContext().startForegroundService(intent)
        }

        binding.btnMusicPause.setOnClickListener {
            val intent = Intent(requireContext(), MusicService::class.java).apply {
                action = MusicService.ACTION_PAUSE
            }
            requireContext().startService(intent)
        }

        binding.btnMusicStop.setOnClickListener {
            val intent = Intent(requireContext(), MusicService::class.java).apply {
                action = MusicService.ACTION_STOP
            }
            requireContext().startService(intent)
        }

        binding.btnAirplaneMode.setOnClickListener {
            findNavController().navigate(R.id.receiverFragment)
        }

        binding.btnCalendarEvents.setOnClickListener {
            findNavController().navigate(R.id.calendarListFragment)
        }

        binding.btnStoryShare.setOnClickListener {
            findNavController().navigate(R.id.storyFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
//dont even try to copy:))
