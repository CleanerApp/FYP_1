package sg.edu.rp.c346.fyp_1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Pay extends AppCompatActivity {
    Button btnDone;
    TextView tvFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        btnDone = findViewById(R.id.buttonDone);
        tvFinal = findViewById(R.id.textViewFinalPrice);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        Intent i = getIntent();
        String date = i.getStringExtra("Date");
        String time = i.getStringExtra("Time");
        String street = i.getStringExtra("Street");
        String postal = i.getStringExtra("Postal");
        String note = i.getStringExtra("Note");
        String contact = i.getStringExtra("Contact");
        String email = i.getStringExtra("Email");

        final Map<String, String> booking = new HashMap<>();
        if (note.isEmpty() == false){
            booking.put("Notes", note);
        } else{
            booking.put("Notes", null);
        }
        booking.put("Service", "Clean - Room");
        booking.put("Date", date);
        booking.put("Time", time);
        booking.put("Street", street);
        booking.put("Postal", postal);
        booking.put("Contact", contact);
        booking.put("Email", email);
        booking.put("Cost", tvFinal.getText().toString());


        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("Booking")
                        .add(booking)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(Pay.this, "Service Booked", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Pay.this, "Service Failed to Book", Toast.LENGTH_LONG).show();
                            }
                        });
                finish();
            }
        });

    }

    @Override
    public void onBackPressed () {
        super.onBackPressed();
    }
}
