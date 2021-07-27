package com.pop.popularmovies.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pop.popularmovies.Activities.DetailsActivity;
import com.pop.popularmovies.R;
import com.pop.popularmovies.models.MoviesModel.Result;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private Context mCtx;
    private List<Result> MoviesList;


    public MoviesAdapter(Context mCtx, List<Result> MoviesList) {
        this.mCtx = mCtx;
        this.MoviesList = MoviesList;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_row, parent, false);
        /*ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = (int) (parent.getWidth() * 0.5);
        layoutParams.height = (int) (parent.getHeight() * 0.5);*/
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MoviesViewHolder holder, int position) {


         Picasso.with(mCtx)
                .load("http://image.tmdb.org/t/p/w185"+MoviesList.get(position).getPosterPath()).fit()
                .into(holder.img);

       holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mCtx, DetailsActivity.class);
                intent.putExtra("movie_details",(Parcelable) MoviesList.get(holder.getAdapterPosition()));
                mCtx.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return MoviesList.size();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder {
        ImageView img;

        public MoviesViewHolder(View itemview) {
            super(itemview);
            img = itemView.findViewById(R.id.movie_img);
        }
    }
}
