package pe2;

import java.util.*;

public class Hospital {
	//creating objects
	Person person = new Person();
	Patient patient = new Patient();
	Physician physician = new Physician();
	Volunteer volunteer = new Volunteer();
	//ArrayList variables
	List<Patient> patientInformation = new ArrayList<Patient>();
	List<Physician> physInformation = new ArrayList<Physician>();
	List<Volunteer> volunteerInformation = new ArrayList<Volunteer>();
	List<PhysicianAdministrator> physAdmin = new ArrayList<PhysicianAdministrator>();
	//2 public static variables for patient 
	public static int employeeID = 100;
    public static int patientID = 1000;
    //private variable for encapsulation
    private Director director;
    //Constructor
	public Hospital(Director director) {
		this.director = director;
	}
	//getter
	public Director getHospDirector() {
		return director;
	}
	//setter
	public void setHospDirector(Director director) {
		this.director = director;
	}
	/**
	 * This method adds the patient information to the hospital patient record and assigns a physician to every newly admitted patient, and then 
	   the patient will be added to the corresponding physician record.
	 * @param patient is the patient being admitted.
	 * @return the boolean value is true or false. If it is the same patient, return false. If the patient was admitted to a physician, return true.
	 */
	public boolean admitPatient(Patient patient) {
		for (Patient p : patientInformation) {
			if (p.equals(patient)) {
                return false;
            }
        }
        for (Physician phys : physInformation) {
            if(phys.hasMaximumpatient()) {
                continue;
            }
            phys.assignPatient(patient);
            patient.setAssignedPhysician(phys);
            patientInformation.add(patient);
            return true;
        }
        throw new NoSpaceException();
    }
	/**
	 * This method adds the patient information to the hospital patient record and assigns a physician to every newly admitted patient, and then 
	   the patient will be added to the corresponding physician record.
	 * @param admin is the admin being added.
	 * @return the boolean value is true or false. If the admin was successfully added and the the physAdmin size is smaller than 3, return true.
	   If not, return false.
	 */
	public boolean addAdministrator(PhysicianAdministrator admin) {
		if (physAdmin.size() < 3){
			physAdmin.add(admin);
			return true;
        }
		return false;
	}
	/**
	 * When the hospital hires a new physician, this method adds the physician information to the hospital physician record.
	   The newly hired physician is assigned to the designated physician 
	   administrator, and then the physician will be added to the corresponding 
       physician administrator record with the same specialty.
	 * @param physician is the physician being hired.
	 * @return the boolean value is true or false. If the physician was successfully hired, return true. Return false
	   if it's the same physician or if the physician was not hired.
	 */
	public boolean hirePhysician(Physician physician) {
		for (Physician p : physInformation){
            if (p.equals(physician)){
                return false;
            }
        }
        if (physInformation.size() < 70) {
            physInformation.add(physician);
            for (PhysicianAdministrator pa : physAdmin) {
                if (pa.getAdminSpecialtyType().equals(physician.getSpecialty())) {
                    physician.setAdmin(pa);
                    pa.assignPhysician(physician);
                }
            }
            return true;
        }
        else {
            return false;
        }
	}
	/**
	 * This methods extracts and returns all the physician information 
	   stored in the hospital physician record as a sorted list of physicians according to the 
       physician's full name.
	 * @return return the sorted list of physician information
	 */
	public List<Physician> extractAllPhysicianDetails() {
		Collections.sort(physInformation);
		return physInformation;
	}
	/**
	 * This method adds the patient information to the hospital patient record and assigns a physician to every newly admitted patient, and then 
	   the patient will be added to the corresponding physician record.
	 * @param patient is the patient being admitted.
	 * @return the boolean value is true or false. If it is the same patient, return false. If the patient was admitted to a physician, return true.
	 */
	public void resignPhysician(Physician physician) {
		for (Physician i : physInformation){
            if (i.equals(physician)){
                for (PhysicianAdministrator j : physAdmin){
                    if (j.getAdminSpecialtyType().equals(i.getSpecialty())){
                        if(j.extractPhysician().size() == 1){
                            throw new NoSpecialtyException();
                        }
                    }
                }
                for (PhysicianAdministrator j : physAdmin){
                    if (j.getAdminSpecialtyType().equals(i.getSpecialty())){
                        List<Patient> curPatients = i.extractPatientDetail(); 
                        List<Volunteer> curVolunteers = i.extractValunterDetail();
                        physInformation.remove(i);
                        for (Patient k : curPatients){
                            for (Physician l : physInformation){
                                if (!l.hasMaximumpatient()){
                                    l.assignPatient(k);
                                    k.setAssignedPhysician(l);
                                    break;
                                }
                            }
                        }
                        for (Volunteer k : curVolunteers){
                            for (Physician l : physInformation){
                                if (!l.hasMaxVolunteers()){
                                    l.assignVolunteer(k);
                                    k.setAssignedPhysician(l);
                                    break;
                                }
                            }
                        }
                        j.resignPhysician(i);
                        return;
                    }
                }
            }
        }
	}
	/**
	 * This method extracts and returns all the patient information 
	   stored in the hospital patient record as a sorted list of patients according to the 
	   patient's full name.
	 * @return return the sorted list of patient information
	 */
	public List<Patient> extractAllPatientDetails() {
		Collections.sort(patientInformation);
		return patientInformation;
	}
	/**
	 * when a hospital discharges a patient (patient discharged from the 
	   hospital when patient treatment is completed), This method to clears/removes the patient 
 	   information from the hospital patient record.
	 * @param patient is the patient being discharged.
	 * @return the boolean value is true or false. If the patient is successfully discharged, return true.
	   If not, return false.
	 */
	public boolean dischargePatient(Patient patient) {
		for (Patient p : patientInformation) {
			if (patient.equals(p)) {
				for (Physician p2 : physInformation) {
					if (patient.getAssignedPhysician() == p2) {
						patientInformation.remove(patient);
						p2.removePatient(patient);
						return true;
					}
				}
			}
		}
		return false;
	}
	/**
	 * When the hospital hires a new volunteer, this method stores the volunteer 
	   information in the hospital volunteer record and then assigns the volunteer to a 
       physician who has not exceeded the volunteer limit yet.
	 * @param volunteer is the volunteer being hired.
	 * @return the boolean value is true or false. If it is the same volunteer, return false. If the volunteer was successfully hired, return true.
	 */
	public boolean hireVolunteer(Volunteer volunteer) {
        for (Volunteer v : volunteerInformation){
            if (v.equals(volunteer)){
                return false;
            }
        }
        for (Physician p : physInformation){
            if(p.hasMaxVolunteers()) {
                continue;
            }
            p.assignVolunteer(volunteer);
            volunteer.setAssignedPhysician(p);
            volunteerInformation.add(volunteer);    
            return true;
        }
        throw new NoSpaceException();
    }
	/**
	 * This method extracts and returns all the volunteer information 
	   assigned to a particular physician as a list of volunteers.
	 * @return return the sorted list of volunteer information
	 */
	public List<Volunteer> extractAllVolunteerDetails() {
		Collections.sort(volunteerInformation);
		return volunteerInformation;
	}
	/**
	 * When a volunteer is resigning from the hospital, this method
	   clears/removes the volunteer information from the hospital volunteer record.
	 * @param volunteer is the volunteer being resigned.
	 * @return the boolean value is true or false. If the volunteer has successfully resigned return true.
	   If not, return false.
	 */
	public boolean resignVolunteer(Volunteer volunteer) {
		for (Volunteer i : volunteerInformation) {
			if (i.equals(volunteer)) {
				if (i.getAssignedPhysician().extractValunterDetail().size() == 1) {
					throw new NoVolunteersException();
	            }
				else {
					i.getAssignedPhysician().extractValunterDetail().remove(i);
					volunteerInformation.remove(i);
					return true;
	            }
	        }
		}
		return false;
	}
}

