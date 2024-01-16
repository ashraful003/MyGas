package com.example.mygas.presentation.login

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.mygas.R

class ViewPageAdapter(private val views:List<View>):
    RecyclerView.Adapter<ViewPageAdapter.ViewPagerViewHolder>() {

    class ViewPagerViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val container:FrameLayout = itemView.findViewById(R.id.view_container)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.login_landing_items,parent,false)
        return ViewPagerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return views.size
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.container.addView(views[position])
    }
}