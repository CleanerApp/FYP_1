package sg.edu.rp.c346.fyp_1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

import sg.edu.rp.c346.fyp_1.Common.Common;
import sg.edu.rp.c346.fyp_1.Model.UserProfile;

public class ProfileActivity extends AppCompatActivity {
    ImageView profilePic;
    TextView profileName, profileEmail, profileCode;
    Button logout, changePassword;

    FirebaseDatabase firebaseDatabase;
    CollectionReference colRef;
    DocumentReference docRef;
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    String name, email, password, securecode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
//
//        profilePic = findViewById(R.id.imageViewProfilePic);
//        profileName = findViewById(R.id.tvProfileName);
//        profileEmail = findViewById(R.id.tvProfileEmail);
//        profileCode = findViewById(R.id.tvSecureCode);


        logout = findViewById(R.id.btnLogout);
        changePassword = findViewById(R.id.btnChangePassword);


        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        //final String user_email = profileEmail.getText().toString();

//        Intent i  = getIntent();
//        email = i.getStringExtra("email");
//
//        colRef = db.collection("users");
//        docRef = colRef.document(email);
//        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
//                if (e != null){
//                    return;
//                }
//
//                if (snapshot != null && snapshot.exists()){
//                    String name = (String) snapshot.get("userName");
//                    String email = (String) snapshot.get("userEmail");
//                    String securecode = (String) snapshot.get("userSecureCode");
//
//                    profileName.setText("Name: " + name);
//                    profileEmail.setText("Email: " + email);
//                    profileCode.setText("Secure Code: " + securecode);
//                }
//            }
//        });




//        docRef.collection("users").document(user_email).set(userProfile).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//
//                profileName.setText("Name: " + userProfile.getUserName());
//                profileEmail.setText("Email: " + userProfile.getUserEmail());
//                profileCode.setText("Secure Code: " + userProfile.getUserSecureCode());
//
//            }
//        });

//
//
//        firebaseDatabase = FirebaseDatabase.getInstance();
//
//        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
//
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
//                profileName.setText("Name: " + userProfile.getUserName());
//                profileEmail.setText("Email: " + userProfile.getUserEmail());
//                profileCode.setText("Secure Code: " + userProfile.getUserSecureCode());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(ProfileActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
//
//            }
//        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout();
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, UpdatePassword.class));
            }
        });
    }

    private void Logout() {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
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
                startActivity(new Intent(ProfileActivity.this, Home.class));
                return true;
            case R.id.sign_in:
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                return true;
            case R.id.contact_us:
                startActivity(new Intent(ProfileActivity.this, contactus.class));
                return true;
            case R.id.view_service:
                startActivity(new Intent(ProfileActivity.this, ServiceList.class));
                return true;
            case R.id.view_bookings:
                startActivity(new Intent(ProfileActivity.this, BookingsActivity.class));
                return true;
            case R.id.view_cleaner:
                startActivity(new Intent(ProfileActivity.this, CleanerInformation.class));
                return true;
            case R.id.view_feedback:
                startActivity(new Intent(ProfileActivity.this, Feedback.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
