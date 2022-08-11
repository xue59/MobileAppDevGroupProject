package edu.neu.madscourse.tennismateandcourt;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TennisCourtAdapter extends RecyclerView.Adapter<TennisCourtAdapter.ViewHolder> {
    List<TennisCourtModel> tennisCourtModelList;
    Context context;

    public TennisCourtAdapter(Context context, List<TennisCourtModel> listTennisCourts) {
        this.context=context;
        this.tennisCourtModelList = tennisCourtModelList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.a_court_view_holder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TennisCourtAdapter.ViewHolder holder, int position) {
        TennisCourtModel userModel = tennisCourtModelList.get(position);
        holder.tennis_court_name.setText(userModel.getName());
        Log.e("bind View: ", userModel.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener!=null){
                    onItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tennis_court_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tennis_court_name = itemView.findViewById(R.id.tennis_court_name);
        }
    }
    private OnItemClickListener onItemClickListener;
    interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
