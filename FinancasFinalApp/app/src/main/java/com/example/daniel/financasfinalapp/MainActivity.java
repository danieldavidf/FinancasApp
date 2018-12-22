package com.example.daniel.financasfinalapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {
    TransacaoDAO dao;
    SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
    Date data = new Date();
    String dataFormatada = formataData.format(data);
    Transacao aux;

    ListView list;
    ArrayList<String> d;
    EditText dataselecionada;

    private TransacaoAdapter adapter;
    List<Transacao> transacoes =new ArrayList<Transacao>();
    List<Transacao> dadosMostrados=new ArrayList<Transacao>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dao=new TransacaoDAO(this);
        dao.open();
        atualizaDados();
        dataselecionada=(EditText)findViewById(R.id.editText);
        dataselecionada.setText(dataFormatada);
        list = (ListView)findViewById(R.id.listview);
        selecionar();
        adapter=new TransacaoAdapter(this, dadosMostrados);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                aux=dadosMostrados.get(pos);

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

                alertDialog.setTitle("Deletando item")
                    .setMessage("Tem certeza que deseja deletar "+aux.getDescrição()+"?")
                    .setPositiveButton("sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dao.remove(aux);
                            atualizaDados();
                            selecionar();
                            adapter.notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("não", null)
                    .show();
            }
        });
        dataselecionada.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

            if (event.getAction() == KeyEvent.ACTION_DOWN) {

                if ( keyCode == KeyEvent.KEYCODE_ENTER  && verificaData(dataselecionada.getText().toString())) {
                    selecionar();
                    adapter.notifyDataSetChanged();
                    v.clearFocus();
                    ((InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                            dataselecionada.getWindowToken(), 0);
                    return true;
                }
            }
            return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        dao.open();
        atualizaDados();
        selecionar();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dao.close();
    }

    private void atualizaDados() {
        transacoes = dao.getAll();
    }

    public void adicionarTransacao(View v) {
        Intent intent2 = new Intent(this, CadastroTransacao.class);
        intent2.putStringArrayListExtra("Transacoes",d);
        startActivityForResult(intent2, 1234);
    }

    public void filtrarDados(View v) {
        Intent intentDosFiltros = new Intent(this, Filtro.class);
        startActivity(intentDosFiltros);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1234 && resultCode == RESULT_OK){
            d=data.getStringArrayListExtra("Transacoes");
            String dia=d.get(4).split("/")[0];
            String mes=d.get(4).split("/")[1];
            String ano=d.get(4).split("/")[2];
            dao.insert(new Transacao(d.get(0),d.get(1),d.get(2),Double.valueOf(d.get(3)),dia,mes,ano,(long) 0));
            atualizaDados();
            selecionar();
            adapter.notifyDataSetChanged();
        }
    }
    public void selecionar(){
        dadosMostrados.removeAll(dadosMostrados);
        if((transacoes.size()>0)){
            double total = 0;
            String data;
            for (Transacao ds:transacoes ) {
                data=ds.getDia()+"/"+ds.getMes()+"/"+ds.getAno();
                if(data.equals(dataselecionada.getText().toString()) && !dadosMostrados.contains(ds))dadosMostrados.add(ds);

            }
            if (dadosMostrados.size() != 0)
                for(Transacao ds : dadosMostrados)
                    total+=ds.getValor();
            dadosMostrados.add(new Transacao("TOTAL","","",total,"","","",-1));
        }

    }

    public boolean verificaData(String data){
        String[] data1;
        data1=data.split("/");
        String[] dhoje=dataFormatada.split("/");
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
            dataselecionada.setText(dataFormatada);
            Toast toast = Toast.makeText(this, "Data em formato invalido.\nTente: DD/MM/AAAA",Toast.LENGTH_LONG);
            toast.show();
            return false;
        }
    }
}
