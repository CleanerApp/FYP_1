package sg.edu.rp.c346.fyp_1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class BookingsActivity extends AppCompatActivity {
    ListView lvBookings;
    FirebaseFirestore db;
    ArrayList<Bookings> alBookings;
    ArrayAdapter aaBookings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);

        lvBookings = findViewById(R.id.listViewBookings);
        db = FirebaseFirestore.getInstance();
        alBookings = new ArrayList<>();

        db.collection("Booking").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                String service = (String) document.getData().get("Service");
                                String date = (String) document.getData().get("Date");
                                String time = (String) document.getData().get("Time");
                                String street = (String) document.getData().get("Street");
                                String postal_code = (String) document.getData().get("Postal");
                                String notes = (String) document.getData().get("Notes");
                                String contact = (String) document.getData().get("Contact");
                                String email = (String) document.getData().get("Email");
                                String cleaner = (String) document.getData().get("Name");
                                Bookings bb = new Bookings(service, date, time, street, postal_code, notes, contact, email, cleaner);
                                alBookings.add(bb);
                            }
                        }
                        aaBookings = new BookingAdapter(BookingsActivity.this, R.layout.row_bookings, alBookings);
                        lvBookings.setAdapter(aaBookings);
                    }
                });

        lvBookings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bookings b = alBookings.get(position);
                Intent i = new Intent(BookingsActivity.this, EditBookings.class);
                i.putExtra("booking", b);
                startActivity(i);
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
                startActivity(new Intent(BookingsActivity.this, Home.class));
                return true;
            case R.id.sign_in:
                startActivity(new Intent(BookingsActivity.this, MainActivity.class));
                return true;
            case R.id.contact_us:
                startActivity(new Intent(BookingsActivity.this, contactus.class));
                return true;
            case R.id.view_service:
                startActivity(new Intent(BookingsActivity.this, ViewActivity.class));
                return true;
            case R.id.view_bookings:
                startActivity(new Intent(BookingsActivity.this, BookingsActivity.class));
                return true;
            case R.id.view_cleaner:
                startActivity(new Intent(BookingsActivity.this, CleanerInformation.class));
                return true;
            case R.id.view_feedback:
                startActivity(new Intent(BookingsActivity.this, Feedback.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
