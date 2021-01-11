package com.deveradev.androidarcheryscorecard.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.deveradev.androidarcheryscorecard.R
import com.deveradev.androidarcheryscorecard.databinding.FragmentHistoryBinding
import com.deveradev.androidarcheryscorecard.utils.SwipeToDeleteCallback
import com.deveradev.androidarcheryscorecard.utils.Utils

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Utils.log("HistoryFragment: onCreate")

        this.binding = FragmentHistoryBinding.inflate(inflater, container, false)

        setUpViewModels()
        setUpButtonListeners()

        return this.binding.root
    }

    // Private methods

    private fun setUpViewModels() {
        this.historyViewModel =
            ViewModelProvider(requireActivity()).get(HistoryViewModel::class.java)
        this.historyViewModel.rounds.observe(this.viewLifecycleOwner, Observer {
            Utils.log("HistoryFragment: rounds -> data changed")

            val adapter = HistoryRecyclerAdapter(requireActivity(), this.historyViewModel) {
                Utils.log("HistoryFragment: goto round #${it.ID}")

                this.historyViewModel.copyRoundForEditing(it)
                findNavController().navigate(R.id.action_history_to_round_editor)
            }

            this.binding.roundsRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
            this.binding.roundsRecyclerView.adapter = adapter
            this.binding.roundsRecyclerView.addItemDecoration(DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL))

            ItemTouchHelper(SwipeToDeleteCallback(adapter))
                .attachToRecyclerView(this.binding.roundsRecyclerView)
        })
    }

    private fun setUpButtonListeners() {
        this.binding.buttonFabNewRound.setOnClickListener {
            this.historyViewModel.createNewRoundForEditing()
            findNavController().navigate(R.id.action_history_to_round_editor)
        }
    }

}