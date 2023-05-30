package com.example.c196carolreid.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.c196carolreid.Database.Repository;
import com.example.c196carolreid.Entities.Course;
import com.example.c196carolreid.Entities.Term;
import com.example.c196carolreid.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TermDetails extends AppCompatActivity {
    String name;
    double price;
    int termID;
    EditText editName;
    EditText editPrice;
    Repository repository;
    Term currentTerm;
    int numCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        name = getIntent().getStringExtra("name");
        editName = findViewById(R.id.termname);
        editName.setText(name);
        price = getIntent().getDoubleExtra("price", -1.0);
        editPrice = findViewById(R.id.termprice);
        editPrice.setText(Double.toString(price));
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

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_termdetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.termsave:
                Term term;
                if (termID == -1) {
                    if (repository.getAllTerms().size() == 0) termID = 1;
                    else
                        termID = repository.getAllTerms().get(repository.getAllTerms().size() - 1).getTermID() + 1;
                    term = new Term(termID, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()));
                    repository.insert(term);
                } else {
                    term = new Term(termID, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()));
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
            case R.id.addSampleCourses:
                if (termID == -1)
                    Toast.makeText(TermDetails.this, "Please save term before adding courses", Toast.LENGTH_LONG).show();

                else {
                    int courseID;

                    if (repository.getAllCourses().size() == 0) courseID = 1;
                    else
                        courseID = repository.getAllCourses().get(repository.getAllCourses().size() - 1).getCourseID() + 1;
                    Course course = new Course(courseID, "English", 10, termID);
                    repository.insert(course);
                    course = new Course(++courseID, "Science", 10, termID);
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