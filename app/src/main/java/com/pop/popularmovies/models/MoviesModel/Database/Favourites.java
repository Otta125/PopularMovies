package com.pop.popularmovies.models.MoviesModel.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.pop.popularmovies.models.MoviesModel.Result;

@Entity(tableName = "favourites")
public class Favourites {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int film_id;
    private Result MoviesList;

    @Ignore
    public Favourites(int film_id,Result MoviesList) {
        this.film_id = film_id;
        this.MoviesList = MoviesList;
    }

    public Favourites(int id, int film_id, Result MoviesList) {
        this.id = id;
        this.film_id = film_id;
        this.MoviesList = MoviesList;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFilm_id() {
        return film_id;
    }

    public void setFilm_id(int film_id) {
        this.film_id = film_id;
    }

    public Result getMoviesList() {
        return MoviesList;
    }

    public void setMoviesList(Result moviesList) {
        MoviesList = moviesList;
    }
}
