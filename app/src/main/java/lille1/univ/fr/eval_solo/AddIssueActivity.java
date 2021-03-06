package lille1.univ.fr.eval_solo;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import lille1.univ.fr.eval_solo.model.Issue;

public class AddIssueActivity extends AppCompatActivity {
    private Spinner spinnerType;
    private EditText editTextDescription;
    private EditText editTextLatitude;
    private EditText editTextLongitude;
    private EditText editTextAddress;
    private Button buttonLocation;
    private FloatingActionButton fabValidate;
    private FloatingActionButton fabDelete;

    private Issue currentIssue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_issue);
        fabDelete = this.findViewById(R.id.delete_btn);
        fabDelete.setVisibility(View.INVISIBLE);
        spinnerType = this.findViewById(R.id.spinner_type);
        editTextDescription = this.findViewById(R.id.edittext_description);
        editTextLatitude = this.findViewById(R.id.edittext_latitude);
        editTextLongitude = this.findViewById(R.id.edittext_longitude);
        editTextAddress = this.findViewById(R.id.edittext_address);
        buttonLocation = this.findViewById(R.id.button_location);
        fabValidate = this.findViewById(R.id.fab);

        fillData();

        buttonLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(AddIssueActivity.this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        });
        fabValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveIssue();
            }
        });
        if (currentIssue != null) {
            fabDelete.setVisibility(View.VISIBLE);
            fabDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currentIssue.delete();
                    finish();
                }
            });
        }
    }

    private void fillData() {
        Long issueId = getIntent().getLongExtra("issue_id", -1L);
        if (issueId != -1) {
            currentIssue = Issue.findById(Issue.class, issueId);
            editTextDescription.setText(currentIssue.getDescription());
            editTextLatitude.setText(currentIssue.getLatitude());
            editTextLongitude.setText(currentIssue.getLongitude());
            editTextAddress.setText(currentIssue.getAddress());
            spinnerType.setSelection(getIndex(spinnerType, currentIssue.getType()));
        }
    }

    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }

    private void saveIssue() {
        if (currentIssue != null) {
            currentIssue.setType(spinnerType.getSelectedItem().toString());
            currentIssue.setDescription(editTextDescription.getText().toString());
            currentIssue.setLatitude(editTextLatitude.getText().toString());
            currentIssue.setLongitude(editTextLongitude.getText().toString());
            currentIssue.setAddress(editTextAddress.getText().toString());
            currentIssue.save();
        }
        else {
            Issue issue = new Issue();
            issue.setType(spinnerType.getSelectedItem().toString());
            issue.setDescription(editTextDescription.getText().toString());
            issue.setLatitude(editTextLatitude.getText().toString());
            issue.setLongitude(editTextLongitude.getText().toString());
            issue.setAddress(editTextAddress.getText().toString());
            issue.save();
        }
        this.finish();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Snackbar.make(this.findViewById(R.id.parent), "Localisation demandée", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    SingleShotLocationProvider.requestSingleUpdate(this,
                            new SingleShotLocationProvider.LocationCallback() {
                                @Override public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {
                                    Log.d("Location", "my location is " + location.latitude + ", " +  location.longitude);
                                    String latitude = "" + location.latitude;
                                    String longitude = "" + location.longitude;
                                    editTextLatitude.setText(latitude);
                                    editTextLongitude.setText(longitude);

                                    Geocoder geocoder;
                                    List<Address> addresses;
                                    geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                                    try {
                                        addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1);
                                        editTextAddress.setText(addresses.get(0).getAddressLine(0));
                                    } catch (IOException e) {
                                        Snackbar.make(AddIssueActivity.this.findViewById(R.id.parent), "Impossible de récupérer une adresse", Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                    }
                                }
                            });

                } else {
                    Snackbar.make(this.findViewById(R.id.parent), "Permissions GPS refusées", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                break;
        }
    }
}
