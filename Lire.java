import java.io.*;
import java.util.regex.*;

public class Lire {
    String[] original;
    String fichier_original;
    Pattern pattern;
    Matcher matcher;
    String nomfichier;
    int langue;
    int type;
    public Lire(Pattern p, String f, int l, int t) {
        this.pattern = p;
        this.nomfichier = f;
        this.langue = l;
        this.type = t;
    }
    public  void Lecture() {
        StringBuilder texte = new StringBuilder();    
        try {
            FileInputStream fichier = new FileInputStream(this.nomfichier);
            BufferedReader lecture = new BufferedReader(new InputStreamReader(fichier, "UTF-8"));
            String ligne;
            while (( ligne = lecture.readLine()) != null) {
                try  {
                    texte.append(ligne);
                }
                catch(Exception e) {
                    texte.append("fichier illisible");
                    lecture.close();
                }
                }
                this.fichier_original = texte.toString();
                this.matcher = pattern.matcher(this.fichier_original);
                String regex = "";
                int compteur = 0;
                while (matcher.find()) {
                    ++compteur;
                       regex = regex + (matcher.group()) + "\n";
                    }
                    regex = regex.replaceAll("\"", "");
                    if (this.type == 0) {
                   if (this.langue == 0) {
                       regex =  regex.replaceAll("messageEN:", "");
                       regex =  regex.replaceAll(",seList", "");
                   }
                   else {
                        regex =  regex.replaceAll("messageJP:", "");
                        regex =  regex.replaceAll(",", "");
                   }
                }
                else {
                    regex =  regex.replaceAll("string..:", "");
                }
                this.original = regex.split("\n");
                for (int i = 0; i< compteur; i++ ) {
                    
                }
                lecture.close();
        }
        catch(Exception ouf) {
            texte.append("fichier inexistant");    }
    
        }

        public String[] getOriginal() {
            return this.original;
        }
        public String getFichierOriginal() {
            return this.fichier_original;
        }
}