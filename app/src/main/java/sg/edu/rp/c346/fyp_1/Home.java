package sg.edu.rp.c346.fyp_1;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Home extends AppCompatActivity {
    Spinner spn;
    EditText etDate, etTime, etStreet, etPostal, etNote, etContact, etEmail;
    Button btnProceed;

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    Calendar calendar;
    int currentHour;
    int currentMinute;
    String amPm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        spn = findViewById(R.id.spinnerService);
        etDate = findViewById(R.id.editTextDate);
        etTime = findViewById(R.id.editTextStart);
        etStreet = findViewById(R.id.editTextStreet);
        etPostal = findViewById(R.id.editTextPostal);
        etNote = findViewById(R.id.editTextAddtional);
        etContact = findViewById(R.id.editTextContact);
        etEmail = findViewById(R.id.editTextEmail);
        btnProceed = findViewById(R.id.button);

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etDate.getText().toString().equalsIgnoreCase("")){
                    etDate.setError("Date is required!");
                }
                if (etTime.getText().toString().equalsIgnoreCase("")){
                    etTime.setError("Time is required!");
                }
                if (etStreet.getText().toString().equalsIgnoreCase("")){
                    etStreet.setError("Street is required!");
                }
                if (etPostal.getText().toString().equalsIgnoreCase("")){
                    etPostal.setError("Postal Code is required!");
                }
                if (etContact.getText().toString().equalsIgnoreCase("")){
                    etContact.setError("Contact is required!");
                }
                if (etEmail.getText().toString().equalsIgnoreCase("")){
                    etEmail.setError("Email is required!");
                }
                if (etDate.getText().toString().equalsIgnoreCase("") && etTime.getText().toString().equalsIgnoreCase("") && etStreet.getText().toString().equalsIgnoreCase("") && etPostal.getText().toString().equalsIgnoreCase("") && etContact.getText().toString().equalsIgnoreCase("") && etEmail.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(Home.this, "Fill in all Required fields", Toast.LENGTH_LONG).show();
                } else{
                    startActivity(new Intent(Home.this, Pay.class));
                }
            }
        });

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(Home.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month +1;
                        String date = day + "/" + month + "/" + year;
                        etDate.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(Home.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay >= 12){
                            hourOfDay -= 12;
                            amPm = "PM";
                        } else{
                            amPm = "AM";
                        }
                        etTime.setText(String.format("%2d:%2d ", hourOfDay, minute) + amPm);
                    }
                }, currentHour, currentMinute, false);
                timePickerDialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                startActivity(new Intent(Home.this, Home.class));
                break;
            case R.id.sign_in:
                startActivity(new Intent(Home.this, MainActivity.class));
                break;
            case R.id.contact_us:
                startActivity(new Intent(Home.this, contactus.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
