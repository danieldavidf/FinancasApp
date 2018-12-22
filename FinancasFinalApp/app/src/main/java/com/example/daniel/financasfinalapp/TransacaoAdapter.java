package com.example.daniel.financasfinalapp;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by daniel on 07/12/18.
 */

public class TransacaoAdapter extends ArrayAdapter<Transacao> {
    private List<Transacao> dados;
    public TransacaoAdapter(@NonNull Context context, List<Transacao> transacoes) {
        super(context, 0, transacoes);
        this.dados = transacoes;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=convertView;
        Transacao transacoes = this.dados.get(position);
        if (this.dados !=null){
            if (view==null){
                LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view=inflater.inflate(R.layout.list_item,null);
                ViewHolder holder=new ViewHolder();
                holder.Descrição=view.findViewById(R.id.t1);
                holder.Local=view.findViewById(R.id.t2);
                holder.FormaPagamento=view.findViewById(R.id.t3);
                holder.valor=view.findViewById(R.id.t4);
                view.setTag(holder);
            }
            ViewHolder holder=(ViewHolder)view.getTag();
            holder.Descrição.setText(transacoes.getDescrição());
            holder.Local.setText(transacoes.getLocal());
            holder.FormaPagamento.setText(transacoes.getFormaPagamento());
            holder.valor.setText(transacoes.getValor().toString());
            if(transacoes.getValor() >= 0){
                holder.valor.setTextColor(Color.parseColor("#6eb343"));
            }else holder.valor.setTextColor(Color.RED);

        }
        return view;
    }
    static class ViewHolder{
        public TextView Descrição;
        public TextView Local;
        public TextView FormaPagamento;
        public TextView valor;

    }
}
