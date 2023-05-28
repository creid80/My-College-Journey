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

import com.example.c196carolreid.Database.Repository;
import com.example.c196carolreid.Entities.Assessment;
import com.example.c196carolreid.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AssessmentDetails extends AppCompatActivity {

    int assessmentID;
    String name;
    String start;
    String end;
    String type;
    int courseID;
    EditText editName;
    EditText editStart;
    EditText editEnd;
    Repository repository;
    Assessment currentAssessment;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();
    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        repository=new Repository(getApplication());
        assessmentID = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        editName = findViewById(R.id.assessmentName);
        editName.setText(name);
        start = getIntent().getStringExtra("start");
        editStart = findViewById(R.id.assessmentStart);
        editStart.setText(start);
        end = getIntent().getStringExtra("end");
        editEnd = findViewById(R.id.assessmentEnd);
        editEnd.setText(end);
        courseID = getIntent().getIntExtra("courseID", -1);

        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        ArrayList<String> courseArrayList= new ArrayList<>();
        courseArrayList.add("Performance");
        courseArrayList.add("Objective");

        ArrayAdapter<String> courseIdAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,courseArrayList);
        spinner=findViewById(R.id.assessmentType);
        spinner.setAdapter(courseIdAdapter);
        type = getIntent().getStringExtra("type");
        int position = courseIdAdapter.getPosition(type);
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
                new DatePickerDialog(AssessmentDetails.this, startDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

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
                new DatePickerDialog(AssessmentDetails.this, endDate, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
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
        getMenuInflater().inflate(R.menu.menu_assessmentdetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.allterms:
                Intent intentHome = new Intent(AssessmentDetails.this,TermList.class);
                startActivity(intentHome);
                return true;
            case R.id.assessmentsave:

                if(editStart.getText().toString().matches("")) editStart.setText("01/02/03");
                if(editEnd.getText().toString().matches("")) editEnd.setText("02/03/04");

                try {
                    String myFormat = "MM/dd/yy";
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                    Date sDate = sdf.parse(editStart.getText().toString());
                    Date eDate = sdf.parse(editEnd.getText().toString());
                    if (sDate.toInstant().isAfter(eDate.toInstant())) {
                        Toast.makeText(this, "The end date must be later than the start date.", Toast.LENGTH_LONG).show();
                        return true;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                assessmentID = getIntent().getIntExtra("id", -1);

                Assessment assessment;
                if (assessmentID == -1) {
                    if (repository.getAllAssessments().size() == 0)
                        assessmentID = 1;
                    else
                        assessmentID = repository.getAllAssessments().get(repository.getAllAssessments().size() - 1).getAssessmentID() + 1;
                    assessment = new Assessment(assessmentID, editName.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(),
                            spinner.getSelectedItem().toString(), courseID);
                    repository.insert(assessment);
                } else {
                    assessment = new Assessment(assessmentID, editName.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(),
                            spinner.getSelectedItem().toString(), courseID);
                    repository.update(assessment);
                }
                Toast.makeText(this, editName.getText().toString() + " was saved", Toast.LENGTH_LONG).show();
                this.finish();
                return false;
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
                Intent intent= new Intent(AssessmentDetails.this,MyReceiver.class);
                intent.putExtra("key" ,dateFromScreen + "- The " + name + " assessment starts today.");
                PendingIntent sender=PendingIntent.getBroadcast(AssessmentDetails.this, ++MainActivity.numAlert,intent,PendingIntent.FLAG_IMMUTABLE);
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
                Intent intent2= new Intent(AssessmentDetails.this,MyReceiver.class);
                intent2.putExtra("key" ,dateFromScreen2 + "- The " + name + " assessment ends today.");
                PendingIntent sender2=PendingIntent.getBroadcast(AssessmentDetails.this, ++MainActivity.numAlert,intent2,PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager2=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager2.set(AlarmManager.RTC_WAKEUP, trigger2, sender2);
                return true;
            case R.id.assessmentdelete:
                for (Assessment assessment1 : repository.getAllAssessments()) {
                    if (assessment1.getAssessmentID() == assessmentID) currentAssessment = assessment1;
                }
                repository.delete(currentAssessment);
                Toast.makeText(AssessmentDetails.this, currentAssessment.getAssessmentName() + " was deleted", Toast.LENGTH_LONG).show();
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
