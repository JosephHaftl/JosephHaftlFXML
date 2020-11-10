/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Friendmodel;
import javax.swing.JOptionPane;

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
