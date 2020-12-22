package fuhrunternehmen;

/**
 * Klasse zum Erzeugen von Termin_Frachtobjekten 
 * mit Elementen von Subklasse Fracht
 * 
 * @author tssve
 * @version 1.0 
 */
public class Termin_Fracht extends Fracht{
    private String tx_lieferung;
    private String tx_annahme;
    
    /**
     * Konstruktor
     * 
     * @param tx_annahme
     * @param tx_lieferung
     
     */
    public Termin_Fracht(String tx_annahme, String tx_lieferung, double txt_anzahl, 
            String txt_rabattzeichen, double txt_gewicht, double txt_warenwert, 
            double txt_volume, String txt_versicherung, String txt_lagerorte){
        super(txt_anzahl, txt_rabattzeichen, txt_gewicht, txt_warenwert, 
                txt_volume, txt_versicherung, txt_lagerorte);
        this.tx_lieferung = tx_lieferung;
        this.tx_annahme = tx_annahme;
    }

    // Getter und Setter Methoden
    public String getA_termin() {
        return tx_annahme;
    }

    public void setA_termin(String a_termin) {
        this.tx_annahme = tx_annahme;
    }

    public String getL_termin() {
        return tx_lieferung;
    }

    public void setL_termin(String l_termin) {
        this.tx_lieferung = tx_lieferung;
    }

    /**
     * Rückgabe der Objekteigenschaften
     * @return 
     */
    @Override
    public String toString() {
        return tx_annahme + ";" + tx_lieferung;
    }
}
