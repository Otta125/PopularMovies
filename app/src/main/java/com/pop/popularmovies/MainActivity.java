package com.pop.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pop.popularmovies.Adapters.MoviesAdapter;
import com.pop.popularmovies.Utilities.MainViewModel;
import com.pop.popularmovies.api.RetrofitClient;
import com.pop.popularmovies.models.MoviesModel.Database.Favourites;
import com.pop.popularmovies.models.MoviesModel.MoviesResponse;
import com.pop.popularmovies.models.MoviesModel.Result;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private List<Result> MoviesList;
    private MoviesAdapter moviesAdapter;
    private RecyclerView recyclerViews;

    ProgressBar progressBar;
    Context mContext;

    ImageView SettingToolbar;
    int myCheck, setCheck;
    boolean isConnected;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("MyInt", setCheck);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        recyclerViews = findViewById(R.id.movies_recycler);
        int posterWidth = 250;
        // gridLayoutManager = new GridLayoutManager(this, calculateBestSpanCount(posterWidth));
        recyclerViews.setLayoutManager(new GridLayoutManager(this, calculateBestSpanCount(posterWidth)));
        progressBar = findViewById(R.id.progress_id);
        SettingToolbar = findViewById(R.id.setting);
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        SettingToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(mContext, v);
                popup.inflate(R.menu.pop_menu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.pop:
                                setCheck = 1;
                                getData(1);
                                return true;
                            case R.id.rate:
                                setCheck = 2;
                                getData(2);
                                return true;
                            case R.id.fav:
                                setCheck = 3;
                                getData(3);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.show();
            }
        });

        ///////
        if (savedInstanceState == null) {
            getData(1);
            setCheck = 1;
        } else {
            setCheck = savedInstanceState.getInt("MyInt", 0);
            getData(savedInstanceState.getInt("MyInt", 0));
        }
    }

    public void getData(int type) {
        Call<MoviesResponse> call = null;
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            if (type != 3) {
                if (isConnected) {
                    if (type == 1) {
                        if (isConnected) {
                            call = RetrofitClient.getInstance().getApi().MoviesApi(/*API_KEY*/);
                        } else {
                            Toast.makeText(mContext, R.string.connection, Toast.LENGTH_SHORT).show();
                        }
                    } else if (type == 2) {
                        if (isConnected) {
                            call = RetrofitClient.getInstance().getApi().MoviesRatedApi(/*API_KEY*/);
                        } else {
                            Toast.makeText(mContext, R.string.connection, Toast.LENGTH_SHORT).show();
                        }
                    }

                call.enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {

                        if (response.isSuccessful()) {
                            if (!response.body().getResults().isEmpty()) {
                                MoviesList = response.body().getResults();
                                moviesAdapter = new MoviesAdapter(mContext, MoviesList);
                                recyclerViews.setAdapter(moviesAdapter);
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, R.string.connection, Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });
                }else{
                    Toast.makeText(mContext, R.string.connection, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            } else {
                MainViewModel viewModel = ViewModelProviders.of(MainActivity.this).get(MainViewModel.class);
                viewModel.getFavs().observe(MainActivity.this, new Observer<List<Favourites>>() {
                    @Override
                    public void onChanged(@Nullable List<Favourites> favourites) {
                        MoviesList = new ArrayList<>();
                        MoviesList.clear();
                        for (int i = 0; i < favourites.size(); i++) {
                            MoviesList.add(favourites.get(i).getMoviesList());
                        }
                        moviesAdapter = new MoviesAdapter(mContext, MoviesList);
                        recyclerViews.setAdapter(moviesAdapter);
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
    }

    private int calculateBestSpanCount(int posterWidth) {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float screenWidth = outMetrics.widthPixels;
        return Math.round(screenWidth / posterWidth);
    }
}
