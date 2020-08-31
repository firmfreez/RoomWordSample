package com.firmfreez.android.roomwordsample.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firmfreez.android.roomwordsample.R
import com.firmfreez.android.roomwordsample.db.Word

class WordListAdapter internal constructor(context: Context): RecyclerView.Adapter<WordListAdapter.WordListViewHolder>() {
    val inflater: LayoutInflater = LayoutInflater.from(context)
    private var words = emptyList<Word>() //Хранит копию Words

    inner class WordListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordListViewHolder {
        val itemView= inflater.inflate(R.layout.item_recyclerview, parent, false)
        return WordListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordListViewHolder, position: Int) {
        val word = words[position].word
        holder.wordItemView.text = word
    }

    override fun getItemCount(): Int {
        return words.size
    }

    internal fun setWords(list: List<Word>) {
        this.words = list
        notifyDataSetChanged()
    }
}