package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Post {

    public int id;

    int user_id;
    String mesaj;
    LocalDateTime createAt;
    public ArrayList<Comment> comments;

    public Post(int id, int user_id, String mesaj, LocalDateTime createAt, ArrayList<Comment> comments) {
        this.id = id;
        this.user_id = user_id;
        this.mesaj = mesaj;
        this.createAt = createAt;
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "\nPost{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", mesaj='" + mesaj + '\'' +
                ", createAt=" + createAt +
                ", comments=" + comments +
                '}';
    }
}
