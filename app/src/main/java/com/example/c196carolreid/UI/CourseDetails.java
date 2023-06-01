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
import com.example.c196carolreid.Entities.Course;
import com.example.c196carolreid.Entities.Term;
import com.example.c196carolreid.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

        String myFormat = "MM/dd/yy"; //In which you need put here
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
                String info= editStart.getText().toString();
                if(info.equals(""))info="11/12/84";
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
                String info= editEnd.getText().toString();
                if(info.equals(""))info="11/12/84";
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
        getMenuInflater().inflate(R.menu.menu_coursedetails, menu);
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

            case R.id.coursesave:
                Course course;
                if (courseID == -1) {
                    if (repository.getAllCourses().size() == 0)
                        courseID = 1;
                    else
                        courseID = repository.getAllCourses().get(repository.getAllCourses().size() - 1).getCourseID() + 1;
                        //editStatus = spinner.getSelectedItem();
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
                String dateFromScreen= editStart.getText().toString();
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                Date myDate=null;
                try {
                    myDate=sdf.parse(dateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger=myDate.getTime();
                Intent intent= new Intent(CourseDetails.this,MyReceiver.class);
                intent.putExtra("key" ,dateFromScreen + " should trigger.");
                PendingIntent sender=PendingIntent.getBroadcast(CourseDetails.this, ++MainActivity.numAlert,intent,PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
