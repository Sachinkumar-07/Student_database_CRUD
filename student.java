package sample_db;
import java.sql.*;
import java.util.*;
import java.text.*;

public class student {

	public static void main(String[] args) {
		
		Scanner in=new Scanner(System.in);

		operation op=new operation();

		int n;
		do {
			System.out.println("1.Insert\n2.Update\n3.Delete\n4.Display\n5.Exit");
			System.out.println("Enter your choice:");
			n=in.nextInt();
					
			switch(n) {
			case 1:op.insert();break;
			case 2:op.update();break;
			case 3:op.delete();break;
			case 4:op.display();break;
			case 5:
				op.exit();
				System.out.println("Exited");
				break;
			default:
				System.out.println("Invalid choice");
				break;
			}
		}while(n!=5);
	}
}

class operation
{
	Connection connect;
	operation()
	{
		String url="jdbc:mysql://localhost:3306/student_db";
		String username="root";
		String password="tiger@01";
		try {
		connect=DriverManager.getConnection(url,username,password);
		}
		catch(Exception e)
		{
			System.out.println("Couldn't connect to database");
		}
	}
	void insert()
	{
		java.sql.Date sqdob;
		java.sql.Date sqdoj;
		
		Scanner in=new Scanner(System.in);
		
		System.out.println("Enter the register number:");
		int no=in.nextInt();
		System.out.println("Enter the Name:");
		String name=in.next();
		System.out.println("Enter the date of birth(dd-mm-yyyy):");
		String dob=in.next();
		System.out.println("Enter the date of join(dd-mm-yyyy):");
		String doj=in.next();
		
		try {
			SimpleDateFormat smp=new SimpleDateFormat("dd-mm-yyyy");
			java.util.Date ndob=smp.parse(dob);
			long ms=ndob.getTime();
			sqdob=new java.sql.Date(ms);
			
			java.util.Date ndoj=smp.parse(doj);
			long ms2=ndoj.getTime();
			sqdoj=new java.sql.Date(ms);
			
			PreparedStatement ps=connect.prepareStatement("insert into student(student_no,student_name,student_dob,student_doj) values(?,?,?,?)");
			ps.setInt(1, no);
			ps.setString(2, name);
			ps.setDate(3, sqdob);
			ps.setDate(4, sqdoj);
			
			int row=ps.executeUpdate();
			
			if(row>0) {
				System.out.println("Record inserted successfully");
			}
			
			ps.close();
		}
		catch(Exception e)
		{
			System.out.println("Some error occured while Inserting record");
		}
	}
	
	void display()
	{
		Scanner in=new Scanner(System.in);
		try {			
			String sql="select * from student";
			Statement statement=connect.createStatement();
			ResultSet result=statement.executeQuery(sql);
			
			System.out.println("ID\tNAME\tDOB\t\tDOJ");
			while(result.next()) {
				int u_id=result.getInt("student_no");
				String name=result.getString("student_name");
				String dob=result.getString("student_dob");
				String doj=result.getString("student_doj");
				
				System.out.println(u_id+"\t"+name+"\t"+dob+"\t"+doj);
			}
		}
		catch(Exception e) {
			System.out.println("Error occurred during retrival of data");
		}
		
	}
	
	void update()
	{
		java.sql.Date sqdob;
		java.sql.Date sqdoj;
		
		Scanner in=new Scanner(System.in);
		
		System.out.println("Enter the register number of student whose record has to update:");
		int no=in.nextInt();
		System.out.println("Enter the updated Name:");
		String name=in.next();
		System.out.println("Enter the updated date of birth(dd-mm-yyyy):");
		String dob=in.next();
		System.out.println("Enter the updated date of join(dd-mm-yyyy):");
		String doj=in.next();
		
		try {
			SimpleDateFormat smp=new SimpleDateFormat("dd-mm-yyyy");
			java.util.Date ndob=smp.parse(dob);
			long ms=ndob.getTime();
			sqdob=new java.sql.Date(ms);
			
			java.util.Date ndoj=smp.parse(doj);
			long ms2=ndoj.getTime();
			sqdoj=new java.sql.Date(ms);
			
			PreparedStatement ps=connect.prepareStatement("update student set student_name=?,student_dob=?,student_doj=? where student_no="+no);
			ps.setString(1, name);
			ps.setDate(2, sqdob);
			ps.setDate(3, sqdoj);
			
			int row=ps.executeUpdate();
			
			if(row>0) {
				System.out.println("Record has been updated");
			}
			
			ps.close();
		}
		catch(Exception e)
		{
			System.out.println("Some error occured while updating the record");
		}
	}
	
	void delete() {
		Scanner in=new Scanner(System.in);
		
		System.out.println("Enter the register number of student whose record has to update:");
		int no=in.nextInt();
		
		try {
			
			String sql="delete from student where student_no="+no;
			Statement statement=connect.createStatement();
			statement.executeUpdate(sql);
			
			System.out.println("Record has successfully deleted");
		}
		catch(Exception e) {
			System.out.println("Couldn't delete the record");
		}
	}
	void exit()
	{
		try {
		connect.close();
		}
		catch(Exception e) {
			System.out.println("Couldn't close the connection");
		}
	}
}
