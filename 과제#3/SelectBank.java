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
@WebServlet("/SelectBank")
public class SelectBank extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectBank() {
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

		out.println("<h1> Hello Bank!!</h1>");
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost/bank";
			conn = DriverManager.getConnection(url, "bankuser", "bank123");
			System.out.println("연결 성공!");
			stmt = conn.createStatement();
			
			String str = "SELECT * FROM V1";
			
			rs = stmt.executeQuery(str);
			
			while(rs.next()) {
				String cname = rs.getString(1);
				int bbalance = rs.getInt(2);
				
				out.println("<h3>" + cname + " " + bbalance + "</h3>");
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