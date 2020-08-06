package Vue;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.EventListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Model.Database;

public class GUI extends JFrame implements ActionListener{

	private static final long serialVersionUID = -4131581731669920940L;
	
	public JFrame window = new JFrame("Natation");
	public JLabel club = new JLabel();

	
	//Barre de menu
	public JMenuBar bar = new JMenuBar();
	public JMenuItem presence = new JMenuItem("Présences");
	public JMenuItem competition = new JMenuItem("Compétitions");
	public JMenuItem membres = new JMenuItem("Membres");
	
	//présences :
	public JLabel date = new JLabel();
	public JButton dateBefore = new JButton("<--");
	public JButton dateNext = new JButton("-->");
	
	public JTable dataMembres = new JTable();
	public JButton sendPresences = new JButton("Valider");
	
	//competitions
	public JTable dataCompet = new JTable();
	
	//membres
	public JLabel newMember = new JLabel();
	public JButton addMember = new JButton();
	
	
	public void createInterface (Database db) {
		window.setSize(500, 500);
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.setLocationRelativeTo(null);
		window.getContentPane().setBackground(Color.white);
		window.setLayout(null);
		
		//Barre de menu
		
		createToolBar();
		
		//Présences
		
		presence(db);
		if (presence.isSelected()) {
			System.out.println("pres");
		} else {
			club.setText("Logo du club ici");
			club.setBounds(0, 25, 500, 475);
		
			window.add(club);
		}

	}
	
	//Creer la barre de menu
	private void createToolBar () {
		presence.addActionListener(this);
		//presence.setBorder(null);
		competition.addActionListener(this);
		//competition.setBorder(null);
		membres.addActionListener(this);
		//membres.setBorder(null);
		
		bar.setBounds(0, 0, 500, 25);
		bar.add(presence);
		bar.add(competition);
		bar.add(membres);
		window.add(bar);
	}
	
	private void presence (Database db) {
		
		DefaultTableModel data = db.getData("nom as \"Nom\", prenom as \"Prénom\", groupe as \"Groupe\", presence as \"Présence\", motif as \"Motif\"", "membres join presence on membres.id = presence.nageur", "");
		
		dataMembres.setModel(data);
		
	}
	
	public GUI () {
		super();	
	}
	
	public void createInterface (String[] args) {
		GUI calendar = new GUI();
		
		presence.addActionListener(calendar);
		competition.addActionListener(calendar);
		membres.addActionListener(calendar);
		changeDate();

		calendar.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
    { 
		System.out.println(presence.isSelected());
		if (presence.isSelected()) {
			System.out.println("coco");
			
		} else if (competition.isSelected()) {
			System.out.println("cece");
		} else {
			System.out.println(e.getActionCommand() + " selected."); 
		}
    }

	private void competition() {
		Database db = new Database();
		DefaultTableModel data = db.getData("*", "competition", "");		
		JTable table = new JTable();
		table.setModel(data);
	}

	@SuppressWarnings("deprecation")
	private static void changeDate() {
		Date today = new Date();
		
		JLabel left = new JLabel();
		
		JButton past = new JButton("<--");
		JButton future = new JButton("-->");
		
		left.setText(today.toLocaleString());
	} 
	
	public static void main (String[] args) {
	}
}
