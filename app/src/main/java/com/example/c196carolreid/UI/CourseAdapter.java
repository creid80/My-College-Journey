package com.example.c196carolreid.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196carolreid.Entities.Course;
import com.example.c196carolreid.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {


    class CourseViewHolder extends RecyclerView.ViewHolder{
        private final TextView courseItemView;
        private final TextView courseItemView2;
        private CourseViewHolder(View itemView){
            super(itemView);
            courseItemView =itemView.findViewById(R.id.textView2);
            courseItemView2 =itemView.findViewById(R.id.textView3);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    final Course current= mCourses.get(position);
                    Intent intent=new Intent(context, CourseDetails.class);
                    intent.putExtra("id", current.getCourseID());
                    intent.putExtra("name", current.getCourseName());
                    intent.putExtra("start", current.getCourseStart());
                    intent.putExtra("end", current.getCourseEnd());
                    intent.putExtra("status", current.getStatus());
                    intent.putExtra("CIName", current.getCIName());
                    intent.putExtra("CIPhone", current.getCIPhone());
                    intent.putExtra("CIEmail", current.getCIEmail());
                    intent.putExtra("note", current.getNote());
                    intent.putExtra("termID",current.getTermID());
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;

    public CourseAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.course_list_item,parent,false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        if(mCourses !=null){
            Course current= mCourses.get(position);
            String name=current.getCourseName();
            String status = current.getStatus();
            holder.courseItemView.setText(name);
            holder.courseItemView2.setText(status);
        }
        else{
            holder.courseItemView.setText("No course name");
            holder.courseItemView.setText("No status selected");
        }
    }

    public void setCourses(List<Course> courses){
        mCourses = courses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }
}
