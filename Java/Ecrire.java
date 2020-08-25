import java.io.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
    /**
     * This class writes the modified text into a game-compatible .txt file
     */
public class Ecrire {
    String fichier_traduit;
    String voie;
    JFrame fenetre;
    /**
     * Constructor
     * @param s a string containing the text we will put into the file
     * @param v a string containing the path the file will be saved to
     * @param f our JFrame, in which we will output a Success/Error message during the process
     */
    public Ecrire(String s, String v, JFrame f) {
        this.fichier_traduit = s;
        this.voie = v;
        this.fenetre = f;
    }
    /**
     * This method does the writing process
     */
    public void Ecriture() {
          this.fichier_traduit = this.fichier_traduit.replaceAll("<html>", "");
          this.fichier_traduit = this.fichier_traduit.replaceAll("</html>", "");
          this.fichier_traduit = this.fichier_traduit.replaceAll("<br>", "");
        try {
        BufferedWriter ecriture = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.voie + ".txt"), "UTF-8"));    
        ecriture.append(this.fichier_traduit);
        ecriture.close();
        JOptionPane.showMessageDialog( this.fenetre,
                "The file has been saved", "Success !", JOptionPane.INFORMATION_MESSAGE);  
        }
        catch (Exception e) {
            logger.log("erreur");
            JOptionPane.showMessageDialog( this.fenetre,
                "The file has not been saved", "Error !", JOptionPane.ERROR_MESSAGE);  
        }
    }
    
}