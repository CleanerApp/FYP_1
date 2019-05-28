package sg.edu.rp.c346.fyp_1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

import sg.edu.rp.c346.fyp_1.Model.UserProfile;

public class signup extends AppCompatActivity {

    EditText userName, userPassword, userEmail, userSecureCode;
    Button btnRegister;
    TextView userLogin;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore docRef;
    String email, name, password, securecode;

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
        setContentView(R.layout.activity_signup);



        userName = findViewById(R.id.etUserName);
        userPassword = findViewById(R.id.etUserPassword);
        userEmail = findViewById(R.id.etUserEmail);
        btnRegister = findViewById(R.id.btnRegister);
        userLogin = findViewById(R.id.tvUserLogin);
        userSecureCode = findViewById(R.id.etSecureCode);


        firebaseAuth = FirebaseAuth.getInstance();
        docRef = FirebaseFirestore.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (validate()) {
                    final String user_email = userEmail.getText().toString().trim();
                    final String user_password = userPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //sendEmailVerification();
                                //sendUserData();
                                UserProfile userProfile = new UserProfile(email, name, password, securecode);
                                docRef.collection("users").document(user_email).set(userProfile).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(signup.this, "Successfully Registered, Upload complete", Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(new Intent(signup.this, MainActivity.class));
                                    }
                                });


                            } else {
                                Toast.makeText(signup.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }
            }
        });

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signup.this, MainActivity.class));
            }
        });
    }


    private Boolean validate() {
        Boolean result = false;

        name = userName.getText().toString();
        password = userPassword.getText().toString();
        email = userEmail.getText().toString();
        securecode = userSecureCode.getText().toString();


        if (!validateEmail() | !validateUsername() | !validatePassword() | !validCode()) {
            //Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }

        return result;
    }

    private boolean validateEmail() {
        String emailInput = userEmail.getText().toString().trim();

        if (emailInput.isEmpty()) {
            userEmail.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            userEmail.setError("Please enter a valid email address");
            return false;
        } else {
            userEmail.setError(null);
            return true;
        }
    }

    private boolean validateUsername() {
        String usernameInput = userName.getText().toString().trim();

        if (usernameInput.isEmpty()) {
            userName.setError("Field can't be empty");
            return false;
        } else if (usernameInput.length() > 15) {
            userName.setError("Username must be not more than 15 characters");
            return false;
        } else {
            userName.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = userPassword.getText().toString().trim();

        if (passwordInput.isEmpty()) {
            userPassword.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            userPassword.setError("Password too weak, must contain at least one special character");
            return false;
        } else {
            userPassword.setError(null);
            return true;
        }
    }

    private boolean validCode() {
        String codeInput = userSecureCode.getText().toString().trim();
        if (codeInput.isEmpty()) {
            userSecureCode.setError("Field can't be empty");
            return false;
        } else if (userSecureCode.length() > 8) {
                userSecureCode.setError("Code must be less than 8 characters");
                return false;
        } else {
            userSecureCode.setError(null);
            return true;
        }
    }




    private void sendEmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        sendUserData();
                        Toast.makeText(signup.this, "Successfully Registered, Verification mail sent!", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(signup.this, MainActivity.class));
                    } else {
                        Toast.makeText(signup.this, "Verification mail has'nt been sent!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }

    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        UserProfile userProfile = new UserProfile(email, name, password, securecode);
        myRef.setValue(userProfile);
    }

    public void computerMD5Hash(String password){
        try{
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer MD5Hash = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++){
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2){
                    h = "0" + h;
                    MD5Hash.append(h);

                }


                userPassword.setText(MD5Hash);

            }



        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }
}




