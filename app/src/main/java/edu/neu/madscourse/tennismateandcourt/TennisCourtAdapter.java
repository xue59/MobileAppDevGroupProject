package edu.neu.madscourse.tennismateandcourt;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class TennisCourtAdapter extends RecyclerView.Adapter<TennisCourtAdapter.ViewHolder> {
    List<TennisCourtModel> tennisCourtModelList;
    Context context;
    SupportMapFragment mapFragment;
    GoogleMap map;


    public TennisCourtAdapter(Context context, List<TennisCourtModel> tennisCourtModelList, @NonNull SupportMapFragment mapFragment) {
        this.tennisCourtModelList = tennisCourtModelList;
        this.context=context;
        this.mapFragment = mapFragment;
        Log.e("public TennisCourtAdapter ", tennisCourtModelList.get(1).getName());
        Log.e("public TennisCourtAdapter Map", mapFragment.toString());
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.a_court_view_holder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TennisCourtAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        TennisCourtModel a_tennis_court = tennisCourtModelList.get(position);
        holder.tennis_court_name.setText(a_tennis_court.getName());
//        Log.e("bind View: ", a_tennis_court.getName()+"");
        holder.btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener!=null){
                    onItemClickListener.onItemClick(position);
                }
                Log.e("btnDetails: ", a_tennis_court.getName()+" Clicked - Details!!!");
                Intent intent = new Intent(context,TennisCourtDetails.class);
                intent.putExtra("TennisCourtModel",  a_tennis_court);
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener!=null){
                    onItemClickListener.onItemClick(position);
                }
                Log.e("bind View: ", a_tennis_court.getName()+" Clicked");
                moveToTennisCourtWithMapFragment(mapFragment, a_tennis_court);

            }
        });

    }

    //following code input with mapFragment & a_tennis_court to move map to the location
    public void moveToTennisCourtWithMapFragment (SupportMapFragment mapFragment, TennisCourtModel a_tennis_court){
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                map = googleMap;
                moveMapToLocation(a_tennis_court);
            }
            private void moveMapToLocation(TennisCourtModel a_tennis_court)
            {
                LatLng court_lat_lng;
                court_lat_lng = new LatLng(a_tennis_court.getLatitudes(), a_tennis_court.getLongitudes());
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(court_lat_lng,15));
                // Zoom in, animating the camera.
                map.animateCamera(CameraUpdateFactory.zoomIn());
                // Zoom out to zoom level 10, animating with a duration of 2 seconds.
                map.animateCamera(CameraUpdateFactory.zoomTo(11), 3000, null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tennisCourtModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tennis_court_name;
        Button btnDetails;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tennis_court_name = itemView.findViewById(R.id.tennis_court_name);
            btnDetails = itemView.findViewById(R.id.btnDetails);
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
