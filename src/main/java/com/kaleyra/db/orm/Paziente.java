package com.kaleyra.db.orm;

public class Paziente {
    public int codiceComune;
    private String provincia;
    String cf;
    String nome;
    String cognome;
    int age;
    String comune_residenza;


    Paziente(String name, String surname, String cf, int age, int codiceComune, String comune_residenza, String provincia) {
        nome = name.toUpperCase();
        cognome = surname.toUpperCase();
        this.cf = cf.toUpperCase();
        this.age = age;
        this.comune_residenza = comune_residenza.toUpperCase();
        this.provincia = provincia.toUpperCase();
        this.codiceComune = codiceComune;
    }
}