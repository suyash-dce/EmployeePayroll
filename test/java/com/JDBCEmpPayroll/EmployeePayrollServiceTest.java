import com.capgemini.employeepayroll.EmployeePayrollService.IOService;
import org.junit.Assert;
import org.junit.Test;
import java.util.*;

public class EmployeePayrollServiceTest {
	
	@Test
	public void retrieveEmployeePayrollTest() {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		List<EmployeePayrollData> employeePayrollData = employeePayrollService.readPayrollData(IOService.DB_IO);
		Assert.assertEquals(3, employeePayrollData.size());
	}
}
