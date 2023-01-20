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
        width = 100;
        height = 100;
        dx = dxParameter;
        dy = dyParameter;
        pic = picParameter;
        isAlive = true;
        rec = new Rectangle(xpos, ypos, width, height);

    }

    public void puckmove() {
        xpos = xpos + dx;
        ypos = ypos + dy;
    }
}
