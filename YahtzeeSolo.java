// add sidebar with move ouputs
// needs setup, end game message, new game reset
// DONE -- needs Hold All / single deselect fix -- Set up timer to check for condition
// shrink layout?  add hover scoring rules?

package yahtzee;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class YahtzeeSolo extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private static Die[] dicepack = new Die[5];
	private static JCheckBox[] checkboxes;
	private static JTextField[] scoreboxes;
	
	int rolls;
	int playerTotalScore, playerSubUpperScore, playerUpperScore, playerSubLowerScore, playerLowerScore, playerUpperBonusScore, playerYahtzeeBonusScore;
	boolean allLocked = true;
	
	private ImageIcon dialogIconImg = new ImageIcon(ClassLoader.getSystemResource("dialogIcon.png"));
	private ImageIcon endTurnImg = new ImageIcon(ClassLoader.getSystemResource("endTurn.png"));
	private ImageIcon rollImg = new ImageIcon(ClassLoader.getSystemResource("roll.png"));
	private ImageIcon rollPressedImg = new ImageIcon(ClassLoader.getSystemResource("rollClick.png"));
	private JPanel contentPane;
	private JTextField playerOnes, playerTwos, playerThrees, playerFours, playerFives, playerSixes, playerThreeKind,
		playerFourKind, playerFullHouse, playerSmallStr, playerLargeStr, playerYahtzee, playerChance, playerSubUpper,
		playerUpperBonus, playerUpperTotal, playerLower, playerTotal;
	
	public JButton btn_hold = new JButton("Hold All");
	public JButton btn_newRoll = new JButton("New Roll");
	public JButton btn_roll = new JButton(rollImg);
	public JLabel lbl_click = new JLabel("Click die to hold/release");
	public JLabel lbl_turnOver = new JLabel("TURN OVER - Click 'New Roll' to start new");	
	public JLabel lbl_rollNumber = new JLabel("Roll Number: " + rolls);
		

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		int xPos = 50;
		
		for(int i = 0; i < dicepack.length; i++) {
			dicepack[i] = new Die(xPos, 50);
			xPos = xPos + 60;
		};		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					YahtzeeSolo frame = new YahtzeeSolo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public YahtzeeSolo() {
		
		int WIDTH = 400;
				
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		for(int i = 0; i < dicepack.length; i++) {
			contentPane.add(dicepack[i].btn_die);
		}
		
//START Timer checkLocked
		class checkLocked extends TimerTask{
			public void run(){
				allLocked = true;
				for(Die dice : dicepack){
					if(dice.getLocked() == false) allLocked = false;
				}
				if(allLocked == false) btn_roll.setIcon(rollImg);
				else btn_roll.setIcon(endTurnImg);
			}
		}
		
		Timer timer = new Timer(true);
		timer.schedule(new checkLocked(), 0 , 100);
		
//END Timer
		
		btn_hold.setForeground(Color.RED);
		
		lbl_rollNumber.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_rollNumber.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbl_rollNumber.setSize(150, 20);
		lbl_rollNumber.setLocation((WIDTH / 2) - (lbl_rollNumber.getWidth() / 2), 20);
		contentPane.add(lbl_rollNumber);
		
		lbl_click.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_click.setSize(300, 20);
		lbl_click.setLocation((WIDTH / 2) - (lbl_click.getWidth() / 2), 110);
		lbl_click.setVisible(false);
		contentPane.add(lbl_click);
		
		lbl_turnOver.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_turnOver.setSize(350, 20);
		lbl_turnOver.setLocation((WIDTH / 2) - (lbl_turnOver.getWidth() / 2), 110);
		lbl_turnOver.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbl_turnOver.setForeground(Color.RED);
		lbl_turnOver.setVisible(false);
		contentPane.add(lbl_turnOver);
		
		btn_hold.setSize(80, 20);
		btn_hold.setLocation(160, 131);
		btn_hold.setBackground(Color.ORANGE);
		btn_hold.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		btn_hold.setBorder(null);
		btn_hold.setOpaque(false);
		btn_hold.setContentAreaFilled(false);
		btn_hold.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn_hold.setVisible(false);
		btn_hold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(allLocked) {
					for(Die die : dicepack) {
						die.setLocked(false);
						die.btn_die.setBorder(null);
					}
				}
				else {
					for(int i = 0; i < dicepack.length; i++) {
						dicepack[i].setLocked(true);
						dicepack[i].btn_die.setBorder(new LineBorder(Color.YELLOW, 3));
					}
				}
			}
		});
		contentPane.add(btn_hold);
		
