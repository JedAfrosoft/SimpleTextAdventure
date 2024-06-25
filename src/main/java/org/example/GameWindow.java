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

    JPanel leftButton;
    JPanel rightButton;
    JButton choice1;
    JButton choice2;
    JButton exitButton;

    JLabel message;

    private void firstScreen(){

        JPanel screenPanel=new JPanel();
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

        leftButton=new JPanel();
        leftButton.setLayout(new BorderLayout());
        leftButton.setBounds(100,300,200,100);
        leftButton.setBackground(Color.BLACK);
        screenPanel.add(leftButton);

        rightButton=new JPanel();
        rightButton.setLayout(new BorderLayout());
        rightButton.setBounds(300,300,200,100);
        rightButton.setBackground(Color.BLACK);
        screenPanel.add(rightButton);


        choice1=new JButton("Stay where you are");
        choice1.setForeground(Color.WHITE);
        choice1.setBackground(Color.BLACK);
        choice1.addActionListener(this);
        choice1.setFocusable(false);
        choice1.setActionCommand("stay");
        leftButton.add(choice1,BorderLayout.CENTER);


        choice2=new JButton("<html>"+"Start walking in a random direction"+ "</html>");
        choice2.setForeground(Color.WHITE);
        choice2.setBackground(Color.BLACK);
        choice2.addActionListener(this);
        choice2.setFocusable(false);
        choice2.setActionCommand("go");
        rightButton.add(choice2,BorderLayout.CENTER);

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

    @Override
    public void actionPerformed(ActionEvent e) {


        if (e.getSource()==startButton)
           {
//               System.out.println("Button Successfully pressed");
               firstScreen();
               //Apparently you can still click the start button in the back if there is no other component in the way
               //Panel doesn't count, I can still click the start button through it
               //Can't disable the start button from here sadly
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
                case "fall":{

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
                                    "your body wishes to move closer to the entity.",
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
    }
}
