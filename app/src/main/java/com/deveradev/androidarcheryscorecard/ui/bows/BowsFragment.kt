package com.deveradev.androidarcheryscorecard.ui.bows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.deveradev.androidarcheryscorecard.R

class BowsFragment : Fragment() {

    private lateinit var bowsViewModel: BowsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_bows, container, false)
        val textView: TextView = root.findViewById(R.id.text_bows)

        this.bowsViewModel = ViewModelProvider(this).get(BowsViewModel::class.java)
        this.bowsViewModel.text.observe(this.viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}