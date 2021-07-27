package com.pop.popularmovies.models.MoviesModel.Database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

// COMPLETED (5) Make this class extend ViewModel
public class AddTaskViewModel extends ViewModel {

    // COMPLETED (6) Add a task member variable for the TaskEntry object wrapped in a LiveData
    private LiveData<Favourites> Favs;

    // COMPLETED (8) Create a constructor where you call loadTaskById of the taskDao to initialize the tasks variable
    // Note: The constructor should receive the database and the taskId
    public AddTaskViewModel(AppDatabase database, int favId) {
        Favs = database.taskDao().loadFavById(favId);
    }

    // COMPLETED (7) Create a getter for the task variable
    public LiveData<Favourites> getFav() {
        return Favs;
    }
}
