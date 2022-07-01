package tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class SidePane extends JPanel
{
    private Color pane_colour = Color.DARK_GRAY;

    private JButton newgame, pause_resume;
    private NextPieceDisplay display;
    private JLabel highscore;
    private JLabel currentScore;
    SidePane()
    {
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
        gbc.insets = new Insets(20, 20, 200 , 20);
        add(display, gbc);

        //current score and highscore
        gbc.insets = new Insets(10, 20, 0, 20);
        gbc.gridx = 0;
        gbc.gridy = 1;
        currentScore = new JLabel("<html>Current Score: <br> 1000000</html>");
        currentScore.setForeground(Color.BLACK);
        currentScore.setPreferredSize(new Dimension(100, 50));
        add(currentScore, gbc);

        gbc.insets = new Insets(0, 20, 0, 20);
        gbc.gridx = 0;
        gbc.gridy = 2;
        highscore = new JLabel("<html>Highscore: <br> 1000000</html>");
        highscore.setForeground(Color.BLACK);
        highscore.setPreferredSize(new Dimension(100, 50));
        add(highscore, gbc);

        //adding buttons
        newgame = new JButton("Start");
        newgame.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                JButton b = (JButton)e.getSource();
                //start game
                if((b.getText()).equals("Start"))
                    b.setText("New Game");
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(20, 20, 10, 20);
        add(newgame, gbc);

        pause_resume = new JButton("Pause");
        pause_resume.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                JButton b = (JButton)e.getSource();
                if((b.getText()).equals("Pause"))
                {
                    //check if theres an ongoing game
                    // if there's none ignore
                    //else pause
                    b.setText("Resume");
                }
                else if((b.getText()).equals("Resume"))
                {
                    b.setText("Pause");
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(pause_resume, gbc);

    }
}

//add update highscore and update current score methods
