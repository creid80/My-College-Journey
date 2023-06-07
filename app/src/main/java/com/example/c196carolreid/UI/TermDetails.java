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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.c196carolreid.Database.Repository;
import com.example.c196carolreid.Entities.Course;
import com.example.c196carolreid.Entities.Term;
import com.example.c196carolreid.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TermDetails extends AppCompatActivity {
    String name;
    String start;
    String end;
    int termID;
    EditText editName;
    EditText editStart;
    EditText editEnd;
    Repository repository;
    Term currentTerm;
    int numCourses;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();
    Bundle savedInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedInstance = savedInstanceState;
        setContentView(R.layout.activity_term_details);
        name = getIntent().getStringExtra("name");
        editName = findViewById(R.id.termname);
        editName.setText(name);
        start = getIntent().getStringExtra("start");
        editStart = findViewById(R.id.termstart);
        editStart.setText(start);
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

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

        editStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Date date;
                //get value from other screen,but I'm going to hard code it right now
                String info=editStart.getText().toString();
                if(info.equals(""))info="02/10/22";
                try{
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(TermDetails.this, startDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        end = getIntent().getStringExtra("end");
        editEnd = findViewById(R.id.termend);
        editEnd.setText(end);


        endDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub

                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, monthOfYear);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                updateLabelEnd();
            }

        };

        editEnd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    //Toast.makeText(TermDetails.this, "unfocus", Toast.LENGTH_LONG).show();
                }
            }
        });

        editEnd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Date date;
                //get value from other screen,but I'm going to hard code it right now
                String info=editEnd.getText().toString();
                if(info.equals(""))info= String.valueOf(Locale.getDefault());
                try{
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(TermDetails.this, endDate, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        termID = getIntent().getIntExtra("id", -1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.courserecyclerview);
        repository = new Repository(getApplication());
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Course> filteredCourses = new ArrayList<>();
        for (Course p : repository.getAllCourses()) {
            if (p.getTermID() == termID) filteredCourses.add(p);
        }
        courseAdapter.setCourses(filteredCourses);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermDetails.this, CourseDetails.class);
                intent.putExtra("termID", termID);
                startActivity(intent);
            }
        });
    }

    private void updateLabelStart() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editStart.setText(sdf.format(myCalendarStart.getTime()));
    }

    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editEnd.setText(sdf.format(myCalendarEnd.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_termdetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.allterms:
                Intent intentHome = new Intent(TermDetails.this, TermList.class);
                startActivity(intentHome);
                return true;
            case R.id.termsave:
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                try {
                    Date sDate = sdf.parse(editStart.getText().toString());
                    System.out.println("in menu after start parse");
                    Date eDate = sdf.parse(editEnd.getText().toString());
                    if (sDate.toInstant().isAfter(eDate.toInstant())) {
                        System.out.println("in if statement " + sDate.toString() + " " + eDate.toString());
                        Toast.makeText(this, "The end date must be later then the start date.", Toast.LENGTH_LONG).show();
                        System.out.println("after toast");

                        //Intent intentHome2 = new Intent(TermDetails.this, TermList.class);
                        //startActivity(intentHome2);
                        return true;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }



                Term term;
                if (termID == -1) {
                    if (repository.getAllTerms().size() == 0) termID = 1;
                    else
                        termID = repository.getAllTerms().get(repository.getAllTerms().size() - 1).getTermID() + 1;
                    term = new Term(termID, editName.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
                    repository.insert(term);
                } else {
                    term = new Term(termID, editName.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
                    repository.update(term);
                }

                Toast.makeText(this, editName.getText().toString() + " was saved", Toast.LENGTH_LONG).show();
                this.finish();
                return false;
            case R.id.termdelete:
                for (Term term1 : repository.getAllTerms()) {
                    if (term1.getTermID() == termID) currentTerm = term1;
                }

                numCourses = 0;
                for (Course course : repository.getAllCourses()) {
                    if (course.getTermID() == termID) ++numCourses;
                }

                if (numCourses == 0) {
                    repository.delete(currentTerm);
                    Toast.makeText(TermDetails.this, currentTerm.getTermName() + " was deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(TermDetails.this, "Delete associated courses and try again", Toast.LENGTH_LONG).show();
                }
                //Intent intentHome2 = new Intent(TermDetails.this, TermList.class);
                //startActivity(intentHome2);
                this.finish();
                break;
            case R.id.addNewCourse:
               if (termID == -1) {
                   Toast.makeText(TermDetails.this, "Save term before adding courses", Toast.LENGTH_LONG).show();
                   System.out.println("in addNewCourse need to save term.");
               }
                else {
                   Intent intent2=new Intent(TermDetails.this, CourseDetails.class);
                   startActivity(intent2);
                   return true;
               }
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {

        super.onResume();
            RecyclerView recyclerView = findViewById(R.id.courserecyclerview);
            final CourseAdapter courseAdapter = new CourseAdapter(this);
            recyclerView.setAdapter(courseAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            List<Course> filteredCourses = new ArrayList<>();
            for (Course p : repository.getAllCourses()) {
                if (p.getTermID() == termID) filteredCourses.add(p);
            }
            courseAdapter.setCourses(filteredCourses);

            //Toast.makeText(ProductDetails.this,"refresh list",Toast.LENGTH_LONG).show();

    }

}