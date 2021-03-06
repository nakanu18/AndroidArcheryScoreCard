package com.deveradev.androidarcheryscorecard.ui.roundeditor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deveradev.androidarcheryscorecard.ui.history.HistoryViewModel
import com.deveradev.androidarcheryscorecard.data.Round
import com.deveradev.androidarcheryscorecard.databinding.RoundEndItemBinding

class RoundEditorRecyclerAdapter(
    private val historyViewModel: HistoryViewModel,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<RoundEditorRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: RoundEndItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(round: Round, index: Int) {
            val viewModel = RoundViewModel(round, historyViewModel.getApplication())
            this.binding.viewModel = viewModel
            this.binding.index = index
            this.binding.isSelectedEnd = historyViewModel.selectedEnd.value == index
            this.binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoundEditorRecyclerAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RoundEndItemBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoundEditorRecyclerAdapter.ViewHolder, index: Int) {
        this.historyViewModel.selectedRound.value?.let {
            holder.bind(it, index)
        }
        holder.binding.root.setOnClickListener {
            this.onItemClick(index)
        }
    }

    override fun getItemCount(): Int {
        return this.historyViewModel.selectedRound.value?.roundFormat?.numEnds ?: 0
    }

}