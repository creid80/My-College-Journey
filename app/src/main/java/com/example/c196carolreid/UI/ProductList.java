package com.example.c196carolreid.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.c196carolreid.Database.Repository;
import com.example.c196carolreid.R;
import com.example.c196carolreid.Entities.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ProductList extends AppCompatActivity {

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        RecyclerView recyclerView = findViewById(R.id.productrecyclerview);
        final ProductAdapter productAdapter = new ProductAdapter(this);
        recyclerView.setAdapter(productAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        repository = new Repository(getApplication());
        List<Product> allProducts = repository.getAllProducts();
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        productAdapter.setProducts(allProducts);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductList.this, ProductDetails.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onResume() {

        super.onResume();
        List<Product> allProducts=repository.getAllProducts();
        RecyclerView recyclerView=findViewById(R.id.productrecyclerview);
        final ProductAdapter productAdapter=new ProductAdapter(this);
        recyclerView.setAdapter(productAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productAdapter.setProducts(allProducts);

        //Toast.makeText(ProductDetails.this,"refresh list",Toast.LENGTH_LONG).show();
    }
}