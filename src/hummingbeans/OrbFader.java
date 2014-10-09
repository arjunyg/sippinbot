package hummingbeans;

import edu.cmu.ri.createlab.hummingbird.HummingbirdRobot;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * <p>
 * <code>OrbFader</code> is a simple demo app which fades the full-color LEDs.
 * </p>
 * Setup: Connect one or both full color LEDs and watch them change colors
 * 
 * @author Chris Bartley (bartley@cmu.edu)
 */
@SuppressWarnings({"UseOfSystemOutOrSystemErr"})
public class OrbFader
   {
    // Change this to have them change color more quickly
   private static final int COLOR_CHANGE_STEP_SIZE = 1;

   public static void main(final String[] args) throws IOException
      {
      // Instantiate the Hummingbird object (establishes a connection to the Hummingbird)    
      HummingbirdRobot hummingbird = new HummingbirdRobot();

      // A reader used to allow the user to exit the program
      final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      
      // Set up the colors to shift between
      final Color[] colors = new Color[]{
            new Color(255, 0, 0), //red
            new Color(255, 255, 0), // yellow
            new Color(0, 255, 0), // green
            new Color(0, 255, 255), // cyan
            new Color(0, 0, 255),  // blue
            new Color(255, 0, 255) // purple
      };
      
      Color currentColor = colors[0];
      // Sets both full color LEDs to the first color, which is red
      setFullColorLEDs(hummingbird, currentColor);

      // Print instructions for exiting
      System.out.println("");
      System.out.println("Press ENTER to quit.");
      int targetColorIndex = 1;
      while (true)
         {
         // check whether the user pressed a key
         if (in.ready())
            {
            break;
            }

         // see if we've reached the target color and need to update our starting and ending colors
         if (currentColor.equals(colors[targetColorIndex]))
            {
            targetColorIndex += 1;
            if (targetColorIndex >= colors.length)
               {
               targetColorIndex = 0;
               }
            }

         // compute the deltas for the color components
         final int rDelta = computeDelta(currentColor.getRed(), colors[targetColorIndex].getRed());
         final int gDelta = computeDelta(currentColor.getGreen(), colors[targetColorIndex].getGreen());
         final int bDelta = computeDelta(currentColor.getBlue(), colors[targetColorIndex].getBlue());

         // create the new color
         currentColor = new Color(currentColor.getRed() + rDelta,
                                  currentColor.getGreen() + gDelta,
                                  currentColor.getBlue() + bDelta);

         // set the color
         setFullColorLEDs(hummingbird, currentColor);
         }

      hummingbird.disconnect();
      }

   // Sets both full color LEDs to the same value.
   private static void setFullColorLEDs(final HummingbirdRobot hummingbird, final Color currentColor)
      {
      hummingbird.setFullColorLED(1,
                                  currentColor.getRed(),
                                  currentColor.getGreen(),
                                  currentColor.getBlue());
      hummingbird.setFullColorLED(2,
                                  currentColor.getRed(),
                                  currentColor.getGreen(),
                                  currentColor.getBlue());
      }

   private static int computeDelta(final int currentValue, final int targetValue)
      {
      if (currentValue > targetValue)
         {
         return -COLOR_CHANGE_STEP_SIZE;
         }
      else if (currentValue < targetValue)
         {
         return COLOR_CHANGE_STEP_SIZE;
         }
      return 0;
      }

   }
