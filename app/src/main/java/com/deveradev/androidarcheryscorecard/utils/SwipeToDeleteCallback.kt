package com.deveradev.androidarcheryscorecard.utils

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SwipeToDeleteCallback(private val recyclerViewAdapter: DeleteItemHandler) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    interface DeleteItemHandler {

        fun deleteItem(viewHolder: RecyclerView.ViewHolder, position: Int)

    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        this.recyclerViewAdapter.deleteItem(viewHolder, position)
    }

}