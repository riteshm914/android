package com.demo.mvvm.app.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.demo.mvvm.app.R
import com.demo.mvvm.app.data.model.Rows
import kotlinx.android.synthetic.main.item_layout.view.*

class MainAdapter(
    private var factModels: List<Rows>
) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(facts: Rows) {
            itemView.tv_factTitle.text = facts.title
            itemView.tv_factDescription.text = facts.description
            Glide.with(itemView.iv_fact.context)
                .load(facts.imageHref)
                .into(itemView.iv_fact)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = factModels.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(factModels[position])

    fun addData(list: List<Rows>) {
        factModels = list
    }
}