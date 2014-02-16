package com.cabrol.francois.localism.view;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: francois * Date: 2014-01-23
 */
public class CalibrationFrame extends JFrame {

    public CalibrationFrame() throws HeadlessException {
        super("Leap screen calibration test" );

        Container pane = getContentPane();
        //pane.setLayout( new BoxLayout( pane, BoxLayout.X_AXIS ) );

        pane.add( new CalibrationPanel() );

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        setSize((int)dimension.getWidth()/2, (int)dimension.getHeight()/2);

        setVisible( true );

        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);
    }

}
