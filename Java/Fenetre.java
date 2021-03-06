import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.regex.*;


/**
     * This class is used  for the JFrame.
     */
public class Fenetre extends JFrame {

    private static final long serialVersionUID = 1L;
    private JFrame fenetre = new JFrame("The Silver Parser");
    String[] texte;
    DefaultTableModel model;
    JTable table;
    JScrollPane defile;
    JButton valider;
    JButton charger;
    Pattern pattern;
    FlowLayout flowlayout;
    JPanel panneau;
    Lire lire;
    int langue;
    int type;
    int version;
    ActionListen listen;

    
    /**
     * Constructor
     */
    public Fenetre() {
        this.fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.fenetre.setTitle("The Silver Parser");
        this.fenetre.setMinimumSize(new Dimension(640, 480));
        this.model = new DefaultTableModel();
        this.table = new JTable(model);
        this.table.setSelectionBackground(new Color(190, 190, 190));
        this.table.putClientProperty("terminateEditOnFocusLost", true);
        model.addColumn("N°");
        model.addColumn("Editable Text");
        JTextField textField = new JTextField();
        textField.setFont(new Font("Verdana", 1, 11));
        this.fenetre.add(table, BorderLayout.CENTER);
        defile = new JScrollPane(table);
        this.valider = new JButton("Apply Changes");
        this.charger = new JButton("Load File");
        this.fenetre.add(defile);
        this.flowlayout = new FlowLayout(FlowLayout.CENTER);
        this.panneau = new JPanel();
        this.panneau.setLayout(flowlayout);
        this.panneau.add(this.valider);
        this.panneau.add(this.charger);
        this.fenetre.add(this.panneau, BorderLayout.SOUTH);
        this.fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.fenetre.setVisible(true);
        this.table.getColumn("N°").setMaxWidth(40);        
    }

    /**
     * Method that handles the whole JFrame by calling the methods <b> chargerFichier() </b> then <b> Ecoute() </b> 
     */
    public void Affichage() {
        chargerFichier();
        Ecoute();
    }
            /**
             * This method loads a file into the JTable
             */
    public void chargerFichier() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
			"Text files", "txt");
		chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
		while (returnVal != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(fenetre,"Select a file to load.","Loading file",JOptionPane.ERROR_MESSAGE);
			returnVal = chooser.showOpenDialog(null);
        }
        String[] selectversion= {"The Silver Case","The 25Th Ward"};
        this.version = JOptionPane.showOptionDialog(fenetre,"Which game is the text file from ?",
                "Gamz Version",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,
                selectversion,null);
        if (this.version == 0) {
        String[] selecttype = {"Messages","Places"};
        this.type = JOptionPane.showOptionDialog(fenetre,"What kind of text will be translated ?",
                "Text type",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,
                selecttype,null);
        } else this.type = 0;
                String[] select = {"English","Japanese"};
        this.langue = JOptionPane.showOptionDialog(fenetre,"Which language will you translate from ?",
                "Select Language",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,
                select,null);

        if (this.type == 0)  {
                if(this.langue == 0) this.pattern = Pattern.compile("messageEN\":\".*?(?=\")*\",\"seList\"");
                else this.pattern = Pattern.compile("messageJP\":\".*?(?=\")*\",");
                }
        else { 
                if(this.langue == 0) this.pattern = Pattern.compile("stringEN\":\".+?(?=\")*\"");
                else this.pattern = Pattern.compile("stringJP\":\".+?(?=\")*\"");
            }

        this.lire = new Lire(pattern, chooser.getSelectedFile().getPath(), this.langue, this.type);
        System.out.println(chooser.getSelectedFile().getPath());
        lire.Lecture();
        this.texte = lire.getOriginal();
        for (int i = 0; i < this.texte.length ; i++) {
            this.texte[i] = "<html>" + this.texte[i] + "</html>";
            model.addRow(new Object[]{i+1 , this.texte[i]});
            this.table.setRowHeight(150);
        }
    }
            /**
             * This method handles the Listeners
             */
    public void Ecoute() {
        this.listen = new ActionListen(this.table, this.fenetre, this.texte, this.valider,  this.charger, this.pattern, this.lire, this.langue, this.type, this.version);
        this.valider.addActionListener(this.listen);
        this.charger.addActionListener(this.listen);
    }
}