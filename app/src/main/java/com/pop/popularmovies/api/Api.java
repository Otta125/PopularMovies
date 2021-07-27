package com.pop.popularmovies.api;


import com.pop.popularmovies.BuildConfig;
import com.pop.popularmovies.models.MoviesModel.MoviesResponse;
import com.pop.popularmovies.models.MoviesModel.ReviewModel.ReviewResponse;
import com.pop.popularmovies.models.MoviesModel.VideosModel.VideoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {

    String API_KEY = BuildConfig.MY_TOKEN;
    @GET("popular?api_key="+API_KEY)
    Call<MoviesResponse> MoviesApi();

    @GET("top_rated?api_key="+API_KEY)
    Call<MoviesResponse> MoviesRatedApi();


    @GET("{id}/videos?api_key="+API_KEY)
    Call<VideoResponse> MoviesVideosApi(@Path("id") String id);

    @GET("{id}/reviews?api_key="+API_KEY)
    Call<ReviewResponse> MoviesReviewApi(@Path("id") String id);
}

