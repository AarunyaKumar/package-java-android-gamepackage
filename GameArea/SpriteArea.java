package com.thefunone.theneonlight.GameArea;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

public class SpriteArea {
    ArrayList<Sprite> sprites = new ArrayList<>(5);
    int width, height;
    Bitmap backgroundStampImage;
    public SpriteArea(int width, int height){
        this.width = width;
        this.height = height;
    }

    public void addSprite(Sprite sprite){
        sprites.add(sprite);
    }

    public boolean contains(Sprite sprite){
        return sprites.contains(sprite);
    }

    public void setToFront(Sprite sprite){
        if (sprites.contains(sprite)){
            sprites.remove(sprite);
            sprites.add(sprite);
        }
    }

    public void setToBack(Sprite sprite){
        if (sprites.contains(sprite)){
            sprites.remove(sprite);
            sprites.add(0, sprite);
        }
    }

    public void goToOneLayerFront(Sprite sprite){
        if (sprites.contains(sprite)){
            int i = sprites.indexOf(sprite);
            if(i!=sprites.size()) {
                sprites.remove(sprite);
                sprites.add(i + 1, sprite);
            }
        }
    }

    public void goToOneLayerBack(Sprite sprite){
        if (sprites.contains(sprite)){
            int i = sprites.indexOf(sprite);
            if(i!=0) {
                sprites.remove(sprite);
                sprites.add(i - 1, sprite);
            }
        }
    }

    public int getNumberOfSprites(){
        return sprites.size();
    }

    public void paint(Paint paint, Canvas canvas, boolean showBody){
        for(int i=0; i<sprites.size(); i++){
            sprites.get(i).paint(paint, canvas, showBody);
        }
    }

    public static double getDirection(float x1, float y1, float x2, float y2){
        double theta = Math.atan2(y1-y2, x1-x2);
        theta += Math.PI/2.0;
        return Math.toDegrees(theta);
    }
}

// Maybe
// bitmap image for background for pen function - NO
// mouseInput if possible - NO
