package com.deveradev.androidarcheryscorecard.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.deveradev.androidarcheryscorecard.R
import com.deveradev.androidarcheryscorecard.data.HistoryViewModel
import com.deveradev.androidarcheryscorecard.data.HistoryViewModelFactory
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

        historyViewModel =
            ViewModelProvider(requireActivity(), viewModelFactory).get(HistoryViewModel::class.java)
        historyViewModel.rounds.observe(viewLifecycleOwner, Observer {
            records_recycler_view.layoutManager = LinearLayoutManager(requireActivity());
            records_recycler_view.adapter = HistoryRecyclerAdapter(historyViewModel)
        })
        return root
    }

}