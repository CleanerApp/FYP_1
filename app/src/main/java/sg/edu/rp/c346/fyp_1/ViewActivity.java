package sg.edu.rp.c346.fyp_1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
        final DocumentReference docRef = db.collection("Service").document("5NBw3RyA1BTYxeH6kFdZ");

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    tvName.setText(documentSnapshot.getString("Name"));
                    tvCost.setText(documentSnapshot.getString("Cost"));
                    tvDetail.setText(documentSnapshot.getString("Detail"));
                }
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
                startActivity(new Intent(ViewActivity.this, Home.class));
                return true;
            case R.id.sign_in:
                startActivity(new Intent(ViewActivity.this, MainActivity.class));
                return true;
            case R.id.contact_us:
                startActivity(new Intent(ViewActivity.this, contactus.class));
                return true;
            case R.id.view_service:
                startActivity(new Intent(ViewActivity.this, ViewActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
