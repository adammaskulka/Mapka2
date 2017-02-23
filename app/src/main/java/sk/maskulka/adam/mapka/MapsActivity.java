package sk.maskulka.adam.mapka;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static sk.maskulka.adam.mapka.R.id.map;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    GPSTracker gpsTracker;
    SharedPreferences sharedPref;
    Switch switcha;
    Calendar myCalendarFrom;
    Calendar myCalendarTo;
    FirebaseDatabase database;
    List<GPSStamp> gpsStampList;
    boolean birthSort = false;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

        saveBooleanInSP("watch", true);
        gpsStampList = new ArrayList<>();


        gpsTracker = new GPSTracker(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (switcha != null) {
            if (getBooleanFromSP("watch")) {
                switcha.setChecked(true);
            } else {
                switcha.setChecked(false);
            }
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        // Read from the database
        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                gpsStampList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    GPSStamp gpsStamp = postSnapshot.child("24-02-2017").getValue(GPSStamp.class);
                    //System.out.println(gpsStamp.toString());
                    gpsStampList.add(gpsStamp);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("data", "Failed to read value.", error.toException());
            }
        });
        System.out.println(gpsStampList.size());

        mMap = googleMap;

        LatLng sydney = new LatLng(48.1257982, 17.1209458);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(sydney , 14.0f) );
        mMap.setMyLocationEnabled(true);
        for (GPSStamp gpsStamp : gpsStampList) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(gpsStamp.getLatitude(), gpsStamp.getLongtitude())).title("Marker in Sydney"));
        }


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.myswitch);
        View view = MenuItemCompat.getActionView(menuItem);
        switcha = (Switch) view.findViewById(R.id.switchForActionBar);
        switcha.setChecked(true);
        switcha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    saveBooleanInSP("watch", true);
                    Toast.makeText(MapsActivity.this, "Sledovanie polohy zapnuté!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    saveBooleanInSP("watch", false);
                    Toast.makeText(MapsActivity.this, "Sledovanie polohy vypnuté!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        if (id == R.id.action_filter) {
            DialogFragment newFragment = new FilterDialog();
            newFragment.show(getSupportFragmentManager(), "missiles");
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean getBooleanFromSP(String key) {
// TODO Auto-generated method stub
        SharedPreferences preferences = MapsActivity.this.getSharedPreferences(" SHARED_PREFERENCES_NAME ", android.content.Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }//getPWDFromSP()


    public void saveBooleanInSP(String key, boolean value) {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(" SHARED_PREFERENCES_NAME ", android.content.Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }


}
