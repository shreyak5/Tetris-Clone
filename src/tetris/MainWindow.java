package tetris;

import javax.swing.*;
import java.awt.*;

class MainWindow extends JFrame
{
    MainWindow()
    {
        //Setting basic properties of the JFrame
        setTitle("Tetris");
        setIconImage((new ImageIcon("images/jframe-icon.png")).getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        setJMenuBar(new MenuBar());

        //Setting the size of the JFrame such that all elements fit perfectly
        pack();
        
        //Placing the JFrame at the centre of the screen
        Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
        int screen_height = screen_size.height;
        int screen_width = screen_size.width;
        setLocation((screen_width - getWidth()) / 2, (screen_height - getWidth()) / 2);
    }
}
