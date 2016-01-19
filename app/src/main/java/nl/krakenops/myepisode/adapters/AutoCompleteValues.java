package nl.krakenops.myepisode.adapters;

/**
 * Created by Matthijs on 19/01/2016.
 */
public class AutoCompleteValues {
    String title;
    public AutoCompleteValues(String title) {
        this.setTitle(title);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
