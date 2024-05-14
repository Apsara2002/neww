package com.example.neww4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.neww4.databinding.ActivityUpdateContactBinding

class UpdateContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateContactBinding
    private lateinit var db:ContactDatabaseHelper
    private var contactId: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = ContactDatabaseHelper(this)

        contactId=intent.getIntExtra("contact_id",-1)
        if (contactId == -1){
            finish()
            return
        }

        //get id of contact
        val contact = db.getConatctByID(contactId)
        binding.UpdateTitleEditText.setText(contact.name)
        binding.UpdateNumberEditText.setText(contact.number)

        binding.UpdateSaveButton.setOnClickListener {
            val newContact = binding.UpdateTitleEditText.text.toString()
            val newNumber =binding.UpdateNumberEditText.text.toString()
            val updatedContact = Contact(contactId,newContact,newNumber)
            db.updateContact(updatedContact)
            finish()
            Toast.makeText(this,"Changes Saved",Toast.LENGTH_SHORT).show()
        }
        binding.back.setOnClickListener {
            onBackPressed()
        }

    }
}