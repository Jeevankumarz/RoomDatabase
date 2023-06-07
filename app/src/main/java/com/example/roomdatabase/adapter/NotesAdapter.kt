package com.example.roomdatabase.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.adapter.NotesAdapter.ClickListener.Companion.LONG_PRESS
import com.example.roomdatabase.adapter.NotesAdapter.ClickListener.Companion.NORMAL
import com.example.roomdatabase.adapter.NotesAdapter.ClickListener.Companion.SELCETCLICK
import com.example.roomdatabase.databinding.ItemViewBinding
import com.example.roomdatabase.model.NotesData

class NotesAdapter(
    val context: Context,
    var list: ArrayList<NotesData>,
    val clickListener: ClickListener
) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    var showAll = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemViewBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }
    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var model = list[position]
        holder.binding.apply {
            title.text = model.title
            message.text = model.message

            if (showAll) {
                select.visibility = View.VISIBLE
                select.isChecked = model.check
            } else {
                select.visibility = View.GONE
            }
            itemView.setOnClickListener{
                clickListener.noteSelection(NORMAL, position)
            }
            itemView.setOnLongClickListener {
                //clickListener.showALlCheckBox()
                showAll = true
                list[position].check = true
                notifyDataSetChanged()
                clickListener.noteSelection(LONG_PRESS, position)
                true
            }
            select.setOnClickListener {
                if (model.check) {
                    select.isChecked = false
                    model.check = false

                } else {
                    select.isChecked = true
                    model.check = true
                }
                clickListener.noteSelection(SELCETCLICK, position)

                /*val checkList = list.filter {
                    it.check
                }
                if (checkList.isEmpty()) {
                    showAll= false
                    notifyDataSetChanged()
                }*/
            }
        }
    }


    fun filterData(notesList: ArrayList<NotesData>) {
        list = notesList
        notifyDataSetChanged()
    }

    class ViewHolder(itemViewHolder: ItemViewBinding) :
        RecyclerView.ViewHolder(itemViewHolder.root) {
        val binding = itemViewHolder
    }

    interface ClickListener {
        companion object{
            const val NORMAL = 1
            const val LONG_PRESS = 2
            const val SELCETCLICK = 3
        }
        fun showALlCheckBox()
        fun noteSelection(click : Int, position: Int)
    }

}