package repository;

import model.User;

import java.sql.*;
import java.util.ArrayList;

public class UserRepository {

    public ArrayList<User> readAll() {
        ArrayList<User> allUsers = new ArrayList<>();
        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM user");
            while (rs.next()) {
                allUsers.add(extractUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allUsers;
    }

    public User readById(int id) {

        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM user WHERE id =" + id);
            if(rs.next()) {
                return extractUserFromResultSet(rs);
            } else {
                return null; //nu exista user
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la citirea userului cu id " + id, e);
        }
    }
    public boolean create(String nume, String prenume, String email, String numar_telefon) {
        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE);
            String template = "INSERT INTO user(nume,prenume,email,numar_telefon) VALUES ('%s','%s','%s','%s');";
            int affectedRows = st.executeUpdate(String.format(template,nume,prenume,email,numar_telefon));
           return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Utilizatorul nu a fost salvat");
            return false;
        }
    }

    public void modifyName(int id, String numeNou) {
        modifyColumn(id, numeNou,"nume");
    }

    public void modifyPrenume(int id, String prenumeNou) {
        modifyColumn(id, prenumeNou,"prenume");
    }
    public void modifyEmail(int id, String emailNou) {
        modifyColumn(id, emailNou,"email");
    }
    public void modifyPhoneNumber(int id, String numarNou) {
        modifyColumn(id, numarNou,"numar_telefon");
    }

    public boolean delete(int id) {
        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE);
            int affectedRows = st.executeUpdate("DELETE FROM user WHERE id = " + id);
            return affectedRows > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    private static void modifyColumn(int id, String valoareNoua, String columnName) {
        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE);
            String template = "UPDATE user SET %s = '%s' WHERE id = %d";
            int affectedRows = st.executeUpdate(String.format(template,columnName, valoareNoua, id));
            System.out.println(columnName + " " + (affectedRows > 0 ? "modificat" : "nemodificat"));
        } catch (SQLException e) {
            System.out.println("Coloana nu a putut fi modificata");
        }
    }

    private static User extractUserFromResultSet(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("nume"),
                rs.getString("prenume"),
                rs.getString("email"),
                rs.getString("numar_telefon"),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }
}
