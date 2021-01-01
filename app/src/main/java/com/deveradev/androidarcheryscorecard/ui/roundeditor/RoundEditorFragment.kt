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
import com.deveradev.androidarcheryscorecard.ui.roundeditor.SaveRoundDialogFragment.SaveRoundDialogListener
import com.google.android.material.snackbar.Snackbar

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

        setUpViewModels()

        this.navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        setHasOptionsMenu(true)

        this.binding = FragmentRoundEditorBinding.inflate(inflater, container, false)
        return this.binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            val saveRoundDialog = SaveRoundDialogFragment(object : SaveRoundDialogListener {
                override fun onSave(dialog: SaveRoundDialogFragment) {
                    showRoundSavedSnackBar()

                    historyViewModel.saveSelectedRound()
                    dialog.dialog?.dismiss()
                    navController.navigateUp()
                }

                override fun onDiscard(dialog: SaveRoundDialogFragment) {
                    historyViewModel.discardSelectedRound()
                    dialog.dialog?.cancel()
                    navController.navigateUp()
                }
            })
            saveRoundDialog.show(requireActivity().supportFragmentManager, "save_round_dialog")
        }
        return super.onOptionsItemSelected(item)
    }

    // Private methods

    private fun setUpViewModels() {
        val viewModelFactory = HistoryViewModelFactory(requireActivity())

        this.historyViewModel =
            ViewModelProvider(requireActivity(), viewModelFactory).get(HistoryViewModel::class.java)
        this.historyViewModel.selectedRound.observe(this.viewLifecycleOwner, Observer {
            Utils.log("RoundEditorFragment: selectedRound->observer")

            this.binding.endsRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
            this.binding.endsRecyclerView.adapter =
                RoundEditorRecyclerAdapter(this.historyViewModel)
        })
    }

    private fun showRoundSavedSnackBar() {
        this.historyViewModel.selectedRound.value?.let {
            Snackbar.make(
                requireActivity().findViewById(android.R.id.content),
                "Round #${it.ID} saved",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

}