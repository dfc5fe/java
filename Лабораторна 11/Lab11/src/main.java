import java.sql.*;

public class main {
    public static void main(String[] args) {
        try (Connection conn = dbhandler.getConnection()) {
            if (conn != null) {
                System.out.println("Connected to the database!");

                // 1. отримати список всіх співробітників
                getAllEmployees(conn);

                // 2. отримати список всіх завдань
                getAllTasks(conn);

                // 3. отримати список співробітників зазначеного відділу
                getEmployeesByDept(conn, 1);

                // 4. додати завдання (використовуємо try-catch щоб не впало, якщо таке id вже є)
                try {
                    addTask(conn, 4, "update windows", 101);
                } catch (SQLException e) {
                    System.out.println("Error adding task (maybe ID exists): " + e.getMessage());
                }

                // 5. отримати список завдань співробітника
                getTasksForEmployee(conn, 101);

                // 6. видалити співробітника
                deleteEmployee(conn, 103);
            }
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }

    private static void getAllEmployees(Connection conn) throws SQLException {
        System.out.println("\n--- All Employees ---");
        String sql = "SELECT * FROM employees";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(rs.getInt("emp_id") + " " + rs.getString("last_name") + " " + rs.getString("position"));
            }
        }
    }

    private static void getAllTasks(Connection conn) throws SQLException {
        System.out.println("\n--- All Tasks ---");
        String sql = "SELECT * FROM tasks";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("Task: " + rs.getString("description") + " (Assigned to emp ID: " + rs.getInt("emp_id") + ")");
            }
        }
    }

    private static void getEmployeesByDept(Connection conn, int deptId) throws SQLException {
        System.out.println("\n--- Employees in Dept " + deptId + " ---");
        String sql = "SELECT * FROM employees WHERE dept_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, deptId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println(rs.getString("last_name"));
                }
            }
        }
    }

    private static void addTask(Connection conn, int taskId, String desc, int empId) throws SQLException {
        System.out.println("\n--- Adding new task ---");
        String sql = "INSERT INTO tasks (task_id, description, emp_id) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, taskId);
            pstmt.setString(2, desc);
            pstmt.setInt(3, empId);
            int rows = pstmt.executeUpdate();
            if (rows > 0) System.out.println("Task added successfully!");
        }
    }

    private static void getTasksForEmployee(Connection conn, int empId) throws SQLException {
        System.out.println("\n--- Tasks for employee " + empId + " ---");
        String sql = "SELECT description FROM tasks WHERE emp_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, empId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("- " + rs.getString("description"));
                }
            }
        }
    }

    private static void deleteEmployee(Connection conn, int empId) throws SQLException {
        System.out.println("\n--- Deleting employee " + empId + " ---");

        // спочатку видаляємо завдання
        String deleteTasks = "DELETE FROM tasks WHERE emp_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(deleteTasks)) {
            pstmt.setInt(1, empId);
            pstmt.executeUpdate();
        }

        // тепер співробітника
        String deleteEmp = "DELETE FROM employees WHERE emp_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(deleteEmp)) {
            pstmt.setInt(1, empId);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Employee deleted successfully!");
            } else {
                System.out.println("Employee not found.");
            }
        }
    }
}