package sg.edu.rp.c346.fyp_1;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.UserHandle;
import android.support.annotation.NonNull;
import android.support.test.espresso.remote.EspressoRemoteMessage;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import sg.edu.rp.c346.fyp_1.Common.Common;
import sg.edu.rp.c346.fyp_1.Model.UserProfile;

public class UpdatePassword extends AppCompatActivity {
    Button update;
    EditText etPassword, etNewPassword, etRepeatPassword, etEmail;
    FirebaseAuth firebaseAuth;
    DocumentReference docRef;
    FirebaseUser firebaseUser;
    Boolean Email, Old, New, Repeat;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        Email = false;
        Old = false;
        New = false;
        Repeat = false;

        update = findViewById(R.id.btnUpdatePassword);
        etPassword = findViewById(R.id.etPassword);
        etNewPassword = findViewById(R.id.etNewPassword);
        etRepeatPassword = findViewById(R.id.etRepeatPassword);
        etEmail = findViewById(R.id.etEmail);
        //firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etEmail.getText().toString().equals("")) {
                    etEmail.setError("Email is required");
                } else {
                    Email = true;
                }
                if (etPassword.getText().toString().equals("")) {
                    etPassword.setError("Old Password required");
                } else {
                    Old = true;
                }
                if (etNewPassword.getText().toString().equals("")) {
                    etNewPassword.setError("New Password required");
                } else if (!PASSWORD_PATTERN.matcher(etNewPassword.getText().toString()).matches()){
                    etNewPassword.setError("Password too weak, must contain at least one special character");
                } else{
                    New = true;
                }
                if (etRepeatPassword.getText().toString().equals("")) {
                    etRepeatPassword.setError("Repeat Password required");
                } else {
                    Repeat = true;
                }

                if (Email == true && Old == true && New == true && Repeat == true) {
                    final String user_email = etEmail.getText().toString().trim();
                    final String password = etPassword.getText().toString().trim();
                    final String newpassword = etNewPassword.getText().toString().trim();
                    final String repassword = etRepeatPassword.getText().toString().trim();

                    final FirebaseFirestore db = FirebaseFirestore.getInstance();
                    final DocumentReference docRef = db.collection("users").document(user_email);

                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    if (password.equals(document.getString("userPassword"))) {
                                        if (newpassword.equals(repassword)) {
                                            docRef.update("userPassword", newpassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(UpdatePassword.this, "Password changed", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(UpdatePassword.this, Home.class));
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(UpdatePassword.this, "Failed to update", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        } else {
                                            etRepeatPassword.setError("Password not same!");
                                        }
                                    } else {
                                        etPassword.setError("Not Old Password!");
                                    }
                                }
                            }
                        }
                    });
                } else{
                    Toast.makeText(UpdatePassword.this, "Fill in all required fields correctly", Toast.LENGTH_LONG).show();
                }
            }
        });


//
//
//        update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (etPassword.getText().toString().equals(Common.currentUser.getUserPassword())) {
//
//                    if (etNewPassword.getText().toString().equals(etRepeatPassword.getText().toString())) {
//                        Map<String, Object> passwordUpdate = new HashMap<>();
//                        passwordUpdate.put("userPassword", etNewPassword.getText().toString());
//
//                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//                        DatabaseReference myRef = firebaseDatabase.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//                        myRef.child(Common.currentUser.getUserName())
//                                .updateChildren(passwordUpdate)
//                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//
//                                        Toast.makeText(UpdatePassword.this, "Password has been updated", Toast.LENGTH_SHORT).show();
//                                        startActivity(new Intent(UpdatePassword.this, ProfileActivity.class));
//                                        finish();
//
//                                    }
//                                })
//                                .addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Toast.makeText(UpdatePassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//
//                                    }
//                                });
//
//                    } else {
//                        Toast.makeText(UpdatePassword.this, "New password does not match", Toast.LENGTH_SHORT).show();
//
//                    }
//
//                }
//
//                /*String newUserPassword = newPassword.getText().toString();
//                firebaseUser.updatePassword(newUserPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()){
//                            Toast.makeText(UpdatePassword.this, "Password has changed", Toast.LENGTH_SHORT).show();
//                            finish();
//                        } else {
//                            Toast.makeText(UpdatePassword.this, "Password update failed", Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//                });
//
//               */
//
//            }
//        });


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
                startActivity(new Intent(UpdatePassword.this, Home.class));
                return true;
            case R.id.contact_us:
                startActivity(new Intent(UpdatePassword.this, contactus.class));
                return true;
            case R.id.view_service:
                startActivity(new Intent(UpdatePassword.this, ServiceList.class));
                return true;
            case R.id.view_bookings:
                startActivity(new Intent(UpdatePassword.this, BookingsActivity.class));
                return true;
            case R.id.view_cleaner:
                startActivity(new Intent(UpdatePassword.this, CleanerInformation.class));
                return true;
            case R.id.nav_change:
                startActivity(new Intent(UpdatePassword.this, UpdatePassword.class));
                return true;
            case R.id.logout:
                startActivity(new Intent(UpdatePassword.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
