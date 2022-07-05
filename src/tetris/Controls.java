package tetris;

import java.awt.event.*;

class Controls extends KeyAdapter
{
    Board board;
    Controls(Board b)
    {
        board = b;
    }
    
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        int key = e.getKeyCode();

        //move right
        if(key == KeyEvent.VK_KP_RIGHT || key == KeyEvent.VK_RIGHT)
        {
            if(board.curr_piece.currPos[1] != 9)
            {
                board.curr_piece.currPos[1]++;
                board.repaint();
            } 
        }

        //move left
        if(key == KeyEvent.VK_KP_LEFT || key == KeyEvent.VK_LEFT)
        {
            if(board.curr_piece.currPos[1] != 0)
            {
                board.curr_piece.currPos[1]--;
                board.repaint();
            } 
        }

        //rotate left

        //rotate right

        //hard drop

        //soft drop
        if(key == KeyEvent.VK_KP_DOWN || key == KeyEvent.VK_DOWN)
        {
            if(board.curr_piece.currPos[0] != 19)
            {
                board.curr_piece.currPos[0]++;
                board.repaint();
            } 
        }
    }
}
