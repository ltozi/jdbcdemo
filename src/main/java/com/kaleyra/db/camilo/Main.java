package com.kaleyra.db.camilo;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

interface DBinsertable {
    void insertInDB(Connection conn, String tableName) throws SQLException;
}

public class Main {
    public static void main(String[] args) throws SQLException, IOException, InvalidMunicipalityOfResidenceORhospitalIDexception, NoHospitalWithFreeBedsException {


        final String USERNAME = "SA";
        final String PASSWORD = "";

        String JDBC_URL;

        Connection conn;
        ResultSet rs;
        Statement stmt;

        //final String JDBC_URL = "jdbc:hsqldb:file:db-ospedale/hsqldb-db;shutdown=true;hsqldb.write_delay=false";
        JDBC_URL = "jdbc:hsqldb:mem:inmemory;hsqldb.sqllog=3";   // this goes to the memory

        conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        conn.setAutoCommit(false);

        stmt = conn.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        // ResultSet obtained invoking executeQuery() on this statement will be scrollable, will not show changes made by others,
        // and will be updatable

        stmt.executeQuery("CREATE TABLE user " +
                "( id INTEGER IDENTITY PRIMARY KEY, name VARCHAR(30), email  VARCHAR(50))"  );


        User user0 = new User("Pedro","pedro@gmail.com");
        User user1 = new User("Luigi","luigi.toziani@kaleyra.com");
        User user2 = new User("Camilo","camilo.perez@gmail.com");
        user0.insertInDB(conn,"user");
        user1.insertInDB(conn,"user");
        user2.insertInDB(conn, "user");

        //rs = stmt.executeQuery("SELECT * FROM USER");
        //printResultSet(rs);

        /*****************************/

        JDBC_URL = "jdbc:hsqldb:file:db-ospedale/hsqldb-db;shutdown=true;hsqldb.write_delay=false";
        conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        conn.setAutoCommit(false);
        stmt = conn.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);


        Patient patient0 = new Patient("AB123","Lucca","Tondini",72,"BRUINO",10614);
        Patient patient1 = new Patient("BC456","Lucia","Bottaro",86,"ARIGNANO",10648);
        Patient patient2 = new Patient("CD567","Andrea","Mengoni",79,"ROBILANTE",10633);
//        patient0.insertInDB(conn,"PAZIENTE");
//        patient1.insertInDB(conn,"PAZIENTE");
//        patient2.insertInDB(conn,"PAZIENTE");


        //rs = stmt.executeQuery("SELECT * FROM PAZIENTE");
        //printResultSet(rs);

        /*****************************/


        DBservice dbs = DBservice.getInstance("jdbc:hsqldb:file:db-ospedale/hsqldb-db;shutdown=true;hsqldb.write_delay=false","SA","");
        //dbs.printQueryOutput("SELECT * FROM PAZIENTE");
        dbs.printPatientsDBtable();
        System.out.println("-----");
        dbs.addPatient(patient0);
        dbs.addPatient(patient1);
        dbs.addPatient(patient2);

        dbs.printPatientsDBtable();



        System.out.println("--------");
        String  municipalityOfResidence = "BRUINO";
        int hospitalID = 10614;
        //dbs.printQueryOutput("SELECT * FROM OSPEDALE where COMUNE='" + municipalityOfResidence + "' AND CODICE_STRUTTURA=" + hospitalID);
        // System.out.println(dbs.isQueryOutputEmpty("SELECT * FROM OSPEDALE where COMUNE='" + municipalityOfResidence + "' AND CODICE_STRUTTURA=" + hospitalID));


        conn.commit();



        System.out.println();
        resetDBtoOriginal();
        System.out.println("done !");
    }





    //Works only on camilo's pc. It resets the db-ospedale DB to it original (from git) state.
    static void resetDBtoOriginal(){
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", "cp -r ~/IdeaProjects/jdbcdemo/db-ospedale__orig/db-ospedale ~/IdeaProjects/jdbcdemo");

        try {

            Process process = processBuilder.start();
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader( new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.print("DB reset to original !");
                System.out.println(output);
            } else {
                System.out.println("Error while resetting DB to original status.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //print a result set and reset the cursor after
    public static void printResultSet(ResultSet rs) throws SQLException {
        int j;
        while (rs.next()) {
            j=1;
            try {
                while (true)
                    System.out.print( rs.getString(j++) + " ");
            }
            catch (SQLException e){}
            System.out.println("");
        }
        resetCursor(rs);
    }

    public static void resetCursor(ResultSet rs) throws SQLException {
        rs.absolute(0);
    }

    /* query per insert in ospedale
     * INSERT INTO PUBLIC.OSPEDALE (CODICE_REGIONE, DESCRIZIONE, CODICE_STRUTTURA, DENOMINAZIONE_STRUTTURA, INDIRIZZO, CODICE_COMUNE, COMUNE, SIGLA_PROVINCIA, POSTI_LETTO_DISPONIBILI) VALUES (10, 'PIEMONTE', 10003, 'OSPEDALE MARIA VITTORIA                 ', 'VIA CIBRARIO LUIGI 72                   ', 1272, 'TORINO                                  ', 'TO', 3);
     * */


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

    //print ResultStatement paziente
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
