package yahtzee;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

public class Die {
	
	private static boolean GameOn = false;
	public static ImageIcon[] faceImg = new ImageIcon[6];
	private static ImageIcon faceOne = new ImageIcon(ClassLoader.getSystemResource("side1.png"));
	private static ImageIcon faceTwo = new ImageIcon(ClassLoader.getSystemResource("side2.png"));
	private static ImageIcon faceThree = new ImageIcon(ClassLoader.getSystemResource("side3.png"));
	private static ImageIcon faceFour = new ImageIcon(ClassLoader.getSystemResource("side4.png"));
	private static ImageIcon faceFive = new ImageIcon(ClassLoader.getSystemResource("side5.png"));
	private static ImageIcon faceSix = new ImageIcon(ClassLoader.getSystemResource("side6.png"));
	private static int width = 50;
	
	private boolean locked = false;
	private int sides = 6;
	private int sideUp = 1;
			
	public ImageIcon currentUp;
	public JButton btn_die;
	
	public Die(int xPos, int yPos){
		faceImg[0] = faceOne;
		faceImg[1] = faceTwo;
		faceImg[2] = faceThree;
		faceImg[3] = faceFour;
		faceImg[4] = faceFive;
		faceImg[5] = faceSix;
		
		currentUp = faceOne;
		
		btn_die = new JButton(currentUp);
		btn_die.setPreferredSize(new Dimension(42, 42));
		btn_die.setBounds(xPos, yPos, 50, 50);
		btn_die.setBorder(null);
		btn_die.setOpaque(false);
		btn_die.setContentAreaFilled(false);
		btn_die.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (GameOn == true) {
					if (getLocked() == false) {
						setLocked(true);
						btn_die.setBorder(new LineBorder(Color.YELLOW, 3));
					} else {
						setLocked(false);
						btn_die.setBorder(null);
					}
				}
			}
		});
	}

	public boolean getLocked() {
		return this.locked;
	}
	
	public static int getSize(){
		return width;
	}
	
	public int getSideUp() {
		return this.sideUp;
	}
	

	public void setGameOn(boolean x) {
		Die.GameOn = x;
	}
	
	public void setLocked(boolean x) {
		this.locked = x;
		if (x) {btn_die.setBorder(new LineBorder(Color.YELLOW, 3));}
		else btn_die.setBorder(null);
	}
	
	public void setNumSides(int x){
		this.sides = x;
	}
	
	public void setSideUp(int x) {
		this.sideUp = x;
		currentUp = faceImg[x-1];
		update();
	}
	

	int counter = 13; // the duration
	Timer timer = null; // initialize
	
	public void setRoll() {
		
	    int delay = 95; // every .095 seconds
	    counter = 13; // reset counter for next button click
	    
		ActionListener action = new ActionListener()
        {        	
            @Override
            public void actionPerformed(ActionEvent event)
            {            	
                if(counter <= 0) // hit end of timer and stop
                {
                    timer.stop();
                }
                else // roll and countdown
                {
                    roll();
                    update();
                    counter--; // decrement countdown
                }
            }
        };

        timer = new Timer(delay, action); // set up timer
        timer.setInitialDelay(0);
        timer.start();
	}
	
	public int roll() {
        Random r = new Random();
        this.setSideUp(r.nextInt(this.sides) + 1);
		return this.sideUp;
	}
	
	public void update() {
		btn_die.setIcon(currentUp);
	}
	
	public void reset(){
		setSideUp(1);
		setLocked(false);
	}
	
}
