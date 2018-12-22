package com.example.daniel.financasfinalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CadastroTransacao extends AppCompatActivity {
    EditText descrição;
    EditText local;
    EditText formapagamento;
    EditText valor;
    EditText data;
    Button salvar;
    Button cancelar;
    ArrayList<String> d;

    SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
    Date hoje = new Date();
    String dataFormatada = formataData.format(hoje);
    String[] dhoje=dataFormatada.split("/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_transacao);
        descrição = (EditText) findViewById(R.id.descrição);
        local = (EditText) findViewById(R.id.local);
        formapagamento = (EditText) findViewById(R.id.forma);
        valor = (EditText) findViewById(R.id.valor);
        data=(EditText)findViewById(R.id.data);
        salvar=(Button)findViewById(R.id.button1);
        cancelar=(Button)findViewById(R.id.button2);
        d=new ArrayList<String>();

        data.setText(Integer.valueOf(dhoje[0])+"/"+Integer.valueOf(dhoje[1])+"/"+Integer.valueOf(dhoje[2]));
    }
    public boolean verificaData(String data){
        String[] data1;
        data1=data.split("/");
        if(data1.length==3){

            if (    Integer.valueOf(data1[0]) > Integer.valueOf(dhoje[0]) &&
                    Integer.valueOf(data1[1]) >= Integer.valueOf(dhoje[1]) &&
                    Integer.valueOf(data1[2]) >= Integer.valueOf(dhoje[2]) ){

                Toast.makeText(this, "Esse dia ainda não chegou",Toast.LENGTH_SHORT).show();
                return false;
            }

            if(Integer.valueOf(data1[0]) < 0 ||Integer.valueOf(data1[0]) > 31){
                Toast toast = Toast.makeText(this, "Dia deve estar entre 0 a 31",Toast.LENGTH_SHORT);
                toast.show();
                return false;

            }
            if (Integer.valueOf(data1[1]) < 0 ||Integer.valueOf(data1[1]) > 12){
                Toast.makeText(this, "Mês inválido",Toast.LENGTH_SHORT).show();
                return false;
            }
            if (Integer.valueOf(data1[2]) < 1900 || Integer.valueOf(data1[2]) > Integer.valueOf(dhoje[2])){
                Toast.makeText(this, "Ano inválido",Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;

        }else{
            Toast toast = Toast.makeText(this, "Data em formato invalido. Tente: DD/MM/AAAA",Toast.LENGTH_LONG);
            toast.show();
            return false;
        }
    }

    public void Add(View v){
        if(descrição.getText().toString().equals("")||local.getText().toString().equals("")||valor.getText().equals("")||!verificaData(data.getText().toString())){
            if(descrição.getText().toString().equals("")||local.getText().toString().equals("")||valor.getText().equals("")|| data.getText().toString().equals("")){
                Toast.makeText(this, "Campos vazios",Toast.LENGTH_SHORT).show();
            }
        }else{
            d.add(descrição.getText().toString());
            d.add(local.getText().toString());
            d.add(formapagamento.getText().toString());
            d.add(valor.getText().toString());
            d.add(data.getText().toString());
            Intent data=new Intent();
            data.putStringArrayListExtra("Transacoes",d);
            setResult(RESULT_OK,data);
            finish();
        }
    }

    public void cancelar(View v){
        setResult(RESULT_CANCELED);
        finish();
    }
}
