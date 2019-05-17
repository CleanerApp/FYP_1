package sg.edu.rp.c346.fyp_1;

import android.content.Intent;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;



public class contactus extends AppCompatActivity {
TextView a, b, c;
FirebaseFirestore db = FirebaseFirestore.getInstance();
DocumentReference docRef = db.collection("Company").document("p4C5WYZfyCbGkXHsLdhm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);

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
                    a.setText(name);
                    b.setText(contact);
                    c.setText(email);
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
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Intent home = new Intent(contactus.this, Home.class);
                startActivity(home);
            case R.id.sign_in:
                Intent sign = new Intent(contactus.this, MainActivity.class);
                startActivity(sign);
            case R.id.contact_us:
                Intent us = new Intent(contactus.this, contactus.class);
                startActivity(us);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
