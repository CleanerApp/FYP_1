package sg.edu.rp.c346.fyp_1;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rengwuxian.materialedittext.MaterialEditText;

import sg.edu.rp.c346.fyp_1.Model.UserProfile;


public class MainActivity extends AppCompatActivity {
    EditText edtUser, edtPassword;
    TextView userSignUp, tvForgotPassword, tvGuest;
    Button btnLogin;
    ProgressDialog progressDialog;
    Boolean Username, Password, Email, SecureCode;
    AlertDialog ad;
    FirebaseFirestore docRef;
    FirebaseAuth firebaseAuth;
    String email, name, password, securecode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Username = false;
        Password = false;
        Email = false;
        SecureCode = false;

        edtUser = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        userSignUp = findViewById(R.id.tvSignUp);
        btnLogin = findViewById(R.id.btnlogin);
        tvForgotPassword = findViewById(R.id.tvForgotPwd);
        tvGuest = findViewById(R.id.textViewGuest);

        progressDialog = new ProgressDialog(this);

        /*if (user != null){
            finish();
            startActivity(new Intent(MainActivity.this, SecondActivity.class));
        }
        */

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtUser.getText().toString().equals("")) {
                    edtUser.setError("Email required");
                } else {
                    Username = true;
                }
                if (edtPassword.getText().toString().equals("")) {
                    edtPassword.setError("Password required");
                } else {
                    Password = true;
                }
                if (Username == true && Password == true) {
                    validate(edtUser.getText().toString(), edtPassword.getText().toString());
                } else {
                    Toast.makeText(MainActivity.this, "Fill in all fields correctly", Toast.LENGTH_SHORT).show();
                }
            }
        });

        userSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, signup.class));

            }
        });

        tvGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Home.class);
                intent.putExtra("sign", "guest");
                startActivity(intent);
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showForgotPwdDialog();
            }
        });


    /*tvsignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent2 = new Intent (MainActivity.this, forgotpass.class);
                    startActivity(intent2);
                }
            });
*/
    }

    private void showForgotPwdDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Forgot Password");
        builder.setMessage("Enter your secret code");

        LayoutInflater inflater = this.getLayoutInflater();
        View forgot_view = inflater.inflate(R.layout.activity_forgotpass, null);

        builder.setView(forgot_view);
        builder.setIcon(R.drawable.ic_security_black_24dp);

        final MaterialEditText etEmail = forgot_view.findViewById(R.id.etEmail);
        final MaterialEditText etSecureCode = forgot_view.findViewById(R.id.etSecureCode);

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (etEmail.getText().toString().equals("")) {
                    etEmail.setError("Email required");
                } else {
                    Email = true;
                }
                if (etSecureCode.getText().toString().equals("")) {
                    etSecureCode.setError("Secure Code required");
                } else {
                    SecureCode = true;
                }

                if (Email == true && SecureCode == true) {
                    db.collection("users").document(etEmail.getText().toString())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            if (etEmail.getText().toString().equals(document.getString("userEmail"))) {
                                                if (etSecureCode.getText().toString().equals(document.getString("userSecureCode"))) {
                                                    db.collection("users").document(etEmail.getText().toString())
                                                            .update("userPassword", "Password123");
                                                    String password = document.getString("userPassword");
                                                    Toast.makeText(MainActivity.this, "The password is: Password123", Toast.LENGTH_LONG).show();
                                                } else {
                                                    Toast.makeText(MainActivity.this, "Wrong secure code", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Toast.makeText(MainActivity.this, "Wrong Username", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                }
                            });
                } else{
                    Toast.makeText(MainActivity.this, "Fill in all required fields correctly", Toast.LENGTH_LONG).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }


    private void validate(final String userEmail, final String userPassword) {
        progressDialog.setMessage("Verifying your account.... ");
        progressDialog.show();

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference docRef = db.collection("users").document(userEmail);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        if (userEmail.equals(document.getString("userEmail"))) {
                            if (userPassword.equals(document.getString("userPassword"))) {
                                progressDialog.dismiss();
//                                Intent intent = new Intent(MainActivity.this, Home.class);
//                                intent.putExtra("email", edtUser.getText().toString());
//                                startActivity(intent);
                                startActivity(new Intent(MainActivity.this, Home.class));
                                //checkEmailVerification();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(MainActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    /*private void checkEmailVerification() {
        Boolean emailflag = firebaseUser.isEmailVerified();

        if(emailflag){
            finish();
            startActivity(new Intent(MainActivity.this, SecondActivity.class));
        }  else {
            Toast.makeText(this, "Verify your email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }

    }*/

    private Boolean validate() {
        Boolean result = false;

        name = edtUser.getText().toString();
        password = edtPassword.getText().toString();


        return result;
    }
}