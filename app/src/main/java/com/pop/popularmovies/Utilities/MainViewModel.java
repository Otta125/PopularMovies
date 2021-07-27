package com.pop.popularmovies.Utilities;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.pop.popularmovies.models.MoviesModel.Database.AppDatabase;
import com.pop.popularmovies.models.MoviesModel.Database.Favourites;

import java.util.List;

// COMPLETED (1) make this class extend AndroidViewModel and implement its default constructor
public class MainViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = MainViewModel.class.getSimpleName();

    // COMPLETED (2) Add a tasks member variable for a list of TaskEntry objects wrapped in a LiveData
    LiveData<List<Favourites>> Favs;


    public MainViewModel(Application application) {
        super(application);
        // COMPLETED (4) In the constructor use the loadAllTasks of the taskDao to initialize the tasks variable
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the DataBase");
        Favs = database.taskDao().getAllFavourite();
    }

    // COMPLETED (3) Create a getter for the tasks variable
    public LiveData<List<Favourites>> getFavs() {
        return Favs;
    }
}
