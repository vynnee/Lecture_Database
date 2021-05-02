package example;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import examples.dto.Department;
import examples.dao.DepartmentDAO;

import java.util.List;

/**
 * Servlet implementation class DepartmentTest
 */
@WebServlet("/DepartmentTest")
public class DepartmentTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DepartmentTest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("DAO add doGet() 호출!");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		DepartmentDAO dao = new DepartmentDAO();
		
		int func = Integer.parseInt(request.getParameter("func"));
		
		if(func == 1) { // insert
			String Dname = request.getParameter("Dname");
			int Dnumber = Integer.parseInt(request.getParameter("Dnumber"));
			String SSN = request.getParameter("Mgr_ssn");
			String start_date = request.getParameter("Mgr_start_date");
			
			Department department = new Department(Dname, Dnumber, SSN, start_date);
			int addCount = dao.addDepartment(department);
			out.println("<h1> insert : " + addCount + " row(s) </h1>");
		}
		
		if(func == 2) { // delete
			int Dnumber = Integer.parseInt(request.getParameter("Dnumber"));
			Department department = dao.getDepartment(Dnumber);
			out.println(Dnumber);
			int delCount = dao.deleteDepartment(Dnumber);
			out.println("<h1> delete : " + delCount + " row(s) </h1>");
		}
		
		if(func == 3) { // select *
	      List<Department> departmentList = dao.getDepartments();
	      
			for(Department department1 : departmentList) {
				out.println("<h1> My name is " + department1.getDname() + "</h1> " +"<h1> My number is " 
			+ department1.getDnumber() + "</h1> " + "<h1> My mgr_ssn is " + department1.getMgr_ssn() 
			+  "</h1> " + "<h1> My mgr_start_date is " + department1.getMgr_start_date() + "</h1>"+ "<h1> </h1>");
			}
		}
		
		if(func == 4) { // select dnumber
			Department department = null;
			int Dnumber = Integer.parseInt(request.getParameter("Dnumber"));
			department = dao.getDepartment(Dnumber);
			
			if(department == null)
				out.println("<h1> My Department is null </h1>");
			else {
				out.println("<h1> My name is " + department.getDname() + "</h1> " +"<h1> My number is "
			+ department.getDnumber() + "</h1> " + "<h1> My mgr_ssn is " + department.getMgr_ssn() 
			+  "</h1> " + "<h1> My mgr_start_date is " + department.getMgr_start_date() + "</h1>"+ "<h1> </h1>");
			}
		}
		
		if(func == 5) { // update
			String Dname = request.getParameter("Dname");
			int Dnumber = Integer.parseInt(request.getParameter("Dnumber"));
			String SSN = request.getParameter("Mgr_ssn");
			String start_date = request.getParameter("Mgr_start_date");
			
	        Department department = new Department(Dname, Dnumber, SSN, start_date);
	        int updateCount = dao.updateDepartment(department);
			out.println("<h1> update : " + updateCount + " row(s) </h1>");
			
			out.println(department.getDname());
		}
		
		out.close();
	}
}