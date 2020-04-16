package android.example.int_extproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.util.Date;

public class InternalStorageActivity extends AppCompatActivity {

    EditText intDate, intText;
    Button intRead, intWrite;
    String fileName, dateStr;

    private final static String fromInternal = "Internal";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal_storage);

        intDate = findViewById(R.id.int_date_et);
        intText = findViewById(R.id.int_text_et);
        intRead = findViewById(R.id.int_read_btn);
        intWrite = findViewById(R.id.int_write_btn);

        intRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReadDiaryActivity.class);
                intent.putExtra("fromHere", fromInternal);
                startActivity(intent);
            }
        });

        intWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToInternal();
            }
        });
    }

    public void writeToInternal() {

        if (intDate.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Fill the Date in the DD/MM/YYYY Format", Toast.LENGTH_SHORT).show();
        }
        else {
            dateStr = intDate.getText().toString();
            String[] parts = dateStr.split("/");
            fileName = parts[0] + parts[1] + parts[2] + ".txt";
            try {
                FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_PRIVATE);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                outputStreamWriter.write(intText.getText().toString());
                outputStreamWriter.close();
                Toast.makeText(getApplicationContext(), "Wrote to your diary", Toast.LENGTH_SHORT).show();
                }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
