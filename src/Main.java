import javax.swing.JFrame;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        JFrame g_win = new JFrame(); //gameplay window
        Gameplay gamePlay = new Gameplay();

        g_win.setBounds(10, 10, 750, 600);
//        g_win.setSize(710, 600);
        g_win.setTitle("Brick Breaker");
        g_win.setResizable(false);
        g_win.setVisible(true);
        g_win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        g_win.add(gamePlay);

        g_win.setVisible(true);

    }

}