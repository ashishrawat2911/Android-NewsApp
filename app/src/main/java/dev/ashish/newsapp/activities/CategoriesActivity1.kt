package dev.ashish.newsapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.ashish.newsapp.R
import dev.ashish.newsapp.adaptor.CategoriesAdaptor
import dev.ashish.newsapp.model.Categories
import dev.ashish.newsapp.utils.ReminderUtilities
import java.util.*

class CategoriesActivity : AppCompatActivity() {
    var categoriesList: MutableList<Categories>? = null
    var categoriesRecyclerView: RecyclerView? = null
    var categoriesAdaptor: CategoriesAdaptor? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)
        //instantiate RecyclerView
        categoriesRecyclerView = findViewById(R.id.categoriesRecyclerView)
        //creating a new list
        categoriesList = ArrayList()
        loadCategories()
        categoriesAdaptor = CategoriesAdaptor(this, categoriesList as ArrayList<Categories>)
        categoriesRecyclerView?.setAdapter(categoriesAdaptor)
        //setting the layout of RecyclerView as Grid
        categoriesRecyclerView?.setLayoutManager(GridLayoutManager(this, 2))
        ReminderUtilities.scheduleNotificationReminder(this)
    }

    private fun loadCategories() {
        //adding all the categories of news in the list
        categoriesList!!.add(Categories(R.drawable.top_news, "Top Headlines", "top_news"))
        categoriesList!!.add(Categories(R.drawable.health_news, "Health", "health"))
        categoriesList!!.add(Categories(R.drawable.entertainment_news, "Entertainment", "entertainment"))
        categoriesList!!.add(Categories(R.drawable.sports_news, "Sports", "sports"))
        categoriesList!!.add(Categories(R.drawable.business_news, "Business", "business"))
        categoriesList!!.add(Categories(R.drawable.tech_news, "Technology", "technology"))
        categoriesList!!.add(Categories(R.drawable.science_news, "Science", "science"))
        categoriesList!!.add(Categories(R.drawable.politics_news, "Politics", "politics"))
    }
}