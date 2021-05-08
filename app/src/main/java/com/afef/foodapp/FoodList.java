package com.afef.foodapp;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afef.foodapp.Interface.ItemClickListener;
import com.afef.foodapp.Model.Category;
import com.afef.foodapp.ViewHolder.MenuViewHolder;
import com.afef.foodapp.common.Common;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import android.os.Bundle;

import com.afef.foodapp.Model.Food;
import com.afef.foodapp.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FoodList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference foodList;
    String categoryId="";
    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        //firebase
        database  = FirebaseDatabase.getInstance();
        foodList = database.getReference("Food");

        recyclerView = findViewById(R.id.Recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

      //get intent
        if(getIntent() !=null)
            categoryId = getIntent().getStringExtra("CategoryId");
        if(!categoryId.isEmpty() && categoryId != null){
            loadListFood(categoryId);
        }





    }

    private void loadListFood(String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodList.orderByChild("MenuId").equalTo(categoryId)) {
            @Override
            protected void populateViewHolder(FoodViewHolder ViewHolder,Food model,int i) {
                ViewHolder.food_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(ViewHolder.food_image);
               final Food local = model;
               ViewHolder.setItemClickListener(new ItemClickListener() {
                   @Override
                   public void onClick(View view,int position,boolean inLongClick) {
                      //start new activity
                       Intent foodDetail =  new Intent(FoodList.this,FoodDetail.class);
                       foodDetail.putExtra("FoodId",adapter.getRef(position).getKey());
                       startActivity(foodDetail);
                   }
               });
            }
        };
    //set adapter
        recyclerView.setAdapter(adapter);
    }
}