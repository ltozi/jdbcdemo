package com.kaleyra.db.daniele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SingletonDbClass implements DbService {

    private static DbService instance = new SingletonDbClass();

    public static DbService getInstance() {
        return instance;
    }

    public static String getJDBC_URL() {
        return JDBC_URL;
    }

    public static String getUsername() {
        return USERNAME;
    }

    public static String getPassword() {
        return PASSWORD;
    }

    public void createHospitalTable(Statement stmt) throws SQLException {
        stmt.executeQuery("CREATE TABLE Ospedale " +
                "(codice_regione INTEGER, " +
                "descrizione_regione VARCHAR(255), " +
                "codice_struttura INTEGER PRIMARY KEY, " +
                "denominazione_struttura VARCHAR(255), " +
                "indirizo VARCHAR(255), " +
                "codice_comune INTEGER, " +
                "comune VARCHAR(255), " +
                "sigla_provincia VARCHAR(2), " +
                "posti_letto_disponibili INTEGER)");
    }

    public void createPatientTable(Statement stmt) throws SQLException {
        stmt.executeQuery("CREATE TABLE Paziente " +
                "(cf VARCHAR(16) PRIMARY KEY, " +
                "nome VARCHAR(100), " +
                "cognome VARCHAR(100), " +
                "eta INTEGER, " +
                "comune_residenza VARCHAR(100), " +
                "idOspedale INTEGER)");
    }

    public int[] findHosptalWithVacancy(Statement stmt, String prov) throws SQLException {

        int[] ret = {0,0};

        //restituisce un ospedale libero nella provincia se ha posti disponibili
        ResultSet rs = stmt.executeQuery("SELECT * FROM Ospedale WHERE sigla_provincia ='" + prov +
                "' AND posti_letto_disponibili > 0 LIMIT 1");
        int codiceStruttura = 0;
        int postiDisponibili = 0;

        while (rs.next()) {
            codiceStruttura = rs.getInt("codice_struttura");
            postiDisponibili = rs.getInt("posti_letto_disponibili");
        }

        //System.out.println(codiceStruttura);
        //System.out.println(postiDisponibili);
        ret[0] = codiceStruttura;
        ret[1] = postiDisponibili;

        return ret;
    }

    public void insertPatient(Statement stmt, int codiceStruttura) throws SQLException {
        stmt.executeQuery("INSERT INTO Paziente(cf, nome, cognome, eta, comune_residenza, idOspedale) VALUES('JRNTND2A2OO801Y', 'Franco', 'Marconi', '69', 'ROMA', '" + codiceStruttura + "')");
    }

    public void increaseUpdate(Statement stmt, int postiDisponibili, int codiceStruttura) throws SQLException {
        postiDisponibili -= 1;
        stmt.executeQuery("UPDATE Ospedale SET posti_letto_disponibili = '" +postiDisponibili+
                "' WHERE codice_struttura = '"+codiceStruttura+"'");
    }

    public void decreaseUpdate(Statement stmt, int postiDisponibili, int codiceStruttura) throws SQLException {
        //update ospedale incrementando il numero di posti letto
        postiDisponibili += 1;
        stmt.executeQuery("UPDATE Ospedale SET posti_letto_disponibili = '" +postiDisponibili+
                "' WHERE codice_struttura = '"+codiceStruttura+"'");
    }

    public void updatePatientWithHospitalCode(Statement stmt, int codiceStruttura, String codiceFiscale) throws SQLException {
        stmt.executeQuery("UPDATE Paziente SET idOspedale = '" +codiceStruttura+
                "' WHERE cf = '"+codiceFiscale+"'");
    }

    public void printHospitalTable(Statement stmt) throws SQLException {
        //stampa la tabella Ospedale
        ResultSet rs = stmt.executeQuery("SELECT * FROM Ospedale");
        while (rs.next()) {
            int x = rs.getInt("codice_regione");
            String d = rs.getString("descrizione");
            int cs = rs.getInt("codice_struttura");
            String i = rs.getString("indirizzo");
            int c = rs.getInt("codice_comune");
            String cmn = rs.getString("comune");
            String s = rs.getString("sigla_provincia");
            int p = rs.getInt("posti_letto_disponibili");
            System.out.print(x);
            System.out.print(" ");
            System.out.print(cs);
            System.out.print(" ");
            System.out.print(i);
            System.out.print(" ");
            System.out.print(c);
            System.out.print(" ");
            System.out.print(cmn);
            System.out.print(" ");
            System.out.print(s);
            System.out.print(" ");
            System.out.print(p);
            System.out.println(); }
    }

    public void printPatientTable(Statement stmt) throws SQLException {
        //stampa la tabella Paziente
        ResultSet rp = stmt.executeQuery("SELECT * FROM Paziente");
        while (rp.next()) {
            String cf = rp.getString("cf");
            String n = rp.getString("nome");
            String cn = rp.getString("cognome");
            int et = rp.getInt("eta");
            String cr = rp.getString("comune_residenza");
            int io = rp.getInt("idOspedale");
            System.out.print(cf);
            System.out.print(" ");
            System.out.print(n);
            System.out.print(" ");
            System.out.print(cn);
            System.out.print(" ");
            System.out.print(et);
            System.out.print(" ");
            System.out.print(cr);
            System.out.print(" ");
            System.out.print(io);
            System.out.println();
        }
    }

    public void destroyHospitalTable(Statement stmt) throws SQLException {
        stmt.executeQuery("DROP TABLE Ospedale");
    }

    public void destroyPatientTable(Statement stmt) throws SQLException {
        stmt.executeQuery("DROP TABLE Studente");
    }
}
