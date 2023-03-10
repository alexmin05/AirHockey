
import java.awt.*;

public class Player {

    public int xpos;
    public int ypos;
    public int width;
    public int height;
    public boolean isAlive;
    public int dx;
    public int dy;
    public Rectangle rec;
    public Image pic;

    public boolean right;
    public boolean down;
    public boolean left;
    public boolean up;
    public boolean right2;
    public boolean down2;
    public boolean left2;
    public boolean up2;
    public boolean isCrashing = false;

    public Player(int pXpos, int pYpos, int dxParameter, int dyParameter, Image picParameter) {

        xpos = pXpos;
        ypos = pYpos;
        width = 50;
        height = 50;
        dx = dxParameter;
        dy = dyParameter;
        pic = picParameter;
        isAlive = true;
        rec = new Rectangle(xpos, ypos, width, height);

    }

    public void move1() {
        xpos = xpos + dx;
        ypos = ypos + dy;

        if(right2 == true) {
            dx = 10;
        } else if (left2 == true) {
            dx = -10;
        } else {
            dx = 0;
        }

        if(down2 == true) {
            dy = 10;
        } else if (up2 == true) {
            dy = -10;
        } else {
            dy = 0;
        }

        if (xpos < 500) {
            xpos = 500;
        }
        if (ypos > 700 - height) {
            ypos = 700 - height;
        }
        if (xpos > 1000 - width) {
            xpos = 1000 - width;
        }
        if (ypos < 0) {
            ypos = 0;
        }
        rec = new Rectangle(xpos, ypos, width, height);
    }

    public void move2() {
        xpos = xpos + dx;
        ypos = ypos + dy;

        if(right == true) {
            dx = 10;
        } else if (left == true) {
            dx = -10;
        } else {
            dx = 0;
        }

        if(down == true) {
            dy = 10;
        } else if (up == true) {
            dy = -10;
        } else {
            dy = 0;
        }

        if (xpos > 500 - width) {
            xpos = 500 - width;
        }
        if (ypos > 700 - height) {
            ypos = 700 - height;
        }
        if (xpos < 0) {
            xpos = 0;
        }
        if (ypos < 0) {
            ypos = 0;
        }
        rec = new Rectangle(xpos, ypos, width, height);
    }

}

