package se.hackerbrau;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        GUI gui = new GUI();
        gui.pack();
        gui.setSize(gui.getPreferredSize());
        gui.setVisible(true);
    }
}
