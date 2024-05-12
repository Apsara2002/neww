package com.example.neww4

import android.app.DownloadManager.Query
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ContactDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME="contactappp.db"
        private const val DATABASE_VERSION=1
        private const val TABLE_NAME="allconatct"
        private const val COLUMN_ID="id"
        private const val COLUMN_NAME="name"
        private const val COLUMN_PHONE="number"


    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY ,$COLUMN_NAME TEXT , $COLUMN_PHONE TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertContact(contact:Contact){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME,contact.name)
            put(COLUMN_PHONE,contact.number)
        }
        db.insert(TABLE_NAME,null,values)
        db.close()
    }

    fun getAllContacts(): List<Contact>{
        val contactsList = mutableListOf<Contact>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query,null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE))

            val contact = Contact(id,name,phone)
            contactsList.add(contact)
        }
        cursor.close()
        db.close()
        return contactsList
    }

    fun updateContact(contact: Contact){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME,contact.name)
            put(COLUMN_PHONE,contact.number)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(contact.id.toString())
        db.update(TABLE_NAME,values,whereClause,whereArgs)
        db.close()
    }


    fun getConatctByID(contactId: Int): Contact{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $contactId"
        val cursor = db.rawQuery(query,null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
        val phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE))

        cursor.close()
        db.close()
        return Contact(id,name,phone)
    }

    fun deleteContact(contactId: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(contactId.toString())
        db.delete(TABLE_NAME,whereClause,whereArgs)
        db.close()
    }

}