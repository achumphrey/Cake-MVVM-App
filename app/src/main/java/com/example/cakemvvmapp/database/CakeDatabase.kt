package com.example.cakemvvmapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cakemvvmapp.model.CakeModel

@Database(entities = arrayOf(CakeModel::class), version = 1,exportSchema = false)
abstract class CakeDatabase: RoomDatabase() {

    abstract fun cakeDao():CakeDAO

    companion object{

        @Volatile
        private var INSTANCE:CakeDatabase? = null

        fun getDatabase(context: Context):CakeDatabase?{
            val tempInstance = INSTANCE
            if (INSTANCE!=null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,
                    CakeDatabase::class.java,"cake_database")
                    .build()

                INSTANCE = instance
            }
            return INSTANCE
        }
    }
}