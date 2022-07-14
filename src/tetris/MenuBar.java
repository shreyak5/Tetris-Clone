package tetris;

import javax.swing.*;

class MenuBar extends JMenuBar
{   
    
    private JMenu game, help;
    JMenuItem new_game, pause, resume, exit;
    JMenuItem instructions;

    MenuBar()
    {
        //Creating menus
        game = new JMenu("Game");
        help = new JMenu("Help");

        //Menu items for Game menu
        new_game = new JMenuItem("New game");
        pause = new JMenuItem("Pause");
        resume = new JMenuItem("Resume");
        exit = new JMenuItem("Exit");
        
        game.add(new_game);
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
