package model;

import java.time.LocalDateTime;

public class Comment {
    int id;
    int user_id;
    int post_id;
    String mesaj;
    LocalDateTime createAt;

    public Comment(int id, int user_id, int post_id, String mesaj, LocalDateTime createAt) {
        this.id = id;
        this.user_id = user_id;
        this.post_id = post_id;
        this.mesaj = mesaj;
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "\nComment{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", post_id=" + post_id +
                ", mesaj='" + mesaj + '\'' +
                ", createAt=" + createAt +
                '}';
    }
}
