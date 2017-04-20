package br.com.fiap.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.OutputStream;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    Spinner spDispositivo;
    EditText textFormacao;

    //Variáveis necessárias para a comunicação Bluetooth
    BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
    BluetoothSocket soquete = null;
    OutputStream saida = null;
    Set<BluetoothDevice> dispositivosPareados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spDispositivo = (Spinner) findViewById(R.id.spDispositivos);
        textFormacao = (EditText) findViewById(R.id.txtInformacao);

        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adapter.add("Selecione um dispotivo");

        if (bluetooth != null){
            if (!bluetooth.isEnabled()){
                
            }
        }

    }

    public void enviar(View view) {

    }
}
