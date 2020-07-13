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
import dev.ashish.newsapp.activities.NewsListActivity;
import dev.ashish.newsapp.model.Categories;
import dev.ashish.newsapp.utils.Constants;

import java.util.List;

public class CategoriesAdaptor extends RecyclerView.Adapter<CategoriesAdaptor.MyViewHolder> {
    private Context ctx;
    private List<Categories> categoriesList;

    public CategoriesAdaptor(Context ctx, List<Categories> categoriesList) {
        this.ctx = ctx;
        this.categoriesList = categoriesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(ctx).inflate(R.layout.item_catagories, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.categoriesImage.setImageResource(categoriesList.get(position).getImage());
        holder.categoriesTitle.setText(categoriesList.get(position).getTitle());
    }


    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView categoriesTitle;
        ImageView categoriesImage;
        CardView cardView;

        MyViewHolder(View itemView) {
            super(itemView);
            categoriesTitle = itemView.findViewById(R.id.catItemTextView);
            categoriesImage = itemView.findViewById(R.id.catItemImageView);
            cardView = itemView.findViewById(R.id.catCardView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ctx, NewsListActivity.class);
                    i.putExtra(Constants.NEWS_TYPE, categoriesList.get(getAdapterPosition()).getNewsType());
                    ctx.startActivity(i);
                }
            });
        }
    }
}

