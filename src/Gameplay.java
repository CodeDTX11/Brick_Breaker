import java.util.*;
import java.awt.event.*;

import javax.swing.*;

import java.awt.*;

import javax.swing.*;

import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener
{
    private boolean play = false;
    private int score = 0;

    private int totalBricks = 48;

    private Timer timer;
    private int delay = 10;

    private int playerX = 325;

    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = 1;
    private int ballYdir = 2;

    private MapGenerator brickMap;

    public Gameplay()
    {
        brickMap = new MapGenerator(4, 12);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer=new Timer(delay,this);
        timer.start();
    }

    public void paint(Graphics g)
    {
        // background
        g.setColor(Color.blue);
        g.fillRect(0, 0, 750, 650);
//        g.fillRect(1, 1, 700, 600);

        // drawing map
        brickMap.draw((Graphics2D) g);

        // borders
//        g.setColor(Color.yellow);
//        g.fillRect(0, 0, 3, 592);
//        g.fillRect(0, 0, 692, 3);
//        g.fillRect(683, 0, 1, 592);

        // the scores

        // the paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);

        // the ball
        g.setColor(Color.yellow);
//        g.fillOval(ballposX, ballposY, 20, 20);
        g.fillRect(ballposX, ballposY, 20, 20);

        // when you won the game

        // when you lose the game
        if(ballposY > 570)
        {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD, 30));
            g.drawString("Game Over, Scores: "+score, 190,300);

//            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD, 20));
            g.drawString("Press (Enter) to Restart", 230,350);
        }

        g.dispose();
    }

    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            if(playerX >= 650)
            {
                playerX = 650;
            }
            else
            {
                moveRight();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            if(playerX < 10)
            {
                playerX = 10;
            }
            else
            {
                moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            if(!play)
            {
                play = true;
                ballposX = 120;
                ballposY = 350;
                ballXdir = -1;
                ballYdir = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                brickMap = new MapGenerator(4, 4);

                repaint();
            }
        }
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public void moveRight()
    {
        play = true;
        playerX+=20;
    }

    public void moveLeft()
    {
        play = true;
        playerX-=20;
    }

    public void actionPerformed(ActionEvent e)
    {
        timer.start();
        if(play)
        {
            if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 30, 8)))
            {
                ballYdir = -ballYdir;
                ballXdir -= 1;
            }
            else if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX + 70, 550, 30, 8)))
            {
                ballYdir = -ballYdir;
                ballXdir += 1;
            }
            else if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX + 30, 550, 40, 8)))
            {
                ballYdir = -ballYdir;
            }

            // check map collision with the ball
            A: for(int r = 0; r<brickMap.map.length; r++)
            {
                for(int c =0; c<brickMap.map[0].length; c++)
                {
                    if(brickMap.map[r][c] > 0)
                    {
                        int brickX = c * brickMap.brickWidth + 80;
                        int brickY = r * brickMap.brickHeight + 50;
                        int brickWidth = brickMap.brickWidth;
                        int brickHeight = brickMap.brickHeight;

                        Rectangle brickRect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
//                        Rectangle brickRect = rect;

//                        if(ballposY == brickRect.y + brickHeight){
//                            System.out.println(ballposY + "=" + brickRect.y);
//                            System.out.println(ballRect.intersects(brickRect));
//                        }

                        if(ballRect.intersects(brickRect))
                        {
                            brickMap.setBrickValue(r, c);
                            score+=5;
                            totalBricks--;

                            // when ball hit right or left of brick
                            //+ brickHeight || ballposY + 19 == brickRect.y

//                            System.out.println(brickHeight + ":" + ballposY + "=" + brickRect.y);

                            if(ballposY - ballYdir == brickRect.y + brickHeight || ballposY + 20 - ballYdir == brickRect.y ){
                                ballYdir = -ballYdir;
                            } else {
                                ballXdir = -ballXdir;
                            }

//                            if(ballposX + 19 == brickRect.x || ballposX  == brickRect.x + brickRect.width - 1 )
//                            {
//                                ballXdir = -ballXdir;
//                            }
//                            // when ball hits top or bottom of brick
//                            else
//                            {
//                                ballYdir = -ballYdir;
//                            }

                            break A;
                        }
                    }
                }
            }

            ballposX += ballXdir;
            ballposY += ballYdir;

            if(ballposX < 0)
            {
                ballXdir = -ballXdir;
            }
            if(ballposY < 0)
            {
                ballYdir = -ballYdir;
            }
            if(ballposX > 730)
            {
                ballXdir = -ballXdir;
            }

            repaint();
        }
    }
}
