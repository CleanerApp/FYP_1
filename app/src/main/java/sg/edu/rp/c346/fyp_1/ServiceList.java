package sg.edu.rp.c346.fyp_1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ServiceList extends AppCompatActivity {
    private static final String TAG = "ServiceList";
    ListView lvService;
    ArrayList<String> alService;
    ArrayAdapter<String> aaService;

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
                                alService.add(name);
                            }
                        }
                        aaService = new ArrayAdapter<String>(ServiceList.this, android.R.layout.simple_list_item_1, alService);
                        lvService.setAdapter(aaService);
                    }
                });

        lvService.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Service selected = alService.get(position);
                Intent intent = new Intent(ServiceList.this, ViewActivity.class);
//                intent.putExtra("select", selected);
                startActivity(intent);
            }
        });
    }
}
