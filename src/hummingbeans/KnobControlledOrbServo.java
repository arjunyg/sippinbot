
package hummingbeans;

import edu.cmu.ri.createlab.hummingbird.HummingbirdRobot;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * Name: KnobControlledOrbServo.java
 * Author: Tom Lauwers
 * Setup: Connect the Knob to sensor port 1, a tri-color LED to tri-color port 1, and a servo to servo port 1.
 * Description: The Knob controls the servo and LED - turn the knob and the servo turns. The LED changes from red to green to blue.
 */

public class KnobControlledOrbServo /*extends Frame implements KeyListener*/ {
    
    public static int TOLERANCE = 32;
    
/*    public void keyTyped(KeyEvent e) {
        int keyCode = e.getKeyCode();

    }
    
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT) {
            System.out.println("elft releades");
        }
    }
    
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT) {
            if(target > 0) {
                target-=10;
            }
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            if(target < 255) {  
                target+=10;
            }
        }
    }*/
    
    public static void main(String[] args) throws IOException {
        
        /*KnobControlledOrbServo frame = new KnobControlledOrbServo();
        frame.addKeyListener(frame);
        frame.setVisible(true);*/
        
        // Instantiate the Hummingbird object (establishes a connection to the Hummingbird)
        HummingbirdRobot robot = new HummingbirdRobot();
        
        // Print instructions for exiting
        System.out.println("");
        System.out.println("Press ENTER to quit.");
        
        int knobVal;
        int target = 50;
        robot.setMotorVelocity(2, 255);
        // Necessary for exit
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true)
        {
            // check whether the user pressed a key, if so, break out of the loop
            if (in.ready())
                {
                break;
                }
            
            // Read the value on port 1 (range is 0 to 255)
            knobVal = robot.getSensorValue(1);
            
            int sensor2 = robot.getSensorValue(2);
            int sensor3 = robot.getSensorValue(3);

            if (sensor2 < TOLERANCE) {
                target += 5;
            }
            if (sensor3 < TOLERANCE) {
                target -= 5;
            }

            // Set the servo with the value directly - conveniently, servo also has a range of 0-255
            robot.setServoPosition(1, target);
            robot.setServoPosition(2, knobVal);
            // Color fade from red at one extreme to green in the middle to blue at the other extreme
            if(target < 128) {
                robot.setFullColorLED(1, (127-target)*2, target*2, 0); // If less than 128, the LED is red at 0 and green at 127
            }
            else {
                robot.setFullColorLED(1, 0, (255-target)*2, (target-128)*2); // Goes from green to blue
            }
            
        }
        // Disconnect - if you miss this call the Hummingbird will continue doing stuff for five more seconds
        // you may also get a java error.
        robot.disconnect();
    }
}
