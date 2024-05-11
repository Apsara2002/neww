package com.example.neww4

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ConatctAdapter(private var cotacts:List<Contact>,context: Context) : RecyclerView.Adapter<ConatctAdapter.ContactViewHolder>() {

    class ContactViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val titleTextView :TextView= itemView.findViewById(R.id.titleTextView)
        val numberTextView :TextView= itemView.findViewById(R.id.numberTextView)
    }
    ////
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val  view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item,parent,false)
        return ContactViewHolder(view)
    }

    override fun getItemCount(): Int = cotacts.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = cotacts[position]
        holder.titleTextView.text=contact.name
        holder.numberTextView.text=contact.number
    }

    fun refreshData(newContacts: List<Contact>){
        cotacts = newContacts
        notifyDataSetChanged()
    }

}