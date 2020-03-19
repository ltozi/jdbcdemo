package com.kaleyra.db.valentino;

import java.sql.*;

public class Main{
    public static void main(String[] args) throws SQLException {
        final String JDBC_URL = "jdbc:hsqldb:file:db/hsqldb-db;shutdown=true;hsqldb.write_delay=false";

        //    private static final String JDBC_URL = "jdbc:hsqldb:mem:inmemory;hsqldb.sqllog=3";
        final String USERNAME = "SA";
        final String PASSWORD = "";
        Connection conn;
        PreparedStatement insertUser;

        //connection to db
        conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        conn.setAutoCommit(false);
        Statement stmt = conn.createStatement();



        //Available hospital in the county
        String prov = "MI";
        ResultSet rs = stmt.executeQuery("SELECT * FROM Ospedale WHERE sigla_provincia ='" + prov +
                "' AND posti_letto_disponibili > 0 LIMIT 1"); // select from table, available county. Available bed < 0. (LIMIT is the same line)
        int structureCode = 0;
        int availablePlace = 0;
        while (rs.next()) {
            structureCode = rs.getInt("codice_struttura");
            availablePlace = rs.getInt("posti_letto_disponibili");
        }
        System.out.println(structureCode);
        System.out.println(availablePlace);


        //update hospital decrease number of beds
        availablePlace -= 1;
        stmt.executeQuery("UPDATE Ospedale SET posti_letto_disponibili = '" +availablePlace+
                "' WHERE codice_struttura = '"+structureCode+"'");






        //insert patient to db
        stmt.executeQuery("INSERT INTO Paziente(cf, nome, cognome, eta, comune_residenza, idOspedale) VALUES('ARNTND2A2OO801Y', 'Giuseppe', 'Rondine', '24', 'MILANO', '" + structureCode + "')");




        //Print Hospital table
        rs = stmt.executeQuery("SELECT * FROM Ospedale");
        while (rs.next()) {
            int codiceRegione = rs.getInt("codice_regione");
            String descrizione = rs.getString("descrizione");
            int codStruttura = rs.getInt("codice_struttura");
            String indirizzo = rs.getString("indirizzo");
            int codiceComune = rs.getInt("codice_comune");
            String comune = rs.getString("comune");
            String siglaProvincia = rs.getString("sigla_provincia");
            int postiLettoDisponibili = rs.getInt("posti_letto_disponibili");
            System.out.print(codiceRegione);
            System.out.print(" ");
            System.out.print(descrizione);
            System.out.print(" ");
            System.out.print(codStruttura);
            System.out.print(" ");
            System.out.print(indirizzo);
            System.out.print(" ");
            System.out.print(codiceComune);
            System.out.print(" ");
            System.out.print(comune);
            System.out.print(" ");
            System.out.print(siglaProvincia);
            System.out.print(" ");
            System.out.print(postiLettoDisponibili);
            System.out.println(); }

        //Print patient table
        ResultSet rp = stmt.executeQuery("SELECT * FROM Paziente");
        while (rp.next()) {
            String cf = rp.getString("cf");
            String nome = rp.getString("nome");
            String cognome = rp.getString("cognome");
            int eta = rp.getInt("eta");
            String comuneResidenza = rp.getString("comune_residenza");
            int idOspedale = rp.getInt("idOspedale");
            System.out.print(cf);
            System.out.print(" ");
            System.out.print(nome);
            System.out.print(" ");
            System.out.print(cognome);
            System.out.print(" ");
            System.out.print(eta);
            System.out.print(" ");
            System.out.print(comuneResidenza);
            System.out.print(" ");
            System.out.print(idOspedale);
            System.out.println();
        }
        conn.commit();
    }
}
