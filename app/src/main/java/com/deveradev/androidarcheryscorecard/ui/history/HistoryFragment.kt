package com.deveradev.androidarcheryscorecard.ui.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.deveradev.androidarcheryscorecard.R
import com.deveradev.androidarcheryscorecard.data.HistoryViewModel
import com.deveradev.androidarcheryscorecard.data.HistoryViewModelFactory
import com.deveradev.androidarcheryscorecard.data.RoundViewModel
import com.deveradev.androidarcheryscorecard.ui.AED_LOG_TAG
import kotlinx.android.synthetic.main.fragment_history.*

class HistoryFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModelFactory = HistoryViewModelFactory(requireActivity())
        val root = inflater.inflate(R.layout.fragment_history, container, false)

        this.historyViewModel =
            ViewModelProvider(requireActivity(), viewModelFactory).get(HistoryViewModel::class.java)
        this.historyViewModel.rounds.observe(this.viewLifecycleOwner, Observer {
            records_recycler_view.layoutManager = LinearLayoutManager(requireActivity());
            records_recycler_view.adapter = HistoryRecyclerAdapter(this.historyViewModel) {
                Log.i(AED_LOG_TAG, it.score.toString())

                this.historyViewModel.selectedRound.value = it.round
                findNavController().navigate(R.id.action_history_to_round_editor)
            }
        })
        return root
    }

}