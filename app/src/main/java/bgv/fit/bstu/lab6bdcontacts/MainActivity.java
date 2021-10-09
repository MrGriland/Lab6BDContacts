package bgv.fit.bstu.lab6bdcontacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bgv.fit.bstu.lab6bdcontacts.Classes.Contact;
import bgv.fit.bstu.lab6bdcontacts.Classes.DataItems;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatePicker datePicker = findViewById(R.id.datedp);
        datePicker.setMaxDate(new Date().getTime());
    }

    public void SaveToInternal(View view)
    {
        String fname = "6LabInt.json";
        if(!IntExists(fname))
        {
            createIntFile(fname);
        }
        List<Contact> contacts = new ArrayList<Contact>();
        File myFile = new File(getFilesDir().toString() + "/" + fname);
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
                if(dataItems.getContacts()!=null) {
                    for (Contact contact :
                            dataItems.getContacts()) {
                        contacts.add(contact);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        EditText name = findViewById(R.id.nameet);
        EditText surname = findViewById(R.id.surnameet);
        EditText phone = findViewById(R.id.phoneet);
        DatePicker datedp = findViewById(R.id.datedp);
        String date = String.valueOf(datedp.getDayOfMonth())+"."+String.valueOf(datedp.getMonth())+"."+String.valueOf(datedp.getYear());
        Contact contact = new Contact(name.getText().toString(),surname.getText().toString(),phone.getText().toString(),date);
        contacts.add(contact);
        if(!ExtExists(fname))
        {
            createExtFile(fname);
        }
        Gson gson = new Gson();
        DataItems dataItems = new DataItems();
        dataItems.setContacts(contacts);
        String jsonString = gson.toJson(dataItems);

        try {
            FileOutputStream outputStream = new FileOutputStream(myFile);
            /*
             * Буфферезируем данные из выходного потока файла
             */
            outputStream.write(jsonString.getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean IntExists(String fname)
    {
        boolean rc = false;
        File f = new File(super.getFilesDir(), fname);
        rc = f.exists();
        return rc;
    }

    public void createIntFile(String fname)
    {
        File f = new File(super.getFilesDir(), fname);
        try
        {
            f.createNewFile();
            Log.d("Activity","File "+fname+" has been created!");
        }
        catch (IOException e)
        {
            Log.d("Activity","File "+fname+" hasn't been created!");
        }
    }

    public void SaveToExternal(View view)
    {
        String fname = "6LabExt.json";
        if(!ExtExists(fname))
        {
            createExtFile(fname);
        }
        List<Contact> contacts = new ArrayList<Contact>();
        File myFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + fname);
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

        EditText name = findViewById(R.id.nameet);
        EditText surname = findViewById(R.id.surnameet);
        EditText phone = findViewById(R.id.phoneet);
        DatePicker datedp = findViewById(R.id.datedp);
        String date = String.valueOf(datedp.getDayOfMonth())+"."+String.valueOf(datedp.getMonth())+"."+String.valueOf(datedp.getYear());
        Contact contact = new Contact(name.getText().toString(),surname.getText().toString(),phone.getText().toString(),date);
        contacts.add(contact);
        if(!ExtExists(fname))
        {
            createExtFile(fname);
        }
        Gson gson = new Gson();
        DataItems dataItems = new DataItems();
        dataItems.setContacts(contacts);
        String jsonString = gson.toJson(dataItems);

        try {
            FileOutputStream outputStream = new FileOutputStream(myFile);
            /*
             * Буфферезируем данные из выходного потока файла
             */
            outputStream.write(jsonString.getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean ExtExists(String fname)
    {
        boolean rc = false;
        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fname);
        rc = f.exists();
        return rc;
    }

    public void createExtFile(String fname)
    {
        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fname);
        try
        {
            f.createNewFile();
            Log.d("Activity","File "+fname+" has been created!");
        }
        catch (IOException e)
        {
            Log.d("Activity","File "+fname+" hasn't been created!");
        }
    }

    public void Search(View view)
    {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
}