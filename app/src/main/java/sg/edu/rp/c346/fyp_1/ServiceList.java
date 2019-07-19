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

public class ServiceList extends AppCompatActivity {
    ListView lvService;
    ArrayList<Service> alService;
    ArrayAdapter aaService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);
        lvService = findViewById(R.id.listViewService);

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        alService = new ArrayList<>();

        db.collection("Service")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String name = (String) document.getData().get("Name");
                                String detail = (String) document.getData().get("Detail");
                                String cost = (String) document.getData().get("Cost");
                                String cleaners = (String) document.getData().get("No_of_Cleaner");
                                Service service = new Service(name, detail, cost, cleaners);
                                alService.add(service);
                            }
                        }
                        aaService = new ServiceAdapter(ServiceList.this, R.layout.row_service, alService);
                        lvService.setAdapter(aaService);
                    }
                });

        lvService.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Service selected = alService.get(position);
                Intent intent = new Intent(ServiceList.this, ViewActivity.class);
                intent.putExtra("select", selected);
                startActivity(intent);
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
                startActivity(new Intent(ServiceList.this, Home.class));
                return true;
            case R.id.sign_in:
                startActivity(new Intent(ServiceList.this, MainActivity.class));
                return true;
            case R.id.contact_us:
                startActivity(new Intent(ServiceList.this, contactus.class));
                return true;
            case R.id.view_service:
                startActivity(new Intent(ServiceList.this, ServiceList.class));
                return true;
            case R.id.view_bookings:
                startActivity(new Intent(ServiceList.this, BookingsActivity.class));
                return true;
            case R.id.view_cleaner:
                startActivity(new Intent(ServiceList.this, CleanerInformation.class));
                return true;
            case R.id.view_feedback:
                startActivity(new Intent(ServiceList.this, Feedback.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
