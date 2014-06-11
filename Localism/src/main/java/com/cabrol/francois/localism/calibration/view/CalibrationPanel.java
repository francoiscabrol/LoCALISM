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

package com.cabrol.francois.localism.calibration.view;

import com.cabrol.francois.localism.calibration.listener.LeapFrontController;
import com.cabrol.francois.localism.calibration.screen.AppScreenPlan;
import com.cabrol.francois.localism.calibration.screen.FingerRelativeToScreen;
import com.cabrol.francois.localism.calibration.listener.LeapListener;
import com.leapmotion.leap.*;
import com.leapmotion.leap.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

/**
 * The panel that allow to calibrate the screen plan with the leap motion
 * and get the visual feedback of the finger projection point on the screen
 * @author Francois Cabrol <francois.cabrol@live.fr>
 * @since 2014-01-23
 */
public class CalibrationPanel extends JPanel implements KeyListener, LeapListener {

    /** List of keyboard keys pressed */
    java.util.List<Integer> keysPressedCode = new ArrayList<Integer>();
    private AppScreenPlan appScreenPlan;
    private FingerRelativeToScreen fingerRelativeToScreen;

    /**
     * Constructor
     * add the panel as LeapFrontController listener
     * and add key listener
     */
    public CalibrationPanel(AppScreenPlan appScreenPlan) {
        this.appScreenPlan =  appScreenPlan;
        LeapFrontController.getInstance().addLeapListener(this);
        setFocusable(true);
        addKeyListener(this);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight());

        if(fingerRelativeToScreen !=null && fingerRelativeToScreen.isTouching()){
            g.setColor(Color.BLUE);
        }
        else{
            g.setColor(Color.BLACK);
        }

        if(appScreenPlan.isDefined() && fingerRelativeToScreen != null){
            drawFingerRelativeToScreen(g, appScreenPlan, fingerRelativeToScreen);
        }
        else{
            drawMessage(g, appScreenPlan);
        }

        drawInformationAboutFingerAndScreen(g, appScreenPlan, fingerRelativeToScreen);
    }

    /**
     * Draw the message that explains how to do the calibration
     * @param g Graphics where to draw
     * @param appScreenPosition Instance of the screen plan before calibration
     */
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

    /**
     * Draw the finger projection point to get visual feedback
     * @param g Graphics where to draw
     * @param appScreenPosition screen plan after calibration
     * @param fingerRelativeToScreen finger projection to draw
     */
    private void drawFingerRelativeToScreen(Graphics g, AppScreenPlan appScreenPosition, FingerRelativeToScreen fingerRelativeToScreen) {
        int xAdj = appScreenPosition.leapCoordToAppCoordX(fingerRelativeToScreen.getProjectionOfFingerWithDirection().getX());
        int yAdj =  appScreenPosition.leapCoordToAppCoordY(fingerRelativeToScreen.getProjectionOfFingerWithDirection().getY());
        int ray = 10;
        g.drawOval((xAdj - (ray/2)), (yAdj - (ray/2)), ray, ray);
    }

    /**
     * @param p Any vector
     * @return the vector toString or the string "Undefined" if p is null
     */
    private String vectToString (Vector p) {
        return ((p==null) ? "Undefined" : p.toString());
    }

    /**
     * Show finger projection coordinates
     * and show 3 points coordinates of the screen plan
     * and show the finger distance from screen
     * and show if the finger is touching the screen plan
     * @param g Graphics where to draw
     * @param appScreenPosition
     * @param fingerRelativeToScreen
     */
    private void drawInformationAboutFingerAndScreen(Graphics g, AppScreenPlan appScreenPosition, FingerRelativeToScreen fingerRelativeToScreen) {
        String p1 = "AppScreenPlan { p1:" + vectToString(appScreenPosition.getP1()) + ",";
        g.drawString(p1, getWidth()-500, 40);
        String p2 = "p2:" + vectToString(appScreenPosition.getP2()) + ",";
        g.drawString(p2, getWidth()-500, 60);
        String p3 = "p3:" + vectToString(appScreenPosition.getP3()) + " }";
        g.drawString(p3, getWidth()-500, 80);
        if(fingerRelativeToScreen!=null){
            String p = "fingerRelativeToScreen: x:" + fingerRelativeToScreen.getProjectionOfFinger().getX() + ", y:" + fingerRelativeToScreen.getProjectionOfFinger().getY() + ", z:" + fingerRelativeToScreen.getProjectionOfFinger().getZ();
            g.drawString(p, getWidth()-500, 120);
        }
        if(fingerRelativeToScreen!=null){
            String p4 = "distance:" + fingerRelativeToScreen.getDistanceFromScreen() + " }";
            g.drawString(p4, getWidth()-500, 160);
        }
        if(fingerRelativeToScreen != null && fingerRelativeToScreen.isTouching())
            g.drawString("is touching the screen", getWidth()-500, 180);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // No implementation
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
        // No implementation
    }

    /**
     * Call by the leap font controller and get the pointable object
     * @see com.cabrol.francois.localism.calibration.listener.LeapFrontController
     * @param pointable the closer finger from the screen
     */
    @Override
    public void frontMostPointableListener(Pointable pointable) {
        if(keysPressedCode.size() == 1){
            if(keysPressedCode.get(0) == 18){    //ALT
                if(appScreenPlan.getP1() == null)
                    appScreenPlan.setP1(pointable.tipPosition().getX(), pointable.tipPosition().getY(), pointable.tipPosition().getZ());
                else if(appScreenPlan.getP2() == null)
                    appScreenPlan.setP2(pointable.tipPosition().getX(), pointable.tipPosition().getY(), pointable.tipPosition().getZ());
                else if(appScreenPlan.getP3() == null)
                    appScreenPlan.setP3(pointable.tipPosition().getX(), pointable.tipPosition().getY(), pointable.tipPosition().getZ());
                else
                    appScreenPlan.reset();
                keysPressedCode.remove((Integer) 18);
            }
            appScreenPlan.setAppHeight(getHeight());
            appScreenPlan.setAppWidth(getWidth());
            appScreenPlan.setAppWindowLocationOnScreen(getLocationOnScreen());
        }
        fingerRelativeToScreen = appScreenPlan.getFingerRelativeToScreen(pointable);
        repaint();
    }
}
