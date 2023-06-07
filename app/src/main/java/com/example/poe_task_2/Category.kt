package com.example.poe_task_2

class Category(
    var id: Int,
    var name: String,
    var imageUrl: Int,
    var imageBackgroundUrl: Int
) {
    var totalDuration: Int = 0

    override fun toString(): String {
        return "Category{" +
                "id=$id" +
                ", name='$name'" +
                ", imageUrl='$imageUrl'" +
                '}'
    }
}