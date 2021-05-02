package examples;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class InsertStudent
 */
@WebServlet("/InsertStudent")
public class InsertStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertStudent() {
        super();
        
        System.out.println("InsertStudent 생성!!");
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet() 호출!");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		out.println("<h1> Hello Studesign!!</h1>");
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost/studesign";
			conn = DriverManager.getConnection(url, "stuuser", "stu1234");
			System.out.println("연결 성공!");
			stmt = conn.createStatement();
			
			int studentnum = Integer.parseInt(request.getParameter("studentnumber"));
			String name = request.getParameter("name");
			String major = request.getParameter("major");
			int studentgrade = Integer.parseInt(request.getParameter("grade"));
			String doublemajor = request.getParameter("doublemajor");
			String admissiondate = request.getParameter("admissiondate");
			String email = request.getParameter("email");
			
			
			String sql = "INSERT INTO studentinf VALUES ('" + studentnum + "', '" + name + "', '" + major + "', '" + studentgrade
					 + "', '" + doublemajor + "', '" + admissiondate + "', '" + email + "')";
			stmt.executeUpdate(sql);
			
			out.println("insert success\n");
	         
			sql = "SELECT * FROM studentinf";
			rs = stmt.executeQuery(sql);
		      
			while(rs.next()) {
			int stu_number = rs.getInt(1);
			String stu_name = rs.getString(2);
			String stu_major = rs.getString(3);
			int stu_grade = rs.getInt(4);
			String stu_dmajor = rs.getString(5);
			String stu_date = rs.getString(6);
			String stu_email = rs.getString(7);
			            
			out.println("<h3>" + stu_number + " " + stu_name + " " + stu_major 
					+ " " + stu_grade + " " + stu_dmajor + " " + stu_date +  " " + stu_email + "</h3>");
			}
		}
		
		catch(ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
		}
		catch(SQLException e) {
			System.out.println("에러" + e);
		}
		finally {
			try {
				if(conn != null && !conn.isClosed()) {
					conn.close();
				}
				if(stmt != null && !stmt.isClosed()) {
					stmt.close();
				}
				if(rs != null && !rs.isClosed()) {
					rs.close();
				}
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		out.close();
	}
}