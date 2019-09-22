package com.example.omdbsearchapisample.RoomDB

import androidx.room.*
import com.example.omdbsearchapisample.model.Search


@Dao
interface BookMarkDao {

    @Query("Select * from bookmark_table")
    fun getAllBookmark(): List<Search>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg todo: Search)

    @Delete
    fun delete(todo: Search)

}