class Person {
	// private variables for encapsulation
	private String firstName;
	private String lastName;
	private int age;
	private String gender;
	private String address;
	
	public Person() {
		this.firstName = "";
		this.lastName = "";
		this.gender = "";
		this.address = "";
	}
	//Constructor
	public Person(String firstName, String lastName, int age, String gender, String address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
		this.address = address;
	}
	//Getter 
	public String getFirstName() {
		return firstName;
	}
	//setter
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	//getter
	public String getLastName() {
		return lastName;
	}
	//setter
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	//getter
	public int getAge() {
		return age;
	}
	//setter
	public void setAge(int age) {
		this.age = age;
	}
	//getter
	public String getGender() {
		return gender;
	}
	//setter
	public void setGender(String gender) {
		this.gender = gender;
	}
	//getter
	public String getAddress() {
		return address;
	}
	//setter
	public void setAddress(String address) {
		this.address = address;
	}
	//toString method
	public String toString() {
		return "";
	}
	//getter
	public String getName() {
		return this.firstName + ", " + this.lastName;
	}
}
class Patient extends Person implements Comparable<Patient>{
	// private Variables for encapsulation
	private Physician physician;
	private int patientID;
	
	/**
	 * @param p variable is for the second name.
	 * @return the comparison of name1 and name2.
	 */
	public int compareTo(Patient p) {
		String name1 = this.getFirstName() + this.getLastName();
		String name2 = p.getFirstName() + p.getLastName();
		int compare = name1.compareTo(name2);
		return compare;
	}
	//Empty constructor
	public Patient() {}
	//Constructor
	public Patient(String firstName, String lastName, int age, String gender, String address) {
		super(firstName, lastName, age, gender, address);
		this.patientID = Hospital.patientID;
		Hospital.patientID++;;
	}
	//Overloaded constructor
	public Patient(String firstName, String lastName, int age, String gender, int patientID, String address) {
		super(firstName, lastName, age, gender, address);
		this.patientID = patientID;
	}
	//getter
	public int getPatientID() {
		return patientID;
	}
	//setter
	public void setPatientID(int patientID) {
		this.patientID = patientID;
	}
	//toString method
	public String toString() {
		return "Patient ["+getPatientID()+", ["+getFirstName()+", "+getLastName()+", "+getAge()+", "+getGender()+", "+getAddress()+"]]";
	}
	//getter
	public Physician getAssignedPhysician() {
		return physician;
	}
	//setter
	public void setAssignedPhysician(Physician physician) {
		this.physician = physician;
	}
	/**
	 * @param 
	 * @return the boolean value is true or false. If the patient record has been cleared return true. 
	 */
	public boolean clearPatientRecord() {
		if (getAssignedPhysician() != null) {
			return false;
		}
		return true;
	}
}
class Employee extends Person {
	//Variable
	private int employeeID;
	
