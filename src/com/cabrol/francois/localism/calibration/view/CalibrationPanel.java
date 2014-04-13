/*
 * Copyright (c) 2014 François Cabrol.
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

package com.cabrol.francois.localism.calibration.view;

import com.cabrol.francois.localism.calibration.AppScreenPlan;
import com.cabrol.francois.localism.calibration.FingerRelativeToScreen;
import com.cabrol.francois.localism.calibration.listener.LeapListener;
import com.cabrol.francois.localism.calibration.listener.LeapMonitor;
import com.cabrol.francois.localism.exemple.mouse.MouseReplacement;
import com.leapmotion.leap.CircleGesture;
import com.leapmotion.leap.Pointable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: francois * Date: 2014-01-23
 */
public class CalibrationPanel extends JPanel implements KeyListener, LeapListener {

    java.util.List<Integer> keysPressedCode = new ArrayList<>();

    public CalibrationPanel() {
        LeapMonitor.getInstance().addLeapListener(this);
        setFocusable(true);
        addKeyListener(this);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight());

        if(MouseReplacement.getInstance().getFingerRelativeToScreen() !=null && MouseReplacement.getInstance().getFingerRelativeToScreen().isTouching()){
            g.setColor(Color.BLUE);
        }
        else{
            g.setColor(Color.BLACK);
        }

        AppScreenPlan appScreenPosition = MouseReplacement.getInstance().getAppScreenPosition();
        FingerRelativeToScreen fingerRelativeToScreen = MouseReplacement.getInstance().getFingerRelativeToScreen();

        if(appScreenPosition.isDefined() && fingerRelativeToScreen != null){
            drawFingerRelativeToScreen(g, appScreenPosition, fingerRelativeToScreen);
        }
        else{
            drawMessage(g, appScreenPosition);
        }

        drawInformationAboutFingerAndScreen(g, appScreenPosition, fingerRelativeToScreen);
    }

    private void drawMessage(Graphics g, AppScreenPlan appScreenPosition) {
        final int l = 20;
        String message = "";
        if(appScreenPosition.getP1() == null){
            g.fillRect(0, getHeight()-l, l, l);
            message = "on the bottom left and press the key alt";
        }
        else if(appScreenPosition.getP2() == null){
            g.fillRect(0, 0, l, l);
            message = "on the top left and press the key alt";
        }
        else if(appScreenPosition.getP3() == null){
            g.fillRect(getWidth()-l, 0, l, l);
            message = "on the top right and press the key alt";
        }
        String s = "Please place your finger above the point " + message;
        g.drawString(s, getWidth()/2-200, getHeight()/2);
    }

    private void drawFingerRelativeToScreen(Graphics g, AppScreenPlan appScreenPosition, FingerRelativeToScreen fingerRelativeToScreen) {
        int xAdj = appScreenPosition.leapCoordToAppCoordX(fingerRelativeToScreen.getProjectionOfFingerWithDirection().getX()); //Math.round((x) * getWidth()/(appScreenPosition.getP2().getX() - appScreenPosition.getP3().getX()));
        int yAdj =  appScreenPosition.leapCoordToAppCoordY(fingerRelativeToScreen.getProjectionOfFingerWithDirection().getY()); // Math.round((y) + getHeight() / (appScreenPosition.getP2().getY() - appScreenPosition.getP1().getY()));
//        float zAdj = 10;
//        if(fingerRelativeToScreen != null)
//            zAdj = (fingerRelativeToScreen.getDistanceFromScreen()*5) ;
        int ray = 10;

       // int intZ = Math.round(zAdj);
        g.drawOval((xAdj - (ray/2)), (yAdj - (ray/2)), ray, ray);
    }

    private void drawInformationAboutFingerAndScreen(Graphics g, AppScreenPlan appScreenPosition, FingerRelativeToScreen fingerRelativeToScreen) {
        String p1 = "com.cabrol.francois.localism.calibration.AppScreenPlan{ p1:" + appScreenPosition.getP1() + ",";
        g.drawString(p1, getWidth()-500, 40);
        String p2 = "p2:" + appScreenPosition.getP2() + ",";
        g.drawString(p2, getWidth()-500, 60);
        String p3 = "p3:" + appScreenPosition.getP3() + " }";
        g.drawString(p3, getWidth()-500, 80);
        if(fingerRelativeToScreen!=null){
            String p = "fingerRelativeToScreen: x:" + fingerRelativeToScreen.getProjectionOfFingerWithDirection().getX() + ", y:" + fingerRelativeToScreen.getProjectionOfFingerWithDirection().getY() + ", z:" + fingerRelativeToScreen.getProjectionOfFingerWithDirection().getZ();
            g.drawString(p, getWidth()-500, 120);
        }
        if(fingerRelativeToScreen!=null){
            String p4 = "distance:" + fingerRelativeToScreen.getDistanceFromScreen() + " }";
            g.drawString(p4, getWidth()-500, 160);
        }
        if(MouseReplacement.getInstance().getFingerRelativeToScreen() != null && MouseReplacement.getInstance().getFingerRelativeToScreen().isTouching())
            g.drawString("is touching the screen", getWidth()-500, 180);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keysPressedCode.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keysPressedCode.remove((Integer)e.getKeyCode());
    }


    @Override
    public void circleGestureListener(CircleGesture circle) {

    }

    @Override
    public void frontMostPointableListener(Pointable pointable) {
        if(keysPressedCode.size() == 1){
            AppScreenPlan appScreenPosition = MouseReplacement.getInstance().getAppScreenPosition();
            if(keysPressedCode.get(0) == 18){    //ALT
                if(appScreenPosition.getP1() == null)
                    appScreenPosition.setP1(pointable.tipPosition().getX(), pointable.tipPosition().getY(), pointable.tipPosition().getZ());
                else if(appScreenPosition.getP2() == null)
                    appScreenPosition.setP2(pointable.tipPosition().getX(), pointable.tipPosition().getY(), pointable.tipPosition().getZ());
                else if(appScreenPosition.getP3() == null)
                    appScreenPosition.setP3(pointable.tipPosition().getX(), pointable.tipPosition().getY(), pointable.tipPosition().getZ());
                keysPressedCode.remove((Integer) 18);
            }
            System.out.println(appScreenPosition.toString());
            MouseReplacement.getInstance().getAppScreenPosition().setAppHeight(getHeight());
            MouseReplacement.getInstance().getAppScreenPosition().setAppWidth(getWidth());
            MouseReplacement.getInstance().getAppScreenPosition().setAppWindowLocationOnScreen(getLocationOnScreen());
        }
        repaint();
    }
}
