package com.apcsa.model;

import com.apcsa.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Student extends User {

    private int studentId;
    private int classRank;
    private int gradeLevel;
    private int graduationYear;
    private double gpa;
    private String firstName;
    private String lastName;
    private String fullName = firstName + lastName;
    
    /**
     * Creates an instance of the Student class.
     * 
     * @param user
     * @param rs
     * @throws SQLException
     */
    
    public Student(User user, ResultSet rs) throws SQLException {
    	super(user);
    	
    	this.studentId = rs.getInt("student_id");
    	this.classRank = rs.getInt("class_rank");
    	this.gradeLevel = rs.getInt("grade_level");
    	this.graduationYear = rs.getInt("graduation");
    	this.gpa = rs.getDouble("gpa");
    	this.firstName = rs.getString("first_name");
    	this.lastName = rs.getString("last_name");
    }
    
}