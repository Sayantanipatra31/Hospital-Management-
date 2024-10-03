package hospitalmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Hospital {
 private static final String url="jdbc:mysql://localhost:3306/project";
 private static final String username="root";
 private static final String password="Sayantani@2003";
 public static void main(String[] args) {
    try
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
    }catch(ClassNotFoundException e)
    {
        e.printStackTrace();
    }
    @SuppressWarnings("resource")
    Scanner sc = new Scanner(System.in);
    try{
        Connection connection=DriverManager.getConnection(url, username, password);
        Patient patient = new Patient(connection,sc);
        Doctors doctor = new Doctors(connection);
        while(true)
        {
            System.out.println("---------- HOSPITAL MANAGEMENT ----------");
            System.out.println("1. ADD NEW PATIENT");
            System.out.println("2. VIEW EXISTING PATIENT");
            System.out.println("3. VIEW EXISTING DOCTORS");
            System.out.println("4. BOOK APPOINTMENT");
            System.out.println("5. EXIT!!");
            System.out.println();
            System.out.println("Enter Your Choice!!");
            int choice =sc.nextInt();
            switch (choice) 
            {
                case 1:
                    patient.AddPatient();
                    System.out.println();
                    break;
                case 2:
                    patient.viewPatient();
                    System.out.println();
                    break;
                case 3:
                    doctor.viewDoctors();
                    System.out.println();
                    break;
                 case 4:
                    BookAppointment(patient, doctor, connection, sc);
                    System.out.println();
                    break;
                 case 5:
                 System.out.println("THANK YOU FOR USING OUR SERVICES!!");
                    return;
                default:
                 System.out.println("ENTER VALID CHOICE");
                 
            }
        }
    }catch(SQLException e)
    {
        e.printStackTrace();
    }
 }
 public static void BookAppointment(Patient patient,Doctors doctor,Connection connection,Scanner sc)
 {
    System.out.println("ENTER PATIENT ID : ");
    int patientId=sc.nextInt();
    System.out.println("ENTER DOCTOR ID  : ");
    int doctorId=sc.nextInt();
    System.out.println("ENTER APPOINTMENT DATE (YY-MM-DDDD) : ");
    String appointmentDate=sc.next();
    if(patient.getPatientbyId(patientId) && doctor.getDoctorbyId(doctorId))
    {
        if(CheckDoctorAvailability(doctorId,appointmentDate,connection))
        {
           String appointmentQuery="INSERT INTO APPOINTMENTS(patient_id,doctors_id,appointment_date)VALUES(?,?,?)";
           try{
             PreparedStatement preparedStatement= connection.prepareStatement(appointmentQuery);
             preparedStatement.setInt(1, patientId);
             preparedStatement.setInt(2, doctorId);
             preparedStatement.setString(3,appointmentDate);
             int affectedrows=preparedStatement.executeUpdate();
             if(affectedrows>0)
             {
                System.out.println("Appointment Booked!!");
             }
             else
             {
                System.out.println("Failed to book appointment");
             }
           }catch(Exception e)
           {
            e.printStackTrace();
           }
        }
        else
        {
            System.out.println("Doctor is not available on this date!!!");
        }
    }
    else{
        System.out.println("Either patient or doctor doesnt exist!!!");
    }
 }
 public static boolean CheckDoctorAvailability(int doctorId, String appointmentDate, Connection connection )
 {
    String query = "SELECT COUNT(*) FROM APPOINTMENTS WHERE doctors_id=? AND appointment_date=?";
    try{
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,doctorId);
        preparedStatement.setString(2,appointmentDate);
        ResultSet resultSet = preparedStatement.executeQuery();
        int count=0;
        if(resultSet.next())
        {
              count=resultSet.getInt(1);
        }
        if(count==0)
        {
            return true;
        }
        else{
            return false;
        }

    }
    catch(SQLException e)
    {
        e.printStackTrace();
    }
    return false;
 }
}
