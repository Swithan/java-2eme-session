import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Model.database;

public class calendar {
	public static void main(String[] args) {
		database db = new database();
		DefaultTableModel data = db.getData("*", "membres", "");
		System.out.println(data);
		
		JFrame f = new JFrame();
		
		
		JButton b = new JButton("Valider");
		b.setBounds(0, 0, 100, 30);
		
		JTable t = new JTable();
		t.setModel(data);
		f.add(b);
		t.setBounds(30,40,200,300);          
		JScrollPane sp=new JScrollPane(t);    
		f.add(sp);          
		f.setSize(400, 400);
		f.setVisible(true);
		
	}
}
