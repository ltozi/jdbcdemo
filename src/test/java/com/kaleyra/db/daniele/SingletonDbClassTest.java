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

    @Before
    public void setUpTest() throws SQLException {
        dbService = new SingletonDbClass();

        //connessione al db
        Connection conn = DriverManager.getConnection(dbService.getJDBC_URL(), dbService.getUsername(), dbService.getPassword());
        Statement stmt = conn.createStatement();
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

    @Test
    public void shouldThrowExceptionWithNullURL() {
        if(dbService.getJDBC_URL().isEmpty() || dbService.getJDBC_URL() == null)
            throw new IllegalArgumentException("dbService.getJDBC_URL cant be null");
    }

    @Test
    public void getUsername() {
    }

    @Test
    public void getPassword() {
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
