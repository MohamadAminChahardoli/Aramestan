package company.aryasoft.aramestan.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import company.aryasoft.aramestan.ApiConnection.ApiServiceGenerator;
import company.aryasoft.aramestan.ApiConnection.DeceasedApis;
import company.aryasoft.aramestan.Implementations.DetailsCallBackImpl;
import company.aryasoft.aramestan.Models.DetailDeceasedApiModel;
import company.aryasoft.aramestan.Models.SliderDataModel;
import company.aryasoft.aramestan.R;
import retrofit2.Call;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DetailActivity extends AppCompatActivity
        implements Button.OnClickListener, DetailsCallBackImpl.OnDetailsReceivedListener, LocationListener {

    private Button ButtonShowLocation;
    private DeceasedApis Api;
    private Call<DetailDeceasedApiModel> DetailCall;
    private Criteria criteria;
    private LocationManager locationManager;
    private String Provider;
    double personLatitude = 0;
    double personLongitude = 0;
    double deceasedLatitude = 0;
    double deceasedLongitude = 0;
    private onPermissionVerifiedListener permissionVerifiedListener;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_show_location) {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initializeViews();
        Api = ApiServiceGenerator.getApiService();
        DetailCall = Api.getDeceasedDetails(1);
        DetailCall.enqueue(new DetailsCallBackImpl(this));

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
        else
        {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            criteria = new Criteria();
            Provider = locationManager.getBestProvider(criteria, false);
            locationManager.requestLocationUpdates(Provider, 12000, 7, DetailActivity.this);

        }
        setPermissionVerifiedListener(new onPermissionVerifiedListener()
        {
            @Override
            public void onPermissionVerified(Location location)
            {
                personLatitude = location.getLatitude();
                personLongitude = location.getLongitude();
                Toast.makeText(DetailActivity.this, location.getLatitude() + "", Toast.LENGTH_SHORT).show();
                Toast.makeText(DetailActivity.this, location.getLongitude() + "", Toast.LENGTH_SHORT).show();
                //call google map APP
                OpenGoogleMap();
            }
        });

    }

    @Override
    public void onReceived(Response<DetailDeceasedApiModel> response) {
        //bind received object to views
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101)
        {
            if (grantResults.length > 0)
            {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                criteria = new Criteria();
                Provider = locationManager.getBestProvider(criteria, false);
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                {
                    locationManager.requestLocationUpdates(Provider, 12000, 7, DetailActivity.this);
                }
            }
        }
    }


    private void initializeViews() {
        ButtonShowLocation = findViewById(R.id.btn_show_location);
        ButtonShowLocation.setOnClickListener(this);
    }

    public void setPermissionVerifiedListener(onPermissionVerifiedListener permissionVerifiedListener)
    {
        this.permissionVerifiedListener = permissionVerifiedListener;
    }

    private void OpenGoogleMap()
    {
        String uri = "http://maps.google.com/maps?f=d&hl=en&saddr=" + personLatitude + "," + personLongitude + "&daddr=" + deceasedLatitude + "," + deceasedLongitude;
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(Intent.createChooser(intent, "Select an application"));
    }

    public interface onPermissionVerifiedListener
    {
        void onPermissionVerified(Location location);
    }

}
