package tetris;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;
import java.util.Random;

class Game 
{
    static enum Status{ONGOING, PAUSED, NONE};
    static Status game_status = Status.NONE;

    private int current_score;
    private Timer timer;

    static Tetromino curr_piece;
    static Tetromino next_piece;
    int minY;
    Board board;
    Random rand = new Random();

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
                board.curr_piece.currPos[0]++;
                board.repaint();
            }
        });
    
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
        board.curr_piece.currPos = new int[]{1, 4};

        timer.start();
    }
    
}
