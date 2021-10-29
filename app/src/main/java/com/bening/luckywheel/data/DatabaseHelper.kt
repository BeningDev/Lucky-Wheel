package com.bening.luckywheel.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DatabaseHelper.DB_NAME, null, DatabaseHelper.DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE_WHEEL = "CREATE TABLE 'Wheel' (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT)"
        val CREATE_TABLE_ITEM = "CREATE TABLE 'Item' (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT, WheelID INTEGER)"
        db.execSQL(CREATE_TABLE_WHEEL)
        db.execSQL(CREATE_TABLE_ITEM)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE_WHEEL = "DROP TABLE IF EXISTS 'Wheel'"
        val DROP_TABLE_ITEM = "DROP TABLE IF EXISTS 'Item'"
        db.execSQL(DROP_TABLE_WHEEL)
        db.execSQL(DROP_TABLE_ITEM)
        onCreate(db)
    }

    fun updateWheel(WheelID: Int, name: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("NAME", name)
        db.update("Wheel", contentValues, "ID='" + WheelID + "'", null)
    }

    fun getWheels(): Cursor {
        val db = this.writableDatabase
        val res = db.rawQuery("SELECT * FROM Wheel", null)
        return res
    }

    fun getWheelItems(id: String): Cursor {
        val db = this.writableDatabase
        val res = db.rawQuery("SELECT * FROM Item WHERE WheelID='" + id + "'", null)
        return res
    }

    fun addWheel(name: String, items: ArrayList<DataItem>) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("NAME", name)
        db.insert("Wheel", null, contentValues)
        val WheelID = this.getWheelID(name)
        items.forEach {
            addItem(WheelID, it.nama)
        }
    }

    fun getWheelID(name: String): Int {
        val db = this.writableDatabase
        val res = db.rawQuery("SELECT ID FROM Wheel WHERE NAME='" + name + "'", null)
        res.moveToFirst()
        return res.getString(0).toInt()
    }

    fun delWheel(ID: Int) {
        val db = this.writableDatabase
        db.delete("Wheel", "ID='" + ID + "'", null)
        db.delete("Item", "WheelID='" + ID + "'", null)
    }

    fun addItem(WheelID: Int, name: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("NAME", name)
        contentValues.put("WheelID", WheelID)
        db.insert("Item", null, contentValues)
    }

    fun delAllItem(WheelID: Int) {
        val db = this.writableDatabase
        db.delete("Item", "WheelID='" + WheelID + "'", null)
    }

    fun delItem(WheelID: Int, ItemID: Int) {
        val db = this.writableDatabase
        db.delete("Item", "WheelID='" + WheelID + "' AND ID='" + ItemID + "'", null)
    }

    companion object {

        private val DB_VERSION = 1
        private val DB_NAME = "LuckyWheel"
    }
}