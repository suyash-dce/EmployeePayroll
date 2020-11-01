import java.time.LocalDate;

public class EmployeePayrollData {
	public int id;
	public String name;
	public double salary;
	public LocalDate startDate;
	public double basic_pay;

	public EmployeePayRollData(int id, String name, double salary) {
		this.id = id;
		this.name = name;
		this.salary = salary;
	}

	public EmployeePayRollData(int id, String name, double basic_pay, Date date) {
		this.id = id;
		this.name = name;
		this.basic_pay = basic_pay;
		this.startDate = date.toLocalDate();
	}

	public String toString() {
		return "id:" + id + " name: " + name + " Salary: " + salary;
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

	public String getName() {
		return name;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

}
