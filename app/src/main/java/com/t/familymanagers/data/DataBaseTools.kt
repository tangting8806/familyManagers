package com.t.familymanagers.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.t.familymanagers.data.DataBaseHelper.Companion.COLUMN_ID
import com.t.familymanagers.data.DataBaseHelper.Companion.COLUMN_IF_NEED_ADD
import com.t.familymanagers.data.DataBaseHelper.Companion.COLUMN_IF_OPEN
import com.t.familymanagers.data.DataBaseHelper.Companion.COLUMN_KIND
import com.t.familymanagers.data.DataBaseHelper.Companion.COLUMN_NAME
import com.t.familymanagers.data.DataBaseHelper.Companion.COLUMN_NUMBER
import com.t.familymanagers.data.DataBaseHelper.Companion.COLUMN_OPEN_DATE
import com.t.familymanagers.data.DataBaseHelper.Companion.COLUMN_PRODUCTION_DATE_D
import com.t.familymanagers.data.DataBaseHelper.Companion.COLUMN_PRODUCTION_DATE_M
import com.t.familymanagers.data.DataBaseHelper.Companion.COLUMN_PRODUCTION_DATE_Y
import com.t.familymanagers.data.DataBaseHelper.Companion.COLUMN_SHELF_LIVE
import com.t.familymanagers.data.DataBaseHelper.Companion.TABLE_NAME
import java.util.ArrayList

class DataBaseTools(val context: Context, val database: SQLiteDatabase) {

    fun insert(food: Food) {
        var contentValues = ContentValues()
        contentValues.put(COLUMN_NAME, food.name)
        contentValues.put(COLUMN_IF_NEED_ADD, food.ifNeedAdd)
        contentValues.put(COLUMN_IF_OPEN, food.ifOpen)
        contentValues.put(COLUMN_NUMBER, food.number)
        contentValues.put(COLUMN_OPEN_DATE, food.openDate)
        contentValues.put(COLUMN_PRODUCTION_DATE_Y, food.productionDateY)
        contentValues.put(COLUMN_PRODUCTION_DATE_M, food.productionDateM)
        contentValues.put(COLUMN_PRODUCTION_DATE_D, food.productionDateD)
        contentValues.put(COLUMN_SHELF_LIVE, food.shelfLive)
        contentValues.put(COLUMN_KIND, food.kind)

        database.insertWithOnConflict(
            TABLE_NAME,
            null,
            contentValues,
            SQLiteDatabase.CONFLICT_REPLACE
        )

        Log.d("DataBaseTools", "insert ${food.name}")
    }



    fun deleteById(id: Int) {
        database.delete(
            TABLE_NAME,
            "id=?"
            ,
            arrayOf(id.toString())
        )
    }


    fun getFoodByName(name: String): List<Food>?{
        var food: Food? = null
        var foodList= mutableListOf<Food>()
        val cursor = database.query(
            TABLE_NAME,
            arrayOf(
                COLUMN_ID,
                COLUMN_NAME,
                COLUMN_IF_NEED_ADD,
                COLUMN_IF_OPEN,
                COLUMN_NUMBER,
                COLUMN_OPEN_DATE,
                COLUMN_PRODUCTION_DATE_Y,
                COLUMN_PRODUCTION_DATE_M,
                COLUMN_PRODUCTION_DATE_D,
                COLUMN_SHELF_LIVE,
                COLUMN_KIND
            ),
            "name like ?",
            arrayOf("$name%"),
            null,
            null,
            null,
            null
        )
        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val name = cursor.getString(1)
            val ifNeedAdd = cursor.getString(2)
            val ifOpen = cursor.getString(3)
            val number = cursor.getInt(4)
            val openDate = cursor.getString(5)
            val productionDateY = cursor.getString(6)
            val productionDateM = cursor.getString(7)
            val productionDateD = cursor.getString(8)
            val shelfLive = cursor.getString(9)
            val kind = cursor.getString(10)
            food= Food(name,number,productionDateY,productionDateM,productionDateD,shelfLive,ifOpen,openDate,ifNeedAdd,kind)
            Log.d("DataBaseTools", "name:${cursor.getString(1)}")
            foodList.add(food)
        }
        cursor.close()
        Log.d("DataBaseTools", "$name ${food}")
        return foodList
    }

    fun getFoodByKind(kind: String): List<Food>? {
        var food: Food? = null
        var foodList= mutableListOf<Food>()
        val cursor = database.query(
            TABLE_NAME,
            arrayOf(
                COLUMN_ID,
                COLUMN_NAME,
                COLUMN_IF_NEED_ADD,
                COLUMN_IF_OPEN,
                COLUMN_NUMBER,
                COLUMN_OPEN_DATE,
                COLUMN_PRODUCTION_DATE_Y,
                COLUMN_PRODUCTION_DATE_M,
                COLUMN_PRODUCTION_DATE_D,
                COLUMN_SHELF_LIVE,
                COLUMN_KIND
            ),
            "kind = ?",
            arrayOf(kind),
            null,
            null,
            null,
            null
        )
        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val name = cursor.getString(1)
            val ifNeedAdd = cursor.getString(2)
            val ifOpen = cursor.getString(3)
            val number = cursor.getInt(4)
            val openDate = cursor.getString(5)
            val productionDateY = cursor.getString(6)
            val productionDateM = cursor.getString(7)
            val productionDateD = cursor.getString(8)
            val shelfLive = cursor.getString(9)
            val kind = cursor.getString(10)
            food=Food(name,number,productionDateY,productionDateM,productionDateD,shelfLive,ifOpen,openDate,ifNeedAdd,kind)
            foodList.add(food)
            Log.d("getFoodByKind","$food $id ${cursor.columnCount} ${cursor.count}")
        }


        cursor.close()

        return foodList
    }

    companion object {

        @Volatile
        private var instance: DataBaseTools? = null

        fun getInstance(context: Context, database: SQLiteDatabase) =
            instance
                ?: synchronized(this) {
                    instance
                        ?: DataBaseTools(context, database).also { instance = it }
                }

    }
}