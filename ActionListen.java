import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.*;
import java.util.regex.Pattern;

public class ActionListen implements ActionListener {
    JTable table;
    JFrame fenetre;
    String[] texte;
    JButton valider;
    JButton charger;
    Pattern pattern;
    Lire lire;
    int langue;
    int type;
    int version;
    public ActionListen(JTable t, JFrame f, String[] r, JButton v, JButton c, Pattern p, Lire l, int o, int g, int k)  {
        this.table = t;
        this.fenetre = f;
        this.texte = r;
        this.valider = v;
        this.pattern = p;
        this.lire = l;
        this.langue = o;
        this.type = g;
        this.version = k;
        this.charger = c;
    }

    public void actionPerformed( ActionEvent evt ) {
        int row = table.getSelectedRow();
        int column = table.getSelectedColumn();

        if ( evt.getSource() == this.valider ) {
           try { 
               this.texte[row] = String.valueOf( table.getValueAt(row,column) );

               JFileChooser chooser = new JFileChooser();
               FileNameExtensionFilter filter = new FileNameExtensionFilter(
                   "Text files", "txt");
               chooser.setFileFilter(filter);
               int returnVal = chooser.showSaveDialog(null);
               if(returnVal == JFileChooser.APPROVE_OPTION) {
           
                   try{
                    Remplacement remplacer = new Remplacement(this.texte, this.pattern, lire.getFichierOriginal(),
                    chooser.getSelectedFile().getPath(), this.langue, this.type, this.version, this.fenetre);

                    remplacer.Remplacer();
                   }
                   catch (Exception e) {
                       e.printStackTrace();
                   }
               }
}
           catch (Exception e) {
            JOptionPane.showMessageDialog( this.fenetre,
            "You have to modify the table before saving", "Attention !", JOptionPane.WARNING_MESSAGE);  
           }
        
        }
        else if (evt.getSource() == this.charger) {
           this.fenetre.dispose();
           Fenetre afficher = new Fenetre();
           afficher.Affichage();
        }		
    } 
    
}