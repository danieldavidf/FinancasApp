package com.example.daniel.financasfinalapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    private static final int versao =1;
    public Database(Context context) {
        super(context, "finan.db", null, versao);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE transacoes(" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "descricao TEXT," +
                        "local TEXT," +
                        "formaPagamento TEXT," +
                        "valor REAL," +
                        "dia TEXT,mes TEXT,ano TEXT);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
