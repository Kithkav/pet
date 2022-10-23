package com.example.barber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

//import com.example.barber.Appointment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Activity1 extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);

        drawerLayout =findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.navigationView);
        actionBarDrawerToggle =new ActionBarDrawerToggle(this,drawerLayout, R.string.menu_Open, R.string.close_menu);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_home:
                        Log.i("MENU_DRAWER_TAG", "Home item is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent i1=new Intent(Activity1.this, ProfileActivity.class);
                        startActivity(i1);

                        break;

                    case R.id.nav_profile:
                        Log.i("MENU_DRAWER_TAG", "Profile item is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);

                        Intent intent=new Intent(Activity1.this, ProfileActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_reminders:
                        Log.i("MENU_DRAWER_TAG", "Reminder item is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);

                        Intent intent1=new Intent(Activity1.this, MainActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.nav_appoitment:
                        Log.i("MENU_DRAWER_TAG", "Appointment item is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent i=new Intent(Activity1.this, Appointment.class);
                        startActivity(i);
                        break;

                    case R.id.nav_logOut:
                        Log.i("MENU_DRAWER_TAG", "Log Out item is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        FirebaseAuth.getInstance().signOut();
                        Intent loginActivity = new Intent(getApplicationContext(),Signup.class);
                        startActivity(loginActivity);
                        finish();

//                        Intent a=new Intent(Activity1.this, Signup.class);
//                        startActivity(a);
//                        Toast.makeText(Activity1.this, "Logging out !", Toast.LENGTH_SHORT).show();                        break;
                }


                return true;
            }
        });

    }
}

