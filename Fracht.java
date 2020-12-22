package fuhrunternehmen;

/**
 * Klasse zum Erzeugen von Frachtobjekten
 * 
 * @author tssve
 * @version 1.1
 */
public class Fracht {
    //Deklarieren der Eigenschaften
    private String txt_versicherung, txt_lagerorte, txt_rabattzeichen;
    private double txt_anzahl, txt_gewicht, txt_warenwert, txt_volume;

    /**
     * Konstruktor
     * 
     * @param txt_anzahl
     * @param txt_rabattzeichen
     * @param txt_gewicht
     * @param txt_warenwert
     * @param txt_volume
     * @param versicherung
     * @param lagerorte 
     */
    public Fracht(double txt_anzahl, String txt_rabattzeichen, double txt_gewicht,
            double txt_warenwert, double txt_volume, 
            String txt_versicherung, String txt_lagerorte){
        this.txt_anzahl = txt_anzahl;
        this.txt_rabattzeichen = txt_rabattzeichen;
        this.txt_gewicht = txt_gewicht;
        this.txt_warenwert = txt_warenwert;
        this.txt_volume = txt_volume;
        this.txt_versicherung = txt_versicherung;
        this.txt_lagerorte = txt_lagerorte;
    }
    
    // Getter und Setter Methoden
    public String getTxt_versicherung() {
        return txt_versicherung;
    }

    public void setTxt_versicherung(String txt_versicherung) {
        this.txt_versicherung = txt_versicherung;
    }

    public String getTxt_lagerorte() {
        return txt_lagerorte;
    }

    public void setTxt_lagerorte(String txt_lagerorte) {
        this.txt_lagerorte = txt_lagerorte;
    }

    public String getTxt_rabattzeichen() {
        return txt_rabattzeichen;
    }

    public void setTxt_rabattzeichen(String txt_rabattzeichen) {
        this.txt_rabattzeichen = txt_rabattzeichen;
    }

    public double getTxt_anzahl() {
        return txt_anzahl;
    }

    public void setTxt_anzahl(double txt_anzahl) {
        this.txt_anzahl = txt_anzahl;
    }

    public double getTxt_gewicht() {
        return txt_gewicht;
    }

    public void setTxt_gewicht(double txt_gewicht) {
        this.txt_gewicht = txt_gewicht;
    }

    public double getTxt_warenwert() {
        return txt_warenwert;
    }

    public void setTxt_warenwert(double txt_warenwert) {
        this.txt_warenwert = txt_warenwert;
    }

    public double getTxt_volume() {
        return txt_volume;
    }

    public void setTxt_volume(double txt_volume) {
        this.txt_volume = txt_volume;
    }

    //Überschreiben toString
    @Override
    public String toString() {
        return txt_anzahl + ";" + txt_rabattzeichen + ";" + txt_gewicht + ";" + txt_warenwert +
                ";" + txt_volume + ";" + txt_versicherung + ";" + txt_lagerorte + "\n";
    }
    
}