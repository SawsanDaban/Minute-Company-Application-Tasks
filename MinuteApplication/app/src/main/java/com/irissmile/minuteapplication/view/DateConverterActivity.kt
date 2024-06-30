package com.irissmile.minuteapplication.view

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.icu.util.IslamicCalendar
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.irissmile.minuteapplication.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class DateConverterActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener{

    private lateinit var pickDateText: TextView
    private lateinit var convertedDateText: TextView
    private lateinit var pickButton: Button

    private val gregorianFormatter = SimpleDateFormat("MMMM dd, yyyy", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_converter)

        // Use the current date as the default date in the picker.
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        findViewById<Button>(R.id.pickDate).setOnClickListener {
            DatePickerDialog(this, this, year, month, day).show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        Log.i("DATE", "$year -- $month -- $dayOfMonth")

        ConvertGregorianToHijri(year, month, dayOfMonth)
    }


    @SuppressLint("SetTextI18n")
    private fun ConvertGregorianToHijri(year: Int, month: Int, dayOfMonth: Int) {
        val gregorianCalendar = Calendar.getInstance()
        gregorianCalendar.set(year, month, dayOfMonth)

        findViewById<TextView>(R.id.gregorianText).text = "Picked Date: " + gregorianFormatter.format(gregorianCalendar.timeInMillis)
        Log.i("GregorianCalendar", gregorianFormatter.format(gregorianCalendar.timeInMillis))

        val islamicCalendar = IslamicCalendar()
        islamicCalendar.time = gregorianCalendar.time

        val hijriYear = islamicCalendar.get(IslamicCalendar.YEAR)
        val hijriMonth = islamicCalendar.get(IslamicCalendar.MONTH) // Months are 0-based
        val hijriDay = islamicCalendar.get(IslamicCalendar.DAY_OF_MONTH)

        val monthName = getHijriMonthName(hijriMonth)

        //String.format(Locale.US, "%s %02d, %d", monthName, hijriDay, hijriYear)
        //String.format("%d-%02d-%02d", hijriYear, hijriMonth, hijriDay)

        findViewById<TextView>(R.id.hijriText).text = "Converted Date: " + String.format(Locale.US, "%s %02d, %d", monthName, hijriDay, hijriYear)
        Log.i("IslamicCalendar", String.format("%d-%02d-%02d", hijriYear, hijriMonth, hijriDay))
    }

    private fun getHijriMonthName(month: Int): String {
        return when (month) {
            0 -> "Muharram"
            1 -> "Safar"
            2 -> "Rabi' al-awwal"
            3 -> "Rabi' al-thani"
            4 -> "Jumada al-awwal"
            5 -> "Jumada al-thani"
            6 -> "Rajab"
            7 -> "Sha'ban"
            8 -> "Ramadan"
            9 -> "Shawwal"
            10 -> "Dhu al-Qi'dah"
            11 -> "Dhu al-Hijjah"
            else -> "Unknown"
        }
    }
}