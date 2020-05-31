import java.io.*;

public class Ecrire {
    String fichier_traduit;
    String voie;
    public Ecrire(String s, String v) {
        this.fichier_traduit = s;
        this.voie = v;
    }
    public void Ecriture() {
          this.fichier_traduit = this.fichier_traduit.replaceAll("<html>", "");
          this.fichier_traduit = this.fichier_traduit.replaceAll("</html>", "");
          this.fichier_traduit = this.fichier_traduit.replaceAll("<br>", "");
        try {
        BufferedWriter ecriture = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.voie + ".txt"), "UTF-8"));    
        ecriture.append(this.fichier_traduit);
        ecriture.close();
        }
        catch (Exception e) {
            System.out.println("erreur");
        }
    }
    
}