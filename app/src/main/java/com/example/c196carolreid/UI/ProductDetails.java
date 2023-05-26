package com.example.c196carolreid.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.c196carolreid.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProductDetails extends AppCompatActivity {

    EditText editName;
    EditText editPrice;
    String name;
    double price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
            editName = findViewById(R.id.productname);
            editPrice = findViewById(R.id.productprice);
            name = getIntent().getStringExtra("name");
            price = getIntent().getDoubleExtra("price", -1);
            editName.setText(name);
            editPrice.setText(Double.toString(price));

        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetails.this, PartDetails.class);
                startActivity(intent);
            }
        });
    }
}