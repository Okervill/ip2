/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ip2;

import javafx.application.Application;
import javafx.stage.Stage;

public class SwitchWindow {
    
    public static void switchWindow(Stage window, Application app) {
    try {
        app.start(window);
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    
}
