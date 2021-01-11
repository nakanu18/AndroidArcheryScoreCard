package com.deveradev.androidarcheryscorecard.ui.tageditor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.deveradev.androidarcheryscorecard.R
import com.deveradev.androidarcheryscorecard.databinding.FragmentTagEditorBinding
import com.deveradev.androidarcheryscorecard.ui.common.SaveDialogFragment
import com.deveradev.androidarcheryscorecard.ui.tags.TagsViewModel
import com.deveradev.androidarcheryscorecard.utils.Utils
import com.deveradev.androidarcheryscorecard.utils.hideKeyboard
import com.google.android.material.snackbar.Snackbar

class TagEditorFragment : Fragment() {

    private lateinit var binding: FragmentTagEditorBinding
    private lateinit var tagsViewModel: TagsViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Utils.log("TagEditorFragment: onCreate")

        this.binding = FragmentTagEditorBinding.inflate(inflater, container, false)

        setUpViewModels()
        setUpListeners()

        this.navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        setHasOptionsMenu(true)

        return this.binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (this.tagsViewModel.isSelectedTagEdited && item.itemId == android.R.id.home) {
            val saveTagDialog = SaveDialogFragment(object : SaveDialogFragment.SaveDialogListener {
                override fun onSave(dialog: SaveDialogFragment) {
                    Utils.log("TagEditorFragment: save tag")
                    showTagSavedSnackbar()

                    tagsViewModel.saveSelectedRound()
                    dialog.dialog?.cancel()
                    navController.navigateUp()
                    hideKeyboard()
                }

                override fun onDiscard(dialog: SaveDialogFragment) {
                    Utils.log("TagEditorFragment: discard tag")
                    tagsViewModel.discardSelectedTag()
                    dialog.dialog?.cancel()
                    navController.navigateUp()
                    hideKeyboard()
                }
            })
            saveTagDialog.show(requireActivity().supportFragmentManager, "save_tag_dialog")
        } else {
            this.navController.navigateUp()
            hideKeyboard()
        }
        return super.onOptionsItemSelected(item)
    }

    // Private methods

    private fun setUpViewModels() {
        this.tagsViewModel = ViewModelProvider(requireActivity()).get(TagsViewModel::class.java)
        this.tagsViewModel.selectedTag.value?.let {
            binding.viewModel = TagViewModel(it, tagsViewModel.getApplication())
        }
    }

    private fun setUpListeners() {
        this.binding.textInputTagTitle.editText?.addTextChangedListener {
            tagsViewModel.updateTagNameForSelectedTag(it.toString())
        }
        this.binding.textInputTagNotes.editText?.addTextChangedListener {
            tagsViewModel.updateTagNotesForSelectedTag(it.toString())
        }
    }

    private fun showTagSavedSnackbar() {
        this.tagsViewModel.selectedTag.value?.let {
            Snackbar.make(
                requireActivity().findViewById(android.R.id.content),
                "Tag #${it.ID} saved",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

}
