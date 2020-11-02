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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		String sql = "SELECT * FROM employee_payroll;";
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

	public List<EmployeePayRollData> getEmployeesForDateRange(LocalDate startDate, LocalDate endDate)
			throws EmployeePayrollException {
		String sql = String.format("SELECT * FROM employee_payroll where start_date between '%s' AND '%s';",
				Date.valueOf(startDate), Date.valueOf(endDate));
		return getEmployeePayrollList(sql);
	}

	private List<EmployeePayRollData> getEmployeePayrollList(String sql) {
		List<EmployeePayRollData> employeePayrollList = new ArrayList<>();
		try (Connection connection = getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				employeePayrollList.add(new EmployeePayRollData(resultSet.getInt("id"), resultSet.getString("name"),
						resultSet.getDouble("basicPay"), resultSet.getDate("start_Date")));
			}
		} catch (SQLException e) {
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

	public int updateSalaryUsingSQL(String name, Double salary) throws SQLException {
		String sql = "UPDATE employee_payroll SET basicPay=? WHERE name=?";
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

	public Map<String, Double> getEmpDataGroupByGender(String column, String operation) {
		Map<String, Double> dataByGenderMap = new HashMap<>();
		String sql = String.format("SELECT gender, %s(%s) FROM employee_payroll GROUP BY gender;", operation, column);
		try (Connection connection = getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				dataByGenderMap.put(resultSet.getString(1), resultSet.getDouble(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dataByGenderMap;
	}

	public int insertNewEmployeeToDB(String name, Double salary, String startDate, String gender)
			throws EmployeePayrollException {
		String sql = String.format(
				"INSERT INTO employee_payroll(name,basicPay,start_date,gender) VALUES ('%s','%s','%s','%s');", name,
				salary, startDate, gender);
		try (Connection connection = getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new EmployeePayrollException("Wrong SQL or field given", ExceptionType.WRONG_SQL);
		}
	}

	public EmployeePayRollData addNewEmployeeToDB(String name, Double salary, String startDate, String gender,
			int company_id, List<String> department) throws EmployeePayrollException, SQLException {
		EmployeePayRollData employeePayrollData = null;
		int empId = -1;
		Connection connection = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (Statement statement = connection.createStatement()) {
			String sql = String.format(
					"INSERT INTO employee_payroll(name,basicPay,start_date,gender,company_id) values ('%s','%s','%s','%s','%s');",
					name, salary, startDate, gender, company_id);
			int rowAffected = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
			if (rowAffected == 1) {
				ResultSet resultSet = statement.getGeneratedKeys();
				if (resultSet.next())
					empId = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
				return employeePayrollData;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		try (Statement statement = connection.createStatement()) {
			String sql = String.format("INSERT INTO departments(empId,department) VALUES ('%s','%s');", empId,
					department);
			int rowAffected = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			if (rowAffected == 1) {
				ResultSet resultSet = statement.getGeneratedKeys();
				if (resultSet.next())
					empId = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
				return employeePayrollData;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			try (Statement statement = connection.createStatement()) {
				double deductions = salary * 0.2;
				double taxablePay = salary - deductions;
				double incomeTax = taxablePay * 0.1;
				double netPay = taxablePay = incomeTax;
				String sql = String.format(
						"INSERT INTO payroll_details(emp_id,basicPay,deductions,taxablePay,incomeTax,netPay) values "
								+ "('%s','%s','%s','%s','%s','%s');",
						empId, salary, deductions, taxablePay, incomeTax, netPay);
				int rowAffected = statement.executeUpdate(sql);
				if (rowAffected == 1) {
					employeePayrollData = new EmployeePayRollData(empId, name, salary, Date.valueOf(startDate),
							gender.charAt(0), company_id, department);
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			try {
				connection.commit();
			} catch (SQLException e3) {
				e3.printStackTrace();
			} finally {
				if (connection != null)
					try {
						connection.close();
					} catch (SQLException e4) {
						e4.printStackTrace();
					}
			}
		}
		return employeePayrollData;
	}
	
	public void removeEmployeeFromDB(int empId) throws EmployeePayrollException, SQLException{
		String sql=String.format("UPDATE employee_payroll SET active = false WHERE id= '%s'",empId);
		try(Connection connection=getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.executeUpdate();
		}catch (SQLException e) {
			throw new EmployeePayrollException("Wrong SQL or field given",ExceptionType.WRONG_SQL);
		}
	}
}
