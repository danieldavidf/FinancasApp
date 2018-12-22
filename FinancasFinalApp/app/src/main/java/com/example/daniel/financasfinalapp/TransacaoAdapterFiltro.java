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

public class TransacaoAdapterFiltro extends ArrayAdapter<Transacao> {
    private List<Transacao> dados;
    public TransacaoAdapterFiltro(@NonNull Context context, List<Transacao> transacoes) {
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
                view=inflater.inflate(R.layout.list_item_filtro,null);
                ViewHolder holder=new ViewHolder();
                holder.Descrição=view.findViewById(R.id.t11);
                holder.Local=view.findViewById(R.id.t21);
                holder.FormaPagamento=view.findViewById(R.id.t31);
                holder.valor=view.findViewById(R.id.t41);
                holder.data=view.findViewById(R.id.t51);
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
            holder.data.setText(transacoes.getDia()+"/"+transacoes.getMes()+"/"+transacoes.getAno());
            if(transacoes.getDescrição().equals("TOTAL"))
                holder.data.setText("");
        }
        return view;
    }
    static class ViewHolder{
        public TextView Descrição;
        public TextView Local;
        public TextView FormaPagamento;
        public TextView valor;
        public TextView data;

    }
}
