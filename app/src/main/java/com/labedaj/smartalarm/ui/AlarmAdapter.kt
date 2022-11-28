package com.labedaj.smartalarm.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.labedaj.smartalarm.data.models.Alarm
import com.labedaj.smartalarm.databinding.RawAlarmBinding

class AlarmAdapter :
    ListAdapter<Alarm, AlarmAdapter.AlarmViewHolder>(AlarmDiffCallback()) {

    var onItemClick: ((Alarm) -> Unit)? = null
    var onItemLongClick: ((Alarm) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val binding =
            RawAlarmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlarmViewHolder(binding, onItemClick, onItemLongClick)
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        val alarm = getItem(position)
        holder.bind(alarm)
    }

    class AlarmViewHolder(
        private val binding: RawAlarmBinding,
        private val onItemClick: ((Alarm) -> Unit)?,
        private val onItemLongClick: ((Alarm) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(alarm: Alarm) {
            /*
                binding.alarm = alarm
                binding.disableSwitch.isChecked = !alarm.disabled
                binding.disableSwitch.setOnCheckedChangeListener { switch, b ->
                    binding.alarm?.let {
                        it.disabled = !b
                        (switch as SwitchMaterial).apply {
                            if (it.disabled) {
                                trackDrawable.setTint(context.getColor(R.color.lightGrey))
                            } else {
                                trackDrawable.setTint(context.getColor(R.color.primaryColorLight))
                            }
                        }
                    }
                    runBlocking { alarmDao.update(requireNotNull(binding.alarm)) }
                }
                binding.deleteIcon.setOnClickListener {
                    runBlocking { alarmDao.delete(alarm) }
                }
            */


        }
    }


}


class AlarmDiffCallback : DiffUtil.ItemCallback<Alarm>() {
    override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
        return oldItem == newItem
    }
}