//START Scorecards

		//START Checkboxes

		JCheckBox chckbxAces = new JCheckBox("Aces");
		chckbxAces.setFont(new Font("Tahoma", Font.BOLD, 13));
		chckbxAces.setBounds(5, 265, 97, 20);
		contentPane.add(chckbxAces);
		chckbxAces.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField thisText = playerOnes;
				upperCount(1, thisText);
				checkOver();
			}
		});
		
		JCheckBox chckbxTwos = new JCheckBox("Twos");
		chckbxTwos.setFont(new Font("Tahoma", Font.BOLD, 13));
		chckbxTwos.setBounds(5, 290, 97, 20);
		contentPane.add(chckbxTwos);
		chckbxTwos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField thisText = playerTwos;
				upperCount(2, thisText);
				checkOver();
			}
		});
		
		JCheckBox chckbxThrees = new JCheckBox("Threes");
		chckbxThrees.setFont(new Font("Tahoma", Font.BOLD, 13));
		chckbxThrees.setBounds(5, 315, 97, 20);
		contentPane.add(chckbxThrees);
		chckbxThrees.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField thisText = playerThrees;
				upperCount(3, thisText);
				checkOver();
			}
		});
		
		JCheckBox chckbxFours = new JCheckBox("Fours");
		chckbxFours.setFont(new Font("Tahoma", Font.BOLD, 13));
		chckbxFours.setBounds(5, 340, 97, 20);
		contentPane.add(chckbxFours);
		chckbxFours.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField thisText = playerFours;
				upperCount(4, thisText);
				checkOver();
			}
		});
		
		JCheckBox chckbxFives = new JCheckBox("Fives");
		chckbxFives.setFont(new Font("Tahoma", Font.BOLD, 13));
		chckbxFives.setBounds(5, 365, 97, 20);
		contentPane.add(chckbxFives);
		chckbxFives.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField thisText = playerFives;
				upperCount(5, thisText);
				checkOver();
			}
		});
		
		JCheckBox chckbxSixes = new JCheckBox("Sixes");
		chckbxSixes.setFont(new Font("Tahoma", Font.BOLD, 13));
		chckbxSixes.setBounds(5, 390, 97, 20);
		contentPane.add(chckbxSixes);
		chckbxSixes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField thisText = playerSixes;
				upperCount(6, thisText);
				checkOver();
			}
		});
				
		JCheckBox chckbxThreeKind = new JCheckBox("3 of a Kind");
		chckbxThreeKind.setFont(new Font("Tahoma", Font.BOLD, 13));
		chckbxThreeKind.setBounds(5, 500, 97, 23);
		contentPane.add(chckbxThreeKind);
		chckbxThreeKind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int sum = findTrips();
				JTextField thisText = playerThreeKind;
				takeZero(chckbxThreeKind, thisText, sum);
				checkOver();
			}
		});
		
		JCheckBox chckbxFourKind = new JCheckBox("4 of a Kind");
		chckbxFourKind.setFont(new Font("Tahoma", Font.BOLD, 13));
		chckbxFourKind.setBounds(5, 525, 97, 20);
		contentPane.add(chckbxFourKind);
		chckbxFourKind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int sum = findQuads();
				JTextField thisText = playerFourKind;
				takeZero(chckbxFourKind, thisText, sum);
				checkOver();
			}
		});
		
		JCheckBox chckbxFullHouse = new JCheckBox("Full House");
		chckbxFullHouse.setFont(new Font("Tahoma", Font.BOLD, 13));
		chckbxFullHouse.setBounds(5, 550, 97, 20);
		contentPane.add(chckbxFullHouse);
		chckbxFullHouse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField thisText = playerFullHouse;
				if(findDubs() == true && findTrips() != 0 && 
						findQuads() == 0) {
					int sum = 25;
					scoreActionLower(chckbxFullHouse, thisText, sum);
				}
				else{
					int sum = 0;
					takeZero(chckbxFullHouse, thisText, sum);
				};
				checkOver();
			}
		});
		
		JCheckBox chckbxSmallStr = new JCheckBox("Sm Straight");
		chckbxSmallStr.setFont(new Font("Tahoma", Font.BOLD, 13));
		chckbxSmallStr.setBounds(5, 575, 114, 20);
		contentPane.add(chckbxSmallStr);
		chckbxSmallStr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int sum = checkStraight();
				JTextField thisText = playerSmallStr;
				if(sum == 40) sum = 30;
				takeZero(chckbxSmallStr, thisText, sum);
				checkOver();
			}
		});
		
		JCheckBox chckbxLargeStr = new JCheckBox("Lg Straight");
		chckbxLargeStr.setFont(new Font("Tahoma", Font.BOLD, 13));
		chckbxLargeStr.setBounds(5, 600, 114, 20);
		contentPane.add(chckbxLargeStr);
		chckbxLargeStr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int sum = checkStraight();
				JTextField thisText = playerLargeStr;
				if(sum == 30) sum = 0;
				takeZero(chckbxLargeStr, thisText, sum);
				checkOver();
			}
		});
		
		// set up first Yahtzee and second Yahtzee
		// if first Yahtzee is disabled and 5 of a kind showing, yahtzee enabled again, adds to sum
		JCheckBox chckbxYahtzee = new JCheckBox("Yahtzee");
		chckbxYahtzee.setFont(new Font("Tahoma", Font.BOLD, 13));
		chckbxYahtzee.setBounds(5, 625, 114, 20);
		contentPane.add(chckbxYahtzee);
		chckbxYahtzee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int sum = 0;
				if(findYahtzee() == true) sum = 50;
				JTextField thisText = playerYahtzee;
				takeZero(chckbxYahtzee, thisText, sum);
				checkOver();
			}
		});
		
		JCheckBox chckbxChance = new JCheckBox("Chance");
		chckbxChance.setFont(new Font("Tahoma", Font.BOLD, 13));
		chckbxChance.setBounds(5, 650, 114, 20);
		contentPane.add(chckbxChance);
		chckbxChance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int sum = 0;
				JTextField thisText = playerChance;
				for(Die dice : dicepack){
					sum += dice.getSideUp();
				}
				takeZero(chckbxChance, thisText, sum);
				checkOver();
			}
		});
		
		checkboxes = new JCheckBox[] {chckbxAces, chckbxTwos, chckbxThrees, chckbxFours, chckbxFives, chckbxSixes,
				chckbxThreeKind, chckbxFourKind, chckbxFullHouse, chckbxSmallStr, chckbxLargeStr, chckbxYahtzee,
				chckbxChance};
		
		for(JCheckBox checkboxes : checkboxes) {
			checkboxes.setEnabled(false);
		}
		

	//END Checkboxes
		
		JLabel lblAddAllAces = new JLabel("Add all aces");
		lblAddAllAces.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddAllAces.setBounds(110, 265, 102, 20);
		contentPane.add(lblAddAllAces);
		
		JLabel lblAddAllTwos = new JLabel("Add all twos");
		lblAddAllTwos.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddAllTwos.setBounds(110, 290, 102, 20);
		contentPane.add(lblAddAllTwos);
		
		JLabel lblAddAllThrees = new JLabel("Add all threes");
		lblAddAllThrees.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddAllThrees.setBounds(110, 315, 102, 20);
		contentPane.add(lblAddAllThrees);
		
		JLabel lblAddAllFours = new JLabel("Add all fours");
		lblAddAllFours.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddAllFours.setBounds(110, 340, 102, 20);
		contentPane.add(lblAddAllFours);
		
		JLabel lblAddAllFives = new JLabel("Add all fives");
		lblAddAllFives.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddAllFives.setBounds(110, 365, 102, 20);
		contentPane.add(lblAddAllFives);
		
		JLabel lblAddAllSxies = new JLabel("Add all sixes");
		lblAddAllSxies.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddAllSxies.setBounds(110, 390, 102, 20);
		contentPane.add(lblAddAllSxies);
		

		//START Player Score Boxes

		
		playerOnes = new JTextField();
		playerOnes.setBounds(235, 265, 125, 20);
		setScorebox(playerOnes);
		
		playerTwos = new JTextField();
		playerTwos.setBounds(235, 290, 125, 20);
		setScorebox(playerTwos);
		
		playerThrees = new JTextField();
		playerThrees.setBounds(235, 315, 125, 20);
		setScorebox(playerThrees);
		
		playerFours = new JTextField();
		playerFours.setBounds(235, 340, 125, 20);
		setScorebox(playerFours);

		playerFives = new JTextField();
		playerFives.setBounds(235, 365, 125, 20);
		setScorebox(playerFives);
		
		playerSixes = new JTextField();
		playerSixes.setBounds(235, 390, 125, 20);
		setScorebox(playerSixes);
		
		playerSubUpper = new JTextField();
		playerSubUpper.setBounds(235, 415, 125, 20);
		setScorebox(playerSubUpper);
		
		playerUpperBonus = new JTextField();
		playerUpperBonus.setBounds(235, 440, 125, 20);
		setScorebox(playerUpperBonus);
		
		playerUpperTotal = new JTextField();
		playerUpperTotal.setBounds(235, 465, 125, 20);
		setScorebox(playerUpperTotal);
		
		playerThreeKind = new JTextField();
		playerThreeKind.setBounds(235, 500, 125, 20);
		setScorebox(playerThreeKind);
		
		playerFourKind = new JTextField();
		playerFourKind.setBounds(235, 525, 125, 20);
		setScorebox(playerFourKind);
		
		playerFullHouse = new JTextField();
		playerFullHouse.setBounds(235, 550, 125, 20);
		setScorebox(playerFullHouse);
		
		playerSmallStr = new JTextField();
		playerSmallStr.setBounds(235, 575, 125, 20);
		setScorebox(playerSmallStr);
		
		playerLargeStr = new JTextField();
		playerLargeStr.setBounds(235, 600, 125, 20);
		setScorebox(playerLargeStr);
		
		playerYahtzee = new JTextField();
		playerYahtzee.setBounds(235, 625, 125, 20);
		setScorebox(playerYahtzee);
		
		playerChance = new JTextField();
		playerChance.setBounds(235, 650, 125, 20);
		setScorebox(playerChance);
		
		
		playerLower = new JTextField();
		playerLower.setBounds(235, 675, 125, 20);
		setScorebox(playerLower);
		
		playerTotal = new JTextField();
		playerTotal.setBounds(235, 730, 125, 20);
		setScorebox(playerTotal);

		scoreboxes = new JTextField[] {playerOnes, playerTwos, playerThrees, playerFours,
				playerFives, playerSixes, playerThreeKind, playerFourKind, playerFullHouse,
				playerSmallStr, playerLargeStr, playerYahtzee, playerChance, playerSubUpper,
				playerUpperBonus, playerUpperTotal, playerLower, playerTotal};
		

	//END Player Score Boxes
		
		JLabel lblPlayer = new JLabel("Score");
		lblPlayer.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayer.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPlayer.setBounds(235, 240, 125, 20);
		contentPane.add(lblPlayer);

