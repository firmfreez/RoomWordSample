package com.firmfreez.android.roomwordsample.view.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.firmfreez.android.roomwordsample.repository.WordRepository
import com.firmfreez.android.roomwordsample.db.Word
import com.firmfreez.android.roomwordsample.db.WordRoomDatabase
import kotlinx.coroutines.launch

class WordViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: WordRepository
    //Использование LiveData позволяет:
    // - Получать актуальные данные по мере их обновления (обсёрвить вью)
    // - Разделить логику работы от отображения
    val allWords: LiveData<List<Word>>

    init {
        val wordDao = WordRoomDatabase.getDatabase(application, viewModelScope).wordDao()
        repository = WordRepository(wordDao)
        allWords = repository.allWords
    }

    //Запускаем корутину для вставки элемента
    fun insert(word: Word) = viewModelScope.launch {
        repository.insert(word)
    }
}