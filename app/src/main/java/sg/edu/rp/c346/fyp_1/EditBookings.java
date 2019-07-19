package sg.edu.rp.c346.fyp_1;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditBookings extends AppCompatActivity {
    EditText etD, etT, etST, etPC, etN, etC;
    Button btnUpdate, btnCancel;
    Bookings bookings;
    FirebaseFirestore db;

    Calendar calendar;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    int currentHour;
    int currentMinute;
    String amPm, email;
    Boolean D, T, S, P, A, C;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bookings);
        D = false;
        T = false;
        S = false;
        P = false;
        A = false;
        C = false;

        Intent i = getIntent();
        bookings = (Bookings) i.getSerializableExtra("booking");

        etD = findViewById(R.id.editTextEditDate);
        etT = findViewById(R.id.editTextEditStart);
        etST = findViewById(R.id.editTextEditStreet);
        etPC = findViewById(R.id.editTextEditPostal);
        etN = findViewById(R.id.editTextEditAddtional);
        etC = findViewById(R.id.editTextEditContact);
        btnUpdate = findViewById(R.id.buttonUpdate);
        btnCancel = findViewById(R.id.buttonCancel);

        etD.setText(bookings.getDate());
        etT.setText(bookings.getTime());
        etST.setText(bookings.getStreet());
        etPC.setText(bookings.getPostal());
        etN.setText(bookings.getNotes());
        etC.setText(bookings.getContact());
        email = bookings.getEmail();

        db = FirebaseFirestore.getInstance();

        etD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(EditBookings.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month +1;
                        String date = day + "/" + month + "/" + year;
                        etD.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        etT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(EditBookings.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay >= 12){
                            hourOfDay -= 12;
                            amPm = "PM";
                        } else{
                            amPm = "AM";
                        }
                        etT.setText(String.format("%2d:%2d ", hourOfDay, minute) + amPm);
                    }
                }, currentHour, currentMinute, false);
                timePickerDialog.show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etD.getText().toString().equals("")){
                    etD.setError("Date required!");
                } else{
                    D = true;
                }
                if (etT.getText().toString().equals("")){
                    etT.setError("Time required!");
                } else{
                    T = true;
                }
                if (etST.getText().toString().equals("")){
                    etST.setError("Street required!");
                } else{
                    S = true;
                }
                if (etPC.getText().toString().equals("")){
                    etPC.setError("Postal Code required!");
                } else if (etPC.length() != 6){
                    etPC.setError("Postal Code contain 6 characters");
                } else{
                    P = true;
                }
                if (etC.getText().toString().equals("")){
                    etC.setError("Contact required!");
                } else{
                    C = true;
                }

                if (D == true && T == true && S == true && P == true && C == true){
                    Map<String, Object> edit = new HashMap<>();
                    edit.put("Date", etD.getText().toString());
                    edit.put("Time", etT.getText().toString());
                    edit.put("Street", etST.getText().toString());
                    edit.put("Postal", etPC.getText().toString());
                    edit.put("Notes", etN.getText().toString());
                    edit.put("Contact", etC.getText().toString());

                    db.collection("Booking").document(email).update(edit)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(EditBookings.this, "Update Successful", Toast.LENGTH_SHORT).show();

                                    Intent i = new Intent();
                                    setResult(RESULT_OK, i);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(EditBookings.this, "Update Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else{
                    Toast.makeText(EditBookings.this, "Fill in all required fields correctly", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
