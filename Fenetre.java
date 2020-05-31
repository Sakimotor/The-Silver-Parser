import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.*;

public class Fenetre extends JFrame implements ActionListener, TableModelListener  {

    /**
     *
     */
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
    public Fenetre() {
        this.fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.fenetre.setTitle("The Silver Parser");
        this.fenetre.setMinimumSize(new Dimension(640, 480));
        this.model = new DefaultTableModel();
        this.table = new JTable(model);
        this.table.setFont(new Font("Lucida Sans", Font.BOLD, 15));
        this.table.setSelectionBackground(new Color(190, 190, 190));
        this.table.putClientProperty("terminateEditOnFocusLost", true);
        model.addColumn("Texte A modifier");
        JTextField textField = new JTextField();
        textField.setFont(new Font("Verdana", 1, 11));
        DefaultCellEditor dce = new DefaultCellEditor(textField);
        table.getColumnModel().getColumn(0).setCellEditor(dce);
        this.fenetre.add(table, BorderLayout.CENTER);
        defile = new JScrollPane(table);
        this.valider = new JButton("Valider les modifications");
        this.charger = new JButton("Charger un autre fichier");
        this.fenetre.add(defile);
        this.flowlayout = new FlowLayout(FlowLayout.CENTER);
        this.panneau = new JPanel();
        this.panneau.setLayout(flowlayout);
        this.panneau.add(this.valider);
        this.panneau.add(this.charger);
        this.fenetre.add(this.panneau, BorderLayout.SOUTH);
        this.fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.fenetre.setVisible(true);
    }
    public void Affichage() {
        chargerFichier();
        Ecoute();
    }

    public void tableChanged(TableModelEvent e) {
          //J'ARRIVE A RIEN PUTAIN ALED
        }


        public void actionPerformed( ActionEvent evt ) {
            int row = table.getSelectedRow();
            int column = table.getSelectedColumn();
    
            if ( evt.getSource() == this.valider ) {
               try { 
                   this.texte[row] = String.valueOf( table.getValueAt(row,column) );

                   JFileChooser chooser = new JFileChooser();
                   FileNameExtensionFilter filter = new FileNameExtensionFilter(
                       "Fichiers texte", "txt");
                   chooser.setFileFilter(filter);
                   int returnVal = chooser.showSaveDialog(null);
                   if(returnVal == JFileChooser.APPROVE_OPTION) {
               
                       try{
                        Remplacement remplacer = new Remplacement(this.texte, this.pattern, lire.getFichierOriginal(), chooser.getSelectedFile().getPath(), this.langue, this.type);
                        remplacer.Remplacer();
                       }
                       catch (Exception e) {
                           e.printStackTrace();
                       }
                   }
 }
               catch (Exception e) {
                JOptionPane.showMessageDialog( this,
                "Veuillez modifier des lignes avant de sauvegarder le fichier", "Attention !", JOptionPane.WARNING_MESSAGE);  
               }
            
            }
            else if (evt.getSource() == this.charger) {
               this.fenetre.dispose();
               Fenetre afficher = new Fenetre();
               afficher.Affichage();
            }		
        }
    

    public void chargerFichier() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
			"Fichiers texte", "txt");
		chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
		while (returnVal != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(fenetre,"Veuillez charger un fichier.","Charger fichier",JOptionPane.ERROR_MESSAGE);
			returnVal = chooser.showOpenDialog(null);
        }

        String[] selecttype = {"Messages","Lieux"};
        this.type = JOptionPane.showOptionDialog(fenetre,"Quel type de texte voulez-vous traduire ?",
                "Type de message",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,
                selecttype,null);
   
                String[] select = {"Anglais","Japonais"};
        this.langue = JOptionPane.showOptionDialog(fenetre,"Quelle langue voulez-vous traduire ?",
                "Choix de Langue",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,
                select,null);
        if (this.type == 0)  {
           if (this.langue == 0) this.pattern = Pattern.compile("messageEN\":\".+?(?=\")*\",\"seList\"");
           else this.pattern = Pattern.compile("messageJP\":\".+?(?=\")*\",");
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
            model.addRow(new Object[]{this.texte[i]});
            this.table.setRowHeight(150);
        }        
    }
    
    public void Ecoute() {
        this.valider.addActionListener(this);
        this.charger.addActionListener(this);
        this.model.addTableModelListener(this);
    }
    public String[] getText() {
        return this.texte;
    }
}