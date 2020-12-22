package fuhrunternehmen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;

/**
 * Verwaltung des Frachtunternahmen
 *
 * @author tssve
 * @version 1.1
 */
public class Verwaltung extends JDialog implements ActionListener {
    public static final int KUNDENVERWALTUNG = 0;
    public static final int FRACHTVERWALTUNG = 1;
    public static final int MITARBEITERVERWALTUNG = 2;
    private int verwaltungsakt;
    private String fehler;
    private ArrayList<Mitarbeiter> mitarbeiterliste;
    private ArrayList<Kunden> kundenliste = new ArrayList<>();
    private ArrayList<Termin_Fracht> terminliste = new ArrayList<>();
    private File termincsv = new File("termine.csv");
    private File kundencsv = new File("kunden.csv");
    private int index, kv_laenge, fv_laenge;
//    public static int screenheight = 
//            java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
//    public static int screenwidth = 
//            java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    // Hauptfenster Verwaltung
    private JDialog verwaltung;
    // JPanel für Authentifizierung
    private JPanel anmeldung;
    private JLabel benutzername, passwort;
    private JTextField txt_benutzername;
    private JPasswordField jpf_passwort;
    private JButton bt_anmelden, bt_abbrechen;
    // JPanel für Kundenverwaltung
    private JPanel kundenverwaltung;
    private JLabel kv_name, kv_strasse, kv_plz, kv_ort, kv_telefon, kv_fax;
    private JTextField txt_kv_name, txt_kv_strasse, txt_kv_plz, txt_kv_ort, 
            txt_kv_telefon, txt_kv_fax;
    private JButton bt_kv_first, bt_kv_before, bt_kv_next,
            bt_kv_last, bt_kv_new, bt_kv_break, bt_kv_save;
    // JPanel für Frachtverwaltung
    private JPanel frachtverwaltung;
    private JLabel annahmetermin, liefertermin, anzahl, rabattzeichen,gewicht, 
            warenwert, volume, versicherung, lagerorte;
    private JTextField tx_annahme, tx_lieferung, txt_anzahl, 
            txt_rabattzeichen, txt_gewicht, txt_warenwert, txt_volume, 
            txt_versicherung, txt_lagerorte ;
    private JButton bt_fv_first, bt_fv_before, bt_fv_next,
            bt_fv_last, bt_fv_new, bt_fv_break, bt_fv_save;
    private JCheckBox uebergroesse, verdeblich, gefahrgut;
    // JPanel für Neuer Mitarbeiter
    private JPanel mitarbeiterverwaltung;
    private JButton bt_mav_break, bt_mav_save;
    private JLabel mav_benutzername, mav_passwort, mav_rep_passwort;
    private JTextField jtf_mav_benutzername;
    private JPasswordField jpf_mav_passwort, jpf_mav_rep_passwort;
        
    
    /** Erstellen des Fensters für die Verwaltung
     *
     * @param verwaltungsakt
     * @version 1.0
     */
    public Verwaltung(int verwaltungsakt) {
        FrachtsystemIO.getIOInstanz().setKundenliste();
        this.verwaltungsakt = verwaltungsakt;
        verwaltung = new JDialog();
        verwaltung.setTitle("Verwaltung");
        // Aktionen ausführen. 
        verwaltung.setModal(true);
        verwaltung.setSize(350, 250);
        verwaltung.setLocationRelativeTo(null);
        verwaltung.add(getAuthentifizierung());
        verwaltung.setVisible(true);
        verwaltung.toFront();
        }
    
    /**
     * Erstellen des Formulars für die Authentifizierung
     *
     * @return Authentifizierung mit Bedienelementen
     * @version 1.1
     */
    private JPanel getAuthentifizierung() {
        anmeldung = new JPanel();
        anmeldung.setLayout(null);
        benutzername = new JLabel("Benutzername");
        benutzername.setBounds(50, 50, 100, 25);
        passwort = new JLabel("Passwort");
        passwort.setBounds(50, 80, 100, 25);
        txt_benutzername = new JTextField();
        txt_benutzername.setBounds(175, 50, 100, 25);
        jpf_passwort = new JPasswordField();
        jpf_passwort.setBounds(175, 80, 100, 25);
        bt_anmelden = new JButton("Anmelden");
        bt_anmelden.setBounds(50, 110, 100, 25);
        bt_anmelden.addActionListener(this);
        bt_abbrechen = new JButton("Abbrechen");
        bt_abbrechen.setBounds(175, 110, 100, 25);
        bt_abbrechen.addActionListener(this);
        anmeldung.add(benutzername);
        anmeldung.add(passwort);
        anmeldung.add(txt_benutzername);
        anmeldung.add(jpf_passwort);
        anmeldung.add(bt_anmelden);
        anmeldung.add(bt_abbrechen);
        return anmeldung;
        }
    
