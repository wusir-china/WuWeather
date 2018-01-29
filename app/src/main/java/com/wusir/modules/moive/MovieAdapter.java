package com.wusir.modules.moive;

import android.content.Context;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wusir.wuweather.R;

import java.util.List;

/**
 * Created by HaohaoChang on 2017/2/11.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movies;
    private Context context;
    public MovieAdapter(List<Movie> movies,Context context) {
        this.movies = movies;
        this.context=context;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MovieViewHolder viewHolder;
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_movie, parent, false);
        viewHolder=new MovieViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        MovieViewHolder vh=holder;
        Movie movie=movies.get(position);
        Glide.with(context).load(movie.getImages().getSmall()).placeholder(R.drawable.cover).into(vh.cover);
        vh.title.setText(movie.getTitle());
        vh.rating_text.setText(String.valueOf(movie.getRating().getAverage()));
        StringBuilder builder = new StringBuilder();
        for (String s : movie.getGenres()) {
            builder.append(s + " ");
        }
        vh.movie_type_text.setText(builder.toString());
        vh.year_text.setText(movie.getYear());
        vh.ratingBar.setRating(movie.getRating().getAverage());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void addItem(Movie movie) {
        movies.add(movie);
        notifyItemInserted(movies.size() - 1);
    }

    public void clearItems() {
        movies.clear();
        notifyDataSetChanged();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        public ImageView cover;
        public TextView title,rating_text,movie_type_text,year_text;
        public AppCompatRatingBar ratingBar;

        public MovieViewHolder(final View itemView) {
            super(itemView);
            cover= (ImageView) itemView.findViewById(R.id.cover);
            title= (TextView) itemView.findViewById(R.id.title);
            rating_text= (TextView) itemView.findViewById(R.id.rating_text);
            movie_type_text= (TextView) itemView.findViewById(R.id.movie_type_text);
            year_text= (TextView) itemView.findViewById(R.id.year_text);
            ratingBar= (AppCompatRatingBar) itemView.findViewById(R.id.ratingBar);
        }
    }
}
