package com.codingwithtushar.findimages.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import java.lang.Exception

class TopExceptionHandler(activity: Activity) : Thread.UncaughtExceptionHandler {

    private var defaultUEH = Thread.getDefaultUncaughtExceptionHandler()
    private var activity: Activity = activity
    private val TAG = "TopExceptionHandler"


    override fun uncaughtException(t: Thread, e: Throwable) {
        val stackTraceElement = e.stackTrace
        var report = e.toString() + "\n\n"
        report += "-----------------Stack Trace \n\n"
        val builder = StringBuffer()

        for (i in stackTraceElement) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                builder.append(i).append(System.lineSeparator())
            }
        }
        report = builder.toString()
        Log.d("TAG", "crashytics report is--->" + report)


        try {
            val trace = activity.openFileOutput("stack.trace", Context.MODE_PRIVATE)
            trace.write(report.toByteArray())
        } catch (e: Exception) {

        }
        defaultUEH.uncaughtException(t, e)
    }
}