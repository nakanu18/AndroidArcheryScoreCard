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
import com.deveradev.androidarcheryscorecard.ui.history.HistoryViewModel
import com.deveradev.androidarcheryscorecard.databinding.FragmentRoundEditorBinding
import com.deveradev.androidarcheryscorecard.ui.common.SaveDialogFragment
import com.deveradev.androidarcheryscorecard.ui.common.SaveDialogFragment.SaveDialogListener
import com.deveradev.androidarcheryscorecard.utils.Utils
import com.google.android.material.snackbar.Snackbar

class RoundEditorFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentRoundEditorBinding
    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var navController: NavController
    private var recyclerAdapter: RoundEditorRecyclerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Utils.log("RoundEditorFragment: onCreate")

        this.binding = FragmentRoundEditorBinding.inflate(inflater, container, false)

        setUpViewModels()
        setUpControlPanelButtonListeners()

        this.navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        setHasOptionsMenu(true)

        return this.binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (this.historyViewModel.isSelectedRoundEdited && item.itemId == android.R.id.home) {
            val saveRoundDialog = SaveDialogFragment(object : SaveDialogListener {
                override fun onSave(dialog: SaveDialogFragment) {
                    Utils.log("RoundEditorFragment: save round")
                    showRoundSavedSnackBar()

                    historyViewModel.saveSelectedRound()
                    dialog.dialog?.dismiss()
                    navController.navigateUp()
                }

                override fun onDiscard(dialog: SaveDialogFragment) {
                    Utils.log("RoundEditorFragment: discard round")
                    historyViewModel.discardSelectedRound()
                    dialog.dialog?.cancel()
                    navController.navigateUp()
                }
            })
            saveRoundDialog.show(requireActivity().supportFragmentManager, "save_round_dialog")
        } else {
            this.navController.navigateUp()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(button: View?) {
        when (button?.id) {
            R.id.button_score_x -> updateCurrentArrow(11)
            R.id.button_score_10 -> updateCurrentArrow(10)
            R.id.button_score_9 -> updateCurrentArrow(9)
            R.id.button_score_8 -> updateCurrentArrow(8)
            R.id.button_score_7 -> updateCurrentArrow(7)
            R.id.button_score_6 -> updateCurrentArrow(6)
            R.id.button_score_5 -> updateCurrentArrow(5)
            R.id.button_score_4 -> updateCurrentArrow(4)
            R.id.button_score_3 -> updateCurrentArrow(3)
            R.id.button_score_2 -> updateCurrentArrow(2)
            R.id.button_score_1 -> updateCurrentArrow(1)
            R.id.button_score_m -> updateCurrentArrow(0)
            R.id.button_score_erase -> updateCurrentArrow(-1)
        }
    }

    // Private methods

    private fun setUpViewModels() {
        this.historyViewModel =
            ViewModelProvider(requireActivity()).get(HistoryViewModel::class.java)
        this.historyViewModel.selectedRound.observe(this.viewLifecycleOwner, Observer {
            Utils.log("RoundEditorFragment: selectedRound -> data changed")

            setUpRecyclerAdapter()
        })
        this.historyViewModel.selectedEnd.observe(this.viewLifecycleOwner, Observer {
            Utils.log("RoundEditorFragment: selectedEnd -> data changed")

            setUpRecyclerAdapter()
        })
    }

    // TODO: this is the fix for adapter resetting to top when selecting ends.  noticing some jumping
    // but its intermittent.
    private fun setUpRecyclerAdapter() {
        if (this.recyclerAdapter == null) {
            this.recyclerAdapter =
                RoundEditorRecyclerAdapter(this.historyViewModel) { selectedEndID ->
                    historyViewModel.selectEndCapped(selectedEndID)
                }
        }

        if (this.binding.endsRecyclerView.adapter == null) {
            this.binding.endsRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
            this.binding.endsRecyclerView.adapter = this.recyclerAdapter
        } else {
            this.recyclerAdapter?.notifyDataSetChanged()
        }
    }

    private fun setUpControlPanelButtonListeners() {
        this.binding.buttonScoreX.setOnClickListener(this)
        this.binding.buttonScore10.setOnClickListener(this)
        this.binding.buttonScore9.setOnClickListener(this)
        this.binding.buttonScore8.setOnClickListener(this)
        this.binding.buttonScore7.setOnClickListener(this)
        this.binding.buttonScore6.setOnClickListener(this)
        this.binding.buttonScore5.setOnClickListener(this)
        this.binding.buttonScore4.setOnClickListener(this)
        this.binding.buttonScore3.setOnClickListener(this)
        this.binding.buttonScore2.setOnClickListener(this)
        this.binding.buttonScore1.setOnClickListener(this)
        this.binding.buttonScoreM.setOnClickListener(this)
        this.binding.buttonScoreErase.setOnClickListener(this)
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

    private fun updateCurrentArrow(arrowScore: Int) {
        this.historyViewModel.updateCurrentArrowForSelectedRound(arrowScore)
    }

}