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
import com.pop.popularmovies.models.MoviesModel.VideosModel.Result;

import java.util.List;


public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.MoviesViewHolder> {

    private Context mCtx;
    private List<Result> MoviesList;


    public VideosAdapter(Context mCtx, List<Result> MoviesList) {
        this.mCtx = mCtx;
        this.MoviesList = MoviesList;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_row, parent, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MoviesViewHolder holder, final int position) {


        holder.Videotxt.setText(MoviesList.get(position).getName());
        holder.VideoLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watchVideo(v.getContext(),MoviesList.get(position).getKey());
            }
        });
    }
    public static void watchVideo(Context context, String id){
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + id));
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));

        try {
            context.startActivity(webIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(appIntent);
        }
    }

    @Override
    public int getItemCount() {
        return MoviesList.size();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder {
        TextView Videotxt;
        LinearLayout VideoLin;

        public MoviesViewHolder(View itemview) {
            super(itemview);
            Videotxt = itemView.findViewById(R.id.videotxt);
            VideoLin = itemView.findViewById(R.id.video_lin);
        }
    }
}
