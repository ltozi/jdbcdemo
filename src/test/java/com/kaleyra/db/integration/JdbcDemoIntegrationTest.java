package com.kaleyra.db.integration;

import com.kaleyra.db.orm.Person;
import org.junit.*;

import java.sql.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JdbcDemoIntegrationTest {

//    public static final String JDBC_URL = "jdbc:hsqldb:file:db/hsqldb-db;shutdown=true;hsqldb.write_delay=false";
    private static final String JDBC_URL = "jdbc:hsqldb:mem:inmemory;hsqldb.sqllog=3";
    private static final String USERNAME = "SA";
    private static final String PASSWORD = "";

    private static Connection conn;

    @BeforeClass
    public static void setupDatabaseOnce() throws SQLException {
        conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        conn.setAutoCommit(false);

        Statement stmt = conn.createStatement();
        stmt.executeQuery("CREATE TABLE user " +
                "(id INTEGER IDENTITY PRIMARY KEY, " +
                "name VARCHAR(30), " +
                "email  VARCHAR(50))");
    }


    @Before
    public void beforeEach() throws SQLException {
        //TODO if needed, prepare data
    }

    @After
    public void afterEach() throws SQLException {

        //TODO remove rows from modified tables.....

    }

    @AfterClass
    public static void teardown() throws SQLException {
        //TODO cleanup database
        //TODO stmt.executeQuery("drop table user");
    }

    @Test
    public void shouldAddAnUserInsideDatabase() throws SQLException {

        Statement stmt = conn.createStatement();
        stmt.executeQuery("INSERT INTO user (name, email) VALUES ('Luigi', 'luigi.toziani@kaleyra.com')");
        ResultSet rs = stmt.executeQuery("SELECT * FROM USER");

        Person person = null;
        int counter = 0;
        while (rs.next()) {
            int firstCol = rs.getInt("id");
            person = new Person();
            person.name = rs.getString("name");
            person.email = rs.getString("email");
            counter++;
        }

        assertEquals(1, counter);
        assertEquals("Luigi", person.name);
        assertEquals("luigi.toziani@kaleyra.com", person.email);

        conn.commit();
    }



}
