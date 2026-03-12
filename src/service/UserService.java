package service;

import model.Post;
import model.User;
import repository.CommentRepository;
import repository.PostRepository;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.Scanner;

public class UserService {

    private final UserRepository userRepository = new UserRepository();

    private final PostRepository postRepository = new PostRepository();

    private final CommentRepository commentRepository = new CommentRepository();

    private final Scanner scannerNumere = new Scanner(System.in);

    private final Scanner scannerText = new Scanner(System.in);

    private final Scanner scannerBoolean = new Scanner(System.in);

    public void readAll() {
        ArrayList<User> allUsers = userRepository.readAll();

        for (User user : allUsers) {
            // luăm postările userului
            user.postari = postRepository.readPostsFromUserWithId(user.id);

            for (Post post : user.postari) {
                // luăm comentariile pentru fiecare post
                post.comments = commentRepository.readCommentsFromPostWithId(post.id);
            }
        }
        allUsers.forEach(System.out::println);
    }
    public void readById() {
        System.out.println("Introduceti idul utilizatorului dorit:");
        int id = scannerNumere.nextInt();

        User user = userRepository.readById(id);

        if (user == null) {
            System.out.println("Nu exista nici un user cu idul " + id);
        } else {
            // luam postarile userului
            user.postari = postRepository.readPostsFromUserWithId(id);

            // pentru fiecare post luam comentariile
            for (Post post : user.postari) {
                post.comments = commentRepository.readCommentsFromPostWithId(post.id);
            }
            System.out.println(user);
        }
    }

    public void create() {
        System.out.println("Introduceti numele:");
        String nume = scannerText.nextLine();
        System.out.println("Introduceti prenumele:");
        String prenume = scannerText.nextLine();
        System.out.println("Introduceti emailul:");
        String email = scannerText.nextLine();
        System.out.println("Introduceti numarul de telefon:");
        String numar_telefon = scannerText.nextLine();
        //trimitem un request cu informatiile pentru a salva in baza de date utilizatorul nou
        boolean succes = userRepository.create(nume,prenume,email,numar_telefon);
        if (succes) {
            System.out.println("Utilizator salvat");
        } else {
            System.out.println("A aparut o eroare.");
        }
    }

    public void update() {
        //introducem id-ul userului
        System.out.println("Introduceti id-ul userului:");
        int id = scannerNumere.nextInt();
        //verificam daca userul exista in BD
        User user = userRepository.readById(id);
        if (user != null) {
            boolean modificamNumele = modificamProprietatea("nume");
            boolean modificamPrenumele = modificamProprietatea("prenume");
            boolean modificamEmailul = modificamProprietatea("email");
            boolean modificamNumarTelefon = modificamProprietatea("numar_telefon");

            if (modificamNumele) {
                System.out.println("Introduceti noul nume:");
                String numeNou = scannerText.nextLine();
                userRepository.modifyName(id,numeNou);
            }
            if (modificamPrenumele) {
                System.out.println("Introduceti noul prenume:");
                String prenumeNou = scannerText.nextLine();
                userRepository.modifyPrenume(id,prenumeNou);
            }
            if (modificamEmailul) {
                System.out.println("Introduceti noul email:");
                String emailNou = scannerText.nextLine();
                userRepository.modifyEmail(id,emailNou);
            }
            if (modificamNumarTelefon) {
                System.out.println("Introduceti noul numar de telefon:");
                String numarTelefonNou = scannerText.nextLine();
                userRepository.modifyPhoneNumber(id,numarTelefonNou);
            }
        } else {
            System.out.println("Nu exista nici un user cu id-ul " + id);
        }

    }
    public boolean modificamProprietatea(String proprietate) {
        System.out.println("Doriti sa modificati proprietatea " + proprietate + " ?");
        return scannerBoolean.nextBoolean();
    }

    public void delete() {
        System.out.println("Introduceti id-ul userului pe care doriti sa il stergeti:");
        int id = scannerNumere.nextInt();
        boolean succes = userRepository.delete(id);
        System.out.println(succes ? "Utilizatorul a fost sters" : "Utilizatorul nu a fost sters");
    }
}
