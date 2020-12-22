package fuhrunternehmen;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Klasse für das Erstellen von Filiale Berlin
 * 
 * @author tssve
 * @version 1.1
 */
public class Auftragsverwaltung extends JDialog implements ActionListener {
    // Erzeugung eines neuen Frames mit JDialog
    private JDialog auftragsverwaltung;
    public static int screenheight = 
            java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
    public static int screenwidth = 
            java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    private ArrayList<Termin_Fracht> terminliste = new ArrayList<>();
    private File termincsv = new File("termine.csv");
    private String fehler;
    private int index, tf_laenge;
    
    // JSplitPane-ELemente
    private JSplitPane jsp_auftragsverwaltung, panel1, panel4;
    private JPanel jp_menu;
    private JButton bt_frachtauftrag, bt_einsatzplanung, bt_fakturierung;
    int button_height = 300;
    int button_width = 30;
    int y = 25;
    
    // JButton Frachtauftrag
    private JPanel jp_frachtauftrag, jp_absender, jp_empfaenger, jp_fracht;   
    private JTextField txt_kv_name, txt_kv_strasse, txt_kv_plz, txt_kv_ort, 
            txt_kv_telefon, txt_kv_fax; // JPanel Absender 
    private JTextField txt_name, txt_strasse, txt_plz, txt_ort,txt_telefon,
            txt_fax; // JPanel Empfänger
    private JScrollPane sp_kunde;
    private JButton name_print, zum_fakturierung;
    private JRadioButton rb_absender, rb_empfaenger;
    private ButtonGroup radiobuttons;
    private JLabel jl_a_termin, jl_l_termin;
    private JTextField tx_annahme, tx_lieferung;
    
    private JCheckBox uebergroesse, verdeblich, gefahrgut;
    private Dimension dim, br, dm, im, frh;
    private ArrayList<Kunden> kundenliste = new ArrayList<>();
    private File kundencsv = new File("kunden.csv");
    private JTextField txt_anzahl, txt_rabattzeichen, txt_gewicht, txt_warenwert, 
            txt_volume, txt_versicherung, txt_lagerorte ;
    
    // JButton Einsatzplanung
    private JPanel jp_einsatzplanung;
    // JButton Fakturierung
    private JPanel jp_fakturierung, jp, panel5, panel2, panel3, panel_fr, panel_rh;
    private JButton  bt1, bt2;
    private JList list_fr, list_rh;
    private JTabbedPane tabbedPane;
    
