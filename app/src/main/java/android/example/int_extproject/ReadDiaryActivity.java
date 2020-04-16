package android.example.int_extproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadDiaryActivity extends AppCompatActivity {

    EditText enterDate;
    Button readFile;
    TextView fileContent;
    String fromWhere, fileName, dateStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_diary);

        enterDate = findViewById(R.id.enter_date_et);
        readFile = findViewById(R.id.read_from_file_btn);
        fileContent = findViewById(R.id.file_content_tv);

        Intent intent = getIntent();
        fromWhere = intent.getStringExtra("fromHere");

        readFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (enterDate.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fill the Date in the DD/MM/YYYY Format", Toast.LENGTH_SHORT).show();
                } else {
                    if (fromWhere.equalsIgnoreCase("internal")) {
                        readFromInternal();
                    } else {
                        readFromExternal();
                    }
                }
            }
        });
    }

    public void readFromInternal() {
        dateStr = enterDate.getText().toString();
        String[] parts = dateStr.split("/");
        fileName = parts[0] + parts[1] + parts[2] + ".txt";

        readFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileInputStream fileInputStream = openFileInput(fileName);
                    if (fileInputStream != null) {
                        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

                        String s = "";
                        int charRead;

                        while ((charRead = inputStreamReader.read()) != -1) {
                            s = s + (char) charRead;
                        }

                        inputStreamReader.close();
                        fileContent.setText(s);
                    } else {
                        Toast.makeText(getApplicationContext(), "No such file found", Toast.LENGTH_SHORT).show();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void readFromExternal() {
        dateStr = enterDate.getText().toString();
        String[] parts = dateStr.split("/");
        fileName = parts[0] + parts[1] + parts[2] + ".txt";

        try {
            File sdCard = Environment.getExternalStorageDirectory();
            File directory = new File(sdCard.getAbsolutePath()+"/MyDiary/");
            File textFile = new File(directory, fileName);
            FileInputStream fileInputStream = new FileInputStream(textFile);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

            String s = "";
            int charRead;

            while ((charRead = inputStreamReader.read()) != -1) {
                s = s + (char) charRead;
            }

            inputStreamReader.close();
            fileContent.setText(s);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
