package com.example.sjr_pringles.Project.Home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sjr_pringles.R
class OnBoardingAdapter(private val items: List<OnBoardItem>) :
    RecyclerView.Adapter<OnBoardingAdapter.OnBoardViewHolder>() {

    class OnBoardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivIcon: ImageView = view.findViewById(R.id.ivSlideIcon)
        val tvTitle: TextView = view.findViewById(R.id.tvSlideTitle)
        val tvDesc: TextView = view.findViewById(R.id.tvSlideDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_onboarding, parent, false)
        return OnBoardViewHolder(view)
    }

    override fun onBindViewHolder(holder: OnBoardViewHolder, position: Int) {
        val item = items[position]
        holder.tvTitle.text = item.title
        holder.tvDesc.text = item.desc
        holder.ivIcon.setImageResource(item.iconRes)
    }

    override fun getItemCount(): Int = items.size
}