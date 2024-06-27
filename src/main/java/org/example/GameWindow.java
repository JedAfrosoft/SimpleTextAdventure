package org.example;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class GameWindow extends JFrame implements ActionListener {

    Dimension gameScreenSize= new Dimension(600,600);
    JButton startButton=new JButton("Start");
    JLayeredPane layeredPane=new JLayeredPane();


    GameWindow(){
        this.setSize(gameScreenSize);
        this.setTitle("Window");
//        this.setResizable(false);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);


        layeredPane.setBounds(0,0, gameScreenSize.width, gameScreenSize.height);
        layeredPane.setOpaque(true);

        createTitleScreen();
        this.add(layeredPane);
        this.setVisible(true);

        //BG Music
        try {
            playBGMusic();
        }
        catch(Exception e){
            System.out.println("Something went wrong loading music. Here is exception");
            System.out.println(e);
        }




    }

    private void createTitleScreen() {

        JPanel backgroundPanel=new JPanel();
        backgroundPanel.setBounds(0,0, gameScreenSize.width, gameScreenSize.height);
        backgroundPanel.setBackground(Color.BLACK);
        backgroundPanel.setOpaque(true);
        backgroundPanel.setLayout(null);

        JLabel title=new JLabel("Lost");
        title.setBounds(225,0,200,100);
        title.setForeground(Color.WHITE);
        title.setFont(new Font(null,Font.BOLD, 60));
        backgroundPanel.add(title);

        startButton.setBounds(225,300,150,50);
        startButton.setFocusable(false);
        startButton.setBackground(Color.BLACK);
        startButton.setForeground(Color.WHITE);
        startButton.addActionListener(this);
//        startButton.setBorder(BorderFactory.createLineBorder(Color.WHITE,2));
        backgroundPanel.add(startButton);

        layeredPane.add(backgroundPanel,Integer.valueOf(0));


    }

    private void playBGMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File bgMusic=new File("BG Music 2.wav");
        AudioInputStream audioStream= AudioSystem.getAudioInputStream(bgMusic);
        Clip clip= AudioSystem.getClip();
        clip.open(audioStream);//For some reason Clip class does not support 24-bit wav files, so export to 16-bit with FL Studio

        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);

    }

    JButton choice1;
    JButton choice2;
    JButton exitButton;
    JLabel message;
    JPanel screenPanel;

    private void firstScreen(){

        screenPanel=new JPanel();
        screenPanel.setBounds(0,0, gameScreenSize.width, gameScreenSize.height);
        screenPanel.setBackground(Color.BLACK);
        screenPanel.setOpaque(true);
        screenPanel.setLayout(null);

        message=new JLabel();
        message.setBounds(50,50,500,200);
        message.setText("<html>" + "You have awoken in a crater and can barely move. You muster enough strength to get up on your feet and look around." +
                "Nothing is to be seen; No clouds, no sign of life, not even dead matter. It's just an abyss.\n What do you do?" + "</html>");
        message.setForeground(Color.WHITE);
        message.setBackground(Color.DARK_GRAY);
        message.setBorder(BorderFactory.createLineBorder(Color.WHITE,2,true));
        message.setOpaque(true);
        message.setFont(new Font(null,Font.PLAIN,20));
        message.setVerticalAlignment(JLabel.TOP);
        screenPanel.add(message);


        choice1=new JButton("Stay where you are");
        choice1.setBounds(100,300,200,100);
        choice1.setForeground(Color.WHITE);
        choice1.setBackground(Color.BLACK);
        choice1.addActionListener(this);
        choice1.setFocusable(false);
        choice1.setActionCommand("stay");
        screenPanel.add(choice1);


        choice2=new JButton("<html>"+"Start walking in a random direction"+ "</html>");
        choice2.setBounds(300,300,200,100);
        choice2.setForeground(Color.WHITE);
        choice2.setBackground(Color.BLACK);
        choice2.addActionListener(this);
        choice2.setFocusable(false);
        choice2.setActionCommand("go");
        screenPanel.add(choice2);


        exitButton=new JButton("Exit Game");
        exitButton.setForeground(Color.WHITE);
        exitButton.setBounds(250,450,100,50);
        exitButton.setBackground(Color.BLACK);
        exitButton.addActionListener(this);
        exitButton.setFocusable(false);
        screenPanel.add(exitButton);


        layeredPane.add(screenPanel,Integer.valueOf(1));
//        System.out.println("Panel added");


    }

    String currentChoice1;
    String currentChoice2;

    String action;
    int actionCounter=0;
    boolean onlyTakeTheHitUsed=true;

    @Override
    public void actionPerformed(ActionEvent e) {



        if (e.getSource()==startButton)
           {
//               System.out.println("Button Successfully pressed");
               firstScreen();
               startButton.setEnabled(false);

           }
        else if (e.getSource()==exitButton)
        {
            System.out.println("Closing game");
            this.dispose();
        }
        else if (e.getSource()==choice1)
        {
            currentChoice1=choice1.getActionCommand();

            switch(currentChoice1)
            {
                case "stay":{
                    JOptionPane.showMessageDialog(null,
                            "Nothing happens and you grow bored",
                            "Choice 1",
                            JOptionPane.PLAIN_MESSAGE);
                };
                break;
                case "continue":{
                    System.out.println("Button 1 function changed successfully");
                    message.setText("<html>Before you know it, you stand face to face with what looks like a reaper." +
                            "It has a message for you:<p>" +
                            "\"Fight and you shall have a chance to leave. Surrender and you shall stay here eternally\"<p>" +
                            "With the strength you have now, you don't even know if you can lift a finger.<p>" +
                            "What should you do?</html>");
                    choice1.setText("Fight for your life");
                    choice2.setText("Fall and give up");

                    choice1.setActionCommand("fight");
                    choice2.setActionCommand("fall");
                }
                break;
                case "fight":{
                    createCombatScreen();
                }
                break;
            }

        }
        else if (e.getSource()==choice2)
        {

            currentChoice2= choice2.getActionCommand();

            switch (currentChoice2)
            {
                case "go":{
                    message.setText("<html>After walking for a few minutes, a black figure begins to emerge from the horizon." +
                            "It's difficult to tell exactly what it is but you have a strange feeling it's not friendly." +
                            "However, your aura tells you to keep moving forward, as if it were a different entity.<p>" +
                            "You wonder if you should press onward or turn back....</html>");
                    choice1.setText("<html>Continue walking towards the figure</html>");
                    choice2.setText("<html>Turn back and go somewhere else</html>");
                    System.out.println("Button text successfully change");

                    choice1.setActionCommand("continue");
                    choice2.setActionCommand("turnback");
                }
                break;
                case "turnback":{
                    System.out.println("Button 2 function changed successfully");
                    JOptionPane.showMessageDialog(null,
                            "You sense you do not have the strength to turn back. You have not recovered much and " +
                                    "your body is stuck in a forward momentum you cannot stop.",
                            "Turn Back",
                            JOptionPane.PLAIN_MESSAGE);
                }
                break;
                case "fall":{
                    JOptionPane.showMessageDialog(null,
                            "<html>Face down on the ground, the reaper laughs at you misery and disappears. You can no longer move." +
                                    "Stuck there forever, lost in thoughts and lost in regret of a life you cannot remember</html>",
                            "Fall",
                            JOptionPane.PLAIN_MESSAGE);
                    JOptionPane.showMessageDialog(null,
                            "GAME OVER. BAD ENDING",
                            "Bad Ending",
                            JOptionPane.ERROR_MESSAGE);
                    this.dispose(); //Closes game window
                }
                break;
            }

        }
        else if(e.getSource()==attack||e.getSource()==defend||e.getSource()==takeTheHit)
        {
            action=e.getActionCommand();
            System.out.println(action);
            actionCounter++;
            HP--;
            yourHP.setText(HP+"/100");

            if (action.equals("attack")||action.equals("defend"))
               {
                   onlyTakeTheHitUsed=false;
               }


            if (actionCounter==5)
            {
                if (onlyTakeTheHitUsed)
                   {
                       System.out.println("True Ending");
                       JOptionPane.showMessageDialog(null,
                               "After the Reaper has seen you are not fighting back, he decided to send you back to where you came from",
                               "Ending",
                               JOptionPane.PLAIN_MESSAGE);
                       JOptionPane.showMessageDialog(null,
                               "You wake up from a shining light and look around you to find yourself in your room",
                               "Ending",
                               JOptionPane.PLAIN_MESSAGE);
                       JOptionPane.showMessageDialog(null,
                               "On the floor besides you are syringes and powder with labels for drugs",
                               "Ending",
                               JOptionPane.PLAIN_MESSAGE);
                       JOptionPane.showMessageDialog(null,
                               "You look slightly more up and see your partner staring at you in tears. They give you the biggest hug",
                               "Ending",
                               JOptionPane.PLAIN_MESSAGE);
                       JOptionPane.showMessageDialog(null,
                               "\"I thought I lost you for good. Please, never leave me again\"",
                               "Your Partner:",
                               JOptionPane.PLAIN_MESSAGE);
                       JOptionPane.showMessageDialog(null,
                               "TRUE ENDING. CONGRATS I GUESS",
                               "True Ending",
                               JOptionPane.INFORMATION_MESSAGE);
                       this.dispose();
                   }
                else{
                    JOptionPane.showMessageDialog(null,
                            "The reaper grows bored of you and seems disappointed. It speaks once more:",
                            "Ending",
                            JOptionPane.PLAIN_MESSAGE);
                    JOptionPane.showMessageDialog(null,
                            "\"You tried to escape from the very thing you are fighting right now\"",
                            "Reaper:",
                            JOptionPane.PLAIN_MESSAGE);
                    JOptionPane.showMessageDialog(null,
                            "\"I would have killed you, but I shall leave you here with your thoughts to suffer\"",
                            "Reaper:",
                            JOptionPane.PLAIN_MESSAGE);
                    JOptionPane.showMessageDialog(null,
                            "\"When you think you are ready, close your eyes and come back again\"",
                            "Reaper:",
                            JOptionPane.PLAIN_MESSAGE);
                    JOptionPane.showMessageDialog(null,
                            "\"You will keep coming back here and you will find me here until you do what is right\"",
                            "Reaper:",
                            JOptionPane.PLAIN_MESSAGE);
                    JOptionPane.showMessageDialog(null,
                            "YOU GOT THE GOOD ENDING? MAYBE TRY AGAIN, JUST TAKE ALL THE HITS",
                            "Good Ending?",
                            JOptionPane.QUESTION_MESSAGE);
                    this.dispose();
                }
            }

            switch (actionCounter){

                case 1:{dialogueBox.setText("<html><div style='text-align: center;'>Reaper:<p>Why do you even bother struggling....</html>");}
                break;
                case 2:{dialogueBox.setText("<html><div style='text-align: center;'>Reaper:<p>You were doomed from the moment you arrived here....</html>");}
                break;
                case 3:{dialogueBox.setText("<html><div style='text-align: center;'>Reaper:<p>I wonder what it is that moves your spirit.....</html>");}
                break;
                case 4:{dialogueBox.setText("<html><div style='text-align: center;'>Reaper:<p>Oh what shall I do with you weak one....</html>");}
                break;
            }

            System.out.println(onlyTakeTheHitUsed);
            System.out.println(actionCounter);
        }

    }
    
    
    JPanel combatPanel;
    JButton attack,defend,takeTheHit;
    JLabel dialogueBox,enemyHealth,yourHealth,yourHP,enemyHP;
    int HP=5;
    
    private void createCombatScreen() {

        Component[] previousPanelComponents= screenPanel.getComponents();

        for(Component component: previousPanelComponents)
           {
               component.setEnabled(false);
               //Keep in mind disabling a component does not disable the components within that component
               //This means you need to consider example situations like a panel that is holding a button.
               //The panel will become disabled but not the button
           }

        combatPanel=new JPanel();
        combatPanel.setBounds(0,0, gameScreenSize.width, gameScreenSize.height);
        combatPanel.setBackground(Color.BLACK);
        combatPanel.setOpaque(true);
        combatPanel.setLayout(null);

        dialogueBox=new JLabel();
        dialogueBox.setBounds(50,50,500,200);
        dialogueBox.setText("<html>Reaper:<p>\"Suffer....\"</html>");
        dialogueBox.setForeground(Color.WHITE);
        dialogueBox.setBackground(Color.DARK_GRAY);
        dialogueBox.setBorder(BorderFactory.createLineBorder(Color.WHITE,2,true));
        dialogueBox.setOpaque(true);
        dialogueBox.setFont(new Font(null,Font.PLAIN,20));
        dialogueBox.setVerticalAlignment(JLabel.TOP);
        dialogueBox.setHorizontalAlignment(JLabel.CENTER);
        combatPanel.add(dialogueBox);

        yourHealth=new JLabel();
        yourHealth.setBounds(50,250,200,100);
        yourHealth.setFont(new Font(null,Font.BOLD,20));
        yourHealth.setText("Your Health");
        yourHealth.setForeground(Color.WHITE);
        yourHealth.setBackground(Color.BLACK);
        combatPanel.add(yourHealth);

        yourHP=new JLabel();
        yourHP.setBounds(80,275,100,100);
        yourHP.setFont(new Font(null,Font.BOLD,20));
        yourHP.setText(HP+"/100");
        yourHP.setForeground(Color.WHITE);
        yourHP.setBackground(Color.BLACK);
        combatPanel.add(yourHP);

        enemyHealth=new JLabel();
        enemyHealth.setBounds(410,250,200,100);
        enemyHealth.setFont(new Font(null,Font.BOLD,20));
        enemyHealth.setText("Reaper Health");
        enemyHealth.setForeground(Color.WHITE);
        enemyHealth.setBackground(Color.BLACK);
        combatPanel.add(enemyHealth);

        enemyHP=new JLabel();
        enemyHP.setBounds(460,270,100,100);
        enemyHP.setFont(new Font(null,Font.BOLD,20));
        enemyHP.setText("???");
        enemyHP.setForeground(Color.WHITE);
        enemyHP.setBackground(Color.BLACK);
        combatPanel.add(enemyHP);

        attack=new JButton("Attack");
        attack.setBounds(50,400,100,100);
        attack.setForeground(Color.WHITE);
        attack.setBackground(Color.BLACK);
        attack.addActionListener(this);
        attack.setFocusable(false);
        attack.setActionCommand("attack");
        combatPanel.add(attack);

        defend=new JButton("Defend");
        defend.setBounds(200,400,100,100);
        defend.setForeground(Color.WHITE);
        defend.setBackground(Color.BLACK);
        defend.addActionListener(this);
        defend.setFocusable(false);
        defend.setActionCommand("defend");
        combatPanel.add(defend);

        takeTheHit=new JButton("Take The Hit");
        takeTheHit.setBounds(350,400,200,100);
        takeTheHit.setForeground(Color.WHITE);
        takeTheHit.setBackground(Color.BLACK);
        takeTheHit.addActionListener(this);
        takeTheHit.setFocusable(false);
        takeTheHit.setActionCommand("takeTheHit");
        combatPanel.add(takeTheHit);

        layeredPane.add(combatPanel,Integer.valueOf(2));
        System.out.println("Combat Panel successfully added");
    }

}
