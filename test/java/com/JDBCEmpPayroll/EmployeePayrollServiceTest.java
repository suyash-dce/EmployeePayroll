import com.capgemini.employeepayroll.EmployeePayrollService.IOService;
import org.junit.Assert;
import org.junit.Test;
import java.util.*;

public class EmployeePayrollServiceTest {
	EmployeePayRollService empPayrollService;
	List<EmployeePayRollData> empPayrollList;
	Map<String, Double> empPayrollDataByGenderMap;

	@Before
	public void Initializer() {
		empPayrollService = new EmployeePayRollService();
		empPayrollList = empPayrollService.readEmployeePayrollData(IOService.DB_IO);

	}

	@Test
	public void givenEmpPayrollDB_WhenRetrieved_ShouldMatchEmpCount() {

		assertEquals(5, empPayrollList.size());
		System.out.println(empPayrollList);
	}

	@Test
	public void givenNewSalary_WhenUpdated_ShouldSyncWithDB() throws SQLException {
		EmployeePayRollService empPayRollService = new EmployeePayRollService();
		List<EmployeePayRollData> empPayrollList = empPayRollService.readEmployeePayrollData(IOService.DB_IO);
		empPayRollService.updateEmployeeSalary("Suyash", 2500000);
		boolean isSynced = empPayRollService.isEmpPayrollSyncedWithDB("Suyash");
		assertTrue(isSynced);
	}

	@Test
	public void givenDateRange_WhenRetrievedEmployee_ShouldReturnEmpCount() throws EmployeePayrollException {
		EmployeePayRollService empPayRollService = new EmployeePayRollService();
		List<EmployeePayRollData> empPayrollList = empPayRollService.readEmployeePayrollData(IOService.DB_IO);
		LocalDate startDate = LocalDate.of(2018, 01, 01);
		LocalDate enDate = LocalDate.now();
		empPayrollList = empPayRollService.getEmpPayrollDataForDataRange(startDate, enDate);
		assertEquals(4, empPayrollList.size());
	}

	@Test
	public void givenEmployeeDB_WhenRetrievedSum_ShouldReturnSumByGender() throws EmployeePayrollException {
		empPayrollDataByGenderMap = empPayrollService.getSumOfDataGroupedByGender(IOService.DB_IO, "basicPay");
		assertEquals(2500000, empPayrollDataByGenderMap.get("M"), 0.0);
		assertEquals(6000000, empPayrollDataByGenderMap.get("F"), 0.0);
	}

	@Test
	public void givenEmployeeDB_WhenRetrievedAvg_ShouldReturnAvgByGender() throws EmployeePayrollException {
		empPayrollDataByGenderMap = empPayrollService.getAvgOfDataGroupedByGender(IOService.DB_IO, "basicPay");
		assertEquals(2500000, empPayrollDataByGenderMap.get("M"), 0.0);
		assertEquals(3000000, empPayrollDataByGenderMap.get("F"), 0.0);
	}

	@Test
	public void givenEmployeeDB_WhenRetrievedMaxMin_ShouldReturnMaxByGender() throws EmployeePayrollException {
		empPayrollDataByGenderMap = empPayrollService.getMAXOfDataGroupedByGender(IOService.DB_IO, "basicPay");
		assertEquals(2500000, empPayrollDataByGenderMap.get("M"), 0.0);
		assertEquals(3000000, empPayrollDataByGenderMap.get("F"), 0.0);
	}

	@Test
	public void givenEmployeeDB_WhenRetrievedCount_ShouldReturnCountByGender() throws EmployeePayrollException {
		empPayrollDataByGenderMap = empPayrollService.getCountOfDataGroupedByGender(IOService.DB_IO, "id");
		assertEquals(2, empPayrollDataByGenderMap.get("M"), 0.0);
		assertEquals(3, empPayrollDataByGenderMap.get("F"), 0.0);
	}
}
