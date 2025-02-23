package com.example.lab1advand.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.lab1advand.R
import com.example.lab1advand.databinding.FragmentHomeBinding
import com.example.lab1advand.service.MusicService

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnMusicStart.setOnClickListener {
            val intent = Intent(requireContext(), MusicService::class.java).apply {
                action = MusicService.ACTION_PLAY
            }
            requireContext().startService(intent)
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
