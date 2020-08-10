package vue;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import model.Database;

public class GUI extends JFrame implements ActionListener, TableModelListener {

	private static final long serialVersionUID = -4131581731669920940L;
	
	public JFrame window = new JFrame("Natation");
	public JLabel club = new JLabel();
	public JLabel left = new JLabel();
	public int row;

	//Barre de menu
	public JMenuBar bar = new JMenuBar();
	public JMenuItem presence = new JMenuItem("Absence");
	public JMenuItem competition = new JMenuItem("Compétitions");
	public JMenuItem membres = new JMenuItem("Membres");
	
	//présences :
	public JLabel date = new JLabel();
	public Date today = new Date();
	public JButton dateBefore = new JButton("<--");
	public JButton dateNext = new JButton("-->");
	public JButton sendPresences = new JButton("Valider");
	public JTable dataMembre = new JTable();
	public JScrollPane scrollPaneMembre = new JScrollPane();

	//Absence
	public JFrame frame = new JFrame("Absence");
	public JTextField motif = new JTextField();
	public JButton sendAbsence = new JButton("Valider");
	
	//competitions
	public JTable dataCompet = new JTable();
	public JScrollPane scrollPaneCompet = new JScrollPane();

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
		
		//DefaultTableModel data = db.getData("nom as \"Nom\", prenom as \"Prénom\", groupe as \"Groupe\", absent as \"Absent\", motif as \"Motif\"", "membres join absence on membres.id = absence.nageur", "");
		DefaultTableModel data = db.getData("nom as \"Nom\", prenom as \"Prénom\", groupe as \"Groupe\"", "membres", "");

		
		dataMembre.setModel(data);
		
		dataMembre.getModel().addTableModelListener(this);
		
		dataMembre.setName("Presences");
		dataMembre.setBounds(0, 25, 500, 395);
		scrollPaneMembre = new JScrollPane(dataMembre);
		dataMembre.setFillsViewportHeight(true);
		scrollPaneMembre.setBounds(0, 25, 500, 395);

		sendPresences.setBounds(0, 420, 500, 25);
		
		window.setVisible(false);
		changeDate();
		window.remove(club);
		window.remove(scrollPaneCompet);
		window.add(scrollPaneMembre);
		window.add(sendPresences);
		window.setVisible(true);
	}
	
	private void competition(Database db) {
		
		DefaultTableModel data = db.getData("*", "competition", "");	
		
		window.setVisible(false);
		
		dataCompet.setModel(data);		
		dataCompet.setName("Presences");
		dataCompet.setBounds(0, 0, 500, 425);
		
		scrollPaneCompet = new JScrollPane(dataCompet);
		dataCompet.setFillsViewportHeight(true);
		scrollPaneCompet.setBounds(0, 0, 500, 425);
		window.remove(scrollPaneMembre);
		window.remove(club);
		window.remove(sendPresences);
		window.remove(left);
		window.add(scrollPaneCompet);
		window.setVisible(true);
	}

	public GUI () {
		super();	
	}

	public void createInterface (String[] args) {
		GUI calendar = new GUI();
		
		presence.addActionListener(this);
		competition.addActionListener(this);
		membres.addActionListener(this);

		calendar.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
    { 
		Database db = new Database();

		Object x = e.getSource();
		if (x == presence) {
			presence(db);
		} else if (x == competition) {
			competition(db);
		} else if (x == membres){
			System.out.println(e.getActionCommand() + " selected."); 
		} else if (x == dateBefore) {
			today.setDate(today.getDate() - 1);
			window.setVisible(false);
			window.remove(left);
			left.setText(today.toLocaleString());
			window.add(left);
			window.setVisible(true);
		} else if (x == dateNext) {
			today.setDate(today.getDate() + 1);
			window.setVisible(false);
			window.remove(left);
			left.setText(today.toLocaleString());
			window.add(left);
			window.setVisible(true);
		} else if(x == sendAbsence) {
			String m = motif.getText();
			System.out.println(motif.getText());
			if(m.length() > 0) {
				dataMembre.setValueAt(m, row, dataMembre.getColumnCount()-1);
				frame.setVisible(false);
			}
		}
    }

	@SuppressWarnings("deprecation")
	private void changeDate() {				
		dateBefore = new JButton("<--");
		dateBefore.setBounds(300, 0, 100, 25);
		dateNext = new JButton("-->");
		dateNext.setBounds(400, 0, 100, 25);

		left.setText(today.toLocaleString());
		dateBefore.addActionListener(this);
		left.add(dateBefore);
		dateNext.addActionListener(this);
		left.add(dateNext);
		left.setBounds(0, 0, 500, 25);
		window.add(left);
	} 
	
	public static void main (String[] args) {
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		row = e.getFirstRow();
		int column = e.getColumn();
		TableModel model = (TableModel)e.getSource();
		String name = model.getColumnName(column);
		Object data = model.getValueAt(row, column);
		
		if (column == 3) {
			System.out.println(data);
			if ((boolean) data) {
				absence();
			}
		}
	}
	
	private void absence () {
		frame.setSize(300, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.white);
		frame.setLayout(new BorderLayout());
		
		JLabel label = new JLabel("Motif de l'absence : ");
		label.setBounds(0, 0, 300, 25);
		
		motif.setBounds(0, 0, 300, 125);
		
		
		sendAbsence.addActionListener(this);
		sendAbsence.setBounds(0, 0, 300, 25);
		
		frame.add(label, BorderLayout.NORTH);
		frame.add(motif, BorderLayout.CENTER);
		frame.add(sendAbsence, BorderLayout.SOUTH);
		
		frame.setVisible(true);
	}
}
