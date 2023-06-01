package com.example.roomdatabase

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.roomdatabase.database.NotesDatabase
import com.example.roomdatabase.databinding.ActivityAddNotesBinding
import com.example.roomdatabase.model.NotesData
import com.example.roomdatabase.viewmodel.AddViewModel


class AddNotes : AppCompatActivity() {
    var binding: ActivityAddNotesBinding? = null
    lateinit var viewmodel: AddViewModel
    lateinit var notesDb: NotesDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNotesBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        viewmodel = ViewModelProvider(this).get(AddViewModel::class.java)
        clicklistener()
    }

    private fun clicklistener() {
        binding?.ivBack?.setOnClickListener {
            super.onBackPressed()
        }

        binding?.ivDone?.setOnClickListener {
            if (binding?.edTitle?.text.isNullOrEmpty()) {
                binding?.edTitle?.error = "please enter title"
            } else if (binding?.edMessage?.text.isNullOrEmpty()) {
                binding?.edMessage?.error = "Please enter message"
            } else {
                viewmodel.addData(
                    this,
                    NotesData(
                        null,
                        binding?.edTitle?.text.toString(),
                        binding?.edMessage?.text.toString()
                    )
                )
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }
}