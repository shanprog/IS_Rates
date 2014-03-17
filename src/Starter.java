import javax.swing.*;

/**
 * User: ShuryginAN
 * Date: 27.11.13
 * Time: 15:38
 *
 * version 0.3   прототип
 */


public class Starter {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow mainFrame = new MainWindow();
            }
        });
    }

}
