package com.example.poe_task_2

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout

class TaskFragment : Fragment() {

    private lateinit var taskRecycler: RecyclerView
    private lateinit var newTask: RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task, container, false)
        init(view)
        setListAdapter(view)
        return view
    }

    private fun init(view: View) {//initializes the variables
        taskRecycler = view.findViewById(R.id.taskRecyclerView)
        newTask = view.findViewById(R.id.NewCard)
        newTask.setOnClickListener {
            val intent = Intent(view.context, NewTask::class.java)
            startActivity(intent)
        }
    }

    private fun setListAdapter(view: View) {//adapter for the List recycler view
        val adapter = ListTaskRecyclerAdapter(view.context)
        taskRecycler.adapter = adapter
        taskRecycler.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
        val util = Utility()
        val tasks: ArrayList<Task> = Utility.getNewTask()
        val categories: ArrayList<Category> = Utility.getAllCategories()
        adapter.setTasks(tasks, categories)
    }
}