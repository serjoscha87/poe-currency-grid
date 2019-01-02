package main;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFrame;

public interface UIRestoreable {

    /**
     * Store location & size of UI
     */
	default void storeOptions(Frame f, Properties p) throws Exception {
        File file = new File(Main__poe_c_g.cfgFileUri);

        // only need to update extended state in properties
        //p.setProperty("extState", String.valueOf(f.getExtendedState()));

        BufferedWriter br = new BufferedWriter(new FileWriter(file));
        p.store(br, "Properties of the user frame");
    }

    /**
     * Restore location & size of UI
     */
    default void restoreOptions(Frame frame, Properties p) throws IOException {
        File file = new File(Main__poe_c_g.cfgFileUri);
        BufferedReader br = new BufferedReader(new FileReader(file));
        p.load(br);

        //int extState = Integer.parseInt(p.getProperty("extState"));

        int x = Integer.parseInt(p.getProperty(frame.getName()+"_x", "0"));
        int y = Integer.parseInt(p.getProperty(frame.getName()+"_y", "0"));
        int w = Integer.parseInt(p.getProperty(frame.getName()+"_w", "200"));
        int h = Integer.parseInt(p.getProperty(frame.getName()+"_h", "100"));

        Rectangle r = new Rectangle(x, y, w, h);
        frame.setBounds(r);
        //f.setExtendedState(extState);
    }
    
    default void restorePosition(JFrame frame, Properties props) {
    	File optionsFile = new File(Main__poe_c_g.cfgFileUri);
        if (optionsFile.exists()) {
            try {
            	this.restoreOptions(frame, props);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } else {
        	frame.setLocationByPlatform(true);
        }
    }
    
    default void attatchRestoreabel(JFrame frame, Properties props) {
    	
    	UIRestoreable inst = this;
    	
    	frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (frame.getExtendedState() != JFrame.MAXIMIZED_BOTH) {
                    Dimension d = frame.getSize();
                    int w = (int) d.getWidth();
                    int h = (int) d.getHeight();                    
                    props.setProperty(frame.getName()+"_w", "" + w);
                    props.setProperty(frame.getName()+"_h", "" + h);
                    
                    try {
                    	inst.storeOptions(frame, props);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    
                }
            }
            @Override
            public void componentMoved(ComponentEvent e) {
                if (frame.getExtendedState() != JFrame.MAXIMIZED_BOTH) {
                    Point l = frame.getLocation();
                    int x = (int) l.getX();
                    int y = (int) l.getY();
                    props.setProperty(frame.getName()+"_x", "" + x);
                    props.setProperty(frame.getName()+"_y", "" + y);
                    
                    try {
                    	inst.storeOptions(frame, props);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    
                }
            }            
        });
    }

}
