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

    public Button button1;

    public boolean gameStart = false;

    public int usertopuckdistance;
    public int xcenterofuser;
    public int ycenterofuser;
    public int xcenterofpuck;
    public int ycenterofpuck;
    public int mouseX, mouseY;

    public Puck thePuck;
    public Player[] users;

    public static void main(String[] args) {
        AirHockey myApp = new AirHockey();
        new Thread(myApp).start();
    }

    public AirHockey() {

        setUpGraphics();
        canvas.addKeyListener(this);
        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);

        puckPic = Toolkit.getDefaultToolkit().getImage("puckPic.png");
        paddlePic = Toolkit.getDefaultToolkit().getImage("paddlePic.png");

        button1 = new Button(300, 300, 150, 70, "Click Here to Start");

        thePuck = new Puck(500, 350, 0,0, puckPic);
        users = new Player[2];
        for (int x = 0; x < users.length; x = x + 1) {
            users[x] = new Player(100 + x*500, 350, 10, 10, paddlePic);
        }

       for (int x = 0; x < users.length; x = x + 1) {
           xcenterofuser = users[x].xpos + users[x].width / 2;
           ycenterofuser = users[x].ypos + users[x].height / 4;
           xcenterofpuck = thePuck.xpos + thePuck.width / 2;
           ycenterofpuck = thePuck.ypos + thePuck.height / 2;
           usertopuckdistance = Math.sqrt(Math.pow(xcenterofuser - thePuck.xpos, 2) + Math.pow(ycenterofuser - thePuck.ypos, 2));
           if (usertopuckdistance = users[x].width / 2 + thePuck.width / 2) {

           }
       }
    }

    public void moveThings() {
        thePuck.puckmove();
        users[0].move2();
        users[1].move1();
    }

    public void checkIntersections() {

    }

    public void run() {
        while (true) {
            moveThings();
            checkIntersections();
            render();
            pause(20);
        }
    }

    public void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        if (gameStart == false) {
            g.setColor(Color.BLUE);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.YELLOW);
        g.drawString("Are you ready to see who is the ultimate Air Hockey champion?", 500, 350);
        }
        else {
            g.drawImage(thePuck.pic, thePuck.xpos, thePuck.ypos, thePuck.width / 2, thePuck.height / 2, null);
            for (int x = 0; x < users.length; x++) {
                g.drawImage(users[x].pic, users[x].xpos, users[x].ypos, users[x].width, users[x].height / 2, null);
            }
        }
        g.dispose();
        bufferStrategy.show();
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

        if (button1.rec.contains(x, y)) {
            System.out.println("TIMER STARTED");
            startTime = System.currentTimeMillis();
            startTimer = true;
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
}

