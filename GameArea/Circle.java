package com.thefunone.theneonlight.GameArea;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class Circle {
    // These x y position are respect to the center of image
    float x, y;
    float radius;

    float tempX, tempY, tempR;


    Circle(float x, float y, float radius){
        this.x = x;
        this.y = y;
        this.radius = radius;
        tempX = x;
        tempY = y;
        tempR = radius;
    }

    private Circle(float x, float y, float radius, float tempX, float tempY, float tempR){
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.tempX = tempX;
        this.tempY = tempY;
        this.tempR = tempR;
    }

    public Circle createClone(){
        return new Circle(x, y, radius, tempX, tempY, tempR);
    }

    public float getX(){
        return tempX;
    }

    public float getY(){
        return tempY;
    }

    public float getRadius(){
        return tempR;
    }

    public float getXW(){
        return getX()+getRadius();
    }

    public float getYH(){
        return getY()+getRadius();
    }

    public float getWX(){
        return getX()-getRadius();
    }

    public float getHY(){
        return getY()-getRadius();
    }

    public boolean isColliding(Circle circle){
        return (circle.getX()-getX())*(circle.getX()-getX())+(circle.getY()-getY())*(circle.getY()-getY()) < (getRadius()+circle.getRadius())*(getRadius()+circle.getRadius());
    }
    public boolean isColliding(Rectangle rectangle){
        return rectangle.isColliding(this);
    }
    public boolean isColliding(Point point){
        return (point.x-getX())*(point.x-getX())+(point.y-getY())*(point.y-getY()) < getRadius()*getRadius();
    }
    public boolean isColliding(float px, float py){
        return (px-getX())*(px-getX())+(py-getY())*(py-getY()) < getRadius()*getRadius();
    }

    public void paint(Paint paint, Canvas canvas){
        canvas.drawCircle(getX(), getY(), getRadius(), paint);
    }
}
