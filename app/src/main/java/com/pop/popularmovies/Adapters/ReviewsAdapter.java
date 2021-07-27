package com.pop.popularmovies.Adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pop.popularmovies.R;
import com.pop.popularmovies.models.MoviesModel.ReviewModel.Result;

import java.util.List;


public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.MoviesViewHolder> {

    private Context mCtx;
    private List<Result> MoviesList;


    public ReviewsAdapter(Context mCtx, List<Result> MoviesList) {
        this.mCtx = mCtx;
        this.MoviesList = MoviesList;
    }
    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_row, parent, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MoviesViewHolder holder, final int position) {


        holder.Authertxt.setText(MoviesList.get(position).getAuthor());
        holder.Reviewtxt.setText(MoviesList.get(position).getContent());
        holder.ReviewLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watchContent(v.getContext(),MoviesList.get(position).getUrl());
            }
        });
    }
    public static void watchContent(Context context, String Url){
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Url));
        try {
            context.startActivity(webIntent);
        } catch (ActivityNotFoundException ex) {
        }
    }

    @Override
    public int getItemCount() {
        return MoviesList.size();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder {
        TextView Reviewtxt,Authertxt;
        LinearLayout ReviewLin;

        public MoviesViewHolder(View itemview) {
            super(itemview);

            Authertxt = itemView.findViewById(R.id.authertxt);
            Reviewtxt = itemView.findViewById(R.id.reviewtxt);
            ReviewLin= itemView.findViewById(R.id.review_lin);
        }
    }
}