    /**
     * Erstellen die Objekten von Aftragsverwaltung
     * 
     * @version 1.0
     */
    public Auftragsverwaltung(){
        auftragsverwaltung = new JDialog();
        auftragsverwaltung.setTitle("Frahtauftrag");
        auftragsverwaltung.setSize(screenwidth, screenheight);
        auftragsverwaltung.setLocationRelativeTo(null);
        // Eine Fensterschließkonstante, mit der das Fenster geschlossen werden kann
        auftragsverwaltung.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE); 
        auftragsverwaltung.setAlwaysOnTop(true);
        auftragsverwaltung.add(setAuftragsverwaltung());
    }
    
    /**
     * Sichtbarmachen der aktuellen JDialog
     * 
     * @version 1.0
     */
    public void getAuftragsverwaltung(){
        auftragsverwaltung.setModal(true);
        auftragsverwaltung.setVisible(true);
    }
    
    /**
     * JPanel Menu mit den Bedienbuttons
     * 
     * @return JPanel
     * @version 1.0
     */
    private JPanel getMenu(){
        jp_menu = new JPanel();
        jp_menu.setLayout(null);
        bt_frachtauftrag = new JButton("Frachtauftrag");
        bt_frachtauftrag.setBounds(300, y, button_height, button_width);
        bt_frachtauftrag.addActionListener(this);
        bt_einsatzplanung = new JButton("Einsatzplanung");
        bt_einsatzplanung.setBounds(650, y, button_height, button_width);
        bt_einsatzplanung.addActionListener(this);
        bt_fakturierung = new JButton("Fakturierung");
        bt_fakturierung.setBounds(1000, y, button_height, button_width);
        bt_fakturierung.addActionListener(this);        
        jp_menu.add(bt_frachtauftrag);
        jp_menu.add(bt_einsatzplanung);
        jp_menu.add(bt_fakturierung);
        jp_menu.setVisible(true);
        return jp_menu;
    }
      
    /**
     * JPanel für den Frachtauftrag
     * 
     * @return JPanel Frachtauftrag
     * @version 1.0
     */
    private JPanel getFrachtauftrag(){
        jp_frachtauftrag = new JPanel();
        jl_a_termin = new JLabel("Annahmetermin");
        jl_a_termin.setVisible(true);
        tx_annahme = new JTextField(10);
        jl_l_termin = new JLabel("Liefertermin");
        jl_l_termin.setVisible(true);
        tx_lieferung = new JTextField(10);
        // Einfügen des Absenderbereich
        jp_frachtauftrag.add(getJPAbsender());
        jp_frachtauftrag.add(jl_a_termin);
        jp_frachtauftrag.add(tx_annahme);
        // Einfügen des Empfängerbereich
        jp_frachtauftrag.add(getJPEmpfaenger());
        jp_frachtauftrag.add(jl_l_termin);
        jp_frachtauftrag.add(tx_lieferung);
        // Einfügen des Frachtbereich
        jp_frachtauftrag.add(getJPFracht());
        uebergroesse = new JCheckBox("Übergröße");
        verdeblich = new JCheckBox("verdeblich");
        gefahrgut = new JCheckBox("Gefahrgut");
        jp_frachtauftrag.add(uebergroesse);
        jp_frachtauftrag.add(verdeblich);
        jp_frachtauftrag.add(gefahrgut);
        // Einfügen des Kundebereich
        jp_frachtauftrag.add(getJSPKunde());
        zum_fakturierung = new JButton("Bestellen");
        zum_fakturierung.addActionListener(this);
        jp_frachtauftrag.add(zum_fakturierung);
        zum_fakturierung.setBackground(Color.red);        
        return jp_frachtauftrag;
    }
    
    /**
     * Erstellen JPanel für die Kundenbereich
     *
     * @return JPanel Kunden mit Bedienelementen
     * @version 1.1
     */
    private JScrollPane getJSPKunde(){
        jp = new JPanel();
//        listname = new JList();
        // Konstruktor, der ein neues Listenmodellobjekt erstellt
        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> listname = new JList<>(model);
        jp.setBorder(BorderFactory.createTitledBorder("Wählen Sie einen Kunde aus"));        
        dim = getPreferredSize();
        dim.height = 250;
        dim.width = 450;
        jp.setPreferredSize(dim);
        rb_absender = new JRadioButton("Absender");
        rb_empfaenger = new JRadioButton("Empfaenger");
        radiobuttons = new ButtonGroup();
        rb_absender.addActionListener(this);
        rb_empfaenger.addActionListener(this);
        sp_kunde = new JScrollPane(jp,
                        JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        br = getPreferredSize();
        br.height = 400;
        br.width = 400;
        listname.setPreferredSize(br);
        sp_kunde.setVisible(true);
        jp.add(rb_absender);
        jp.add(rb_empfaenger);
        radiobuttons.add(rb_absender);
        radiobuttons.add(rb_empfaenger);
        jp.setVisible(true);
       
        // Erstellen einen Leser
        try (BufferedReader br = Files.newBufferedReader(Paths.get("kunden.csv"))) {
            // CSV Datei Delimiter
            String DELIMITER = ",";
            // Lesen die Datei Zeile für Zeile
            String line;
            while ((line = br.readLine()) != null) {
                // Zeile in Spalten konvertieren
                String[] columns = line.split(DELIMITER);
                // der entsprechende Inhalt der Modelposition ausgegeben
                model.addElement(( String.join(", ", columns)));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        listname.setModel(model);
        listname.setVisible(true);
        jp.add(new JScrollPane(listname));
        name_print = new JButton("Einfügen");
        jp.add(name_print);
        name_print.setVisible(true);

        return sp_kunde;
    }
       
    /**
     * Erstellen JPanel für die Absenderbereich
     *
     * @return JPanel Absender mit Bedienelementen
     * @version 1.1
     */
    private JPanel getJPAbsender(){
        jp_absender = new JPanel();
        jp_absender.setBorder(BorderFactory.createTitledBorder("Absender"));
        dm = getPreferredSize();
        dm.height = 330;
        dm.width = 430;
        jp_absender.setPreferredSize(dm);
        jp_absender.setLayout(new GridBagLayout());
        GridBagConstraints gbCon = new GridBagConstraints();
        gbCon.gridx = 0;
        gbCon.gridy = 0;
        gbCon.anchor = GridBagConstraints.WEST;
        gbCon.ipady = 7;
        gbCon.gridwidth = 1;
        gbCon.insets = new Insets(5, 5, 5, 5);
        txt_kv_name = new JTextField(30);
        txt_kv_strasse = new JTextField(30);
        txt_kv_plz = new JTextField(30);
        txt_kv_ort = new JTextField(30);
        txt_kv_telefon = new JTextField(30);
        txt_kv_fax = new JTextField(30);
        jp_absender.add(new JLabel("Name"), gbCon);
        gbCon.gridx++;
        jp_absender.add(txt_kv_name, gbCon);
        gbCon.gridx = 0;
        gbCon.gridy++;
        gbCon.anchor = GridBagConstraints.NORTHWEST;
        jp_absender.add(new JLabel("Strasse"), gbCon);
        gbCon.gridx++;
        jp_absender.add(txt_kv_strasse, gbCon);
        gbCon.gridx = 0;
        gbCon.gridy++;
        gbCon.anchor = GridBagConstraints.NORTHWEST;
        jp_absender.add(new JLabel("Plz"), gbCon);
        gbCon.gridx++;
        jp_absender.add(txt_kv_plz, gbCon);
        gbCon.gridx = 0;
        gbCon.gridy++;
        gbCon.anchor = GridBagConstraints.NORTHWEST;
        jp_absender.add(new JLabel("Ort"), gbCon);
        gbCon.gridx++;
        jp_absender.add(txt_kv_ort, gbCon);
        gbCon.gridx = 0;
        gbCon.gridy++;
        gbCon.anchor = GridBagConstraints.NORTHWEST;
        jp_absender.add(new JLabel("Telefon"), gbCon);
        gbCon.gridx++;
        jp_absender.add(txt_kv_telefon, gbCon);
        gbCon.gridx = 0;
        gbCon.gridy++;
        gbCon.anchor = GridBagConstraints.NORTHWEST;
        jp_absender.add(new JLabel("Fax"), gbCon);
        gbCon.gridx++;
        jp_absender.add(txt_kv_fax, gbCon);
        
        jp_absender.setVisible(true);
        jp_absender.setVisible(true);
        return jp_absender;
    }
        
    /**
     * Erstellen JPanel für die Empfängerbereich
     *
     * @return JPanel Empfänger mit Bedienelementen
     * @version 1.1
     */
    private JPanel getJPEmpfaenger(){
        jp_empfaenger = new JPanel();
        jp_empfaenger.setBorder(BorderFactory.createTitledBorder("Empfänger"));
        im = getPreferredSize();
        im.height = 320;
        im.width = 480;
        jp_empfaenger.setPreferredSize(im);
        jp_empfaenger.setLayout(new GridBagLayout());
        GridBagConstraints gbCon = new GridBagConstraints();
        gbCon.gridx = 0;
        gbCon.gridy = 0;
        gbCon.anchor = GridBagConstraints.WEST;
        gbCon.ipady = 7;
        gbCon.gridwidth = 1;
        gbCon.insets = new Insets(5, 5, 5, 5);
        txt_name = new JTextField(30);
        txt_strasse = new JTextField(30);
        txt_plz = new JTextField(30);
        txt_ort = new JTextField(30);
        txt_telefon = new JTextField(30);
        txt_fax = new JTextField(30);
        jp_empfaenger.add(new JLabel("Name"), gbCon);
        gbCon.gridx++;
        jp_empfaenger.add(txt_name, gbCon);
        gbCon.gridx = 0;
        gbCon.gridy++;
        gbCon.anchor = GridBagConstraints.NORTHWEST;
        jp_empfaenger.add(new JLabel("Strasse"), gbCon);
        gbCon.gridx++;
        jp_empfaenger.add(txt_strasse, gbCon);
        gbCon.gridx = 0;
        gbCon.gridy++;
        gbCon.anchor = GridBagConstraints.NORTHWEST;
        jp_empfaenger.add(new JLabel("Plz"), gbCon);
        gbCon.gridx++;
        jp_empfaenger.add(txt_plz, gbCon);
        gbCon.gridx = 0;
        gbCon.gridy++;
        gbCon.anchor = GridBagConstraints.NORTHWEST;
        jp_empfaenger.add(new JLabel("Ort"), gbCon);
        gbCon.gridx++;
        jp_empfaenger.add(txt_ort, gbCon);
        gbCon.gridx = 0;
        gbCon.gridy++;
        gbCon.anchor = GridBagConstraints.NORTHWEST;
        jp_empfaenger.add(new JLabel("Telefon"), gbCon);
        gbCon.gridx++;
        jp_empfaenger.add(txt_telefon, gbCon);
        gbCon.gridx = 0;
        gbCon.gridy++;
        gbCon.anchor = GridBagConstraints.NORTHWEST;
        jp_empfaenger.add(new JLabel("Fax"), gbCon);
        gbCon.gridx++;
        jp_empfaenger.add(txt_fax, gbCon);
        
        jp_empfaenger.setVisible(true);
        return jp_empfaenger;
    }
    
    /**
     * Erstellen JPanel für die Frachtbereich
     *
     * @return JPanel Fracht mit Bedienelementen
     * @version 1.1
     */
    private JPanel getJPFracht(){
        jp_fracht = new JPanel();
        jp_fracht.setBorder(BorderFactory.createTitledBorder("Fracht"));
        frh = getPreferredSize();
        frh.height = 330;
        frh.width = 450;
        jp_empfaenger.setPreferredSize(frh);
        jp_fracht.setLayout(new GridBagLayout());
        GridBagConstraints gbCon = new GridBagConstraints();
        gbCon.gridx = 0;
        gbCon.gridy = 0;
        gbCon.anchor = GridBagConstraints.WEST;
        gbCon.ipady = 7;
        gbCon.gridwidth = 1;
        gbCon.insets = new Insets(5, 5, 5, 5);
        txt_anzahl = new JTextField(30);
        txt_rabattzeichen = new JTextField(30);
        txt_gewicht = new JTextField(30);
        txt_warenwert = new JTextField(30);
        txt_volume = new JTextField(30);
        txt_versicherung = new JTextField(30);
        txt_lagerorte = new JTextField(30);
        jp_fracht.add(new JLabel("Anzahl"), gbCon);
        gbCon.gridx++;
        jp_fracht.add(txt_anzahl, gbCon);
        gbCon.gridx = 0;
        gbCon.gridy++;
        gbCon.anchor = GridBagConstraints.NORTHWEST;
        jp_fracht.add(new JLabel("Rabbatzeichen"), gbCon);
        gbCon.gridx++;
        jp_fracht.add(txt_rabattzeichen, gbCon);
        gbCon.gridx = 0;
        gbCon.gridy++;
        gbCon.anchor = GridBagConstraints.NORTHWEST;
        jp_fracht.add(new JLabel("Gewicht (kg)"), gbCon);
        gbCon.gridx++;
        jp_fracht.add(txt_gewicht, gbCon);
        gbCon.gridx = 0;
        gbCon.gridy++;
        gbCon.anchor = GridBagConstraints.NORTHWEST;
        jp_fracht.add(new JLabel("Warenwert"), gbCon);
        gbCon.gridx++;
        jp_fracht.add(txt_warenwert, gbCon);
        gbCon.gridx = 0;
        gbCon.gridy++;
        gbCon.anchor = GridBagConstraints.NORTHWEST;
        jp_fracht.add(new JLabel("Volume (m³)"), gbCon);
        gbCon.gridx++;
        jp_fracht.add(txt_volume, gbCon);
        gbCon.gridx = 0;
        gbCon.gridy++;
        gbCon.anchor = GridBagConstraints.NORTHWEST;
        jp_fracht.add(new JLabel("Versicherung"), gbCon);
        gbCon.gridx++;
        jp_fracht.add(txt_versicherung, gbCon);
        gbCon.gridx = 0;
        gbCon.gridy++;
        gbCon.anchor = GridBagConstraints.NORTHWEST;
        jp_fracht.add(new JLabel("Lagerort"), gbCon);
        gbCon.gridx++;
        jp_fracht.add(txt_lagerorte, gbCon);
        jp_fracht.setVisible(true);
        return jp_fracht;
    }
    
    /**
     * JPanel für den Einsatzplanung
     * 
     * @return JPanel Einsatzplanung
     * @version 1.0
     */
    private JPanel getEinsatzplanung(){
        jp_einsatzplanung = new JPanel();
        jp_einsatzplanung.setLayout(null);
        jp_einsatzplanung.setBackground(Color.pink);
        return jp_einsatzplanung;
    }
    
    /**
     * JPanel für den Fakturierung
     * 
     * @return JPanel Fakturierung
     * @version 1.0
     */
    private JPanel getFakturierung(){
        jp_fakturierung = new JPanel();
        jp_fakturierung.setLayout(new BorderLayout());
        // Einfügen des Bereichs mit den Registerkarten
        tabbedPane = new JTabbedPane();
        panel5 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        panel1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        panel4 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        tabbedPane.addTab("VERBINDLICHER TRANSPORTAUFTRAG", panel1);
        tabbedPane.addTab("RECHNUNG", panel4);
        tabbedPane.addTab("------------------", panel5);
        tabbedPane.addTab("------------------", panel2);
        tabbedPane.addTab("------------------", panel3);
                
        jp_fakturierung.add(tabbedPane,BorderLayout.CENTER);
        jp_fakturierung.setVisible(true);
        tabbedPane.setVisible(true);
        panel4.setVisible(true);
        panel5.setVisible(true);
        
        // Erste Registerkarte 
        // Erstellen ein JList (In Zukunft eine Liste aller bestellten 
        // Frachturen /Funktion der Button "Bestellen" von Fragtauftrag/)  
        
        DefaultListModel<String> model2 = new DefaultListModel<>();
        list_fr = new JList<>(model2);
        panel1.setLeftComponent(list_fr);
        list_fr.setVisible(true);
        panel1.setDividerSize(0);
        panel1.setDividerLocation(185);
        list_fr.setBorder(BorderFactory.createTitledBorder
            ("List von geplante Frachtturen"));
        // Erstellen JPanel  
        panel1.setRightComponent(panel_fr = new JPanel());
        panel_fr.setBorder(BorderFactory.createTitledBorder("Vorschau"));
        bt1 = new JButton("Rechnung schreiben");
        bt1.addActionListener(this);
        panel_fr.add(bt1);
        bt1.setVisible(true);
       
            try (BufferedReader br = Files.newBufferedReader(Paths.get("termine.csv"))) {
            // CSV Datei Delimiter
            String DELIMITER = ",";
            // Lesen die Datei Zeile für Zeile
            String line;
            while ((line = br.readLine()) != null) {
                // Zeile in Spalten konvertieren
                String[] columns = line.split(DELIMITER);
                // der entsprechende Inhalt der Modelposition ausgegeben
                model2.addElement(( String.join(", ", columns)));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        list_fr.setModel(model2);
        
        // Zweite Registerkarte
        // Erstellen ein JList (In Zukunft eine Liste aller abgeschlossenen 
        // Frachturen /Funktion der Button b1 "Rechnung schreiben" 
        // von erste Registerkarte/)
        panel4.setLeftComponent(list_rh = new JList());
        panel4.setDividerSize(0);
        panel4.setDividerLocation(220);
        list_rh.setBorder(BorderFactory.createTitledBorder
            ("Liste der Rechnungen zum Drucken"));
        // Erstellen JPanel
        panel4.setRightComponent(panel_rh = new JPanel());
        panel_rh.setBorder(BorderFactory.createTitledBorder("Vorschau"));
        bt2 = new JButton("Rechnung drucken");
        bt2.addActionListener(this);
        panel_rh.add(bt2);
        bt2.setVisible(true);
        
        return jp_fakturierung;
    }
      
    /**
     * Grundgerüst des geteilten Fensters
     * 
     * @return JSplitPane mit unterem JPanel
     * @version 1.0
     */
    private JSplitPane setAuftragsverwaltung(){
        jsp_auftragsverwaltung = 
                new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        jsp_auftragsverwaltung.setTopComponent(getMenu());
        setDivider();
        jsp_auftragsverwaltung.setDividerSize(0);
        return jsp_auftragsverwaltung;
    }
    
    /**
     * Setzen des Trenners für das JSplitPane
     * 
     * @version 1.0
     */
    private void setDivider(){
        jsp_auftragsverwaltung.setDividerLocation(80);
        
    } 
    
    /**
     * Terminliste speichern
     *
     * @version 1.1
     */
    private void terminliste_speichern() {
        FrachtsystemIO.getIOInstanz().updateTerminliste(terminliste);
    }
    
    /**
     * Prüfung, ob Einträge für Fracht vollständig sind
     *
     * @return true, wenn Einträge vollständig
     * @version 1.1
     */
    private boolean terminliste_eintrag_vollstaendig() {
        boolean pruef = true;
        if (tx_annahme.getText().trim().isEmpty()
            || tx_lieferung.getText().trim().isEmpty()
            || txt_anzahl.getText().trim().isEmpty()
            || txt_rabattzeichen.getText().trim().isEmpty()
            || txt_gewicht.getText().trim().isEmpty()
            || txt_volume.getText().trim().isEmpty()
            || txt_versicherung.getText().trim().isEmpty()
            || txt_lagerorte.getText().trim().isEmpty()){
            pruef = false;
            fehler = "Einträge unvollständig.";
        }
        return pruef;
    }
    
    
    /**
     * Eventhandling ActionListener
     *
     * @param e Auslösendes Objekt
     * @version 1.1
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // ActionEvents bei Frachtauftrag
        if(e.getSource() == bt_frachtauftrag){
            jsp_auftragsverwaltung.setBottomComponent(getFrachtauftrag());
            setDivider();
            bt_frachtauftrag.setBackground(Color.lightGray);
            bt_einsatzplanung.setBackground(null);
            bt_fakturierung.setBackground(null);
        }
        
        // ActionEvents bei Einsatzplanung
        if(e.getSource() == bt_einsatzplanung){
            jsp_auftragsverwaltung.setBottomComponent(getEinsatzplanung());
            setDivider();
            bt_einsatzplanung.setBackground(Color.LIGHT_GRAY);
            bt_frachtauftrag.setBackground(null);
            bt_fakturierung.setBackground(null);;
        }
        
        // ActionEvents bei Facturierung
        if(e.getSource() == bt_fakturierung){
            jsp_auftragsverwaltung.setBottomComponent(getFakturierung());
            setDivider();
            bt_fakturierung.setBackground(Color.LIGHT_GRAY);
            bt_frachtauftrag.setBackground(null);
            bt_einsatzplanung.setBackground(null);
        }
        
        if(e.getSource() == zum_fakturierung){
            System.out.println("Fügen aktuelle FrachtList und terminliste ein");
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
            
            if (pruef) {
                terminliste.add(new Termin_Fracht(tx_annahme.getText().trim(),
                        tx_lieferung.getText().trim(),
                        Double.valueOf(txt_anzahl.getText().trim()), 
                        txt_rabattzeichen.getText().trim(), 
                        Double.valueOf(txt_gewicht.getText().trim()), 
                        Double.valueOf(txt_warenwert.getText().trim()), 
                        Double.valueOf(txt_volume.getText().trim()), 
                        txt_versicherung.getText().trim(),
                        txt_lagerorte.getText().trim()));
                tf_laenge = terminliste.size();
                index = tf_laenge - 1;
                terminliste_speichern();
            }
            
            if (pruef) {
               
            }
        }
        
        if(e.getSource() == bt1){
            System.out.println("Fügen RechnungsList ein");
        } 
    }       
}
