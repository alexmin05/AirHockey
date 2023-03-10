import java.awt.*;

public class Button {

    public int xpos;				//the x position
    public int ypos;				//the y position
    public boolean isAlive;			//a boolean to denote if the object is alive or dead.
    public int width;
    public int height;
    public Rectangle rec;			//declare a rectangle variable
    public String text;
    public boolean isCrashing = false;

    public Button(int xParameter, int yParameter, int widthParameter, int heightParameter, String textParameter)
    {
        xpos = xParameter;
        ypos = yParameter;
        width = widthParameter;
        height = heightParameter;
        text = textParameter;
        isAlive = true;
        rec= new Rectangle (xpos,ypos,width,height);
    }

}
