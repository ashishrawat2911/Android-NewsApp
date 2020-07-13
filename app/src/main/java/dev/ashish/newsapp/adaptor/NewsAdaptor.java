package dev.ashish.newsapp.adaptor;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import dev.ashish.newsapp.R;
import dev.ashish.newsapp.activities.DetailsActivity;
import dev.ashish.newsapp.model.News;
import dev.ashish.newsapp.utils.Constants;
import com.bumptech.glide.Glide;

import java.util.List;

public class NewsAdaptor extends RecyclerView.Adapter<NewsAdaptor.MyViewHolder> {
    private Context ctx;
    private List<News.Article> newsList;
    private String newsType;

    public NewsAdaptor(Context ctx, String newsType) {
        this.ctx = ctx;

        this.newsType = newsType;

    }

    public void setNewsList(List<News.Article> newsList) {
        this.newsList = newsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(ctx).inflate(R.layout.news_list_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(newsList.get(position).getTitle());
        if (newsList.get(position).getUrlToImage() == null) {
            holder.displayImage.setImageResource(R.drawable.no_image_available);

        } else
            Glide.with(ctx)
                    .asBitmap()
                    .load(newsList.get(position).getUrlToImage())
                    .into(holder.displayImage);
    }


    @Override
    public int getItemCount() {
        if (newsList == null)
            return 0;
        else
            return newsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView displayImage;
        CardView cardView;

        MyViewHolder(final View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.itemTextView);
            displayImage = itemView.findViewById(R.id.itemImageView);
            cardView = itemView.findViewById(R.id.listCardView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ctx, DetailsActivity.class);
                    i.putExtra(Constants.NEWS_TYPE, newsType);
                    i.putExtra(Constants.POSITION, getAdapterPosition());
                    ctx.startActivity(i);

                }
            });
        }
    }
}

