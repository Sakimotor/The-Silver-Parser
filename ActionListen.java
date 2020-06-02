import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.*;
import java.util.regex.Pattern;
        /**
             * This class my ActionListener
             */
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
            /**
             * This method represents my ActionListener, it receives a lot of paramaters that will be used in other classes
             * @param t the JTable we read
             * @param f the JFrame we will output our error messages to
             * @param r the String array we  will use later
             * @param v a JButton we want to listen
             * @param c another JButton we want to listen
             * @param p a pattern used for character replacing later on
             * @param l used to call the method getFichierOriginal() from the method "Lire"
             * @param o tells us about the language used
             * @param g tells us about the type of message we look for
             * @param k tells us about the game version
             */
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
            /**
             * This method checks if an action has been performed by one of the buttons we are listening to
             */
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