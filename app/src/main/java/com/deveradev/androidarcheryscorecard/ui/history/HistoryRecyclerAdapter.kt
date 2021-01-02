package com.deveradev.androidarcheryscorecard.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deveradev.androidarcheryscorecard.databinding.HistoryRoundItemBinding
import com.deveradev.androidarcheryscorecard.data.HistoryViewModel
import com.deveradev.androidarcheryscorecard.data.Round
import com.deveradev.androidarcheryscorecard.data.RoundViewModel

class HistoryRecyclerAdapter(
    private val historyViewModel: HistoryViewModel,
    private val onItemClick: (Round) -> Unit
) : RecyclerView.Adapter<HistoryRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: HistoryRoundItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(round: Round) {
            this.binding.viewModel = RoundViewModel(round, historyViewModel.getApplication())
            this.binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HistoryRoundItemBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        this.historyViewModel.rounds.value?.get(position)?.let {
            holder.bind(it)
            holder.binding.root.setOnClickListener { _ ->
                this.onItemClick(it)
            }
        }
    }

    override fun getItemCount(): Int {
        return this.historyViewModel.rounds.value?.size ?: 0
    }

}
