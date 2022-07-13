package tetris;

import java.awt.Color;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.Timer;
import javax.swing.*;
import java.util.Random;
import java.util.Scanner;

class Game 
{
    static enum Status{ONGOING, PAUSED, NONE};
    static Status game_status = Status.NONE;

    private Timer timer;

    Board board;
    SidePane side_pane;
    MenuBar menu_bar;
    static Random rand = new Random();
    Controls controls;

    //Constructor for new Game
    Game(Board b, SidePane s, MenuBar mb)
    {
        board = b;
        side_pane = s;
        menu_bar = mb;

        //initializing timer
        timer = new Timer(1000, new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                board.requestFocus();
                board.curr_piece.curr_pos[0]++;
                if(!board.checkPosition())
                {
                    board.curr_piece.curr_pos[0]--;
                    board.changePiece();
                }
                board.repaint();

                if(board.update_current_score)
                    updateCurrentScore();
                
                if(side_pane.toggle_plus_points)
                    togglePlusPoints();
            }
        });

        //adding action Listeners to the buttons
        initializeButtons();

        //adding action listeners to the menu items
        initializeMenu();

        //initizalizing controls object
        controls = new Controls(board);
    }

    /* INITIALIZATION METHODS */
    void initializeButtons()
    {
        //start_stop button
        side_pane.start_stop.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                String text = side_pane.start_stop.getText();
                if(text.equals("Start Game"))
                {
                    startGame();
                }
                else if(text.equals("End Game"))
                {  
                    endGame();
                }
                
            }
        });

        //pause_resume button
        side_pane.pause_resume.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if(game_status == Status.ONGOING)
                {
                    pauseGame();
                }
                else if(game_status == Status.PAUSED)
                {
                    resumeGame();
                }
            }
        });
    }

    void initializeMenu()
    {
        //new game menu item
        menu_bar.new_game.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) 
            {
                if(game_status == Status.ONGOING || game_status == Status.PAUSED)
                {
                    endGame();
                }
                if(game_status == Status.NONE)
                    startGame();
            }
        });

        //pause menu item
        menu_bar.pause.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) 
            {
                if(game_status == Status.ONGOING)
                {
                    pauseGame();

                }
                else if(game_status == Status.PAUSED)
                {
                    //the game is already paused - JOptionPane
                    JOptionPane.showMessageDialog(null, "The game is already paused!");

                }
                else if(game_status == Status.NONE)
                {
                    // there is no ongoing game to pause - JOptionPane
                    JOptionPane.showMessageDialog(null, "There is no ongoing game to pause.");
                }
                
            }
        });

        //resume menu item
        menu_bar.resume.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) 
            {
                if(game_status == Status.PAUSED)
                {
                    resumeGame();
                }
                else if(game_status == Status.ONGOING)
                {
                    // the game isnt paused to resume it
                    JOptionPane.showMessageDialog(null, "You can't resume an ongoing game!\n(Pause it first)");
                }
                else if(game_status == Status.NONE)
                {
                    // there is no ongoing game
                    JOptionPane.showMessageDialog(null, "There is no ongoing game to resume.");
                }

                
            }
        });

    }

    /* KEYBOARD CONTROL METHODS */

    //Adding keyboard controls to the board
    void addControls()
    {
        board.setFocusable(true);
        board.addKeyListener(controls);
    }

    //Removes keyboard controls from the board
    void removeControls()
    {
        board.removeKeyListener(controls);
    }

    /* GAME STATUS METHODS */
    void startGame()
    {
        board.current_score = 0;
        board.update_current_score = true;
        game_status = Status.ONGOING;

        //Initializing curr_piece and next_piece
        int r = rand.nextInt(0, Tetromino.Shape.values().length);
        board.curr_piece = new Tetromino(Tetromino.Shape.values()[r]);

        r = rand.nextInt(0, Tetromino.Shape.values().length);
        board.next_piece = new Tetromino(Tetromino.Shape.values()[r]);

        //initializing current position of current piece
        board.curr_piece.curr_pos = new int[]{1, 4};
        if(board.curr_piece.piece_shape == Tetromino.Shape.I_shape && board.curr_piece.curr_orientation == 1)
            board.curr_piece.curr_pos[0] = 2;
        
        //painting the nextpiece display
        board.next_display.paint_piece = true;
        board.next_display.piece = board.next_piece;
        board.next_display.repaint();
        
        addControls();
        timer.start();

        //changing the text on buttons
        side_pane.start_stop.setText("End Game");
        side_pane.pause_resume.setText("Pause");
    }

    void endGame()
    {
        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to end this game?", "End Game Confirmation:", JOptionPane.YES_NO_CANCEL_OPTION);
    
        //doesn't end the game
        if(result != JOptionPane.YES_OPTION)
            return;

        timer.stop();

        //clearing board
        board.clearBoard();
        board.repaint();

        //clearing nextpiece display
        side_pane.display.paint_piece = false;
        side_pane.display.repaint();

        //resetting game status
        game_status = Status.NONE;

        //removing controls
        removeControls();

        //set highscore
        setHighscore();
        board.current_score = 0;
        updateCurrentScore();

        //changing text on buttons
        side_pane.start_stop.setText("Start Game");
        side_pane.pause_resume.setText("Pause");

        //changing plus_points message
        board.plus_points_message = "Good Luck!";
    }

    void pauseGame()
    {
        timer.stop();

        //setting game status
        game_status = Status.PAUSED;

        //removing controls
        removeControls();

        //changing text on buttons
        side_pane.pause_resume.setText("Resume");
    }

    void resumeGame()
    {
        timer.start();

        //setting game status
        game_status = Status.ONGOING;

        //adding controls
        addControls();

        //changing text on buttons
        side_pane.pause_resume.setText("Pause");
    }

    /* SIDEPANE DISPLAY METHODS */

    //setting the highscore
    void setHighscore()
    {
        try 
        {
            Scanner sc = new Scanner(new File("highscore.txt"));
            int highscore = 0;
            while(sc.hasNextInt())
                highscore = sc.nextInt();
            
            if(board.current_score <= highscore)
                return;

            sc.close();

            highscore = board.current_score;
            try 
            {
                FileWriter fw = new FileWriter("highscore.txt");
                fw.write(Integer.toString(highscore));
                fw.close();
            } 
            catch (IOException e) 
            {
                System.out.println("IOException\n");
            }

            side_pane.highscore.setText("<html>Highscore: <br> "+Integer.toString(highscore)+"</html>");
            
        } 
        catch (FileNotFoundException e) 
        {
            System.out.println("\"highscore.txt\" not found!\n");
        }
    }

    //updating the current_score
    void updateCurrentScore()
    {
        side_pane.currentScore.setText("<html>Current Score: <br> "+Integer.toString(board.current_score)+"</html>");
        board.update_current_score = false;
        side_pane.toggle_plus_points = true;
    }

    // showing the plus points
    void togglePlusPoints()
    {
        JLabel points_display = side_pane.plus_points;
        if(points_display.getForeground() == side_pane.pane_colour)
        {
            points_display.setText(board.plus_points_message);
            points_display.setForeground(Color.WHITE);
        }
        else
        {
            points_display.setForeground(side_pane.pane_colour);
            side_pane.toggle_plus_points = false;
        }
    }

    


}
