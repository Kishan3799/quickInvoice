package com.kishan.quickinvoice.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kishan.quickinvoice.R
import com.kishan.quickinvoice.databinding.FragmentInvoiceBinding

class InvoiceFragment: Fragment(R.layout.fragment_invoice) {
    private lateinit var binding: FragmentInvoiceBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInvoiceBinding.inflate(layoutInflater)

        return binding.root
    }
}