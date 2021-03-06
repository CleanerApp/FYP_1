package sg.edu.rp.c346.fyp_1;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Home extends AppCompatActivity {
    TextView tvError;
    Spinner spn;
    List<String> serve;
    EditText etDate, etTime, etStreet, etPostal, etNote, etContact, etEmail;
    Button btnProceed;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    Calendar calendar;
    int currentHour;
    int currentMinute;
    String amPm;
    Boolean Service, Date, Time, Street, Postal, Contact, Email;
    Intent i, intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvError = findViewById(R.id.textViewInvisible);
        spn = findViewById(R.id.spinnerService);
        Service = false;
        etDate = findViewById(R.id.editTextDate);
        Date = false;
        etTime = findViewById(R.id.editTextStart);
        Time = false;
        etStreet = findViewById(R.id.editTextStreet);
        Street = false;
        etPostal = findViewById(R.id.editTextPostal);
        Postal = false;
        etNote = findViewById(R.id.editTextAddtional);
        etContact = findViewById(R.id.editTextContact);
        Contact = false;
        etEmail = findViewById(R.id.editTextEmail);
        Email = false;
        btnProceed = findViewById(R.id.button);

        i = getIntent();

        serve = new ArrayList<String>();
        serve.add("Select a Service");

        db.collection("Service").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document:task.getResult()){
                        String name = (String) document.getData().get("Name");
                        serve.add(name);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Home.this, android.R.layout.simple_spinner_item, serve);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn.setAdapter(adapter);
                }
            }
        });

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selected = spn.getSelectedItem().toString();
                if (selected.equalsIgnoreCase("Select a Service")){
                    tvError.setError("Select a Service");
                    Toast.makeText(Home.this, "Select a Service", Toast.LENGTH_SHORT).show();
                } else{
                    Service = true;
                }
                if (etDate.getText().toString().equals("")){
                    etDate.setError("Date is required!");
                } else{
                    Date = true;
                }
                if (etTime.getText().toString().equals("")){
                    etTime.setError("Time is required!");
                } else{
                    Time = true;
                }
                if (etStreet.getText().toString().equals("")){
                    etStreet.setError("Street is required!");
                } else{
                    Street = true;
                }
                if (etPostal.getText().toString().equals("")){
                    etPostal.setError("Postal Code is required!");
                } else if (etPostal.length() != 6){
                    etPostal.setError("Postal Code contain 6 characters");
                } else{
                    Postal = true;
                }
                if (etContact.getText().toString().equals("")){
                    etContact.setError("Contact is required!");
                } else if(etContact.length() != 8){
                    etContact.setError("Contact contains 8 numbers");
                } else{
                    Contact = true;
                }
                if (etEmail.getText().toString().equals("")){
                    etEmail.setError("Email is required!");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()){
                    etEmail.setError("Please enter a valid email address");
                } else{
                    Email = true;
                }
                if (Service == true && Date == true && Time == true && Street == true && Postal == true && Contact == true && Email == true) {
                    Intent intent = new Intent(Home.this, Pay.class);
                    intent.putExtra("Service", spn.getSelectedItem().toString());
                    intent.putExtra("Date", etDate.getText().toString());
                    intent.putExtra("Time", etTime.getText().toString());
                    intent.putExtra("Street", etStreet.getText().toString());
                    intent.putExtra("Postal", etPostal.getText().toString());
                    intent.putExtra("Note", etNote.getText().toString());
                    intent.putExtra("Contact", etContact.getText().toString());
                    intent.putExtra("Email", etEmail.getText().toString());
                    startActivity(intent);
                    spn.setSelection(0);
                    etDate.setText("");
                    etTime.setText("");
                    etStreet.setText("");
                    etPostal.setText("");
                    etNote.setText("");
                    etContact.setText("");
                    etEmail.setText("");
                    Service = false;
                    Date = false;
                    Time = false;
                    Street = false;
                    Postal = false;
                    Contact = false;
                    Email = false;
                } else{
                    Toast.makeText(Home.this, "Fill in all Required fields correctly", Toast.LENGTH_LONG).show();
                }
            }
        });

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
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
                        if (hourOfDay > 12){
                            hourOfDay -= 12;
                            amPm = "PM";
                        } else if (hourOfDay == 12){
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
        if (i.getExtras() != null){
            getMenuInflater().inflate(R.menu.guest_menu, menu);
        }else{
            getMenuInflater().inflate(R.menu.option_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                intent = new Intent(Home.this, Home.class);
                intent.putExtra("sign", "guest");
                startActivity(intent);
                return true;
            case R.id.contact_us:
                intent = new Intent(Home.this, contactus.class);
                intent.putExtra("sign", "guest");
                startActivity(intent);
                return true;
            case R.id.view_service:
                intent = new Intent(Home.this, ServiceList.class);
                intent.putExtra("sign", "guest");
                startActivity(intent);
                return true;
            case R.id.view_bookings:
                intent = new Intent(Home.this, BookingsActivity.class);
                intent.putExtra("sign", "guest");
                startActivity(intent);
                return true;
            case R.id.view_cleaner:
                intent = new Intent(Home.this, CleanerInformation.class);
                intent.putExtra("sign", "guest");
                startActivity(intent);
                return true;
            case R.id.view_feedback:
                intent = new Intent(Home.this, Feedback.class);
                intent.putExtra("sign", "guest");
                startActivity(intent);
                return true;
            case R.id.nav_change:
                startActivity(new Intent(Home.this, UpdatePassword.class));
                return true;
            case R.id.logout:
                startActivity(new Intent(Home.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
