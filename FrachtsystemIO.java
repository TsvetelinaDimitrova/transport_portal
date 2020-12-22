package fuhrunternehmen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Klasse für die Dateioperationen (Lesen/Speichern)
 * 
 * @author tssve
 * @version 1.1
 */
public class FrachtsystemIO {
    private static FrachtsystemIO frachtsystemIO;
    private ArrayList<Mitarbeiter> mitarbeiterliste = new ArrayList<>();
    private ArrayList<Kunden> kundenliste = new ArrayList<>();
    private ArrayList<Termin_Fracht> terminliste = new ArrayList<>();
    private File kundencsv = new File("kunden.csv");
    private File mitarbeitertxt = new File("mitarbeiter.txt");
    private File termincsv = new File("termine.csv");
    private String[] kunden;
    private String[] termin;

    /**
     * Konstruktor
     * 
     * @version 1.1
     */
    private FrachtsystemIO(){}
         
    /**
     * Übermitteln der Kundenliste
     * 
     * @return Kunden als ArrayList
     * @version 1.1
     */
    public ArrayList<Kunden> getKundenliste(){
        return kundenliste;
    }

    /**
     * Rückgabe eines String-Arrays mit den Kunden
     * 
     * @return Kundenarray
     * @version 1.2
     */
    public String[] getKundenArray(){
        return kunden;
    }
    
    /**
     * Übermitteln der Terminliste
     * 
     * @return Terminen als ArrayList
     * @version 1.1
     */
    public ArrayList<Termin_Fracht> getTerminliste(){
        return terminliste;
    }

    /**
     * Rückgabe eines String-Arrays mit den Terminen
     * 
     * @return TerminArray
     * @version 1.2
     */
    public String[] getTerminArray(){
        return termin;
    }
    
    /**
     * Übermitteln der Mitarbeiterliste
     * 
     * @return Mitarbeiterliste als ArrayList
     * @version 1.1
     */
    public ArrayList<Mitarbeiter> getMitarbeiterliste(){
        return mitarbeiterliste;
    }
    
    /**
     * Setzen der Kundenliste
     * 
     * @version 1.1
     */
    public void setKundenliste(){
        try{
            BufferedReader zeile = new BufferedReader(new FileReader(kundencsv));
            String str;
            while((str=zeile.readLine()) != null){
                String[] temp = str.split(";");
                kundenliste.add(new Kunden((temp[0]),(temp[1]), (temp[2]),
                    (temp[3]), (temp[4]), (temp[5])));
            }
            zeile.close();
            int lang = kundenliste.size();
            kunden = new String[lang];
            for(int i = 0; i < lang; i++){
                 kunden[i] = kundenliste.get(i).getName()+ ", "
                         + kundenliste.get(i).getStrasse() + ", "
                         + kundenliste.get(i).getPlz()+ ", "
                         + kundenliste.get(i).getOrt() +  ", "
                         + kundenliste.get(i).getTelefon()+ ", "
                         + kundenliste.get(i).getFax();
            }
        }
        catch(IOException e){
            errorMessage("Fehler beim Auslesen der Kunden.");
        }
    }           
    
    /**
     * Setzen der Terminliste
     * 
     * @version 1.1
     */
    public void setTerminliste(){
        try{
            BufferedReader zeile = new BufferedReader(new FileReader(termincsv));
            String str1;
            while((str1=zeile.readLine()) != null){
                String[] temp = str1.split(";");
                terminliste.add(new Termin_Fracht(temp[0],temp[1], 
                        (String.valueOf(temp[2])), temp[3], 
                        (String.valueOf(temp[4])), (String.valueOf(temp[5])),
                        (String.valueOf(temp[6])), temp[7], temp[8]));
            }
            zeile.close();
            int lang = terminliste.size();
            termin = new String[lang];
            for(int i = 0; i < lang; i++){
                 termin[i] = terminliste.get(i).getA_termin()+ ", "
                         + terminliste.get(i).getL_termin() + ", "
                         + terminliste.get(i).getTxt_anzahl()+ ", "
                         + terminliste.get(i).getTxt_rabattzeichen()+  ", "
                         + terminliste.get(i).getTxt_gewicht()+ ", "
                         + terminliste.get(i).getTxt_warenwert()+ ", "
                         + terminliste.get(i).getTxt_volume()+ ", "
                         + terminliste.get(i).getTxt_versicherung()+ ", "
                         + terminliste.get(i).getTxt_lagerorte();
            }
        }
        catch(IOException e){
            errorMessage("Fehler beim Auslesen die Termine.");
        }
    }           
    
