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
import com.example.lab1advand.databinding.FragmentMusicBinding
import com.example.lab1advand.service.MusicService

class MusicFragment : Fragment() {

    private var _binding: FragmentMusicBinding? = null
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
        _binding = FragmentMusicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (requireContext().checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED) {
            permissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }

        binding.btnStart.setOnClickListener {
            val intent = Intent(requireContext(), MusicService::class.java).apply {
                action = MusicService.ACTION_PLAY
            }
            requireContext().startService(intent)
        }

        binding.btnPause.setOnClickListener {
            val intent = Intent(requireContext(), MusicService::class.java).apply {
                action = MusicService.ACTION_PAUSE
            }
            requireContext().startService(intent)
        }

        binding.btnStop.setOnClickListener {
            val intent = Intent(requireContext(), MusicService::class.java).apply {
                action = MusicService.ACTION_STOP
            }
            requireContext().startService(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
