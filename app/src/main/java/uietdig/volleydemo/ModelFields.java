package uietdig.volleydemo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by akshay on 23/11/17.
 */

public class ModelFields {
    private String description;
    private String url;
    private String image;
    private Boolean showOnHomeScreen;
    private String shortDescription;
    private String heading;
    private String addedOn;

    public ModelFields(JSONObject object){
        try{
            description = object.getString("description");
            url = object.getString("url");
            image = object.getString("image");
            showOnHomeScreen = object.getBoolean("showOnHomeScreen");
            shortDescription = object.getString("shortDescription");
            heading = object.getString("heading");
            addedOn = object.getString("addedOn");

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getImage() {
        return image;
    }

    public Boolean getShowOnHomeScreen() {
        return showOnHomeScreen;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getHeading() {
        return heading;
    }

    public String getAddedOn() {
        return addedOn;
    }
}
