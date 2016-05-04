package com.unlimitedappworks.u3_4;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText edt_nombre, edt_texto;
    private SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edt_nombre = (EditText) findViewById(R.id.edt_nombre);
        edt_texto = (EditText) findViewById(R.id.edt_texto);
        sh = getSharedPreferences("pref", 0);
        edt_nombre.setText(sh.getString("nombre", "archivo1"));
        edt_texto.setText(abrir(Environment.getExternalStorageDirectory().getPath() + "/" + edt_nombre.getText().toString() + ".txt"));
        Toast.makeText(MainActivity.this, "Abriendo archivo", Toast.LENGTH_SHORT).show();
        sh.edit().putString("nombre", edt_nombre.getText().toString()).commit();
    }

    public void botones(View view) {
        if (view.getId() == R.id.btn_abrir) {
            if (edt_nombre.getText().toString().length() > 0) {
                edt_texto.setText(abrir(Environment.getExternalStorageDirectory().getPath() + "/" + edt_nombre.getText().toString() + ".txt"));
                Toast.makeText(MainActivity.this, "Abriendo archivo", Toast.LENGTH_SHORT).show();
                sh.edit().putString("nombre", edt_nombre.getText().toString()).commit();
            } else {
                Toast.makeText(MainActivity.this, "Ingresa un nombre de archivo", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (edt_nombre.getText().toString().length() > 0) {
                guardar(Environment.getExternalStorageDirectory().getPath() + "/" + edt_nombre.getText().toString() + ".txt",
                        edt_texto.getText().toString(), false);
                Toast.makeText(MainActivity.this, "Guardando archivo", Toast.LENGTH_SHORT).show();
                sh.edit().putString("nombre", edt_nombre.getText().toString()).commit();
            } else {
                Toast.makeText(MainActivity.this, "Ingresa un nombre de archivo", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String abrir(String ruta) {
        try {
            File f = new File(ruta);
            FileReader fr = new FileReader(f);
            char[] buffer = new char[(int) f.length()];
            fr.read(buffer);
            fr.close();
            return new String(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void guardar(String ruta, String datos, boolean agregar) {
        try {
            FileWriter fw = new FileWriter(ruta);
            if (agregar) {
                fw.append(datos);
            } else {
                fw.write(datos);
            }
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
