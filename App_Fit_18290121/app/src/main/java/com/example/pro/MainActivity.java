package com.example.pro;
//Yahya Baturay Saraçoğlu
//18290121
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Stetho.initializeWithDefaults(this);

        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        DBAdapter db = new DBAdapter(this);
        db.open();

        int numberRows = db.count("food");

        if(numberRows < 1){

            DBSetupInsert setupInsert = new DBSetupInsert(this);
            setupInsert.insertAllCategories();
            setupInsert.insertAllFood();

        }

        numberRows = db.count("users");

        db.close();

        if(numberRows < 1){

            Intent i = new Intent(MainActivity.this, SignUp.class);
            startActivity(i);
        }
        else{
            Intent i = new Intent(MainActivity.this, FragmentActivity.class);
            startActivity(i);

        }
    }
}