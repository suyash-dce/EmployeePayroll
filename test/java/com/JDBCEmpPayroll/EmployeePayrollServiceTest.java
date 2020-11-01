import com.capgemini.employeepayroll.EmployeePayrollService.IOService;
import org.junit.Assert;
import org.junit.Test;
import java.util.*;

public class EmployeePayrollServiceTest {
	EmployeePayRollService empPayrollService;
	List<EmployeePayRollData> empPayrollList;

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
		empPayRollService.updateEmployeeSalary("Suyash", 3000001.0);
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
}
