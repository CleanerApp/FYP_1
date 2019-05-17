package sg.edu.rp.c346.fyp_1;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ViewActivity extends AppCompatActivity {
    ImageView iv;
    TextView tvName, tvCost, tvDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        tvName = findViewById(R.id.textViewName);
        tvCost = findViewById(R.id.textViewCost);
        tvDetail = findViewById(R.id.textViewDetail);

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference docRef = db.collection("Service").document("DOTlnSFpzK59YGnsmy6w");

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()){
                        tvName.setText(document.getString("Name"));
                        tvCost.setText(document.getString("Cost"));
                        tvDetail.setText(document.getString("Detail"));
                    } else{
                        Toast.makeText(ViewActivity.this, "Service does not exist", Toast.LENGTH_SHORT);
                    }
                }
            }
        });

    }
}
