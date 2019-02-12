package project;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.text.ParseException;
import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

public class MyForm extends JFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				MyForm frame = new MyForm();
				frame.setVisible(true);
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MyForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 362, 249);
		setTitle("ThaiCreate.Com Java GUI Tutorial");
		getContentPane().setLayout(null);
		
		// Format
    	MaskFormatter mask = null;
		try {
			mask = new MaskFormatter("##########");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Formatted TextField
		JFormattedTextField fmField = new JFormattedTextField(mask);
		fmField.setBounds(125, 87, 90, 20);
		getContentPane().add(fmField);

	}
}
