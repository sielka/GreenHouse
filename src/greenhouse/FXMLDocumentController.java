/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package greenhouse;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;


/**
 *
 *
 */
public class FXMLDocumentController implements Initializable, Runnable{
    
    int temperature = 30;
    
    int wet = 80; 
    
    //--------------------------------------------------------------------------
    int cu_temperature = 30;
    
    int cu_wet = 80;
    
    Random rand = new Random();
    
    boolean isSimulationStop = true;
    
    @FXML
    private Separator spv_open_right, spv_open_left;
    
    @FXML
    private Label label_shower;
    
    @FXML
    private TextField current_temperature, current_wet;
    
    @FXML
    private Button b_windows, b_shower;
    
    @FXML
    private void startTheSimulation(ActionEvent event) {
       
        new Thread(this, "GreenHouse").start();
       
    }
    
    @FXML
    private void stopTheSimulation(ActionEvent event){
    
        isSimulationStop = false; 

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        offShower();
        
        closeWindows();
       
    } 
    
    
    @Override
    public void run(){
    
        while(isSimulationStop){
        
            theFirstState();
            
           
            cu_temperature = rand.nextInt((40- 30) + 1) + 30; // генеруємо темпаратуру
            
            showCurrentTemperature();
            
            if(cu_temperature > temperature){
            
                cu_wet = rand.nextInt((80 - 70) + 1) + 70; // генеруємо вологу
                
                showCurrentWet();
                
                procedureForTemperature();
                
                procedureForWet();
            
            }

        }       
      
    }
    
    private void showCurrentTemperature(){
    
        current_temperature.setText(Integer.toString(cu_temperature));

    }
    
    private void showCurrentWet(){
    
    current_wet.setText(Integer.toString(cu_wet));
    
    }
    
    private void openWindows(){
        
        spv_open_right.setVisible(false);
        spv_open_left.setVisible(false);
    
        Platform.runLater(() -> { 
        
         b_windows.setStyle("-fx-background-color: #10100F -fx-text-fill: #10100F; ");
         b_windows.setText("Вікна відкрито");
        
        });
        
        cu_temperature -= 1;
    }
    
    private void closeWindows(){
    
        spv_open_right.setVisible(true);
        spv_open_left.setVisible(true);
        
        Platform.runLater(() -> { 
        
          b_windows.setStyle("-fx-background-color: #10FF00; -fx-text-fill: #10100F; ");
          b_windows.setText("Вікна закриті");
        
        });
        
       
    }
    
    private void onShower(){
    
        label_shower.setVisible(true);
        
        Platform.runLater(() -> { 
        
          b_shower.setStyle("-fx-background-color: #E576E5; -fx-text-fill: #10100F; ");
          b_shower.setText("Зволоження включено");
        
        });

        cu_wet += 1;
    }
    
    private void offShower(){
    
        label_shower.setVisible(false);
        
        Platform.runLater(() -> { 
        
         b_shower.setStyle("-fx-background-color: #FFF800; -fx-text-fill: #10100F; ");
         b_shower.setText("Зволоження виключено");
        
        });
    }
    
    private void waitTwoSec() {
  
       try {
           TimeUnit.SECONDS.sleep(2);
       } catch (InterruptedException ex) {
           Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
       }
      
  }
    
    private void theFirstState(){
    
        for(int i = 0; i < 3; i++){
                
                showCurrentTemperature();
                
                showCurrentWet();
                
                waitTwoSec();
                
            }
    
    }
    
    private void procedureForTemperature(){
    
         while(cu_temperature > temperature){
                      
             openWindows();
             
             showCurrentTemperature();
            
             waitTwoSec();
            
            }
         
         closeWindows();
    }
    
    private void procedureForWet(){
    
    while(cu_wet < wet){
            
             onShower();
             
             showCurrentWet();
            
             waitTwoSec();
            
            }
         
         offShower();
    }
       
}
