/*package ___;*/

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.ArrayList;


public class Body{
    ArrayList<BodyPart> bodyParts = new ArrayList<>(3);
    BodyPart outerPart; // It's not compulsory to initial this, but doing this is beneficial for fast performance

    public Body createClone(){
        Body result = new Body();
        result.outerPart = (outerPart==null) ? null: outerPart.createClone();
        for(int i=0; i<bodyParts.size(); i++){
            result.bodyParts.add(bodyParts.get(i).createClone());
        }
        return result;
    }

    public void addBodyPart(BodyPart bodyPart){
        bodyParts.add(bodyPart);
    }

    public void setOuterPart(BodyPart bodyPart){
        outerPart = bodyPart;
    }

    boolean isColliding(Point point){
        if(bodyParts.size()==0)
            return outerPart.isColliding(point);
        if (outerPart!=null && !outerPart.isColliding(point))
            return false;
        for (int i = 0; i < bodyParts.size(); i++) {
            if(bodyParts.get(i).isColliding(point)){
                return true;
            }
        }
        return false;
    }


    public void update(float spriteX, float spriteY, float spriteScale){
        spriteScale /= 100f;
        if(outerPart!=null)
            outerPart.update(spriteX, spriteY, spriteScale);
        for(BodyPart bp: bodyParts){
            bp.update(spriteX, spriteY, spriteScale);
        }
    }

    boolean isColliding(float x, float y){
        if(bodyParts.size()==0)
            return outerPart.isColliding(x, y);
        if (outerPart!=null && !outerPart.isColliding(x, y))
            return false;
        for (int i = 0; i < bodyParts.size(); i++) {
            if(bodyParts.get(i).isColliding(x, y)){
                return true;
            }
        }
        return false;
    }

    boolean isColliding(Body body){
        if(bodyParts.size()==0) {
            if (body.bodyParts.size()==0 && body.outerPart!=null)
                return outerPart.isColliding(body.outerPart);
            for (int j=0; j<body.bodyParts.size(); j++){
                if(outerPart.isColliding(body.bodyParts.get(j))){
                    return true;
                }
            }
        }
        if (outerPart!=null && !outerPart.isColliding(body.outerPart))
            return false;
        for (int i = 0; i < bodyParts.size(); i++) {
            for (int j=0; j<body.bodyParts.size(); j++){
                if(bodyParts.get(i).isColliding(body.bodyParts.get(j))){
                    return true;
                }
            }
        }
        return false;
    }

    boolean isColliding(BodyPart bodyPart){
        if(bodyParts.size()==0)
            return outerPart.isColliding(bodyPart);
        if (outerPart!=null && !outerPart.isColliding(bodyPart))
            return false;
        for (int i = 0; i < bodyParts.size(); i++) {
            if (bodyParts.get(i).isColliding(bodyPart)) {
                return true;
            }
        }
        return false;
    }

    public void paint(Paint paint, Canvas canvas){
        paint.setStyle(Paint.Style.STROKE);
        if(outerPart!=null) {
            paint.setColor(Color.rgb(255, 0, 0));
            outerPart.paint(paint, canvas);
        }

        paint.setColor(Color.rgb(255, 255, 0));
        for(BodyPart bp: bodyParts)
            bp.paint(paint, canvas);
    }
}

