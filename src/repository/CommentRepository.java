package repository;

import model.Comment;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class CommentRepository {
    public ArrayList<Comment> readCommentsFromPostWithId(int post_id) {

        ArrayList<Comment> comentarilePostarii = new ArrayList<>();
        Connection c = ConnectionSingleton.getInstance().getConnection();

        try {
            PreparedStatement ps = c.prepareStatement(
                    "SELECT * FROM comment WHERE post_id = ?"
            );

            ps.setInt(1, post_id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                comentarilePostarii.add(extractCommentFromResultSet(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return comentarilePostarii;
    }

    private Comment extractCommentFromResultSet(ResultSet rs) {
        try {
            return new Comment(rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("post_id"),
                    rs.getString("mesaj"),
                    LocalDateTime.of(rs.getInt("an"),
                            rs.getInt("luna"),
                            rs.getInt("zi"),
                            rs.getInt("ora"),
                            rs.getInt("minut")
                    ));
        } catch (SQLException e) {
            return null;
        }
    }

    public int create(int user_id,int post_id, String mesaj, LocalDateTime createAt) {
        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            String template = "INSERT INTO comment (user_id,post_id, mesaj, an, luna, zi, ora, minut)"
                    + "VALUES (%d, %d, '%s', %d, %d, %d, %d, %d)";
            return st.executeUpdate(String.format(template,
                    user_id,
                    post_id,
                    mesaj,
                    createAt.getYear(),
                    createAt.getMonthValue(),
                    createAt.getDayOfMonth(),
                    createAt.getHour(),
                    createAt.getMinute()
            ));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Comment readById(int id) {

        Connection c = ConnectionSingleton.getInstance().getConnection();

        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM comment WHERE id = " + id);

            if (rs.next()) {
                return new Comment(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("post_id"),
                        rs.getString("mesaj"),
                        LocalDateTime.of(rs.getInt("an"),
                                rs.getInt("luna"),
                                rs.getInt("zi"),
                                rs.getInt("ora"),
                                rs.getInt("minut"))
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void update(int id, String mesaj) {
        Connection c = ConnectionSingleton.getInstance().getConnection();

        try {
            Statement st = c.createStatement();
            st.executeUpdate("UPDATE comment SET mesaj = '" + mesaj + "' WHERE id = " + id);
            System.out.println("Comentariul a fost modificat.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int delete(int id) {
        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            return st.executeUpdate("DELETE FROM comment WHERE id = " + id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
