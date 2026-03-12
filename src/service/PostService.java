package service;

import model.Post;
import model.User;

import repository.PostRepository;
import repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class PostService {
    private final PostRepository postRepository = new PostRepository();
    private final UserRepository userRepository = new UserRepository();
    private final Scanner scNumere = new Scanner(System.in);

    private final Scanner scText = new Scanner(System.in);
    public void readAll() {
        ArrayList<Post> allPosts = postRepository.readAll();
        //printam toate postarile
        for (Post post : allPosts) {
            System.out.println(post);
        }
    }

    public void readUserPosts() {

        System.out.println("Introduceti id-ul userului pentru a-i returna postarile");
        int user_id = scNumere.nextInt();
        User user = userRepository.readById(user_id);
        if (user == null) {
            System.out.println("Nu exista nici un user cu id-ul " + user_id);
        } else {
            ArrayList<Post> postarileUserului = postRepository.readPostsFromUserWithId(user_id);
            postarileUserului.forEach(postare -> System.out.println(postare));
        }
    }

    public void create() {

        System.out.println("Introduceti id-ul userului: ");
        int user_id = scNumere.nextInt();
        User user = userRepository.readById(user_id);
        if (user == null) {
            System.out.println("Nu exista nici un user cu id-ul " + user_id);
        } else {
            System.out.println("Introduceti mesajul: ");
            String mesaj = scText.nextLine();
            LocalDateTime createAt = LocalDateTime.now();
            int affectedRows = postRepository.create(user_id,mesaj,createAt);
            if (affectedRows > 0 ) {
                System.out.println("Postarea a fost salvata");
            } else {
                System.out.println("Postarea nu a putut fi salvata");
            }
        }
        /*LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt.getYear());
        System.out.println(ldt.getMonthValue());
        System.out.println(ldt.getDayOfMonth());
        System.out.println(ldt.getHour());
        System.out.println(ldt.getMinute()); */

    }

    public void update() {

        System.out.println("Introduceti id-ul postarii: ");
        int post_id = scNumere.nextInt();

        Post post = postRepository.readById(post_id);
        if (post == null) {
            System.out.println("Nu exista nici o postare cu id-ul " + post_id);
        } else {
            System.out.println("Introduceti noul mesaj: ");
            String mesajNou = scText.nextLine();
            int affectedRows = postRepository.update(post_id,mesajNou);
            if (affectedRows > 0) {
                System.out.println("Postarea a fost modificata");
            } else {
                System.out.println("Nu a putut fi modificata");
            }
        }
    }
    public void delete() {

        System.out.println("Introduceti id-ul postarii pe care doriti sa o stergeti: ");
        int post_id = scNumere.nextInt();
        int affectedRows = postRepository.delete(post_id);
        System.out.println("Postarea " + (affectedRows == 0 ? "nu" : "") + " a fost stearsa");
    }
}
