import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Start {

    private static final String DBURL = "jdbc:mysql://localhost:3306/newdb";
    private static final String USER = "developer";
    private static final String PASSWORD = "passwordhere";

    public static void main(String[] args) {
        Connection connection = null;
        List<Student> italianStudents = new ArrayList<>();
        List<Student> germanStudents = new ArrayList<>();

        try {

            // create a connection to the database
            connection = DriverManager.getConnection(DBURL, USER, PASSWORD);
            Statement statement = connection.createStatement();

            statement.executeUpdate("CREATE VIEW italian_students AS SELECT first_name, last_name FROM students WHERE country = 'Italy';");
            statement.executeUpdate("CREATE VIEW german_students AS SELECT first_name, last_name FROM students WHERE country = 'Germany';");
            System.out.println("Views have been created");

            ResultSet resultSet = statement.executeQuery("SELECT * FROM italian_students;");
            while (resultSet.next()){
                Student student = new Student(resultSet.getString("first_name"), resultSet.getString("last_name"));
                italianStudents.add(student);
            }

            resultSet = statement.executeQuery("SELECT * FROM german_students;");
            while (resultSet.next()){
                Student student1 = new Student(resultSet.getString("first_name"), resultSet.getString("last_name"));
                germanStudents.add(student1);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

        System.out.println("\nPrint all italian students");
        for (Student student : italianStudents){
            System.out.println(student.getName() + " " + student.getSurname());
        }

        System.out.println("\nPrint all german students");
        for (Student student : germanStudents){
            System.out.println(student.getName() + " " + student.getSurname());
        }
    }
}
