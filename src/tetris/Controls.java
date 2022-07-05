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
            moveRight();

        //move left
        else if(key == KeyEvent.VK_KP_LEFT || key == KeyEvent.VK_LEFT)
            moveLeft();

        //rotate right
        else if(key == KeyEvent.VK_KP_UP || key == KeyEvent.VK_UP || key == KeyEvent.VK_D)
            rotateRight();

        //rotate left
        else if(key == KeyEvent.VK_A)
            rotateLeft();

        //hard drop
        else if(key == KeyEvent.VK_SPACE)
            hardDrop();

        //soft drop
        else if(key == KeyEvent.VK_KP_DOWN || key == KeyEvent.VK_DOWN)
            softDrop();
        
    }

    void moveRight()
    {
        int column = board.curr_piece.currPos[1];
        //checking border cases
        if(column != 9)
        {
            column++;
            board.curr_piece.currPos[1] = column;
            board.repaint();
        }    
    }

    void moveLeft()
    {
        int column = board.curr_piece.currPos[1];
        //checking border cases
        if(column != 0)
        {
            column--;
            board.curr_piece.currPos[1] = column;
            board.repaint();
        }
    }

    void rotateRight()
    {
        int size = board.curr_piece.possible_orientations.length;
        int orientation = board.curr_piece.curr_orientation;
        orientation = (orientation + 1) % size;
        board.curr_piece.curr_orientation = orientation;
        board.repaint();
    } 

    void rotateLeft()
    {
        int size = board.curr_piece.possible_orientations.length;
        int orientation = board.curr_piece.curr_orientation;
        orientation = (orientation - 1 + size) % size;
        board.curr_piece.curr_orientation = orientation;
        board.repaint();
    }

    void hardDrop()
    {
        board.curr_piece.currPos[0] = 19;
        board.repaint();
    }

    void softDrop()
    {
        int row = board.curr_piece.currPos[0];
        if(row != 19)
        {
            row++;
            board.curr_piece.currPos[0] = row;
            board.repaint();
        } 
    }

}
