/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diningphilosopher;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * Main Class that handles the creation and execution of all the threads
 * 
 * @author Steel
 */
public final class DiningPhilosopher extends JFrame {
    
    private final Philosopher men[];
    private final Chopstick chopsticks[];
    
    private final JPanel board;
    private final GridLayout gridLayout; 
    
    public DiningPhilosopher(){
        this.men = new Philosopher[5];
        this.chopsticks = new Chopstick[5];
        
        for(int i = 0;i < 5;i++){
            chopsticks[i] = new Chopstick(i);  //create the five Chopstick
        }
        
        for(int i = 0;i < 5;i++){
            men[i] = new Philosopher(i + 1, chopsticks[(i + 6 - 1)% 5], chopsticks[(i + 6)% 5]);  //create the five philosophers
        }
        
        gridLayout = new GridLayout(3, 5, 10, 100);
        board = new JPanel(gridLayout);
        
        JLabel j = new JLabel("TABLE", SwingConstants.CENTER);
        j.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
        
        board.add(new JLabel());
        board.add(men[0]);
        board.add(new JLabel());
        board.add(men[1]);
        board.add(new JLabel());
        board.add(men[4]);
        board.add(new JLabel());
        board.add(j);
        board.add(new JLabel());
        board.add(men[2]);
        board.add(new JLabel());
        board.add(new JLabel());
        board.add(men[3]);
        board.add(new JLabel());
        board.add(new JLabel());
        
        this.add(board);
        
        this.setTitle("DINING PHILOSOPHER'S PROBLEM");
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args){
        final DiningPhilosopher d = new DiningPhilosopher();
        final ExecutorService spaghetti = Executors.newCachedThreadPool();  // runs all the threads in the pool at the same time
        
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                for(int i = 0;i < 5;i++){
                    spaghetti.execute(d.men[i]);
                }
                d.setVisible(true);
                spaghetti.shutdown();
            }
        });
    }
    
    @Override
    public void paint(Graphics g) {
        paintComponents(g);
        repaint();
    }
    
}
