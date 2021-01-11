package com.deveradev.androidarcheryscorecard.ui.common

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class SaveDialogFragment(private val listener: SaveDialogListener) : DialogFragment() {

    interface SaveDialogListener {

        fun onSave(dialog: SaveDialogFragment)
        fun onDiscard(dialog: SaveDialogFragment)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it).apply {
                setMessage("Save data?")
                setPositiveButton("Save", DialogInterface.OnClickListener { _, _ ->
                    listener.onSave(this@SaveDialogFragment)
                })
                setNegativeButton("Discard", DialogInterface.OnClickListener { _, _ ->
                    listener.onDiscard(this@SaveDialogFragment)
                })
            }
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}