package fuhrunternehmen;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/**
 * Klasse für das Erstellen von Filiale Hamburg
 * 
 * @author tssve
 * @version 1.0
 */
public class InBearbeitung extends JDialog {
    private JDialog inbearbeitung;
    public static int screenheight = 
            java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
    public static int screenwidth = 
            java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    private JLabel test;
    
    /**
     * Erstellen die Objekten von InBearbeitung
     * 
     * @version 1.0
     */
    public InBearbeitung(){
    inbearbeitung = new JDialog();
    inbearbeitung.setTitle("Filial Hamburg");
    inbearbeitung.setSize(screenwidth, screenheight);
    inbearbeitung.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    inbearbeitung.setAlwaysOnTop(true);
    inbearbeitung.add(setInBearbeitung());
    }
    
    /**
     * Sichtbarmachen der aktuellen JDialog
     * 
     * @version 1.0
     */
    public void getInBearbeitung(){
        inbearbeitung.setModal(true);
        inbearbeitung.setVisible(true);
    }

    /**
     * Grundgerüst des Fensters
     * 
     * @return JLabel 
     * @version 1.0
     */
    public JLabel setInBearbeitung() {
        test = new JLabel("Dieser Teil soll in der "
                + "nächsten Version gemacht werden",SwingConstants.CENTER);
        test.setVisible(true);
        return test;
    }
    
}
