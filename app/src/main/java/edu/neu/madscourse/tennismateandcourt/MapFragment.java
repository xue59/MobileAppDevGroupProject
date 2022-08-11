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

        // hardcode some test data
        TennisCourtModel court1= new TennisCourtModel("1","NEU Boston Tennis Court","4.5","addres1111", "10AM-9PM","www.tennisss.com","762-301-231","2021-01-30");
        TennisCourtModel court2= new TennisCourtModel("2","Second Tennis Court","4.5","addres1111", "10AM-9PM","www.tennisss.com","762-301-231","2021-01-30");
        TennisCourtModel court3= new TennisCourtModel("3","333 Tennis Court","4.5","addres1111", "10AM-9PM","www.tennisss.com","762-301-231","2021-01-30");
        TennisCourtModel court4= new TennisCourtModel("4","444 Tennis Court","4.5","addres1111", "10AM-9PM","www.tennisss.com","762-301-231","2021-01-30");
        TennisCourtModel court5= new TennisCourtModel("5","555 Tennis Court","4.5","addres1111", "10AM-9PM","www.tennisss.com","762-301-231","2021-01-30");
        listTennisCourts.add(court1);
        listTennisCourts.add(court2);
        listTennisCourts.add(court3);
        listTennisCourts.add(court4);
        listTennisCourts.add(court5);
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