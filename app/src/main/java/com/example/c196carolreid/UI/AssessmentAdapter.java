package com.example.c196carolreid.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196carolreid.Entities.Assessment;
import com.example.c196carolreid.R;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder>{


    class AssessmentViewHolder extends RecyclerView.ViewHolder{
        private final TextView assessmentItemView;
        private final TextView assessmentItemView2;
        private AssessmentViewHolder(View itemView){
            super(itemView);
            assessmentItemView =itemView.findViewById(R.id.textView);
            assessmentItemView2 =itemView.findViewById(R.id.textView2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    final Assessment current= mAssessments.get(position);
                    Intent intent=new Intent(context, AssessmentDetails.class);
                    intent.putExtra("id", current.getAssessmentID());
                    intent.putExtra("name", current.getAssessmentName());
                    intent.putExtra("start", current.getAssessmentStart());
                    intent.putExtra("end", current.getAssessmentEnd());
                    intent.putExtra("type", current.getAssessmentType());
                    intent.putExtra("courseID",current.getCourseID());
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<Assessment> mAssessments;
    private final Context context;
    private final LayoutInflater mInflater;

    public AssessmentAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }

    @NonNull
    @Override
    public AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.assessment_list_item,parent,false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentViewHolder holder, int position) {
        if(mAssessments !=null){
            Assessment current= mAssessments.get(position);
            String name=current.getAssessmentName();
            String type = current.getAssessmentType();
            holder.assessmentItemView.setText(name);
            holder.assessmentItemView2.setText(type);
        }
        else{
            holder.assessmentItemView.setText("No assessment name");
            holder.assessmentItemView2.setText("No assessment type");
        }
    }

    public void setAssessments(List<Assessment> assessments){
        mAssessments = assessments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() { return mAssessments.size(); }
}

