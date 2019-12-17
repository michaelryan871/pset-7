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
    
    /*
     * Displays all students.
     */
    
    private void viewStudents() {
    	//
    	// get a list of students
    	// 
    	// if list of students is empty...
    	//		print a message saying exactly that
    	// otherwise...
    	//		print the list of students by name and graduation year
    	//
    }
    
    /*
     * Displays all students by grade. 
     */
    
    private void viewStudentsByGrade() {
    	//
    	// get list of students by grade
    	//		to do this, you'll need to prompt the user to choose a grade level (more on this later)
    	//
    	// if the list of students is empty...
    	//		print a message saying exactly that
    	// otherwise...
    	//		print the list of students by name and class rank
    	//
    }
    
    /*
     * Displays all students by course.
     */
    
    private void viewStudentsByCourse() {
    	//
    	// get a list of students by course
    	//		to do this, you'll need to prompt the user to choose a course (more on this later)
    	//
    	// if the list of students is empty...
    	//		print a message saying exactly that
    	// otherwise...
    	//		print the list of students by name and grade point average
    	//
    }
    
    /*
     * Allows a user to change his or her password.
     * 
     * @param firstLogin true if the user has never logged in; false otherwise
     */
    
    private void changePassword(boolean firstLogin) {
    	// if it isn't the user's first login...
    	//		ask the user for his or her current password
    	//
    	// ask all users (first login or not) to enter a new password
    	//
    	// change the password (this will require a call to the database)
    	//		this requires three pieces of information: the username, the old password, and the new password
    	//		the old password will either be something the use entered (if it isn't his or her first login) or 
    	//		it'll be the same as their username 
    }
    
    /*
     * Retrieves the user's department selection.
     * 
     * @return the selected department
     */
    
    private int getDepartmentSelection() {
    	int selection = -1; 
    	System.out.println("\nChoose a department.");
    	
    	while (selection < 1 || selection > 6) {
    		System.out.println("\n[1] Computer Science.");
    		System.out.println("[2] English.");
    		System.out.println("[3] History.");
    		System.out.println("[4] Mathematics.");
    		System.out.println("[5] Physical Education.");
    		System.out.println("[6] Science.");
    		
    		selection = Utils.getInt(in, -1);
    	}
    	
    	return selection; 
    }
   
    /*
     * Retrieves a user's grade selection.
     * 
     * @return the selected grade
     */
    
    private int getGradeSelecion() {
    	int selection = -1; 
    	System.out.println("\nChoose a grade level.");
    	
    	while (selection < 1 || selection > 4) {
    		System.out.println("\n[1] Freshman.");
    		System.out.println("[2] Sophomore.");
    		System.out.println("[3] Junior.");
    		System.out.println("[4] Senior.");
    		System.out.println("\n:::");
    				
    		selection = Utils.getInt(in, -1);
    	}
    		
    	return selection + 8; 	// +8 because you want a value between 9 and 12 
    }
    
    
    /*
     * Retrieves a user's course selection
     * 
     * @return the selected course
     */
    
    private String getCourseSelection() throws SQLException {
    	boolean valid = false; 
    	String courseNo = null; 
    	
    	while (!valid) {
    		System.out.print("\nCourse No.: ");
    		courseNo = in.next();
    		
    		if (/* is a valid course number */) {
    			valid = true; 
    		} else {
    			System.out.println("\nCourse not found.");
    		}
    	}
    	
    	return courseNo;
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