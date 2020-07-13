package dev.ashish.newsapp.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import dev.ashish.newsapp.R;
import dev.ashish.newsapp.adaptor.CategoriesAdaptor;
import dev.ashish.newsapp.model.Categories;
import dev.ashish.newsapp.utils.ReminderUtilities;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity {
    List<Categories> categoriesList;
    RecyclerView categoriesRecyclerView;
    CategoriesAdaptor categoriesAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        //instantiate RecyclerView
        categoriesRecyclerView = findViewById(R.id.categoriesRecyclerView);
        //creating a new list
        categoriesList = new ArrayList<>();
        loadCategories();
        categoriesAdaptor = new CategoriesAdaptor(this, categoriesList);
        categoriesRecyclerView.setAdapter(categoriesAdaptor);
        //setting the layout of RecyclerView as Grid
        categoriesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        ReminderUtilities.scheduleNotificationReminder(this);
    }

    private void loadCategories() {
        //adding all the categories of news in the list
        categoriesList.add(new Categories(R.drawable.top_news, "Top Headlines", "top_news"));
        categoriesList.add(new Categories(R.drawable.health_news, "Health", "health"));
        categoriesList.add(new Categories(R.drawable.entertainment_news, "Entertainment", "entertainment"));
        categoriesList.add(new Categories(R.drawable.sports_news, "Sports", "sports"));
        categoriesList.add(new Categories(R.drawable.business_news, "Business", "business"));
        categoriesList.add(new Categories(R.drawable.tech_news, "Technology", "technology"));
        categoriesList.add(new Categories(R.drawable.science_news, "Science", "science"));
        categoriesList.add(new Categories(R.drawable.politics_news, "Politics", "politics"));


    }
}
