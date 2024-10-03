package hospitalmanagement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
// import java.util.jar.Attributes.Name;

public class Patient
{
    private Connection connection;
    private Scanner scanner;
    Patient(Connection connection, Scanner scanner)
    {
        this.connection=connection;
        this.scanner=scanner;
    }
    public void AddPatient()
    {
        System.out.println("Enter Name : ");

        String name=scanner.next();
        System.out.println("Enter Age : ");
        int age=scanner.nextInt();
        System.out.println("Enter Gender(M for male and F for female) : ");
        String gender=scanner.next();

        try
        {
         String query="INSERT INTO PATIENTS(Name,Age,Gender)VALUES(?,?,?)";
         PreparedStatement preparedStatement=connection.prepareStatement(query);
         preparedStatement.setString(1, name);
         preparedStatement.setInt(2,age);
         preparedStatement.setString(3, gender);
         int affectedrows=preparedStatement.executeUpdate();
         if(affectedrows>0)
         {
            System.out.println("Patient has been added successfully");
         }
         else
         {
            System.out.println("Failed to add Patient");
         }
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public void viewPatient()
    {
      String query="SELECT * FROM PATIENTS";
      try 
      {
        PreparedStatement preparedStatement=connection.prepareStatement(query);
        ResultSet resultSet=preparedStatement.executeQuery();
        while(resultSet.next())
        {
            int id=resultSet.getInt("Id");
            String name=resultSet.getString("Name");
            int age=resultSet.getInt("Age");
            String gender=resultSet.getString("Gender");
            System.out.println("===========================");
            System.out.println("Patient_id     : "+id);
            System.out.println("Patient_name   : "+name);
            System.out.println("Patient_age    : "+age);
            System.out.println("Patient_gender : "+gender);
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    public boolean getPatientbyId(int id)
    {
        String query="SELECT * FROM PATIENTS WHERE Id=? ";
        try{
        PreparedStatement preparedStatement=connection.prepareStatement(query);
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next())
        {
           return true;
        }
        else
        {
            return false;
        }
        }catch (SQLException e)
          {
            e.printStackTrace();
          }
        return false;
    }
}
