package cells;

import cells.gui.window.MainFrame;
import javax.swing.JFrame;

/**
 *
 * @author mzak
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Cells");
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

}
