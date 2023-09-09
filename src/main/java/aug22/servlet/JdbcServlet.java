package aug22.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/jdbc")
public class JdbcServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			PrintWriter pw = response.getWriter();
			response.setContentType("text/html");
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			}catch(ClassNotFoundException e) {
				System.out.println("Please check driver doesn't exist");
			}
			String jdbc_url = "jdbc:mysql://localhost:3306/sample";
			String user = "root";
			String password = "root";
				//Connecting to the database
			try(
				Connection conn = DriverManager.getConnection(jdbc_url,user,password);
				//Create the statement
				Statement stmt = conn.createStatement();
				//Run the query
				ResultSet rs = stmt.executeQuery("select * from student");){
				pw.println("<h1>Student details</h1>");
				pw.println("<table border=1>");
				pw.println("<tr>");
				pw.println("<td>Student number</td>");
				pw.println("<td>First Name</td>");
				pw.println("<td>Last Name</td>");
				pw.println("</tr>");
				while(rs.next()) {
					int stu_id = rs.getInt("stud_id");
					String FirstName = rs.getString("FirstName");
					String LastName = rs.getString("LastName");
					pw.println("<tr>");
					pw.println("<td>"+stu_id+"</td>");
					pw.println("<td>"+FirstName+"</td>");
					pw.println("<td>"+LastName+"</td>");
					pw.println();
					pw.println(stu_id+"\t "+FirstName+"\t"+LastName);
				}
			}
			catch(Exception ex) {
				pw.println("Error message :"+ex.getMessage());
				pw.close();
			}
	}

}
