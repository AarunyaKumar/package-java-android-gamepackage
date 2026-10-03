/*package ___;*/

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;

import com.thefunone.theneonlight.MyCanvas;

import java.util.ArrayList;

public class Sprite {
    public ImageGroup imageGroup = new ImageGroup(); // All image work will needs to be access here
    public float x, y;
    public float direction; // range 0 - 360
    float transparency = 255; // 0 is null 255 is opaque
    float scale = 100;
    boolean isVisible = true;
    ArrayList<Sprite> sprites = new ArrayList<>(0);

    public Sprite(){}

    public void deleteAllClone(){
        sprites.clear();
    }

    public Sprite createClone(){
        Sprite result = new Sprite();
        result.imageGroup = imageGroup.createClone();
        result.x = x;
        result.y = y;
        result.direction = direction;
        result.transparency = transparency;
        result.scale = scale;
        result.isVisible = isVisible;
        sprites.add(result);
        return result;
    }

    public Sprite[] getSprites(){
        return sprites.toArray(new Sprite[0]);
    }

    public Sprite getSprite(int index){
        if(index<0 || index>=sprites.size()){
            return null;
        }else{
            return sprites.get(index);
        }
    }

    public int getNumberOfClones(){
        return sprites.size();
    }

    public void deleteClone(int index){
        sprites.remove(index);
    }

    public void deleteThisClone(Sprite sprite){
        sprites.remove(sprite);
    }

    public boolean contains(Sprite sprite){
        return sprites.contains(sprite);
    }


//    public int touchingCorner(int width, int height){
//        // 100 indicates no collision
//        // -1: left side
//        // 0: up side
//        // 1: right side
//        // 2: down side
//        int x1, y1, x2, y2;
//        Body body = imageGroup.getBody();
//        if(body.isRectBody){
//            x1 = body.x;
//            y1 = body.y;
//            x2 = x1+body.width;
//            y2 = y1+body.height;
//        }else{
//            x1 = body.x-body.width;
//            y1 = body.y-body.height;
//            x2 = body.x+body.width;
//            y2 = body.y+body.height;
//        }
//
//        if(x1<0){
//            return -1;
//        }else if(y1<0){
//            return 0;
//        }else if(x2>width){
//            return 1;
//        }else if(y2>height){
//            return 2;
//        }else{
//            return 100;
//        }
//    }

    public void updateBody(){
        if(imageGroup.getBody()!=null)
            imageGroup.getBody().update(x, y, scale);
    }

    public void setX(float newX){
        x = newX;
        updateBody();
    }

    public void setY(float newY){
        y = newY;
        updateBody();
    }

    public void changeX(float step){
        x += step;
        updateBody();
    }

    public void changeY(float step){
        y += step;
        updateBody();
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
        updateBody();
    }

    public void setPosition(Point p){
        x = p.x;
        y = p.y;
        updateBody();
    }

    public void setPosition(Sprite sprite){
        x = sprite.x;
        y = sprite.y;
        updateBody();
    }

    public Point getPosition(){
        return new Point((int)x, (int)y);
    }

    public void move(float steps){
        x += Math.sin(Math.toRadians(direction))*steps;
        y -= Math.cos(Math.toRadians(direction))*steps;
        updateBody();
    }

    public void move(float steps, float direction){
        x += Math.sin(Math.toRadians(direction))*steps;
        y -= Math.cos(Math.toRadians(direction))*steps;
        updateBody();
    }

    public float getDirection(){
        setDirectionRange();
        return direction;
    }

    public void setDirection(float dir){
        direction = dir;
        setDirectionRange();
    }

    public void changeDirection(float changeDir){
        direction += changeDir;
        setDirectionRange();
    }

    private void setDirectionRange(){
        while(true) {
            if (360 < direction) {
                direction -= 360;
            } else if (direction < 0) {
                direction += 360;
            }else{
                break;
            }
        }
    }

    public float getScale(){
        return scale;
    }

    public void setScale(float newScale){
        scale = newScale* MyCanvas.sizeRatio;
        updateBody();
    }

    public void changeScale(float changeBy){
        scale += changeBy* MyCanvas.sizeRatio;
        updateBody();
    }

    public float getTransparency(){
        return transparency;
    }

    public void setTransparency(float transparency){
        if(transparency<0){
            transparency = 0;
        }else if(transparency>255){
            transparency = 255;
        }
        this.transparency = transparency;
    }

