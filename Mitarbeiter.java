package fuhrunternehmen;

/**
 * Klasse zum Erzeugen von Mitarbeiter
 * 
 * @author tssve
 * @version 1.0
 */
public class Mitarbeiter {
    //Deklarieren der Eigenschaften
    private String benutzer, passwort;

    /**
     * Konstruktor
     * 
     * @param benutzer Benutzername
     * @param passwort Passwort des Benutzers
     * @version 1.1
     */
    public Mitarbeiter(String benutzer, String passwort) {
        this.benutzer = benutzer;
        this.passwort = passwort;
    }
    
    /**
     * Rückgabe des Benutzernamens
     * @return Benutzername
     * @version 1.1
     */
    public String getBenutzer() {
        return benutzer;
    }
    
    /**
     * Ändern des Benutzernamens
     * @param benutzer Benutzername
     * @deprecated In dieser Version nicht verwendet.
     * @version 1.1
     */
    @Deprecated
    public void setBenutzer(String benutzer) {
        this.benutzer = benutzer;
    }

    /**
     * Rückgabe des Passwortes
     * @return Passwort
     * @version 1.1
     */
    public String getPasswort() {
        return passwort;
    }

    /**
     * Ändern des Passwortes
     * @param passwort Neues Passwort
     * @deprecated In dieser Version nicht vorgesehen
     * @version 1.1
     */
    @Deprecated
    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    /**
     * Rückgabe der Objekteigenschaften
     * @return 
     */
    @Override
    public String toString() {
        return "Mitarbeiter{" + "benutzer=" + benutzer + ", passwort=" + passwort + '}';
    }
    
}
