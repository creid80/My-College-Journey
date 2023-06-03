package com.example.c196carolreid.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.c196carolreid.Database.Repository;
import com.example.c196carolreid.Entities.Term;
import com.example.c196carolreid.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TermList extends AppCompatActivity {
    private Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        FloatingActionButton fab=findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TermList.this, TermDetails.class);
                startActivity(intent);
            }
        });
        repository=new Repository(getApplication());
        List<Term> allTerms=repository.getAllTerms();
        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        final TermAdapter termAdapter =new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerms(allTerms);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_termlist, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()) {
            /*case android.R.id.home:
                this.finish();
                Intent intent=new Intent(TermList.this, MainActivity.class);
                startActivity(intent);
                return true;

             */

            case R.id.addNewTerm:
                Intent intent2=new Intent(TermList.this, TermDetails.class);
                startActivity(intent2);
                /*Repository repo = new Repository(getApplication());
                Term term = new Term(1, "Term 1", "11/12/84", "12/12/84");
                repo.insert(term);
                term = new Term(2, "Term 2", "10/15/84", "11/15/84");
                repo.insert(term);
                List<Term> allTerms=repository.getAllTerms();
                RecyclerView recyclerView=findViewById(R.id.recyclerview);
                final TermAdapter termAdapter =new TermAdapter(this);
                recyclerView.setAdapter(termAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                termAdapter.setTerms(allTerms);

                 */

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {

        super.onResume();
        List<Term> allTerms =repository.getAllTerms();
        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        final TermAdapter termAdapter =new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerms(allTerms);

        //Toast.makeText(TermList.this,"refresh list", Toast.LENGTH_LONG).show();
    }
}