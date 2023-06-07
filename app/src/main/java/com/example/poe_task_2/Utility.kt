package com.example.poe_task_2

import java.util.*

class Utility {





    fun getTaskId(): Int {
        TASK_ID++
        return TASK_ID
    }

    fun setTaskId(taskId: Int) {
        TASK_ID = taskId
    }

    fun removeCategory(category: Category): Boolean {
        return AllCategories.remove(category)
    }


    companion object {
        private var CATEGORY_ID = 0
        private var TASK_ID = 0
        private val AllCategories = ArrayList<Category>()
        private val newTask = ArrayList<Task>()

        init {
            if (AllCategories.isEmpty()) {
                initAllCategory()
            }

            if (newTask.isEmpty()) {
                initAllTask()
            }
        }

        fun getNewTask(): ArrayList<Task> {
            return newTask
        }
        fun setNewTask(_Title: String, _ImageUrl: String, _Description: String, _EndDate:  Date?,
                       _StartDate: String, _Time: String, CATEGORY_ID: Int, _Duration: Int) {
            TASK_ID++
            newTask.add(Task(TASK_ID, _Title, _ImageUrl, _Description, _StartDate, _EndDate, _Time, CATEGORY_ID, _Duration))
        }



        fun getAllCategories(): ArrayList<Category> {
            return AllCategories
        }


        private fun initAllCategory() {
            var CATEGORY_ID = 0
            var Name = "Work"
            var image = R.drawable.farmland
            var background = R.drawable.weekday_back

            AllCategories.add(Category(CATEGORY_ID, Name, image, background))

            CATEGORY_ID++
            Name = "Week End"
            image = R.drawable.weekend
            background = R.drawable.cattleellipse

            AllCategories.add(Category(CATEGORY_ID, Name, image, background))

            CATEGORY_ID++
            Name = "Week Day"
            image = R.drawable.sunrise
            background = R.drawable.sunrise_back

            AllCategories.add(Category(CATEGORY_ID, Name, image, background))
        }

        private fun initAllTask() {
            val TASK_ID = 0
            val Title = "Clean the Yard"
            val ImageUrl = "data:image/jpeg;base64,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"
            val Description = "Clean the yard and remove the leaves."
            val EndDate = Date()
            val StartDate = "2023-06-01"
            val Time = "10:00 AM"
            val CATEGORY_ID = 1
            val Duration = 2

            newTask.add(Task(TASK_ID, Title, ImageUrl, Description, StartDate, EndDate, Time, CATEGORY_ID, Duration));

            val task2 = Task(
                1,
                "Paint the Fence",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTJ2HCQwNOetaK7RAV-XTjKdwNsOlGce2D_QljqHUMI&s",
                "Paint the fence with a fresh coat of white paint.",
                "2023-06-03",
                Date(123,11,11),
                "09:00 AM",
                0,
                3
            )
            newTask.add(task2)
            val task3 = Task(
                1,
                "Do my homework",
                "data:image/jpeg;base64,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",
                "Doing my homework for another time.",
                "2023-06-03",
                Date(123,10,12),
                "09:00 AM",
                2,
                3
            )
            newTask.add(task3)
        }
    }}


