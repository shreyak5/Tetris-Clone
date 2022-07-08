package tetris;

import java.awt.*;
import java.util.Hashtable;
import java.util.Random;

class Tetromino 
{
    //This code is executed only once, i.e when the class is loaded into the memory
    static enum Shape {O_shape, I_shape, J_shape, L_shape, S_shape, Z_shape, T_shape};
    static Hashtable<Shape, Color> Colours = new Hashtable<Shape, Color>();
    static
    {
        Colours.put(Shape.O_shape, Color.RED);
        Colours.put(Shape.I_shape, Color.BLUE);
        Colours.put(Shape.J_shape, Color.GREEN);
        Colours.put(Shape.L_shape, Color.YELLOW);
        Colours.put(Shape.S_shape, Color.PINK);
        Colours.put(Shape.Z_shape, Color.ORANGE);
        Colours.put(Shape.T_shape, Color.MAGENTA);
    }
    Random rand = new Random();

    //Tetromino instance variables
    Shape piece_shape;
    Color piece_colour;
    int curr_orientation;
    int possible_orientations[][][];
    int curr_pos[];
    // int maxLeft, maxRight, maxDown;   

    //Constructor
    Tetromino(Shape shape)
    {
        piece_shape = shape;
        piece_colour = Colours.get(shape);
        
        switch(piece_shape)
        {
            case O_shape:
                initOshape();
                break;
            case I_shape:
                initIshape();
                break;
            case J_shape:
                initJshape();
                break;
            case L_shape:
                initLshape();
                break;
            case S_shape:
                initSshape();
                break;
            case Z_shape:
                initZshape();
                break;
            case T_shape:
                initTshape();
                break;
        }

    }

    //Functions used to initialize different tetromino shapes
    void initOshape()
    {
        possible_orientations = new int[][][]{{{0, 0}, {0, 1},{1, 0},{1, 1}}};
        curr_orientation = 0;
    }

    void initIshape()
    {
        possible_orientations = new int[][][]{
            {{0, -2}, {0, -1}, {0, 0}, {0, 1}},
            {{-2, 0}, {-1, 0}, {0, 0}, {1, 0}}
        };
        curr_orientation = rand.nextInt(0, possible_orientations.length);
    }

    void initJshape()
    {
        possible_orientations = new int[][][]{
            {{0, -1}, {0, 0}, {0, 1}, {1, 1}},
            {{1, -1}, {1, 0}, {0, 0}, {-1, 0}},
            {{-1, -1}, {0, -1}, {0, 0}, {0, 1}},
            {{-1, 0}, {-1, -1}, {0, 0}, {1, 0}}
        };
        curr_orientation = rand.nextInt(0, possible_orientations.length);
    }

    void initLshape()
    {
        possible_orientations = new int[][][]{
            {{1, -1}, {0, -1}, {0, 0}, {0, 1}},
            {{-1, -1}, {-1, 0}, {0, 0}, {1, 0}},
            {{0, -1}, {0, 0}, {0, 1}, {-1, 1}},
            {{-1, 0}, {0, 0}, {1, 0}, {1, 1}}
        };
        curr_orientation = rand.nextInt(0, possible_orientations.length);
    }

    void initSshape()
    {
        possible_orientations = new int[][][]{
            {{1, -1}, {1, 0}, {0, 0}, {0, 1}},
            {{-1, 0}, {0, 0}, {0, 1}, {1, 1}}
        };
        curr_orientation = rand.nextInt(0, possible_orientations.length);
    }

    void initZshape()
    {
        possible_orientations = new int[][][]{
            {{0, -1}, {0, 0}, {1, 0}, {1, 1}},
            {{-1, 1}, {0, 1}, {0, 0}, {1, 0}},
        };
        curr_orientation = rand.nextInt(0, possible_orientations.length);
    }

    void initTshape()
    {
        possible_orientations = new int[][][]{
            {{0, -1}, {0, 0}, {0, 1}, {1, 0}},
            {{0, -1}, {0, 0}, {-1, 0}, {1, 0}},
            {{-1, 0}, {0, 0}, {0, -1}, {0, 1}},
            {{-1, 0}, {0, 0}, {0, 1}, {1, 0}}
        };
        curr_orientation = rand.nextInt(0, possible_orientations.length);
    }

    // public static void main(String args[])
    // {
    //     new Tetromino(Shape.I_shape);
    // }

}
