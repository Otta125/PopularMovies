package com.pop.popularmovies.Activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pop.popularmovies.Adapters.ReviewsAdapter;
import com.pop.popularmovies.Adapters.VideosAdapter;
import com.pop.popularmovies.R;
import com.pop.popularmovies.Utilities.Constants;
import com.pop.popularmovies.api.RetrofitClient;
import com.pop.popularmovies.models.MoviesModel.Database.AppDatabase;
import com.pop.popularmovies.models.MoviesModel.Database.AppExecutors;
import com.pop.popularmovies.models.MoviesModel.Database.Favourites;
import com.pop.popularmovies.models.MoviesModel.Result;
import com.pop.popularmovies.models.MoviesModel.ReviewModel.ReviewResponse;
import com.pop.popularmovies.models.MoviesModel.VideosModel.VideoResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {

    ImageView poster_img, back_img, like_img;
    TextView name_txt, year_txt, time_txt, rate_txt, desc_txt;
    Context mCtx;
    Result result;
    private AppDatabase mDb;
    RecyclerView TrailerRecycler, ReviewRecycler;
    private List<com.pop.popularmovies.models.MoviesModel.VideosModel.Result> VideosList;
    private List<com.pop.popularmovies.models.MoviesModel.ReviewModel.Result> ReiewsList;
    private VideosAdapter videosAdapter;
    private ReviewsAdapter reviewsAdapter;

    boolean Exists = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mCtx = this;
        poster_img = findViewById(R.id.poster);
        back_img = findViewById(R.id.back);
        like_img = findViewById(R.id.like);
        TrailerRecycler = findViewById(R.id.trailers);
        TrailerRecycler.setLayoutManager(new LinearLayoutManager(mCtx));
        ReviewRecycler = findViewById(R.id.reviews);
        ReviewRecycler.setLayoutManager(new LinearLayoutManager(mCtx));
        TrailerRecycler.setNestedScrollingEnabled(true);
        ReviewRecycler.setNestedScrollingEnabled(true);
        mDb = AppDatabase.getInstance(getApplicationContext());

        name_txt = findViewById(R.id.name);
        year_txt = findViewById(R.id.year);
        time_txt = findViewById(R.id.time);
        rate_txt = findViewById(R.id.rate);
        desc_txt = findViewById(R.id.details);
        /////////////////
        Intent i = getIntent();
        if (i.hasExtra(Constants.MOVIES_DETAILS)) {
            result = i.getParcelableExtra(Constants.MOVIES_DETAILS);

            Picasso.with(mCtx)
                    .load(Constants.IMAGE_URL + result.getPosterPath()).fit()
                    .into(poster_img);

            name_txt.setText(result.getTitle());
            year_txt.setText(result.getReleaseDate());
            rate_txt.setText(String.valueOf(result.getVoteAverage()));
            desc_txt.setText(result.getOverview());
        } else {
            Toast.makeText(mCtx, "There is no data", Toast.LENGTH_SHORT).show();
        }
        //////////

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        like_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Exists) {
                    like_img.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
                    final LiveData<Favourites> Fav = mDb.taskDao().loadFavById(result.getId());
                    Fav.observe(DetailsActivity.this, new Observer<Favourites>() {
                        @Override
                        public void onChanged(@Nullable final Favourites favourites) {
                            Fav.removeObserver(this);
                            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                @Override
                                public void run() {
                                    mDb.taskDao().deleteTask(favourites);
                                }
                            });
                        }
                    });

                } else {
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            Favourites favourites = new Favourites(result.getId(), result);
                            mDb.taskDao().insertFav(favourites);
                        }
                    });
                    Exists = true;
                    like_img.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
                }
            }
        });
        GetVideos(String.valueOf(result.getId()));
        GetReviews(String.valueOf(result.getId()));
        CheckExistFav(result.getId());
    }

    public void GetVideos(String ID) {
        ///////
        Call<VideoResponse> call = RetrofitClient.getInstance().getApi().MoviesVideosApi(ID);
        call.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().getResults() != null) {
                        if (!response.body().getResults().isEmpty()) {
                            VideosList = response.body().getResults();
                            videosAdapter = new VideosAdapter(mCtx, response.body().getResults());
                            TrailerRecycler.setAdapter(videosAdapter);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {
                Log.e("error_retrofit", call.toString());
                //progressBar.setVisibility(View.GONE);
                Toast.makeText(DetailsActivity.this, "there is error check yor internet please", Toast.LENGTH_SHORT).show();

                t.printStackTrace();
            }
        });
    }

    public void GetReviews(String ID) {
        ///////
        Call<ReviewResponse> call = RetrofitClient.getInstance().getApi().MoviesReviewApi(ID);
        call.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().getResults() != null) {
                        if (!response.body().getResults().isEmpty()) {
                            ReiewsList = response.body().getResults();
                            reviewsAdapter = new ReviewsAdapter(mCtx, response.body().getResults());
                            ReviewRecycler.setAdapter(reviewsAdapter);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Log.e("error_retrofit", call.toString());
                //progressBar.setVisibility(View.GONE);
                Toast.makeText(DetailsActivity.this, "there is error check yor internet please", Toast.LENGTH_SHORT).show();

                t.printStackTrace();
            }
        });
    }

    public void CheckExistFav(int ID) {
        final LiveData<Favourites> Fav = mDb.taskDao().loadFavById(ID);
        Fav.observe(this, new Observer<Favourites>() {
            @Override
            public void onChanged(@Nullable Favourites favourites) {
                Fav.removeObserver(this);
                populateUI(favourites);
            }
        });
    }

    private void populateUI(Favourites Fav) {
        if (Fav != null) {
            Exists = true;
            like_img.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
        } else {
            Exists = false;
            like_img.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
        }
    }

}
