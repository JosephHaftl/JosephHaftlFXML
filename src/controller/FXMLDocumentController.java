/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

import javax.swing.JOptionPane;
import model.Friendmodel;
/**
 *
 * @author Joseph Haftl
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    
   //preview code from scenebuilder
    @FXML
    private Button button;

    @FXML
    private Button buttonCreateFriend;

    @FXML
    private Button buttonRead;

    @FXML
    private Button buttonUpdate;

    @FXML
    private Button buttonDelete;

    //Quiz 4
    
     @FXML
    private TextField textboxName;

     
    @FXML
    private TableView<Friendmodel> friendTable;

    @FXML
    private TableColumn<Friendmodel, Integer> friendID;

    @FXML
    private TableColumn<Friendmodel, String> friendName;

    @FXML
    private TableColumn<Friendmodel, Boolean> friendStatus;

    @FXML
    private TableColumn<Friendmodel, String> friendNotes;



    // the observable list of students that is used to insert data into the table
    private ObservableList<Friendmodel> friendData;
    
 public void setTableData(List<Friendmodel> friendList) {

        // initialize the studentData variable
        friendData = FXCollections.observableArrayList();

        // add the student objects to an observable list object for use with the GUI table
        friendList.forEach(f -> {
            friendData.add(f);
        });

        // set the the table items to the data in studentData; refresh the table
        friendTable.setItems(friendData);
        friendTable.refresh();
        System.out.println("setTableData runs");
    }   

 @FXML
    void searchByNameAction(ActionEvent event) {
    System.out.println("clicked");
           

        // getting the name from input box        
        String name = textboxName.getText();

        // calling a db read operaiton, readByName
        List<Friendmodel> Friends = readByName(name);

        if (Friends == null || Friends.isEmpty()) {

            // show an alert to inform user 
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog Box");// line 2
            alert.setHeaderText("This is header section to write heading");// line 3
            alert.setContentText("No friend");// line 4
            alert.showAndWait(); // line 5
        } else {

            // setting table data
            setTableData(Friends);
        }

    }
    
 @FXML
    void searchByNameAdvancedAction(ActionEvent event) {
        
    }
    
  @FXML
    void actionShowDetailsInPlace(ActionEvent event) throws IOException {

    }
    
   @FXML
    void actionShowDetails(ActionEvent event) throws IOException {

    }
    
    public List<Friendmodel> readByName(String name) {
        Query query = manager.createNamedQuery("Friendmodel.findByName");

        // setting query parameter
        query.setParameter("name", name);

        // execute query
        List<Friendmodel> friends = query.getResultList();
        for (Friendmodel friend : friends) {
            System.out.println(friend.getId() + " " + friend.getName() + " " + friend.getStatus() + " " + friend.getNotes());
        }

        return friends;
    }
    
    //End Quiz 4
    @FXML
    void createFriend(ActionEvent event) {
        //code taken from IntroJavaFX demo
Scanner input = new Scanner(System.in);
        
     
      
        int id = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Friend ID"));
        
        String name = JOptionPane.showInputDialog(null, "Enter Friend's Name");
        
        boolean status = Boolean.parseBoolean(JOptionPane.showInputDialog(null, "Is this person your friend?"));
        
        String notes = JOptionPane.showInputDialog(null, "Enter Friend Notes");
        
        //System.out.println("Enter Friend ID");
        //int id = input.nextInt();
        
        //System.out.println("Enter Friend's Name:");
        //String name = input.next();
      
        //System.out.println("Is this person your friend?"); //the statement from this is being passed in for the string notes I will fix later
        //boolean status = input.hasNextBoolean();
        
        //System.out.println("Enter Friend Notes");
        //String notes = input.next(); 
        

        
        
        
        
        // create a student instance
        Friendmodel friend = new Friendmodel();
        
        // set properties
       friend.setId(id);
       friend.setName(name);
       friend.setStatus(status);
       friend.setNotes(notes);
        
        // save this student to databse by calling Create operation        
        create(friend); //need to make the create method before this can work
        System.out.println("Create method ran");

    }
    //delete taken from the demo
    public void delete(Friendmodel friend) {
        try {
            Friendmodel existingFriend = manager.find(Friendmodel.class, friend.getId());

            // sanity check
            if (existingFriend != null) {
                
                // begin transaction
                manager.getTransaction().begin();
                
                //remove student
                manager.remove(existingFriend);
                
                // end transaction
                manager.getTransaction().commit();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    //delete taken from the demo
    @FXML
    void deleteFriend(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
         // read input from command line
        int id = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Friend ID"));
        
        Friendmodel s = readById(id); 
        System.out.println("we are deleting this friend: "+ s.toString());
        delete(s);
    }
    //readById from the demo
 public Friendmodel readById(int id){
        Query query = manager.createNamedQuery("Friendmodel.findById");
        
        // setting query parameter
        query.setParameter("id", id);
        
        // execute query
        Friendmodel friend = (Friendmodel) query.getSingleResult();
        if (friend != null) {
            System.out.println(friend.getId() + " " + friend.getName() + " " + friend.getStatus() + " " + friend.getNotes());
        }
        
        return friend;
    } 
    //readall from the demo
       public List<Friendmodel> readAll(){
    Query query = manager.createNamedQuery("Friendmodel.findAll");
        List<Friendmodel> friends = query.getResultList();

        for (Friendmodel s : friends) {
            System.out.println(s.getId() + " " + s.getName() + " " + s.getStatus() + " " + s.getNotes());
        }
        
        return friends;
    }
    @FXML

   //queries are required before it can work
   void readFriend(ActionEvent event) {
    readAll();
    }

    public void update(Friendmodel model) {
        try {

            Friendmodel existingFriend = manager.find(Friendmodel.class, model.getId());

            if (existingFriend != null) {
                // begin transaction
                manager.getTransaction().begin();
                
                // update all atttributes
                existingFriend.setName(model.getName());
                existingFriend.setStatus(model.getStatus());
                existingFriend.setNotes(model.getNotes());
                
                
                // end transaction
                manager.getTransaction().commit();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
   
    @FXML
    void updateFriend(ActionEvent event) {
        //Scanner input = new Scanner(System.in);
        //code from the demo but instead of using the console I will be using joptionpanes
        // read input from command line
//        System.out.println("Enter ID:");
//        int id = input.nextInt();
//        
//        System.out.println("Enter Name:");
//        String name = input.next();
//        
//        System.out.println("Enter CPGA:");
//        double cgpa = input.nextDouble();
        //reads the values from joptionpanes instead of scanner. If I use scanner and multiple input.next() then the inputs become incorrect
        int id = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Friend ID"));
        
        String name = JOptionPane.showInputDialog(null, "Enter Friend's Name");
        
        boolean status = Boolean.parseBoolean(JOptionPane.showInputDialog(null, "Is this person your friend?"));
        
        String notes = JOptionPane.showInputDialog(null, "Enter Friend Notes");

        // create a student instance
        Friendmodel friend = new Friendmodel();
        
        // set properties
        friend.setId(id);
        friend.setName(name);
        friend.setStatus(status);
        friend.setNotes(notes);
        
        // save this student to databse by calling Create operation        
        update(friend);//need to add update method for this to work

    }
    
        public List<Friendmodel> findByNameAndId(String name, int id){
        Query query = manager.createNamedQuery("Friendmodel.findByNameAndId");
        
        // setting query parameter
        
        query.setParameter("name", name);
        query.setParameter("id", id);
        
        // execute query
        List<Friendmodel> friends =  query.getResultList();
        for (Friendmodel friend: friends) {
            System.out.println(friend.getName() + " " + friend.getId());
        }
        
        return friends;
    } 
    
    @FXML
    void readByNameId(ActionEvent event) {
        // name and cpga
        
        //Scanner input = new Scanner(System.in);
        
        // read input from command line
        
       
        String name = JOptionPane.showInputDialog(null, "Enter Friend's Name");

        int id = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Friend ID"));
        // create a student instance      
        List<Friendmodel> friends =  findByNameAndId(name, id);

    }
    
        public List<Friendmodel> findByNameContaining(String text){//needs to be fixed
        Query query = manager.createNamedQuery("Friendmodel.findByNameContaining");
        
        // setting query parameter
        
        query.setParameter("name",text);
      
        
        // execute query
        List<Friendmodel> friends =  query.getResultList();
        for (Friendmodel friend: friends) {
            System.out.println(friend.getName());
        }
        
        return friends;
    } 
    
    @FXML
    void readByNameContaining(ActionEvent event) {//needs to be fixed
        // name and cpga
      String text = JOptionPane.showInputDialog(null, "Enter part of friends name");
        // create a student instance      
        List<Friendmodel> friends =  findByNameContaining(text);

    }
     public List<Friendmodel> readByStatusName(Boolean status, String name){//needs to be fixed
        Query query = manager.createNamedQuery("Friendmodel.findbyStatusAndName");
        
        // setting query parameter
        
        query.setParameter("status", status);
        query.setParameter("name", name);
        
        // execute query
        List<Friendmodel> friends =  query.getResultList();
        for (Friendmodel friend: friends) {
        System.out.println(friend.getName());
        }
        
        return friends;
    } 
    
        @FXML
    void readByStatusName(ActionEvent event) {//needs to be fixed
        boolean status = Boolean.parseBoolean(JOptionPane.showInputDialog(null, "Enter Friend Status"));
        String name = JOptionPane.showInputDialog(null, "Enter Friend's Name");

        // create a student instance      
        List<Friendmodel> friends =  readByStatusName(status,name);
        
    }
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    //Database manager from IntroJavaFX demo
    EntityManager manager;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // loading students from database
        //database reference: "IntroJavaFXPU"
        manager = (EntityManager) Persistence.createEntityManagerFactory("JosephHaftlFXMLPU").createEntityManager();
//quiz 4
        friendName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        friendID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        friendStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        friendNotes.setCellValueFactory(new PropertyValueFactory<>("Notes"));
        

        //eanble row selection
        // (SelectionMode.MULTIPLE);
        friendTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
    }
        public void create(Friendmodel friend) {
        try {
            // begin transaction
            manager.getTransaction().begin();
            
            // sanity check
            if (friend.getId() != null) {
                
                // create student
                manager.persist(friend);
                
                // end transaction
                manager.getTransaction().commit();
                
                System.out.println(friend.toString() + " is created");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
