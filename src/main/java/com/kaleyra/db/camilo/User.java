package com.kaleyra.db.camilo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class User implements DBinsertable {
    private  String name;
    private String email;

    User (String name, String email) {
        this.name = name;
        this.email = email;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Notice that the target DB table should have the same nof columns as the nof field of this class
    public void insertInDB(Connection conn,  String tableName) throws SQLException {
        PreparedStatement insertUser;
        insertUser = conn.prepareStatement("INSERT INTO "+tableName+" (name, email) VALUES (?, ?)");
        insertUser.setString(1,name);
        insertUser.setString(2,email);
        insertUser.execute();
    }
}
