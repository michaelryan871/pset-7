package com.apcsa.model;

import com.apcsa.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList; 

public class Administrator extends User {

    private int administratorId;
    private String firstName;
    private String lastName;
    private String jobTitle;

    enum AdministratorAction { FACULTY, DEPARTMENT, STUDENTS, GRADE, COURSE, PASSWORD, LOGOUT }
    
    /*
     * Displays an interface for root users.
     */
    
    private void showAdministratorUI() {
    	while (activeUser != null) {
    		switch (getAdministratorMenuSelection()) {
    		case FACULTY: viewFaculty(); break; 
    		case DEPARTMENT: viewFacultyByDepartment(); break; 
    		case STUDENTS: viewStudents(); break; 
    		case GRADE: viewStudentsByGrade(); break;
    		case COURSE: viewStudentsByCourse(); break;
    		case PASSWORD: changePassword(false); break;
    		case LOGOUT: logout(); break;
    		default: System.out.println("\nInvalid selection."); break;
    		}
    	}
    }
    
    /*
     * Retrieves a root user's menu selection.
     * 
     * @return the menu selection 
     */
    
    private AdministratorAction getAdministratorMenuSelection() {
    	System.out.println(); 
    	
    	System.out.println("[1] View faculty.");
    	System.out.println("[2] View faculty by department.");
    	System.out.println("[3] View student enrollment.");
    	System.out.println("[4] View student enrollment by grade.");
    	System.out.println("[5] View student enrollment by course.");
    	System.out.println("[6] Change password.");
    	System.out.println("[7] Logout.");
    	System.out.print("\n::: ");
    	
    	switch (Utils.getInt(in, -1)) {
	    	case 1: return AdministratorAction.FACULTY;
	    	case 2: return AdministratorAction.DEPARTMENT; 
	    	case 3: return AdministratorAction.STUDENTS; 
	    	case 4: return AdministratorAction.GRADE; 
	    	case 5: return AdministratorAction.COURSE; 
	    	case 6: return AdministratorAction.PASSWORD; 
	    	case 7: return AdministratorAction.LOGOUT; 
    	}
    	
    	return null; 
    }
    
    /*
     * Displays all faculty members. 
     */
    
    private void viewFaculty() {
    	ArrayList<Teacher> teachers = PowerSchool.getTeachers(); 
    	
    	if (teachers.isEmpty()) {
    		System.out.println("\nNo teachers to display.");
    	} else {
    		System.out.println();
    		
    		int i = 1; 
    		for (Teacher teacher : teachers) {
    			System.out.println(i++ + ". " + teacher.getName() + " / " + teacher.getDepartment());
    		}
    	}
    }
    
    /*
     * Displays all faculty members by department. 
     */
    
    private void viewFacultyByDepartment() {
    	//
    	// get a list of teachers by deparment (this requires a database call)
    	//		to do this, you'll need to prompt the user to choose a department (more on this later)
    	//
    	// if list of teachers is empty...
    	//		print a message saying exactly that
    	// otherwise... 
    	//		print the list of teachers by name an department (just like last time)
    	//
    }
    
    
    
    /** 
     * Creates an instance of the Administrator class. 
     * 
     * @param user
     * @param rs 
     * @throws SQLException
     */
    
    public Administrator(User user, ResultSet rs) throws SQLException {
    	super(user);
    	
    	this.administratorId = rs.getInt("administrator_id");
    	this.firstName = rs.getString("first_name");
    	this.lastName = rs.getString("last_name");
    	this.jobTitle = rs.getString("job_title");
    	
    }
    
}