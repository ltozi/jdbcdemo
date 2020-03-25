package com.kaleyra.db.daniele;

import java.sql.*;

public class MainClass {

    public static void main(String[] args) throws SQLException {

        SingletonDbClass dbService = new SingletonDbClass();

        //connessione al db
        Connection conn = DriverManager.getConnection(dbService.getJDBC_URL(), dbService.getUsername(), dbService.getPassword());
        Statement stmt = conn.createStatement();
        conn.setAutoCommit(false);

        int a[] = new int[2];
        a = dbService.findHosptalWithVacancy(stmt, "RS");
        int codiceStruttura = a[0];
        int postiDisponibili = a[1];

        //recupero uno dei codici struttura
        //ResultSet rc = stmt.executeQuery("SELECT codice_struttura FROM Ospedale WHERE sigla_provincia ='" + prov  + "');


 /*     //estrae il comune di residenza di un paziente e lo stampa
        ResultSet comune = stmt.executeQuery("SELECT comune_residenza FROM Paziente WHERE cf = 'FRNMCN51A20H501O'");
        while (comune.next()) {
            String d = comune.getString("comune_residenza");
            System.out.print(d);
            System.out.println();
        }   */

        //PUNTO 3, se non ci sono posti nella provincia cerca nella propria regione,
        //         se non trova nulla cerca nelle regioni vicine
        //         nota: la tabella è ordinata per codice regione ascendente,
        //
        if(postiDisponibili == 0) {

            /*restituisce la 'sigla_provincia' dell'ospedale, accetta in input il codice_regione dell'ospedale
            int codiceRegione = 10;
            String siglaProvincia = "";
            ResultSet rr = stmt.executeQuery("SELECT sigla_provincia FROM Ospedale WHERE codice_regione ='" +
                                                codiceRegione + "' LIMIT 1");
            while (rr.next()) {
                String sp = rr.getString("sigla_provincia");
                siglaProvincia = sp;
                System.out.println();
            }
            //System.out.print(siglaProvincia); */
        }

        //insertUser = conn.prepareStatement("INSERT INTO user (name, email) VALUES (?, ?)");

        conn.commit();

    }

}