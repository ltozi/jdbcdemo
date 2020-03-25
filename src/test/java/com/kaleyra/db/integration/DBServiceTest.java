package com.kaleyra.db.integration;

import com.kaleyra.db.federico.Hospital;
import com.kaleyra.db.federico.Paziente;
import com.kaleyra.db.orm.Person;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.*;

import static org.junit.Assert.assertEquals;

public class DBServiceTest {

    //    public static final String JDBC_URL = "jdbc:hsqldb:file:db/hsqldb-db;shutdown=true;hsqldb.write_delay=false";
    private static final String JDBC_URL = "jdbc:hsqldb:mem:inmemory;hsqldb.sqllog=3";
    private static final String USERNAME = "SA";
    private static final String PASSWORD = "";

    private static Connection conn;

    private static Paziente testSubject = new Paziente("Paolo", "Rossi", "PLARSS93R16D286X", 26,  "Torino", "TO");


    @BeforeClass
    public static void setupDatabaseOnce() throws SQLException {
        conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        conn.setAutoCommit(false);

        Statement stmt = conn.createStatement();
        stmt.executeQuery(//
                "create table OSPEDALE\n" +
                        "(\n" +
                        "\tCODICE_REGIONE INTEGER,\n" +
                        "\tDESCRIZIONE VARCHAR(255),\n" +
                        "\tCODICE_STRUTTURA INTEGER not null\n" +
                        "\t\tconstraint OSPEDALE_PK\n" +
                        "\t\t\tprimary key,\n" +
                        "\tDENOMINAZIONE_STRUTTURA VARCHAR(255),\n" +
                        "\tINDIRIZZO VARCHAR(255),\n" +
                        "\tCODICE_COMUNE INTEGER,\n" +
                        "\tCOMUNE VARCHAR(100),\n" +
                        "\tSIGLA_PROVINCIA VARCHAR(2),\n" +
                        "\tPOSTI_LETTO_DISPONIBILI INTEGER\n" +
                        ")");
        stmt.executeQuery(//
                "create table PAZIENTE\n" +
                        "(\n" +
                        "\tCF VARCHAR(16) not null\n" +
                        "\t\tconstraint PAZIENTE_PK\n" +
                        "\t\t\tprimary key,\n" +
                        "\tNOME VARCHAR(100),\n" +
                        "\tCOGNOME VARCHAR(100),\n" +
                        "\tETA INTEGER,\n" +
                        "\tCOMUNE_RESIDENZA VARCHAR(100),\n" +
                        "\tIDOSPEDALE INTEGER\n" +
                        "\t\tconstraint PAZIENTE_OSPEDALE_CODICE_STRUTTURA_FK\n" +
                        "\t\t\treferences OSPEDALE)");
        stmt.executeQuery("INSERT INTO\n" +
                "        PUBLIC.OSPEDALE(CODICE_REGIONE, DESCRIZIONE, CODICE_STRUTTURA, DENOMINAZIONE_STRUTTURA, INDIRIZZO, CODICE_COMUNE, COMUNE, SIGLA_PROVINCIA, POSTI_LETTO_DISPONIBILI)\n" +
                "        VALUES(10, 'PIEMONTE', 10003, 'OSPEDALE MARIA VITTORIA                 ', 'VIA CIBRARIO LUIGI 72                   ', 1272, 'TORINO                                  ', 'TO', 3)");

    }

    @Test
    public void shouldAddAPaziente() throws SQLException {

        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery(Hospital.searchForComune(testSubject.comune_residenza));
        rs.next(); //non so perché ma serve
        int codiceStruttura = rs.getInt("codice_struttura");
        int nBeds = rs.getInt("posti_letto_disponibili");

        rs = stmt.executeQuery(Hospital.insertPatient(testSubject,codiceStruttura ));

        rs = stmt.executeQuery("select * from paziente");
        int counter = 0;
        String name = "";
        String comuneResidenza = "";
        String cf = "";
        while(rs.next()){
            cf = rs.getString("CF");
            name = rs.getString("NOME");
            comuneResidenza = rs.getString("COMUNE_RESIDENZA");
            counter++;
        }

        assertEquals(1, counter);
        assertEquals("PAOLO", name);
        assertEquals("TORINO", comuneResidenza);

        conn.commit();

             /*
        printResult(rs);
        rs = stmt.executeQuery(updateBeds(codiceStruttura, nBeds - 1));
        printResult(rs);
        rs = stmt.executeQuery(searchForComune(p.codiceComune));
        printResult(rs);
        */
        }


}
