package com.example.neww4

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ConatctAdapter(private var cotacts:List<Contact>,context: Context) : RecyclerView.Adapter<ConatctAdapter.ContactViewHolder>() {

    private val db:ContactDatabaseHelper = ContactDatabaseHelper(context)
    class ContactViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val titleTextView :TextView= itemView.findViewById(R.id.titleTextView)
        val numberTextView :TextView= itemView.findViewById(R.id.numberTextView)
        val updateButton :ImageView= itemView.findViewById(R.id.updateButton)
        val deleteButton : ImageView = itemView.findViewById(R.id.deleteButton)
    }
    //////
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val  view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item,parent,false)
        return ContactViewHolder(view)
    }

    override fun getItemCount(): Int = cotacts.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = cotacts[position]
        holder.titleTextView.text=contact.name
        holder.numberTextView.text=contact.number

        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context,UpdateContactActivity::class.java).apply {
                putExtra("contact_id",contact.id)
            }
            holder.itemView.context.startActivity(intent)
        }
        //delete
        holder.deleteButton.setOnClickListener {
            showConfirmationDialog(contact.id,holder.itemView.context)
        }
    }

    fun refreshData(newContacts: List<Contact>){
        cotacts = newContacts
        notifyDataSetChanged()
    }

    private fun showConfirmationDialog(contactId:Int,context: Context){
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle("Confirmation")
        alertDialogBuilder.setMessage("Are you sure you want to delete this contact?")
        alertDialogBuilder.setPositiveButton("Yes"){ _, _ ->
            db.deleteContact(contactId)
            refreshData(db.getAllContacts())
            Toast.makeText(context,"Contact Deleted Successfully",Toast.LENGTH_SHORT).show()
        }
        alertDialogBuilder.setNegativeButton("No"){dialog, _ ->
            dialog.dismiss()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

}