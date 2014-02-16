package com.cabrol.francois.localism.view;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: francois * Date: 2014-01-23
 */
public class ConfigFrame extends JFrame {

    public ConfigFrame() throws HeadlessException {
        super("Leap screen calibration test" );
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container pane = getContentPane();
        //pane.setLayout( new BoxLayout( pane, BoxLayout.X_AXIS ) );

        pane.add(new ConfigPanel());

        pack();
        setVisible(true);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);
    }
}
