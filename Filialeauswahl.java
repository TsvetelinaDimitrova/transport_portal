package fuhrunternehmen;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * Start der Anwendung - Hauptfenster
 * @author tssve
 * @version 1.0
 */
public class Filialeauswahl extends JFrame 
        implements ActionListener, WindowListener{
    private static Filialeauswahl f;
    
    // Elemente Hauptfenster
    private JFrame frame;
    private JPanel jp;
    private JButton bt_berlin, bt_hamburg;
    
    // Elemente Menü
    private JMenuBar jmb;
    private JMenu verwaltung, jm_hilfe;
    private JMenuItem kundenverwaltung, frachtverwaltung,
            neuerMitarbeiter, jmi_hilfe;
    private JSeparator jsep = new JSeparator();
            
    /**
     * Konstruktor zum Erstellen einer Instanz von Filialeuswahl
     * @version 1.0
     */
    private Filialeauswahl(){
        frame = new JFrame("Fuhrunternehmen");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setJMenuBar(getMenubar());
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.addWindowListener(this);
        frame.add(getFilialeauswahl());
        frame.setVisible(true);
    }
     
    /**
     * Erstellen des JPanels mit den Buttons
     * 
     * @return JPanel
     * @version 1.2
     */
    private JPanel getFilialeauswahl(){    
        jp = new JPanel();
        jp.setLayout(null);
        bt_berlin = new JButton("Filial Berlin");
        bt_berlin.setBounds(200,100,200,30);
        bt_berlin.addActionListener(this);
        bt_hamburg = new JButton("Filial Hamburg");
        bt_hamburg.setBounds(200,150,200,30);
        bt_hamburg.addActionListener(this);
        jp.add(bt_berlin);
        jp.add(bt_hamburg);
        jp.setVisible(true);
        return jp;
    }
    
    /**
     * Zusammensetzen der JMenuBar
     * 
     * @return zusammengesetzte JMenuBar
     * @version 1.1
     */
    private JMenuBar getMenubar(){
        jmb = new JMenuBar();
        jmb.setBorder(new LineBorder(Color.DARK_GRAY));
        // JMenu verwaltung
        verwaltung = new JMenu("Verwaltung");
        // Festlegen des Buchstabens für die Menüaktivierung
        // mit der ALT-Taste (Mnemonic)
        // Tastenkombination hinzufügen (Keybinding)
        verwaltung.setMnemonic('V');
        kundenverwaltung = new JMenuItem("Kunden",'K');
        kundenverwaltung.setAccelerator(KeyStroke.getKeyStroke("F2"));
        kundenverwaltung.addActionListener(this);
        frachtverwaltung = new JMenuItem("Fracht",'F');
        frachtverwaltung.setAccelerator(KeyStroke.getKeyStroke("F4"));
        frachtverwaltung.addActionListener(this);
        neuerMitarbeiter = new JMenuItem("Neuer Mitarbeiter",'N');
        neuerMitarbeiter.addActionListener(this);
        verwaltung.add(kundenverwaltung);
        verwaltung.add(frachtverwaltung);
        verwaltung.add(jsep);
        verwaltung.add(neuerMitarbeiter);
        // JMenu hilfe
        jm_hilfe = new JMenu("Hilfe");
        jm_hilfe.setMnemonic('H');
        jmi_hilfe = new JMenuItem("Hilfe",'H');
        jmi_hilfe.setAccelerator(KeyStroke.getKeyStroke("F1"));
        jmi_hilfe.addActionListener(this);
        jm_hilfe.add(jmi_hilfe);
        // JMenu-Items der JMenuBar hinzufügen
        jmb.add(verwaltung);
        jmb.add(jm_hilfe);
        return jmb;
    }
    
    /**
     * Prüfung, ob Instanz vorhanden und Rückgabe der Instanz
     * @return Instanz der Filialeauswahl
     */
    public static Filialeauswahl getInstance(){
        if(f == null){
            f = new Filialeauswahl();
        }
        return f;
    }

    /** Anzeige eine Abfragedialogfensters
     * 
     * @param nachricht Frage an den Benutzer
     * @param titel Eintrag in der Titelleiste des Dialogfensters
     * @return Wert des gedrückten Buttons (0 = ja, 1 = nein)
     * @version 1.0
     */ 
    private int showConfirmDialog(String nachricht, String titel){
        return JOptionPane.showConfirmDialog(null, nachricht, titel,
                JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
    }
    
    private void verlassen() {
        if(showConfirmDialog("Wollen Sie die Anwendung beenden",
                "Sicherheitsabfrage")==0){
            System.exit(0);
        }
    }
    
    /**
     * Eventhandlung ActionListener
     *
     * @param e Auslösendes Objekt
     * @version 1.0
     */  
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == bt_berlin){
            Auftragsverwaltung v = new Auftragsverwaltung();
            v.getAuftragsverwaltung();          
        }
        if(e.getSource() == bt_hamburg){
            InBearbeitung b = new InBearbeitung();
            b.getInBearbeitung(); 
        }
        if(e.getSource() == kundenverwaltung){
            new Verwaltung(Verwaltung.KUNDENVERWALTUNG);
        }
        if (e.getSource() == frachtverwaltung){
            new Verwaltung(Verwaltung.FRACHTVERWALTUNG);
        }
        if (e.getSource() == neuerMitarbeiter){
            new Verwaltung(Verwaltung.MITARBEITERVERWALTUNG);
        }
    }
    
    /**
     * Event, wenn Fenster sich geöffnet hat
     *
     * @param e Auslösendes Fenster
     * @version 1.0
     */
    @Override
    public void windowOpened(WindowEvent e) {
        System.out.println("6");
    }

    /**
     * Event, wenn Fenster sich schließt
     *
     * @param e Auslösendes Fenster
     * @version 1.0
     */
    @Override
    public void windowClosing(WindowEvent e) {
        verlassen();
    }

    /**
     * Event, wenn Fenster sich geschlossen hat
     *
     * @param e Auslösendes Fenster
     * @version 1.0
     */
    @Override
    public void windowClosed(WindowEvent e) {
        System.out.println("1");
    }

    /**
     * Event, wenn Fenster minimiert wird
     *
     * @param e Auslösendes Fenster
     * @version 1.0
     */
    @Override
    public void windowIconified(WindowEvent e) {
        System.out.println("2");
    }
    
    /**
     * Event, wenn Fenster aus Minimierung maximiert wird
     *
     * @param e Auslösendes Fenster
     * @version 1.0
     */
    @Override
    public void windowDeiconified(WindowEvent e) {
        System.out.println("3");
    }

    /**
     * Event, wenn Fenster aktiv ist
     *
     * @param e Auslösendes Fenster
     * @version 1.0
     */
    @Override
    public void windowActivated(WindowEvent e) {
        System.out.println("4");
    }
    
    /**
     * Event, wenn Fenster nicht aktiv ist
     *
     * @param e Auslösendes Fenster
     * @version 1.0
     */
    @Override
    public void windowDeactivated(WindowEvent e) {
        System.out.println("5");
    }
}
