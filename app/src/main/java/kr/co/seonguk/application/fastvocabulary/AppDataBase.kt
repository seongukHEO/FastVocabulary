package kr.co.seonguk.application.fastvocabulary

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Word::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun wordDao():WordDao

    companion object{
        @Volatile
        private var INSTANCE: AppDataBase? = null
        fun getInstance(context: Context):AppDataBase? {
            if (INSTANCE == null){
                synchronized(AppDataBase::class.java){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext, AppDataBase::class.java,
                        "app-database.db"
                    ).build()
                }
            }
            return INSTANCE
        }
    }

}