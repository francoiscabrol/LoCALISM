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

package com.cabrol.francois.localism.calibration.screen;

import com.cabrol.francois.localism.Debug;
import com.leapmotion.leap.Pointable;
import com.leapmotion.leap.Vector;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: francois
 * Date: 2013-11-19
 * Time: 15:40
 * To change this template use File | Settings | File Templates.
 */
public class AppScreenPlan {

    Vector p1;
    Vector p2;
    Vector p3;
    Point appWindowLocationOnScreen;
    private int appWidth;
    private int appHeight;
    private Vector normal;


    public AppScreenPlan() {
    }

    public Vector getP1() {
        return p1;
    }

    public Vector getP2() {
        return p2;
    }

    public Vector getP3() {
        return p3;
    }

    public void setP1(Vector p1) {
        this.p1 = p1;
    }

    public void setP1(float x, float y, float z) {
        this.p1 = new Vector(x, y, z);
    }

    public void setP2(Vector p2) {
        this.p2 = p2;
    }

    public void setP2(float x, float y, float z) {
        this.p2 = new Vector(x, y, z);
    }

    public void setP3(Vector p3) {
        this.p3 = p3;
    }

    public void setP3(float x, float y, float z) {
        this.p3 = new Vector(x, y, z);
    }

    public Point getAppWindowLocationOnScreen() {
        return appWindowLocationOnScreen;
    }

    public void setAppWindowLocationOnScreen(Point appWindowLocationOnScreen) {
        this.appWindowLocationOnScreen = appWindowLocationOnScreen;
    }

    public int getAppWidth() {
        return appWidth;
    }

    public int getAppHeight() {
        return appHeight;
    }

    public void setAppWidth(int appWidth) {
        this.appWidth = appWidth;
    }

    public void setAppHeight(int appHeight) {
        this.appHeight = appHeight;
    }

    public float getRatioAppScreenWidth() {
        return getAppWidth() / (Math.abs(getP3().getX() - getP2().getX()));
    }

    public float getRatioAppScreenHeight() {
        return getAppHeight() / (Math.abs(getP2().getY() - getP1().getY()));
    }

    public int leapCoordToAppCoordX(float XCoord) {
        //TODO: Verify that it is the good calcul
        return (int) ((XCoord-getP2().getX())*getRatioAppScreenWidth());
    }

    public int leapCoordToAppCoordY(float YCoord) {
        //TODO: Verify that it is the good calcul
        return (int) (-(YCoord-getP2().getY())*getRatioAppScreenHeight());
    }

    public int leapCoordToScreenCoordX(float XCoord) {
        //TODO: Verify that it is the good calcul
        int x = (int) (((XCoord-getP2().getX())*getRatioAppScreenWidth())+getAppWindowLocationOnScreen().getX());
        return x;
    }
    public int leapCoordToScreenCoordY(float YCoord) {
        //TODO: Verify that it is the good calcul
        return (int) ((-(YCoord-getP2().getY())*getRatioAppScreenHeight())+getAppWindowLocationOnScreen().getY());
    }

    public Vector getNormalVector() {
        if (!this.isDefined())
            return null;
        if (normal == null)
            normal = calculateNormalVector();
        return normal;
    }

    public FingerRelativeToScreen getFingerRelativeToScreen(Pointable pointable) {
        if(!this.isDefined())
            return null;
        return new FingerRelativeToScreen(pointable, this);
    }

    public Vector calculateNormalVector(){
        float xU = p2.getX() - p1.getX();
        float yU = p2.getY() - p1.getY();
        float zU = p2.getZ() - p1.getZ();

        float xV = p3.getX() - p1.getX();
        float yV = p3.getY() - p1.getY();
        float zV = p3.getZ() - p1.getZ();

        float xN = yU * zV - zU * yV;
        float yN = zU * xV - xU * zV;
        float zN = xU * yV - yU * xV;

        return new Vector(xN, yN, zN);
    }

    public boolean isDefined() {
        if(p1!=null && p2 !=null && p3 !=null)
            return true;
        else
            return false;
    }

    @Override
    public String toString() {
        return "AppScreenPlan{\n" +
                "p1=" + p1 +
                "\np2=" + p2 +
                "\np3=" + p3 +
                "\n"+'}';
    }

    public void reset() {
        p1 = p2 = p3 = null;
    }
}
