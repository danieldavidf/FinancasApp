package com.example.daniel.financasfinalapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Filtro extends AppCompatActivity {
    List<Transacao> transacoes =new ArrayList<Transacao>();
    List<Transacao> transacoesMostradas =new ArrayList<Transacao>();
    ListView list;
    EditText local;
    EditText formaPagamento;
    EditText mes;
    EditText ano;
    CheckBox gastos;
    CheckBox ganhos;
    private TransacaoAdapterFiltro adapter;
    TransacaoDAO dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro);
        formaPagamento=(EditText)findViewById(R.id.editText3);
        mes=(EditText)findViewById(R.id.editText4);
        ano=(EditText)findViewById(R.id.editText5);
        gastos = (CheckBox)findViewById(R.id.checkBox);
        ganhos = (CheckBox)findViewById(R.id.checkBox2);
        dao=new TransacaoDAO(this);
        dao.open();
        atualizaDados();
        list=(ListView)findViewById(R.id.listFiltro);
        local=(EditText)findViewById(R.id.editText2);
        adapter=new TransacaoAdapterFiltro(this, transacoesMostradas);
        list.setAdapter(adapter);
    }
    private void atualizaDados() {
        transacoes = dao.getAll();

    }
    public void selecionar() {
        transacoesMostradas.removeAll(transacoesMostradas);
        boolean soGastos=true, soGanhos=true;

        if(!gastos.isChecked() && !ganhos.isChecked()) {
            Toast.makeText(this, "Escolha gastos e/ou ganhos", Toast.LENGTH_SHORT).show();
        } else {
            if (gastos.isChecked() && !ganhos.isChecked())
                soGanhos=false;
            else if (!gastos.isChecked() && ganhos.isChecked())
                soGastos=false;

            if ((transacoes.size() > 0)) {
                double total = 0;

                String local_busc = local.getText().toString();
                String pgto_busc = formaPagamento.getText().toString();
                String mes_busc = mes.getText().toString();
                String ano_busc = ano.getText().toString();
                int vet[];
                vet = new int[]{0, 0, 0, 0};

                if (!local_busc.equals(""))
                    vet[0] = 1;
                if (!pgto_busc.equals(""))
                    vet[1] = 1;
                if (!mes_busc.equals(""))
                    vet[2] = 1;
                if (!ano_busc.equals(""))
                    vet[3] = 1;

                for (Transacao ds : transacoes) {
                    if (vet[0] == 1 && vet[1] == 1 && vet[2] == 1 && vet[3] == 1) {
                        if (ds.getLocal().equals(local_busc) && ds.getFormaPagamento().equals(pgto_busc) && ds.getMes().equals(mes_busc) && ds.getAno().equals(ano_busc)) {
                            if (soGanhos && ds.getValor() > 0)
                                transacoesMostradas.add(ds);
                            if (soGastos && ds.getValor() < 0)
                                transacoesMostradas.add(ds);
                        }
                    }
                    else if (vet[0] == 1 && vet[1] == 1 && vet[2] == 1 && vet[3] == 0) {
                        if (ds.getLocal().equals(local_busc) && ds.getFormaPagamento().equals(pgto_busc) && ds.getMes().equals(mes_busc)) {
                            if (soGanhos && ds.getValor() > 0)
                                transacoesMostradas.add(ds);
                            if (soGastos && ds.getValor() < 0)
                                transacoesMostradas.add(ds);
                        }
                    }
                    else if (vet[0] == 1 && vet[1] == 1 && vet[2] == 0 && vet[3] == 1) {
                        if (ds.getLocal().equals(local_busc) && ds.getFormaPagamento().equals(pgto_busc) && ds.getAno().equals(ano_busc)) {
                            if (soGanhos && ds.getValor() > 0)
                                transacoesMostradas.add(ds);
                            if (soGastos && ds.getValor() < 0)
                                transacoesMostradas.add(ds);
                        }
                    }
                    else if (vet[0] == 1 && vet[1] == 0 && vet[2] == 1 && vet[3] == 1) {
                        if (ds.getLocal().equals(local_busc) && ds.getMes().equals(mes_busc) && ds.getAno().equals(ano_busc)) {
                            if (soGanhos && ds.getValor() > 0)
                                transacoesMostradas.add(ds);
                            if (soGastos && ds.getValor() < 0)
                                transacoesMostradas.add(ds);
                        }
                    }
                    else if (vet[0] == 0 && vet[1] == 1 && vet[2] == 1 && vet[3] == 1) {
                        if (ds.getFormaPagamento().equals(pgto_busc) && ds.getMes().equals(mes_busc) && ds.getAno().equals(ano_busc)) {
                            if (soGanhos && ds.getValor() > 0)
                                transacoesMostradas.add(ds);
                            if (soGastos && ds.getValor() < 0)
                                transacoesMostradas.add(ds);
                        }
                    }
                    else if (vet[0] == 1 && vet[1] == 1 && vet[2] == 0 && vet[3] == 0) {
                        if (ds.getLocal().equals(local_busc) && ds.getFormaPagamento().equals(pgto_busc)) {
                            if (soGanhos && ds.getValor() > 0)
                                transacoesMostradas.add(ds);
                            else if (soGastos && ds.getValor() < 0)
                                transacoesMostradas.add(ds);
                        }
                    }
                    else if (vet[0] == 0 && vet[1] == 0 && vet[2] == 1 && vet[3] == 1) {
                        if (ds.getMes().equals(mes_busc) && ds.getAno().equals(ano_busc)) {
                            if (soGanhos && ds.getValor() > 0)
                                transacoesMostradas.add(ds);
                            if (soGastos && ds.getValor() < 0)
                                transacoesMostradas.add(ds);
                        }
                    }
                    if (vet[0] == 1 && vet[1] == 0 && vet[2] == 1 && vet[3] == 0) {
                        if (ds.getLocal().equals(local_busc) && ds.getMes().equals(mes_busc)) {
                            if (soGanhos && ds.getValor() > 0)
                                transacoesMostradas.add(ds);
                            if (soGastos && ds.getValor() < 0)
                                transacoesMostradas.add(ds);
                        }
                    }
                    else if (vet[0] == 0 && vet[1] == 1 && vet[2] == 0 && vet[3] == 1) {
                        if (ds.getFormaPagamento().equals(pgto_busc) && ds.getAno().equals(ano_busc)) {
                            if (soGanhos && ds.getValor() > 0)
                                transacoesMostradas.add(ds);
                            if (soGastos && ds.getValor() < 0)
                                transacoesMostradas.add(ds);
                        }
                    }
                    else if (vet[0] == 1 && vet[1] == 0 && vet[2] == 0 && vet[3] == 1) {
                        if (ds.getLocal().equals(local_busc) && ds.getAno().equals(ano_busc)) {
                            if (soGanhos && ds.getValor() > 0)
                                transacoesMostradas.add(ds);
                            if (soGastos && ds.getValor() < 0)
                                transacoesMostradas.add(ds);
                        }
                    }
                    else if (vet[0] == 0 && vet[1] == 1 && vet[2] == 1 && vet[3] == 0) {
                        if (ds.getFormaPagamento().equals(pgto_busc) && ds.getMes().equals(mes_busc)) {
                            if (soGanhos && ds.getValor() > 0)
                                transacoesMostradas.add(ds);
                            if (soGastos && ds.getValor() < 0)
                                transacoesMostradas.add(ds);
                        }
                    }
                    else if (vet[0] == 0 && vet[1] == 0 && vet[2] == 0 && vet[3] == 1) {
                        if (ds.getAno().equals(ano_busc)) {
                            if (soGanhos && ds.getValor() > 0)
                                transacoesMostradas.add(ds);
                            if (soGastos && ds.getValor() < 0)
                                transacoesMostradas.add(ds);
                        }
                    }
                    else if (vet[0] == 0 && vet[1] == 0 && vet[2] == 1 && vet[3] == 0) {
                        if (ds.getMes().equals(mes_busc)) {
                            if (soGanhos && ds.getValor() > 0)
                                transacoesMostradas.add(ds);
                            if (soGastos && ds.getValor() < 0)
                                transacoesMostradas.add(ds);
                        }
                    }
                    else if (vet[0] == 0 && vet[1] == 1 && vet[2] == 0 && vet[3] == 0) {
                        if (ds.getFormaPagamento().equals(pgto_busc)) {
                            if (soGanhos && ds.getValor() > 0)
                                transacoesMostradas.add(ds);
                            if (soGastos && ds.getValor() < 0)
                                transacoesMostradas.add(ds);
                        }
                    }
                    else if (vet[0] == 1 && vet[1] == 0 && vet[2] == 0 && vet[3] == 0) {
                        if (ds.getLocal().equals(local_busc)) {
                            if (soGanhos && ds.getValor() > 0)
                                transacoesMostradas.add(ds);
                            if (soGastos && ds.getValor() < 0)
                                transacoesMostradas.add(ds);
                        }
                    }
                    else if (vet[0] == 0 && vet[1] == 0 && vet[2] == 0 && vet[3] == 0) {
                        //Toast.makeText(this, "Favor preencher ao menos um campo", Toast.LENGTH_SHORT).show();
                        if (soGanhos && ds.getValor() > 0)
                            transacoesMostradas.add(ds);
                        if (soGastos && ds.getValor() < 0)
                            transacoesMostradas.add(ds);
                    }
                }

                if (transacoesMostradas.size() == 0)
                    Toast.makeText(this, "Sem resultados para estes parâmetros", Toast.LENGTH_SHORT).show();
                if (transacoesMostradas.size() != 0)
                    for(Transacao ds : transacoesMostradas)
                        total+=ds.getValor();
                transacoesMostradas.add(new Transacao("TOTAL","","",total,"","","",-1));
            }
        }
    }
    public void filtrar(View v){
        selecionar();
        adapter.notifyDataSetChanged();
    }

    public void voltar(View v){
        finish();
    }
}
