package tetris;

import javax.swing.*;
import java.awt.*;

class MainWindow extends JFrame
{
    private int square_size;
    private int board_width, sidepane_width, board_height;

    private int screen_height, screen_width;

    Board board;
    SidePane sidepane;

    MainWindow()
    {
        //Setting basic properties of the JFrame
        setTitle("Tetris");
        setIconImage((new ImageIcon("images/jframe-icon.png")).getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        setLayout(new BorderLayout());
        //adding menubar
        setJMenuBar(new MenuBar());

        Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
        screen_height = screen_size.height;
        screen_width = screen_size.width;

        square_size = (int)(0.04 * screen_height);
        board_width = 10*square_size;
        sidepane_width = 5*square_size;
        board_height = 20*square_size;

        //adding board 
        board = new Board(square_size);
        board.setPreferredSize(new Dimension(board_width, board_height));
        add(board, BorderLayout.WEST);

        //adding the sidepane
        sidepane = new SidePane();
        sidepane.setPreferredSize(new Dimension(sidepane_width, board_height));
        add(sidepane, BorderLayout.EAST);

        //Setting the size of the JFrame such that all elements fit perfectly
        pack();

        //Placing the JFrame at the centre of the screen
        setLocation((screen_width - getWidth())/2, (screen_height - getHeight())/2);
    }
}
