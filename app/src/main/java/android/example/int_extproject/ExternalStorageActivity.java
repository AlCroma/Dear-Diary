package android.example.int_extproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class ExternalStorageActivity extends AppCompatActivity {

    EditText extDate, extText;
    Button extRead, extWrite;
    String fileName, dateStr;

    private final static String fromExternal = "External";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_storage);

        extDate = findViewById(R.id.ext_date_et);
        extText = findViewById(R.id.ext_text_et);
        extRead = findViewById(R.id.ext_read_btn);
        extWrite = findViewById(R.id.ext_write_btn);

        extRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReadDiaryActivity.class);
                intent.putExtra("fromHere", fromExternal);
                startActivity(intent);
            }
        });

        extWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToExternal();
            }
        });
    }

    public void writeToExternal() {
        if (extDate.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Fill the Date in the DD/MM/YYYY Format", Toast.LENGTH_SHORT).show();
        } else {
            dateStr = extDate.getText().toString();
            String[] parts = dateStr.split("/");
            fileName = parts[0] + parts[1] + parts[2] + ".txt";
            if (isWritable() && checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                File sdCard = Environment.getExternalStorageDirectory();
                File directory = new File(sdCard.getAbsolutePath() + "/MyDiary/");
                directory.mkdir();
                File textFile = new File(directory, fileName);
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream(textFile);
                    fileOutputStream.write(extText.getText().toString().getBytes());
                    fileOutputStream.close();
                    Toast.makeText(getApplicationContext(), "Wrote to your diary", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(), "No writable external storage/Permission not granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isWritable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            Log.v("State", "Yes, Writable Storage is Present");
            return true;
        } else {
            return false;
        }
    }

    public boolean checkPermission(String permission) {
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }
}
