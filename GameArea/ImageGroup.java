/*package ___;*/

import android.graphics.Bitmap;
import android.graphics.Point;

import java.util.ArrayList;

// NOTE - All the changes in data will be changed in sprite's clone also
public class ImageGroup {
    private ArrayList<OneImage> images = new ArrayList<>(3);
    private Body commonBody;
    private int selectedCostume = -1;
    boolean needBodyUpdate = true;

    ImageGroup(){}
    ImageGroup(Body body){
        this.commonBody = body;
    }

    public ImageGroup createClone(){
        ImageGroup result = new ImageGroup();
        if(commonBody!=null)
            result.commonBody = commonBody.createClone();
        result.selectedCostume = selectedCostume;
        result.needBodyUpdate = needBodyUpdate;
        for(int i=0; i<images.size(); i++){
            result.addImage(images.get(i).image,
                    images.get(i).name,
                    (images.get(i).body==null) ? null: images.get(i).body.createClone(),
                    images.get(i).centerX, images.get(i).centerY);
        }
        return result;
    }

    public int getSelectedIndex(){
        return selectedCostume;
    }

    public String getSelectedImageName(){
        if(selectedCostume==-1){
            return null;
        }else{
            return images.get(selectedCostume).name;
        }
    }

    public void setCommonBody(Body commonBody){
        this.commonBody = commonBody;
    }

    public Body getBody(){
        if (selectedCostume==-1)
            return null;
        return (images.get(selectedCostume).body!=null) ? images.get(selectedCostume).body: commonBody;
    }

    public Body getCommonBody(){
        return commonBody;
    }

    public void addImage(Bitmap image, String name){
        images.add(new OneImage(image, name));
        if (selectedCostume==-1) {
            selectedCostume = 0;
            needBodyUpdate = true;
        }
    }

    public void addImage(Bitmap image, String name, Body body){
        images.add(new OneImage(image, name, body));
        if (selectedCostume==-1) {
            selectedCostume = 0;
            needBodyUpdate = true;
        }
    }

    public void addImage(Bitmap image, String name, float centerX, float centerY){
        images.add(new OneImage(image, name, centerX, centerY));
        if (selectedCostume==-1) {
            selectedCostume = 0;
            needBodyUpdate = true;
        }
    }

    public void addImage(Bitmap image, String name, Body body, float centerX, float centerY){
        images.add(new OneImage(image, name, body, centerX, centerY));
        if (selectedCostume==-1) {
            selectedCostume = 0;
            needBodyUpdate = true;
        }
    }

    public void setImage(int number){
        if(number<0){
            if(images.size()==0){
                selectedCostume = -1;
            }else{
                selectedCostume = 0;
            }
        }else if(number>=images.size()){
            selectedCostume = images.size()-1;
        }else{
            selectedCostume = number;
        }
        needBodyUpdate = true;
    }

    public void setImage(String name){
        selectedCostume = getIndexByName(name);
        needBodyUpdate = true;
    }

    public void setNextImage(){
        selectedCostume++;
        if (selectedCostume>= images.size()){
            if(images.size()==0){
                selectedCostume = -1;
            }else {
                selectedCostume = 0;
            }
        }
        needBodyUpdate = true;
    }

    public void setPreviousImage(){
        selectedCostume--;
        if (selectedCostume<0){
            selectedCostume = images.size()-1;
        }
        needBodyUpdate = true;
    }

    public Bitmap getImage(){
        if(selectedCostume==-1){
            return null;
        }else{
            return images.get(selectedCostume).image;
        }
    }

    public Bitmap getImage(int number){
        if(number<0){
            return null;
        }else if(number>=images.size()){
            return null;
        }else{
            return images.get(number).image;
        }
    }

    public Bitmap getImage(String name){
        return getOneImageByName(name).image;
    }

    private OneImage getOneImageByName(String name){
        for(int i=0; i<images.size(); i++){
            if(name.equals(images.get(i).name)){
                return images.get(i);
            }
        }
        return null;
    }

    private int getIndexByName(String name){
        for(int i=0; i<images.size(); i++){
            if(name.equals(images.get(i).name)){
                return i;
            }
        }
        return -1;
    }

    public Point getCenter(){
        if(selectedCostume==-1){
            return null;
        }else{
            return new Point((int)images.get(selectedCostume).centerX, (int)images.get(selectedCostume).centerY);
        }
    }

    public float getCenterX(){
        if(selectedCostume==-1){
            return -1;
        }else{
            return images.get(selectedCostume).centerX;
        }
    }

    public float getCenterY(){
        if(selectedCostume==-1){
            return -1;
        }else{
            return images.get(selectedCostume).centerY;
        }
    }

    public Point getCenter(int number){
        if(number<0){
            return null;
        }else if(number>= images.size()){
            return null;
        }else{
            return new Point((int)images.get(number).centerX, (int)images.get(number).centerY);
        }
    }

    public float getCenterX(int number){
        if(number<0){
            return -1;
        }else if(number>= images.size()){
            return -1;
        }else{
            return images.get(number).centerX;
        }
    }

    public float getCenterY(int number){
        if(number<0){
            return -1;
        }else if(number>= images.size()){
            return -1;
        }else{
            return images.get(number).centerY;
        }
    }

    public Point getCenter(String name){
        return getCenter(getIndexByName(name));
    }

    public float getCenterX(String name){
        return getCenterX(getIndexByName(name));
    }

    public float getCenterY(String name){
        return getCenterY(getIndexByName(name));
    }

}