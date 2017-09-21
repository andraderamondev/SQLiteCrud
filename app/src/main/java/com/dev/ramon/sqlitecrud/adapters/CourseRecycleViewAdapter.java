package com.dev.ramon.sqlitecrud.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.ramon.sqlitecrud.R;
import com.dev.ramon.sqlitecrud.objects.Course;

import java.util.ArrayList;

/**
 * Created by Ramon Dev on 21/09/2017.
 */

public class CourseRecycleViewAdapter extends RecyclerView.Adapter<CourseRecycleViewAdapter.ViewHolder>{
    private ArrayList<Course> list;
    private Context context;

    public interface OnClickListener {
        void onClickItemList(Course course, int position);
    }

    private OnClickListener listener;

    public CourseRecycleViewAdapter(ArrayList<Course> list, OnClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @Override
    public CourseRecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_courses, parent, false);
        this.context = parent.getContext();
        return new CourseRecycleViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CourseRecycleViewAdapter.ViewHolder holder, int position) {
        holder.tvNome.setText(list.get(position).getNome());
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNome;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNome = itemView.findViewById(R.id.tvNome);
            applyListeners();
        }

        private void applyListeners() {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClickItemList(list.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }
}
