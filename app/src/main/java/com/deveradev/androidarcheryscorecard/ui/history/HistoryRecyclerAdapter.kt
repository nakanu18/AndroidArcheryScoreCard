package com.deveradev.androidarcheryscorecard.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.deveradev.androidarcheryscorecard.data.HistoryViewModel
import com.deveradev.androidarcheryscorecard.data.Round
import com.deveradev.androidarcheryscorecard.data.RoundViewModel
import com.deveradev.androidarcheryscorecard.databinding.HistoryRoundItemBinding
import com.deveradev.androidarcheryscorecard.utils.SwipeToDeleteCallback
import com.deveradev.androidarcheryscorecard.utils.mutation
import com.google.android.material.snackbar.Snackbar

class HistoryRecyclerAdapter(
    private val fragmentActivity: FragmentActivity,
    private val historyViewModel: HistoryViewModel,
    private val onItemClick: (Round) -> Unit
) : RecyclerView.Adapter<HistoryRecyclerAdapter.ViewHolder>(),
    SwipeToDeleteCallback.DeleteItemHandler {

    inner class ViewHolder(val binding: HistoryRoundItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(round: Round) {
            this.binding.viewModel = RoundViewModel(round, historyViewModel.getApplication())
            this.binding.executePendingBindings()
        }

    }

    // RecyclerView.Adapter

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

    // SwipeToDeleteCallback.DeleteItemHandler

    override fun deleteItem(viewHolder: RecyclerView.ViewHolder, position: Int) {
        this.historyViewModel.deleteRound(position)

        Snackbar.make(
            this.fragmentActivity.findViewById(android.R.id.content),
            "Undo Delete?",
            Snackbar.LENGTH_SHORT
        ).setAction("Undo") {
            this.historyViewModel.undoDeletedRound()
        }.show()
    }

}
