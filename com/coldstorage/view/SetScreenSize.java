package com.coldstorage.view;
//Java Program to set JFrame Size according to the Screen
import javax.swing.*;
import java.awt.*;

public class SetScreenSize extends JFrame {
    public static void main(String[] args) {

        // Creating a Java JFrame
        JFrame f = new JFrame();        
        f.setTitle("Set Size According to Screen"); 
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        f.setVisible(true);

        //APPROACH - 1 : Using getScreenSize() method
/*        Toolkit tk=Toolkit.getDefaultToolkit(); //Initializing the Toolkit class.
        Dimension screenSize = tk.getScreenSize(); //Get the Screen resolution of our device.
        f.setSize(screenSize.width,screenSize.height); //Set the width and height of the JFrame.
*/
        //APPROACH - 2 : Using MAXIMIZED_BOTH
        f.setExtendedState(JFrame.MAXIMIZED_BOTH); //Maximize both the horizontal and vertical directions
       
    }
}