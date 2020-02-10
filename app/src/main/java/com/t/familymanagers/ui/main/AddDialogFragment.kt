package com.t.familymanagers.ui.main

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.t.familymanagers.R
import com.t.familymanagers.Tools.PickerView
import com.t.familymanagers.Tools.PickerView.onSelectListener
import com.t.familymanagers.data.CONSTANT
import com.t.familymanagers.data.DataBaseHelper
import com.t.familymanagers.data.DataBaseTools
import com.t.familymanagers.data.Food
import kotlinx.android.synthetic.main.add_dialog_fragment.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AddDialogFragment() : DialogFragment() ,View.OnClickListener{

    var database: SQLiteDatabase? = null

    private lateinit var nameEditText:EditText
    private lateinit var numEditText: EditText
    private var selectedKind=CONSTANT().FOOD_KIND_GANHUO
    private var selectedProductY=0
    private var selectedProductM=0
    private var selectedProductD=0
    private var selectedShelfLiveN=0
    private var selectedShelfLiveInit=CONSTANT().INIT_WEEK

    private lateinit var confirmButton:Button
    private lateinit var nextButton:Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.add_dialog_fragment, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = DataBaseHelper(activity!!.applicationContext).writableDatabase
        nameEditText=view.findViewById(R.id.name)
        numEditText=view.findViewById(R.id.num)
        confirmButton=view.findViewById(R.id.yes_Button)
        nextButton=view.findViewById(R.id.next_Button)
        confirmButton.setOnClickListener(this)
        nextButton.setOnClickListener(this)
        initSLPV(view)
        initKindPV(view)
        initDataPicker(view)
    }

    fun initKindPV(view: View) {
        val kind_pv = view.findViewById(R.id.kind_picker) as PickerView
        val data: MutableList<String> = ArrayList()
        for (kind in TAB_TITLES) {
            data.add(getKindString(kind))
        }
        kind_pv.setData(data)
        selectedKind=data[data.size/2]
        kind_pv.setOnSelectListener(object : onSelectListener {
            override fun onSelect(text: String?) {
                selectedKind=text?:""
                Toast.makeText(
                    context, "选择了 $text ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    fun initSLPV(view: View) {
        val shelfLive_num_pv = view.findViewById(R.id.shelfLive_num_piker) as PickerView
        val dataNum: MutableList<String> = ArrayList()
        for (n in 0..24) {
            dataNum.add(n.toString())
        }
        shelfLive_num_pv.setData(dataNum)
        selectedShelfLiveN=dataNum[dataNum.size/2].toInt()
        shelfLive_num_pv.setOnSelectListener(object : onSelectListener {
            override fun onSelect(text: String?) {
                selectedShelfLiveN=(text?:"").toInt()
                Toast.makeText(
                    context, "选择了 $text ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        val shelfLive_init_pv = view.findViewById(R.id.shelfLive_YorM_piker) as PickerView
        val dataInit: MutableList<String> = mutableListOf("天", "周", "月", "年")
        shelfLive_init_pv.setData(dataInit)
        selectedShelfLiveInit=dataInit[dataInit.size/2]
        shelfLive_init_pv.setOnSelectListener(object : onSelectListener {
            override fun onSelect(text: String?) {
                selectedShelfLiveInit=text?:""
                Toast.makeText(
                    context, "选择了 $text ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    fun initDataPicker(view: View) {
        val productDateY_piker = view.findViewById(R.id.productDateY_piker) as PickerView
        val dataY: MutableList<String> = ArrayList()
        for (n in 2018..2020) {
            dataY.add(n.toString())
        }
        productDateY_piker.setData(dataY)
        selectedProductY=dataY[dataY.size/2].toInt()
        productDateY_piker.setOnSelectListener(object : onSelectListener {
            override fun onSelect(text: String?) {
                selectedProductY=(text?:"").toInt()
                Toast.makeText(
                    context, "选择了 $text ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        val productDateM_piker = view.findViewById(R.id.productDateM_piker) as PickerView
        val dataM: MutableList<String> =ArrayList()
        for (n in 1..12) {
            dataM.add(n.toString())
        }
        productDateM_piker.setData(dataM)
        selectedProductM=dataM[dataM.size/2].toInt()
        productDateM_piker.setOnSelectListener(object : onSelectListener {
            override fun onSelect(text: String?) {
                selectedProductM=(text?:"").toInt()
                Toast.makeText(
                    context, "选择了 $text ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        val productDateD_piker = view.findViewById(R.id.productDateD_piker) as PickerView
        val dataD: MutableList<String> =ArrayList()
        for (n in 1..31) {
            dataD.add(n.toString())
        }
        productDateD_piker.setData(dataD)
        selectedProductD=dataD[dataD.size/2].toInt()
        productDateD_piker.setOnSelectListener(object : onSelectListener {
            override fun onSelect(text: String?) {
                selectedProductD=(text?:"").toInt()
                Toast.makeText(
                    context, "选择了 $text ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    };


    companion object {
        fun newInstance(): AddDialogFragment? {
            val fragment = AddDialogFragment()
            return fragment
        }
    }

    fun getKindString(resourceId: Int): String {
        return this.resources.getString(resourceId)
    }


    fun productDateConverter(year:Int,month:Int,day:Int):Long{
        Log.d("AddDialogFragment","productDateConverter $year  $month  $day")
        return (day+month*100+year*10000).toLong()
    }

    fun shelfLiveConverter(num:Int,init:String):Int{
        Log.d("AddDialogFragment","shelfLiveConverter $num  $init  ")
        when(init){
            CONSTANT().INIT_DAY->{
                return num
            }
            CONSTANT().INIT_WEEK->{
                return num*7
            }
            CONSTANT().INIT_MONTH->{
                return num*30
            }
            CONSTANT().INIT_YEAR->{
                return num*360
            }
            else-> return 0
        }
    }

    override fun onClick(p0: View?) {
        if(nameEditText.text.isNullOrEmpty()){
            Toast.makeText(context,"食物名称不可以为空哦",Toast.LENGTH_SHORT).show()
            return
        }
        if(numEditText.text.isNullOrEmpty()){
            Toast.makeText(context,"食物数量不可以为空哦",Toast.LENGTH_SHORT).show()
            return
        }
        val shelfLive=shelfLiveConverter(selectedShelfLiveN,selectedShelfLiveInit)
        val food= Food(nameEditText.text.toString(),numEditText.text.toString().toInt(),selectedProductY.toString(),selectedProductM.toString(),selectedProductD.toString(),shelfLive.toString()
            ,CONSTANT().IF_OPEN_FALSE,CONSTANT().OPEN_DATE,CONSTANT().IF_NEED_ADD_FALSE,selectedKind)
            when(p0){
                confirmButton->{
                    DataBaseTools.getInstance(context!!,database!!).insert(food)
                    dismiss()
                    Toast.makeText(context,"保存好啦",Toast.LENGTH_SHORT).show()
                }
                nextButton->{
                    DataBaseTools.getInstance(context!!,database!!).insert(food)
                    nameEditText.setText("")
                    numEditText.setText("")
                    Toast.makeText(context,"保存好啦，填下一个吧",Toast.LENGTH_SHORT).show()
                }
            }

    }
}