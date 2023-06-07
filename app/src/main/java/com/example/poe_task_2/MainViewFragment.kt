package com.example.poe_task_2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class MainViewFragment : Fragment() {

    private lateinit var categoryRecView: RecyclerView
    private lateinit var newTask: RelativeLayout
    private lateinit var timerTextView: TextView
    private lateinit var StartButton: Button
    private lateinit var StopButton: Button
    private lateinit var DateCardText: TextView
    private lateinit var TitleCardText: TextView
    private lateinit var categoryView: ImageView
    private lateinit var image: ImageView
    private lateinit var UpcomingCard: RelativeLayout

    private  val TAG = "MainViewFragment"

    private var IsRunning: Boolean = false
    private var startTime: Long = 0
    private val DateFormat: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    private lateinit var inflater: LayoutInflater

    private lateinit var tasks: ArrayList<Task>
    private lateinit var categories: ArrayList<Category>

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        fun newInstance(param1: String, param2: String): MainViewFragment {
            val fragment = MainViewFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            // Handle arguments if needed
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_view, container, false)
        tasks = Utility.getNewTask()
        categories = Utility.getAllCategories()
        init(view)
        setCardView()
        return view
    }

    private fun init(view: View) {
        //initializes the variables
        categoryRecView = view.findViewById(R.id.categoryRecyclerFragView)
        newTask = view.findViewById(R.id.NewCard)
        timerTextView = view.findViewById(R.id.Timer)
        StartButton = view.findViewById(R.id.StartTime)
        StopButton = view.findViewById(R.id.EndTime)
        IsRunning = false
        startTime = 0
        DateCardText = view.findViewById(R.id.Date)
        TitleCardText = view.findViewById(R.id.titleCard)
        categoryView = view.findViewById(R.id.categoryImage)
        image = view.findViewById(R.id.image)
        UpcomingCard = view.findViewById(R.id.UpcomingCard)

        newTask.setOnClickListener {
            val intent = Intent(context, NewTask::class.java)
            startActivity(intent)
        }

        UpcomingCard.setOnClickListener {
            inflater = LayoutInflater.from(context)
            val layout =
                inflater.inflate(R.layout.toast_custom, view.findViewById(R.id.toast_custom_layout))
            val toastText = layout.findViewById<TextView>(R.id.message)
            toastText.text =
                "Description:${tasks[0].desc}\nStartDate:${tasks[0].startDate}\nDuration:${tasks[0].duration}hours"

            val toast = Toast(context)
            toast.duration = Toast.LENGTH_LONG
            toast.view = layout
            toast.show()
        }

        StartButton.setOnClickListener {
            if (!IsRunning) {
                startTimer()
            }
        }
        StopButton.setOnClickListener {
            if (IsRunning) {
                stopTimer()
            }
        }
        timerTextView.setOnClickListener {
            startTime = 0
            timerTextView.text = "00:00:000"
        }

        val adapter = CategoryRecyclerAdapter(view.context)
        categoryRecView.adapter = adapter
        categoryRecView.layoutManager = LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
        adapter.setCategories(categories, tasks)
    }

    private fun setCardView() {//method sets up the upcoming cardview
        tasks.sortByDescending { it.endDate }

        val highestTask = tasks[0]
        val date = DateFormat.format(highestTask.endDate)
        Log.d(TAG, "SetCardView: ${highestTask.title}")
        Log.d(TAG, "SetCardView: ${highestTask.endDate}")
        DateCardText.text = date
        TitleCardText.text = highestTask.title

        Glide.with(this).asBitmap()
            .load(highestTask.imageUrl)
            .centerCrop()
            .into(image)
        val categoryNumber = highestTask.categoryId
        Glide.with(this).asBitmap()
            .load(categories[categoryNumber].imageUrl)
            .centerCrop()
            .into(categoryView)
    }

    private fun startTimer() {//method starts the timer
        IsRunning = true
        if (startTime == 0L) {
            startTime = System.currentTimeMillis()
        }

        // Starts updating the timer
        updateTimerUI()
    }

    private fun stopTimer() {//method starts the timer
        IsRunning = false
    }

    private fun updateTimerUI() {//methed continuesly update the timer
        // Calculate the elapsed time
        val currentTime = System.currentTimeMillis()
        val elapsedTime = currentTime - startTime

        // Convert the elapsed time to minutes, seconds, and milliseconds
        val minutes = (elapsedTime / 60000).toInt()
        val seconds = (elapsedTime % 60000 / 1000).toInt()
        val milliseconds = (elapsedTime % 1000).toInt()

        // Update the timer TextView
        val timeString = String.format(Locale.getDefault(), "%02d:%02d:%03d", minutes, seconds, milliseconds)
        timerTextView.text = timeString

        // If the timer is still running, schedule the next UI update after a small delay
        if (IsRunning) {
            timerTextView.postDelayed({ updateTimerUI() }, 10)
        }
    }


}