/*
enum Collide{
 *
    UP_LEFT,
    UP_RIGHT,
    DOWN_LEFT,
    DOWN_RIGHT,
    RECT_COLLIDE, // This may shown when the rect is something like this x+width<x or y+height<y
    NO_COLLIDE,
    CIRCLE_COLLIDE;

    float direction = 0;
    float distance = 0;
}

public class Body {
    int mainX, mainY, mainWidth, mainHeight, mainRadius;

    int x, y;
    int radius;
    // Based upon gui rectangle.
    int width, height;
    boolean isRectBody;
    public Body(boolean isRectBody){
        this.isRectBody = isRectBody;
    }


    public void updateRect(float posX, float posY, float posWidth, float posHeight) {
        // x-imageGroup.getCenterX()*scale/100f, y-imageGroup.getCenterY()*scale/100f
        x = (int) posX;
        y = (int) posY;
        width = (int) posWidth;
        height = (int) posHeight;
    }

    public void updateCircle(float posX, float posY, float posRadius){
        x = (int)posX;
        y = (int)posY;
        radius = (int)posRadius;
    }

    boolean isColliding(Point point){
        if(isRectBody){
            return (x<point.x)&&(x+width>point.x)&&
                    (y<point.y)&&(y+height>point.y);
        }else{
            return (radius*radius<(x-point.x)*(x-point.x)+(y-point.y)*(y-point.y));
        }
    }

    Collide getCollide(Point point){
        Collide result = Collide.NO_COLLIDE;

        if(isRectBody){
            float x1 = x+width*0.5f;
            float y1 = y+height*0.5f;

            int horizontal = 0;
            if((x<point.x)&&(x+width>point.x)){
                if (point.x>x1)
                    horizontal = 1;
                else
                    horizontal = -1;
            }
            if((y<point.y)&&(y+height>point.y)&&horizontal!=0){
                if (point.y>y1){
                    if (horizontal==-1)
                        result = Collide.UP_LEFT;
                    else
                        result = Collide.UP_RIGHT;
                }else{
                    if (horizontal==-1)
                        result = Collide.DOWN_LEFT;
                    else
                        result = Collide.DOWN_RIGHT;
                }
            }
            result.distance = (float)Math.sqrt((x1 - point.x) * (x1 - point.x) + (y1 - point.y) * (y1 - point.y));
            result.direction = (float)getDirection(point.x, point.y, x1, y1);
        }else{
            if ((radius*radius<(x-point.x)*(x-point.x)+(y-point.y)*(y-point.y)))
                result = Collide.CIRCLE_COLLIDE;
            result.distance = (float)Math.sqrt((x - point.x) * (x - point.x) + (y - point.y) * (y - point.y));
            result.direction = (float)getDirection(point.x, point.y, x, y);
        }
        return result;
    }

    Collide getCollide(int x, int y){
        return getCollide(new Point(x, y));
    }

    boolean isColliding(int x, int y){
        return isColliding(new Point(x, y));
    }

    boolean isColliding(Body body){
        try {
            if (body.isRectBody == isRectBody) {
                if (isRectBody) {
                    // Both rect
                    return ((x + width < x) || (x + width > body.x)) &&
                            ((y + height < y) || (y + height > body.y)) &&
                            ((body.x + body.width < body.x) || (body.x + body.width > x)) &&
                            ((body.y + body.height < body.y) || (body.y + body.height > y));
                } else {
                    // Both circle
                    return ((radius + body.radius) * (radius + body.radius) > (x - body.x) * (x - body.x) + (y - body.y) * (y - body.y));
                }
            } else {
                if (isRectBody) {
                    // Mine rect
                    return ((x + width < x) || (x + width > body.x - body.width)) &&
                            ((y + height < y) || (y + height > body.y - body.height)) &&
                            ((body.x + body.width < body.x - body.width) || (body.x + body.width > x)) &&
                            ((body.y + body.height < body.y - body.height) || (body.y + body.height > y));
                } else {
                    // Mine circle
                    return ((x + width < x - width) || (x + width > body.x)) &&
                            ((y + height < y - height) || (y + height > body.y)) &&
                            ((body.x + body.width < body.x) || (body.x + body.width > x - width)) &&
                            ((body.y + body.height < body.y) || (body.y + body.height > y - height));
                }
            }
        }catch (Exception error){
            Log.e("Error in Body", "Error in Body at func isColliding(Body) : "+error);
            return false;
        }
    }

    Collide getCollide(Body body){
        try {
            Collide result = Collide.NO_COLLIDE;
            float x1 = x+width*0.5f, y1 = y+height*0.5f, x2 = body.x+body.width*0.5f, y2 = body.y+body.height*0.5f;

            if (body.isRectBody == isRectBody) {
                if (isRectBody) {
                    // Both rect

                    if(((x + width < x) || (x + width > body.x)) &&
                            ((y + height < y) || (y + height > body.y)) &&
                            ((body.x + body.width < body.x) || (body.x + body.width > x)) &&
                            ((body.y + body.height < body.y) || (body.y + body.height > y))) {
                        int horizontal = 0;
                        if (x + width > body.x && body.x+body.width>x) {
                            if (x1 > x2) {
                                horizontal = -1;
                            } else {
                                horizontal = 1;
                            }
                        }
                        if(horizontal!=0){
                            if (y + height > body.y && body.y+body.height>y) {
                                if(y1>y2){
                                    if(horizontal==-1)
                                        result = Collide.DOWN_LEFT;
                                    else
                                        result = Collide.DOWN_RIGHT;
                                }else{
                                    if(horizontal==-1)
                                        result = Collide.UP_LEFT;
                                    else
                                        result = Collide.UP_RIGHT;
                                }
                            }else{
                                result = Collide.RECT_COLLIDE;
                            }
                        }else{
                            result = Collide.RECT_COLLIDE;
                        }
                    }

                    result.distance = (float)Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
                    result.direction = (float) getDirection(x2, y2, x1, y1);
                } else {
                    // Both circle
                    float distance = (float)Math.sqrt((x - body.x) * (x - body.x) + (y - body.y) * (y - body.y));
                    if(radius+body.radius > distance)
                        result = Collide.CIRCLE_COLLIDE;

                    result.distance = distance;
                    result.direction = (float) getDirection(body.x, body.y, x, y);
                }
            } else {
                if (isRectBody) {
                    // Mine rect
                    x2 = body.x;
                    y2 = body.y;

                    if(((x+width < x) || (x+width > body.x-body.width)) &&
                            ((y+height < y) || (y+height > body.y-body.height)) &&
                            ((body.x+body.width < body.x-body.width) || (body.x+body.width > x)) &&
                            ((body.y+body.height < body.y-body.height) || (body.y+body.height > y))) {
                        int horizontal = 0;
                        if (x + width > body.x-body.width && body.x+body.width>x) {
                            if (x1 > x2) {
                                horizontal = -1;
                            } else {
                                horizontal = 1;
                            }
                        }
                        if(horizontal!=0){
                            if (y + height > body.y-body.height && body.y+body.height>y) {
                                if(y1>y2){
                                    if(horizontal==-1)
                                        result = Collide.DOWN_LEFT;
                                    else
                                        result = Collide.DOWN_RIGHT;
                                }else{
                                    if(horizontal==-1)
                                        result = Collide.UP_LEFT;
                                    else
                                        result = Collide.UP_RIGHT;
                                }
                            }else{
                                result = Collide.RECT_COLLIDE;
                            }
                        }else{
                            result = Collide.RECT_COLLIDE;
                        }
                    }
                    result.distance = (float)Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
                    result.direction = (float) getDirection(x2, y2, x1, y1);
                } else {
                    // Mine circle
                    x1 = x;
                    y1 = y;

                    if(((x + width < x-width) || (x + width > body.x)) &&
                            ((y + height < y-height) || (y + height > body.y)) &&
                            ((body.x + body.width < body.x) || (body.x + body.width > x-width)) &&
                            ((body.y + body.height < body.y) || (body.y + body.height > y-height))) {
                        int horizontal = 0;
                        if (x + width > body.x && body.x+body.width>x-width) {
                            if (x1 > x2) {
                                horizontal = -1;
                            } else {
                                horizontal = 1;
                            }
                        }
                        if(horizontal!=0){
                            if (y + height > body.y && body.y+body.height>y-height) {
                                if(y1>y2){
                                    if(horizontal==-1)
                                        result = Collide.DOWN_LEFT;
                                    else
                                        result = Collide.DOWN_RIGHT;
                                }else{
                                    if(horizontal==-1)
                                        result = Collide.UP_LEFT;
                                    else
                                        result = Collide.UP_RIGHT;
                                }
                            }else{
                                result = Collide.RECT_COLLIDE;
                            }
                        }else{
                            result = Collide.RECT_COLLIDE;
                        }
                    }
                    result.distance = (float)Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
                    result.direction = (float) getDirection(x2, y2, x1, y1);
                }
            }
            return result;
        }catch (Exception error){
            Log.e("Error in Body", "Error in Body at func isColliding(Body) : "+error);
            return null;
        }
    }

    public void paint(Paint paint, Canvas canvas){
        paint.setColor(Color.rgb(255, 0, 0));
        if(isRectBody){
            canvas.drawRect(x, y, x+width, y+height, paint);
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                canvas.drawOval(x-width, y-height, x+width, y+height, paint);
            }
        }
    }

    private static double getDirection(float x1, float y1, float x2, float y2){
        double theta = Math.atan2(y1-y2, x1-x2);
        theta += Math.PI/2.0;
        return Math.toDegrees(theta);
    }

    /*
    * ((p1.x+<p1.x-)||(p1.x+>p2.x-))&&
    * ((p1.y+<p1.y-)||(p1.y+>p2.y-))&&
    * ((p2.x+<p2.x-)||(p2.x+>p1.x-))&&
    * ((p2.y+<p2.y-)||(p2.y+>p1.y-))*
}
*/