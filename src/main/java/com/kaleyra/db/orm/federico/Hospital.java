package com.kaleyra.db.orm.federico;

import java.sql.*;
import java.util.Scanner;

public class Hospital {
    private static Connection conn;
    public static final String JDBC_URL = "jdbc:hsqldb:file:db/hsqldb-db;shutdown=true;hsqldb.write_delay=false";
    private static final String USERNAME = "SA";
    private static final String PASSWORD = "";

    public static void setup() throws SQLException {
        conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        conn.setAutoCommit(true);
    }

    public static void main(String[] args) throws SQLException {

        String name;
        Paziente p = new Paziente("Paolo", "Rossi", "PLARSS93R16D286X", 26, 20037, "Paderno", "MI");

        setup();
        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery(searchForComune(p.codiceComune));
        rs.next(); //non so perché ma serve
        int codiceStruttura = rs.getInt("codice_struttura");
        int nBeds = rs.getInt("posti_letto_disponibili");

        rs = stmt.executeQuery(insertPatient(p,codiceStruttura ));
        printResult(rs);
        rs = stmt.executeQuery("select * from paziente");
        printResult(rs);
        rs = stmt.executeQuery(updateBeds(codiceStruttura, nBeds - 1));
        printResult(rs);
        rs = stmt.executeQuery(searchForComune(p.codiceComune));
        printResult(rs);
    }


    static String searchForComune(int codiceComune) {
        return String.format("SELECT TOP 1 codice_struttura, posti_letto_disponibili  FROM Ospedale WHERE codice_comune >= %d and posti_letto_disponibili > 0", codiceComune);
    }

    static String insertPatient(Paziente p, int codiceStruttura) {
        return String.format("insert into Paziente values ('%s', '%s','%s', %d, '%s', %d)", p.cf, p.nome, p.cognome, p.age, p.comune_residenza, codiceStruttura);
    }

    static String updateBeds(int codiceStruttura, int postiLetto) {
        return String.format("update Ospedale set posti_letto_disponibili = %d where codice_struttura = %d", postiLetto, codiceStruttura);
    }

    static void printResult(ResultSet rs) throws SQLException {
        while (rs.next()) {

            ResultSetMetaData rsmd = rs.getMetaData();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = rs.getString(i);
                System.out.print(columnValue + " " + rsmd.getColumnName(i));
            }
            System.out.println("");

        }
    }

}

//2 1  0297100337

