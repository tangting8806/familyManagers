package com.t.familymanagers

import android.app.Dialog
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.t.familymanagers.data.DataBaseHelper
import com.t.familymanagers.data.DataBaseHelper.Companion.TABLE_NAME
import com.t.familymanagers.data.DataBaseTools
import com.t.familymanagers.data.Food.Companion.FOOD_KIND_LINGSHI
import com.t.familymanagers.ui.main.AddDialogFragment
import com.t.familymanagers.ui.main.SectionsPagerAdapter


class MainActivity : AppCompatActivity(){
    var database: SQLiteDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.tool_bar)
        this.setSupportActionBar(toolbar);
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        database = DataBaseHelper(this).writableDatabase
        Log.d("onCreate", "${HaveData(database!!, TABLE_NAME)}")

        val fabAdd: FloatingActionButton = findViewById(R.id.fab_add)
        fabAdd.setOnClickListener { view ->
            if (database != null) {
                Log.d("onCreate", "setOnClickListener ${HaveData(database!!, TABLE_NAME)}")
            }
//             DataBaseTools.getInstance(this@MainActivity, database!!).insert(food)
//             DataBaseTools.getInstance(this@MainActivity, database!!).deleteById(1)
//             DataBaseTools.getInstance(this@MainActivity, database!!).getFoodByKind(getKindString(FOOD_KIND_LINGSHI))

            val addDialogFragment: AddDialogFragment? = AddDialogFragment.newInstance()
            addDialogFragment!!.show(supportFragmentManager, "edit")
        }

        val testfab: FloatingActionButton = findViewById(R.id.fab_test)
        testfab.setOnClickListener { view ->
            if (database != null) {
                Log.d("onCreate", "setOnClickListener ${HaveData(database!!, TABLE_NAME)}")
            }
             val list=DataBaseTools.getInstance(this@MainActivity, database!!).getFoodByKind(getKindString(FOOD_KIND_LINGSHI))
        for(l in list!!){
            Log.d("MainActivity","$l")
        }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_searchview, menu)
        initMenu(menu!!)
        return true
    }

    private fun initMenu(menu: Menu) {
        val menuItem = menu.findItem(R.id.search_view_menu)
        val mSearchView = menuItem.actionView as SearchView
        mSearchView.isSubmitButtonEnabled = true
        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return true
            }

            override fun onQueryTextChange(s: String): Boolean {
                DataBaseTools.getInstance(this@MainActivity, database!!).getFoodByName(s)
                return true
            }
        })
    }

    fun getKindString(resourceId: Int): String {
        return this.resources.getString(resourceId)
    }


    fun HaveData(db: SQLiteDatabase, tablename: String): Boolean {
        var cursor: Cursor? = null;
        var a = false;
        cursor = db.rawQuery("select name from sqlite_master where type='table' ", null);
        while (cursor.moveToNext()) {
            //遍历出表名
            var name = cursor.getString(0);
            if (name.equals(tablename)) {
                a = true;
            }
            Log.i("每张表名", "$name $tablename ${name.equals(tablename)}")
        }
        if (a) {
            cursor = db.query(tablename, null, null, null, null, null, null);
            //检查是不是空表
            Log.i("有", "有内容吗？ ${cursor.getCount() > 0}")
            return cursor.getCount() > 0;
        } else
            return false;

    }



}






