package examples.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import examples.dto.Department;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DepartmentDAO {
	private static String dburl = "jdbc:mysql://localhost/department?serverTimezone=Asia/Seoul";
	private static String dbUser = "depuser";
	private static String dbpasswd = "dep1234";

	public Department getDepartment(Integer Dnumber) {
		Department department = null;
		
		String sql = "SELECT Dname, Dnumber, Mgr_ssn, Mgr_start_date FROM department WHERE Dnumber = ?";
		
		try(Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
			PreparedStatement ps = conn.prepareStatement(sql)){
			
			ps.setInt(1, Dnumber);
			
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					String dname = rs.getString(1);
					int dnumber = rs.getInt(2);
					String mgr_ssn = rs.getString(3);
					String mgr_start_date = rs.getString(4);
					department = new Department(dname, dnumber, mgr_ssn, mgr_start_date);
				}
			} catch(Exception e) {
					e.printStackTrace();
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return department;
	}
	
	public int addDepartment(Department department) {
		int insertCount = 0;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("연결 성공!!~");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String sql = "INSERT INTO department (Dname, Dnumber, Mgr_ssn, Mgr_start_date) VALUES ( ?, ?, ?, ?)";
		
		try (Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
				PreparedStatement ps = conn.prepareStatement(sql)){

			ps.setString(1, department.getDname());
			ps.setInt(2, department.getDnumber());
			ps.setString(3, department.getMgr_ssn());
			ps.setString(4, department.getMgr_start_date());
			
			insertCount = ps.executeUpdate();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return insertCount;
	}
	
	public int deleteDepartment(Integer Dnumber) {
		int deleteCount = 0;
		
		try {
			Class.forName("com.mysql.cj.jdbcDriver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String sql = "DELETE FROM department WHERE Dnumber = ?";
		
		try (Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, Dnumber);
			deleteCount = ps.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return deleteCount;
	}
	
	public int updateDepartment(Department department) {
		int updateCount = 0;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		String sql = "update department set Dname = ?, Mgr_ssn = ?, Mgr_start_date = ? where Dnumber = ?";
		
		try(Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
				PreparedStatement ps = conn.prepareStatement(sql)) {
			
			ps.setString(1, department.getDname());
			ps.setString(2, department.getMgr_ssn());
			ps.setString(3, department.getMgr_start_date());
			ps.setInt(4, department.getDnumber());
			
			updateCount = ps.executeUpdate();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return updateCount;
	}
	
	public List<Department> getDepartments(){
		List<Department> list = new ArrayList<>();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		
		String sql = "SELECT Mgr_start_date, Mgr_ssn, Dnumber, Dname FROM department order by Dname desc";
		
		try (Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
				PreparedStatement ps = conn.prepareStatement(sql)){
			
			try(ResultSet rs = ps.executeQuery()){
				
				while(rs.next()) {
					String Mgr_start_date = rs.getString(1);
					String Mgr_ssn = rs.getString(2);
					int Dnumber = rs.getInt("Dnumber");
					String Dname = rs.getString(4);
					Department department = new Department(Dname, Dnumber, Mgr_ssn, Mgr_start_date);
					list.add(department);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
}
