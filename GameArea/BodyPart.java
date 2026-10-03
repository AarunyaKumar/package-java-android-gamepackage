/*package ___;*/

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class BodyPart {
    Rectangle rectangle;
    Circle circle;

    public BodyPart(int x, int y, int width, int height) {
        rectangle = new Rectangle(x, y, width, height);
    }

    public BodyPart(int x, int y, int radius){
        circle = new Circle(x, y, radius);
    }

    private BodyPart(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    private BodyPart(Circle circle){
        this.circle = circle;
    }

    public BodyPart createClone(){
        if(rectangle!=null){
            return new BodyPart(rectangle.createClone());
        }else{
            return new BodyPart(circle.createClone());
        }
    }

    public void update(float spriteX, float spriteY, float spriteScale){
        if (rectangle!=null){
            rectangle.tempX = spriteX+rectangle.x*spriteScale;
            rectangle.tempY = spriteY+rectangle.y*spriteScale;
            rectangle.tempW = rectangle.width*spriteScale;
            rectangle.tempH = rectangle.height*spriteScale;
        }else{
            circle.tempX = spriteX+circle.x*spriteScale;
            circle.tempY = spriteY+circle.y*spriteScale;
            circle.tempR = circle.radius*spriteScale;
        }
    }

    public boolean isColliding(BodyPart bodyPart){
        if(circle==null){
            if(bodyPart.circle==null){
                return rectangle.isColliding(bodyPart.rectangle);
            }else{
                return rectangle.isColliding(bodyPart.circle);
            }
        }else{
            if(bodyPart.circle==null){
                return circle.isColliding(bodyPart.rectangle);
            }else{
                return circle.isColliding(bodyPart.circle);
            }
        }
    }

    public boolean isColliding(Point point){
        if(circle==null){
            return rectangle.isColliding(point);
        }else{
            return circle.isColliding(point);
        }
    }
    public boolean isColliding(float x, float y){
        if(circle==null){
            return rectangle.isColliding(x, y);
        }else{
            return circle.isColliding(x, y);
        }
    }

    public void paint(Paint paint, Canvas canvas){
        if(circle==null){
            rectangle.paint(paint, canvas);
        }else{
            circle.paint(paint, canvas);
        }
    }
}
