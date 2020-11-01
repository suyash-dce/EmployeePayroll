import com.capgemini.employeepayroll.EmployeePayrollService.IOService;
import org.junit.Assert;
import org.junit.Test;
import java.util.*;
import java.sql.SQLException;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Ignore;

public class EmployeePayrollServiceTest {
	EmployeePayRollService empPayrollService;
	List<EmployeePayRollData> empPayrollList;
	Map<String, Double> empPayrollDataByGenderMap;

	@Before
	public void Initializer() {
		empPayrollService = new EmployeePayRollService();
		empPayrollList = empPayrollService.readEmployeePayrollData(IOService.DB_IO);
	}

	@Ignore
	@Test
	public void givenEmpPayrollDB_WhenRetrieved_ShouldMatchEmpCount() {
		assertEquals(5, empPayrollList.size());
		System.out.println(empPayrollList);
	}

	@Ignore
	@Test
	public void givenNewSalary_WhenUpdated_ShouldSyncWithDB() throws SQLException {
		EmployeePayRollService empPayRollService = new EmployeePayRollService();
		List<EmployeePayRollData> empPayrollList = empPayRollService.readEmployeePayrollData(IOService.DB_IO);
		empPayRollService.updateEmployeeSalary("Suyash", 2500000);
		boolean isSynced = empPayRollService.isEmpPayrollSyncedWithDB("Suyash");
		assertTrue(isSynced);
	}

	@Ignore
	@Test
	public void givenDateRange_WhenRetrievedEmployee_ShouldReturnEmpCount() throws EmployeePayrollException {
		EmployeePayRollService empPayRollService = new EmployeePayRollService();
		List<EmployeePayRollData> empPayrollList = empPayRollService.readEmployeePayrollData(IOService.DB_IO);
		LocalDate startDate = LocalDate.of(2018, 01, 01);
		LocalDate enDate = LocalDate.now();
		empPayrollList = empPayRollService.getEmpPayrollDataForDataRange(startDate, enDate);
		assertEquals(4, empPayrollList.size());
	}

	@Ignore
	@Test
	public void givenEmployeeDB_WhenRetrievedSum_ShouldReturnSumByGender() throws EmployeePayrollException {
		empPayrollDataByGenderMap = empPayrollService.getSumOfDataGroupedByGender(IOService.DB_IO, "basicPay");
		assertEquals(2500000, empPayrollDataByGenderMap.get("M"), 0.0);
		assertEquals(6000000, empPayrollDataByGenderMap.get("F"), 0.0);
	}

	@Ignore
	@Test
	public void givenEmployeeDB_WhenRetrievedAvg_ShouldReturnAvgByGender() throws EmployeePayrollException {
		empPayrollDataByGenderMap = empPayrollService.getAvgOfDataGroupedByGender(IOService.DB_IO, "basicPay");
		assertEquals(2500000, empPayrollDataByGenderMap.get("M"), 0.0);
		assertEquals(3000000, empPayrollDataByGenderMap.get("F"), 0.0);
	}

	@Ignore
	@Test
	public void givenEmployeeDB_WhenRetrievedMaxMin_ShouldReturnMaxByGender() throws EmployeePayrollException {
		empPayrollDataByGenderMap = empPayrollService.getMAXOfDataGroupedByGender(IOService.DB_IO, "basicPay");
		assertEquals(2500000, empPayrollDataByGenderMap.get("M"), 0.0);
		assertEquals(3000000, empPayrollDataByGenderMap.get("F"), 0.0);
	}

	@Ignore
	@Test
	public void givenEmployeeDB_WhenRetrievedCount_ShouldReturnCountByGender() throws EmployeePayrollException {
		empPayrollDataByGenderMap = empPayrollService.getCountOfDataGroupedByGender(IOService.DB_IO, "id");
		assertEquals(2, empPayrollDataByGenderMap.get("M"), 0.0);
		assertEquals(3, empPayrollDataByGenderMap.get("F"), 0.0);
	}

	@Ignore
	@Test
	public void givenNewEmployee_WhenAdded_ShouldSyncWithDB() throws EmployeePayrollException {
		empPayrollService.readEmployeePayrollData(IOService.DB_IO);
		empPayrollService.addEmployeePayrollData("Harshit", 200000.00, "2016-02-01", "M");
		boolean isSynced = empPayrollService.isEmpPayrollSyncedWithDB("Harshit");
		assertTrue(isSynced);
	}

	@Test
	public void givenNewEmployee_WhenAddedInTwoTables_ShouldSyncWithDB() throws EmployeePayrollException, SQLException {
		empPayrollService.readEmployeePayrollData(IOService.DB_IO);
		empPayrollService.addEmployeeAndPayrollData("Harshit", 200000.00, "2016-02-01", "M");
		boolean isSynced = empPayrollService.isEmpPayrollSyncedWithDB("Harshit");
		assertTrue(isSynced);
	}
}
