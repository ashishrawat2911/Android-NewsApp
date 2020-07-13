package dev.ashish.newsapp.adaptor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import dev.ashish.newsapp.R
import dev.ashish.newsapp.activities.NewsListActivity
import dev.ashish.newsapp.model.Categories
import dev.ashish.newsapp.utils.Constants

class CategoriesAdaptor(private val ctx: Context, private val categoriesList: ArrayList<Categories>) : RecyclerView.Adapter<CategoriesAdaptor.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v: View
        v = LayoutInflater.from(ctx).inflate(R.layout.item_catagories, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.categoriesImage.setImageResource(categoriesList[position].image)
        holder.categoriesTitle.text = categoriesList[position].title
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var categoriesTitle: TextView
        var categoriesImage: ImageView
        var cardView: CardView

        init {
            categoriesTitle = itemView.findViewById(R.id.catItemTextView)
            categoriesImage = itemView.findViewById(R.id.catItemImageView)
            cardView = itemView.findViewById(R.id.catCardView)
            cardView.setOnClickListener {
                val i = Intent(ctx, NewsListActivity::class.java)
                i.putExtra(Constants.NEWS_TYPE, categoriesList[adapterPosition].newsType)
                ctx.startActivity(i)
            }
        }
    }

}