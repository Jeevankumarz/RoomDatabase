package com.example.roomdatabase

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.roomdatabase.adapter.NotesAdapter
import com.example.roomdatabase.databinding.ActivityMainBinding
import com.example.roomdatabase.model.NotesData
import com.example.roomdatabase.viewmodel.MainViewModel
import com.google.gson.Gson

class MainActivity : AppCompatActivity(), NotesAdapter.ClickListener {
    var binding: ActivityMainBinding? = null
    var viewModel: MainViewModel? = null

    lateinit var deleteList: ArrayList<NotesData>
    lateinit var notesAdapter: NotesAdapter
    companion object{
        lateinit var notesList: ArrayList<NotesData>
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding?.rvRecords?.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        viewModel?.getNotesData(this)

        /*var list1 = mutableListOf(11, 12, 13, 14, 15, 16, 17, 18, 19, 20)
        var list2 = mutableListOf(13, 20)

        list2.forEach { itt->
            list1.removeIf{it == itt}
        }
        list1.removeAll(list2)
        Log.d("listtttt", "List : $list1 ")*/
        observer()
        clickListener()
    }
    private fun filterNotes(text: String) {
        val filterList: ArrayList<NotesData> = ArrayList()
        for (item in notesList) {
            if (item.title.contains(text)) {
                filterList.add(item)
            }
        }
        //notesAdapter.filterData(filterList)
        if (filterList.isNotEmpty()) {
            notesAdapter.filterData(filterList)
        } else {
            //Toast.makeText(this, "No record Found", Toast.LENGTH_SHORT).show()
            binding?.searchCancle?.visibility = View.GONE
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun clickListener() {
        binding?.fabAdd?.setOnClickListener {
            intentResultLuncher.launch(Intent(this, AddNotes::class.java))
            //helper.createNote(model)
        }
        binding?.edSearch?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0.isNullOrEmpty()) {
                    binding?.searchCancle?.visibility = View.GONE
                    notesAdapter.filterData(notesList)
                } else {
                    binding?.searchCancle?.visibility = View.VISIBLE
                    filterNotes(p0.toString())
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding?.allCheck?.setOnClickListener {
            notesList.forEach {
                it.check = binding?.allCheck?.isChecked!!
            }
            if(binding?.allCheck?.isChecked!!){
                binding?.tvSelectCount?.text = "${notesList.size} Selected"
            }
            else{
                binding?.tvSelectCount?.text = "0 Selected"
            }
            notesAdapter.notifyDataSetChanged()
        }
        binding?.deleteBtn?.setOnClickListener {
            if (deleteList.isNotEmpty()) {
                viewModel?.deleteNotes(this, deleteList)
                notesList.clear()
                deleteList.clear()
                binding?.rvRecords?.adapter?.notifyDataSetChanged()
                binding?.rvDelete?.visibility = View.GONE
                viewModel?.getNotesData(this)
            }
        }
    }
    override fun onBackPressed() {
        if (binding?.rvDelete?.isVisible!!) {
            binding?.rvDelete?.visibility = View.GONE

            notesList.forEach {
                it.check = false
            }
            notesAdapter.showAll = false
            notesAdapter.notifyDataSetChanged()
            return
        }
        super.onBackPressed()
    }
    private fun observer() {
        viewModel?.model?.observe(this) {
            it.forEach { Log.d("data", "Data ${it.id}") }
            notesList = it
            notesAdapter = NotesAdapter(this, notesList, this)
            binding?.rvRecords?.adapter = notesAdapter
        }
    }

    val intentResultLuncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {

            }
        }
    override fun showALlCheckBox() {

    }
    override fun noteSelection(click: Int, position: Int) {
        Log.d("list", "noteSelection: ${Gson().toJson(notesList)}")
        deleteList = notesList.filter {
            it.check
        } as ArrayList<NotesData>
        if (click==1) {
            val intent = Intent(this, AddNotes::class.java)

            intent.putExtra("id", notesList.get(position).id)
            intent.putExtra("list", position.toString())
            intentResultLuncher.launch(intent)

        } else if (click == 2) {
            binding?.rvDelete?.visibility = View.VISIBLE
        } else if (click == 3) {
            if (!deleteList.isNullOrEmpty()) {
                binding?.tvSelectCount?.text = "${deleteList.size} Selected"
                if (deleteList.size == notesList.size) {
                    binding?.allCheck?.isChecked = true
                }
            } else {
                notesAdapter.showAll = false
                binding?.rvDelete?.visibility = View.GONE
                notesAdapter.notifyDataSetChanged()
            }
        }
    }
}