import java.sql.*;
import java.util.Enumeration;

public class EmployeePayrollJDBC {
	public static void main(String[] args) {
		String jdbcURL = "jdbc:mysql://localhost:3306/employee_payroll?useSSL=false";
		String userName = "root";
		String password = "jain1234";
		Connection con;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver Loaded!");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("Cannot find driver in classpath!", e);
		}
		listDrivers();
		try{
			System.out.println("Connecting to database: " + jdbcURL);
			con = DriverManager.getConnection(jdbcURL, userName, password);
			System.out.println("Connection is successful!!!!!" + con);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static void listDrivers() {
		Enumeration<Driver> driverList = DriverManager.getDrivers();
		while (driverList.hasMoreElements()) {
			Driver driverClass = (Driver) driverList.nextElement();
			System.out.println(" " + driverClass.getClass().getName());
		}
	}
}
