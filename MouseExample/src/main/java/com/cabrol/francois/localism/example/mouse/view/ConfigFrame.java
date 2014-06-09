/*
 * Copyright (c) 2014 Francois Cabrol.
 *
 *  This file is part of LoCALISM.
 *
 *     LoCALISM is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     LoCALISM is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with LoCALISM.  If not, see <http://www.gnu.org/licenses/>.
 */

package main.java.com.cabrol.francois.localism.example.mouse.view;

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
