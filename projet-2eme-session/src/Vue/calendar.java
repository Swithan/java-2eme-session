package Vue;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Model.database;

public class calendar extends JFrame implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4131581731669920940L;

	static JButton presence ;

	static JButton competition;

	static JButton membres;
	
	static JLabel l = new JLabel("nothing selected"); 
	private JToolBar createToolBar () {
		JToolBar bar = new JToolBar ();
		
		presence = new JButton("Présences");
		bar.add(presence);
		
		competition = new JButton("Compétitions");
		bar.add(competition);
		
		membres = new JButton("Gérer les membres");
		bar.add(membres);		
		return bar;
	}
	
	private JPanel presence () {
		database db = new database();
		DefaultTableModel data = db.getData("nom as \"Nom\", prenom as \"Prénom\", groupe as \"Groupe\", presence as \"Présence\", motif as \"Motif\"", "membres join presence on membres.id = presence.nageur", "");
		
		JTable t = new JTable ();
		t.setModel(data);
		t.isCellEditable(3, 1);
		
		JButton b = new JButton();
		b.setText("Valider");
		b.setSize(10, 5);
		
		JPanel contentPane = (JPanel) getContentPane();
		contentPane.add(createToolBar(), BorderLayout.NORTH);

		contentPane.add(t);
		contentPane.add(b, BorderLayout.SOUTH);
		
		return contentPane;
	}
	
	public calendar () {
		super();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(600, 400);
		this.setLocationRelativeTo(null);
		
		presence();
		
	}
	
	public static void main(String[] args) {
		calendar calendar = new calendar();
		
		presence.addActionListener(calendar);
		competition.addActionListener(calendar);
		membres.addActionListener(calendar);

		calendar.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) 
    { 
        l.setText(e.getActionCommand() + " selected."); 
    } 
}
