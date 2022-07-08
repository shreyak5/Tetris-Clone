package tetris;

import java.awt.event.*;

import javax.swing.Timer;
import java.util.Random;

class Game 
{
    static enum Status{ONGOING, PAUSED, NONE};
    static Status game_status = Status.NONE;

    private int current_score;
    private Timer timer;

    
    int minY;
    Board board;
    static Random rand = new Random();
    Controls controls;

    //Constructor for new Game
    Game(Board b)
    {
        board = b;
        current_score = 0;
        game_status = Status.ONGOING;
        minY = 20;

        //initializing timer
        timer = new Timer(1000, new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                board.curr_piece.curr_pos[0]++;
                if(!board.checkPosition())
                {
                    board.curr_piece.curr_pos[0]--;
                    board.changePiece();
                }
                board.repaint();
            }
        });

        //initizalizing controls object
        controls = new Controls(board);
        startGame();
    }

    void startGame()
    {
        //Initializing curr_piece and next_piece
        int r = rand.nextInt(0, Tetromino.Shape.values().length);
        board.curr_piece = new Tetromino(Tetromino.Shape.values()[r]);

        r = rand.nextInt(0, Tetromino.Shape.values().length);
        board.next_piece = new Tetromino(Tetromino.Shape.values()[r]);

        //initializing current position of current piece
        board.curr_piece.curr_pos = new int[]{1, 4};
        if(board.curr_piece.piece_shape == Tetromino.Shape.I_shape && board.curr_piece.curr_orientation == 1)
            board.curr_piece.curr_pos[0] = 2;
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


}
