package com.firmfreez.android.roomwordsample.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Word::class], version = 1, exportSchema = false)
public abstract class WordRoomDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao

    private class WordDatabaseCallback(val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANSE?.let { database ->
                scope.launch {
                    populateDatabase(database.wordDao())
                }
            }
        }

        suspend fun populateDatabase(wordDao: WordDao) {
            wordDao.removeAll()

            //Добавляем простые слова
            var word = Word("Hello")
            wordDao.insert(word)
            word = Word("world!")
            wordDao.insert(word)
        }
    }

    companion object {
        //Синглтон объект базы
        @Volatile //Значит, что это поле будет видно даже в разных потоках
        private var INSTANSE: WordRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope) : WordRoomDatabase {
            var tempInstance = INSTANSE
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "word_database"
                ).addCallback(WordDatabaseCallback(scope)).build()
                INSTANSE = instance
                return instance
            }
        }
    }
}