	public Employee() {}
	//Constructor
	public Employee(String firstName, String lastName, int age, String gender, String address) {
		super(firstName, lastName, age, gender, address);
		this.employeeID = Hospital.employeeID;
		Hospital.employeeID++;
	}
	//Overloaded constructor
	public Employee(String firstName, String lastName, int age, String gender, int employeeID, String address) {
		super(firstName, lastName, age, gender, address);
		this.employeeID = employeeID;
	}
	//getter
	public int getEmployeeID() {
		return employeeID;
	}
	//setter
	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}
	//toString method.
	public String toString() {
		return "Employee [["+getEmployeeID()+",["+getFirstName()+", "+getLastName()+", "+getAge()+", "+getGender()+", "+getAddress()+"]]]";
		
	}
}
class Volunteer extends Employee implements Comparable<Volunteer> {
	//Variable
	private Physician physician;
	/**
	 * @param v variable is for the second name of the volunteer.
	 * @return the comparison of name1 and name2.
	 */
	public int compareTo(Volunteer v) {
		String name1 = this.getFirstName() + this.getLastName();
		String name2 = v.getFirstName() + v.getLastName();
		int compare = name1.compareTo(name2);
		return compare;
	}
	//Empty constructor
	public Volunteer() {}
	//Constructor
	public Volunteer(String firstName, String lastName, int age, String gender, String address) {
		super(firstName, lastName, age, gender, address);
	}
	//Overloaded constructor
	public Volunteer(String firstName, String lastName, int age, String gender, int employeeID, String address) {
		super(firstName, lastName, age, gender, employeeID, address);
	}
	//toString
	public String toString() {
		return "Volunteer [["+getEmployeeID()+",["+getFirstName()+", "+getLastName()+", "+getAge()+", "+getGender()+", "+getAddress()+"]]]";
	}
	//getter
	public Physician getAssignedPhysician() {
		return physician;
	}
	//setter
	public void setAssignedPhysician(Physician physician) {
		this.physician = physician;
	}
}
class SalariedEmployee extends Employee {
	//Variable
	private double salary;
	//Empty constructor
	public SalariedEmployee() {}
	//Constructor
	public SalariedEmployee(String firstName, String lastName, int age, String gender, String address) {
		super(firstName, lastName, age, gender, address);
	}
	//Constructor 
	public SalariedEmployee(String firstName, String lastName, int age, String gender, int employeeID, String address, double salary) {
		super(firstName, lastName, age, gender, employeeID, address);
		this.salary = salary;
	}
	//getter
	public double getSalary() {
		return salary;
	}
	//setter
	public void setSalary(int salary) {
		this.salary = salary;
	}
	//toString
	public String toString() {
		return "SalariedEmployee [[["+getEmployeeID()+",["+getFirstName()+", "+getLastName()+", "+getAge()+", "+getGender()+", "+getAddress()+"]], "+getSalary()+"]]";
	}
	
}
class Administrator extends SalariedEmployee {
	//constructor
	public Administrator(String firstName, String lastName, int age, String gender, String address) {
		super(firstName, lastName, age, gender, address);
	}
	//overloaded constructor
	public Administrator(String firstName, String lastName, int age, String gender, int employeeID, String address, int salary) {
		super(firstName, lastName, age, gender, employeeID, address, salary);
	}
	//toString
	public String toString() {
		return "Administrator [[["+getEmployeeID()+",["+getFirstName()+", "+getLastName()+", "+getAge()+", "+getGender()+", "+getAddress()+"]], "+getSalary()+"]]";
	}
}
class Physician extends SalariedEmployee implements Comparable<Physician> {
	//privatized variables
	private String specialty;
	private PhysicianAdministrator admin;
	private List<Patient> patients = new ArrayList<Patient>();
	private List<Volunteer> volunteers = new ArrayList<Volunteer>();
	/**
	 * @param p variable is for the second name of the physician.
	 * @return the comparison of name1 and name2.
	 */
	public int compareTo(Physician p) {
		String name1 = this.getFirstName() + this.getLastName();
		String name2 = p.getFirstName() + p.getLastName();
		int compare = name1.compareTo(name2);
		return compare;
	}
	//Empty constructor
	public Physician() {}
	//Constructor
	public Physician(String firstName, String lastName, int age, String gender, String address) {
		super(firstName, lastName, age, gender, address);
	}
	//Overloaded constructor
	public Physician(String firstName, String lastName, int age, String gender, int employeeID, String address, int salary) {
		super(firstName, lastName, age, gender, employeeID, address, salary);
	}
	//toString
	public String toString() {
		return "Physician [[["+getEmployeeID()+",["+getFirstName()+", "+getLastName()+", "+getAge()+", "+getGender()+", "+getAddress()+"]], "+getSalary()+"]]";
	}
	//getter
	public String getSpecialty() {
		return specialty;
	}
	/**
	 * 
	 * @param specialty is used to determine which physician is in which specialty.
	 */
	public void setSpecialty(String specialty) {
		if ((specialty != "Immunology") && (specialty != "Dermatology") && (specialty != "Neurology")){
			//throw IllegalArgumentException if and only if specialty is none of the 3 above.
            throw new IllegalArgumentException();
        }
		else {
            this.specialty = specialty;
        }
	}
	//extract the patient detail
	public List<Patient> extractPatientDetail() {
		return patients;
	}
	//extract the volunteer detail
	public List<Volunteer> extractValunterDetail() {
		Collections.sort(volunteers);
		return volunteers;
	}
	public boolean assignVolunteer(Employee employee) {
        if (volunteers.size() >= 5){
            return false;
        }
        //type casting
        volunteers.add((Volunteer)employee);
        return true;
    }
	public void assignPatient(Patient patient) {
		patients.add(patient);
	}
	public void removePatient(Patient patient) {
		patients.remove(patient);
	}
	/**
	 * 
	 * @return true if the size of volunteers is equal to 5.
	 */
	public boolean hasMaxVolunteers() {
		if (volunteers.size() == 5) {
			return true;
		}
		else {
			return false;
		}
	}
	/**
	 * 
	 * @return true is the patients size is equal to 8.
	 */
	public boolean hasMaximumpatient() {
		if (patients.size() == 8) {
			return true;
		}
		else {
			return false;
		}
	}
	//setter
	public void setAdmin(PhysicianAdministrator admin){
        this.admin = admin;
    }
	//getter
	public PhysicianAdministrator getAdmin() {
		return admin;
	}
	//equals method
	public boolean equals(Physician p){
        return (this.getEmployeeID() == p.getEmployeeID());
    }
}
class PhysicianAdministrator extends Administrator {
	//private variables
	private String specialty;
	private ArrayList<Physician> physicians = new ArrayList<Physician>();
	//constructor
	public PhysicianAdministrator(String firstName, String lastName, int age, String gender, String address) {
		super(firstName, lastName, age, gender, address);
	}
	//overloaded constructor
	public PhysicianAdministrator(String firstName, String lastName, int age, String gender, int employeeID, String address, int salary) {
		super(firstName, lastName, age, gender, employeeID, address, salary);
	}
	//toString
	public String toString() {
		return "PhysicianAdministrator [[["+getEmployeeID()+",["+getFirstName()+", "+getLastName()+", "+getAge()+", "+getGender()+", "+getAddress()+"]], "+getSalary()+"], "+getAdminSpecialtyType()+"]";
	}
	/**
	 * 
	 * @param p is the physician being assigned to physician admin.
	 * @return return true if it was succesfully assigned.
	 */
	public boolean assignPhysician(Physician p) {
        if (physicians.size() < 25){
            physicians.add(p);
            return true;
        }
        return false;
    }
	//resigning a physician by removing physician p.
	public void resignPhysician(Physician p) {
        physicians.remove(p);
    }
	//getter 
	public String getAdminSpecialtyType() {
		return specialty;
	}
	/**
	 * 
	 * @param specialty is the type of specialty in what the admin specifies in.
	 */
	public void setAdminSpecialtyType(String specialty) {
		if ((specialty != "Immunology") && (specialty != "Dermatology") && (specialty != "Neurology")){
			//throw new IllegalArgumentException if specialty is none of the 3.
            throw new IllegalArgumentException();
        }
		else {
            this.specialty = specialty;
        }
	}
	//return the physicians
	public List<Physician> extractPhysician() {
		return physicians;
	}
}
class Director extends Administrator {
	
