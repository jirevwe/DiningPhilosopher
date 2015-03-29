package diningphilosopher;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JLabel;

/**
 * Represents a philosopher
 * @author Steel
 */
public class Philosopher extends JLabel implements Runnable{
    
    final Chopstick leftChopstick; //left Chopstick
    final Chopstick rightChopstick; //right Chopstick    
    /**
     * the philosophers possible activities or states
     */
    private enum state {
        EATING,
        THINKING
    };
    
    private final int id;              //the philosopher's id number
    private state curPhilosopherState;           //the philosopher's current activity or state
    private boolean hasLeft = false;   //check if the current philosopher has the left fork
    private boolean hasRight = false;  //check if the current philosopher has the right fork
    
    /**
     * creates a new Philosopher
     * @param id The philosopher's id number
     * @param left The Chopstick on the philosopher's left
     * @param right The Chopstick on the philosopher's right
     */
    public Philosopher(int id, Chopstick left, Chopstick right){
        this.leftChopstick = left;
        this.rightChopstick = right;
        curPhilosopherState = state.THINKING;  //set the initial activity of the philosopher to thinking
        this.id = id;
    }
    
    /**
     * sets the current activity of the philosopher
     * 
     * @param theState the current state of the philosopher
     */
    public void setState(state theState){
        curPhilosopherState = theState;
    }
    
    /**
     *  gets the current activity of the philosopher
     * @return the current activity of the philosopher
     */
    public state getState(){
        return curPhilosopherState;
    } 
    
    /**
     * gets the philosopher's id number
     * @return the philosopher's id number
     */
    public int getId(){
        return this.id;
    }  
    
    @Override
    public void run() {
        try {
            Thread.sleep(getRandom());    //wait for a while before trying to EAT
        } catch (InterruptedException ex) {
        }
        while(true){        //continue forever
            eat();
            think();
        }
    }

    @Override
    public String toString() {
        return this.curPhilosopherState.toString();
    }
    
    /**
     * gets a random time that helps all the philosopher's eat and think at random times
     * @return a new number each time
     */
    public int getRandom(){
        return (int) (Math.random() * 10000);
    } 
   
    /**
     * try to pick up the left Chopstick 
     * @param chopstick 
     */
    public void pickLeftUpChopstick(Chopstick chopstick){
        hasLeft = chopstick.carry();
    }
    
    /**
     * try to pick up the right Chopstick
     * @param chopstick 
     */
    public void pickRightUpChopstick(Chopstick chopstick){
        hasRight = chopstick.carry();
    }
    
    /**
     * drop the left Chopstick
     * @param chopstick 
     */
    public void dropLeftChopstick(Chopstick chopstick){
        chopstick.drop();
        hasLeft = false;
    }
    
    /**
     * drop the right Chopstick
     * @param chopstick 
     */
    public void dropRightChopstick(Chopstick chopstick){
        chopstick.drop();
        hasRight = false;
    }
    
    /**
     * try to eat, first check if you have both forks, if you do eat, else continue thinking 
     */
    public void eat(){
        try {
            if(curPhilosopherState == state.THINKING){           //if you are THINKING, try to eat
                this.pickLeftUpChopstick(leftChopstick);         //try to pick up left Chopstick
                if(hasLeft){                                     //if you picked up the left Chopstick
                    this.pickRightUpChopstick(rightChopstick);   //try to pick up the right Chopstick
                    if(hasRight){                                //if you have the right Chopstick
                        this.setState(state.EATING);             //then EAT
                        this.repaint();
                    }
                    else{
                        dropLeftChopstick(leftChopstick);       //if you couldn't pick up the left Chopstick, drop if back down
                    }
                }
            }
            Thread.sleep(getRandom());                           //THINK or EAT for a while
        } catch (InterruptedException ex) {
        }
    }
    
    /**
     * think, first drop all the forks (if you had any), or continue thinking if you didn't
     */
    public void think(){
        if(hasLeft){                                               //if you had the left Chopstick, drop it
            dropLeftChopstick(leftChopstick);                      //drop the left Chopstick with you
            if(hasRight){                                          //if you have the right Chopstick, drop it
                dropRightChopstick(rightChopstick);                //drop the right with you
            }
        }
        try {
            if(curPhilosopherState == state.EATING){              //if you are EATING
                if(!hasLeft){                           //if you dont have the left Chopstick with you
                    if(!hasRight){                      //if you dont have the right Chopstick with you
                    this.setState(state.THINKING);      //start THINKING
                    this.repaint();
                    }
                }
            }
            Thread.sleep(getRandom());                   //THINK for a while
        } catch (InterruptedException ex) {
        }
    }
    
    @Override
    public void paintComponent(Graphics g){
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        g.setColor(Color.LIGHT_GRAY);
        g.fillOval(0, 10, 85, 50);
        switch(curPhilosopherState){
            case EATING:
                g.setColor(Color.RED);
                g.drawString(this.toString(), 10, 40);
                break;
            case THINKING:
                g.setColor(Color.getHSBColor(105, 67, 17));
                g.drawString(this.toString(), 5, 40);
                break;
        }
    }
}
