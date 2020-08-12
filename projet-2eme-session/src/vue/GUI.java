package vue;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import model.Competition;
import model.Database;
import model.Membre;
import model.Presence;

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
	SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	public JLabel date = new JLabel();
	public Date today = new Date();
	public JButton dateBefore = new JButton("<--");
	public JButton dateNext = new JButton("-->");
	public JButton sendPresences = new JButton("Valider");
	public JTable dataMembre = new JTable();
	public JScrollPane scrollPanePresences = new JScrollPane();

	//Absence
	public JFrame frame = new JFrame("Absence");
	public JTextField motif = new JTextField();
	public JLabel label = new JLabel("Motif de l'absence : ");
	public JButton sendAbsence = new JButton("Valider");
	
	//competitions
	public JTable dataCompet = new JTable();
	public JScrollPane scrollPaneCompet = new JScrollPane();

	//membres
	public JLabel newMember = new JLabel();
	public JButton addMember = new JButton();
	public JScrollPane scrollPaneMembre = new JScrollPane();

	//nouveau membre
	public JFrame member = new JFrame("Nouveau membre");
	public JTable table = new JTable();
	public JLabel lastNameLabel = new JLabel("Nom :");
	public JTextField lastName = new JTextField();
	public JLabel firstNameLabel = new JLabel("Prénom :");
	public JTextField firstName = new JTextField();
	public JLabel sexeLabel = new JLabel("Sexe :");
	public JComboBox<String> sexe = new JComboBox<String>();
	public JLabel dateLabel = new JLabel("Date de naissance : ");
	public DateFormat df = new SimpleDateFormat("dd-M-yyyy");
	public JFormattedTextField txtDate = new JFormattedTextField(df);
	public JLabel groupLabel = new JLabel("Groupe : ");
	public JComboBox<String> group = new JComboBox<String>();
	public JButton confirmMember = new JButton("Ajouter");

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
		DefaultTableModel data = db.getData("nom as \"Nom\", prenom as \"Prénom\", groupe as \"Groupe\"", "membres", "");
		
		dataMembre.setModel(data);
		dataMembre.getModel().addTableModelListener(this);
		
		dataMembre.setName("Presences");
		dataMembre.setBounds(0, 25, 500, 395);
		scrollPanePresences = new JScrollPane(dataMembre);
		dataMembre.setFillsViewportHeight(true);
		scrollPanePresences.setBounds(0, 25, 500, 395);

		sendPresences.setBounds(0, 420, 500, 25);
		sendPresences.addActionListener(this);
		
		window.setVisible(false);
		changeDate();
		window.remove(club);
		window.remove(scrollPaneCompet);
		window.remove(scrollPaneMembre);
		window.remove(addMember);
		presence.setEnabled(false);
		membres.setEnabled(true);
		competition.setEnabled(true);

		window.add(scrollPanePresences);
		window.add(sendPresences);
		window.setVisible(true);
	}
	
	private void competition(Database db) {
		
		DefaultTableModel data = db.getData("*", "competition", "");	
		
		window.setVisible(false);
		
		dataCompet.setAutoCreateRowSorter(true);
		dataCompet.setModel(data);		
		dataCompet.setName("Presences");
		dataCompet.setBounds(0, 0, 500, 425);
		dataCompet.getTableHeader().setReorderingAllowed(false);
		//dataCompet.setFocusable(false);
		dataCompet.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if (me.getClickCount() == 2) {
					JTable target = (JTable)me.getSource();
					int row = target.getSelectedRow();
					int column = target.getSelectedColumn();
					Competition compet = new Competition();
					compet.editCompet(dataCompet.getValueAt(row, 0));
				}
			}
		});
		scrollPaneCompet = new JScrollPane(dataCompet);
		dataCompet.setFillsViewportHeight(true);
		scrollPaneCompet.setBounds(0, 0, 500, 425);
		window.remove(scrollPanePresences);
		window.remove(club);
		window.remove(sendPresences);
		window.remove(left);
		window.remove(scrollPaneMembre);
		window.remove(addMember);

		presence.setEnabled(true);
		membres.setEnabled(true);
		competition.setEnabled(false);
		
		window.add(scrollPaneCompet);
		window.setVisible(true);
	}

	public GUI () {
		super();	
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) 
    { 
		Database db = new Database();
		Presence p = new Presence();
		Membre m = new Membre();
		Object x = e.getSource();
		
		//Gestion des présences
		if (x == presence) {
			presence(db);
		} else if (x == dateBefore) {
			today.setDate(today.getDate() - 1);
			window.setVisible(false);
			window.remove(left);
			left.setText(format.format(today));
			window.add(left);
			window.setVisible(true);
		} else if (x == dateNext) {
			today.setDate(today.getDate() + 1);
			window.setVisible(false);
			window.remove(left);
			left.setText(format.format(today));
			window.add(left);
			window.setVisible(true);
		} else if(x == sendAbsence) {
			String mtf = motif.getText();
			if(mtf.length() > 0) {
				dataMembre.setValueAt(mtf, row, dataMembre.getColumnCount()-1);
				frame.setVisible(false);
				//row => id membre
				//date => date sélectionnée
				//motif => motif absence
				p.setAbsent(db, row+1, today, motif.getText());
				motif.setText(null);
			}
		} else if (x == sendPresences) {
			competition.setEnabled(true);
			membres.setEnabled(true);
			dateBefore.setEnabled(true);
			dateNext.setEnabled(true);
			p.takePresences();
		}
		
		//Gestion des competitions
		else if (x == competition) {
				competition(db);
		} else if(x == "") {
			
		}
		
		//Gestion de la page Membres
		else if (x == membres){
			membre("","nom, prenom", db); 
		}  else if (x == addMember) {
			newMember();
		} else if (x == confirmMember) {
			JFrame error = new JFrame("Error");
			JLabel err = new JLabel();
			if (lastName.getText().length()<1||firstName.getText().length()<1||sexe.getSelectedItem().toString().length()<1||group.getSelectedItem().toString().length()<1) {
				err.setText("Informations incomplètes");
				err.setForeground(Color.red);
				error.add(err);
				error.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				error.setLocationRelativeTo(null);
				error.getContentPane().setBackground(Color.white);
				error.setVisible(true);
			} else {
				boolean added = m.createMembre(db, lastName.getText(), firstName.getText(), sexe.getSelectedItem().toString(), txtDate.getText(), group.getSelectedItem().toString());
				System.out.println(added);
				if (added) {
					member.setVisible(false);
					membre("","nom, prenom", db);
				} else {
					err.setText("Ce membre existe déjà");
					error.add(err);
					error.setVisible(true);				
				}
			}
		}
    }

	private void newMember() {
		member.setSize(300, 150);
		member.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		member.setLocationRelativeTo(null);
		member.getContentPane().setBackground(Color.white);
		member.setLayout(null);
		
		lastNameLabel.setBounds(5, 0, 40, 25);
		lastName.setBounds(45, 0, 100, 25);
		
		firstNameLabel.setBounds(145, 0, 60, 25);
		firstName.setBounds(205, 0, 100, 25);
		
		sexeLabel.setBounds(5, 25, 40, 25);
		sexe.setBounds(45, 25, 50, 25);
		sexe.addItem("");
		sexe.addItem("M");
		sexe.addItem("F");

		dateLabel.setBounds(5, 50, 150, 25);
		txtDate.setBounds(205, 50, 100, 25);
		
		groupLabel.setBounds(145, 25, 100, 25);
		group.setBounds(205, 25, 100, 25);
		group.addItem("");
		group.addItem("Seniors");
		group.addItem("Juniors");
		group.addItem("Cadets");
		group.addItem("Minimes");
		group.addItem("Benjamins");
		group.addItem("Canetons");

		confirmMember.setBounds(5, 75, 300, 25);
		confirmMember.addActionListener(this);
		
		member.add(lastNameLabel);
		member.add(lastName);
		member.add(firstNameLabel);
		member.add(firstName);
		member.add(sexeLabel);
		member.add(sexe);
		member.add(dateLabel);
		member.add(txtDate);
		member.add(groupLabel);
		member.add(group);
		member.add(confirmMember);

		member.setVisible(true);
	}

	private void membre(String groupe, String order, Database db) {
		DefaultTableModel data = new DefaultTableModel();
		if (groupe.length() > 0) {
			data = db.getData("*", "membres", "WHERE groupe = '"+groupe+"' ORDER BY "+order+" ASC");
		} else {
			data = db.getData("*", "membres", "ORDER BY "+order+" ASC");
		}
		window.setVisible(false);
		table.setModel(data);
		table.setBounds(0, 0, 500, 425);
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
        table.setRowSorter(sorter);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
        sortKeys.add(new RowSorter.SortKey(5, SortOrder.ASCENDING));
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
		
		scrollPaneMembre = new JScrollPane(table);
		scrollPaneMembre.setBounds(0, 0, 500, 420);
		
		addMember.setText("Nouveau membre");
		addMember.setBounds(0, 420, 500, 25);
		addMember.addActionListener(this);
		window.remove(club);
		window.remove(sendPresences);
		window.remove(left);
		window.remove(scrollPanePresences);
		window.remove(scrollPaneCompet);

		presence.setEnabled(true);
		membres.setEnabled(false);
		competition.setEnabled(true);
		
		window.add(scrollPaneMembre);
		window.add(addMember);
		window.setVisible(true);
	}

	private void changeDate() {				
		dateBefore = new JButton("<--");
		dateBefore.setBounds(300, 0, 100, 25);
		dateNext = new JButton("-->");
		dateNext.setBounds(400, 0, 100, 25);

		left.setText(format.format(today));
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
		//String name = model.getColumnName(column);
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
		
		label.setBounds(0, 0, 300, 25);
		
		motif.setBounds(0, 0, 300, 125);
		
		sendAbsence.addActionListener(this);
		sendAbsence.setBounds(0, 0, 300, 25);
		
		frame.add(label, BorderLayout.NORTH);
		frame.add(motif, BorderLayout.CENTER);
		frame.add(sendAbsence, BorderLayout.SOUTH);
		
		frame.setVisible(true);
		
		competition.setEnabled(false);
		membres.setEnabled(false);
		dateBefore.setEnabled(false);
		dateNext.setEnabled(false);
	}
}
