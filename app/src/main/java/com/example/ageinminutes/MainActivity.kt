package com.example.ageinminutes

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.ageinminutes.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        datePickerBtn.setOnClickListener {
            clickDatePicker(it)
        }
    }

    private fun clickDatePicker(view: View) {

        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(
            this, DatePickerDialog.OnDateSetListener
            { v, y, m, d -> // month starts from 0
                val selectedDate = "$d/${m + 1}/$y"
                tvSelectedDate.text = selectedDate

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

                // the date needed to calculate date in minutes
                val beginDate = sdf.parse(selectedDate)
                val selectedDateInMinutes = beginDate!!.time / 60_000

                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                val currentDateInMinutes = currentDate!!.time / 60_000

                val difference = currentDateInMinutes - selectedDateInMinutes
                tvSelectedDateInMinutes.text = "$difference"

            }, year,
            month,
            day
        )
        // restrict the datePicker from selecting any date in the future
        dpd.datePicker.maxDate = Date().time - 1 * 24 * 60 * 60 * 100 //  millis in one day
        dpd.show()
    }
}