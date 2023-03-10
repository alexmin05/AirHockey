import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import java.awt.Graphics2D;
import java.awt.event.*;

public class AirHockey implements KeyListener, MouseListener, MouseMotionListener, Runnable {

    final int WIDTH = 1000;
    final int HEIGHT = 700;

    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;
    public BufferStrategy bufferStrategy;

    public Image puckPic;
    public Image paddlePic;
    public Image airhockeyPic;

    public Button startButton;
    public Button[] goals;

    public boolean gameStart = false;
    public boolean gameEnd = false;

    public int mouseX, mouseY;
    public double useronescore;
    public double usertwoscore;

    public Puck thePuck;
    public Player[] users;

    public SoundFile hitSound;
    public SoundFile celebration;

    public static void main(String[] args) {
        AirHockey myApp = new AirHockey();
        new Thread(myApp).start();
    }

    public AirHockey() {

        useronescore = 0;
        usertwoscore = 0;

        setUpGraphics();
        canvas.addKeyListener(this);
        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);

        puckPic = Toolkit.getDefaultToolkit().getImage("puckPic.png");
        paddlePic = Toolkit.getDefaultToolkit().getImage("paddlePic.png");
        airhockeyPic = Toolkit.getDefaultToolkit().getImage("airhockeyPic.png");

        users = new Player[2]; // array for users
        for (int x = 0; x < users.length; x = x + 1) {
            users[x] = new Player(100 + x * 700, 325, 10, 10, paddlePic);
        }

        startButton = new Button(425, 315, 150, 70, "Click Here to Start");
        goals = new Button[2]; // array for goals
        for (int x = 0; x < goals.length; x = x + 1) {
            goals[x] = new Button(0 + x * 995, 225, 5, 250, "");
        }

        hitSound = new SoundFile("PaddleHit.wav");
        celebration = new SoundFile("Celebration.wav");

        thePuck = new Puck(475, 325, 0, 0, puckPic);
    }

    public void moveThings() {
        thePuck.puckmove();
        users[0].move2();
        users[1].move1();
    }

    public void checkIntersections() {

        for (int x = 0; x < users.length; x = x + 1) {
            if (thePuck.rec.intersects(users[x].rec) && users[x].isCrashing == false) {
                users[x].isCrashing = true;
                hitSound.play();
                if(users[x].dx == 0 && users[x].dy == 0) {
                    thePuck.dx = -thePuck.dx;
                    thePuck.dy = -thePuck.dy;
                } else {
                    thePuck.dx = users[x].dx;
                    thePuck.dy = users[x].dy;
                }
            }
            if (!thePuck.rec.intersects(users[x].rec)) {
                users[x].isCrashing = false;
            }
        }

        for (int x = 0; x < goals.length; x = x + 1) {
            if (thePuck.rec.intersects(goals[0].rec) && goals[x].isCrashing == false) {
                goals[x].isCrashing = true;
                usertwoscore = usertwoscore + 0.5;
                thePuck.dx = 0;
                thePuck.dy = 0;
                thePuck.xpos = 475;
                thePuck.ypos = 325;
                users[x].xpos = 100 + x * 700;
                users[x].ypos = 325;
            }
            if (!thePuck.rec.intersects(goals[x].rec)) {
                goals[x].isCrashing = false;
            }

            if (thePuck.rec.intersects(goals[1].rec) && goals[x].isCrashing == false) {
                goals[x].isCrashing = true;
                useronescore = useronescore + 0.5;
                thePuck.dx = 0;
                thePuck.dy = 0;
                thePuck.xpos = 475;
                thePuck.ypos = 325;
                users[x].xpos = 100 + x * 700;
                users[x].ypos = 325;
            }
            if (!thePuck.rec.intersects(goals[x].rec)) {
                goals[x].isCrashing = false;
            }

            if (useronescore == 5) {
                gameEnd = true;
            }
            if (usertwoscore == 5) {
                gameEnd = true;
            }
        }
    }

    public void run() {
        while (true) {
            moveThings();
            checkIntersections();
            render();
            pause(20);
        }
    }

    public void keyPressed(KeyEvent event) {
        char key = event.getKeyChar();
        int keyCode = event.getKeyCode();
        System.out.println("Key Pressed: " + key + "  Code: " + keyCode);

        if (keyCode == 65) { // a
            users[0].left = true;
        }
        if (keyCode == 68) { // d
            users[0].right = true;
        }
        if (keyCode == 83) { // s
            users[0].down = true;
        }
        if (keyCode == 87) { // w
            users[0].up = true;
        }
        if (keyCode == 37) {
            users[1].left2 = true;
        }
        if (keyCode == 38) {
            users[1].up2 = true;
        }
        if (keyCode == 39) {
            users[1].right2 = true;
        }
        if (keyCode == 40) {
            users[1].down2 = true;
        }
    }

    public void keyReleased(KeyEvent event) {
        char key = event.getKeyChar();
        int keyCode = event.getKeyCode();
        if (keyCode == 65) {
            users[0].left = false;
        }
        if (keyCode == 68) {
            users[0].right = false;
        }
        if (keyCode == 83) {
            users[0].down = false;
        }
        if (keyCode == 87) {
            users[0].up = false;
        }
        if (keyCode == 37) {
            users[1].left2 = false;
        }
        if (keyCode == 38) {
            users[1].up2 = false;
        }
        if (keyCode == 39) {
            users[1].right2 = false;
        }
        if (keyCode == 40) {
            users[1].down2 = false;
        }

    }

    public void keyTyped(KeyEvent event) {
    }

    public void mouseClicked(MouseEvent e) {
        int x, y;
        x = e.getX();
        y = e.getY();

        mouseX = x;
        mouseY = y;

        if (startButton.rec.contains(x, y)) {
            gameStart = true;
        }
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }
    public void setUpGraphics() {
        frame = new JFrame("AirHockey");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");
    }

    public void pause(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }

    public void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);
        g.drawImage(airhockeyPic,0,0, WIDTH, HEIGHT, null);
        System.out.println(gameStart);

        if (gameStart == false) {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, 1000, 700);
            g.setColor(Color.BLUE);
            g.fillRect(startButton.xpos, startButton.ypos, startButton.width, startButton.height);
            g.setColor(Color.ORANGE);
            g.drawString(startButton.text, startButton.xpos + 20, startButton.ypos + 40);
        }
        else {
            g.drawImage(thePuck.pic, thePuck.xpos, thePuck.ypos, thePuck.width, thePuck.height, null);
            for (int x = 0; x < users.length; x++) {
                g.drawImage(users[x].pic, users[x].xpos, users[x].ypos, users[x].width, users[x].height, null);
            }
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.setColor(Color.BLUE);
            g.drawString("" + (int)useronescore, 440, 50);
            g.setColor(Color.RED);
            g.drawString("" + (int)usertwoscore, 540, 50);
        }

        if (gameEnd == true) {
            g.setColor(Color.WHITE);
            g.fillRect(0,0, 1000, 700);
            g.setColor(Color.BLUE);
            g.setFont(new Font ("Arial", Font.BOLD, 50));
            g.drawString("GAME OVER", 330, 300);
            if (useronescore == 5){
                g.drawString("Player One has Won " + (int)useronescore + "-" + (int)usertwoscore, 240, 400);
                celebration.play();
            } else {
                g.setColor(Color.RED);
                g.drawString("Player Two has Won " + (int)usertwoscore + "-" + (int)useronescore, 240, 400);
                celebration.play();
            }
        }
        g.dispose();
        bufferStrategy.show();
    }
}

