package sg.edu.rp.c346.fyp_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class contactus extends AppCompatActivity {
TextView a, b, c;
FirebaseFirestore db = FirebaseFirestore.getInstance();
DocumentReference docRef = db.collection("Company").document("p4C5WYZfyCbGkXHsLdhm");
    Intent i, intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);

        i = getIntent();
        a = findViewById(R.id.textView6);
        b = findViewById(R.id.textView7);
        c = findViewById(R.id.textView8);

        onStart();


        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
//                    DocumentSnapshot document = documentSnapshot.getResult();
//                    if (document.exists()){
                    String name = documentSnapshot.getString("Address");
                    String contact = documentSnapshot.getString("Contact_no");
                    String email = documentSnapshot.getString("Email");
                    a.setText("Company Address: " + name);
                    b.setText("Contact: " + contact);
                    c.setText("Email: " + email);
                }


//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//
//

            }
        });
}




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if (i.getExtras() != null){
            inflater.inflate(R.menu.guest_menu, menu);
        }else{
            inflater.inflate(R.menu.option_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                intent = new Intent(contactus.this, Home.class);
                intent.putExtra("sign", "guest");
                startActivity(intent);
                return true;
            case R.id.contact_us:
                intent = new Intent(contactus.this, contactus.class);
                intent.putExtra("sign", "guest");
                startActivity(intent);
                return true;
            case R.id.view_service:
                intent = new Intent(contactus.this, ServiceList.class);
                intent.putExtra("sign", "guest");
                startActivity(intent);
                return true;
            case R.id.view_bookings:
                intent = new Intent(contactus.this, BookingsActivity.class);
                intent.putExtra("sign", "guest");
                startActivity(intent);
                return true;
            case R.id.view_cleaner:
                intent = new Intent(contactus.this, CleanerInformation.class);
                intent.putExtra("sign", "guest");
                startActivity(intent);
                return true;
            case R.id.nav_change:
                startActivity(new Intent(contactus.this, UpdatePassword.class));
                return true;
            case R.id.logout:
                startActivity(new Intent(contactus.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
