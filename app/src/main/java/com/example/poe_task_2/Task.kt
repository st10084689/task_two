package com.example.poe_task_2

import java.util.Date

class Task(
    var id: Int,
    var title: String,
    var imageUrl: String,
    var desc: String?,
    var startDate: String,
    var endDate: Date?,
    var time: String,
    var categoryId: Int,
    var duration: Int
) {

}