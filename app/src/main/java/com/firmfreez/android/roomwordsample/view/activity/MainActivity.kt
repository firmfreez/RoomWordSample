package com.firmfreez.android.roomwordsample.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firmfreez.android.roomwordsample.R
import com.firmfreez.android.roomwordsample.db.Word
import com.firmfreez.android.roomwordsample.view.adapters.WordListAdapter
import com.firmfreez.android.roomwordsample.view.viewModels.WordViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: WordViewModel
    private val newWordActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(WordViewModel::class.java)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = WordListAdapter(this)

        viewModel.allWords.observe(this) {
            (recyclerView.adapter as? WordListAdapter)?.setWords(it)
        }

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, NewWordActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            newWordActivityRequestCode -> {
                when (resultCode) {
                    RESULT_OK -> {
                        val extraString = data?.getStringExtra(NewWordActivity.EXTRA_REPLY)
                        val word = extraString?.let { Word(it) }
                        word?.let { viewModel.insert(it) }
                    }
                    else -> {
                        Toast.makeText(this, R.string.empty_not_saved, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}