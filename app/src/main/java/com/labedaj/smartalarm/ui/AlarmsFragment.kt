package com.labedaj.smartalarm.ui

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.labedaj.smartalarm.R
import com.labedaj.smartalarm.databinding.FragmentAlarmsBinding
import com.labedaj.smartalarm.ui.viewmodels.AlarmsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AlarmsFragment : Fragment() {


    private var _binding: FragmentAlarmsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<AlarmsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlarmsBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).supportActionBar?.hide()

        binding.viewModel = viewModel

        val adapter = AlarmAdapter()

        binding.alarmsRecyclerView.adapter = adapter

        viewModel.alarms.observe(viewLifecycleOwner) { alarms ->
            alarms?.apply {
                adapter.submitList(this)
            }
        }

        binding.addAlarmButton.setOnClickListener {
            it.findNavController()
                .navigate(AlarmsFragmentDirections.actionAlarmsFragmentToAddAlarmFragment())
        }

        binding.lifecycleOwner = this
        return binding.root
    }


}