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

    public Puck thePuck;
    public Player user1;
    public Player user2;

    public static void main(String[] args) {
        AirHockey myApp = new AirHockey();
        new Thread(myApp).start();
    }

    public AirHockey() {

        setUpGraphics();
        canvas.addKeyListener(this);
        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);

        thePuck = new Puck(500, 350, 0, 0, puckPic);
        user1 = new Player(250, 250, 10, 10, paddlePic);
        user2 = new Player(750, 250, 10, 10, paddlePic);

    }

    public void moveThings() {
        thePuck.puckmove();
        user1.move1();
        user2.move2();
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

        g.drawImage(thePuck.pic, thePuck.xpos, thePuck.ypos, thePuck.width, thePuck.height, null);
        g.drawImage(user1.pic, user1.xpos, user1.ypos, user1.width, user1.height, null);
        g.drawImage(user2.pic, user2.xpos, user2.ypos, user2.width, user2.height, null);

        g.dispose();
        bufferStrategy.show();
    }

    public void keyPressed(KeyEvent event) {
        char key = event.getKeyChar();
        int keyCode = event.getKeyCode();
        System.out.println("Key Pressed: " + key + "  Code: " + keyCode);

        if (keyCode == 37) { // left
            user1.left = true;
        }
        if (keyCode == 38) { // up
            user1.up = true;
        }
        if (keyCode == 39) { // right
            user1.right = true;
        }
        if (keyCode == 40) { // down
            user1.down = true;
        }
        if (keyCode == 65) { // a
            user2.up = true;
        }
        if (keyCode == 68) { // d
            user2.right = true;
        }
        if (keyCode == 83) { // s
            user2.down = true;
        }
        if (keyCode == 87) { // w
            user2.up = true;
        }
    }

    public void keyReleased(KeyEvent event) {
        char key = event.getKeyChar();
        int keyCode = event.getKeyCode();
        if (keyCode == 37) {
            user1.left = false;
        }
        if (keyCode == 38) {
            user1.up = false;
        }
        if (keyCode == 39) {
            user1.right = false;
        }
        if (keyCode == 40) {
            user1.down = false;
        }
        if (keyCode == 65) { // a
            user2.up = false;
        }
        if (keyCode == 68) { // d
            user2.right = false;
        }
        if (keyCode == 83) { // s
            user2.down = false;
        }
        if (keyCode == 87) { // w
            user2.up = false;
        }

    }

    public void keyTyped(KeyEvent event) {
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        System.out.println();
        System.out.println("Mouse Button Pressed");
    }

    public void mouseReleased(MouseEvent e) {
        System.out.println();
        System.out.println("Mouse Button Released");
    }

    public void mouseEntered(MouseEvent e) {
        System.out.println();
        System.out.println("Mouse has entered the window");
    }

    public void mouseExited(MouseEvent e) {
        System.out.println();
        System.out.println("Mouse has left the window");
    }

    public void mouseDragged(MouseEvent e) {
        System.out.println("Mouse is being dragged");
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

