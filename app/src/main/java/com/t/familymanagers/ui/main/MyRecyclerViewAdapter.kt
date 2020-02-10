package com.t.familymanagers.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.t.familymanagers.R
import com.t.familymanagers.Tools.DateUtils
import com.t.familymanagers.data.Food
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.SimpleFormatter

class MyRecyclerViewAdapter : RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    private var mContext: Context? = null
    private var mdata: List<Food>? = null

    constructor(context: Context, data: List<Food>) {
        mContext = context
        mdata = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.food_list_view_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (mdata == null) {
            0
        } else {
            mdata!!.size
        }
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (position % 2 == 0) {
            holder.nameTextView.setBackgroundResource(R.color.color6)
            holder.numTextView.setBackgroundResource(R.color.color6)
            holder.daysTextView.setBackgroundResource(R.color.color6)
            holder.ifOpenTextView.setBackgroundResource(R.color.color6)
            holder.openDaysTextView.setBackgroundResource(R.color.color6)
        } else {
            holder.nameTextView.setBackgroundResource(R.color.white)
            holder.numTextView.setBackgroundResource(R.color.white)
            holder.daysTextView.setBackgroundResource(R.color.white)
            holder.ifOpenTextView.setBackgroundResource(R.color.white)
            holder.openDaysTextView.setBackgroundResource(R.color.white)
        }
        holder.nameTextView.text = mdata!![position].name
        holder.numTextView.text = mdata!![position].number.toString()

        val shelfLive = mdata!![position].shelfLive
        val productDateY = mdata!![position].productionDateY.toInt()
        val productDateM = mdata!![position].productionDateM.toInt()
        val productDateD = mdata!![position].productionDateD.toInt()

        val productDate=productDateY*10000+productDateM*100+productDateD
        val todayDate = SimpleDateFormat("yyyyMMdd").format(Date(System.currentTimeMillis()))

        val daysBefore=DateUtils.checkDateInValid(productDate.toString(),todayDate)
        val daysLeft=shelfLive.toInt()-daysBefore

        holder.daysTextView.text = daysLeft.toString()

        holder.ifOpenTextView.text = mdata!![position].ifOpen
        holder.openDaysTextView.text = mdata!![position].openDate
    }

    class MyViewHolder : RecyclerView.ViewHolder {
        var nameTextView: AppCompatTextView
        var numTextView: AppCompatTextView
        var daysTextView: AppCompatTextView
        var ifOpenTextView: AppCompatTextView
        var openDaysTextView: AppCompatTextView

        constructor(view: View) : super(view) {
            nameTextView = view.findViewById(R.id.name)
            numTextView = view.findViewById(R.id.num)
            daysTextView = view.findViewById(R.id.days)
            ifOpenTextView = view.findViewById(R.id.if_open)
            openDaysTextView = view.findViewById(R.id.open_days)
        }

    }


}