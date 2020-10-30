import java.util.*;

public class EmployeePayrollService {
	public enum IOService {
		CONSOLE_IO, FILE_IO, DB_IO, REST_IO
	}
	
	private List<EmployeePayrollData> employeePayrollList;

	public EmployeePayrollService() {
	}
	
	public EmployeePayrollService(List<EmployeePayrollData> employeePayrollList) {
		this.employeePayrollList = employeePayrollList;
	}
	
	public static void main(String[] args) {
		System.out.println("Welcome to the Employee Payroll Service.");
		ArrayList<EmployeePayrollData> employeePayrollList = new ArrayList<>();
		EmployeePayrollService employeePayrollService = new EmployeePayrollService(employeePayrollList);
		Scanner consoleInputReader = new Scanner(System.in);
		employeePayrollService.readEmployeePayrollData(consoleInputReader);
		employeePayrollService.writeEmployeePayrollData(IOService.CONSOLE_IO);
	}
	
	private void readEmployeePayrollData(Scanner consoleInputReader) {
		System.out.println("Enter Employee ID: ");
		int id = consoleInputReader.nextInt();
		System.out.println("Enter Employee Name: ");
		String name = consoleInputReader.next();
		System.out.println("Enter Employee Salary: ");
		Double salary = consoleInputReader.nextDouble();
		employeePayrollList.add(new EmployeePayrollData(id, name, salary));
	}
	
	public void writeEmployeePayrollData(IOService ioService) {
		if (ioService.equals(IOService.CONSOLE_IO))
			System.out.println("\nWriting Employee payroll data on Console\n" + employeePayrollList);
		else if (ioService.equals(IOService.FILE_IO))
			new EmployeePayrollFileIOService().writeData(employeePayrollList);
	}
	
	public void printData(IOService ioService) {
		if(ioService.equals(IOService.FILE_IO))
			new EmployeePayrollFileIOService().printData();
	}
	
	public long countEntries(IOService ioService) {
		if(ioService.equals(IOService.FILE_IO))
			return new EmployeePayrollFileIOService().countEntries();
		return 0;
	}
	
	public List<EmployeePayrollData> readPayrollData(IOService ioService) {
		if (ioService.equals(IOService.FILE_IO))
			this.employeePayrollList = new EmployeePayrollFileIOService().readData();
		else if(ioService.equals(IOService.DB_IO))
			this.employeePayrollList = new EmployeePayrollDBService().readData();
		return employeePayrollList;
	}
}
