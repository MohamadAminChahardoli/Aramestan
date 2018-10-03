package company.aryasoft.aramestan.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import company.aryasoft.aramestan.Utilities.GPSTracker;
import retrofit2.Call;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DetailActivity extends AppCompatActivity
        implements Button.OnClickListener, DetailsCallBackImpl.OnDetailsReceivedListener {

    private Button ButtonShowLocation;
    private double personLatitude = 0;
    private double personLongitude = 0;
    private double deceasedLatitude = 35.70152641193356;
    private double deceasedLongitude = 51.419779301213566;
    private DeceasedApis Api;
    private Call<DetailDeceasedApiModel> DetailCall;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_show_location) {
            getLatLong();
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
    }

    @Override
    public void onReceived(Response<DetailDeceasedApiModel> response) {
        //bind received object to views
    }

    private void initializeViews() {
        ButtonShowLocation = findViewById(R.id.btn_show_location);
        ButtonShowLocation.setOnClickListener(this);
    }

    private void getLatLong() {
        GPSTracker gpsTracker = new GPSTracker(this);
        personLatitude = gpsTracker.getLatitude();
        personLongitude = gpsTracker.getLongitude();
        gpsTracker.stopUsingGPS();

        Toast.makeText(this, personLatitude + "", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, personLongitude + "", Toast.LENGTH_SHORT).show();
        OpenGoogleMap();
    }

    private void OpenGoogleMap() {
        String uri = "http://maps.google.com/maps?f=d&hl=en&saddr=" + personLatitude + "," + personLongitude + "&daddr=" + deceasedLatitude + "," + deceasedLongitude;
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(Intent.createChooser(intent, "Select an application"));
    }

}
