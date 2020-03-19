package com.kaleyra.db;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

public class OspedaleTest {

    public static final String JDBC_URL = "jdbc:hsqldb:file:db/hsqldb-db;shutdown=true;hsqldb.write_delay=false";
      //  private static final String JDBC_URL = "jdbc:hsqldb:mem:inmemory;hsqldb.sqllog=3";
    private static final String USERNAME = "SA";
    private static final String PASSWORD = "";
    private Connection conn;

    @Before
    public void setupTest() throws SQLException {
        conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        conn.setAutoCommit(false);
    }

    @After
    public void teardown() throws SQLException {
//        Statement statement = conn.createStatement();
//        statement.executeQuery("");
//        conn.commit();
    }

    @Test
    public void initConnection() throws SQLException {


        Statement stmt = conn.createStatement();
/*
        stmt.executeQuery("INSERT INTO user (name, email) VALUES ('Luigi', 'luigi.toziani@kaleyra.com')");
        stmt.executeQuery("INSERT INTO user (name, email) VALUES ('Marco', 'marco.bazzan@kaleyra.com')");
*/
        ResultSet rs = stmt.executeQuery("SELECT * FROM Ospedale WHERE POSTI_LETTO_DISPONIBILI > 0");

        System.out.println(rs);

        while (rs.next()) {
            int codReg = rs.getInt("CODICE_REGIONE");
            String regione = rs.getString("DESCRIZIONE");
            int codStrutt = rs.getInt("CODICE_STRUTTURA");
            String strutt = rs.getString("DENOMINAZIONE_STRUTTURA");
            int codComu = rs.getInt("CODICE_COMUNE");
            int postiLetto = rs.getInt("POSTI_LETTO_DISPONIBILI");

            System.out.print(codReg);
            System.out.print(" | ");
            System.out.print(regione);
            System.out.print(" | ");
            System.out.print(codStrutt);
            System.out.print(" | ");
            System.out.print(strutt);
            System.out.print(" | ");
            System.out.print(codComu);
            System.out.print(" | ");
            System.out.print(postiLetto);
            System.out.println("");
        }


        conn.commit();
    }
}
