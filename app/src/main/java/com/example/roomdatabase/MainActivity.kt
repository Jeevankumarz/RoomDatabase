package com.example.roomdatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.roomdatabase.adapter.NotesAdapter
import com.example.roomdatabase.databinding.ActivityAddNotesBinding
import com.example.roomdatabase.databinding.ActivityMainBinding
import com.example.roomdatabase.model.NotesData
import com.example.roomdatabase.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers.Main

class MainActivity : AppCompatActivity() {
    var binding : ActivityMainBinding? = null
    var viewModel: MainViewModel?= null
    lateinit var notesList : ArrayList<NotesData>
    lateinit var notesAdapter: NotesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding?.rvRecords?.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)


        viewModel?.getNotesData(this)
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
        if(filterList.isNotEmpty()){
            notesAdapter.filterData(filterList)
        }
        else{
            //Toast.makeText(this, "No record Found", Toast.LENGTH_SHORT).show()
            binding?.searchCancle?.visibility = View.GONE
        }
    }

    private fun clickListener() {
        binding?.fabAdd?.setOnClickListener {
            intentResultLuncher.launch(Intent(this, AddNotes::class.java))
            //helper.createNote(model)
        }
        binding?.edSearch?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(p0.isNullOrEmpty()){
                    binding?.searchCancle?.visibility = View.GONE
                }
                else{
                    binding?.searchCancle?.visibility = View.VISIBLE
                    filterNotes(p0.toString())
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

    }

    private fun observer() {
        viewModel?.model?.observe(this) {
            it.forEach { Log.d("data", "Data ${it.id}") }
            notesList = it
            notesAdapter = NotesAdapter(this, notesList)
            binding?.rvRecords?.adapter = notesAdapter

        }
    }

    val intentResultLuncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {

            }
        }
}