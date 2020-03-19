package com.kaleyra.db.orm.marco;

//import com.sun.org.apache.bcel.internal.generic.PUSH;

import javax.persistence.Entity;
import java.util.Scanner;

@Entity
public class Paziente {

        public String cf;
        public String nome;
        public String cognome;
        public int eta;
        public String comuneResidenza;
        public int idOspedale;

        Scanner scan = new Scanner(System.in);

        public Paziente (Scanner scan) {
                System.out.println("Inserire codice fiscale del nuovo paziente: ");
                this.cf = scan.next();
                System.out.println("Inserire il nome: ");
                this.nome = scan.next();
                System.out.println("Inserire il cognome: ");
                this.cognome = scan.next();
                System.out.println("Inserire l'età: ");
                this.eta = scan.nextInt();
                System.out.println("Inserire il comune di residenza: ");
                this.comuneResidenza = scan.next();
                System.out.println("Inserire codice identificativo dell'ospedale attuale: ");
                this.idOspedale = scan.nextInt();
        }

        public String getCf() {
                return cf;
        }
        public String getNome() {
                return nome;
        }
        public String getCognome() {
                return cognome;
        }
        public int getEta() {
                return eta;
        }
        public String getComuneResidenza() {
                return comuneResidenza;
        }
        public int getIdOspedale() {
                return idOspedale;
        }

}