     /**
     * Setzen der Mitarbeiterliste
     * @version 1.1
     */
    public void setMitarbeiterliste(){
        try{
            BufferedReader zeile = 
                    new BufferedReader(new FileReader(mitarbeitertxt));
            String str;
            int counter = 0;
            String ben = "";
            while((str=zeile.readLine()) != null){
                if(counter == 0){
                    ben = str;
                    counter++;
                }else
                {
                    mitarbeiterliste.add(new Mitarbeiter(ben,str));
                    counter = 0;
                }
            }
            zeile.close();
        }
        catch(IOException e){
            errorMessage("Fehler beim Auslesen der Mitarbeiter.");
        }
    }
    
    /**
     * Instanzprüfung FrachtsystemIO
     * 
     * @return Instand der FrachtsystemIO
     * @version 1.1
     */
    public static FrachtsystemIO getIOInstanz(){
        if(frachtsystemIO == null){
            frachtsystemIO = new FrachtsystemIO();
        }
        return frachtsystemIO;
    }  
    
    /**
     * Kundenliste updaten und anschließend speichern
     * 
     * @param kundenliste aktualisierte Kundenliste
     * @version 1.1
     */
    public void updateKundenliste(ArrayList<Kunden> kundenliste){
        this.kundenliste = kundenliste;
        schreibeKundeliste();
    }
    
    /**
     * Terminliste updaten und anschließend speichern
     * 
     * @param terminliste aktualisierte Terminliste
     * @version 1.1
     */
    public void updateTerminliste(ArrayList<Termin_Fracht> terminliste){
        this.terminliste = terminliste;
        schreibeTerminliste();
    }
    
    /**
     * Speichern der Kundenliste
     * 
     * @version 1.1
     */    
    private void schreibeKundeliste(){
        try {
            FileWriter fw = new FileWriter(kundencsv);
             int zaehler = 0;
            // Hinzufügen von Inhalten
            for (Kunden k : kundenliste){
                fw.write(k.toString());
                zaehler++;
            }
            fw.close();
        } catch (IOException ex) {
            errorMessage("Fehler beim Speichern der Kunden.");
        }
    }
    
    /** 
     * Speichern der Terminliste
     * 
     * @version 1.1
     */    
    private void schreibeTerminliste(){
        try {
            FileWriter fw = new FileWriter(this.termincsv, true);
             int zaehler = 0;
            // Hinzufügen von Inhalten
            for (Termin_Fracht t : terminliste){
                fw.write(t.getA_termin()+ ";" + t.getL_termin()+ ";" +
                        t.getTxt_anzahl()+ ";" + t.getTxt_rabattzeichen()+ ";" + 
                        t.getTxt_gewicht()+ ";" + t.getTxt_warenwert()+ ";" + 
                        t.getTxt_volume()+ ";" + t.getTxt_versicherung()+ ";" +
                        t.getTxt_lagerorte() + "\n");
                zaehler++;
            }
            fw.close();
        } catch (IOException ex) {
            errorMessage("Fehler beim Speichern der Termine.");
        }
    }        
    
    /**
     * Mitarbeiterliste updaten und anschließend speichern
     * 
     * @param mitarbeiterliste aktualisierte Mitarbeiterliste
     * @version 1.1
     */
    public void updateMitarbeiterliste(ArrayList<Mitarbeiter> mitarbeiterliste){
        this.mitarbeiterliste = mitarbeiterliste;
        schreibeMitarbeiterliste();
    }
    
    /**
     * Speichern der Mitarbeiterliste
     * @version 1.1
     */
    private void schreibeMitarbeiterliste(){
        try {
            FileWriter fw = new FileWriter(mitarbeitertxt);
            // Hinzufügen von Inhalten
            for (Mitarbeiter ma : mitarbeiterliste){
                fw.append(ma.getBenutzer()+"\n"+ma.getPasswort()+"\n");
            }
            fw.close();
        } catch (IOException ex) {
            errorMessage("Fehler beim Speichern der Mitarbeiter.");
        }
    }    
    
    /**
     * Dialogfenster zur Ausgabe der Fehlermeldungen
     * 
     * @param fehlermeldung Text der Fehlermeldung
     * @version 1.1
     */
    private void errorMessage(String fehlermeldung){
        JOptionPane.showMessageDialog(null, fehlermeldung, "Datenproblem", 
                JOptionPane.ERROR_MESSAGE);
    }
}