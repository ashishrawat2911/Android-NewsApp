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
import com.bumptech.glide.Glide
import dev.ashish.newsapp.R
import dev.ashish.newsapp.activities.DetailsActivity
import dev.ashish.newsapp.model.News.Article
import dev.ashish.newsapp.utils.Constants

class NewsAdaptor(private val ctx: Context, private val newsType: String?) : RecyclerView.Adapter<NewsAdaptor.MyViewHolder>() {
    private var newsList: List<Article>? = null
    fun setNewsList(newsList: List<Article>?) {
        this.newsList = newsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v: View
        v = LayoutInflater.from(ctx).inflate(R.layout.news_list_item, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.title.text = newsList!![position].title
        if (newsList!![position].urlToImage == null) {
            holder.displayImage.setImageResource(R.drawable.no_image_available)
        } else Glide.with(ctx)
                .asBitmap()
                .load(newsList!![position].urlToImage)
                .into(holder.displayImage)
    }

    override fun getItemCount(): Int {
        return if (newsList == null) 0 else newsList!!.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView
        var displayImage: ImageView
        var cardView: CardView

        init {
            title = itemView.findViewById(R.id.itemTextView)
            displayImage = itemView.findViewById(R.id.itemImageView)
            cardView = itemView.findViewById(R.id.listCardView)
            cardView.setOnClickListener {
                val i = Intent(ctx, DetailsActivity::class.java)
                i.putExtra(Constants.NEWS_TYPE, newsType)
                i.putExtra(Constants.POSITION, adapterPosition)
                ctx.startActivity(i)
            }
        }
    }

}