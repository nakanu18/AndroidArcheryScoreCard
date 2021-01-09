package com.deveradev.androidarcheryscorecard.ui.tags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.deveradev.androidarcheryscorecard.R
import com.deveradev.androidarcheryscorecard.databinding.FragmentTagsBinding
import com.deveradev.androidarcheryscorecard.utils.SwipeToDeleteCallback
import com.deveradev.androidarcheryscorecard.utils.Utils

class TagsFragment : Fragment() {

    private lateinit var binding: FragmentTagsBinding
    private lateinit var tagsViewModel: TagsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Utils.log("TagsFragment: onCreate")

        this.binding = FragmentTagsBinding.inflate(inflater, container, false)

        setUpViewModels()

        return this.binding.root
    }

    // Private methods

    private fun setUpViewModels() {
        this.tagsViewModel = ViewModelProvider(requireActivity()).get(TagsViewModel::class.java)
        this.tagsViewModel.tags.observe(this.viewLifecycleOwner, Observer {
            Utils.log("TagsFragment: tags -> data changed")

            val adapter = TagsRecyclerAdapter(requireActivity(), this.tagsViewModel) {
                Utils.log("TagsFragment: goto tag #${it.ID}")
            }

            this.binding.tagsRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
            this.binding.tagsRecyclerView.adapter = adapter

            ItemTouchHelper(SwipeToDeleteCallback(adapter))
                .attachToRecyclerView(this.binding.tagsRecyclerView)
        })
    }

}