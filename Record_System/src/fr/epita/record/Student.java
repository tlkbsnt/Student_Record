package fr.epita.record;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Student {
    String name;
    String faculty;
    String email;
    String contacts;
    String address;
    List<String> studentList = new ArrayList<>();
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    void insertData(){

        Scanner data = new Scanner(System.in);

        System.out.print("Enter Name: ");
        this.name = data.nextLine();
        setName(this.name);

        System.out.print("Enter Faculty: ");
        this.faculty = data.nextLine();
        setFaculty(this.faculty);

        System.out.print("Enter Email: ");
        this.email = data.nextLine();
        setEmail(this.email);

        System.out.print("Enter Contact: ");
        this.contacts = data.nextLine();
        setContacts(this.contacts);

        System.out.print("Enter Address: ");
        this.address = data.nextLine();
        setAddress(this.address);

        String query= "INSERT INTO STUDENT_RECORD(name,faculty,email,contacts,address) VALUES(?, ?, ?, ?, ?)";
        //Try to  Connection the postgres local database
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/Students", "postgres", " ")) {

            if (conn != null) {
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1,getName());
                preparedStatement.setString(2,getFaculty());
                preparedStatement.setString(3,getEmail());
                preparedStatement.setString(4, getContacts());
                preparedStatement.setString(5,getAddress());
                preparedStatement.executeUpdate();
                conn.close(); // Close the Database connection
                System.out.println("Inserted Data Successfully!");
            } else {
                System.out.println("Failed to make connection!");
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //End the Database connection
    }
    void fetchData(){

        String query= "SELECT * FROM STUDENT_RECORD";
        try(Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/Students", "postgres", " ")) {
            if(connection != null){
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){

                    // Fetch the data form the local database table and set value
                    this.setName(resultSet.getString("name"));
                    this.setFaculty(resultSet.getString("faculty"));
                    this.setEmail(resultSet.getString("email"));
                    this.setContacts(resultSet.getString("contacts"));
                    this.setAddress(resultSet.getString("address"));

                    // add the data into the arrayList which are fetch from the local database table
                    studentList.add(this.getName());
                    studentList.add(this.getFaculty());
                    studentList.add(this.getEmail());
                    studentList.add(this.getContacts());
                    studentList.add(this.getAddress());
                    // Add new line command after the every data row
                    studentList.add("\n");
                }
                // print the data contained in the arraylist
                studentList.forEach(x->System.out.print(x+"\t"));

                // Before fetch the new data it will remove all data from arraylist
                studentList.removeAll(new ArrayList<>(studentList));
            } else {
                System.out.println("Failed to make connection!");
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    String working(){
        int num;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose the option: ");
        System.out.print("1. For Show  and ");
        System.out.print("2. For Insert,  ");
        System.out.print("0. For exit : ");
        num = scanner.nextInt();
        switch (num) {
            case 1 -> fetchData();
            case 2 -> insertData();
            case 0 -> System.exit(0);
            default -> System.out.println("Enter only number 1 or 2 only :");
        }
        //Self call until the exit
        return working();
    }
}
