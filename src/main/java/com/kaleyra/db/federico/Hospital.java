package com.kaleyra.db.federico;

import java.sql.*;

public class Hospital {
    private static Connection conn;
    public static final String JDBC_URL = "jdbc:hsqldb:file:db/hsqldb-db;shutdown=true;hsqldb.write_delay=false";
    private static final String USERNAME = "SA";
    private static final String PASSWORD = "";


    public static String searchForComune(String comune) {
        return String.format("SELECT TOP 1 codice_struttura, posti_letto_disponibili  FROM Ospedale WHERE comune = '%s' AND posti_letto_disponibili > 0", comune);
    }

    public static String insertPatient(Paziente p, int codiceStruttura) {
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

