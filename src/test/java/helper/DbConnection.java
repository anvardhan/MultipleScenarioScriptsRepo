package helper;

//step1 - Import statements
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DbConnection {

  public static Connection getConnection() throws ClassNotFoundException {
  	
  	//This method is used to connect the java application with mysql database. 
  	//Here register the JDBC driver for the application,
  	//configure the database properties(fetch from mysql.properties) and return the connection object.
  	
   /*   ResourceBundle rb = ResourceBundle.getBundle("mysql");
		String url = rb.getString("db.url");
		String username = rb.getString("db.username");
		String password = rb.getString("db.password");  */
	    // fill the code
	
  /*step2 -Load Driver - Register JDBC driver -Requires that you initialize a 
  	driver so we can open a communications channel with the database.
  	i.e enables java application to interact with database. */
		try {
  	  Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
       // System.out.println("*** Driver loaded");
     } catch(Exception e) {
        System.out.println("*** Error : "+e.toString());
        System.out.println("*** ");
        System.out.println("*** Error : ");
        e.printStackTrace();
    }  
		
		/*step3 - Open a connection - Requires using the DriverManager.getConnection() method to 
		  create a Connection object, which represents a physical connection with a database server. 
		  It requires Database URL with database name and without database name, username and password */
		
		Connection con=null;
		try {
		   /*Specifying database name in db url- if database already exist then use this.
			Also it requires when we need physical connection with a selected database to do 
			more activities like select query, update,delete,etc*/
			
			con = DriverManager.getConnection( //url,username,password);
					"jdbc:mysql://localhost:3306/qa2qe","root","rootpassword");
			
			/*Not specifying database name in db url - if database is not exist then use this.
			  Like creating a database for the first time or dropping database*/
			//con = DriverManager.getConnection( //url,username,password);
					//"jdbc:mysql://localhost:3306/","root","rootpassword");
			
			return con;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
  }

  public static ResultSet getDataFromDB(Connection con, String sql) throws ClassNotFoundException {
		
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
			         ResultSet.CONCUR_UPDATABLE);
			//stmt.setInt(1, id);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
			   
		return rs;
	}

}
