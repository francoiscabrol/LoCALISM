package com.cabrol.francois.localism.calibration;

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

    public Vector getP1() {
        return p1;
    }

    public Vector getP2() {
        return p2;
    }

    public Vector getP3() {
        return p3;
    }

    public Vector getNormalVector(){
        float xU = p2.getX() - p1.getX();
        float yU = p2.getY() - p1.getY();
        float zU = p2.getZ() - p1.getZ();

        float xV = p3.getX() - p1.getX();
        float yV = p3.getY() - p1.getY();
        float zV = p3.getZ() - p1.getZ();

        float xN = yU * zV - zU * yV;
        float yN = zU * xV - xU * zV;
        float zN = xU * yV - yU * xV;

        Vector normal = new Vector(xN, yN, zN);

        return normal;
    }

    public Point getAppWindowLocationOnScreen() {
        return appWindowLocationOnScreen;
    }

    public void setAppWindowLocationOnScreen(Point appWindowLocationOnScreen) {
        this.appWindowLocationOnScreen = appWindowLocationOnScreen;
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

    //    public boolean isWasTouching(Vector frontFinger) {
//        if(!this.isDefined())
//            return false;
//        float xU = p2.getX() - p1.getX();
//        float yU = p2.getY() - p1.getY();
//        float zU = p2.getZ() - p1.getZ();
//
//        float xV = p3.getX() - p1.getX();
//        float yV = p3.getY() - p1.getY();
//        float zV = p3.getZ() - p1.getZ();
//
//        float t = (yU*zV - zU*yV - p1.getX())*frontFinger.getX() + (zU*xV - xU*zV - p2.getX())*frontFinger.getY() + (xU*yV - yU*xV - p3.getX())*p3.getZ();
//
//        if(t==0)
//            return true;
//        else
//            return false;
//    }

    public int leapCoordToAppCoordX(float coord) {
        int f = (int) ((int) ((getAppWidth()/(getP3().getX() - getP2().getX()))*coord) + getAppWindowLocationOnScreen().getX());
        return f;
    }
    public int leapCoordToAppCoordY(float coord) {

        int f = (int) (((int) ((getAppHeight()/(getP2().getY() - getP1().getY()))*coord)) - getAppWindowLocationOnScreen().getY());
        System.out.println(f);
        return f;
    }

    public int leapCoordToScreenCoordX(float coord) {
        int f = (int) (((int) ((getAppWidth()/(getP3().getX() - getP2().getX()))*coord)) + getAppWindowLocationOnScreen().getX() * 2);
        return f;
    }
    public int leapCoordToScreenCoordY(float coord) {

        int f = (int) (((int) ((getAppHeight()/(getP2().getY() - getP1().getY()))*coord)));
        return f;
    }

    public FingerRelativeToScreen getFingerRelativeToScreen(Pointable pointable) {
        if(!this.isDefined())
            return null;

        return new FingerRelativeToScreen(pointable, this);
    }

    @Override
    public String toString() {
        return "com.cabrol.francois.localism.calibration.AppScreenPlan{\n" +
                "p1=" + p1 +
                "\np2=" + p2 +
                "\np3=" + p3 +
                "\n"+'}';
    }

    public boolean isDefined() {
        if(p1!=null && p2 !=null && p3 !=null)
            return true;
        else
            return false;
    }

}
