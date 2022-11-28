package com.labedaj.smartalarm.ui

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.labedaj.smartalarm.R
import com.labedaj.smartalarm.databinding.FragmentAddAlarmBinding
import com.labedaj.smartalarm.databinding.WeekdayCardBinding
import com.labedaj.smartalarm.ui.viewmodels.AddAlarmViewModel
import com.labedaj.smartalarm.util.hideSoftKeyboard
import com.labedaj.smartalarm.util.showBasicMessageDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddAlarmFragment : Fragment() {

    private var _binding: FragmentAddAlarmBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<AddAlarmViewModel>()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddAlarmBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).supportActionBar?.hide()

        binding.viewModel = viewModel

        binding.addAlarmContainer.setOnClickListener {
            hideSoftKeyboard(requireContext(), it)
        }

        binding.saveAlarmButton.setOnClickListener {
            lifecycleScope.launch {
                val saved = viewModel.saveAlarm(binding.alarmTitle.text.toString())
                if (!saved) {
                    showBasicMessageDialog("Error saving alarm", requireActivity())
                } else {
                    it.findNavController().navigateUp()
                }
            }
        }

        binding.cancelButton.setOnClickListener {
            it.findNavController().navigateUp()
        }

        val alarmSelectListener = View.OnClickListener {
            val popupMenu = PopupMenu(requireContext(), it)
            popupMenu.menuInflater.inflate(R.menu.alram_types_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                viewModel.setAlarmSound(it.title.toString())
                true
            }
            popupMenu.show()
        }

        binding.selectAlarmButton.setOnClickListener(alarmSelectListener)
        binding.alarmSoundText.setOnClickListener(alarmSelectListener)

        binding.previewButton.setOnClickListener {
            if (viewModel.mediaPlaying.value == true) {
                viewModel.stopAlarmSound()
            } else {
                viewModel.playSelectedAlarmSound()
            }
        }

        binding.vibrationSwitch.setOnCheckedChangeListener { p0, p1 ->
            viewModel.addVibration.value = p1
            if (p1) {
                val vibrator =
                    requireActivity().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        200,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            }
        }

        binding.volumeSlider.value = 0.5F
        binding.volumeSlider.addOnChangeListener { _, value, _ ->
            viewModel.changeAlarmVolume(value)
        }

        setUp()

        binding.lifecycleOwner = this

        return binding.root
    }


    private fun setUp() {
        // Select the hour in Picker
        binding.hourPicker.minValue = 1
        binding.hourPicker.maxValue = 12
        binding.hourPicker.value = viewModel.hour.toInt()
        val hourRage = 1..12
        binding.hourPicker.displayedValues = (hourRage.map {
            it.toString().padStart(2, '0')
        }).toTypedArray()
        binding.hourPicker.setOnValueChangedListener { _, _, i ->
            viewModel.hour = i
        }

        // Select the minute in Picker
        binding.minutePicker.minValue = 0
        binding.minutePicker.maxValue = 59
        binding.minutePicker.value = viewModel.minute.toInt()
        val minRange = 0..59
        binding.minutePicker.displayedValues = (minRange.map {
            it.toString().padStart(2, '0')
        }).toTypedArray()
        binding.minutePicker.setOnValueChangedListener { _, _, i ->
            viewModel.minute = i
        }


        val days = arrayListOf("Mon", "Tues", "Wed", "Thurs", "Fri", "Sat", "Sun")
        days.forEach {
            val day = it
            val weekdayBinding =
                WeekdayCardBinding.inflate(layoutInflater, binding.weekdaysHolder, false)
            weekdayBinding.selected = false
            weekdayBinding.weekDay = it

            val layoutParams = LinearLayout.LayoutParams(
                0,
                (80 * requireContext().resources.displayMetrics.density).toInt(),
                1f
            )
            if (it != "Sun") {
                layoutParams.marginEnd = 10
            }

            weekdayBinding.button.setOnClickListener {
                weekdayBinding.selected = !weekdayBinding.selected!!
                if (weekdayBinding.selected == true) {
                    viewModel.addToSelectedDays(day)
                } else {
                    viewModel.removeFromSelectedDays(day)
                }
            }

            weekdayBinding.root.layoutParams = layoutParams
            binding.weekdaysHolder.addView(weekdayBinding.root)
        }
    }

}