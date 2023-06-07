package com.example.poe_task_2

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat


class ListTaskRecyclerAdapter(private val context: Context) : RecyclerView.Adapter<ListTaskRecyclerAdapter.ViewHolder>() {
    private val TAG = "ListTaskRecyclerAdapter"

    private val tasks = ArrayList<Task>()
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private val categories = ArrayList<Category>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.task_lists_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: called")
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        val dateString = dateFormat.format(tasks[position].endDate)
        holder.name.text = tasks[position].title
        val imagePath = tasks[position].imageUrl
        Log.d(TAG, "onBindViewHolder: "+ imagePath)
        holder.date.text = dateString
        holder.time.text = tasks[position].time

        holder.relativeLayout.setOnClickListener { view ->
            showCustomToast(
                "Description:${tasks[position].desc}\n" +
                        "StartDate:${tasks[position].startDate}\n" +
                        "Duration:${tasks[position].duration}", view
            )
        }
        holder.backgroundImage.background = null
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        val bitmap = BitmapFactory.decodeFile(imagePath, options)

        if (!imagePath.isNullOrEmpty() && tasks[position].id > 2) {

            Glide.with(context)
                .asBitmap()
                .load(bitmap)
                .error(R.drawable.add_me_icon)
                .into(holder.backgroundImage)
        } else {
            Glide.with(context)
                .load(tasks[position].imageUrl)
                .error(R.drawable.add_me_icon)
                .centerCrop()
                .into(holder.backgroundImage)
        }

        val pos = tasks[position].categoryId
        Glide.with(context)
            .load(categories[pos].imageUrl)
            .centerCrop()
            .into(holder.categoryImage)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val backgroundImage: ImageView = itemView.findViewById(R.id.Image)
        val name: TextView = itemView.findViewById(R.id.TaskName)
        val date: TextView = itemView.findViewById(R.id.DateTxt)
        val time: TextView = itemView.findViewById(R.id.TimeTxt)
        val categoryImage: ImageView = itemView.findViewById(R.id.CategoryImage)
        val relativeLayout: RelativeLayout = itemView.findViewById(R.id.relativeCard)
    }

    private fun showCustomToast(message: String, view: View) {
        val layout = inflater.inflate(R.layout.toast_custom, view.findViewById(R.id.toast_custom_layout))
        val toastText = layout.findViewById<TextView>(R.id.message)
        toastText.text = message

        val toast = Toast(context)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()
    }

    fun setTasks(tasks: ArrayList<Task>, categories: ArrayList<Category>) {
        this.tasks.clear()
        this.tasks.addAll(tasks)
        this.categories.clear()
        this.categories.addAll(categories)
        notifyDataSetChanged()
    }
}