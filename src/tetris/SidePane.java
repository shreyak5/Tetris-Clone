package tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class SidePane extends JPanel
{
    private Color pane_colour = Color.DARK_GRAY;
    
    
    JLabel highscore;
    JLabel currentScore;
    JLabel plus_points;

    NextPieceDisplay display;
    JButton start_stop, pause_resume;

    boolean toggle_plus_points;
    
    SidePane()
    {
        toggle_plus_points = false;
        setBackground(pane_colour);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 100;

        //setting up next piece display
        display = new NextPieceDisplay();
        display.setPreferredSize(new Dimension(100, 100));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 50, 20);
        add(display, gbc);

        plus_points = new JLabel("points", SwingConstants.CENTER);
        plus_points.setForeground(Color.DARK_GRAY);
        plus_points.setFont(new Font("Serif", Font.BOLD, 15));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 20, 100, 20);
        add(plus_points, gbc);

        //current score and highscore
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 20, 0, 20);
        currentScore = new JLabel("<html>Current Score: <br> 0</html>");
        currentScore.setForeground(Color.BLACK);
        currentScore.setPreferredSize(new Dimension(100, 50));
        add(currentScore, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        highscore = new JLabel();
        //displaying current highscore
        try 
        {
            Scanner sc = new Scanner(new File("highscore.txt"));
            int score = 0;
            while(sc.hasNextInt())
            {
                score = sc.nextInt();
            }

            sc.close();
            highscore.setText("<html>Highscore: <br>"+Integer.toString(score)+"</html>");
        } 
        catch (FileNotFoundException e) 
        {  
            System.out.println("\"highscore.txt\" not found!\n");
        }
        
        highscore.setForeground(Color.BLACK);
        highscore.setPreferredSize(new Dimension(100, 50));
        add(highscore, gbc);

        //adding buttons
        start_stop = new JButton("Start Game");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(20, 20, 10, 20);
        add(start_stop, gbc);

        pause_resume = new JButton("Pause");
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(pause_resume, gbc);

    }
}

