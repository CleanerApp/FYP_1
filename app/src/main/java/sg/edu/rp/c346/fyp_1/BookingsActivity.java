package sg.edu.rp.c346.fyp_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
    Intent i, intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);

        lvBookings = findViewById(R.id.listViewBookings);
        db = FirebaseFirestore.getInstance();
        alBookings = new ArrayList<>();
        i = getIntent();

        aaBookings = new BookingAdapter(BookingsActivity.this, R.layout.row_bookings, alBookings);
        lvBookings.setAdapter(aaBookings);
        updateTable();

        lvBookings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bookings b = alBookings.get(position);
                Intent i = new Intent(BookingsActivity.this, EditBookings.class);
                i.putExtra("booking", b);
                startActivityForResult(i, 9);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            updateTable();
        }
    }

    private void updateTable() {
        db.collection("Booking").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            alBookings.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String service = (String) document.getData().get("Service Type");
                                String date = (String) document.getData().get("Date");
                                String time = (String) document.getData().get("Time");
                                String street = (String) document.getData().get("Street");
                                String postal_code = (String) document.getData().get("Postal Code");
                                String notes = (String) document.getData().get("Notes");
                                String contact = (String) document.getData().get("Contact");
                                String email = (String) document.getData().get("Email");
                                String cleaner = (String) document.getData().get("Name");
                                String cost = (String) document.getData().get("Cost");
                                String payment = (String) document.getData().get("Payment Type");
                                Bookings bb = new Bookings(service, date, time, street, postal_code, notes, contact, email, cleaner, cost, payment);
                                alBookings.add(bb);
                            }
                            aaBookings.notifyDataSetChanged();
                        }

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
                intent = new Intent(BookingsActivity.this, Home.class);
                intent.putExtra("sign", "guest");
                startActivity(intent);
                return true;
            case R.id.contact_us:
                intent = new Intent(BookingsActivity.this, contactus.class);
                intent.putExtra("sign", "guest");
                startActivity(intent);
                return true;
            case R.id.view_service:
                intent = new Intent(BookingsActivity.this, ServiceList.class);
                intent.putExtra("sign", "guest");
                startActivity(intent);
                return true;
            case R.id.view_bookings:
                intent = new Intent(BookingsActivity.this, BookingsActivity.class);
                intent.putExtra("sign", "guest");
                startActivity(intent);
                return true;
            case R.id.view_cleaner:
                intent = new Intent(BookingsActivity.this, CleanerInformation.class);
                intent.putExtra("sign", "guest");
                startActivity(intent);
                return true;
            case R.id.view_feedback:
                intent = new Intent(BookingsActivity.this, Feedback.class);
                intent.putExtra("sign", "guest");
                startActivity(intent);
                return true;
            case R.id.nav_change:
                startActivity(new Intent(BookingsActivity.this, UpdatePassword.class));
                return true;
            case R.id.logout:
                startActivity(new Intent(BookingsActivity.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
