package com.example.c196carolreid.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
        FloatingActionButton fab=findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProductList.this,ProductDetails.class);
                startActivity(intent);
            }
        });
        repository=new Repository(getApplication());
        List<Product> allProducts=repository.getAllProducts();
        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        final ProductAdapter productAdapter=new ProductAdapter(this);
        recyclerView.setAdapter(productAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productAdapter.setProducts(allProducts);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_productlist, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
//                Intent intent=new Intent(ProductDetails.this,MainActivity.class);
//                startActivity(intent);
                return true;

            case R.id.addSampleProducts:
                Repository repo = new Repository(getApplication());
                Product product = new Product(1, "bicycle", 100.0);
                repo.insert(product);
                product = new Product(2, "tricycle", 150.0);
                repo.insert(product);
                List<Product> allProducts=repository.getAllProducts();
                RecyclerView recyclerView=findViewById(R.id.recyclerview);
                final ProductAdapter productAdapter=new ProductAdapter(this);
                recyclerView.setAdapter(productAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                productAdapter.setProducts(allProducts);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {

        super.onResume();
        List<Product> allProducts=repository.getAllProducts();
        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        final ProductAdapter productAdapter=new ProductAdapter(this);
        recyclerView.setAdapter(productAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productAdapter.setProducts(allProducts);

        //Toast.makeText(ProductDetails.this,"refresh list",Toast.LENGTH_LONG).show();
    }
}