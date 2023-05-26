package com.example.c196carolreid.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.c196carolreid.Database.Repository;
import com.example.c196carolreid.Entities.Part;
import com.example.c196carolreid.Entities.Product;
import com.example.c196carolreid.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PartDetails extends AppCompatActivity {
    String name;
    Double price;
    int partID;
    int prodID;
    EditText editName;
    EditText editPrice;
    EditText editNote;
    EditText editDate;
    Repository repository;
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        repository=new Repository(getApplication());
        name = getIntent().getStringExtra("name");
        editName = findViewById(R.id.partName);
        editName.setText(name);
        price = getIntent().getDoubleExtra("price", -1.0);
        editPrice = findViewById(R.id.partPrice);
        editPrice.setText(Double.toString(price));
        partID = getIntent().getIntExtra("id", -1);
        prodID = getIntent().getIntExtra("prodID", -1);
        editNote=findViewById(R.id.note);
        editDate=findViewById(R.id.date);
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        ArrayList<Product> productArrayList= new ArrayList<>();
        productArrayList.addAll(repository.getAllProducts());
        ArrayList<Integer> productIdList= new ArrayList<>();
        for(Product product:productArrayList){
            productIdList.add(product.getProductID());
        }
        ArrayAdapter<Integer> productIdAdapter= new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item,productIdList);
        Spinner spinner=findViewById(R.id.spinner);
        spinner.setAdapter(productIdAdapter);
        startDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub

                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                updateLabelStart();
            }

        };

        editDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Date date;
                //get value from other screen,but I'm going to hard code it right now
                String info=editDate.getText().toString();
                if(info.equals(""))info="02/10/22";
                try{
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(PartDetails.this, startDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }
    private void updateLabelStart() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editDate.setText(sdf.format(myCalendarStart.getTime()));
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_partdetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
//                Intent intent=new Intent(PartDetails.this,MainActivity.class);
//                startActivity(intent);
//                return true;

            case R.id.partsave:
                Part part;
                if (partID == -1) {
                    if (repository.getAllParts().size() == 0)
                        partID = 1;
                    else
                        partID = repository.getAllParts().get(repository.getAllParts().size() - 1).getPartID() + 1;
                    part = new Part(partID, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()), prodID);
                    repository.insert(part);
                } else {
                    part = new Part(partID, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()), prodID);
                    repository.update(part);
                }
                return true;
            case R.id.share:
                Intent sendIntent=new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,editNote.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_TITLE,"Message Title");
                sendIntent.setType("text/plain");
                Intent shareIntent=Intent.createChooser(sendIntent,null);
                startActivity(shareIntent);
                return true;
            case R.id.notify:
                String dateFromScreen=editDate.getText().toString();
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                Date myDate=null;
                try {
                    myDate=sdf.parse(dateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger=myDate.getTime();
                Intent intent= new Intent(PartDetails.this,MyReceiver.class);
                intent.putExtra("key" ,dateFromScreen + " should trigger.");
                PendingIntent sender=PendingIntent.getBroadcast(PartDetails.this, ++MainActivity.numAlert,intent,PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
