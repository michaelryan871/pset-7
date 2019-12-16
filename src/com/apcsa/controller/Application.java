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
    
    /*
     * Shuts down the application after encountering an error.
     * 
     * @param e the error that initialized the shutdown sequence
     */
    
    private void shutdown (Exception e) {
    	if (in != null) {
    		in.close();
    	}
    	
    	System.out.println("Encountered unrecoverable error. Shuting down...\n");
    	System.out.println(e.getMessage());
    	
    	System.out.println("\nGoodbye!");
    	System.exit(0);
    }
    
    /*
     * Releases all resources and kills the application.
     */
    
    private void shutdown() {
    	System.out.println();
    	
    	if (Utils.confirm(in, "Are you sure? (y/n) ")) {
    		if (in != null) {
    			in.close();
    		}
    		
    		System.out.println("\nGoodbye!");
    		System.exit(0);
    		
    	}
    }
    
    /*
     * Allows a root user to reset another user's password.
     */
    
    private void resetPassword() {
    	//
    	// prompt root user to enter username of user whose password needs to be reset
    	//
    	// ask root user to confirm intent to reset the password for that username
    	//
    	// if confirmed...
    	//      call database method to reset password for username
    	//		print success message
    	//
    }
    
    /*
     * Resets the database to its factory settings.
     */
    
    private void factoryReset() {
    	//
    	// ask root user to confirm intent to reset the database
        // if confirmed...
    	//		call database initialize method with parameter of true
    	//		print success message
    	//
    }
    
    /*
     * Logs out of the application.
     */
    
    private void logout() {
    	// 
    	// ask user to confirm intent to logout
    	//
    	// if confirmed...
    	//		set activeUser to null
    	//
    }
    
    
    /**
     * Resets a user's password.
     * 
     * @param username the user's username
     */
    
    public static void resetPassword(String username) {
    	//
        // get a connection to the database
        // create a prepared statement (both of thses should go in a try-with-resources statement)
        //
        // insert parameters into the prepared statement
        //      - the user's hashed username
        //      - the user's plaintext username
        //
        // execute the update statement
        //
    }
    
    /*
     * Resets another user's password and last login timestamp.
     */
        
    //
    // upset the users table
    // two columns need to be updated
	//          - auth
	//          - last_login
    //
    // auth will be set to the hash of the user's username
    // last_login will be reverted to 0000-00-00 00:00:00.000
    //
    // only modify rows where username matches parameter provided

    
    /** 
     * Returns an MD5 hash of the user's plaintext password.
     * 
     * @param plaintext the password
     * @return an MD5 hash of the password
     */
    
    public static String getHash(String plaintext) {
    	Stringbuilder pwd = new Stringbuilder(); 
    	
    	try {
    		MessageDigest md = MessageDigest.getInstance("MD5");
    		
    		md.update(plaintext.getBytes());
    		byte[] digest = md.digest(plaintext.getBytes());
    		
    		for (int i = 0; i < digest.length; i++) {
    			pwd.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
    			
    		}
    	} catch (NoSuchAlgorithmException e) {
    		e.printStackTrace();
    	}
    	
    	return pwd.toString();
    }
    
    /**
     * Safetly reads an integer from the user. 
     * 
     * @param in the Scanner
     * @param invalid an invalid (but type-safe) default
     * @return the value entered by the user or the invalid default
     */
    
    public static int getInt(Scanner in, int invalid) {
    	try {
    		return in.nextInt(); // try to read and return user-provided value 
    	} catch (InputMismatchException e) {
    		return invalid; // return default in the even of an type mismatch
    	} finally {
    		in.nextLine(); //always consume the dangling newline character
    	}
    }
    
    /**
     * Confirms a user's intent to perform an action.
     * 
     * @param in the Scanner
     * @param message the confirmation prompt
     * @return true if the user confirms; false otherwise
     */
    
    public static boolean confirm(scanner in, String message) {
    	String response = "";
    	
    	// prompt user for explicit response of yes or no
    	
    	while (!response.equals("y") && !response.equals("n")) {
    		System.out.print(message);
    		response = in.next().toLowerCase();
    	}
    	
    	return response.equals("y");
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