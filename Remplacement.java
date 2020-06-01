import java.util.regex.*;

import javax.swing.JFrame;

public class Remplacement {
String[] traduction;
Pattern pattern;
Matcher matcher;
String voie;
String fichier_traduit;
int langue;
int type;
int version;
JFrame fenetre;
public Remplacement(String[] t, Pattern p, String s, String  v, int l, int c, int d, JFrame f) {
    this.traduction = t;
    this.pattern = p;
    this.matcher = pattern.matcher(s);
    this.voie = v;
    this.langue = l;
    this.type = c;
    this.version = d;

}
public void Remplacer() {
    int compteur = 0;
    StringBuffer buffer = new StringBuffer();
    while (matcher.find()) {
        if (this.type == 0) {
       if (this.langue ==0) matcher.appendReplacement(buffer,"messageEN\":\"" + this.traduction[compteur] + "\",\"seList\"");
       else matcher.appendReplacement(buffer,"messageJP\":\"" + this.traduction[compteur] + "\",");
        }
        else {
            if (this.langue == 0) matcher.appendReplacement(buffer,"stringEN\":\"" + this.traduction[compteur] + "\"");
            else matcher.appendReplacement(buffer,"stringJP\":\"" + this.traduction[compteur] + ",\"");
            
        }
        ++compteur; 
    }
    matcher.appendTail(buffer);
    this.fichier_traduit = buffer.toString();
    this.fichier_traduit = this.fichier_traduit.replace("\\", "\\\\");
    this.fichier_traduit = this.fichier_traduit.replace("\\\\\\\\", "\\\\");
    this.fichier_traduit = this.fichier_traduit.replace("\\\\\\", "\\\\");
    this.fichier_traduit = this.fichier_traduit.replaceAll(",\"}", "\"}");
    this.fichier_traduit = this.fichier_traduit.replace("\\\\\"", "\\\"");
    if (this.version !=0)  {
        this.fichier_traduit = this.fichier_traduit.replaceAll("Dic\":\\{", "Dic\":\\{\n");
        this.fichier_traduit = this.fichier_traduit.replaceAll("},", "},\n");
    }
    else {
        this.fichier_traduit = this.fichier_traduit.replace(":true}},", ":true}},\n");
    }
    Ecrire ecrire = new Ecrire(this.fichier_traduit, this.voie, this.fenetre);
    ecrire.Ecriture();
}
}