package sg.edu.rp.c346.fyp_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
    Intent i, intent;
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
        i = getIntent();

        db.collection("Cleaner").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String name = (String) document.getData().get("Name");
                                String age = (String) document.getData().get("Age");
                                String gender = (String) document.getData().get("Gender");
                                String contact = (String) document.getData().get("Contacts");
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
                intent = new Intent(CleanerInformation.this, Home.class);
                intent.putExtra("sign", "guest");
                startActivity(intent);
                return true;
            case R.id.contact_us:
                intent = new Intent(CleanerInformation.this, contactus.class);
                intent.putExtra("sign", "guest");
                startActivity(intent);
                return true;
            case R.id.view_service:
                intent = new Intent(CleanerInformation.this, ServiceList.class);
                intent.putExtra("sign", "guest");
                startActivity(intent);
                return true;
            case R.id.view_bookings:
                intent = new Intent(CleanerInformation.this, BookingsActivity.class);
                intent.putExtra("sign", "guest");
                startActivity(intent);
                return true;
            case R.id.view_cleaner:
                intent = new Intent(CleanerInformation.this, CleanerInformation.class);
                intent.putExtra("sign", "guest");
                startActivity(intent);
                return true;
            case R.id.view_feedback:
                intent = new Intent(CleanerInformation.this, Feedback.class);
                intent.putExtra("sign", "guest");
                startActivity(intent);
                return true;
            case R.id.nav_change:
                startActivity(new Intent(CleanerInformation.this, UpdatePassword.class));
                return true;
            case R.id.logout:
                startActivity(new Intent(CleanerInformation.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
