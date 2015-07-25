package beingme.cybereye.beingme;

/**
 * Created by Samyuktha-Tech on 25-Jul-15.
 */
public class Post {
    String post;
    String date;

    public Post(String post, String date) {
        this.post = post;
        this.date = date;
    }

    @Override
    public String toString() {
        return date+"\n"+post+"\n\n";
    }
}
