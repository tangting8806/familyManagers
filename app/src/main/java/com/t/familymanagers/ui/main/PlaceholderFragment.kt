package com.t.familymanagers.ui.main

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.t.familymanagers.data.CONSTANT
import com.t.familymanagers.data.DataBaseHelper
import com.t.familymanagers.data.DataBaseTools
import com.t.familymanagers.R


class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    var database: SQLiteDatabase? = null
    var recyclerView: RecyclerView? = null
    private var kind = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
        database = DataBaseHelper(activity!!).writableDatabase
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        recyclerView = root.findViewById<RecyclerView>(R.id.recycler_view)
        submitUI()
        return root
    }

    fun submitUI() {
        pageViewModel.index.observe(this, Observer<Int> {
            Log.d("PlaceholderFragment","submitUI $it")
            kind = CONSTANT().KIND_LIST.get(it-1)
            Log.d("PlaceholderFragment","submitUI  $kind")
            val dataList = DataBaseTools.getInstance(activity!!.applicationContext, database!!)
                .getFoodByKind(kind)
            Log.d("PlaceholderFragment","submitUI  $dataList")
            if (dataList != null) {
                val adapter = MyRecyclerViewAdapter(activity!!.applicationContext, dataList!!)
                val layoutManager = LinearLayoutManager(activity)
                recyclerView!!.layoutManager = layoutManager
                recyclerView!!.adapter = adapter

            }
        })
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}