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
import com.deveradev.androidarcheryscorecard.ui.LogUtils
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
            records_recycler_view.layoutManager = LinearLayoutManager(requireActivity())
            records_recycler_view.adapter = HistoryRecyclerAdapter(this.historyViewModel) {
                LogUtils.log(it.score.toString())

                this.historyViewModel.selectedRound.value = it.round
                findNavController().navigate(R.id.action_history_to_round_editor)
            }
        })
        return root
    }

}