package web_student_project.web.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	
	private StudentDataUtil studentdatautil;
	@Resource(name="jdbc/web_student_tracker")
	
	private DataSource dataSource;
	
	

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		try{
			studentdatautil = new StudentDataUtil(dataSource);
		}
		catch (Exception exe)
		{
			throw new ServletException(exe);
		}
	}



	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String thecommand=request.getParameter("command");
			
			if(thecommand==null)
			{
				thecommand="LIST";
			}
			switch (thecommand)
			{
			case "LIST":
				listStudents(request,response);
				break;
			case "ADD":
				addStudent(request,response);
				break;
			case "LOAD":
				loadStudent(request, response);
				break;	
			case "UPDATE":
				updateStudent(request, response);
				break;
				
			case "DELETE":
				deleteStudent(request, response);
				break;	
				
			default:
					listStudents(request,response);
			}
			listStudents(request,response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void deleteStudent(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

			// read student id from form data
			String theStudentId = request.getParameter("studentId");
			
			// delete student from database
			studentdatautil.deleteStudent(theStudentId);
			
			// send them back to "list students" page
			listStudents(request, response);
		}
	
	private void loadStudent(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {

			// read student id from form data
			String theStudentId = request.getParameter("studentId");
			
			// get student from database (db util)
			Student theStudent = studentdatautil.getStudent(theStudentId);
			
			// place student in the request attribute
			request.setAttribute("THE_STUDENT", theStudent);
			
			// send to jsp page: update-student-form.jsp
			RequestDispatcher dispatcher = 
					request.getRequestDispatcher("/updatestudent.jsp");
			dispatcher.forward(request, response);		
		}

	private void updateStudent(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

			// read student info from form data
			int id = Integer.parseInt(request.getParameter("studentId"));
			String firstname = request.getParameter("firstName");
			String lastname = request.getParameter("lastName");
			String email = request.getParameter("email");
			
			// create a new student object
			Student theStudent = new Student(id, firstname, lastname, email);
			
			// perform update on database
			studentdatautil.updateStudent(theStudent);
			
			// send them back to the "list students" page
			listStudents(request, response);
			
		}



	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String firstName=request.getParameter("firstName");
		String lastName=request.getParameter("lastName");
		String email=request.getParameter("email");
		
		Student theStudent = new Student(firstName,lastName,email);
		studentdatautil.addStudent(theStudent);
		

		
	}



	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

		ArrayList<Student> students=studentdatautil.getStudents();
		request.setAttribute("STUDENT_LIST",students);
		

		RequestDispatcher dispatcher = request.getRequestDispatcher("/liststudents.jsp");
		dispatcher.forward(request,response);
		
		
		
	}
	

}
