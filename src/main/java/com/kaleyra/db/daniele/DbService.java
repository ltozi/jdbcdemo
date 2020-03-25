package com.kaleyra.db.daniele;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public interface DbService {

    final String JDBC_URL = "jdbc:hsqldb:file:db/hsqldb-db;shutdown=true;hsqldb.write_delay=false";
    //    private static final String JDBC_URL = "jdbc:hsqldb:mem:inmemory;hsqldb.sqllog=3";
    final String USERNAME = "SA";
    final String PASSWORD = "";

    public void createHospitalTable(Statement stmt) throws SQLException;

    public void createPatientTable(Statement stmt) throws SQLException;

    public int[] findHosptalWithVacancy(Statement stmt, String prov) throws SQLException;

    public void insertPatient(Statement stmt, int codiceStruttura) throws SQLException;

    public void increaseUpdate(Statement stmt, int postiDisponibili, int codiceStruttura) throws SQLException;

    public void decreaseUpdate(Statement stmt, int postiDisponibili, int codiceStruttura) throws SQLException;

    public void updatePatientWithHospitalCode(Statement stmt, int codiceStruttura, String codiceFiscale) throws SQLException;

    public void printHospitalTable(Statement stmt) throws SQLException;

    public void printPatientTable(Statement stmt) throws SQLException;

    public void destroyHospitalTable(Statement stmt) throws SQLException;

    public void destroyPatientTable(Statement stmt) throws SQLException;
}
