package sg.edu.rp.c346.fyp_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class contactus extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);
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
