package com.deveradev.androidarcheryscorecard.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deveradev.androidarcheryscorecard.databinding.HistoryRecordItemBinding
import com.deveradev.androidarcheryscorecard.data.HistoryViewModel
import com.deveradev.androidarcheryscorecard.data.RecordViewModel

class HistoryRecyclerAdapter(
    private val viewModel: HistoryViewModel
) : RecyclerView.Adapter<HistoryRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: HistoryRecordItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(record: RecordViewModel) {
            binding.recordViewModel = record
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HistoryRecordItemBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        viewModel.records.value?.get(position)?.let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int {
        return viewModel.records.value?.size ?: 0
    }

}
