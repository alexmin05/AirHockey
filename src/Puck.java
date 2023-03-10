import java.awt.*;

public class Puck {
    public int xpos;
    public int ypos;
    public int width;
    public int height;
    public boolean isAlive;
    public int dx;
    public int dy;
    public Rectangle rec;
    public Image pic;

    public Puck(int pXpos, int pYpos, int dxParameter, int dyParameter, Image picParameter){

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

    public void puckmove() {
        xpos = xpos + dx;
        ypos = ypos + dy;

        if(xpos < 0) {
            dx = -dx;
        }
        if(xpos > 1000 - width) {
            dx = -dx;
        }
        if(ypos < 0) {
            dy = -dy;
        }
        if(ypos > 700 - height) {
            dy = -dy;
        }
        rec = new Rectangle(xpos, ypos, width, height);
    }
}
