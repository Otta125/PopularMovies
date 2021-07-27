package com.pop.popularmovies.models.MoviesModel.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FavouriteDao {

    @Query("SELECT * FROM favourites ")
    LiveData<List<Favourites>> getAllFavourite();

    @Insert
    void insertFav(Favourites favourites);

    @Query("SELECT * FROM favourites WHERE film_id = :id")
    LiveData<Favourites> loadFavById(int id);

    @Delete
    void deleteTask(Favourites favourites);

}
