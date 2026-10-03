/*package ___;*/

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class Rectangle {
    // These x y position are respect to the center of image
    float x, y;
    float width, height;

    float tempX, tempY;
    float tempW, tempH;

    Rectangle(float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        tempX = x;
        tempY = y;
        tempW = width;
        tempH = height;
    }

    private Rectangle(float x, float y, float width, float height, float tempX, float tempY, float tempW, float tempH){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.tempX = tempX;
        this.tempY = tempY;
        this.tempW = tempW;
        this.tempH = tempH;
    }

    public Rectangle createClone(){
        return new Rectangle(x, y, width, height, tempX, tempY, tempW, tempH);
    }

    public float getX(){
        return tempX;
    }

    public float getY(){
        return tempY;
    }

    public float getWidth(){
        return tempW;
    }

    public float getHeight(){
        return tempH;
    }

    public float getXW(){
        return getX()+getWidth();
    }

    public float getYH(){
        return getY()+getHeight();
    }

    public boolean isColliding(Rectangle rectangle){
        return !(getX()>rectangle.getXW() ||
                        getXW()<rectangle.getX() ||
                        getY()>rectangle.getYH() ||
                        getYH()<rectangle.getY());
    }
    public boolean isColliding(Circle circle){
//        return !(getX()>circle.getXW() ||
//                getXW()<circle.getWX() ||
//                getY()>circle.getYH() ||
//                getYH()<circle.getHY());

        return circle.isColliding(getX(), getY()) ||
                circle.isColliding(getXW(), getY()) ||
                circle.isColliding(getX(), getYH()) ||
                circle.isColliding(getXW(), getYH()) ||
                isColliding(circle.getWX(), circle.getY()) ||
                isColliding(circle.getX(), circle.getHY()) ||
                isColliding(circle.getXW(), circle.getY()) ||
                isColliding(circle.getX(), circle.getYH());
    }
    public boolean isColliding(Point point){
        return !(getX()>point.x ||
                getXW()<point.x ||
                getY()>point.y ||
                getYH()<point.y);
    }

    public boolean isColliding(float px, float py){
        return !(getX()>px ||
                getXW()<px ||
                getY()>py ||
                getYH()<py);
    }

    public void paint(Paint paint, Canvas canvas){
        canvas.drawRect(getX(), getY(), getXW(), getYH(), paint);
    }
}
