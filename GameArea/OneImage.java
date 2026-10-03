/*package ___;*/

import android.graphics.Bitmap;

class OneImage {
    Bitmap image;
    String name;
    Body body; // This might be null, use commonBody for that
    float centerX, centerY;

    OneImage(Bitmap image, String name) {
        this.image = image;
        this.name = name;
        centerX = image.getWidth() * 0.5f;
        centerY = image.getHeight() * 0.5f;
    }

    OneImage(Bitmap image, String name, Body body) {
        this(image, name);
        this.body = body;
    }

    OneImage(Bitmap image, String name, float centerX, float centerY) {
        this(image, name);
        this.centerX = centerX;
        this.centerY = centerY;
    }

    OneImage(Bitmap image, String name, Body body, float centerX, float centerY) {
        this(image, name, centerX, centerY);
        this.body = body;
    }
}
