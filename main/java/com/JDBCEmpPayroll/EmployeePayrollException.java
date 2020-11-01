public class EmployeePayrollException extends Exception {
	public enum ExceptionType{
		NO_DATA_FOUND,WRONG_SQL,WRONG_NAME
	}
	private ExceptionType exeptionType;
	private String message;
	
	public EmployeePayrollException(String message,ExceptionType exception) {
		this.exeptionType=exception;
		this.message=message;
	}
	public ExceptionType getExceptionType() {
		return exeptionType;
	}
	public String getMessage() {
		return message;
	}
}
