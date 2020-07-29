package Vue;
import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Model.database;

public class calendar extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4131581731669920940L;

	private JToolBar createToolBar () {
		JToolBar bar = new JToolBar ();
		
		JButton presence = new JButton("Présences");
		bar.add(presence);
		
		JButton competition = new JButton("Compétitions");
		bar.add(competition);
		
		JButton membres = new JButton("Gérer les membres");
		bar.add(membres);
		
		return bar;
	}
	
	public calendar () {
		super();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(600, 400);
		this.setLocationRelativeTo(null);
		
		database db = new database();
		DefaultTableModel data = db.getData("nom as \"Nom\", prenom as \"Prénom\", groupe as \"Groupe\", presence as \"Présence\", motif as \"Motif\"", "membres join presence on membres.id = presence.nageur", "");
		
		JTable t = new JTable ();
		t.setModel(data);
				
		JPanel contentPane = (JPanel) getContentPane();
		contentPane.add(createToolBar(), BorderLayout.NORTH);

		contentPane.add(t);

	}
	
	public static void main(String[] args) {
		calendar calendar = new calendar();
		calendar.setVisible(true);
	}
}
