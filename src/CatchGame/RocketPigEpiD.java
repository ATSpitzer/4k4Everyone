package CatchGame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Mega Catch 4K THOUSAND AND ONE!
 * 'Game of The Year Edition?'
 *
 * Hate your mom telling you to go play outside?  Now you can bring the outside inside, with MEGA CATCH 4K Thousand and One: 'GOTYE?'
 * MC4K^2&1:GOTYE? offers...
 *
 * @author andrewspitzer
 */
public class RocketPigEpiD extends Applet implements Runnable{
    final int GAMEWIDTH = 800;
    final int GAMEHEIGHT = 600;
    final int PAVEMENT = GAMEHEIGHT - 150;
    final int PLAYERSPRITEW = 40;
    final int BRICKS = 15; 
    final int BRICKHEIGHT = ( PAVEMENT - 4*BRICKS ) / BRICKS;
    final float GRAVITY = (float) 0.25;
        
    int players, holdingBall, playerState;
    float[] gameData;
    boolean playerMoving;
    
    BufferedImage[] playerSprites;
        

    public void init() {
        this.setSize(GAMEWIDTH, GAMEHEIGHT);


        players = 0;
        new Thread(this).start();
    }

    public void start() {

    }

    public void run() {

        /*
         * For now this is how sprites are loaded in the game.  Will be made small later.
         */
        playerSprites = new BufferedImage[4];
        
        try {
            
            playerSprites[0] = ImageIO.read(new File("L1.bmp"));
            playerSprites[1] = ImageIO.read(new File("M1.bmp"));
            playerSprites[2] = ImageIO.read(new File("R1.bmp"));
            playerSprites[3] = ImageIO.read(new File("Standing.png"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        gameData = new float[]{20 + PLAYERSPRITEW, PAVEMENT, 0, 0, 200 + PLAYERSPRITEW , PAVEMENT, 0, 0, 0, 0, 0, 0};     // ball_loc_x, ball_loc_y, ball_vel_x, ball_vel_y, player_1_loc_x, player_1_loc_y, player_1_vel_x, player_1_vel_y, player_2_loc_x, player_2_loc_y, player_2_vel_x, player_2_vel_y;
        holdingBall = 1;
        
        BufferedImage image =  new BufferedImage(GAMEWIDTH, GAMEHEIGHT, 4);
        Graphics2D g = (Graphics2D) image.getGraphics();
        Graphics2D g2 = null;
        
        
        g.setColor(Color.lightGray);
        g.fillRect(GAMEWIDTH - (3 * BRICKHEIGHT), 0, (3 * BRICKHEIGHT), PAVEMENT);

        for(int i = 0; i < BRICKS; i++) {
            g.setColor(Color.red);
            g.fillRect(GAMEWIDTH - (3 * BRICKHEIGHT), i*(BRICKHEIGHT + 4), 3 * BRICKHEIGHT, BRICKHEIGHT);
        }
        
        /*
        * Loading graphics ends here
        */
        
        playerState = 1;
        
        /**
         * ============================================= Single Player =============================================
         * You have no people-friends, but that's okay.  A brick wall offers all the friendship you could ever need.
         */

        long animationClock = System.nanoTime();
        while(true) {
        if (System.nanoTime() - animationClock > 100000000) {
            if(gameData[6] != 0 && gameData[5] == PAVEMENT) {
                playerState = (playerState + 1)%3;
            }    else {
                playerState = 1;
            }
            animationClock = System.nanoTime();
        }
        g.clearRect(0, 0, GAMEWIDTH - (3 * BRICKHEIGHT), PAVEMENT);
       g.setColor(Color.blue);
        g.fillRect(0, 0, GAMEWIDTH - (3 * BRICKHEIGHT), PAVEMENT);
        g.drawImage(playerSprites[playerState],(int) gameData[4] - PLAYERSPRITEW, (int) gameData[5] - 80, 80, 80, this);
//        g.setColor(Color.white);
//        g.fillRect((int) gameData[4] - PLAYERSPRITEW, (int) gameData[5] - 80, 2 * PLAYERSPRITEW, 80);
//        g.setColor(Color.orange);
//        g.fillOval((int) gameData[0] + 40, (int) gameData[1] - 40, 40, 40);
                
        
        /**
         *
         * Gravity in action 
         * 
         */
        for(int i = 3; i <= 11; i += 4) {
            if(gameData[i - 2] + gameData[i] >= PAVEMENT)
            {
                gameData[i] = 0;
                gameData[i - 2] = PAVEMENT;
                
            } else {
                gameData[i - 2] += gameData[i];
                gameData[i] += GRAVITY;
            }
        }
        


        
        /**
         * 
         * Move the player left and right, bound by the screen and brick wall.
         *
         */
        if(playerMoving || gameData[5] < PAVEMENT) {
            if(gameData[4] + gameData[6] <= PLAYERSPRITEW)
            {
                gameData[4] = PLAYERSPRITEW;
                gameData[6] = 0;
                playerMoving = false;
            } else if((gameData[4] + gameData[6]) >= GAMEWIDTH - (3 * BRICKHEIGHT) - PLAYERSPRITEW) {
                gameData[4] = GAMEWIDTH - (3 * BRICKHEIGHT) - PLAYERSPRITEW;
                gameData[6] = 0;
                playerMoving = false;
            } 
        } else {
            if(gameData[6] < 0)
                if(gameData[6] >= -.05) {
                    gameData[6] = 0;
                } else {
                gameData[6] += .05; 
                }
            if(gameData[6] > 0)
                if(gameData[6] <= .05) {
                    gameData[6] = 0;
                } else {
                gameData[6] += -.05; 
                }
        }
        
        gameData[4] += gameData[6];
        
        /**
         * Catch the ball
         */

        /**
         * Who has the ball the ball
         */
        switch(holdingBall) {
            case 0:
                // No one is holding the ball
                break;
            case 1:
                if(gameData[6] < 0)
                {
                    gameData[0] = gameData[4] - 60;
                } else
                {
                    gameData[0] = gameData[4] - 60;
                }
                
                gameData[1] = gameData[5] - 40; 
                 
                    
        }


            if (g2 == null) {
                g2 = (Graphics2D) getGraphics();
                requestFocus();
            } else {
                g2.drawImage(image, 0, 0, GAMEWIDTH, GAMEHEIGHT, null);
            }
        }
        
        
    }
        

    public boolean handleEvent(Event e) {

        switch (e.id) {
            case 501: // Press Mouse
                
                break;
            case 506: // Drag Mouse
                
                break;
            case 502: // Release Mouse
                
                break;

        /*
         * Keyboard input for moving player
         */
            case 403: // Press Key
                switch(e.key)
                {
                    case 1006: // Left Arrow
                        if(gameData[4] > PLAYERSPRITEW)
                        {
                            if(gameData[6] > -10)
                            {
                                gameData[6] -= .2;
                            }
//                            // SLAM ON THE BREAKS
//                            if(gameData[6] > 0)
//                            {
//                                playerState = 2;
//                            }
                            playerMoving = true;
                        }
                        break;
                    case 1007: // Right Arrow
                        if(gameData[4] <  GAMEWIDTH - (3 * BRICKHEIGHT) - PLAYERSPRITEW)
                        {
                            if(gameData[6] < 10)
                            {
                                gameData[6] += .2;
                            }
//                            //SLAM ON THE BREAKS
//                            if(gameData[6] < 0)
//                            {
//                                playerState = 0;
//                            }

                            playerMoving = true;
                        }
                        break;
                    case 1004: // Up Arrow
                        if(gameData[5] == PAVEMENT)
                        {
                            gameData[7] = -10;
                        }
                        break;
                    default:
                        break;
                }
                break;
            case 404: // Release Key
                switch(e.key)
                {
                    case 1006: // Left Arrow
                        playerMoving = false;
                        break;
                    case 1007: // Right Arrow
                        playerMoving = false;
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;

        }

        return true;
    }

}
