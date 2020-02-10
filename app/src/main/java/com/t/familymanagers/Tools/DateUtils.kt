package com.t.familymanagers.Tools

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

/** * * @author Liang Jx * * @since 2019/8/12 12:38 PM * @version ${VERSION} * @desc 日期工具类 * */
object DateUtils {
    /**
     * 《纯手写良心代码》 为了解决跨年问题 润平年问题  精确计算两个日期天数差值
     * 通过文件夹日期名 计算日志是否在有限期时间内
     * @param oldDate 日志文件日期 yyyyMMdd
     * @param newDate 当前日期 yyyyMMdd
     */
    fun checkDateInValid(oldDate: String, newDate: String): Int {
        val c1 = Calendar.getInstance()
        val c2 = Calendar.getInstance()
        Log.d("DateUtils","$oldDate $newDate")
        val  df =  SimpleDateFormat("yyyyMMdd");
        c1.time = df.parse(oldDate)
        c2.time = df.parse(newDate)
        val year1 = c1.get(Calendar.YEAR)
        val year2 = c2.get(Calendar.YEAR)
        var days = 0
        if (year2 > year1) {            //如果跨年了
            val day1 = c1.get(Calendar.DAY_OF_YEAR)
            val day2 = c2.get(Calendar.DAY_OF_YEAR)
             days = 0
            for (i in year1..year2) {
                if (i == year1) {
                    days += if (GregorianCalendar().isLeapYear(year1)) {
                        366 - day1
                    } else {
                        365 - day1
                    }
                } else if (i == year2) {

                    days += day2
                } else {
                    days += if (GregorianCalendar().isLeapYear(year1)) {
                        366
                    } else {
                        365
                    }
                }
            }

        } else if (year2 == year1) {
         //   如果没跨年
             days = c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR)

        }
        return days
    }
}
