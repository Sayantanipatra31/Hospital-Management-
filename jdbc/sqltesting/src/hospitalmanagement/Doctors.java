package hospitalmanagement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Doctors {
    private Connection connection;
    Doctors(Connection connection)
    {
        this.connection=connection;
    }
    public void viewDoctors()
    {
      String query="SELECT * FROM DOCTORS";
      try 
      {
        PreparedStatement preparedStatement=connection.prepareStatement(query);
        ResultSet resultSet=preparedStatement.executeQuery();
        while(resultSet.next())
        {
            int id=resultSet.getInt("Id");
            String name=resultSet.getString("Name");
            String specialization=resultSet.getString("Specialization");
            System.out.println("===========================");
            System.out.println("Doctor_id     : "+id);
            System.out.println("Doctor_name   : "+name);
            System.out.println("Doctor_specialization   : "+ specialization);
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    public boolean getDoctorbyId(int id)
    {
        String query="SELECT * FROM DOCTORS WHERE Id=? ";
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
