package com.kaleyra.db.camilo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class Patient implements DBinsertable {

    private String fiscalCode;
    private String name;
    private String lastname;
    private int age;
    private String municipalityOfResidence;


    private int hospitalID;

    Patient(String fiscalCode, String name, String lastname, int age, String municipalityOfResidence, int hospitalID) {
        this.fiscalCode = fiscalCode;
        this.name = name;
        this.lastname = lastname;
        this.age = age;
        this.municipalityOfResidence = municipalityOfResidence;
        this.hospitalID = hospitalID;
    }


    // Notice that the target DB table should have the same nof columns as the nof field of this class
    @Override
    public void insertInDB(Connection conn, String tableName) throws SQLException {

        PreparedStatement insertUser;
        insertUser = conn.prepareStatement("INSERT INTO " + tableName + " (cf, nome, cognome, eta, comune_residenza, idOspedale) VALUES (?,?,?,?,?,?)");
        insertUser.setString(1, fiscalCode);
        insertUser.setString(2, name);
        insertUser.setString(3, lastname);

        insertUser.setInt(4, age);
        insertUser.setString(5, municipalityOfResidence);
        insertUser.setInt(6, hospitalID);

        insertUser.execute();

    }

    public String getFiscalCode() {
        return fiscalCode;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public int getAge() {
        return age;
    }

    public String getMunicipalityOfResidence() {
        return municipalityOfResidence;
    }

    public int getHospitalID() {
        return hospitalID;
    }

    public void setHospitalID(int hospitalID) {
        this.hospitalID = hospitalID;
    }

}
