import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.bridgelabz.employee.EmployeePayrollException.ExceptionType;

public class EmployeePayrollDBService {
	private PreparedStatement empPreparedStatement;
	private static EmployeePayrollDBService employeePayrollDBService;

	public static EmployeePayrollDBService getInstance() {
		if (employeePayrollDBService == null)
			employeePayrollDBService = new EmployeePayrollDBService();
		return employeePayrollDBService;
	}

	private Connection getConnection() throws SQLException {
		String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
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
		return getEmployeePayrollList(sql);
	}

	public List<EmployeePayRollData> getEmployeePayrollDatas(String name) {
		List<EmployeePayRollData> employeePayrollList = new ArrayList<>();
		if (empPreparedStatement == null)
			prepareStatementForEmployeeData();
		try {
			empPreparedStatement.setString(1, name);
			ResultSet resultSet = empPreparedStatement.executeQuery();
			employeePayrollList = getEmployeePayrollList(resultSet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employeePayrollList;
	}

	public List<EmployeePayRollData> getEmployeesForDateRange(LocalDate startDate,LocalDate endDate)
		throws EmployeePayrollException{
		String  sql=String.format("SELECT * FROM employee_payroll where start_date between '%s' AND '%s';",
				Date.valueOf(startDate),Date.valueOf(endDate));
		return getEmployeePayrollList(sql);
	}
	private List<EmployeePayRollData> getEmployeePayrollList(String sql) {
			List<EmployeePayRollData> employeePayrollList= new ArrayList<>();
			try(Connection connection =getConnection()){
				PreparedStatement preparedStatement=connection.prepareStatement(sql);
				ResultSet resultSet=preparedStatement.executeQuery();
				while(resultSet.next()) {
					employeePayrollList.add(new EmployeePayRollData(resultSet.getInt("id"),resultSet.getString("name"),
							resultSet.getDouble("basicPay"),resultSet.getDate("start_Date")));
				}
			}catch(SQLException e) {
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

	public int updateEmployeeData(String name, double salary) throws EmployeePayrollException {
		String sql = "update employee_payroll set basicPay=? where name=?";
		try (Connection connection = this.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setDouble(1, salary);
			preparedStatement.setString(2, name);
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new EmployeePayrollException("Wrong SQL query given", ExceptionType.WRONG_SQL);
		}
	}

	private List<EmployeePayRollData> getEmployeePayrollList(ResultSet resultSet) {
		List<EmployeePayRollData> employeePayrollList = new ArrayList<>();
		try {
			while (resultSet.next()) {
				employeePayrollList.add(new EmployeePayRollData(resultSet.getInt("id"), resultSet.getString("name"),
						resultSet.getDouble("basicPay"), resultSet.getDate("start_date")));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollList;
	}
	
	public int updateSalaryUsingSQL(String name,Double salary) throws SQLException {
		String sql="UPDATE employee_payroll SET basicPay=? WHERE name=?";
		try(Connection connection=getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setDouble(1, salary);
			preparedStatement.setString(2, name);
			return preparedStatement.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
