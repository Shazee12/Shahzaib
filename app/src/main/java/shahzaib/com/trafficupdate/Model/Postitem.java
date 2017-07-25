package shahzaib.com.trafficupdate.Model;

import android.widget.EditText;

/**
 * Created by shahzaib on 7/25/2017.
 */

public class Postitem {
    public Postitem() {
    }

    String name,textmessage,image;
    long time;

    public Postitem(String name, String textmessage, String image, long time) {
        this.name = name;
        this.textmessage = textmessage;
        this.image = image;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTextmessage() {
        return textmessage;
    }

    public void setTextmessage(String textmessage) {
        this.textmessage = textmessage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        image = image;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
