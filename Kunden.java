package fuhrunternehmen;

/**
 * Klasse zum Erzeugen von Kundenobjekten
 * 
 * @author tssve
 * @version 1.1
 */
public class Kunden {
    //Deklarieren der Eigenschaften
    private String name, strasse, plz, ort, telefon, fax;
    
    /**
     * Konstruktor
     * 
     * @param name
     * @param strasse
     * @param plz
     * @param ort
     * @param telefon
     * @param fax 
     * @version 1.0
     */ 
    public Kunden(String name, String strasse, String plz, String ort,
            String telefon, String fax) {
        this.name = name;
        this.strasse = strasse;
        this.plz = plz;
        this.ort = ort;
        this.telefon = telefon;
        this.fax = fax;
    }
    
    //Getter und Setter Methoden
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }
    
   
    //Überschreiben toString
    @Override
    public String toString() {
        return name + ";" + strasse + ";" + plz + ";" + ort + ";" + telefon + ";" 
                + fax + "\n";
    }
    
}

   
    
 
  
