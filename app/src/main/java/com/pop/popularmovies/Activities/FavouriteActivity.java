package com.pop.popularmovies.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pop.popularmovies.Adapters.FavouriteAdapter;
import com.pop.popularmovies.R;
import com.pop.popularmovies.Utilities.MainViewModel;
import com.pop.popularmovies.models.MoviesModel.Database.Favourites;

import java.util.List;

public class FavouriteActivity extends AppCompatActivity {

    RecyclerView FavRecycler;
    private FavouriteAdapter favouriteAdapter;
    TextView Title;
    ImageView Backimg;
    Context mCx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        mCx = this;
        FavRecycler = findViewById(R.id.fav_recycler);
        Title =findViewById(R.id.toolbar_title);
        Backimg =findViewById(R.id.back);
        FavRecycler.setLayoutManager(new LinearLayoutManager(mCx));
        Title.setText(R.string.favourite);

        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getFavs().observe(this, new Observer<List<Favourites>>() {
            @Override
            public void onChanged(@Nullable List<Favourites> favourites) {
                favouriteAdapter =new FavouriteAdapter(mCx,favourites);
                FavRecycler.setAdapter(favouriteAdapter);
            }
        });
        Backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
