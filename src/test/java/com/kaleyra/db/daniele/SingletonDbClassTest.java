package com.kaleyra.db.daniele;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;


public class SingletonDbClassTest {
    SingletonDbClass dbService;
    Statement stmt;
    Connection conn;

    @Before
    public void setUpTest() throws SQLException {
        dbService = new SingletonDbClass();

        //connessione al db
        conn = DriverManager.getConnection(dbService.getJDBC_URL(), dbService.getUsername(), dbService.getPassword());
        stmt = conn.createStatement();
        conn.setAutoCommit(false);
    }

    @Test
    public void verifyThatTheSingletonInstanceIsUnique() {
        SingletonDbClass ins_1 = dbService;
        SingletonDbClass ins_2 = new SingletonDbClass();

        System.out.println(ins_1.hashCode());
        System.out.println(ins_2.hashCode());

        assertEquals(ins_1, ins_2);
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWithNullURL() {
        if(dbService.getJDBC_URL().isEmpty() || dbService.getJDBC_URL() == null)
            throw new IllegalArgumentException("dbService.getJDBC_URL cant be null");
    }

    @Test
    public void shouldInsertOnePatient() throws Exception {
        conn.prepareStatement("insert into Paziente values ('CMMDNL89P06H294P', Daniele, Commodaro, 30, RN, 10030)").execute();

        assertTrue(
                conn.prepareStatement("select * from Paziente where nome = 'Daniele' AND cognome = 'Commodaro', 10030").executeQuery().next());
    }

    @Test
    public void createHospitalTable() {
    }

    @Test
    public void createPatientTable() {
    }

    @Test
    public void findHosptalWithVacancy() {
    }

    @Test
    public void insertPatient() {
    }

    @Test
    public void increaseUpdate() {
    }

    @Test
    public void decreaseUpdate() {
    }

    @Test
    public void updatePatientWithHospitalCode() {
    }

    @Test
    public void printHospitalTable() {
    }

    @Test
    public void printPatientTable() {
    }

    @Test
    public void destroyHospitalTable() {
    }

    @Test
    public void destroyPatientTable() {
    }
}