//END Scorecards
		
		btn_roll.setSize(125, 43);
		btn_roll.setLocation(135, 160);
		btn_roll.setFont(new Font("Tahoma", Font.BOLD, 13));
		btn_roll.setForeground(Color.WHITE);
		btn_roll.setBackground(Color.BLUE);
		btn_roll.setBorder(null);
		btn_roll.setOpaque(false);
		btn_roll.setContentAreaFilled(false);
		btn_roll.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn_roll.setPressedIcon(rollPressedImg);
		btn_roll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				if(rolls == 0) {
					btn_roll.setEnabled(true);
					btn_newRoll.setEnabled(false);
					for(int j = 0; j < dicepack.length; j++){
						dicepack[j].setGameOn(true);
					}
				}
				if(rolls < 3) {
					btn_hold.setVisible(true);
					
					int countLock = 0;
					for(Die Die : dicepack) {
						if(Die.getLocked() == true) {
							countLock++;
						}
					}
					if(countLock == 5) {
						rolls = 2;
					}
					
					else {
//						for(int i = 0; i < dicepack.length; i++){
//							if(i > 2) dicepack[i].setSideUp(1);
//							else dicepack[i].setSideUp(2);
//							dicepack[i].update();
//						}
						for(Die dice : dicepack) {
							if(dice.getLocked() == false) {
								dice.setRoll();
								//dice.setSideUp(2);
								dice.update();								
							}
						}
					}
					rolls++;
					lbl_rollNumber.setText("Roll number: " + rolls);
					lbl_click.setVisible(true);
				}
				if(rolls > 2) {
					lbl_click.setVisible(false);
					lbl_turnOver.setVisible(true);
					btn_hold.setVisible(false);
					btn_roll.setIcon(rollImg);
					btn_roll.setEnabled(false);
					for(int i = 0; i < dicepack.length; i++) {
						dicepack[i].setGameOn(false);
						dicepack[i].btn_die.setBorder(new LineBorder(Color.BLUE, 2));
					}
					for(JCheckBox checkboxes : checkboxes) {
						if(checkboxes.isSelected());
						else checkboxes.setEnabled(true);
					}
				}
			}
		});
		contentPane.add(btn_roll);
		
		btn_newRoll.setSize(100, 20);
		btn_newRoll.setLocation(150, 215);
		btn_newRoll.setEnabled(false);
		btn_newRoll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btn_roll.setEnabled(true);
				btn_roll.setIcon(rollImg);
				btn_newRoll.setEnabled(false);
				lbl_click.setVisible(false);
				lbl_turnOver.setVisible(false);
				rolls = 0;
				lbl_rollNumber.setText("Roll number: " + rolls);
				for(int i = 0; i < dicepack.length; i++) {
					dicepack[i].setLocked(false);					
					dicepack[i].setGameOn(false);
					dicepack[i].btn_die.setBorder(null);
					dicepack[i].setSideUp(1);
					dicepack[i].update();
				}
				for(JCheckBox checkboxes : checkboxes) {
					checkboxes.setEnabled(false);
				}
			}
		});
		contentPane.add(btn_newRoll);
		

		
		JLabel lblThreeKind = new JLabel("Total of All Dice");
		lblThreeKind.setHorizontalAlignment(SwingConstants.CENTER);
		lblThreeKind.setBounds(111, 500, 102, 20);
		contentPane.add(lblThreeKind);
		
		JLabel lblTotal = new JLabel("TOTAL");
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTotal.setBounds(125, 732, 88, 14);
		contentPane.add(lblTotal);
		
		JLabel lblFourKind = new JLabel("Total of All Dice");
		lblFourKind.setHorizontalAlignment(SwingConstants.CENTER);
		lblFourKind.setBounds(111, 525, 102, 20);
		contentPane.add(lblFourKind);
		
		JLabel lblScore = new JLabel("SCORE 25");
		lblScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblScore.setBounds(111, 550, 102, 20);
		contentPane.add(lblScore);
		
		JLabel lblScore_1 = new JLabel("SCORE 30");
		lblScore_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblScore_1.setBounds(111, 575, 102, 20);
		contentPane.add(lblScore_1);
		
		JLabel lblScore_2 = new JLabel("SCORE 40");
		lblScore_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblScore_2.setBounds(111, 600, 102, 20);
		contentPane.add(lblScore_2);

		
		JLabel lblScore_3 = new JLabel("SCORE 50");
		lblScore_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblScore_3.setBounds(111, 625, 102, 20);
		contentPane.add(lblScore_3);
		
		JLabel lblTotalOfAll = new JLabel("Total of All Dice");
		lblTotalOfAll.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalOfAll.setBounds(111, 650, 102, 20);
		contentPane.add(lblTotalOfAll);
		
		JLabel lblSubUpper = new JLabel("UPPER SCORE");
		lblSubUpper.setHorizontalAlignment(SwingConstants.LEFT);
		lblSubUpper.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSubUpper.setBounds(15, 415, 97, 20);
		contentPane.add(lblSubUpper);
		
		JLabel label = new JLabel("---->");
		label.setFont(new Font("Tahoma", Font.BOLD, 13));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(111, 415, 102, 20);
		contentPane.add(label);
		
		JLabel lblBonus = new JLabel("BONUS (If >= 63)");
		lblBonus.setHorizontalAlignment(SwingConstants.LEFT);
		lblBonus.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblBonus.setBounds(15, 440, 114, 20);
		contentPane.add(lblBonus);
		
		JLabel lblScore_4 = new JLabel("SCORE 35");
		lblScore_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblScore_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblScore_4.setBounds(111, 440, 102, 20);
		contentPane.add(lblScore_4);
		
		JLabel lblUpperTotal = new JLabel("UPPER TOTAL");
		lblUpperTotal.setHorizontalAlignment(SwingConstants.LEFT);
		lblUpperTotal.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblUpperTotal.setBounds(15, 465, 97, 20);
		contentPane.add(lblUpperTotal);
		
		JLabel label_4 = new JLabel("---->");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_4.setBounds(112, 465, 102, 20);
		contentPane.add(label_4);
		
		JLabel label_2 = new JLabel("---->");
		label_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(110, 675, 102, 20);
		contentPane.add(label_2);
		
		JLabel lblLowerTotal = new JLabel("LOWER TOTAL");
		lblLowerTotal.setHorizontalAlignment(SwingConstants.LEFT);
		lblLowerTotal.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblLowerTotal.setBounds(15, 675, 97, 20);
		contentPane.add(lblLowerTotal);
		
		JButton btnNewButton = new JButton("New Game");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resetGame();
			}
		});
		btnNewButton.setBounds(5, 11, 110, 23);
		contentPane.add(btnNewButton);	
	
	}
	
	public void refreshSum(){
		checkBonuses();
		playerUpperBonus.setText(Integer.toString(playerUpperBonusScore));
		
		playerSubUpper.setText(Integer.toString(playerSubUpperScore));
		
		playerUpperScore = playerSubUpperScore + playerUpperBonusScore;
		playerUpperTotal.setText(Integer.toString(playerUpperScore));
		
		playerLowerScore = playerSubLowerScore + playerYahtzeeBonusScore;
		playerLower.setText(Integer.toString(playerLowerScore));
		
		playerTotalScore = playerUpperScore + playerLowerScore;
		playerTotal.setText(Integer.toString(playerTotalScore));
		
		btn_newRoll.setEnabled(true);
	}
	
	public void upperCount(int input, JTextField thisText){
		int sum = 0;
		for(Die dice : dicepack) {
			if(dice.getSideUp() == input){
				sum += input;
				}
			}
		thisText.setText(Integer.toString(sum));
		playerSubUpperScore += sum;
		for(JCheckBox checkboxes : checkboxes) {checkboxes.setEnabled(false);}
		refreshSum();
	}
	
	public void checkBonuses(){
		if(playerSubUpperScore >= 63) {playerUpperBonusScore = 35;}
	}
	
	public boolean findDubs(){
		int[] checkNum = {0,0,0,0,0,0};
		boolean dubs = false;
		for(int i = 0; i < dicepack.length; i++) {
			checkNum[dicepack[i].getSideUp() - 1] += 1;
		}
		for(int i = 0; i < checkNum.length; i++){
			if (checkNum[i] == 2){
				dubs = true;
				break;
			}
		}
		return dubs;
	}
	
	public int findTrips(){
		int[] checkNum = {0,0,0,0,0,0};
		int sum = 0;
		boolean trips = false;
		for(int i = 0; i < dicepack.length; i++) {
			checkNum[dicepack[i].getSideUp() - 1] += 1;
		}
		for(int i = 0; i < checkNum.length; i++){
			if (checkNum[i] >= 3){
				trips = true;
				break;
			}
		}
		if(trips == true) {
			for(Die dice : dicepack){
				sum += dice.getSideUp();
			}
		} else sum = 0;
		return sum;
	}
	
	public int findQuads(){
		int[] checkNum = {0,0,0,0,0,0};
		int sum = 0;
		boolean quads = false;
		for(int i = 0; i < dicepack.length; i++) {
			checkNum[dicepack[i].getSideUp() - 1] += 1;
		}
		for(int i = 0; i < checkNum.length; i++){
			if (checkNum[i] >= 4){
				quads = true;
				break;
			}
		}
		if(quads == true) {
			for(Die dice : dicepack){
				sum += dice.getSideUp();
			}
		} else sum = 0;
		return sum;
	}
	
	public boolean findYahtzee(){
		int[] checkNum = {0,0,0,0,0,0};
		boolean yahtzee = false;
		for(int i = 0; i < dicepack.length; i++) {
			checkNum[dicepack[i].getSideUp() - 1] += 1;
		}
		for(int i = 0; i < checkNum.length; i++){
			if (checkNum[i] == 5){
				yahtzee = true;
				break;
			}
		}
		return yahtzee;
	}
	
	public int checkStraight(){
		int[] checkNum = {0,0,0,0,0,0};
		int sum = 0;
		for(Die dice : dicepack) {
			checkNum[dice.getSideUp() - 1] += 1;
		}
		if(checkNum[2] != 0 && checkNum[3] != 0){
			if(checkNum[1] == 1 && checkNum[2] == 1 && checkNum[3] == 1 && checkNum[4] == 1) {sum = 40;}
			else if(
				(checkNum[0] >= 1 && checkNum[1] >= 1) |
				(checkNum[1] >= 1 && checkNum[4] >= 1) |
				(checkNum[4] >= 1 && checkNum[5] >= 1)
				) {sum = 30;}
		}
		return sum;
	}
	
	public void scoreActionLower(JCheckBox input, JTextField thisText, int sum) {
		input.setEnabled(false);
		for(JCheckBox checkboxes : checkboxes) {
			checkboxes.setEnabled(false);
		}
		playerSubLowerScore += sum;
		refreshSum();
		thisText.setText(Integer.toString(sum));
	}
	
	public void takeZero(JCheckBox thisCheck, JTextField thisText, int sum){
		if(sum == 0) {
			if (JOptionPane.showConfirmDialog(this, "Score unavailable - do you want to take a ZERO?", "Are you sure?",
				JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, dialogIconImg) == JOptionPane.YES_OPTION) {
					scoreActionLower(thisCheck, thisText, sum);
				}
			else {thisCheck.setSelected(false);}
			}
		else {
			scoreActionLower(thisCheck, thisText, sum);
		}
	}
	
	public void resetGame(){		
		rolls = 0;

		lbl_rollNumber.setText("Roll number: " + rolls);
		for(Die die : dicepack){
			die.reset();
		}
		for(JCheckBox checkbox : checkboxes){
			checkbox.setSelected(false);
			checkbox.setEnabled(false);
		}
		// can't this to work in an array looP?
		playerSubUpperScore = 0;
		playerUpperBonusScore = 0;
		playerUpperScore = 0;
		playerSubLowerScore = 0;
		playerLowerScore = 0;
		playerTotalScore = 0;
		//
		refreshSum();
		for(JTextField scorebox : scoreboxes){
			scorebox.setText("");
		}
		btn_newRoll.setEnabled(false);
		btn_roll.setEnabled(true);
		lbl_turnOver.setVisible(false);
		btn_hold.setVisible(false);
		lbl_click.setVisible(false);
	}
	
	public void checkOver(){
		boolean allSelected = true;
		for(int i = 0; i < checkboxes.length; i++) {
			if(checkboxes[i].isSelected() == false) allSelected = false;
		}
		if(allSelected == true) {
			btn_roll.setEnabled(false);
			PlayNote.getScale(12, 3);
			JOptionPane.showMessageDialog(this, 
					"                  Game Over!\n\n"
						+"PLAYER FINAL SCORE:  " + playerTotalScore + "\n"
							+ "Press 'New Game' to start new game");
		}
	}
	
	private void setScorebox(JTextField box){
		box.setForeground(Color.GRAY);
		box.setFont(new Font("Tahoma", Font.PLAIN, 13));
		box.setHorizontalAlignment(SwingConstants.CENTER);
		box.setEditable(false);
		box.setColumns(10);
		contentPane.add(box);
	}
}