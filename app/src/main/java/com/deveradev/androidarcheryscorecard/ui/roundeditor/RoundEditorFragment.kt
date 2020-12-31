package com.deveradev.androidarcheryscorecard.ui.roundeditor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.deveradev.androidarcheryscorecard.R
import com.deveradev.androidarcheryscorecard.data.HistoryViewModel
import com.deveradev.androidarcheryscorecard.data.HistoryViewModelFactory
import com.deveradev.androidarcheryscorecard.databinding.FragmentRoundEditorBinding
import com.deveradev.androidarcheryscorecard.ui.Utils

class RoundEditorFragment : Fragment() {

    private lateinit var binding: FragmentRoundEditorBinding
    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Utils.log("RoundEditorFragment: onCreate")

        val viewModelFactory = HistoryViewModelFactory(requireActivity())

        this.binding = FragmentRoundEditorBinding.inflate(inflater, container, false)
        this.historyViewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(HistoryViewModel::class.java)
        this.historyViewModel.selectedRound.observe(this.viewLifecycleOwner, Observer {
            Utils.log("RoundEditorFragment: selectedRound->observer")

            this.binding.endsRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
            this.binding.endsRecyclerView.adapter = RoundEditorRecyclerAdapter(this.historyViewModel)
        })

        this.navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        setHasOptionsMenu(true)

        return this.binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            this.navController.navigateUp()
        }
        return super.onOptionsItemSelected(item)
    }

}