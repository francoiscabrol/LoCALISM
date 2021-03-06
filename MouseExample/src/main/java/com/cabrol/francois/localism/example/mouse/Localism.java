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

package com.cabrol.francois.localism.example.mouse;

import com.cabrol.francois.localism.calibration.listener.LeapFrontController;
import com.cabrol.francois.localism.calibration.screen.AppScreenPlan;
import com.cabrol.francois.localism.example.mouse.view.ConfigFrame;
import com.leapmotion.leap.Controller;

public class Localism {

    public static void main(String[] args) {
        AppScreenPlan appScreenPlan = new AppScreenPlan();
        MouseReplacement.getInstance().setAppScreenPosition(appScreenPlan);
        LeapFrontController.getInstance().addLeapListener(MouseReplacement.getInstance());
        Controller controller = new Controller(LeapFrontController.getInstance());

        ConfigFrame frame = new ConfigFrame();
    }

}
