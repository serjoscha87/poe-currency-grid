package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import main.Main__poe_c_g;
import main.SpringUtilities;
import main.UIRestoreable;

@SuppressWarnings("serial")

public class MainWindow extends JFrame implements UIRestoreable {
	
	public static JTextField stack_size=null;
	public static JTextField amount=null;
	
	public static JFrame gridWindow = null;
	
	public MainWindow() {
		
		this.setTitle("PoE Currency Grid");
		this.setName("main_window");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);

		this.getRootPane().setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		this.getContentPane().setLayout(new BorderLayout());
		
		JPanel inputsPanel = new JPanel(new SpringLayout());
		
		inputsPanel.add(new JLabel("Stack size:"));
		MainWindow.stack_size = new JTextField("20", 15);
		inputsPanel.add(MainWindow.stack_size);
		
		inputsPanel.add(new JLabel("Amount"));
		MainWindow.amount = new JTextField("351", 15);
		inputsPanel.add(MainWindow.amount);
		
		SpringUtilities.makeGrid(inputsPanel,
        	2, 2, //rows, cols
            1, 1, //initialX, initialY
            0, 1  //xPad, yPad
        );
		
		this.getContentPane().add(inputsPanel, BorderLayout.CENTER);
		
		/*
		 * ctrls
		 */
		JPanel ctrlsPanel = new JPanel(new FlowLayout());
		
		ctrlsPanel.add(new JButton(new AbstractAction("Show grid") {
			@Override
			public void actionPerformed(ActionEvent e) { 
				if(MainWindow.gridWindow == null) // allow only one instance of the grid window
					MainWindow.gridWindow = new GridWindow();
				else
					JOptionPane.showMessageDialog(MainWindow.gridWindow, "Please close the current grid before you create a new one.");
			}
		}));
		this.getContentPane().add(ctrlsPanel, BorderLayout.SOUTH);
		
		/*
		 * rest typical view stuff... 
		 */
		this.pack();
		
		this.attatchRestoreabel(this, Main__poe_c_g.props);
		this.restorePosition(this, Main__poe_c_g.props);
		
		this.setVisible(true);
		
	}

}
