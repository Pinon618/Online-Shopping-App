package com.shopanbdecommerce.vendor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Second_Splash extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    private TextView appname;

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second__splash);
        appname= findViewById(R.id.appname);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();




        Typeface typeface = ResourcesCompat.getFont(this, R.font.blacklist);
        appname.setTypeface(typeface);

        YoYo.with(Techniques.Bounce)
                .duration(7000)
                .playOn(findViewById(R.id.logo));

        YoYo.with(Techniques.FadeInUp)
                .duration(5000)
                .playOn(findViewById(R.id.appname));

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if (firebaseUser!=null) {
                    startActivity(new Intent(Second_Splash.this, Main_Dash.class));
                    finish();
                }
                else {
                    startActivity(new Intent(Second_Splash.this, Login2.class));
                    finish();
                }




            }
        }, SPLASH_TIME_OUT);

    }
}
