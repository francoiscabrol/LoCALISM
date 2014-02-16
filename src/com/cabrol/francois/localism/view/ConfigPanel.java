package com.cabrol.francois.localism.view;

import com.cabrol.francois.localism.mouse.MouseReplacement;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: francois * Date: 2014-01-23
 */
public class ConfigPanel extends JPanel implements ActionListener {


    private JButton calibrate;
    private JToggleButton mouseOn;

    private CalibrationFrame calibrationFrame;

    public ConfigPanel() {
        createUIComponents();
        addUIComponents();
        addActionListener();
    }

    private void createUIComponents() {
        calibrate = new JButton("Calibrate the screen");
        mouseOn = new JToggleButton("Mouse On Off");
    }

    private void addUIComponents(){
        add(calibrate);
        add(mouseOn);
    }

    private void addActionListener(){
        calibrate.addActionListener(this);
        mouseOn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == calibrate){
            calibrationFrame = new CalibrationFrame();
        }
        else if(e.getSource() == mouseOn){
            if(mouseOn.isSelected())
                MouseReplacement.getInstance().setMouseOn(true);
            else
                MouseReplacement.getInstance().setMouseOn(false);
        }
    }


}
