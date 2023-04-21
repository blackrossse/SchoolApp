package com.example.school.javacode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.school.R;

import java.util.ArrayList;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<News> news;

    public NewsRecyclerViewAdapter(Context context, ArrayList<News> news) {
        this.context = context;
        this.news = news;
    }

    @NonNull
    @Override
    public NewsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.new_item,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsRecyclerViewAdapter.ViewHolder holder, int position) {
        News currentNew = news.get(position);

        String userName = currentNew.getUserName();
        String dateAndTime = currentNew.getTimeAndDate();
        String text = currentNew.getText();

        holder.usernameTextView.setText(userName);
        holder.timeAndDateTextView.setText(dateAndTime);
        holder.textTextView.setText(text);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView usernameTextView;
        TextView timeAndDateTextView;
        TextView textTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            usernameTextView = itemView.findViewById(R.id.userNameTextView);
            timeAndDateTextView = itemView.findViewById(R.id.timeAndDateTextView);
            textTextView = itemView.findViewById(R.id.textTextView);
        }
    }
}
