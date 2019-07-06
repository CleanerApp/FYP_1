package sg.edu.rp.c346.fyp_1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CleanerInformation extends AppCompatActivity {

    ListView lvCleaner;
    FirebaseFirestore db;
    ArrayList<Cleaner> alCleaner;
    ArrayAdapter aaCleaner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaner_information);

        lvCleaner = findViewById(R.id.lvInformation);
        db = FirebaseFirestore.getInstance();
        alCleaner = new ArrayList<>();

        db.collection("Cleaner").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String name = (String) document.getData().get("Name");
                                String age = (String) document.getData().get("Age");
                                String gender = (String) document.getData().get("Gender");
                                String contact = (String) document.getData().get("Contact");
                                String rate = (String) document.getData().get("RatePerHour");
                                String experience = (String) document.getData().get("Experience");
                                Cleaner cleaner = new Cleaner(name, age, gender, contact, rate, experience);
                                alCleaner.add(cleaner);
                            }
                        }

                        aaCleaner = new CleanerAdapter(CleanerInformation.this, R.layout.row_cleaner_information, alCleaner);
                        lvCleaner.setAdapter(aaCleaner);

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
                startActivity(new Intent(CleanerInformation.this, Home.class));
                return true;
            case R.id.sign_in:
                startActivity(new Intent(CleanerInformation.this, MainActivity.class));
                return true;
            case R.id.contact_us:
                startActivity(new Intent(CleanerInformation.this, contactus.class));
                return true;
            case R.id.view_service:
                startActivity(new Intent(CleanerInformation.this, ViewActivity.class));
                return true;
            case R.id.view_bookings:
                startActivity(new Intent(CleanerInformation.this, BookingsActivity.class));
                return true;
            case R.id.view_cleaner:
                startActivity(new Intent(CleanerInformation.this, CleanerInformation.class));
                return true;
            case R.id.view_profile:
                startActivity(new Intent(CleanerInformation.this, ProfileActivity.class));
                return true;
            case R.id.view_feedback:
                startActivity(new Intent(CleanerInformation.this, Feedback.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
