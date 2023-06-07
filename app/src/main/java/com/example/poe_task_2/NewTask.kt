package com.example.poe_task_2

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class NewTask : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private var Title: EditText? = null
    private var Desc: EditText? = null
    private var StartDate: EditText? = null
    private var EndDate: EditText? = null
    private var Duration: EditText? = null
    private var Time: EditText? = null
    private var camera: ImageButton? = null
    private var image: ImageView? = null
    private var category: Spinner? = null
    private var categoryId = 0
    private var EndDateVar: Date? = null
    private var currentPhotoPath: File? = null
    private var dateFormat: SimpleDateFormat? = null
    private var categories: ArrayList<Category>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)
        innit()
        category = findViewById(R.id.spinner)
        categories = Utility.getAllCategories()

        val adapter: ArrayAdapter<Category> = object : ArrayAdapter<Category>(
            this, android.R.layout.simple_spinner_item,
            categories!!
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                var convertView = convertView
                if (convertView == null) {
                    convertView = LayoutInflater.from(context)
                        .inflate(android.R.layout.simple_spinner_item, parent, false)
                }
                val textView = convertView!!.findViewById<TextView>(android.R.id.text1)
                textView.text = getItem(position)!!.name
                return convertView
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                var convertView = convertView
                if (convertView == null) {
                    convertView = LayoutInflater.from(context)
                        .inflate(android.R.layout.simple_spinner_dropdown_item, parent, false)
                }
                val textView = convertView!!.findViewById<TextView>(android.R.id.text1)
                textView.text = getItem(position)!!.name
                return convertView!!
            }
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        category?.adapter = adapter
        category?.onItemSelectedListener = this
    }

    private fun innit() {
        Duration = findViewById(R.id.DurationInput)
        Title = findViewById(R.id.titeInput)
        Desc = findViewById(R.id.DecriptionInput)
        StartDate = findViewById(R.id.StartDateEditTxt)
        EndDate = findViewById(R.id.endDateEditTxt)
        Time = findViewById(R.id.TimeInput)
        image = findViewById(R.id.capturedImage)
        dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        camera = findViewById(R.id.cameraPrompt)
        Title?.text.toString()
        camera?.setOnClickListener(View.OnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            var FilePhoto: File? = null
            try {
                FilePhoto = createImageFile()
                Log.d(TAG, "onClick: $FilePhoto")
            } catch (ei: IOException) {
            }
            startActivityForResult(intent, 0)
        })
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName,
            ".jpg",
            storageDir
        )
        Log.d(TAG, "createImageFile: $image")
        currentPhotoPath = image
        return image
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            super.onActivityResult(requestCode, resultCode, data)
            val bm = data?.extras?.get("data") as Bitmap?
            image?.setImageBitmap(bm)
        } catch (ex: Exception) {
            Toast.makeText(this, "Pic not saved", Toast.LENGTH_SHORT).show()
        }
    }

    fun showTimePickerDialog(view: View?) {
        val calendar = Calendar.getInstance()
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minute = calendar[Calendar.MINUTE]
        val timePickerDialog = TimePickerDialog(
            this,
            { timePicker, selectedHour, selectedMinute ->
                calendar[Calendar.HOUR_OF_DAY] = selectedHour
                calendar[Calendar.MINUTE] = selectedMinute

                val timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT)
                val selectedTime = timeFormat.format(calendar.time)
                Time?.setText(selectedTime)
            },
            hour,
            minute,
            true
        )
        timePickerDialog.show()
    }

    fun showStartDatePickerDialog(view: View?) {
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]

        val datePickerDialog = DatePickerDialog(
            this@NewTask,
            { view, year, monthOfYear, dayOfMonth ->
                calendar[year, monthOfYear] = dayOfMonth
                StartDate?.setText(dateFormat?.format(calendar.time))
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    fun showEndDatePickerDialog(view: View?) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this@NewTask,
            { _, selectedYear, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, selectedYear)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                EndDateVar = calendar.time
                EndDate?.setText(dateFormat?.format(calendar.time))
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    fun AddNewTask(view: View?) {
        val util = Utility()
        val id: Int = util.getTaskId()

        try {
            val DurationInt = Duration?.text.toString().toInt()

            Utility.setNewTask(

                Title?.text.toString(),
                currentPhotoPath.toString(),
                Desc?.text.toString(),
                EndDateVar,
                StartDate?.text.toString(),
                Time?.text.toString(),
                categoryId,
                DurationInt
            )
        }catch(e: Exception){
            Toast.makeText(applicationContext, "Failed!? make sure to fill in all the bars", Toast.LENGTH_SHORT).show()

        }

        for (task in Utility.getNewTask()) {
            println("Task ID: ${task.id}")
            println("Title: ${task.title}")
            println("Title: ${task.imageUrl}")
            println("Description: ${task.desc}")
            println("End Date: ${task.endDate}")
            println("Start Date: ${task.startDate}")
            println("Time: ${task.time}")
            println("-------------------------")
        }

        val ToMain = Intent(this@NewTask, MainView::class.java)
        startActivity(ToMain)
    }

    override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
        val selectedCategory = adapterView.getItemAtPosition(i) as Category
        categoryId = selectedCategory.id
    }

    override fun onNothingSelected(adapterView: AdapterView<*>?) {}

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
        private const val TAG = "NewTask"
    }

}