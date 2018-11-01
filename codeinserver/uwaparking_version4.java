

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.sql.ResultSet;

public class uwaparking_version4 {

	public static String driver = "com.mysql.jdbc.Driver";  

    public static void main(String[] args) {      
    	
        startService();
    }
    
    
    /**  
    * start the socket, wait for client connection
    */  
   private static void startService() {  
       try {  
           // establish ServerSocket  
           ServerSocket serverSocket = new ServerSocket(9999);  
           System.out.println("--start server, waiting at port 9999--");  
 
            
           while (true) {  
               System.out.println("--wait for client--");  
               Socket socket = serverSocket.accept(); 
               System.out.println("client connected£º" + socket);  
                 
               startReader(socket);  
           }  
 
       } catch (IOException e) {  
           e.printStackTrace();  
       }  
   }  
 
   /**  
    * the main fucntion 
    */  
   private static void startReader(final Socket socket) {  
	  
       new Thread(){  
           @SuppressWarnings("deprecation")
           @Override  
           public void run() {  
               DataInputStream reader;  
               DataOutputStream out;
               
               try {  
                   reader = new DataInputStream( socket.getInputStream());  
                   out = new DataOutputStream(socket.getOutputStream());
                   while (true) 
                   {  
                       System.out.println("*wait for client input*");  
                       String msg = reader.readUTF();  
                       System.out.println("get client information£º" + msg); 
                       
                       // message from the register page
                       if (msg.charAt(0)=='r')
                       {
                    	   String[] register = new String[6];   
                    	   int formal = 1;
                    	   int temp = 0;
                           for (int i=1;i<msg.length();i++)
                           {
                        	   if (msg.charAt(i) == '/')
                        	   {
                        		   register[temp] = msg.substring(formal, i);
                        		   System.out.println(register[temp]);
                        		   formal = i + 1;
                        		   temp = temp + 1;
                        	   }
                           }
                    	   String name = register[0];
                    	   String password = register[1];
                    	   String email = register[2];
                    	   String role = register[3];
                    	   String permission = register[4];
                           
                           Connection conn = null;
                           PreparedStatement pst = null;
                           ResultSet rs = null;
                           try
                    	   {
	                    	   Class.forName(driver);
	                    	   conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/uwa_parking?user=root&password=");
	                    	   String sql = "select * from user_info where name = ?";
	                    	   pst = conn.prepareStatement(sql);
	                    	   pst.setString(1, name);
	                    	   rs = pst.executeQuery();
	                    	   if (rs.next())
	                    	   {
	                    		   // the username exists, send back "rr"
	                    		   out.writeUTF("rr");
	                    	   }
	                    	   else
	                    	   {
		                    	   Date date = new Date();
		                    	   Timestamp time = new Timestamp(date.getTime());
		                    	   System.out.println(time);
	                    		   sql = "insert into user_info(name,password,email,role,permission_type,create_date) values(?,?,?,?,?,?)";
		                    	   pst = conn.prepareStatement(sql);
		                    	   pst.setString(1, name);
		                    	   pst.setString(2, password);
		                    	   pst.setString(3, email);
		                    	   pst.setString(4, role);
		                    	   pst.setString(5, permission);
		                    	   pst.setTimestamp(6, time);
		                    	   pst.executeUpdate();
		                    	   out.writeUTF("rp");
	                    	   }
                    	   }
                    	   catch(Exception e)
                    	   {	
                    		   	e.printStackTrace();
                    		   	out.writeUTF("rf");
                    	   }
                 			try
                  			{
                  				conn.close();
                  				pst.close();
                  				rs.close();
                  			}
                  			catch (SQLException e)
                  			{
                  				e.printStackTrace();
                  			}  
                       }
                       // message from login page
                       else if (msg.charAt(0)=='l') 
                       {
                    	   String[] login = new String[2]; 
                    	   int formal = 1;
                    	   int temp = 0;
                           for (int i=1;i<msg.length();i++)
                           {
                        	   if (msg.charAt(i) == '/')
                        	   {
                        		   login[temp] = msg.substring(formal, i);
                        		   formal = i + 1;
                        		   temp = temp + 1;
                        	   }
                           }
                    	   String name = login[0];
                    	   String password = login[1];
                           Connection conn = null;
                           PreparedStatement pst = null;
                           ResultSet rs = null;
                    	   try
                     	   {
 	                    	   Class.forName(driver);
 	                    	   conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/uwa_parking?user=root&password=");
 	                    	   String sql = "select password from user_info where name = ?";
 	                    	   pst = conn.prepareStatement(sql);
 	                    	   pst.setString(1, name);
 	                    	   rs = pst.executeQuery();
 	                    	   String pass_temp = "";
 	                    	   while (rs.next())
 	                    	   {
 	                    		   pass_temp = rs.getString(1);
 	                    	   }
 	                    	   if (pass_temp.equals(password))
 	                    	   {
 	                    		  out.writeUTF("lp");
 	                    		  System.out.println("1.0");
 	                    	   }
 	                    	   else 
 	                    	   {
 	                    		  out.writeUTF("lf");
 	                    	   } 	                
                     	   }
                     	   catch(Exception e)
                     	   {
                     		   e.printStackTrace();
                     	   }
                    	   try
                    	   {
                    		   conn.close();
                    		   pst.close();
                    		   rs.close();
                    	   }
                    	   catch (SQLException e)
                    	   {
                    		   e.printStackTrace();
                    	   } 
                       }
                       // if press "in" button in app
                       else if (msg.charAt(0)=='i')
                       {
                    	   String[] in = new String[2]; 
                    	   int formal = 1;
                    	   int temp = 0;
                           for (int i=1;i<msg.length();i++)
                           {
                        	   if (msg.charAt(i) == '/')
                        	   {
                        		   in[temp] = msg.substring(formal, i);
                        		   formal = i + 1;
                        		   temp = temp + 1;
                        	   }
                           }
                    	   String username = in[0];
                    	   String parkinglots_name = in[1];
                           Connection conn = null;
                           PreparedStatement pst = null;
                           ResultSet rs = null;
                           try
                     	   {
                        	   // store the data in parkink_record table
                        	   Class.forName(driver);
 	                    	   conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/uwa_parking?user=root&password=");
	                    	   Date date = new Date();
	                    	   Timestamp time = new Timestamp(date.getTime());
 	                    	   String sql = "insert into parking_record(parkinglots_name,user_name,action,time) values(?,?,?,?)";
 	                    	   pst = conn.prepareStatement(sql);
	                    	   pst.setString(1, parkinglots_name);
	                    	   pst.setString(2, username);
	                    	   pst.setString(3, "in");
	                    	   pst.setTimestamp(4, time);
	                    	   pst.executeUpdate();
	                    	   // find the permission_type of specific user
	                    	   sql = "select permission_type from user_info where name = ?";
 	                    	   pst = conn.prepareStatement(sql);
 	                    	   pst.setString(1, username);
 	                    	   rs = pst.executeQuery();
 	                    	   String permission_type = "";
 	                    	   while (rs.next())
 	                    	   {
 	                    		   	permission_type = rs.getString(1);
 	                    	   }
 	                    	   if (permission_type.equals("red"))
 	                    	   {
		 	                    	sql = "select rest_red_permission from parkinglots_status where parkinglots_name = ?";
		 	                    	pst = conn.prepareStatement(sql);
		 	 	                    pst.setString(1, parkinglots_name);
		 	 	                    rs = pst.executeQuery();
		 	 	                    int rest = 0;
		 	 	                    while (rs.next())
		 	 	                    {
		 	 	                    	rest = rs.getInt(1);
		 	 	                    }
		 	 	                    if (rest>0)
		 	 	                    {
		 	 	                    	rest = rest - 1;
		 	 	                    }
                       				sql = "update parkinglots_status set rest_red_permission = ? where parkinglots_name = ?";
                       				pst = conn.prepareStatement(sql);
                       				pst.setInt(1, rest);
                       				pst.setString(2, parkinglots_name);
                       				pst.executeUpdate();
 	                    	   }
 	                    	   else if (permission_type.equals("yellow"))
 	                    	   {
		 	                    	sql = "select rest_yellow_permission from parkinglots_status where parkinglots_name = ?";
		 	                    	pst = conn.prepareStatement(sql);
		 	 	                    pst.setString(1, parkinglots_name);
		 	 	                    rs = pst.executeQuery();
		 	 	                    int rest = 0;
		 	 	                    while (rs.next())
		 	 	                    {
		 	 	                    	rest = rs.getInt(1);
		 	 	                    }
		 	 	                    if (rest>0)
		 	 	                    {
		 	 	                    	rest = rest - 1;
		 	 	                    }
                      				sql = "update parkinglots_status set rest_yellow_permission = ? where parkinglots_name = ?";
                      				pst = conn.prepareStatement(sql);
                      				pst.setInt(1, rest);
                      				pst.setString(2, parkinglots_name);
                      				pst.executeUpdate();
 	                    	   }
 	                    	   out.writeUTF("p");
                     	   }
                     	   catch(Exception e)
                     	   {
                     		   e.printStackTrace();
                     		   out.writeUTF("f");
                     	   }
                    	   try
                    	   {
                    		   conn.close();
                    		   pst.close();
                    		   rs.close();
                    	   }
                    	   catch (SQLException e)
                    	   {
                    		   e.printStackTrace();
                    	   }  
                       }
                       // if press "out" button in app
                       else if (msg.charAt(0)=='o')
                       {
                    	   String[] outout = new String[2]; 
                    	   int formal = 1;
                    	   int temp = 0;
                           for (int i=1;i<msg.length();i++)
                           {
                        	   if (msg.charAt(i) == '/')
                        	   {
                        		   outout[temp] = msg.substring(formal, i);
                        		   formal = i + 1;
                        		   temp = temp + 1;
                        	   }
                           }
                    	   String username = outout[0];
                    	   String parkinglots_name = outout[1];
                           Connection conn = null;
                           PreparedStatement pst = null;
                           ResultSet rs = null;
                           try
                     	   {
                        	// store the data in parkink_record table
                        	   Class.forName(driver);
 	                    	   conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/uwa_parking?user=root&password=");
	                    	   Date date = new Date();
	                    	   Timestamp time = new Timestamp(date.getTime());
 	                    	   String sql = "insert into parking_record(parkinglots_name,user_name,action,time) values(?,?,?,?)";
 	                    	   pst = conn.prepareStatement(sql);
	                    	   pst.setString(1, parkinglots_name);
	                    	   pst.setString(2, username);
	                    	   pst.setString(3, "out");
	                    	   pst.setTimestamp(4, time);
	                    	   pst.executeUpdate();
	                    	   // find the permission_type of specific user
	                    	   sql = "select permission_type from user_info where name = ?";
 	                    	   pst = conn.prepareStatement(sql);
 	                    	   pst.setString(1, username);
 	                    	   rs = pst.executeQuery();
 	                    	   String permission_type = "";
 	                    	   while (rs.next())
 	                    	   {
 	                    		   	permission_type = rs.getString(1);
 	                    	   }
 	                    	   if (permission_type.equals("red"))
 	                    	   {
		 	                    	sql = "select rest_red_permission from parkinglots_status where parkinglots_name = ?";
		 	                    	pst = conn.prepareStatement(sql);
		 	 	                    pst.setString(1, parkinglots_name);
		 	 	                    rs = pst.executeQuery();
		 	 	                    int rest = 0;
		 	 	                    while (rs.next())
		 	 	                    {
		 	 	                    	rest = rs.getInt(1);
		 	 	                    }
		 	 	                    
		 	                    	sql = "select red_permission from parkinglots_general where name = ?";
		 	                    	pst = conn.prepareStatement(sql);
		 	 	                    pst.setString(1, parkinglots_name);
		 	 	                    rs = pst.executeQuery();
		 	 	                    int total = 0;
		 	 	                    while (rs.next())
		 	 	                    {
		 	 	                    	total = rs.getInt(1);
		 	 	                    }
		 	 	                    
		 	 	                    
		 	 	                    if (rest < total)
		 	 	                    {
		 	 	                    	rest = rest + 1;
		 	 	                    }
                       				sql = "update parkinglots_status set rest_red_permission = ? where parkinglots_name = ?";
                       				pst = conn.prepareStatement(sql);
                       				pst.setInt(1, rest);
                       				pst.setString(2, parkinglots_name);
                       				pst.executeUpdate();
 	                    	   }
 	                    	   else if (permission_type.equals("yellow"))
 	                    	   {
		 	                    	sql = "select rest_yellow_permission from parkinglots_status where parkinglots_name = ?";
		 	                    	pst = conn.prepareStatement(sql);
		 	 	                    pst.setString(1, parkinglots_name);
		 	 	                    rs = pst.executeQuery();
		 	 	                    int rest = 0;
		 	 	                    while (rs.next())
		 	 	                    {
		 	 	                    	rest = rs.getInt(1);
		 	 	                    }
		 	 	                    
		 	                    	sql = "select yellow_permission from parkinglots_general where name = ?";
		 	                    	pst = conn.prepareStatement(sql);
		 	 	                    pst.setString(1, parkinglots_name);
		 	 	                    rs = pst.executeQuery();
		 	 	                    int total = 0;
		 	 	                    while (rs.next())
		 	 	                    {
		 	 	                    	total = rs.getInt(1);
		 	 	                    }
		 	 	                    
		 	 	                    if (rest < total)
		 	 	                    {
		 	 	                    	rest = rest + 1;
		 	 	                    }
                      				sql = "update parkinglots_status set rest_yellow_permission = ? where parkinglots_name = ?";
                      				pst = conn.prepareStatement(sql);
                      				pst.setInt(1, rest);
                      				pst.setString(2, parkinglots_name);
                      				pst.executeUpdate();
 	                    	   }
 	                    	   out.writeUTF("p");
                     	   }
                     	   catch(Exception e)
                     	   {
                     		   e.printStackTrace();
                     		   out.writeUTF("f");
                     	   }
                    	   try
                    	   {
                    		   conn.close();
                    		   pst.close();
                    		   rs.close();
                    	   }
                    	   catch (SQLException e)
                    	   {
                    		   e.printStackTrace();
                    	   }  
                       }
                                        
                   }
               } catch (IOException e) {  
                   e.printStackTrace();  
               }  
           }  
       }.start();  
   }  

}
