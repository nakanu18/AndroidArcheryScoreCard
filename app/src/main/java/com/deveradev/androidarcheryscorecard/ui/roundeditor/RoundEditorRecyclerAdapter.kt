package com.deveradev.androidarcheryscorecard.ui.roundeditor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deveradev.androidarcheryscorecard.data.HistoryViewModel
import com.deveradev.androidarcheryscorecard.data.RoundViewModel
import com.deveradev.androidarcheryscorecard.databinding.RoundEndItemBinding
import com.deveradev.androidarcheryscorecard.ui.LogUtils

class RoundEditorRecyclerAdapter(
    private val historyViewModel: HistoryViewModel
) : RecyclerView.Adapter<RoundEditorRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: RoundEndItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(round: RoundViewModel) {
            binding.viewModel = round
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoundEditorRecyclerAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RoundEndItemBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoundEditorRecyclerAdapter.ViewHolder, position: Int) {
        this.historyViewModel.selectedRound.value?.let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int {
        return this.historyViewModel.selectedRound.value?.round?.roundFormat?.numEnds ?: 0
    }

}