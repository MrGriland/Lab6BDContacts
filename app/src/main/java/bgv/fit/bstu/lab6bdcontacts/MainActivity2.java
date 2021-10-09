package bgv.fit.bstu.lab6bdcontacts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import bgv.fit.bstu.lab6bdcontacts.Classes.Contact;
import bgv.fit.bstu.lab6bdcontacts.Classes.DataItems;

public class MainActivity2 extends AppCompatActivity {

    List<Contact> contacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        contacts = Read();
    }

    public List<Contact> Read()
    {
        List<Contact> contacts = new ArrayList<Contact>();
        File myFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + "6LabExt.json");
        try {
            FileInputStream inputStream = new FileInputStream(myFile);
            /*
             * Буфферезируем данные из выходного потока файла
             */
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            /*
             * Класс для создания строк из последовательностей символов
             */
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            try {
                /*
                 * Производим построчное считывание данных из файла в конструктор строки,
                 * Псоле того, как данные закончились, производим вывод текста в TextView
                 */
                while ((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line);
                }
                Gson gson = new Gson();
                DataItems dataItems = gson.fromJson(stringBuilder.toString(), DataItems.class);
                for (Contact contact:
                        dataItems.getContacts()) {
                    contacts.add(contact);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    public void Find(View view)
    {
        EditText sn = findViewById(R.id.snet);
        TextView info = findViewById(R.id.infotv);
        for (Contact c:
             contacts) {
            if (c.name.equals(sn.getText().toString()) || c.surname.equals(sn.getText().toString()))
            {
                info.setText(c.name+" "+c.surname+" "+c.phone+" "+c.date.toString());
            }
        }

    }
}