    public void changeTransparency(float changeBy){
        // Note - parameter must be negative to make it hide
        transparency += changeBy;
        if(transparency<0){
            transparency = 0;
        }else if(transparency>255){
            transparency = 255;
        }
    }

    public boolean isVisible(){
        return isVisible;
    }

    public void show(){
        isVisible = true;
    }

    public void hide(){
        isVisible = false;
    }

    public void setVisible(boolean isVisible){
        this.isVisible = isVisible;
    }

    public boolean isColliding(float x, float y){
        return imageGroup.getBody().isColliding(x, y);
    }

    public boolean isColliding(Point point){
        return imageGroup.getBody().isColliding(point);
    }

    public boolean isColliding(Body body){
        return imageGroup.getBody().isColliding(body);
    }
    public boolean isColliding(BodyPart bodyPart){
        return imageGroup.getBody().isColliding(bodyPart);
    }

    public boolean isColliding(Sprite sprite){
        return imageGroup.getBody().isColliding(sprite.imageGroup.getBody());
    }

//    public Collide getCollide(int x, int y){
//        return imageGroup.getBody().getCollide(x, y);
//    }

//    public Collide getCollide(Point point){
//        return imageGroup.getBody().getCollide(point);
//    }

//    public Collide getCollide(Body body){
//        return imageGroup.getBody().getCollide(body);
//    }

//    public Collide getCollide(Sprite sprite){
//        return imageGroup.getBody().getCollide(sprite.imageGroup.getBody());
//    }

    public float getDistance(float x, float y){
        return (float)Math.sqrt((this.x - x) * (this.x - x) + (this.y - y) * (this.y - y));
    }

    public float getDistance(Point point){
        return getDistance(point.x, point.y);
    }

    public float getDistance(Sprite sprite){
        return getDistance(sprite.x, sprite.y);
    }

    public float getDirection(float x, float y){
        return (float)SpriteArea.getDirection(x, y, this.x, this.y);
    }

    public float getDirection(Sprite sprite){
        return (float)SpriteArea.getDirection(sprite.x, sprite.y, this.x, this.y);
    }

    public float getDirection(Point point){
        return (float)SpriteArea.getDirection(point.x, point.y, this.x, this.y);
    }

    public void pointTowards(float x, float y){
        direction = (float)SpriteArea.getDirection(x, y, this.x, this.y);
    }

    public void pointTowards(Sprite sprite){
        direction = (float)SpriteArea.getDirection(sprite.x, sprite.y, this.x, this.y);
    }

    public void pointTowards(Point point){
        direction = (float)SpriteArea.getDirection(point.x, point.y, this.x, this.y);
    }


    public float getImageWidth(){
        return imageGroup.getImage().getWidth()*scale/100f;
    }

    public float getImageHeight(){
        return imageGroup.getImage().getHeight()*scale/100f;
    }

    public void paint(Paint paint, Canvas canvas, boolean showBody) {
        if (imageGroup.needBodyUpdate) {
            updateBody();
            imageGroup.needBodyUpdate = false;
        }
        update();
        float realScale = scale / 100f;

//        if (imageGroup.getBody().isRectBody)
//            imageGroup.getBody().updateRect(x - imageGroup.getCenterX() * realScale, y - imageGroup.getCenterY() * realScale, imageGroup.getCenterX() * realScale * 2, imageGroup.getCenterY() * realScale * 2);
//        else
//            imageGroup.getBody().updateCircle(x, y, imageGroup.getCenterX() * realScale);

        if (isVisible) {
            Bitmap image = imageGroup.getImage();
            Matrix matrix = new Matrix();

            matrix.postRotate(direction, imageGroup.getCenterX(), imageGroup.getCenterY());
            matrix.postScale(realScale, realScale);
            matrix.postTranslate(x - imageGroup.getCenterX() * realScale, y - imageGroup.getCenterY() * realScale);
            paint.setAlpha((int)transparency);

            canvas.drawBitmap(image, matrix, paint);

            if(showBody && imageGroup.getBody()!=null)
                imageGroup.getBody().paint(paint, canvas);
        }
        for (Sprite spr : sprites)
            spr.paint(paint, canvas, showBody);
    }

    protected void update(){
        // Use for child class
    }
}

// playSound(---) sound effects (use another class for that)
// Movement class for physics
