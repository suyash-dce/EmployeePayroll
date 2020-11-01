import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class EmployeePayRollData {
	public int id;
	public String name;
	public double salary;
	public LocalDate startDate;
	public double basicPay;
	public char gender;
	private int company_id;
	private List<String> departmentName;

	public EmployeePayRollData(int id, String name, double salary) {
		this.id = id;
		this.name = name;
		this.salary = salary;
	}

	public EmployeePayRollData(int id, String name, double basicPay, Date date) {
		this.id = id;
		this.name = name;
		this.basicPay = basicPay;
		this.startDate = date.toLocalDate();
	}

	public EmployeePayRollData(int id, String name, double basicPay, Date date, char gender, int company_id,
			List<String> departmentName) {
		this.id = id;
		this.name = name;
		this.basicPay = basicPay;
		this.startDate = date.toLocalDate();
		this.company_id = company_id;
		this.departmentName = departmentName;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "EmployeePayRollData [id=" + id + ", name=" + name + ", salary=" + salary + ", startDate=" + startDate
				+ ", basicPay=" + basicPay + ", gender=" + gender + ", company_id=" + company_id + ", departmentName="
				+ departmentName + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmployeePayRollData other = (EmployeePayRollData) obj;
		if (Double.doubleToLongBits(basic_pay) != Double.doubleToLongBits(other.basic_pay))
			return false;
		if (company_id != other.company_id)
			return false;
		if (departmentName == null) {
			if (other.departmentName != null)
				return false;
		} else if (!departmentName.equals(other.departmentName))
			return false;
		if (gender != other.gender)
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(salary) != Double.doubleToLongBits(other.salary))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}

	public int getcompany_id() {
		return company_id;
	}

	public void setcompany_id(int company_id) {
		this.company_id = company_id;
	}

	public List<String> getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(List<String> departmentName) {
		this.departmentName = departmentName;
	}

	public int getId() {
		return id;
	}

	public double getSalary() {
		return salary;
	}

	public double getBasic_pay() {
		return basicPay;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public void setBasic_pay(double basicPay) {
		this.basicPay = basicPay;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}
}
