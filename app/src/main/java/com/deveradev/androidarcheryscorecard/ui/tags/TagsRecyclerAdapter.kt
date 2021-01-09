package com.deveradev.androidarcheryscorecard.ui.tags

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.deveradev.androidarcheryscorecard.data.Tag
import com.deveradev.androidarcheryscorecard.databinding.TagItemBinding
import com.deveradev.androidarcheryscorecard.ui.tageditor.TagViewModel
import com.deveradev.androidarcheryscorecard.utils.SwipeToDeleteCallback
import com.google.android.material.snackbar.Snackbar

class TagsRecyclerAdapter(
    private val fragmentActivity: FragmentActivity,
    private val tagsViewModel: TagsViewModel,
    private val onItemClick: (Tag) -> Unit
) : RecyclerView.Adapter<TagsRecyclerAdapter.ViewHolder>(),
    SwipeToDeleteCallback.DeleteItemHandler {

    inner class ViewHolder(val binding: TagItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(tag: Tag) {
            this.binding.viewModel = TagViewModel(tag, tagsViewModel.getApplication())
            this.binding.executePendingBindings()
        }

    }

    // RecyclerView.Adapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TagItemBinding.inflate(layoutInflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        this.tagsViewModel.tags.value?.get(position)?.let {
            holder.bind(it)
            holder.binding.root.setOnClickListener { _ ->
                this.onItemClick(it)
            }
        }
    }

    override fun getItemCount(): Int {
        return this.tagsViewModel.tags.value?.size ?: 0
    }

    // SwipeToDeleteCallback.DeleteItemHandler

    override fun deleteItem(viewHolder: RecyclerView.ViewHolder, position: Int) {
        this.tagsViewModel.deleteTag(position)

        Snackbar.make(
            this.fragmentActivity.findViewById(android.R.id.content),
            "Undo Delete?",
            Snackbar.LENGTH_SHORT
        ).setAction("Undo") {
            this.tagsViewModel.undoDeletedTag()
        }.show()
    }

}