package com.cc.randomuserapimvp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.URLUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cc.randomuserapimvp.databinding.ItemLayoutBinding
import com.cc.randomuserapimvp.model.APIData
import com.cc.randomuserapimvp.util.LogToConsole
import java.net.URL

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.UserViewHolder>() {

    private var list:List<com.cc.randomuserapimvp.model.Result> = emptyList()

    class UserViewHolder(binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        val firstName = binding.firstName
        val lastName = binding.lastName
        val picture = binding.imageView

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val data = list[position]
        LogToConsole.log("data first : ${data.name.first}")
        LogToConsole.log("data first : ${data.name.last}")

        LogToConsole.log("data first : ${data.picture.medium}")

        holder.firstName.text = data.name.first
        holder.lastName.text = data.name.last

        //if (URLUtil.isValidUrl(data.picture.medium))
        Glide.with(holder.itemView.context)
            .load(data.picture.medium)
            .into(holder.picture)
    }

    fun update (data : APIData) {
        LogToConsole.log("adapter, update")
        this.list = data.results
        notifyDataSetChanged()
    }
}