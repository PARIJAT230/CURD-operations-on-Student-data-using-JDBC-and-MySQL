package web_student_project.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import javax.sql.DataSource;


public class StudentDataUtil {
	private DataSource dataSource;

	public StudentDataUtil(DataSource dataSource) {
	
		this.dataSource = dataSource;
	}
	
	public ArrayList<Student> getStudents() throws Exception
	{
		ArrayList<Student> students= new ArrayList<Student>();
		Connection myconn=null;
		Statement mystate=null;
		ResultSet myresult=null;
		
		
		try{
			myconn=dataSource.getConnection();
			
			String sql="select * from student";
			mystate=myconn.createStatement();
			
			myresult=mystate.executeQuery(sql);
			while(myresult.next())
			{
				int id=myresult.getInt("id");
				String firstname=myresult.getString("first_name");
				String lastname=myresult.getString("last_name");
				String email=myresult.getString("email");
				

				
				Student temp= new Student(id,firstname,lastname,email);
				
			

				students.add(temp);
				
			
				
				
				
			}
			
			return students;
			
		}
		finally{
		close(myconn,mystate,myresult);
		
			
		}
	}
	public Student getStudent(String theStudentId) throws Exception {

		Student theStudent = null;
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int studentId;
		
		try {
			// convert student id to int
			studentId = Integer.parseInt(theStudentId);
			
			// get connection to database
			myConn = dataSource.getConnection();
			
			// create sql to get selected student
			String sql = "select * from student where id=?";
			
			// create prepared statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, studentId);
			
			// execute statement
			myRs = myStmt.executeQuery();
			
			// retrieve data from result set row
			if (myRs.next()) {
				String firstname = myRs.getString("first_name");
				String lastname = myRs.getString("last_name");
				String email = myRs.getString("email");
				
				// use the studentId during construction
				theStudent = new Student(studentId, firstname, lastname, email);
			}
			else {
				throw new Exception("Could not find student id: " + studentId);
			}				
			
			return theStudent;
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
	}

	private void close(Connection myconn, Statement mystate, ResultSet myresult) {
		// TODO Auto-generated method stub
		try{
			if(myconn != null)
			{
				myconn.close();
			}
			if(mystate !=null)
			{
				mystate.close();
				
			}
			if(myresult !=null)
			{
				myresult.close();
			}
		}
		catch(Exception exe)
		{
			exe.printStackTrace();
		}
		
	}

	public void addStudent(Student theStudent) throws Exception {
		// TODO Auto-generated method stub
		Connection myconn=null;
		PreparedStatement mystate=null;
		 try{
			 myconn= dataSource.getConnection();
			 String sql="insert into student"+"(first_name, last_name, email)"+"value (?,?,?)";
			 mystate=myconn.prepareStatement(sql);
			 
			 mystate.setString(1, theStudent.getFirstname());
			 mystate.setString(2, theStudent.getLastname());
			 mystate.setString(3, theStudent.getEmail());
			 mystate.execute();
			 

			 
					 
			 
		 }
		 finally{
			 close(myconn,mystate,null);
		 }
		
		
	}
public void updateStudent(Student theStudent) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn = dataSource.getConnection();
			
			// create SQL update statement
			String sql = "update student "
						+ "set first_name=?, last_name=?, email=? "
						+ "where id=?";
			
			// prepare statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setString(1, theStudent.getFirstname());
			myStmt.setString(2, theStudent.getLastname());
			myStmt.setString(3, theStudent.getEmail());
			myStmt.setInt(4, theStudent.getId());

			
			
			// execute SQL statement
			myStmt.execute();
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}
	}

public void deleteStudent(String theStudentId) throws Exception {

	Connection myConn = null;
	PreparedStatement myStmt = null;
	
	try {
		// convert student id to int
		int studentId = Integer.parseInt(theStudentId);
		
		// get connection to database
		myConn = dataSource.getConnection();
		
		// create sql to delete student
		String sql = "delete from student where id=?";
		
		// prepare statement
		myStmt = myConn.prepareStatement(sql);
		
		// set params
		myStmt.setInt(1, studentId);
		
		// execute sql statement
		myStmt.execute();
	}
	finally {
		// clean up JDBC code
		close(myConn, myStmt, null);
	}	
}
}




	
	


