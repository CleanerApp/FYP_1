package sg.edu.rp.c346.fyp_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pay extends AppCompatActivity {
    Button btnDone, btnBack, btnFind;
    TextView tvFinal;
    EditText etPromo;
    Spinner spnType;
    Boolean Type;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Intent i;
    String service, date, time, street, postal, note, contact, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        Type = false;

        btnDone = findViewById(R.id.buttonDone);
        btnBack = findViewById(R.id.buttonBack);
        btnFind = findViewById(R.id.buttonFind);
        etPromo = findViewById(R.id.editTextPromo);
        spnType = findViewById(R.id.spinnerType);
        tvFinal = findViewById(R.id.textViewFinalPrice);

        i = getIntent();
        service = i.getStringExtra("Service");
        date = i.getStringExtra("Date");
        time = i.getStringExtra("Time");
        street = i.getStringExtra("Street");
        postal = i.getStringExtra("Postal");
        note = i.getStringExtra("Note");
        contact = i.getStringExtra("Contact");
        email = i.getStringExtra("Email");

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("Promo").document(etPromo.getText().toString()).get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        Double discount = document.getDouble("discount");
                                        Double price = 36 - discount;
                                        tvFinal.setText("S$ " + price);
                                        etPromo.setFocusable(false);
                                    }
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Pay.this, "Promo Code does not exist", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String select = spnType.getSelectedItem().toString();
                if (select.equalsIgnoreCase("Select a Payment Type")) {
                    Toast.makeText(Pay.this, "Select a Payment Type", Toast.LENGTH_SHORT).show();
                } else {
                    Type = true;
                }

                final Map<String, String> booking = new HashMap<>();
                if (note.isEmpty() == false) {
                    booking.put("Notes", note);
                } else {
                    booking.put("Notes", null);
                }
                booking.put("Service Type", service);
                booking.put("Date", date);
                booking.put("Time", time);
                booking.put("Street", street);
                booking.put("Postal Code", postal);
                booking.put("Contact", contact);
                booking.put("Email", email);
                booking.put("Cost", tvFinal.getText().toString());


                if (Type == true) {
                    booking.put("Payment Type", spnType.getSelectedItem().toString());
                    db.collection("Booking").document(email)
                            .set(booking).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(Pay.this, "Booking Recorded", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Pay.this, "Booking not Record", Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
