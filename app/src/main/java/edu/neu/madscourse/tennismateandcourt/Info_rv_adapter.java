package edu.neu.madscourse.tennismateandcourt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class Info_rv_adapter extends RecyclerView.Adapter<Info_rv_adapter.ViewHolder> {
    Context context;
    List<String> tennisCourtModel;
    List<String> item_key_list = Arrays.asList(new String[]{"ID: ","Name: ", "Rating: ", "Address: ", "Hours Of Operations: ", "Website: ", "Phone: ", "Las update time: "});


    public  Info_rv_adapter(Context context, List<String>  tennisCourtModel){
        this.context=context;
        this.tennisCourtModel=tennisCourtModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.a_info_row_holder, parent, false);
        return new Info_rv_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Info_rv_adapter.ViewHolder holder, int position) {
        String info_string = tennisCourtModel.get(position);
        holder.item_key.setText(item_key_list.get(position));
        holder.item_value.setText(info_string);
    }

    @Override
    public int getItemCount() {
        return tennisCourtModel.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView item_value;
        TextView item_key;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_value = itemView.findViewById(R.id.item_value);
            item_key = itemView.findViewById(R.id.item_key);
        }
    }
    private Info_rv_adapter.OnItemClickListener onItemClickListener;
    interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(Info_rv_adapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
