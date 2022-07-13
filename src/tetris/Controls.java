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
        board.curr_piece.curr_pos[1]++;

        //checking if it's a valid position
        if(board.checkPosition())
            board.repaint();
        else
            board.curr_piece.curr_pos[1]--;
 
    }

    void moveLeft()
    {
        board.curr_piece.curr_pos[1]--;

        //checking if it's a valid position
        if(board.checkPosition())
            board.repaint();
        else
            board.curr_piece.curr_pos[1]++;
    }

    void rotateRight()
    {
        int size = board.curr_piece.possible_orientations.length;
        int orientation = board.curr_piece.curr_orientation;
        orientation = (orientation + 1) % size;
        board.curr_piece.curr_orientation = orientation;

        //checking if the new orientation is valid
        if(board.checkPosition())
            board.repaint();
        else
        {
            orientation = (orientation - 1 + size) % size;
            board.curr_piece.curr_orientation = orientation;
        }

    } 

    void rotateLeft()
    {
        int size = board.curr_piece.possible_orientations.length;
        int orientation = board.curr_piece.curr_orientation;
        orientation = (orientation - 1 + size) % size;
        board.curr_piece.curr_orientation = orientation;

        //checking if the new orientation is valid
        if(board.checkPosition())
            board.repaint();
        else
        {
            orientation = (orientation + 1) % size;
            board.curr_piece.curr_orientation = orientation;
        }
    }

    void hardDrop()
    {
        //wont hard drop if the piece isn't fully displayed on the grid
        if(!board.checkPosition())
            return;
        
        int prev = board.curr_piece.curr_pos[0];
        while(board.checkPosition())
        {
            prev = board.curr_piece.curr_pos[0];
            board.curr_piece.curr_pos[0]++;
        }
        board.curr_piece.curr_pos[0] = prev;
        board.changePiece();
        board.repaint();
    }

    void softDrop()
    {
        board.curr_piece.curr_pos[0]++;

        //checking if the position is valid
        if(board.checkPosition())
            board.repaint();
        else
        {
            board.curr_piece.curr_pos[0]--;
            board.changePiece();
        }
    }

}
