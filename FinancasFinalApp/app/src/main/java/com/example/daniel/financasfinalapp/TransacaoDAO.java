package com.example.daniel.financasfinalapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 07/12/18.
 */

public class TransacaoDAO {
    private Database dbHelp;
    private SQLiteDatabase db;

    public TransacaoDAO(Context context) {
        dbHelp=new Database(context);
    }
    public void open(){
        db=dbHelp.getWritableDatabase();
    }
    public void close(){
        dbHelp.close();
    }

    public List<Transacao> getAll(){
        List<Transacao> transacoes = new ArrayList<Transacao>();
        Cursor cursor=db.query("transacoes", new String[]{"_id","descricao","local","formaPagamento","valor","dia","mes","ano"},null,null,null,null,null);
        if(!cursor.equals(null)){
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                transacoes.add(new Transacao(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getDouble(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getLong(0)));
                cursor.moveToNext();
            }
            return transacoes;

        }return transacoes;
    }
    public void insert(Transacao d){
        ContentValues values=new ContentValues();
        values.put("descricao",d.getDescrição());
        values.put("local",d.getLocal());
        values.put("formaPagamento",d.getFormaPagamento());
        values.put("valor",d.getValor());
        values.put("dia",d.getDia());
        values.put("mes",d.getMes());
        values.put("ano",d.getAno());
        long id = db.insert("transacoes",null,values);
    }

    public void remove(Transacao d){
        db.delete("transacoes","_id="+d.getId(),null);
    }
}
