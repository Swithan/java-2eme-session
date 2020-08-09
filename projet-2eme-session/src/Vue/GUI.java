package Vue;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Model.Database;

public class GUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = -4131581731669920940L;
	
	public JFrame window = new JFrame("Natation");
	public JLabel club = new JLabel();
	public JTable dataTable = new JTable();
	public Date today = new Date();
	public JLabel left = new JLabel();

	//Barre de menu
	public JMenuBar bar = new JMenuBar();
	public JMenuItem presence = new JMenuItem("Présences");
	public JMenuItem competition = new JMenuItem("Compétitions");
	public JMenuItem membres = new JMenuItem("Membres");
	
	//présences :
	public JLabel date = new JLabel();
	public JButton dateBefore = new JButton("<--");
	public JButton dateNext = new JButton("-->");
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
		
		createMenuBar();
		
		//Page d'accueil
		club.setText("Logo du club ici");
		club.setBounds(0, 25, 500, 475);
		window.add(club);
	}
	
	//Creer la barre de menu
	private void createMenuBar () {
		presence.addActionListener(this);
		competition.addActionListener(this);
		membres.addActionListener(this);
		
		bar.setBounds(0, 0, 500, 25);
		bar.add(presence);
		bar.add(competition);
		bar.add(membres);
		window.setJMenuBar(bar);
		
	}
	
	private void presence (Database db) {
		
		DefaultTableModel data = db.getData("nom as \"Nom\", prenom as \"Prénom\", groupe as \"Groupe\", present as \"Présence\", motif as \"Motif\"", "membres join presence on membres.id = presence.nageur", "");
				
		dataTable.setModel(data);
		dataTable.setName("Presences");
		dataTable.setBounds(0, 25, 500, 395);
		
		sendPresences.setBounds(0, 420, 500, 25);
		
		window.remove(club);
		window.setVisible(false);
		changeDate();
		window.add(dataTable);
		window.add(sendPresences);
		window.setVisible(true);
	}
	
	private void competition(Database db) {
		
		DefaultTableModel data = db.getData("*", "competition", "");	
		
		window.setVisible(false);
		
		//dataTable = null;
		dataTable.setModel(data);		
		//dataTable = new JTable(data, names);
		dataTable.setName("Presences");
		dataTable.setBounds(0, 25, 500, 395);
		
		window.remove(club);
		window.remove(sendPresences);
		window.remove(left);
		window.add(dataTable);
		window.setVisible(true);
	}

	public GUI () {
		super();	
	}

	public void createInterface (String[] args) {
		GUI calendar = new GUI();
		
		presence.addActionListener(calendar);
		competition.addActionListener(calendar);
		membres.addActionListener(calendar);

		calendar.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
    { 
		Database db = new Database();
		if ("Présences" == e.getActionCommand()) {
			presence(db);
		} else if ("Compétitions" == e.getActionCommand()) {
			competition(db);
		} else if ("Membres" == e.getActionCommand()){
			System.out.println(e.getActionCommand() + " selected."); 
		} else if ("<--" == e.getActionCommand()) {
			today.setDate(today.getDate() - 1);
			window.setVisible(false);
			window.remove(left);
			left.setText(today.toLocaleString());
			window.add(left);
			window.setVisible(true);
		} else if ("-->" == e.getActionCommand()) {
			today.setDate(today.getDate() + 1);
			window.setVisible(false);
			window.remove(left);
			left.setText(today.toLocaleString());
			window.add(left);
			window.setVisible(true);
		}
    }

	@SuppressWarnings("deprecation")
	private void changeDate() {				
		JButton past = new JButton("<--");
		past.setBounds(300, 0, 100, 25);
		JButton future = new JButton("-->");
		future.setBounds(400, 0, 100, 25);

		left.setText(today.toLocaleString());
		past.addActionListener(this);
		left.add(past);
		future.addActionListener(this);
		left.add(future);
		left.setBounds(0, 0, 500, 25);
		window.add(left);
	} 
	
	public static void main (String[] args) {
	}
}
