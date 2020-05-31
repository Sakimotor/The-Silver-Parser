import java.util.regex.*;

public class Remplacement {
String[] traduction;
Pattern pattern;
Matcher matcher;
String voie;
String fichier_traduit;
int langue;
int type;
public Remplacement(String[] t, Pattern p, String s, String  v, int l, int c) {
    this.traduction = t;
    this.pattern = p;
    this.matcher = pattern.matcher(s);
    this.voie = v;
    this.langue = l;
    this.type = c;

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
            else matcher.appendReplacement(buffer,"stringJP\":\"" + this.traduction[compteur] + "\"");
            
        }
        ++compteur; 
    }
    matcher.appendTail(buffer);
    this.fichier_traduit = buffer.toString();
    Ecrire ecrire = new Ecrire(this.fichier_traduit, this.voie);
    ecrire.Ecriture();
}
}