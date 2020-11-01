import com.capgemini.employeepayroll.EmployeePayrollService.IOService;
import org.junit.Assert;
import org.junit.Test;
import java.util.*;

public class EmployeePayrollServiceTest {
	
	@Test
	public void givenEmpPayrollDB_WhenRetrieved_ShouldMatchEmpCount() {
		EmployeePayRollService empPayRollService = new EmployeePayRollService();
		List<EmployeePayRollData> empPayrollList = empPayRollService.readEmployeePayrollData(IOService.DB_IO);
		assertEquals(5, empPayrollList.size());
		System.out.println(empPayrollList);
	}
	
	@Test
	public void givenNewSalary_WhenUpdated_ShouldSyncWithDB() {
		EmployeePayRollService empPayRollService = new EmployeePayRollService();
		List<EmployeePayRollData> empPayrollList = empPayRollService.readEmployeePayrollData(IOService.DB_IO);
		empPayRollService.updateEmployeeSalary("Suyash", 3000001.0);
		boolean isSynced = empPayRollService.isEmpPayrollSyncedWithDB("Suyash");
		assertTrue(isSynced);
	}
}
