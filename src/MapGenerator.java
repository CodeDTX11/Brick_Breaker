import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenerator
{
    public int map[][];
    public int brickWidth;
    public int brickHeight;

    public MapGenerator (int row, int col)
    {
        map = new int[row][col];
        for(int i = 0; i<map.length; i++)
        {
            for(int j =0; j<map[0].length; j++)
            {
                map[i][j] = 1;
            }
        }

        brickWidth = 540/col;
        brickHeight = 200/row;
//        brickWidth = 600/col;
//        brickHeight = 175/row;
    }

    public void draw(Graphics2D g)
    {
        for(int row = 0; row < map.length; row++)
        {
            for(int col = 0; col < map[0].length; col++)
            {
                if(map[row][col] > 0)
                {
                    g.setColor(Color.white);
                    g.fillRect(col * brickWidth + 80, row * brickHeight + 50, brickWidth, brickHeight);

                    // this is just to show separate brick, game can still run without it
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(col * brickWidth + 80, row * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
    }

    public void setBrickValue(int row, int col)
    {
        map[row][col] = 0;
    }
}