package sg.edu.rp.c346.fyp_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

public class EditBookings extends AppCompatActivity {
    EditText etD, etT, etST, etPC, etN,etC, etE;
    Button btnUpdate, btnCancel;
    Bookings bookings;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bookings);

        Intent i = getIntent();
        bookings = (Bookings) i.getSerializableExtra("booking");

        etD = findViewById(R.id.editTextEditDate);
        etT = findViewById(R.id.editTextEditStart);
        etST = findViewById(R.id.editTextEditStreet);
        etPC = findViewById(R.id.editTextEditPostal);
        etN = findViewById(R.id.editTextEditAddtional);
        etC = findViewById(R.id.editTextEditContact);
        etE = findViewById(R.id.editTextEditEmail);

        etD.setText(bookings.getDate());
        etT.setText(bookings.getTime());
        etST.setText(bookings.getStreet());
        etPC.setText(bookings.getPostal());
        etN.setText(bookings.getNotes());
        etC.setText(bookings.getContact());
        etE.setText(bookings.getEmail());

        db = FirebaseFirestore.getInstance();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                db.collection("Booking")

                finish();
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
