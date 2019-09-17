package com.example.cakemvvmapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cakemvvmapp.model.CakeModel
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface CakeDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCake(cake: List<CakeModel>): Completable

    @Query("Select * from cake_table")
    fun getAllCakes(): Flowable<List<CakeModel>>

    @Query("select * from cake_table where title = :title")
    fun getByTitle(title:String): Flowable<CakeModel>

    @Query("delete  from cake_table")
    fun deleteTable(): Completable

}