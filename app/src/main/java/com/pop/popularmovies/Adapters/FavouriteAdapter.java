package com.pop.popularmovies.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pop.popularmovies.R;
import com.pop.popularmovies.models.MoviesModel.Database.Favourites;

import java.util.List;


public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.MoviesViewHolder> {

    private Context mCtx;
    private List<Favourites> MoviesList;


    public FavouriteAdapter(Context mCtx, List<Favourites> MoviesList) {
        this.mCtx = mCtx;
        this.MoviesList = MoviesList;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_row, parent, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MoviesViewHolder holder, int position) {

        holder.name.setText(MoviesList.get(position).getMoviesList().getTitle());
    }


    @Override
    public int getItemCount() {
        return MoviesList.size();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public MoviesViewHolder(View itemview) {
            super(itemview);
            name = itemView.findViewById(R.id.movie_name);
        }
    }
}
