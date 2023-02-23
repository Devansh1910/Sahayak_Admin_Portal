package com.devanshsaxena.onestoreadmin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devanshsaxena.onestoreadmin.R
import com.devanshsaxena.onestoreadmin.databinding.ItemCategoryLayoutBinding
import com.devanshsaxena.onestoreadmin.model.CategoryModel

class CategoryAdapter (var context : Context, private val list : ArrayList<CategoryModel>) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(view : View) : RecyclerView.ViewHolder(view){
        var binding = ItemCategoryLayoutBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.item_category_layout,parent,false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.binding.textView2.text = list[position].img
        Glide.with(context).load(list[position].cate).into(holder.binding.imageView2)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}