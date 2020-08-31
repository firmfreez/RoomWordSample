package com.firmfreez.android.roomwordsample.repository

import androidx.lifecycle.LiveData
import com.firmfreez.android.roomwordsample.db.Word
import com.firmfreez.android.roomwordsample.db.WordDao

//Класс-репозиторий, обеспечивающий доступ к данныи
//Достаточно передать ему Dao объект (а не базу), потому что для запросов его хватает
class WordRepository(private val wordDao: WordDao) {
    //Возвращает liveData, которую достаточно просто заобсёрвить на view
    val allWords: LiveData<List<Word>> = wordDao.getAlphabetizeWords()

    //Позволяет вставить элемент в базу
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }
}