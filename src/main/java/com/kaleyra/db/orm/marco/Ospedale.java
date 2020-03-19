package com.kaleyra.db.orm.marco;

import javax.persistence.Entity;

@Entity
public class Ospedale {

    public int codiceRegione;
    public String descrizione;
    public int codiceStruttura;
    public String denominazioneStruttura;
    public String indirizzo;
    public int codiceComune;
    public String comune;
    public String siglaProvincia;
    public int postiLettoDisponibili;

    public Ospedale(int codiceRegione, String descrizione, int codiceStruttura, String denominazioneStruttura, String indirizzo, int codiceComune, String comune, String siglaProvincia, int postiLettoDisponibili) {
        this.codiceRegione = codiceRegione;
        this.descrizione = descrizione;
        this.codiceStruttura = codiceStruttura;
        this.denominazioneStruttura = denominazioneStruttura;
        this.indirizzo = indirizzo;
        this.codiceComune = codiceComune;
        this.comune = comune;
        this.siglaProvincia = siglaProvincia;
        this.postiLettoDisponibili = postiLettoDisponibili;
    }
}
