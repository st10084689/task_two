package com.example.poe_task_2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CategoryRecyclerAdapter(private val context: Context) : RecyclerView.Adapter<CategoryRecyclerAdapter.ViewHolder>() {
    private val categories: ArrayList<Category> = ArrayList()
    private val tasks: ArrayList<Task> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listitem_category_rec_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.CategoryText.text = categories[position].name

        getCategoryDuration()

        holder.categoryCard.setOnClickListener { view ->
            showCustomToast("Duration:" + categories[position].totalDuration, view)
        }

        Glide.with(context)
            .asBitmap()
            .load(categories[position].imageUrl)
            .into(holder.CategoryImage)

        holder.CategoryImage.setBackgroundResource(categories[position].imageBackgroundUrl)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val CategoryImage: ImageView = itemView.findViewById(R.id.backgroundImage)
        val CategoryText: TextView = itemView.findViewById(R.id.categoryName)
        val categoryCard: CardView = itemView.findViewById(R.id.CategoryCard)
    }

    fun setCategories(categories: ArrayList<Category>, tasks: ArrayList<Task>) {
        this.categories.clear()
        this.categories.addAll(categories)
        this.tasks.clear()
        this.tasks.addAll(tasks)
        notifyDataSetChanged()
    }

    private fun showCustomToast(message: String, view: View) {
        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(R.layout.toast_custom, view.findViewById(R.id.toast_custom_layout))

        val toastText = layout.findViewById<TextView>(R.id.message)
        toastText.text = message

        val toast = Toast(context)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()
    }

    private fun getCategoryDuration() {
        for (category in categories) {
            val categoryId = category.id
            var totalDuration = 0
            for (task in tasks) {
                if (task.categoryId == categoryId) {
                    totalDuration += task.duration
                }
            }
            category.totalDuration = totalDuration
        }
    }
}