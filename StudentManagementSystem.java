
import java.sql.*;
import java.util.Scanner;

public class StudentManagementSystem {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/StudentDB",
                "root",
                "YOUR_PASSWORD"
            );

            System.out.println("Database Connected!");

            while (true) {
                System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
                System.out.println("1. Add Student");
                System.out.println("2. View Students");
                System.out.println("3. Search Student");
                System.out.println("4. Update Student");
                System.out.println("5. Delete Student");
                System.out.println("6. Exit");

                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();

                switch (choice) {

                    case 1:
                        System.out.print("Enter ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Course: ");
                        String course = sc.nextLine();

                        PreparedStatement add = con.prepareStatement(
                            "INSERT INTO Student VALUES (?, ?, ?)"
                        );
                        add.setInt(1, id);
                        add.setString(2, name);
                        add.setString(3, course);
                        add.executeUpdate();

                        System.out.println("Student Added Successfully!");
                        break;

                    case 2:
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery(
                            "SELECT * FROM Student"
                        );

                        while (rs.next()) {
                            System.out.println(
                                rs.getInt("id") + " | " +
                                rs.getString("name") + " | " +
                                rs.getString("course")
                            );
                        }
                        break;

                    case 3:
                        System.out.print("Enter Student ID: ");
                        int searchId = sc.nextInt();

                        PreparedStatement search = con.prepareStatement(
                            "SELECT * FROM Student WHERE id=?"
                        );
                        search.setInt(1, searchId);
                        ResultSet sr = search.executeQuery();

                        if (sr.next()) {
                            System.out.println("ID: " + sr.getInt("id"));
                            System.out.println("Name: " + sr.getString("name"));
                            System.out.println("Course: " + sr.getString("course"));
                        } else {
                            System.out.println("Student Not Found!");
                        }
                        break;

                    case 4:
                        System.out.print("Enter ID to Update: ");
                        int updateId = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter New Name: ");
                        String newName = sc.nextLine();

                        System.out.print("Enter New Course: ");
                        String newCourse = sc.nextLine();

                        PreparedStatement update = con.prepareStatement(
                            "UPDATE Student SET name=?, course=? WHERE id=?"
                        );
                        update.setString(1, newName);
                        update.setString(2, newCourse);
                        update.setInt(3, updateId);

                        int updated = update.executeUpdate();

                        if (updated > 0)
                            System.out.println("Student Updated Successfully!");
                        else
                            System.out.println("Student Not Found!");
                        break;

                    case 5:
                        System.out.print("Enter ID to Delete: ");
                        int deleteId = sc.nextInt();

                        PreparedStatement delete = con.prepareStatement(
                            "DELETE FROM Student WHERE id=?"
                        );
                        delete.setInt(1, deleteId);

                        int deleted = delete.executeUpdate();

                        if (deleted > 0)
                            System.out.println("Student Deleted Successfully!");
                        else
                            System.out.println("Student Not Found!");
                        break;

                    case 6:
                        con.close();
                        sc.close();
                        System.out.println("Thank You!");
                        return;

                    default:
                        System.out.println("Invalid Choice!");
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
