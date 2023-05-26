package com.example.c196carolreid.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.c196carolreid.Database.Repository;
import com.example.c196carolreid.Entities.Part;
import com.example.c196carolreid.Entities.Product;
import com.example.c196carolreid.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProductDetails extends AppCompatActivity {
    String name;
    double price;
    int productID;
    EditText editName;
    EditText editPrice;
    Repository repository;
    Product currentProduct;
    int numParts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        name = getIntent().getStringExtra("name");
        editName = findViewById(R.id.productname);
        editName.setText(name);
        price = getIntent().getDoubleExtra("price", -1.0);
        editPrice = findViewById(R.id.productprice);
        editPrice.setText(Double.toString(price));
        productID = getIntent().getIntExtra("id", -1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.partrecyclerview);
        repository = new Repository(getApplication());
        final PartAdapter partAdapter = new PartAdapter(this);
        recyclerView.setAdapter(partAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Part> filteredParts = new ArrayList<>();
        for (Part p : repository.getAllParts()) {
            if (p.getProductID() == productID) filteredParts.add(p);
        }
        partAdapter.setParts(filteredParts);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetails.this, PartDetails.class);
                intent.putExtra("prodID", productID);
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_productdetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.productsave:
                Product product;
                if (productID == -1) {
                    if (repository.getAllProducts().size() == 0) productID = 1;
                    else
                        productID = repository.getAllProducts().get(repository.getAllProducts().size() - 1).getProductID() + 1;
                    product = new Product(productID, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()));
                    repository.insert(product);
                } else {
                    product = new Product(productID, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()));
                    repository.update(product);
                }
                return true;
            case R.id.productdelete:
                for (Product prod : repository.getAllProducts()) {
                    if (prod.getProductID() == productID) currentProduct = prod;
                }

                numParts = 0;
                for (Part part : repository.getAllParts()) {
                    if (part.getProductID() == productID) ++numParts;
                }

                if (numParts == 0) {
                    repository.delete(currentProduct);
                    Toast.makeText(ProductDetails.this, currentProduct.getProductName() + " was deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ProductDetails.this, "Can't delete a product with parts", Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.addSampleParts:
                if (productID == -1)
                    Toast.makeText(ProductDetails.this, "Please save product before adding parts", Toast.LENGTH_LONG).show();

                else {
                    int partID;

                    if (repository.getAllParts().size() == 0) partID = 1;
                    else
                        partID = repository.getAllParts().get(repository.getAllParts().size() - 1).getPartID() + 1;
                    Part part = new Part(partID, "wheel", 10, productID);
                    repository.insert(part);
                    part = new Part(++partID, "pedal", 10, productID);
                    repository.insert(part);
                    RecyclerView recyclerView = findViewById(R.id.partrecyclerview);
                    final PartAdapter partAdapter = new PartAdapter(this);
                    recyclerView.setAdapter(partAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    List<Part> filteredParts = new ArrayList<>();
                    for (Part p : repository.getAllParts()) {
                        if (p.getProductID() == productID) filteredParts.add(p);
                    }
                    partAdapter.setParts(filteredParts);
                    return true;
                }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {

        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.partrecyclerview);
        final PartAdapter partAdapter = new PartAdapter(this);
        recyclerView.setAdapter(partAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Part> filteredParts = new ArrayList<>();
        for (Part p : repository.getAllParts()) {
            if (p.getProductID() == productID) filteredParts.add(p);
        }
        partAdapter.setParts(filteredParts);

        //Toast.makeText(ProductDetails.this,"refresh list",Toast.LENGTH_LONG).show();
    }

}