package tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class MainWindow extends JFrame
{
    private int square_size;
    private int board_width, sidepane_width, board_height;

    private int screen_height, screen_width;

    Board board;
    SidePane sidepane;
    MenuBar menuBar;

    MainWindow()
    {
        //Setting basic properties of the JFrame
        setTitle("Tetris");
        setIconImage((new ImageIcon("images/jframe-icon.png")).getImage());
        setResizable(false);
        setVisible(true);
        setLayout(new BorderLayout());

        //adding menubar
        menuBar = new MenuBar();
        setJMenuBar(menuBar);

        Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
        screen_height = screen_size.height;
        screen_width = screen_size.width;

        square_size = (int)(0.04 * screen_height);
        board_width = 10*square_size;
        sidepane_width = 5*square_size;
        board_height = 20*square_size;

        //adding the sidepane
        sidepane = new SidePane();
        sidepane.setPreferredSize(new Dimension(sidepane_width, board_height));
        add(sidepane, BorderLayout.EAST);

        //adding board 
        board = new Board(square_size, sidepane.display);
        board.setPreferredSize(new Dimension(board_width, board_height));
        add(board, BorderLayout.WEST);

        //Setting the size of the JFrame such that all elements fit perfectly
        pack();

        //Placing the JFrame at the centre of the screen
        setLocation((screen_width - getWidth())/2, (screen_height - getHeight())/2);

        //adding confirmation dialog box before closing window
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) 
            {   
                int result = JOptionPane.showConfirmDialog(null, "Do you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
                if(result == JOptionPane.YES_OPTION)
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                else
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);    
            }
        });

        //creating Game class object
        new Game(board, sidepane, menuBar);
    }
}
