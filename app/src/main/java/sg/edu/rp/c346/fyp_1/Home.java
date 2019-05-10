package sg.edu.rp.c346.fyp_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.sign_in:
                Intent sign = new Intent(Home.this, MainActivity.class);
                startActivity(sign);
            case R.id.profile_page:
                Intent profile = new Intent(Home.this, ProfileActivity.class);
                startActivity(profile);
            case R.id.contact_us:
                Intent us = new Intent(Home.this, contactus.class);
                startActivity(us);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed () {
        super.onBackPressed();
    }
}
