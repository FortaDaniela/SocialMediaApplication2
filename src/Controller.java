import service.CommentService;
import service.PostService;
import service.UserService;

import java.util.Scanner;

public class Controller {

    private final static UserService userService = new UserService();

    private final static PostService postService = new PostService();

    private final static CommentService commentService = new CommentService();
    static Scanner scText = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("Introduceti flowul dorit (user/post/comment):");
            String response = scText.nextLine();
            switch (response) {
                case "user":
                    startUserFlow();
                    break;
                case "post":
                    startPostFlow();
                    break;
                case "comment":
                    startCommentFlow();
                default:
                    System.out.println("Acest flow nu exista.");
            }
        }
    }

    private static void startUserFlow() {

        String operatiaAleasa = chooseOperation("RA / RBI / C / U /D");

        switch (operatiaAleasa) {
            case "RA":
                userService.readAll();
                break;
            case "RBI":
                userService.readById();
                break;
            case "C":
                userService.create();
                break;
            case "U":
                userService.update();
                break;
            case "D":
                userService.delete();
                break;
            default:
                System.out.println("Operatie invalida");

        }
    }

    private static void startPostFlow() {

        String operatiaAleasa = chooseOperation("RA / RUP / C / U /D");

        switch (operatiaAleasa) {
            case "RA":
                postService.readAll();
                break;
            case "RUP":
                postService.readUserPosts();
                break;
            case "C":
                postService.create();
                break;
            case "U":
                postService.update();
                break;
            case "D":
                postService.delete();
                break;
            default:
                System.out.println("Operatie invalida");
        }
    }

    private static void startCommentFlow() {
        String operatiaAleasa = chooseOperation("C / U /D");
        switch (operatiaAleasa) {
            case "C":
                commentService.create();
                break;
            case "U":
                commentService.update();
                break;
            case "D":
                commentService.delete();
                break;
            default:
                System.out.println("Operatie invalida");
        }
    }

    private static String chooseOperation(String operations) {
        System.out.println("Introduceti operatia dorita (" + operations + ")");
        String operatiaAleasa = scText.nextLine();
        return operatiaAleasa;
    }

    /*
    tabel comment: id, post_id, user_id, mesaj, an, luna, zi, ora, minut (post_id & user_id foreign keys)
    clasa Comment: id, post_id, user_id, mesaj, createdAt(LocalDateTime)

    un Post va avea o lista de Comment

    CREATE
     -introducem id-ul postarii la care vrem sa adaugam un comentariu
     -daca postarea NU exista,afisam un mesaj corespunzator
     -daca postarea exista, introducem id-ul userului care dorim sa comenteze la acea postare
     -daca userul NU exista, afisam un mesaj corespunzator
     -daca userul exista, introducem de la tastatura mesajul comentariului
     -trimitem un request catre commentRepository in care facem un CREATE in BD
     -in functie de raspunsul returnat in service, afisam un mesaj corespunzator

    UPDATE
    -singurul lucru care va putea fi modificat la un comment este mesajul
    -introducem id-ul comentariului pe care dorim sa il stergem
    -citim comentariul respectiv din BD prin commentRepository
    -daca comentariul NU exista, afisam un mesaj corespunzator
    -daca comentariul exista, introducem un nou mesaj de la tastatura
    -trimitem un request catre commentRepository, unde facem un UPDATE

    DELETE
    -introducem id-ul comentariului pe care dorim sa il stergem
    -trimitem un request catre commentRepository pentru a sterge comentariul
    -in functie de raspunsul returnat in service, printam un mesaj corespunzator

     */
}
