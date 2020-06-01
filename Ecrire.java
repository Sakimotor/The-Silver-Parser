import java.io.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Ecrire {
    String fichier_traduit;
    String voie;
    JFrame fenetre;
    public Ecrire(String s, String v, JFrame f) {
        this.fichier_traduit = s;
        this.voie = v;
        this.fenetre = f;
    }
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
            System.out.println("erreur");
            JOptionPane.showMessageDialog( this.fenetre,
                "The file has not been saved", "Error !", JOptionPane.ERROR_MESSAGE);  
        }
    }
    
}