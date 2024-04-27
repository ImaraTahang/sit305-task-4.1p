package com.example.taskmanagerapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapterClass extends RecyclerView.Adapter<TaskAdapterClass.ViewHolder>{

    List<TaskModelClass> task;
    Context context;
    DatabaseHelperClass databaseHelperClass;

    public TaskAdapterClass(List<TaskModelClass> task, Context context) {
        this.task = task;
        this.context = context;
        databaseHelperClass = new DatabaseHelperClass(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.task_item_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final TaskModelClass taskModelClass = task.get(position);

        holder.textViewID.setText(Integer.toString(taskModelClass.getId()));
        holder.editText_task.setText(taskModelClass.getTask());
        holder.editText_desc.setText(taskModelClass.getDescription());
        holder.editText_date.setText(taskModelClass.getDate());

        holder.button_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringtask = holder.editText_task.getText().toString();
                String stringDesc= holder.editText_desc.getText().toString();
                String stringDate = holder.editText_date.getText().toString();

                databaseHelperClass.updateTask(new TaskModelClass(taskModelClass.getId(),stringtask,stringDesc,stringDate));
                notifyDataSetChanged();
                ((Activity) context).finish();
                context.startActivity(((Activity) context).getIntent());
            }
        });
        holder.button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    TaskModelClass removedTask = task.remove(currentPosition);
                    notifyItemRemoved(currentPosition);
                    databaseHelperClass.deleteTask(removedTask.getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return task.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewID;
        EditText editText_task;
        EditText editText_desc;
        EditText editText_date;
        Button button_Edit;
        Button button_delete;
        public ViewHolder(View itemView){
            super(itemView);

            textViewID = itemView.findViewById(R.id.text_id);
            editText_task = itemView.findViewById(R.id.edittext_task);
            editText_desc = itemView.findViewById(R.id.edittext_desc);
            editText_date = itemView.findViewById(R.id.edittext_date);
            button_delete = itemView.findViewById(R.id.button_delete);
            button_Edit = itemView.findViewById(R.id.button_edit);
        }
    }
}
