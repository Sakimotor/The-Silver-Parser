import java.io.*;
import java.util.regex.*;
    /**
     * This class reads a text file and parses the messages from it
     */
public class Lire {
    String[] original;
    String fichier_original;
    Pattern pattern;
    Matcher matcher;
    String nomfichier;
    int langue;
    int type;
     /**
     * Constructor
     * @param p the regex pattern used to match  the sentences
     * @param f the file's location and name
     * @param l a variable used to know which language will be read
     * @param t a variable used to known which game the file comes from
     */
    public Lire(Pattern p, String f, int l, int t) {
        this.pattern = p;
        this.nomfichier = f;
        this.langue = l;
        this.type = t;
    }
    /**
     * This method handles the reading process
     */
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
    /**
     * This method is used to transmit the untouched messages
     * @return A string array containing the messages;
     */
        public String[] getOriginal() {
            return this.original;
        }
     /**
     * This method is used to transmit the untouched messages as a single string
     * @return A string containing the messages;
     */
        public String getFichierOriginal() {
            return this.fichier_original;
        }
}