	List<PhysicianAdministrator>physicianAdministrators = new ArrayList<PhysicianAdministrator>();
	//Constructor 
	public Director(String firstName, String lastName, int age, String gender, String address) {
		super(firstName, lastName, age, gender, address);
	}
	//Overloaded constructor
	public Director(String firstName, String lastName, int age, String gender, int employeeID, String address, int salary) {
		super(firstName, lastName, age, gender, employeeID, address, salary);
	}
	//toString
	public String toString() {
		return "Director [[["+getEmployeeID()+",["+getFirstName()+", "+getLastName()+", "+getAge()+", "+getGender()+", "+getAddress()+"]], "+getSalary()+"]]";
	}
	/**
	 * 
	 * @param admin is the physician being admitted to one of the 3 Physician administrators.
	 * @return return true if the physician administrator was succesfully assigned.
	 */
	public boolean assignAdministrator(PhysicianAdministrator admin) {
		if (physicianAdministrators.size() < 3) {
            physicianAdministrators.add(admin);
            return true;
        }
		else {
            return false;
        }
	}
	//extract the physician admins
	public List<PhysicianAdministrator> extractPhysicianAdmins() {
		return physicianAdministrators;
	}
}
//Exception
class IllegalArgumentException extends RuntimeException {
	public IllegalArgumentException() {
		super("");
  }
}
//Exception
class NoSpecialtyException extends RuntimeException {
	public NoSpecialtyException() {
		super("");
  }
}
//Exception
class NoSpaceException extends RuntimeException {
	public NoSpaceException() {
		super("");
	}
}
//Exception
class NoVolunteersException extends RuntimeException {
	public NoVolunteersException() {
		super("");
	}
}