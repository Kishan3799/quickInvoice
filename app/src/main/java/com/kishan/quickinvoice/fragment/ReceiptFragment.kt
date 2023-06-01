package com.kishan.quickinvoice.fragment

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.kishan.quickinvoice.R
import com.kishan.quickinvoice.databinding.FragmentReceiptBinding
import kotlin.concurrent.fixedRateTimer

class ReceiptFragment:Fragment(R.layout.fragment_receipt) {
    private lateinit var binding : FragmentReceiptBinding
    private lateinit var receiptNumber:String
    private lateinit var receiptDate:String
    private lateinit var dialog: Dialog
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReceiptBinding.inflate(layoutInflater)
        database = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()

        receiptNumber = "000"
        receiptDate= "00/00/00"
        //set receipt  Number
        // in Fragment always use requireContext()
        dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.receipt_no_enter_dialog)
        dialog.window?.setBackgroundDrawableResource(R.drawable.receipt_no_enter_bg)

        val etNumber = dialog.findViewById<EditText>(R.id.etReceiptNo)
        val doneBtn = dialog.findViewById<Button>(R.id.done_btn)

        binding.invoiceNoBtn.setOnClickListener {
            dialog.show()
        }
        doneBtn.setOnClickListener {
            receiptNumber = etNumber.text.toString()
            binding.tvReceiptNumber.text = receiptNumber
            dialog.dismiss()
        }

        //set receipt date
        val dateBuilder: MaterialDatePicker.Builder<*> = MaterialDatePicker.Builder.datePicker()
        dateBuilder.setTitleText("Select Receipt Date")

        val currentDatePicker = dateBuilder.build()

        binding.invoiceDateButton.setOnClickListener {
            currentDatePicker.show(childFragmentManager, "MATERIAL_DATE_PICKER")
        }

        currentDatePicker.addOnPositiveButtonClickListener {
            binding.tvReceiptDateSet.text = currentDatePicker.headerText
        }

        //set business detail
        binding.businessDetailCard.setOnClickListener {

        }

         database.child("business").child(auth.currentUser!!.uid).child("businessName")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        binding.tvBusinessInfo.text = snapshot.child("businessName").toString()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })



        return binding.root
    }
}