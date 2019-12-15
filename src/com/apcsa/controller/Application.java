package com.apcsa.controller;

import java.util.Scanner;
import com.apcsa.data.PowerSchool;
import com.apcsa.model.User;

public class Application {

    private Scanner in;
    private User activeUser;

    enum RootAction { PASSWORD, DATABASE, LOGOUT, SHUTDOWN }
    
    /**
     * Creates an instance of the Application class, which is responsible for interacting
     * with the user via the command line interface.
     */

    public Application() {
        this.in = new Scanner(System.in);

        try {
            PowerSchool.initialize(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays an user type-specific menu with which the user
     * navigates and interacts with the application.
     */
    
    public void createAndShowUI() {
    	System.out.println("\nHello, again, " + activeUser.getFirstName() + "!");
    	
    	if (activeUser.isRoot()) {
    		showRootUI();
    	} else {
    		//TODO - add cases for admin, teacher, student, and unknown
    	}
    }
    
    /*
     * Displays an interface for root users.
     */
    
    private void showRootUI() {
    	while (activeUser != null) {
    		switch (getRootMenuSelection()) {
	    		case PASSWORD: resetPassword(); break;
	    		case DATABASE: factoryReset(); break;
	    		case LOGOUT: logout(); break;
	    		case SHUTDOWN: shutdown(); break;
	    		default: System.out.println("\nInvalid selection."); break;
    		}
    	}
    }
    
    /*
     * Retrieves a root user's menu selection.
     * 
     * @return the menu selection
     */
    
    private RootAction getRootMenuSelection() {
    	System.out.println();
    	
    	System.out.println("[1] Reset user password.");
    	System.out.println("[2] Factory reset database.");
    	System.out.println("[3] Logout.");
    	System.out.println("[4] Shutdown.");
    	System.out.println("\n:::");
    	
    	switch (Utils.getInt(in, -1)) {
    		case 1: return RootAction.PASSWORD; 
    		case 2: return RootAction.DATABASE; 
    		case 3: return RootAction.LOGOUT;
    		case 4: return RootAction.SHUTDOWN; 
    		default: return null;
    	}
    }
    
    /**
     * Starts the PowerSchool application.
     */

    public void startup() {
        System.out.println("PowerSchool -- now for students, teachers, and school administrators!");
        
        // continuously prompt for login credentials and attempt to login

        while (true) {
            System.out.print("\nUsername: ");
            String username = in.next();

            System.out.print("Password: ");
            String password = in.next();

            // if login is successful, update generic user to administrator, teacher, or student         
            
            if (login(username, password)) {
                activeUser = activeUser.isAdministrator()
                    ? PowerSchool.getAdministrator(activeUser) : activeUser.isTeacher()
                    ? PowerSchool.getTeacher(activeUser) : activeUser.isStudent()
                    ? PowerSchool.getStudent(activeUser) : activeUser.isRoot()
                    ? activeUser : null;

                if (isFirstLogin() && !activeUser.isRoot()) {
                    // first-time users need to change their passwords from the default provided
                }

                // create and show the user interface
                //
                // remember, the interface will be difference depending on the type
                // of user that is logged in (root, administrator, teacher, student)
                createAndShowUI();
            } else {
                System.out.println("\nInvalid username and/or password.");
            }
        } catch (Exception e) {
        	shutdown(e);
        }
    }

    /**
     * Logs in with the provided credentials.
     *
     * @param username the username for the requested account
     * @param password the password for the requested account
     * @return true if the credentials were valid; false otherwise
     */

    public boolean login(String username, String password) {
        activeUser = PowerSchool.login(username, password);

        return activeUser != null;
    }

    /**
     * Determines whether or not the user has logged in before.
     *
     * @return true if the user has never logged in; false otherwise
     */

    public boolean isFirstLogin() {
        return activeUser.getLastLogin().equals("0000-00-00 00:00:00.000");
    }

    /////// MAIN METHOD ///////////////////////////////////////////////////////////////////

    /*
     * Starts the PowerSchool application.
     *
     * @param args unused command line argument list
     */

    public static void main(String[] args) {
        Application app = new Application();

        app.startup();
    }
}