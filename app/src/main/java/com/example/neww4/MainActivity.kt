package com.example.neww4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neww4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityMainBinding
    private lateinit var db : ContactDatabaseHelper
    private  lateinit var contactAdapter: ConatctAdapter
    private var contactList: MutableList<Contact> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = ContactDatabaseHelper(this)
        contactAdapter = ConatctAdapter(db.getAllContacts(),this)

        binding.contactsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.contactsRecyclerView.adapter=contactAdapter

        binding.addButton.setOnClickListener {
            val intent =Intent(this,AddContactActivity::class.java)
            startActivity(intent)
        }

        ///////////////////////
        binding.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { searchQuery ->
                    val filteredList = contactList.filter { contact ->
                        contact.name.contains(searchQuery, ignoreCase = true)
                    }
                    contactAdapter.refreshData(filteredList.toMutableList())
                }
                return true
            }
        })
    }

    override fun onResume() {
        super.onResume()

        contactList.clear()
        contactList.addAll(db.getAllContacts())
        contactAdapter.refreshData(db.getAllContacts())
    }
}