package com.example.roomdatabase.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.databinding.ItemViewBinding
import com.example.roomdatabase.model.NotesData

class NotesAdapter(val context: Context, var list: ArrayList<NotesData>):
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemViewBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]
        holder.binding.apply {
            title.text = model.title
            message.text = model.message
        }
    }

    fun filterData(notesList: ArrayList<NotesData>) {
        list = notesList
        notifyDataSetChanged()
    }

    class ViewHolder(itemViewHolder: ItemViewBinding) : RecyclerView.ViewHolder(itemViewHolder.root)
    {
        val binding = itemViewHolder
    }

}