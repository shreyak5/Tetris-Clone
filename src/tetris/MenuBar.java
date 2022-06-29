package tetris;

import javax.swing.*;

class MenuBar extends JMenuBar
{   
    JMenu game;
    JMenuItem newGame, pause, resume, exit;

    JMenu help;
    JMenuItem instructions;

    MenuBar()
    {
        //Creating menus
        game = new JMenu("Game");
        help = new JMenu("Help");

        //Menu items for Game menu
        newGame = new JMenuItem("New game");
        pause = new JMenuItem("Pause");
        resume = new JMenuItem("Resume");
        exit = new JMenuItem("Exit");
        
        game.add(newGame);
        game.add(pause);
        game.add(resume);
        game.add(exit);

        //Menu item for Help menu
        instructions = new JMenuItem("Instructions");
        help.add(instructions);

        //Adding the menus to the menubar
        add(game);
        add(help);
    }
}