    /**
     * Prüfung der Authentifizierung
     *
     * @return true Anmeldedaten korrekt
     * @version 1.1
     */
    private boolean pruefeAuthentifizierung() {
        boolean pruef = true;
        mitarbeiterliste = FrachtsystemIO.getIOInstanz().getMitarbeiterliste();        
        if (txt_benutzername.getText().trim().isEmpty()
                || jpf_passwort.getPassword().length == 0) {
            pruef = false;
            fehler = "Nicht alle benötigten Felder ausgefüllt.";
        }
        if (pruef) {
            boolean treffer = false;
            String ben = "";
            String pw = "";
            try {
                ben = EncryptPassword.
                        SHA512(txt_benutzername.getText().trim());
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            }
            for (Mitarbeiter ma : mitarbeiterliste) {
                if (ma.getBenutzer().equals(ben)) {
                    treffer = true;
                    pw = ma.getPasswort();
                }
            }
            if (!treffer) {
                pruef = false;
                fehler = ("Benutzer nicht bekannt");
            } else {
                try {
                    if (!(EncryptPassword.SHA512(Arrays.toString(jpf_passwort.
                            getPassword())).equals(pw))) {
                        pruef = false;
                        fehler = "Passwort nicht korrekt.";
                    }
                } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                }
            }
        }
        return pruef;
    }
        
    /**
     * Prüfung, ob bei Neuanmeldung alle Eingabefelder befüllt sind
     *
     * @return true, wenn alle Felder befüllt sind
     * @version 1.1
     */
    private boolean pruefeEingabe() {
        boolean pruef = true;
        if (jtf_mav_benutzername.getText().trim().isEmpty() 
                || jpf_mav_passwort.getPassword().length == 0 
                || jpf_mav_rep_passwort.getPassword().length == 0) {
            fehler = "Sie haben nicht alle Felder ausgefüllt.";
            pruef = false;
        }
        if (pruef) {
            pruef = pruefePasswortIdentisch();
        }
        return pruef;
    }
    
    /**
     * Prüfung, ob die Passwörter bei Neuanmeldung identisch sind
     *
     * @return true, wenn Passwörter identisch
     * @version 1.1
     */
    private boolean pruefePasswortIdentisch() {
        boolean pruef = true;
        if (jpf_mav_passwort.getPassword().length != jpf_mav_rep_passwort.getPassword().length) {
            pruef = false;
        } else {
            for (int i = 0; i < jpf_mav_passwort.getPassword().length; i++) {
                if (jpf_mav_passwort.getPassword()[i] != jpf_mav_rep_passwort.getPassword()[i]) {
                    pruef = false;
                    break;
                }
            }
        }
        if (!pruef) {
            fehler = "Passwort und Passwortwiederholung stimmen nicht überein.";
        }
        return pruef;
    }
    
    /**
     * Anzeigen des Dialogfensters für die Fehlermeldung
     *
     * @param fehlermeldung anzuzeigende Fehlermeldung
     * @version 1.1
     */
    private void errorMessage(String fehlermeldung) {
        JOptionPane.showMessageDialog(null, fehlermeldung, "Datenproblem", 
                JOptionPane.ERROR_MESSAGE);
    }

    /** 
     * Erstellen des Fensters für die Kunden
     * 
     * @return kundenverwaltung
     */
    private JPanel getKundenverwaltung() {
        kundenverwaltung = new JPanel();
        kundenverwaltung.setLayout(null);
        kv_name = new JLabel("Name");
        kv_name.setBounds(50, 150, 100, 25);
        kv_strasse = new JLabel("Strasse");
        kv_strasse.setBounds(50, 180, 100, 25);
        kv_plz = new JLabel("PLZ");
        kv_plz.setBounds(50, 210, 100, 25);
        kv_ort = new JLabel("Ort");
        kv_ort.setBounds(50, 240, 100, 25);
        kv_telefon = new JLabel("Telefon");
        kv_telefon.setBounds(50, 270, 100, 25);
        kv_fax = new JLabel("Fax");
        kv_fax.setBounds(50, 300, 100, 25);
        
        txt_kv_name = new JTextField();
        txt_kv_name.setBounds(175, 150, 175, 25);
        txt_kv_name.setEditable(false);
        txt_kv_strasse = new JTextField();
        txt_kv_strasse.setBounds(175, 180, 175, 25);
        txt_kv_strasse.setEditable(true);
        txt_kv_plz = new JTextField();
        txt_kv_plz.setBounds(175, 210, 175, 25);
        txt_kv_plz.setEditable(true);
        txt_kv_ort = new JTextField();
        txt_kv_ort.setBounds(175, 240, 175, 25);
        txt_kv_ort.setEditable(true);
        txt_kv_telefon = new JTextField();
        txt_kv_telefon.setBounds(175, 270, 175, 25);
        txt_kv_telefon.setEditable(true);
        txt_kv_fax = new JTextField();
        txt_kv_fax.setBounds(175, 300, 175, 25);
        txt_kv_fax.setEditable(true);

        bt_kv_first = new JButton("<<");
        bt_kv_first.setBounds(50, 340, 50, 25);
        bt_kv_first.addActionListener(this);
        bt_kv_first.setEnabled(false);
        bt_kv_before = new JButton("<");
        bt_kv_before.setBounds(100, 340, 50, 25);
        bt_kv_before.addActionListener(this);
        bt_kv_before.setEnabled(false);
        bt_kv_next = new JButton(">");
        bt_kv_next.setBounds(150, 340, 50, 25);
        bt_kv_next.addActionListener(this);
        bt_kv_next.setEnabled(true);
        bt_kv_last = new JButton(">>");
        bt_kv_last.setBounds(200, 340, 50, 25);
        bt_kv_last.addActionListener(this);
        bt_kv_last.setEnabled(false);
        bt_kv_new = new JButton("Neu");
        bt_kv_new.setBounds(250, 340, 80, 25);
        bt_kv_new.addActionListener(this);
        bt_kv_break = new JButton("Abbrechen");
        bt_kv_break.setBounds(330, 340, 100, 25);
        bt_kv_break.addActionListener(this);
        bt_kv_save = new JButton("Speichern");
        bt_kv_save.setBounds(430, 340, 100, 25);
        bt_kv_save.addActionListener(this);
        bt_kv_save.setVisible(false);
        kundenverwaltung.add(kv_name);
        kundenverwaltung.add(kv_strasse);
        kundenverwaltung.add(kv_plz);
        kundenverwaltung.add(kv_ort);
        kundenverwaltung.add(kv_telefon);
        kundenverwaltung.add(kv_fax);
        kundenverwaltung.add(txt_kv_name);
        kundenverwaltung.add(txt_kv_strasse);
        kundenverwaltung.add(txt_kv_plz);
        kundenverwaltung.add(txt_kv_ort);
        kundenverwaltung.add(txt_kv_telefon);
        kundenverwaltung.add(txt_kv_fax);
        kundenverwaltung.add(bt_kv_first);
        kundenverwaltung.add(bt_kv_before);
        kundenverwaltung.add(bt_kv_next);
        kundenverwaltung.add(bt_kv_last);
        kundenverwaltung.add(bt_kv_new);
        kundenverwaltung.add(bt_kv_break);
        kundenverwaltung.add(bt_kv_save);
        kundenverwaltung.setVisible(true);
        return kundenverwaltung;
    }
   
    
    
    /**
     * Erstellen des Fensters für die Fracht
     * 
     * @return frachtverwaltung
     */
    private JPanel getFrachtverwaltung() {
        frachtverwaltung = new JPanel();
        frachtverwaltung.setLayout(null);
  
        annahmetermin = new JLabel("Annahmetermin");
        annahmetermin.setBounds(50, 50, 100, 25);
        liefertermin = new JLabel("Liefertermin");
        liefertermin.setBounds(200, 50, 100, 25);
        
        tx_annahme = new JTextField();
        tx_annahme.setBounds(50, 80, 100, 25);
        tx_annahme.setEditable(false);

        tx_lieferung = new JTextField();
        tx_lieferung.setBounds(200, 80, 100, 25);
        tx_lieferung.setEditable(false);

        anzahl = new JLabel("Anzahl");
        anzahl.setBounds(50, 150, 100, 25);
        rabattzeichen = new JLabel("Rabbatzeichen");
        rabattzeichen.setBounds(50, 180, 100, 25);
        gewicht = new JLabel("Gewicht (kg)");
        gewicht.setBounds(50, 210, 100, 25);
        warenwert = new JLabel("Warenwert");
        warenwert.setBounds(50, 240, 100, 25);
        volume = new JLabel("Volume (m³)");
        volume.setBounds(50, 270, 100, 25);
        versicherung = new JLabel("Versicherung");
        versicherung.setBounds(50, 300, 100, 25);
        lagerorte = new JLabel("Lagerorte");
        lagerorte.setBounds(50, 330, 100, 25);
        
        txt_anzahl = new JTextField();
        txt_anzahl.setBounds(175, 150, 175, 25);
        txt_anzahl.setEditable(false);
        txt_rabattzeichen = new JTextField();
        txt_rabattzeichen.setBounds(175, 180, 175, 25);
        txt_rabattzeichen.setEditable(false);
        txt_gewicht = new JTextField();
        txt_gewicht.setBounds(175, 210, 175, 25);
        txt_gewicht.setEditable(false);
        txt_warenwert = new JTextField();
        txt_warenwert.setBounds(175, 240, 175, 25);
        txt_warenwert.setEditable(false);
        txt_volume = new JTextField();
        txt_volume.setBounds(175, 270, 175, 25);
        txt_volume.setEditable(false);
        txt_versicherung = new JTextField();
        txt_versicherung.setBounds(175, 300, 175, 25);
        txt_versicherung.setEditable(false);
        txt_lagerorte = new JTextField();
        txt_lagerorte.setBounds(175, 330, 175, 25);
        txt_lagerorte.setEditable(false);
        
        uebergroesse = new JCheckBox("Übergröße");
        verdeblich = new JCheckBox("verdeblich");
        gefahrgut = new JCheckBox("Gefahrgut");
               
        bt_fv_first = new JButton("<<");
        bt_fv_first.setBounds(50, 370, 50, 25);
        bt_fv_first.addActionListener(this);
        bt_fv_first.setEnabled(false);
        bt_fv_before = new JButton("<");
        bt_fv_before.setBounds(100, 370, 50, 25);
        bt_fv_before.addActionListener(this);
        bt_fv_before.setEnabled(false);
        bt_fv_next = new JButton(">");
        bt_fv_next.setBounds(150, 370, 50, 25);
        bt_fv_next.addActionListener(this);
        bt_fv_next.setEnabled(false);
        bt_fv_last = new JButton(">>");
        bt_fv_last.setBounds(200, 370, 50, 25);
        bt_fv_last.addActionListener(this);
        bt_fv_last.setEnabled(false);
        bt_fv_new = new JButton("Änderung");
        bt_fv_new.setBounds(250, 370, 100, 25);
        bt_fv_new.addActionListener(this);
        bt_fv_break = new JButton("Abbrechen");
        bt_fv_break.setBounds(350, 370, 100, 25);
        bt_fv_break.addActionListener(this);
        bt_fv_save = new JButton("Speichern");
        bt_fv_save.setBounds(450, 370, 100, 25);
        bt_fv_save.addActionListener(this);
        bt_fv_save.setVisible(false);
        
        frachtverwaltung.add(annahmetermin);
        frachtverwaltung.add(tx_annahme); 
        frachtverwaltung.add(liefertermin);
        frachtverwaltung.add(tx_lieferung);
        frachtverwaltung.add(anzahl);
        frachtverwaltung.add(rabattzeichen);
        frachtverwaltung.add(gewicht);
        frachtverwaltung.add(warenwert);
        frachtverwaltung.add(volume);
        frachtverwaltung.add(versicherung);
        frachtverwaltung.add(lagerorte);
        frachtverwaltung.add(txt_anzahl);
        frachtverwaltung.add(txt_rabattzeichen);
        frachtverwaltung.add(txt_gewicht);
        frachtverwaltung.add(txt_warenwert);
        frachtverwaltung.add(txt_volume);
        frachtverwaltung.add(txt_versicherung);
        frachtverwaltung.add(txt_lagerorte);
        frachtverwaltung.add(uebergroesse);
        frachtverwaltung.add(verdeblich);
        frachtverwaltung.add(gefahrgut);
        frachtverwaltung.add(bt_fv_first);
        frachtverwaltung.add(bt_fv_before);
        frachtverwaltung.add(bt_fv_next);
        frachtverwaltung.add(bt_fv_last);
        frachtverwaltung.add(bt_fv_new);
        frachtverwaltung.add(bt_fv_break);
        frachtverwaltung.add(bt_fv_save);
        frachtverwaltung.setVisible(true);
        return frachtverwaltung;
    }
    
    /**
     * Kunden in Formular anzeigen
     * 
     * @version 1.1
     */
    private void showKunden(){
        if (kundenliste.size()!=0){
        txt_kv_name.setText(kundenliste.get(index).getName());
        txt_kv_strasse.setText(kundenliste.get(index).getStrasse());
        txt_kv_plz.setText(kundenliste.get(index).getPlz());
        txt_kv_ort.setText(kundenliste.get(index).getOrt());
        txt_kv_telefon.setText(kundenliste.get(index).getTelefon());
        txt_kv_fax.setText(kundenliste.get(index).getFax());
        }
    }

    /**
     * Terminen in Formular anzeigen
     * 
     * @version 1.1
     */
    private void showTerminen(){
        if (terminliste.size()!=0){
        tx_annahme.setText(terminliste.get(index).getA_termin());
        tx_lieferung.setText(terminliste.get(index).getL_termin());
        txt_anzahl.setText(String.valueOf(terminliste.get(index).getTxt_anzahl()));
        txt_rabattzeichen.setText(terminliste.get(index).getTxt_rabattzeichen());
        txt_gewicht.setText(String.valueOf(terminliste.get(index).getTxt_gewicht()));
        txt_warenwert.setText(String.valueOf(terminliste.get(index).getTxt_warenwert()));
        txt_volume.setText(String.valueOf(terminliste.get(index).getTxt_volume()));
        txt_versicherung.setText(terminliste.get(index).getTxt_versicherung());
        txt_lagerorte.setText(terminliste.get(index).getTxt_lagerorte());
        }
    }
    
    /**
     * Erstellen des Formulars für die Mitarbeiterverwaltung
     *
     * @return Mitarbeiterverwaltung mit Bedienelementen
     * @version 1.1
     */
    private JPanel getMitarbeiterverwaltung() {
        mitarbeiterverwaltung = new JPanel();
        mitarbeiterverwaltung.setLayout(null);
        mav_benutzername = new JLabel("Benutzername");
        mav_benutzername.setBounds(50, 50, 150, 25);
        mav_passwort = new JLabel("Passwort");
        mav_passwort.setBounds(50, 80, 150, 25);
        mav_rep_passwort = new JLabel("Passwortwiederholung");
        mav_rep_passwort.setBounds(50, 110, 150, 25);
        jtf_mav_benutzername = new JTextField();
        jtf_mav_benutzername.setBounds(200, 50, 150, 25);
        jpf_mav_passwort = new JPasswordField();
        jpf_mav_passwort.setBounds(200, 80, 150, 25);
        jpf_mav_rep_passwort = new JPasswordField();
        jpf_mav_rep_passwort.setBounds(200, 110, 150, 25);
        bt_mav_save = new JButton("Speichern");
        bt_mav_save.setBounds(50, 140, 140, 25);
        bt_mav_save.addActionListener(this);
        bt_mav_break = new JButton("Abbrechen");
        bt_mav_break.setBounds(200, 140, 140, 25);
        bt_mav_break.addActionListener(this);
        mitarbeiterverwaltung.add(bt_mav_break);
        mitarbeiterverwaltung.add(bt_mav_save);
        mitarbeiterverwaltung.add(mav_benutzername);
        mitarbeiterverwaltung.add(mav_passwort);
        mitarbeiterverwaltung.add(mav_rep_passwort);
        mitarbeiterverwaltung.add(jtf_mav_benutzername);
        mitarbeiterverwaltung.add(jpf_mav_passwort);
        mitarbeiterverwaltung.add(jpf_mav_rep_passwort);
        return mitarbeiterverwaltung;
    }
    
    /**
     * Einblenden des JPanels je nach Auswahl im Menü
     *
     * @version 1.1
     */
    private void verwaltungsaktEinblenden() {
        verwaltung.setSize(800, 500);
        verwaltung.setLocationRelativeTo(null);
//        verwaltung.setLocation(-5, 0);
        verwaltung.remove(anmeldung);
        switch (verwaltungsakt) {
            case 0:
                verwaltung.add(getKundenverwaltung());
                kundenliste
                        = FrachtsystemIO.getIOInstanz().getKundenliste();
                index = 0;
                kv_laenge = kundenliste.size();
                if (kv_laenge <= 1) {
                    bt_kv_next.setEnabled(false);
                }
                bt_kv_before.setEnabled(false);
                showKunden();
                break;     
            case 1:
                verwaltung.add(getFrachtverwaltung());
                terminliste
                        = FrachtsystemIO.getIOInstanz().getTerminliste();
                index = 0;
                fv_laenge = terminliste.size();
                if (fv_laenge <= 1) {
                    bt_fv_next.setEnabled(false);
                }
                bt_fv_before.setEnabled(false);
                showTerminen();
                break;     
            case 2:
                verwaltung.add(getMitarbeiterverwaltung());
                break;
        }
        verwaltung.repaint();
    }

    /**
     * Kundenliste speichern
     *
     * @version 1.1
     */
    private void kv_speichern() {
        FrachtsystemIO.getIOInstanz().updateKundenliste(kundenliste);
    }
    
    /**
     * Terminliste speichern
     *
     * @version 1.1
     */
    private void fv_speichern() {
        FrachtsystemIO.getIOInstanz().updateTerminliste(terminliste);
    }
    
    /**
     * Prüfung, ob Einträge in derKundenverwaltung vollständig sind
     *
     * @return true, wenn Einträge vollständig
     * @version 1.1
     */
    private boolean kv_eintrag_vollstaendig() {
        boolean pruef = true;
        if (txt_kv_name.getText().trim().isEmpty()
            || txt_kv_strasse.getText().trim().isEmpty()
            || txt_kv_plz.getText().trim().isEmpty()
            || txt_kv_ort.getText().trim().isEmpty()
            || txt_kv_telefon.getText().trim().isEmpty()
            || txt_kv_fax.getText().trim().isEmpty()){
            pruef = false;
            fehler = "Einträge unvollständig.";
        }
        return pruef;
    }

    /**
     * Prüfung, ob Einträge in derKundenverwaltung vollständig sind
     *
     * @return true, wenn Einträge vollständig
     * @version 1.1
     */
    private boolean fv_eintrag_vollstaendig() {
        boolean pruef = true;
        if (tx_annahme.getText().trim().isEmpty()
                    || tx_lieferung.getText().trim().isEmpty()
                    || txt_anzahl.getText().trim().isEmpty()
                    || txt_rabattzeichen.getText().trim().isEmpty()
                    || txt_gewicht.getText().trim().isEmpty()
                    || txt_warenwert.getText().trim().isEmpty()
                    || txt_volume.getText().trim().isEmpty()
                    || txt_versicherung.getText().trim().isEmpty()
                    || txt_lagerorte.getText().trim().isEmpty()){ 
            pruef = false;
                fehler = "Nicht alle Felder ausgefüllt";
        }
        return pruef;
    }
    
    /**
     * Update und Speichern der Kunden
     *
     * @version 1.1
     */
    private void updateAndSave() {
        kundenliste.get(index).setStrasse(txt_kv_strasse.getText().trim());
        kundenliste.get(index).setPlz(txt_kv_plz.getText().trim());
        kundenliste.get(index).setOrt(txt_kv_ort.getText().trim());
        kundenliste.get(index).setTelefon(txt_kv_telefon.getText().trim());
        kundenliste.get(index).setFax(txt_kv_fax.getText().trim());
        kv_speichern();
    }
    
    /**
     * Update und Speichern der Kunden
     *
     * @version 1.1
     */
    private void updateAndSaveTermine() {
        terminliste.get(index).setA_termin(tx_annahme.getText().trim());
        terminliste.get(index).setL_termin(tx_lieferung.getText().trim());
        terminliste.get(index).setTxt_anzahl(Double.valueOf(txt_anzahl.getText().trim()));
        terminliste.get(index).setTxt_rabattzeichen(txt_rabattzeichen.getText().trim());
        terminliste.get(index).setTxt_gewicht(Double.valueOf(txt_gewicht.getText().trim()));
        terminliste.get(index).setTxt_warenwert(Double.valueOf(txt_warenwert.getText().trim()));
        terminliste.get(index).setTxt_volume(Double.valueOf(txt_volume.getText().trim()));
        terminliste.get(index).setTxt_versicherung(txt_versicherung.getText().trim());
        terminliste.get(index).setTxt_lagerorte(txt_lagerorte.getText().trim());
        fv_speichern();
    }
    
    /**
     * Eventhandling ActionListener
     *
     * @param e auslösendes Objekt
     * @version 1.1
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == bt_kv_break
            || e.getSource() == bt_fv_break
            || e.getSource() == bt_mav_break){
        verwaltung.dispose();
        }
        
        // Eventhandling Kundenverwaltung
        if (e.getSource() == bt_kv_new) {
            txt_kv_name.setEditable(true);
            txt_kv_name.setText("");
            txt_kv_strasse.setText("");
            txt_kv_strasse.setEditable(true);
            txt_kv_plz.setText("");
            txt_kv_plz.setEditable(true);
            txt_kv_ort.setText("");
            txt_kv_ort.setEditable(true);
            txt_kv_telefon.setText("");
            txt_kv_telefon.setEditable(true);
            txt_kv_fax.setText("");
            txt_kv_fax.setEditable(true);
            bt_kv_save.setVisible(true);
        }
        if (e.getSource() == bt_kv_save) {
            boolean pruef = true;
            if (txt_kv_name.getText().trim().isEmpty()
                    || txt_kv_strasse.getText().trim().isEmpty()
                    || txt_kv_plz.getText().trim().isEmpty()
                    || txt_kv_ort.getText().trim().isEmpty()
                    || txt_kv_telefon.getText().trim().isEmpty()
                    || txt_kv_fax.getText().trim().isEmpty()) {
                pruef = false;
                fehler = "Nicht alle Felder ausgefüllt";
            }
            
            if (pruef) {
                kundenliste.add(new Kunden(txt_kv_name.getText().trim(), 
                        txt_kv_strasse.getText().trim(), 
                        txt_kv_plz.getText().trim(), 
                        txt_kv_ort.getText().trim(), 
                        txt_kv_telefon.getText().trim(), 
                        txt_kv_fax.getText().trim()));
                kv_laenge = kundenliste.size();
                index = kv_laenge - 1;
                kv_speichern();
                bt_kv_save.setVisible(false);
                txt_kv_name.setEditable(false);
            }
        }
        
        if (e.getSource() == bt_kv_next
                || e.getSource() == bt_kv_first
                || e.getSource() == bt_kv_last
                || e.getSource() == bt_kv_before) {
            boolean pruef = true;
            pruef = kv_eintrag_vollstaendig();
            if (pruef) {
                updateAndSave();
                if (e.getSource() == bt_kv_next) {
                    index++;
                    bt_kv_before.setEnabled(true);
                    if (index == kv_laenge - 1) {
                        bt_kv_next.setEnabled(false);
                    }else{
                        bt_kv_next.setEnabled(true);
                    }
                } else if (e.getSource() == bt_kv_first) {
                    index = 0;
                    bt_kv_before.setEnabled(false);
                    if (kv_laenge > 1) {
                        bt_kv_next.setEnabled(true);
                    }
                }else if (e.getSource() == bt_kv_last){
                    index = kv_laenge - 1;
                    bt_kv_next.setEnabled(false);
                    if (kv_laenge > 1){
                        bt_kv_before.setEnabled(true);
                    }
                }else if (e.getSource() == bt_kv_before){
                    index--;
                    bt_kv_next.setEnabled(true);
                    if (index != 0){
                        bt_kv_before.setEnabled(true);
                    }else{
                        bt_kv_before.setEnabled(false);
                    }
                }
                showKunden();

            } else {
                errorMessage(fehler);
            }
        }
        
        // Eventhandling Frachtverwaltung
        if (e.getSource() == bt_fv_new){ 
            
            tx_annahme.setEditable(true);
            tx_lieferung.setEditable(true); 
            txt_anzahl.setEditable(true);
            txt_rabattzeichen.setEditable(true);
            txt_gewicht.setEditable(true);
            txt_warenwert.setEditable(true);
            txt_volume.setEditable(true);
            txt_versicherung.setEditable(true);
            txt_lagerorte.setEditable(true);
            bt_fv_save.setVisible(true);
        }
        
        if (e.getSource() == bt_fv_save){
            boolean pruef = true;
            if( tx_annahme.getText().trim().isEmpty()
                ||tx_lieferung.getText().trim().isEmpty()
                || txt_anzahl.getText().trim().isEmpty()
                || txt_rabattzeichen.getText().trim().isEmpty()
                || txt_gewicht.getText().trim().isEmpty()
                || txt_warenwert.getText().trim().isEmpty()
                || txt_volume.getText().trim().isEmpty()
                || txt_versicherung.getText().trim().isEmpty()
                || txt_lagerorte.getText().trim().isEmpty()){
            pruef = false;
                fehler = "Nicht alle Felder ausgefüllt";
            }
        }
            
        
        if (e.getSource() == bt_fv_next
                || e.getSource() == bt_fv_first
                || e.getSource() == bt_fv_last
                || e.getSource() == bt_fv_before) {
            boolean pruef = true;
            pruef = fv_eintrag_vollstaendig();
            if (pruef) {
                updateAndSaveTermine();
                if (e.getSource() == bt_fv_next) {
                    index++;
                    bt_fv_before.setEnabled(true);
                    if (index == fv_laenge - 1) {
                        bt_fv_next.setEnabled(false);
                    }else{
                        bt_fv_next.setEnabled(true);
                    }
                } else if (e.getSource() == bt_fv_first) {
                    index = 0;
                    bt_fv_before.setEnabled(false);
                    if (fv_laenge > 1) {
                        bt_fv_next.setEnabled(true);
                    }
                }else if (e.getSource() == bt_fv_last){
                    index = fv_laenge - 1;
                    bt_fv_next.setEnabled(false);
                    if (fv_laenge > 1){
                        bt_fv_before.setEnabled(true);
                    }
                }else if (e.getSource() == bt_fv_before){
                    index--;
                    bt_fv_next.setEnabled(true);
                    if (index != 0){
                        bt_fv_before.setEnabled(true);
                    }else{
                        bt_fv_before.setEnabled(false);
                    }
                }
                showTerminen();
            } else {
                errorMessage(fehler);
            }
        }
        
        // Eventhandling Authentifizierung
        if (e.getSource() == bt_anmelden) {
            boolean pruef = pruefeAuthentifizierung();
            pruef = true;
            if (pruef) {
                verwaltungsaktEinblenden();
            } else {
                errorMessage(fehler);
            }
        }
        
        // Eventhandlich Mitarbeiterverwaltung
        if (e.getSource() == bt_mav_save) {
            boolean pruef = pruefeEingabe();
            if (pruef) {
                try {
                    String benutzerSHA512 = EncryptPassword.SHA512(jtf_mav_benutzername.getText().trim());
                    String passwortSHA512 = EncryptPassword.SHA512(Arrays.toString(jpf_mav_passwort.getPassword()));
                    mitarbeiterliste.add(new Mitarbeiter(benutzerSHA512, passwortSHA512));
                    FrachtsystemIO.getIOInstanz().updateMitarbeiterliste(mitarbeiterliste);
                } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                    errorMessage("Daten können nicht verschlüsselt werden.");
                }
            } else {
                errorMessage(fehler);
            }
        }
    }
}