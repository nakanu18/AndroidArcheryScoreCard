package com.deveradev.androidarcheryscorecard.ui.roundeditor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.deveradev.androidarcheryscorecard.R
import com.deveradev.androidarcheryscorecard.data.HistoryViewModel
import com.deveradev.androidarcheryscorecard.data.HistoryViewModelFactory

class RoundEditorFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModelFactory = HistoryViewModelFactory(requireActivity())

        this.historyViewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(HistoryViewModel::class.java)
        this.historyViewModel.rounds.observe(this.viewLifecycleOwner, Observer {

        })

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_round_editor, container, false)
    }

}