package br.com.fiap.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import static br.com.fiap.bluetooth.R.id.txtInformacao;

public class MainActivity extends AppCompatActivity {

    Spinner spDispositivo;
    EditText txtInformacao;

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
        txtInformacao = (EditText) findViewById(R.id.txtInformacao);

        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adapter.add("Selecione um dispotivo");

        if (bluetooth != null){
            if (!bluetooth.isEnabled()){
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                int REQUEST_ENABLE_BT = 1;
                startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            }
            // retorna todos os dispositivos ja pareados no aparelho
            dispositivosPareados = bluetooth.getBondedDevices();

            if (dispositivosPareados.size() > 8){
                for (BluetoothDevice item : dispositivosPareados){
                    adapter.add(item.getName());
                }
            }
        }
        spDispositivo.setAdapter(adapter);
    }

    public void enviar(View view) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        for (BluetoothDevice item : dispositivosPareados){
            if (spDispositivo.getSelectedItem().toString().equalsIgnoreCase(item.getName())){
                try{
                    BluetoothDevice dispotivoRemoto = bluetooth.getRemoteDevice(item.getAddress());
                    soquete = criarSoqueteBluetooth(dispotivoRemoto);
                    soquete.connect();

                    bluetooth.cancelDiscovery();

                    saida = soquete.getOutputStream();

                    byte[] buffer = txtInformacao.getText().toString().getBytes();
                    saida.write(buffer);

                    saida.close();
                    soquete.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private BluetoothSocket criarSoqueteBluetooth(BluetoothDevice dispositivo) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method metodo;
        BluetoothSocket tempSoquete = null;

        try{
            metodo = dispositivo.getClass().getMethod("createRfcommSocket", new Class[] {int.class});
            tempSoquete = (BluetoothSocket) metodo.invoke(dispositivo, 1);
        }finally {
            return  tempSoquete;
        }
    }
}
