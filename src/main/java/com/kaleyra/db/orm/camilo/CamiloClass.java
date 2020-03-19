package com.kaleyra.db.orm.camilo;

import java.sql.*;
import java.util.Scanner;


public class CamiloClass {




   public ResultSet queryfreeBedHospitalsByRC(Statement stmt, int RC) throws SQLException {
        return stmt.executeQuery("SELECT * FROM OSPEDALE where CODICE_REGIONE=" + RC +
                " AND POSTI_LETTO_DISPONIBILI > 0");
    }

    //query free bed hospitals by region code and municipality
    public ResultSet queryfreeBedHospitalsByRCandM(Statement stmt, int RC, String comune_residenza) throws SQLException {
        return stmt.executeQuery("SELECT * FROM OSPEDALE where COMUNE='" + comune_residenza + "' AND CODICE_REGIONE=" +
                RC + " AND POSTI_LETTO_DISPONIBILI > 0");
    }

    public boolean isQueryOutputEmpty(ResultSet rs) throws SQLException {
        return !rs.isBeforeFirst();
    }

    public void printRSpaziente(ResultSet rsPaziente) throws SQLException {
        while (rsPaziente.next()) {
            /*unique*/
            String cf = rsPaziente.getString("cf");

            String nome = rsPaziente.getString("nome");
            String cognome = rsPaziente.getString("cognome");

            int eta = rsPaziente.getInt("eta");

            /*tabella Ospedale*/
            String comune_residenza = rsPaziente.getString("comune_residenza");
            /*tabella Ospedale*/
            int idOspedale = rsPaziente.getInt("idOspedale");

            System.out.print(cf + " ");
            System.out.print(nome + " ");
            System.out.print(cognome + " ");
            System.out.print(eta + " ");
            System.out.print(comune_residenza + " ");
            System.out.println(idOspedale);
        }

    }

}
