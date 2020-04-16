package android.example.int_extproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button intStorage, extStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intStorage = findViewById(R.id.int_strg_btn);
        extStorage = findViewById(R.id.ext_strg_btn);

        intStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intStorageActivity = new Intent(getApplicationContext(), InternalStorageActivity.class);
                startActivity(intStorageActivity);
            }
        });

        extStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent extStorageActivity = new Intent(getApplicationContext(), ExternalStorageActivity.class);
                startActivity(extStorageActivity);
            }
        });
    }

}
