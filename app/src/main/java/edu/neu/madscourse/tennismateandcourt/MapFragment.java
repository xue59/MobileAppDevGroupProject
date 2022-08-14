package edu.neu.madscourse.tennismateandcourt;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment {
    private MapViewModel mViewModel;
    GoogleMap map;
    private RecyclerView recyclerView;
    private TennisCourtAdapter adapter;
    private List<TennisCourtModel> listTennisCourts = new ArrayList();

    public DatabaseReference dataRef;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        // display map fragment first & set location:
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        setMapFragement(mapFragment);


        // Display recycelr tennis court list view & hardcode some test data
        recyclerView = rootView.findViewById(R.id.photos_recyclerview);
        getData(dataRef, mapFragment);

        adapter = new TennisCourtAdapter(this.getContext(), listTennisCourts, mapFragment);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rootView;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MapViewModel.class);
        // TODO: Use the ViewModel
    }

    private void getData(DatabaseReference dataRef, SupportMapFragment mapFragment) {
        dataRef = FirebaseDatabase.getInstance().getReference("Courts");
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listTennisCourts.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    int    id =  Integer.parseInt(snapshot.child("id").getValue().toString());
                    String name = snapshot.child("name").getValue().toString();
                    Double rating = Double.parseDouble(snapshot.child("rating").getValue().toString());
                    Double latitudes = Double.parseDouble(snapshot.child("latitudes").getValue().toString());
                    Double longitudes = Double.parseDouble(snapshot.child("longitudes").getValue().toString());
                    String address = snapshot.child("address").getValue().toString();
                    String hoursOfOperations = snapshot.child("hoursOfOperations").getValue().toString();
                    String website = snapshot.child("website").getValue().toString();
                    String phone = snapshot.child("phone").getValue().toString();
                    String lastUpdateTime = snapshot.child("lastUpdateTime").getValue().toString();
                    List<String> imgs = (List<String>) snapshot.child("photos").getValue();
                    TennisCourtModel a_court = new TennisCourtModel(id, name, rating, latitudes,longitudes,address,hoursOfOperations,website,phone, lastUpdateTime);
                    a_court.setPhotos(imgs);
                    a_court.setKey(snapshot.getKey());
                    listTennisCourts.add(a_court);
                    Log.e("test snapshot get database: ",  a_court.getName());
                }
                adapter.notifyDataSetChanged();
                setMapFragement(mapFragment);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    //如下代码用于 setup 下半部分的Map fragment & auto zoom map的功能
    public void setMapFragement(SupportMapFragment mapFragment){
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                map = googleMap;
                LatLng court_lat_lng;
                for(int i =0; i <listTennisCourts.size();i++ ){
                    TennisCourtModel a_court = listTennisCourts.get(i);
                    court_lat_lng = new LatLng(listTennisCourts.get(i).getLatitudes(), listTennisCourts.get(i).getLongitudes());
                    map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(0)).position(court_lat_lng).title(a_court.getName()).snippet(a_court.address));
                    map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(120)).position(court_lat_lng).title(a_court.getName()));
                    moveMapToLocation(listTennisCourts.get(i));
                }

            }

            private void moveMapToLocation(TennisCourtModel a_tennis_court)
            {
                LatLng court_lat_lng;
                court_lat_lng = new LatLng(a_tennis_court.getLatitudes(), a_tennis_court.getLongitudes());
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(court_lat_lng,15));
                // Zoom in, animating the camera.
                map.animateCamera(CameraUpdateFactory.zoomIn());
                // Zoom out to zoom level 10, animating with a duration of 2.2 seconds.
                map.animateCamera(CameraUpdateFactory.zoomTo(11), 2200, null);
            }
        });
    }
}