package tetris;

import java.awt.event.*;

import javax.swing.Timer;
import javax.swing.*;
import java.util.Random;

class Game 
{
    static enum Status{ONGOING, PAUSED, NONE};
    static Status game_status = Status.NONE;

    private int current_score;
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
            }
        });

        //adding action Listeners to the buttons
        initializeButtons();

        //initizalizing controls object
        controls = new Controls(board);
    }

    void initializeButtons()
    {
        //start_stop button
        side_pane.start_stop.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                JButton b = (JButton)e.getSource();
                //start game
                if((b.getText()).equals("Start Game"))
                {
                    startGame();
                    b.setText("End Game");
                }
                else
                {
                    endGame();
                    b.setText("Start Game");
                    if((side_pane.pause_resume.getText()).equals("Resume"))
                        side_pane.pause_resume.setText("Pause");
                }
                
            }
        });

        //pause_resume button
        side_pane.pause_resume.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                JButton b = (JButton)e.getSource();
                if((b.getText()).equals("Pause") && game_status == Status.ONGOING)
                {
                    pauseGame();
                    b.setText("Resume");
                }
                else if((b.getText()).equals("Resume") && game_status == Status.PAUSED)
                {
                    resumeGame();
                    b.setText("Pause");
                }
            }
        });
    }

    void startGame()
    {
        current_score = 0;
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
    }

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

    void endGame()
    {
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
        current_score = 0;
    }

    void pauseGame()
    {
        timer.stop();

        //setting game status
        game_status = Status.PAUSED;

        //removing controls
        removeControls();
    }

    void resumeGame()
    {
        timer.start();

        //setting game status
        game_status = Status.ONGOING;

        //adding controls
        addControls();
    }

}
