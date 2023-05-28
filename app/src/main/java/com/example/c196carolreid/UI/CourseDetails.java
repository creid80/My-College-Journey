package com.example.c196carolreid.UI;

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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196carolreid.Database.Repository;
import com.example.c196carolreid.Entities.Assessment;
import com.example.c196carolreid.Entities.Course;
import com.example.c196carolreid.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CourseDetails extends AppCompatActivity {

    int courseID;
    String name;
    String start;
    String end;
    String status;
    String CIName;
    String CIPhone;
    String CIEmail;
    String note;
    int termID;
    EditText editName;
    EditText editEnd;
    EditText editStatus;
    EditText editCIName;
    EditText editCIPhone;
    EditText editCIEmail;
    EditText editNote;
    EditText editStart;
    Repository repository;
    Course currentCourse;
    int numAssessments;
    Assessment currentAssessment;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        repository=new Repository(getApplication());
        courseID = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        editName = findViewById(R.id.courseName);
        editName.setText(name);
        start = getIntent().getStringExtra("start");
        editStart = findViewById(R.id.courseStart);
        editStart.setText(start);
        end = getIntent().getStringExtra("end");
        editEnd = findViewById(R.id.courseEnd);
        editEnd.setText(end);
        CIName = getIntent().getStringExtra("CIName");
        editCIName = findViewById(R.id.courseCIName);
        editCIName.setText(CIName);
        CIPhone = getIntent().getStringExtra("CIPhone");
        editCIPhone = findViewById(R.id.courseCIPhone);
        editCIPhone.setText(CIPhone);
        CIEmail = getIntent().getStringExtra("CIEmail");
        editCIEmail = findViewById(R.id.courseCIEmail);
        editCIEmail.setText(CIEmail);
        note = getIntent().getStringExtra("note");
        editNote=findViewById(R.id.note);
        editNote.setText(note);
        termID = getIntent().getIntExtra("termID", -1);

        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        ArrayList<String> termArrayList= new ArrayList<>();
        termArrayList.add("In Progress");
        termArrayList.add("Completed");
        termArrayList.add("Dropped");
        termArrayList.add("Plan to Take");

        ArrayAdapter<String> termIdAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,termArrayList);
        spinner=findViewById(R.id.courseStatus);
        spinner.setAdapter(termIdAdapter);
        status = getIntent().getStringExtra("status");
        int position = termIdAdapter.getPosition(status);
        spinner.setSelection(position);

        startDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                updateLabelStart();
            }
        };

        editStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String info= editStart.getText().toString();
                if(info.equals(""))info="01/02/03";
                try{
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetails.this, startDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, monthOfYear);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                updateLabelEnd();
            }
        };

        editEnd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String info= editEnd.getText().toString();
                if(info.equals(""))info="02/03/04";
                try{
                    myCalendarEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetails.this, endDate, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        courseID = getIntent().getIntExtra("id", -1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.assessmentrecyclerview);
        repository = new Repository(getApplication());
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> filteredAssessments = new ArrayList<>();
        for (Assessment a : repository.getAllAssessments()) {
            if (a.getCourseID() == courseID) filteredAssessments.add(a);
        }
        assessmentAdapter.setAssessments(filteredAssessments);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton3);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseDetails.this, AssessmentDetails.class);
                intent.putExtra("courseID", courseID);
                startActivity(intent);
            }
        });
    }

    private void updateLabelStart() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editStart.setText(sdf.format(myCalendarStart.getTime()));
    }

    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editEnd.setText(sdf.format(myCalendarEnd.getTime()));
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_coursedetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.allterms:
               Intent intentHome = new Intent(CourseDetails.this,TermList.class);
                startActivity(intentHome);
                return true;
            case R.id.coursesave:

                if(editStart.getText().toString().matches("")) editStart.setText("01/02/03");
                if(editEnd.getText().toString().matches("")) editEnd.setText("02/03/04");

                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                try {
                    Date sDate = sdf.parse(editStart.getText().toString());
                    Date eDate = sdf.parse(editEnd.getText().toString());
                    if (sDate.toInstant().isAfter(eDate.toInstant())) {
                        Toast.makeText(this, "The end date must be later than the start date.", Toast.LENGTH_LONG).show();
                        return true;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Course course;
                if (courseID == -1) {
                    if (repository.getAllCourses().size() == 0)
                        courseID = 1;
                    else
                        courseID = repository.getAllCourses().get(repository.getAllCourses().size() - 1).getCourseID() + 1;
                    course = new Course(courseID, editName.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(),
                                        spinner.getSelectedItem().toString(), editCIName.getText().toString(), editCIPhone.getText().toString(),
                                        editCIEmail.getText().toString(), editNote.getText().toString(), termID);
                    repository.insert(course);
                } else {
                    course = new Course(courseID, editName.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(),
                            spinner.getSelectedItem().toString(), editCIName.getText().toString(), editCIPhone.getText().toString(),
                            editCIEmail.getText().toString(), editNote.getText().toString(), termID);
                    repository.update(course);
                }
                Toast.makeText(this, editName.getText().toString() + " was saved", Toast.LENGTH_LONG).show();
                this.finish();
                return false;
            case R.id.share:
                Intent sendIntent=new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,editNote.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_TITLE,"Message Title");
                sendIntent.setType("text/plain");
                Intent shareIntent=Intent.createChooser(sendIntent,null);
                startActivity(shareIntent);
                return true;
            case R.id.subitemstart:
                String dateFromScreen= editStart.getText().toString();
                String myFormat2 = "MM/dd/yy";
                SimpleDateFormat sdf2 = new SimpleDateFormat(myFormat2, Locale.US);
                Date myDate=null;
                try {
                    myDate=sdf2.parse(dateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger=myDate.getTime();
                Intent intent= new Intent(CourseDetails.this,MyReceiver.class);
                intent.putExtra("key" ,dateFromScreen + "- The " + name + " course starts today.");
                PendingIntent sender=PendingIntent.getBroadcast(CourseDetails.this, ++MainActivity.numAlert,intent,PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                return true;
            case R.id.subitemend:
                String dateFromScreen2= editEnd.getText().toString();
                String myFormat3 = "MM/dd/yy";
                SimpleDateFormat sdf3 = new SimpleDateFormat(myFormat3, Locale.US);
                Date myDate2=null;
                try {
                    myDate2=sdf3.parse(dateFromScreen2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger2=myDate2.getTime();
                Intent intent2= new Intent(CourseDetails.this,MyReceiver.class);
                intent2.putExtra("key" ,dateFromScreen2 + "- The " + name + " course ends today.");
                PendingIntent sender2=PendingIntent.getBroadcast(CourseDetails.this, ++MainActivity.numAlert,intent2,PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager2=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager2.set(AlarmManager.RTC_WAKEUP, trigger2, sender2);
                return true;
            case R.id.coursedelete:
                for (Course course1 : repository.getAllCourses()) {
                    if (course1.getCourseID() == courseID) currentCourse = course1;
                }

                numAssessments = 0;
                for (Assessment assessment : repository.getAllAssessments()) {
                    if (assessment.getCourseID() == courseID) {
                        currentAssessment = assessment;
                        repository.delete(currentAssessment);
                    }
                }

                repository.delete(currentCourse);
                Toast.makeText(CourseDetails.this, currentCourse.getCourseName() + " and it's associated assessments were deleted", Toast.LENGTH_LONG).show();
                this.finish();
                break;
            case R.id.addNewAssessment:
                if(courseID == -1) {
                    Toast.makeText(CourseDetails.this, "Save course before adding assessment", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent4 = new Intent(CourseDetails.this, AssessmentDetails.class);
                    startActivity(intent4);
                    return true;
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {

        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.assessmentrecyclerview);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> filteredAssessments = new ArrayList<>();
        for (Assessment a : repository.getAllAssessments()) {
            if (a.getCourseID() == courseID) filteredAssessments.add(a);
        }
        assessmentAdapter.setAssessments(filteredAssessments);

    }
}
