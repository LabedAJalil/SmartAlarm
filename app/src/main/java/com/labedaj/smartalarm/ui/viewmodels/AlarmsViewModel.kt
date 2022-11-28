package com.labedaj.smartalarm.ui.viewmodels

import androidx.lifecycle.*
import com.labedaj.smartalarm.data.AlarmRepository
import com.labedaj.smartalarm.data.models.Alarm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AlarmsViewModel @Inject constructor(
    private val repository: AlarmRepository
) : ViewModel() {

    private val _alarms = MutableLiveData<List<Alarm>>()
    val alarms: LiveData<List<Alarm>> = _alarms

    init {
        loadAlarms()
    }

    private fun loadAlarms() {
        viewModelScope.launch {
            repository.getAllAlarms().collect{ listOfAlarms ->
                _alarms.value = listOfAlarms
           }
        }
    }


}