package com.kaleyra.db.camilo;

import java.sql.*;

//singleton DBservice
class DBservice {
    private static DBservice dbService;

    private String JDBC_URL = null;
    private String USERNAME = null;
    private String PASSWORD = null;

    private Connection conn;
    private Statement stmt;

    private DBservice(String JDBC_URL, String USERNAME, String PASSWORD) throws SQLException {
        this.JDBC_URL = JDBC_URL;
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;

        conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        conn.setAutoCommit(false);

        /*ResultSet objects obtained invoking executeQuery() on this statement will be scrollable, will not show changes made by others,
         * and will be updatable */
        stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
    }

    public static DBservice getInstance(String JDBC_URL, String USERNAME, String PASSWORD) throws SQLException {
        if (dbService == null) {
            dbService = new DBservice(JDBC_URL, USERNAME, PASSWORD);
        }
        return dbService;
    }

    public ResultSet executeQuery(String query) throws SQLException {
//        ResultSet rs = stmt.executeQuery(query);
//        conn.commit();
        return stmt.executeQuery(query);
    }

    public void printQueryOutput(String query) throws SQLException {
        ResultSet rs = executeQuery(query);
        printResultSet(rs);
    }

    public void printPatientsDBtable() throws SQLException {
        printQueryOutput("SELECT * FROM PAZIENTE");
    }

    public void printHospitalsDBtable() throws SQLException {
        printQueryOutput("SELECT * FROM OSPEDALE");
    }

    public void addPatient(Patient patient) throws SQLException, InvalidMunicipalityOfResidenceORhospitalIDexception, NoHospitalWithFreeBedsException {
        String municipalityOfResidence = patient.getMunicipalityOfResidence();
        int hospitalID = patient.getHospitalID();

        if (isQueryOutputEmpty("SELECT * FROM OSPEDALE where COMUNE='" + municipalityOfResidence + "' AND CODICE_STRUTTURA=" + hospitalID)) {
            throw new InvalidMunicipalityOfResidenceORhospitalIDexception("The municipality of residence xxx and the hospital ID xxx do not " +
                    "correspond to a same row in the OSPEDALE database.");
        } else if (isQueryOutputEmpty("SELECT * FROM OSPEDALE where POSTI_LETTO_DISPONIBILI > 0")) {
            throw new NoHospitalWithFreeBedsException("There are no hospitals with free beds, so the patient cannot be added to the patients database");
        } else {
            String query = String.format("INSERT INTO " + "PAZIENTE" + " (cf, nome, cognome, eta, comune_residenza, idOspedale) VALUES ('%s', '%s','%s', %d, '%s', %d)",
                    patient.getFiscalCode(), patient.getName(), patient.getLastname(), patient.getAge(), patient.getMunicipalityOfResidence(), patient.getHospitalID());
            executeQuery(query);
        }

        //TODO: each time a patient is added to the Patients database, the Hospitals database must be updated
        //updateHospitalsDBtableWithPatient();
    }

    /* TODO: finds the hospitalID of a newly added patient to the Hospitals database, and decrease the number of free beds
     * of that Hospital in one */
    private void updateHospitalsDBtableWithPatient(){

    }

    public boolean isQueryOutputEmpty(String query) throws SQLException {
        ResultSet rs = executeQuery(query);
        return !rs.isBeforeFirst();
    }

    public boolean areThereHospitalsWithFreeBeds() throws SQLException {
        return !isQueryOutputEmpty("SELECT * FROM OSPEDALE where POSTI_LETTO_DISPONIBILI > 0");
    }


    //print a result set and reset the cursor after
    private void printResultSet(ResultSet rs) throws SQLException {
        int j;
        while (rs.next()) {
            j = 1;
            try {
                while (true)
                    System.out.print(rs.getString(j++) + " ");
            } catch (SQLException e) {
            }
            System.out.println("");
        }
        rs.absolute(0);  //reset cursor to first row !
    }


}
