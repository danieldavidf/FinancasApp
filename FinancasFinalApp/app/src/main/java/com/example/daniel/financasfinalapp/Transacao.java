package com.example.daniel.financasfinalapp;

public class Transacao {
    String Descrição;
    String Local;
    String formaPagamento;
    Double valor;
    String dia;
    String mes;
    String ano;
    long id;

    public Transacao(String descrição, String local, String formaPagamento, Double valor, String dia, String mes, String ano, long id) {
        Descrição = descrição;
        Local = local;
        this.formaPagamento = formaPagamento;
        this.valor = valor;
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
        this.id = id;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    public String getDescrição() {
        return Descrição;
    }

    public void setDescrição(String descrição) {
        Descrição = descrição;
    }

    public String getLocal() {
        return Local;
    }

    public void setLocal(String local) {
        Local = local;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
