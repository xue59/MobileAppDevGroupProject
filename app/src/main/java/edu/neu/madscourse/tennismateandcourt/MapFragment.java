package edu.neu.madscourse.tennismateandcourt;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        recyclerView = rootView.findViewById(R.id.photos_recyclerview);
        TennisCourtModel court1= new TennisCourtModel(1,"NEU Boston Tennis Court");
        TennisCourtModel court2= new TennisCourtModel(2,"Second Tennis Court");
        TennisCourtModel court3= new TennisCourtModel(3,"333 Tennis Court");
        TennisCourtModel court4= new TennisCourtModel(4,"444 Tennis Court");
        TennisCourtModel court5= new TennisCourtModel(5,"555 Tennis Court");
        TennisCourtModel court6= new TennisCourtModel(6,"666 Tennis Court");
        listTennisCourts.add(court1);
        listTennisCourts.add(court2);
        listTennisCourts.add(court3);
        listTennisCourts.add(court4);
        listTennisCourts.add(court5);
        listTennisCourts.add(court6);
        adapter = new TennisCourtAdapter(this.getContext(), listTennisCourts);
        Log.d("Check court list: ", "test111" + listTennisCourts.get(0).getName());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                map = googleMap;
                LatLng NEUBoston = new LatLng(42.33962289703249, -71.08975307364507);
                map.addMarker(new MarkerOptions().position(NEUBoston).title("NEU Boston Tennis Court"));
                map.moveCamera(CameraUpdateFactory.newLatLng(NEUBoston));
            }
        });
        return rootView;

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MapViewModel.class);
        // TODO: Use the ViewModel
    }

//    GoogleMap map;
//    @Override
//    protected void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_map);
//
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap){
//        map = googleMap;
//        LatLng NEUBoston = new LatLng(42.33962289703249, -71.08975307364507);
//        map.addMarker(new MarkerOptions().position(NEUBoston).title("NEU Boston"));
//        map.moveCamera(CameraUpdateFactory.newLatLng(NEUBoston));
//    }
}