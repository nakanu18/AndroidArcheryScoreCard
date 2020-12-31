package com.deveradev.androidarcheryscorecard.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.deveradev.androidarcheryscorecard.R
import com.deveradev.androidarcheryscorecard.data.HistoryViewModel
import com.deveradev.androidarcheryscorecard.data.HistoryViewModelFactory
import com.deveradev.androidarcheryscorecard.databinding.FragmentHistoryBinding
import com.deveradev.androidarcheryscorecard.ui.Utils

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Utils.log("HistoryFragment: onCreate")

        val viewModelFactory = HistoryViewModelFactory(requireActivity())

        this.binding = FragmentHistoryBinding.inflate(inflater, container, false)
        this.historyViewModel =
            ViewModelProvider(requireActivity(), viewModelFactory).get(HistoryViewModel::class.java)
        this.historyViewModel.rounds.observe(this.viewLifecycleOwner, Observer {
            Utils.log("HistoryFragment: rounds->observer")

            this.binding.roundsRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
            this.binding.roundsRecyclerView.adapter =
                HistoryRecyclerAdapter(this.historyViewModel) {
                    Utils.log("HistoryFragment: select round #${it.ID}")

                    // TODO: may want to create a new RoundViewModel here so we can discard later
                    this.historyViewModel.selectedRound.value = Utils.deepCopy(it)
                    findNavController().navigate(R.id.action_history_to_round_editor)
                }
        })
        return this.binding.root
    }

}