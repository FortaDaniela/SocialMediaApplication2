package service;

import model.Comment;
import model.Post;
import model.User;
import repository.CommentRepository;
import repository.PostRepository;
import repository.UserRepository;

import java.time.LocalDateTime;

import java.util.Scanner;

public class CommentService {

    private final PostRepository postRepository = new PostRepository();

    private final UserRepository userRepository = new UserRepository();

    private final CommentRepository commentRepository = new CommentRepository();
    private final Scanner scNumere = new Scanner(System.in);

    private final Scanner scText = new Scanner(System.in);

    public void create() {
        System.out.println("Introduceti id-ul postarii la care vrem sa adaugam un mesaj:");
        int post_id = scNumere.nextInt();
        Post post = postRepository.readById(post_id);
        if (post == null) {
            System.out.println("Nu exista nici o postare cu id-ul " + post_id);
        } else {
            System.out.println("Introduceti id-ul userului care doreste sa comenteze la acea postare: ");
            int user_id = scNumere.nextInt();
            User user = userRepository.readById(user_id);

            if (user == null) {
                System.out.println("Nu exista nici un user cu id-ul citit");
            } else {
                System.out.println("Introduceti commentariul:");
                String mesaj = scText.nextLine();
                LocalDateTime createAt = LocalDateTime.now();

                int affectedRows = commentRepository.create(user_id,post_id,mesaj,createAt);

                if (affectedRows > 0 ) {
                    System.out.println("Comentariul a fost salvat");
                    System.out.println(affectedRows);
                } else {
                    System.out.println("Comentariul nu a putut fi salvat");
                }
            }
        }
    }

    public void update() {

        System.out.println("Introducem id-ul comentariului pe care dorim sa il modificam: ");
        int id = scNumere.nextInt();

        Comment comment = commentRepository.readById(id);

        System.out.println("Introducem user_id: ");
        int user_id = scNumere.nextInt();
        User user = userRepository.readById(user_id);

        System.out.println("Introducem post_id: ");
        int post_id = scNumere.nextInt();
        Post post = postRepository.readById(post_id);

        if (id <= 0) {
            System.out.println("Id invalid.");
            return;
        }

        if(comment == null){
            System.out.println("Comentariul cu id-ul " + id + " nu exista.");
            return;
        }

        if(user == null){
            System.out.println("Userul nu exista.");
            return;
        }

        if(post == null){
            System.out.println("Postarea nu exista.");
            return;
        }

        scText.nextLine();
        System.out.println("Introducem noul mesaj:");
        String mesaj = scText.nextLine();

        commentRepository.update(id,mesaj);
    }

    public void delete() {

        System.out.println("Introduceti id-ul comentariului pe care doriti sa il stergeti: ");
        int id = scNumere.nextInt();

        int affectedRows = commentRepository.delete(id);
        System.out.println("Comentariul " + (affectedRows == 0 ? "nu" : "") + " a fost stears");
    }
}
