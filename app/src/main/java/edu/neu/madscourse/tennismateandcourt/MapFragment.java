package edu.neu.madscourse.tennismateandcourt;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

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
        recyclerView = rootView.findViewById(R.id.tennis_courts_recycler_view);
        TennisCourtModel court1= new TennisCourtModel();
        court1.id = 1;
        court1.name = "First Tennis Court";
        TennisCourtModel court2= new TennisCourtModel();
        court2.id = 2;
        court2.name = "Second Tennis Court";
        listTennisCourts.add(court1);
        listTennisCourts.add(court2);
        adapter = new TennisCourtAdapter(this.getContext(), listTennisCourts);
        recyclerView.setAdapter(adapter);


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