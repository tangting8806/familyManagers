package com.t.familymanagers.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DataBaseHelper : SQLiteOpenHelper {
    companion object {
        private const val DATABASE_NAME = "food_database.db"
        private const val DATABASE_VERSION = 4
         const val TABLE_NAME = "food_table"
         const val COLUMN_ID = "id"
         const val COLUMN_NAME = "name"
         const val COLUMN_NUMBER = "number"
         const val COLUMN_PRODUCTION_DATE = "production_date"
         const val COLUMN_SHELF_LIVE = "shelf_live"
         const val COLUMN_IF_OPEN = "if_open"
         const val COLUMN_OPEN_DATE = "open_date"
         const val COLUMN_IF_NEED_ADD = "if_need_add"
         const val COLUMN_KIND = "kind"
    }

    constructor(context: Context) : super(context, DATABASE_NAME, null, DATABASE_VERSION)

    override fun onCreate(database: SQLiteDatabase) {
        Log.d("DataBaseHelper","onCreate")
        val sql = (("CREATE TABLE  "
                + TABLE_NAME) + " ( "
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT,"
                + COLUMN_NUMBER + " INTEGER,"
                + COLUMN_PRODUCTION_DATE + " TEXT,"
                + COLUMN_SHELF_LIVE + " TEXT,"
                + COLUMN_IF_OPEN + " TEXT,"
                + COLUMN_OPEN_DATE + " TEXT,"
                + COLUMN_KIND + " TEXT,"
                + COLUMN_IF_NEED_ADD + " TEXT)")
        database.execSQL(sql)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

}