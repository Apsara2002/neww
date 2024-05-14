package com.example.neww4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.neww4.databinding.ActivityAddContactBinding

class AddContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddContactBinding
    private lateinit var db :ContactDatabaseHelper

    private fun isPhoneNumberValid(phoneNumber:String): Boolean{
        return phoneNumber.length == 10 && phoneNumber .all { it.isDigit() }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = ContactDatabaseHelper(this)

        binding.saveButton.setOnClickListener {
            val name =binding.titleEditText.text.toString()
            val phnumber = binding.numberEditText.text.toString()
            if (isPhoneNumberValid(phnumber)){
                val contact = Contact(0,name,phnumber)
                db.insertContact(contact)
                finish()
                Toast.makeText(this,"Contact save successfully",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"Please enter a valid 10 digit phone number",Toast.LENGTH_SHORT).show()
            }
        }

        binding.back.setOnClickListener {
            onBackPressed()
        }
    }
}