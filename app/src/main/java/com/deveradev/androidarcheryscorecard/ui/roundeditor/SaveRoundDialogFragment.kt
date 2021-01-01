package com.deveradev.androidarcheryscorecard.ui.roundeditor

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class SaveRoundDialogFragment(private val listener: SaveRoundDialogListener) : DialogFragment() {

    interface SaveRoundDialogListener {

        fun onSave(dialog: SaveRoundDialogFragment)
        fun onDiscard(dialog: SaveRoundDialogFragment)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it).apply {
                setMessage("Save data?")
                setPositiveButton("Save", DialogInterface.OnClickListener { _, _ ->
                    listener.onSave(this@SaveRoundDialogFragment)
                })
                setNegativeButton("Discard", DialogInterface.OnClickListener { _, _ ->
                    listener.onDiscard(this@SaveRoundDialogFragment)
                })
            }
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}