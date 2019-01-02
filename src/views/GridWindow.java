package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import lib.ComponentResizer;

import java.awt.event.ActionEvent;
import java.awt.Dimension;

import main.Main__poe_c_g;
import main.MotionPanel;
import main.UIRestoreable;

@SuppressWarnings("serial")

public class GridWindow extends JFrame implements UIRestoreable {
    
	public GridWindow() {
		
		float stack_size = new Float(MainWindow.stack_size.getText());
		float amount = new Float(MainWindow.amount.getText());
		
		// ----
		
		this.setTitle("PoE Currency Grid");
		this.setName("grid_window");
		
		this.setUndecorated(true);
		this.setSize(200, 100); // default for a program run that has no stored window props yet
		
		ComponentResizer cr = new ComponentResizer();
        cr.setMinimumSize(new Dimension(100, 60));
        //cr.setMaximumSize(new Dimension(800, 600));
        cr.registerComponent(this);
        
        ((JPanel)this.getContentPane()).setBorder(new LineBorder(Color.BLACK, 2));
		
		this.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		this.setLocationRelativeTo(null);
		this.setAlwaysOnTop(true);
		
		this.getRootPane().setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
		
		this.setLayout(new BorderLayout());

		this.attatchRestoreabel(this, Main__poe_c_g.props);
		
		/*
		 * the grid
		 */
		GridLayout gl = new GridLayout(5, 12);
		JPanel gridPane = new JPanel(gl);
		gridPane.setBackground(new Color(1.0f,1.0f,1.0f,0.4f));
		
		float aim = amount/stack_size; // the amount of slots we need to highlight
		float aim_ceiled = (float) Math.ceil(aim);
		
		for (int i_rows = 0; i_rows < 5; i_rows++){
			for (int i_cols = 0; i_cols < 12; i_cols++) {
				float slot_num = i_rows + ( i_cols * 5 );
				float rest = amount - slot_num*stack_size;
				final JLabel label = new JLabel(
					String.valueOf( new Float( rest < stack_size ? rest : stack_size ).intValue() ) + " ",
					SwingConstants.RIGHT
				);
			    label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			    label.setFont(label.getFont().deriveFont(label.getFont().getStyle() | Font.BOLD));
			    label.setVerticalAlignment(JLabel.BOTTOM);
			    if(slot_num >= aim_ceiled) {
			    	label.setBackground(new Color(1.0f,0.0f,0.0f,0.4f));
			    	label.setOpaque(true);
			    	label.setText("x");
			    	label.setHorizontalAlignment(SwingConstants.CENTER);
			    	label.setVerticalAlignment(JLabel.CENTER);
			    }
			    gridPane.add(label);
			}
		}
		
		// add the grid panel to the main window/frame
		this.add(gridPane, BorderLayout.CENTER);
		
		/*
		 * the grid action row beneath the grid
		 */
		JPanel ctrlsPane = new MotionPanel(this);
		ctrlsPane.setLayout(new BorderLayout());
		ctrlsPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		ctrlsPane.add(new JLabel("Move the grid around by clicking+dragging here"), BorderLayout.CENTER);
		
		JButton closeGridBtn = new JButton(new AbstractAction("x") {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow.gridWindow.dispose();
				MainWindow.gridWindow = null;
			}
		});		
		ctrlsPane.add(closeGridBtn, BorderLayout.EAST);
		
		// add the action row the main window/frame
		this.add(ctrlsPane, BorderLayout.PAGE_END);
		
		/*
		 * 
		 */
		this.restorePosition(this, Main__poe_c_g.props);
		
		this.setVisible(true);
		
	}


}
