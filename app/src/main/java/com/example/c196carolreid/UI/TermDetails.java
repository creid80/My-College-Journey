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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        editEnd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Date date;
                //get value from other screen,but I'm going to hard code it right now
                String info=editEnd.getText().toString();
                if(info.equals(""))info="02/10/22";
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
                this.finish();
                return true;
            case R.id.termsave:
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
                return true;
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
                    Toast.makeText(TermDetails.this, "Can't delete a term with courses", Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.addNewCourse:
                Intent intent2=new Intent(TermDetails.this, CourseDetails.class);
                startActivity(intent2);
               /* if (termID == -1)
                    Toast.makeText(TermDetails.this, "Please save term before adding courses", Toast.LENGTH_LONG).show();

                else {
                    int courseID;

                    if (repository.getAllCourses().size() == 0) courseID = 1;
                    else
                        courseID = repository.getAllCourses().get(repository.getAllCourses().size() - 1).getCourseID() + 1;
                    Course course = new Course(courseID, "English", "11/11/11", "12/12/12", "In Progress", "Howard", "879-898-9474",
                                               "j@hotmail.com", "Note", termID);
                    repository.insert(course);
                    course = new Course(++courseID, "Science","11/11/11", "12/12/12", "In Progress", "Howard", "879-898-9474",
                            "j@hotmail.com", "Note", termID);
                    repository.insert(course);
                    RecyclerView recyclerView = findViewById(R.id.courserecyclerview);
                    final CourseAdapter courseAdapter = new CourseAdapter(this);
                    recyclerView.setAdapter(courseAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    List<Course> filteredCourses = new ArrayList<>();
                    for (Course p : repository.getAllCourses()) {
                        if (p.getTermID() == termID) filteredCourses.add(p);
                    }
                    courseAdapter.setCourses(filteredCourses);
                    return true;
                }

                */
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