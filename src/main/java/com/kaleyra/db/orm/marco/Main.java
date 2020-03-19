package com.kaleyra.db.orm.marco;

import java.util.Scanner;

public class Main {



    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        Paziente marco = new Paziente(scan);

        System.out.print("Paziente con codice fiscale " + marco.cf + " creato correttamente.");

        //ResultSet rs = stmt.executeQuery("SELECT * FROM Ospedale WHERE POSTI_LETTO_DISPONIBILI > 0 AND LIKE '%%'");
    }
}
