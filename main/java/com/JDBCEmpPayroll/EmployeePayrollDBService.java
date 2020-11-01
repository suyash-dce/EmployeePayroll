import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollDBService {
	private PreparedStatement empPreparedStatement;
	private static EmployeePayrollDBService employeePayrollDBService;
	
	public static EmployeePayrollDBService getInstance() {
		if (employeePayrollDBService == null)
			employeePayrollDBService = new EmployeePayrollDBService();
		return employeePayrollDBService;
	}
	
	private Connection getConnection() throws SQLException {
		String jdbcURL = "jdbc:mysql://localhost:3306/employee_payroll?useSSL=false";
		String userName = "root";
		String password = "jain1234";
		Connection connection;
		System.out.println("Connecting to database:" + jdbcURL);
		connection = DriverManager.getConnection(jdbcURL, userName, password);
		System.out.println("Connection is successful!" + connection);
		return connection;

	}
	
	public List<EmployeePayRollData> readData() {
		String sql = "SELECT * FROM employee_payroll";
		List<EmployeePayRollData> employeePayrollList = new ArrayList<>();
		try (Connection connection = this.getConnection();) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				employeePayrollList.add(new EmployeePayRollData(resultSet.getInt("id"), resultSet.getString("name"),
						resultSet.getDouble("basicPay"), resultSet.getDate("start_date")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollList;
	}
	
	public List<EmployeePayRollData> getEmployeePayrollDatas(String name) {
		List<EmployeePayRollData> employeePayrollList = new ArrayList<>();
		if (empPreparedStatement == null)
			prepareStatementForEmployeeData();
		try {
			empPreparedStatement.setString(1, name);
			ResultSet resultSet = empPreparedStatement.executeQuery();
			while (resultSet.next()) {
				employeePayrollList.add(new EmployeePayRollData(resultSet.getInt("id"), resultSet.getString("name"),
						resultSet.getDouble("basicPay"), resultSet.getDate("start_date")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employeePayrollList;
	}
	
	private void prepareStatementForEmployeeData() {
		try {
			Connection connection = getConnection();
			String sql = "SELECT * FROM employee_payroll WHERE name = ?";
			empPreparedStatement = connection.prepareStatement(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int updateEmployeeData(String name, double salary) {
		String sql= String.format("update employee_payroll set  basicPay=%.2f where name='%s';", salary,name);
		try (Connection connection = this.getConnection();) {
			Statement statement = connection.createStatement();
			return statement.executeUpdate(sql);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int updateSalaryUsingSQL(String name, Double salary) {
		String sql = "UPDATE employee_payroll SET basicPay = ? WHERE name = ? ";
		try (Connection connection = getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setDouble(1, salary);
			preparedStatement.setString(2, name);
			return preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
