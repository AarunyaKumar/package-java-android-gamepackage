/*package ___;*/

import java.util.ArrayList;

public class SpriteMovement extends Sprite {
    private float speedX, speedY;

    boolean isGravityEnable = false;
    float gravityX, gravityY;
    float frictionX = 1, frictionY = 1;
    float frictionGiver = 1;
    float bounceForce = 0;
    ArrayList<SpriteMovement> collides = new ArrayList<>(2);
    float rotationSpeed = 0, transparencySpeed = 0;


    public void addRotationSpeed(float value){
        rotationSpeed = value;
    }
    public float getRotationSpeed(){
        return rotationSpeed;
    }
    public void addTransparencySpeed(float value){
        transparencySpeed = value;
    }
    public void addColliderSpriteMovement(SpriteMovement spriteMovement){
        collides.add(spriteMovement);
    }
    public void removeColliderSpriteMovement(SpriteMovement spriteMovement){
        collides.remove(spriteMovement);
    }

    public void setBounceForce(float value){
        bounceForce = value;
    }

    public void setFrictionGiver(float value){
        frictionGiver = value;
    }

    public void setFriction(float frictionX, float frictionY){
        this.frictionX = frictionX;
        this.frictionY = frictionY;
    }

    public void setFrictionX(float value){
        frictionX = value;
    }

    public void setFrictionY(float value){
        frictionY = value;
    }

    public void setGravity(float x, float y){
        gravityX = x;
        gravityY = y;
    }

    public void setGravityX(float x){
        gravityX = x;
    }

    public void setGravityY(float y){
        gravityY = y;
    }

    public float getGravityX(){
        return gravityX;
    }

    public float getGravityY(){
        return gravityY;
    }

    public void enableGravity(){
        isGravityEnable = true;
    }

    public void disableGravity(){
        isGravityEnable = false;
    }

    public boolean isGravityEnable(){
        return isGravityEnable;
    }

    public void addForce(float direction, float step){
        direction = (float) Math.toRadians(direction);
        speedX += (float)Math.sin(direction);
        speedY -= (float)Math.cos(direction);
    }

    public void addForce(float step){
        speedX += (float)Math.sin(Math.toRadians(direction))*step;
        speedY -= (float)Math.cos(Math.toRadians(direction))*step;
    }

    public void addForceBy(float forceX, float forceY){
        speedX += forceX;
        speedY -= forceY;
    }

    public float getSpeedX(){
        return speedX;
    }

    public float getSpeedY(){
        return speedY;
    }

    @Override
    public void update(){
        speedX -= gravityX;
        speedY += gravityY;
        speedX *= frictionX;
        speedY *= frictionY;

        float prePosition;
        float additionValue = Math.abs(speedX)/speedX;
        for(int i=0; i<Math.abs(speedX); i++){
            prePosition = x;
            x += additionValue;
            if (checkCollide()){
                x = prePosition;
                speedX = 0;
                break;
            }
        }

        additionValue = Math.abs(speedY)/speedY;
        for(int i=0; i<Math.abs(speedY); i++){
            prePosition = y;
            y += additionValue;
            if (checkCollide()){
                y = prePosition;
                speedY = 0;
                break;
            }
        }
        direction += rotationSpeed;
        changeTransparency(transparencySpeed);
        updateBody();
    }

    private boolean checkCollide(){
        for (SpriteMovement sprite : collides) {
            if (isColliding(sprite)){
                speedX *= sprite.frictionGiver;
                speedY *= sprite.frictionGiver;
                speedX -= (float) Math.sin(Math.toRadians(getDirection(sprite)))*bounceForce;
                speedY += (float) Math.cos(Math.toRadians(getDirection(sprite)))*bounceForce;
                return true;
            }
        }
        return false;
    }

    @Override
    public SpriteMovement createClone(){
        SpriteMovement result = new SpriteMovement();
        result.imageGroup = imageGroup.createClone();
        result.x = x;
        result.y = y;
        result.direction = direction;
        result.transparency = transparency;
        result.scale = scale;
        result.isVisible = isVisible;
        result.speedX = speedX;
        result.speedY = speedY;
        result.collides = collides;
        sprites.add(result);
        return result;
    }
}

// loop for collision for all collides

// addCollideListener(SpriteMovement spriteMovement)
