package com.deveradev.androidarcheryscorecard.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deveradev.androidarcheryscorecard.databinding.HistoryRecordItemBinding
import com.deveradev.androidarcheryscorecard.data.HistoryViewModel
import com.deveradev.androidarcheryscorecard.data.RoundViewModel

class HistoryRecyclerAdapter(
    private val viewModel: HistoryViewModel,
    private val onItemClick: (RoundViewModel) -> Unit
) : RecyclerView.Adapter<HistoryRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: HistoryRecordItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(record: RoundViewModel) {
            binding.viewModel = record
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HistoryRecordItemBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        this.viewModel.rounds.value?.get(position)?.let {
            holder.bind(it)
            holder.binding.root.setOnClickListener { _ ->
                this.onItemClick(it)
            }
        }
    }

    override fun getItemCount(): Int {
        return this.viewModel.rounds.value?.size ?: 0
    }

}
