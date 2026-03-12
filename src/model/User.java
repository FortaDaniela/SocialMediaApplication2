package model;

import java.util.ArrayList;

public class User {

    public int id;
    public String nume;
    public String prenume;
    public String email;
    public String numar_telefon;
    public ArrayList<Post> postari;
    public ArrayList<Comment> comments;

    public User(int id, String nume, String prenume, String email, String numar_telefon,
                ArrayList<Post> postari, ArrayList<Comment> comments) {
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.numar_telefon = numar_telefon;
        this.postari = postari;
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", email='" + email + '\'' +
                ", numar_telefon='" + numar_telefon + '\'' +
                ", postari=" + postari +
                ", comments=" + comments +
                "}\n";
    }
}
