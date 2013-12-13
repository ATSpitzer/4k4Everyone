/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package FourKmonTwo;

import java.applet.Applet;
import java.awt.Color;
import java.util.Random;
import java.awt.Event;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author andrewspitzer
 */
public class FourKmonBlue extends Applet implements Runnable {

    public void init() {

        this.setSize(SW * MW * 2, ((SH * MH) + (4 * SH)));

    }

    public void start() {

        new Thread(this).start();

    }
    boolean odd = false;
    boolean move = false;
    boolean battle1 = false;
    boolean select = false;
    boolean caught = false;
    boolean pause = false;
    static boolean turn;
    boolean r = false;
    static int pl, pl2; //location of the player and a variable for where the player is trying to move
    static int i, j;
    final int MW = 12; //Width of the map in tiles
    final int MH = 12; //Height of the map in tiles
    final int SW = 9; //Map Sprite width (tiles are sysmetrical so only half is drawn, final width is double this number hence many calculations involve SW*2
    final int PH = 35; //The height of a 4kmon sprite.  The width is equal to the width of a map tile sprite.  These sprites are also symetrical.
    final int SH = 17;
    final int MAXKM = 4;
    final int COLORTYPE = 10;
    static public int map[];
    static public String names[];
    static public int[] kmC; //This string represents the 4kmon collection
    static public int[] wkm;
    final String M = ""
            /*
             * The first part of String M is the world map.
             *
             */
            + "fffffgffffffaaaaaaaaaaaaaaaaccccccaaaaaaccccccaaaaaacccaaaaaaccccccaccccaccccccaccccaccccccaccccaaaaaaaaccccaaaaaaeaaaaaaaaaaaaaaaaaffffffffffff" //d = 13
            /*
             * Information about 4kmon
             */
            + "Forkmon$Dead Fish$Manureman$Fryangle$Litigator$5kmon$Half Dead Fish$Stinky Shroom$Pyromid$" //4kmon names seperated by $
            + ""
            + "Fork$Squirt$Compost$VatO'crisco$Injunction$2Big$Wave$Falling Branch$Arson";//4kmon attacks
//            + ""
//            + "$4kBall$4kmon$Switch $Wild$Press enter...$You$fail$Run$Your $Pick $spot for$\t Lv:"
    
    /*
     *
     * One can have up to 4 pokemon. Each pokemon 6 has attributes: Type (From
     * which are derived: name, sprite, and element). ExP HpMax HP Attack
     * Defence
     */
    
    /*
     * The sprites for moving on the map The are path, player on path, tall
     * grass, player in tall grass, hospital, rock, and enemy trainer
     */
    final String SP = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabbbaaaaabbbbaaaaababaa"+
"aaabaaaaabaabaabababaababbaaabaabaabbaabaabbaabbabbbaaaaababaaaaaaabbaaaaaaaabbbaaaaaabbbaaaaabbbbaaabbbbbaaabaaaabaaabbbbbaacacccaacccaccaaaacacccacccaccacccaacccccacacccaaaacccacc"+
"acaaacccaaaacacccccccaacaccaccaaccaaacaaaaaaacaaccaaccacaacaccaaccaaacacccccaaacaaaaccacccacaaccacacccaacccaccaabbbacccabbbbccaccbabaccccbaaaacbaabaabababcababbaaabacbaabbaabaabbccb"+
"babbbaaaacbabaaaacaabbaaccacaacaccaaccaaacacccccaaacaaaaccacccacaaccaaabbbbbbbaabaaaaaaaabaccaccaabaccaccaabaaaaaaaabaccaccaabaccaccaabaaaaccaabacccccaabacccccaabacccccaaba"+
"aaaccaabaaaaccaabaaaaccaabaaaaaaaabaaaaaabbbbbbbbbaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabbbaaaabbbccaaabbccccaabbcccccabcccccccbbcccccccbccccccccbccccccccbccccccccbccccccccbbcccccccabbbb"+
"ccccaaaabbbbbaaaaaaaaaaaaaaccccaaaaabbaaaaaaacabaaaaacaacaaaaacaacaaaaacaaaaacaaacabacacaacbabcacbaacaacccabaacaaaababbbbaaaabbaaaaaaaabaaaaaaaababbaaaaababaaaabbbabaaaacccbba"+
"acaaaaccaccaaacccaccaaacccaccaaacccaccaaacccaccaaacccaccaaacccacccaacccacccaacccacccaacccaccccacccacccbbbccacccbbbbccccbccbbbcaccccccbbaaacbbbccaaabaaabcaaabaaabcaabaaaaabaababbbabaaabbbbbcaaabbbb"+
"bcaaaabbbccaaaaaacccaaaabbbbcbaabbbbbbbbbbbbbbbbbbbbbccbabbbbacccaaaaaacccaaaaaacccaaaaaacccaaaaaacccaaaaaaaccaaaaaaaacaaaaaaaaaaaaaaaaaaaaaaaabbaaaaaabbbaaaaaabccbaaaabbccbaaaabccc"+
"caaabbccbcaaabccccbaabbcccbcaabccccccaabccccccaabccccccaabccccccaabbcccccaaabcccccaaabbccccaaaabccccaaaabbcccaaaaabbccaaaaaabbcaaaaaaabcaaaaaaabcaaaaaaabcaaaaaabbcaaaaaabccaaaaabcccaaaaabcccaaaabcccb"+
"aaabbccbbaaabbbbbaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabaaaabaabbaaabaaaabbaabbaaaabbaabbaaaabaaabaaaabaabbaaaababbaaaabbabaaaaabaaabaabbbaaaababaaaaabcbaabbbbcca"+
"abbaccbbaabbbbbbbaabcccccbaabcccccbabbcccccbabbccccbcaabcccbbcaabbccbccaabbbbbccabbccccbbbbbccccccbccbacccccccbbacccbccbbaaccbccbbabccabcbbbbacabbbabaaabcccabbabbccccbbbbbccccaabbbcccccababccccccccab"+
"bbbbbbbaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaacaaaaaaaccaaccaaacaaaacaaccaaaacaacccaaaacacccbaaacccccbaaaccccbaaaaacacbaaaaaaabaacbaaaabaacbaaabbbabbcaabbbbacccabbbbabccbbbbbabbbbabbabacbaaaaabaabaaaaabcb"+
"aaaaaabcbaaaaaaccbaabbbbcbaaaaaaabbaaaaaaabaaaaaaaabaaaaaaaaaaaaaaaaabbbbbbbbbaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabaccaaaabccccaaaabbbcbaaacbaabcaaacbaabcaaaacbbccaaaa"+
"cccbcaaaaccbccaaaacccbbaaaaaccccaaaaaccbbaaaaabaccaabbbaaacabbcccaaaabcccccabbbccccccbbcccccccbbccbcccbbcccbcccbbbcccbccbbbbcccbbcbabbcccbbbaabbccbccaaabccbcbaaaabbbbbaaaabccccaaabbccccaaabc"+
"ccccaaabccccbaaabccbbbaaabccbaaaaabbbbbbaaabbbbbbcccbbbbbbcccbbbbbbcaaaccaaccaaaccaacccaacccacccaacccacccaacccacccaacccacccaacccacccaacccacccaacccacccaacccaccccacccacccccbb"+
"bcccccbbbbccccbccbbbcaccccccbbaaacbbbccaaabaaabcaaabaaabcaabaaaaabaababbbabaaabbbbbcaaabbbbbcaaaaaacccaaaabbbbcbaabbbbbbbbbbbbbbbbbbbbbccbabbbbbcbbaaaabbbccaaaabbbccaaaabbbbcaaaaabbbbaaaaabbbbaaaaaaa"+
"bbaaaaaaaabaaaaaaaaaaaaaaaaaaaaaaaaaaaabbaaaaaaabaaaaababbaaaabbabbaaaabaaabbaaabbaaabbaaabbaaabaaaabaaabbaabbaaabbabbaaabbaabaaaaaaaaaaaaabbbbbbbaabccccccaabbcccccaaabcccccaaabbccccaaaabccc"+
"caaaabbcccaaaaabbccaaaaaabbcaaaaaaabcaaaaaaabcaaaaaabbcaaaaaabccaaaaabcccaaaaabcccaaaabcccbaaabbccbbaaabbbbbaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabbaaaaabbaaaaaabccaaaaabccbacaabccbabcabacbaaab"+
"baaabababbcaabbbbbbcccbbbbbbccccbbbcbbaaccbcaaabbccccaaaabbbcccaaaabcbbcaaaabcbabaaabbcbaaaabbccbbaabbcccccbbbcccccccbccbbccccbcbabcbbcbccbbbcbbbbccccccbabbbccccbaaabbccbbaaaabbbbcaaaabbbccaaaabcccca"+
"aaabccccaaabbbcccabcccccccbccccccccbcccccccbabcccccbbaabbbbbbaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabaaccaaaabbaacaaaabbaccaaccbbaacaaacbbcbaaaacbbcbcaaaacbcbaaaaacccbcaaaaccccabaa"+
"aacacbbaaaaaababcbaaaababcbaaabbbabbcaabbbbacccabbbbabccbbbbbabaababbabbcbaaaabbaabaaaabbabaaaaaabababbbbbcababaaaacbaabaaaaabaabbbbbbaaaaaaaabaaaaaaabaaaaaaaabbaaaaaaababbaaaaabaaabbaaabaaaaabbabaaa"+
"aaaabb";
    int ord = 0; //Which pokemon do you have selected
    int lv = 0;
    static long t, t2;
    static boolean[] win;
    static BufferedImage[] msprites;  //Holds sprites for movement on the map
    static BufferedImage[] psprites; //Holds sprites for battle
    static int ball = 3;
    static int act, sel;
    boolean select2 = false;
    int[] COLORS = {
        -1,
        -16777216,
        -4144960
    };

    public void run() {

        Color[] grey = new Color[COLORS.length];
        for (int c : COLORS) {
            grey[i] = new Color(c);
            i++;
        }
        Random rand = new Random();

        /*
         * An array called map is made to represent the world map. Each type of tile is
         * represented by an integer. This allows us to later use Switch{} to
         * easily dictate how the game behaves on each type of tile. Also, a
         * tile with a character on it has its value increased by 1. When the
         * player moves, the tile they were on becomes 1 less, and the tile they
         * move to becomes 1 more.
         */

        map = new int[MW * MH];
        for (i = 0; i < MW * MH; i++) {
            map[i] = M.charAt(i);
        }


        /*
         * Names of all the 4kmon and their attacks
         */
        names = new String[18];
        j = 0;
        names[j] = "";
        for (; i < M.length(); i++) {
            if (M.charAt(i) == '$') {
                j++;
                names[j] = "";
            } else {
                names[j] += M.charAt(i);
            }

        }
        
        //An array of booleans representing whether a level has been won or not
        win = new boolean[4];
        for (i = 0; i < 4; i++) {
            win[i] = false;
        }

        //The player's starting location
        pl = 112;
        map[pl] = 'b';

        kmC = new int[MAXKM * 6];
        for (j = 0; j < MAXKM * 6; j++) {
            kmC[j] = 0;
        }
        kmC[0] = 1; //Name of 4kmon
        kmC[1] = 5 * 10; // Experience starts at level 5
        kmC[2] = (rand.nextInt(5) + 3 + 2 * (kmC[1] / 10)); //HP = HPBonus + 3 + level (10xp) * 2
        kmC[3] = kmC[2]; // Current HP
        kmC[4] = rand.nextInt(4) + 3; // Attack
        kmC[5] = rand.nextInt(2) + 1; // Defend

        /*
         * Sprites are drawn first for the map, then for the 4kmon
         * 
         *      Note: Change to 1 nested loop instead of 2 made December 10 @ 5:24
         */
        BufferedImage ss = new BufferedImage(SW * 2, (7 * SH) + (9 * PH), COLORTYPE);
                for (j = 0; j < (9 * PH) + (7 * SH); j++) {
            for (int k = 0; k < SW; k++) {
                ss.setRGB(k, j, COLORS[SP.charAt(k + j * SW) - 'a']);
                ss.setRGB(2 * SW - (1 + k), j, COLORS[SP.charAt(k + j * SW) - 'a']);
            }
        }

        msprites = new BufferedImage[7];
        for (j = 0; j < 7; j++) {
            msprites[j] = ss.getSubimage(0, j * SH, SW * 2, SH);
        }

        psprites = new BufferedImage[9];
        for (j = 0; j < 9; j++) {
            psprites[j] = ss.getSubimage(0, 7 * SH + j * PH, SW * 2, PH);
        }

        /*
         * Double buffering
         */ 
        BufferedImage image = new BufferedImage(MW * SW * 2, ((MH + 4) * SH), COLORTYPE);
        Graphics2D g = (Graphics2D) image.getGraphics();
        Graphics2D g2 = null;
        
        g.setBackground(grey[0]);
        g.setColor(grey[1]);
        
        /*
         * Draw starting conditions, i.e. the map with the player where we earlier
         * defined him
         * 
         * Some space is made at the bottom to right messages to the player.  Ideally there would
         * be plenty to right here, but do to space configurations currently it is just the number
         * of 4kball the player has.  This space also displays when the player's 4kmon are healed
         * 
         * An earlier iteration of this program contained a box and the word "Status:" to provide
         * organization but this has been commented out to make space
         * 
         */
        for (i = 0; i < MW * (MH + 4); i++) {
            if (i < MW * MH) {
                g.drawImage(msprites[map[i] - 'a'], null, (i % MW) * 2 * SW, (i / MW) * SH);
            } else {
                g.drawImage(msprites[0], null, (i % MW) * 2 * SW, (i / MW) * SH);
            }

        }
        g.clearRect(0, (MH + 4) * SH, MW * SW * 2, SH * 4);
        g.drawString("4kball" + ball, 4, (MH + 4) * SH - 60);
        //g.drawRect(0, (((MH + 4) * SH) - 58) , ((MW * SW * 2) - 2) , ((SH * 2) + 10) );
        //g.drawString("4kmon" , 4, (MH + 4) * SH  - 40);

        /*
         * GAME LOOP
         * 
         */
        while (true) {
           
            /*
             * The boolean variable move is made true when an appropriate action has
             * been performed.  See the handleEvent(Event e) method.
             */
            if (move) {

                /*
                 * Perform different things depending what tile the player is trying to move to.
                 * 
                 * NOTE: There are no cases for 'b' or 'd' These are tiles for the path and the
                 * tall grass with the player on them.  Since there is only one player, these 
                 * tiles can never be moved to.
                 */
                switch (map[pl2]) {
                    /*
                     * Moving to a path tile
                     */
                    case 'a':
                        if (pl == 137 && pl2 == 5) {
                            if (lv <= 1) {
                                map[137] = 'g' + 1;
                            }
                            lv--;
                        }
                        if (pl == 5 && pl2 == 137) {
                            lv++;
                        }
                        map[pl]--;
                        pl = pl2;
                        map[pl]++;
                        break;

                    // Tall grass
                    case 'c':
                        map[pl]--;
                        /*
                         * Player moves into tall grass, there is a 1 in 5
                         * chance that a wild 4kmon wil be encountered/
                         */
                        if (rand.nextInt(5) == 0) {
                            /*
                             * Create a random 4kmon, it's type is limited by what
                             * level the player is on.
                             */
                            wkm = new int[6];
                            if (lv > 0) {
                                wkm[0] = rand.nextInt(lv + 1) + 1; //Name of 4kmon
                            } else {
                                wkm[0] = 1;
                            }
                            wkm[1] = (rand.nextInt(5) + 1 + (lv * 5))*10; // Experience (level) determined by lv on map
                            wkm[2] = (rand.nextInt(5) + 3 + 2 * (wkm[1] / 10)); //HP = HPBonus + 3 + level (10xp) * 2
                            wkm[3] = wkm[2]; // Current HP
                            wkm[4] = 3 + rand.nextInt(4) + 3 * (wkm[1]/10); //Attack
                            wkm[5] = 1 + rand.nextInt(2) + 1 * (wkm[1]/10); //Defend

                            g.setBackground(grey[0]);
                            g.setColor(grey[1]);

                            pause = true;
                            while (pause) {
                                g.clearRect(0, 0, MW * SW * 2, (MH + 4) * SH);
                                g.drawString("Wild" + "\t" + names[wkm[0] - 1], 20, 20);
                                g.drawString("Press enter...", (MW * SW * 2) - 90, (MH * SH) - 20);

                                if (g2 == null) {
                                    g2 = (Graphics2D) getGraphics();
                                    requestFocus();
                                } else {
                                    g2.drawImage(image, 0, 0, MW * SW * 2, (MH + 4) * SH, null);
                                }
                            }
                            
                            turn = (wkm[4]%2==0);
                            battle1 = true;
                            caught = false;

                            while (wkm[3] > 0 && !caught && !r && (kmC[3] > 0 || kmC[9] > 0 || kmC[17] > 0 || kmC[23] > 0)) {

                                /*
                                 * Battle with wild 4kmon
                                 */

                                g.clearRect(0, 0, MW * SW * 2, (MH + 4) * SH);
                                g.drawRect(10, 200, 90, 15);
                                g.drawString(names[8 + kmC[0 + ord * 6]], 14, 212);

//                                //If pokemon is evolved allow second attack
//                                if (kmC[0 + ord*6] > 4) {
//                                    g.drawRect(110, 200, 90, 15);
//                                    g.drawString(names[kmC[0 + ord * 6] + 3], 114, 212);
//                                }

                                //throw 4kball
                                g.drawRect(10, 230, 60, 15);
                                g.drawString("4kball" + ball, 14, 242);

                                // Switch 4kmon
                                g.drawRect(80, 230, 80, 15);
                                g.drawString("Switch" + "4kmon", 84, 242);

                                // Run
                                g.drawRect(170, 230, 40, 15);
                                g.drawString("Run", 174, 242);

                                g.drawString(names[wkm[0] - 1] + " \t Lv: " + wkm[1] / 10, 90, 20);
                                g.drawString("HP: " + wkm[3], 120, 40);
                                g.drawString(names[kmC[0 + (ord * 6)] - 1] + " \t Lv: " + kmC[1 + ord * 6] / 10, 20, 160);
                                g.drawString("HP: " + kmC[3 + (ord * 6)], 20, 180);
                                g.drawImage(psprites[wkm[0] - 1], 20, 20, SW * 4, PH * 2, null);
                                g.drawImage(psprites[kmC[0 + (ord * 6)] - 1], 160, 110, SW * 4, PH * 2, null);


                                if (g2 == null) {
                                    g2 = (Graphics2D) getGraphics();
                                    requestFocus();
                                } else {
                                    g2.drawImage(image, 0, 0, MW * SW * 2, (MH + 4) * SH, null);
                                }

                                switch (act) {
                                    case 0:     //Enemy moves (attacks)
                                        if(!turn)
                                        {
                                            kmC[3 + 6 * ord] -= 2; //For now just remove 2 health regardless
                                            t = System.nanoTime();
                                            t2 = t;
                                            
                                                while (t2 - t <= 500000000) {
                                                    t2 = System.nanoTime();
                                                }
                                            t = System.nanoTime();
                                            t2 = t;
                                            g.drawString("Wild " + names[wkm[0] - 1] + " uses ", 20, (MH + 4) * SH - 15);
                                            g.drawString(names[8 + wkm[0]], 20, (MH + 4) * SH - 5);
                                            g2.drawImage(image, 0, 0, MW * SW * 2, (MH + 4) * SH, null);
                                            for (i = 0; i < 3; i++) {
                                                while (t2 - t <= 1000000000) {
                                                    t2 = System.nanoTime();
                                                }
                                            }
                                            if(kmC[3 + 6 * ord] > 0)
                                            {
                                            turn = true;
                                            }
                                            else
                                            {
                                                kmC[3 + 6 * ord] = 0;
                                                select2 = true;
                                                act = 4;
                                            }
                                        }
                                        break;
                                    case 1:     //You attack
                                        wkm[3] -= 10;
                                        t = System.nanoTime();
                                        t2 = t;
                                        for (i = 0; i < 3; i++) {
                                            while (t2 - t <= 1000000000) {
                                                g.drawString(names[kmC[0 + ord * 6] - 1] + " uses ", 20, (MH + 4) * SH - 15);
                                                g.drawString(names[8 + kmC[0 + ord * 6]], 20, (MH + 4) * SH - 5);
                                                t2 = System.nanoTime();
                                                g2.drawImage(image, 0, 0, MW * SW * 2, (MH + 4) * SH, null);

                                            }
                                        }
                                        turn = false;
                                        act = 0;
                                        break;
//                                    case 2:  //Second attack
//                                        wkm[3] -= 5;
//                                        t = System.nanoTime();
//                                        t2 = t;
//                                        for (i = 0; i < 3; i++) {
//                                            while (t2 - t <= 1000000000) {
//                                                g.drawString( names[kmC[0 + ord * 6] - 1] + " uses " + names[kmC[0 + ord * 6] + 3], 20, (MH + 4) * SH  - 20);
//                                                t2 = System.nanoTime();
//                                                g2.drawImage(image, 0, 0, MW * SW * 2 , (MH + 4) * SH , null);
//
//                                            }
//                                        }
//                                        turn = false;
//                                        act = 0;
//                                        break;
                                    case 3:  //Throw ball
                                        ball--;
                                        act = 0;
                                        g.drawString("You" + " throw " + "4kball", 20, (MH + 4) * SH - 15);
                                        for (i = 0; i < 9; i++) {
                                            t = System.nanoTime();
                                            t2 = t;
                                            while (t2 - t <= 300000000) {
                                                g.drawString("?", 20 + (10 * i), (MH + 4) * SH - 5);
                                                g2.drawImage(image, 0, 0, MW * SW * 2, (MH + 4) * SH, null);
                                                t2 = System.nanoTime();
                                            }

                                        }

                                        if (rand.nextInt(8) < 4 && !caught) { //wkm[3] * wkm[3]
//                                            t = System.nanoTime();
//                                            t2 = t;
//                                            while (t2 - t <= 300000000) {
//                                                g.drawString("!", 120, (MH + 4) * SH  - 10);
//                                                g2.drawImage(imageb, 0, 0, MW * SW * 2 , (MH + 4) * SH , null);
//                                                t2 = System.nanoTime();
//                                            }
                                            sel = 5;
                                            select = true;
                                            while (!caught) {
                                                
                                                g.clearRect(0, 0, MW * SW * 2, (MH + 4) * SH);

                                                //g.drawString("You" + "caught" + " \t Lv: "  + wkm[1] / 10 + " " + names[wkm[0] - 1], 20, 20);
                                                g.drawString("Pick" + "spot For" + names[wkm[0] - 1], 20, 40);
                                                for (i = 0; i < 4; i++) {
                                                    g.drawRect(10, 100 + 45 * i, 200, 30);
                                                    if (kmC[0 + i * 6] != 0) {
                                                        g.drawString(names[kmC[0 + i * 6] - 1] + " \t Lv: " + kmC[1 + i * 6] / 10 + "\t HP: " + kmC[3 + i * 6] + " / " + kmC[2 + i * 6], 15, 120 + 45 * i);
                                                    }
                                                }


                                                if (!select) {

                                                    for (i = 0; i < 6; i++) {
                                                        kmC[i + sel * 6] = wkm[i];
                                                    }
                                                    act = 0;
                                                    caught = true;
                                                    g = (Graphics2D) image.getGraphics();
                                                    g2.drawImage(image, 0, 0, MW * SW * 2, (MH + 4) * SH, null);

                                                } else {
                                                    if (g2 == null) {
                                                        g2 = (Graphics2D) getGraphics();
                                                        requestFocus();
                                                    } else {
                                                        g2.drawImage(image, 0, 0, MW * SW * 2, (MH + 4) * SH, null);
                                                    }
                                                }
                                            }

                                        } else {

                                            t = System.nanoTime();
                                            t2 = t;
                                            g.setColor(grey[0]);
                                            g.fillRect(0, (((MH + 4) * SH) - (2 * SH)), MW * SW * 2, 60);
                                            g.setColor(grey[1]);
                                            while (t2 - t <= 1000000000) {
                                                g.drawString("4kball" + " fails", 10, (MH + 4) * SH - 5);
                                                g2.drawImage(image, 0, 0, MW * SW * 2, (MH + 4) * SH, null);
                                                t2 = System.nanoTime();
                                            }
                                        }
                                        turn = false;
                                        break;

                                    case 4:
                                        //Change the order of the 4kmon;
                                        while (select2) {
                                            select = true;
                                            g.clearRect(0, 0, MW * SW * 2, (MH + 4) * SH);

                                            g.drawString("Pick" + "4kmon", 20, 20);

                                            for (i = 0; i < 4; i++) {
                                                g.drawRect(10, 100 + 45 * i, 200, 30);
                                                if (kmC[0 + i * 6] != 0) {

                                                    g.drawString(names[kmC[0 + i * 6] - 1] + " \t Lv: " + kmC[1 + i * 6] / 10 + "\t HP: " + kmC[3 + i * 6] + " / " + kmC[2 + i * 6], 15, 120 + 45 * i);
                                                }


                                            }
                                            if (g2 == null) {
                                                g2 = (Graphics2D) getGraphics();
                                                requestFocus();
                                            } else {
                                                g2.drawImage(image, 0, 0, MW * SW * 2, (MH + 4) * SH, null);
                                            }


                                            do {
                                                if (kmC[0 + sel * 6] != 0 && kmC[3 + sel * 6] > 0) {
                                                    select = true;
                                                }

                                            } while (select && select2);
                                            if(ord != sel)
                                            {
                                            turn = !turn;
                                            }
                                            ord = sel;
                                            select2 = false;
                                        }
                                        act = 0;
                                        break;
                                    case 5:
                                        if (rand.nextInt(9) != 0) {
                                            r = true;
                                        }
                                        turn = false;
                                        act = 0;
                                        break;
                                }

                            }
                            r = false;
                            g2.clearRect(0, 0, MW * SW * 2, (MH + 4) * SH);
                            if (g2 == null) {
                                g2 = (Graphics2D) getGraphics();
                                requestFocus();
                            } else {
                                g2.drawImage(image, 0, 0, MW * SW * 2, (MH + 4) * SH, null);
                            }
                            if (wkm[3] <= 0) {
                                
                                
                                for(i = 0; i < (kmC[1 + ord * 6] + 8)/10 - (kmC[1 + ord * 6] )/10; i++)
                                {
                                kmC[2 + ord * 6] += 2;
                                kmC[4 + ord * 6] += 3;
                                }
                                kmC[1 + ord * 6] += 8;
                                if (kmC[1 + ord * 6] / 10 >= 10 && kmC[0 + ord * 6] < 5) {
                                    kmC[0 + ord * 6] += 5;

                                }

                            }

                            g = (Graphics2D) image.getGraphics();
                            if (g2 == null) {
                                g2 = (Graphics2D) getGraphics();
                                requestFocus();
                            } else {
                                g2.drawImage(image, 0, 0, MW * SW * 2, (MH + 4) * SH, null);
                            }

                            battle1 = false;
                        }


                        if (kmC[3] > 0 || kmC[9] > 0 || kmC[17] > 0 || kmC[23] > 0) // Conditions underwhich 4kmon ar not all dead
                        {
                            
                            pl = pl2;
                            map[pl]++;

                            break;

                        } else // All player's 4kmon are dead
                        {
                            
                            lv = 0;
                            pl = 112;
                            map[pl]++;
                            for (i = 0; i < 4; i++) {
                            kmC[3 + i * 6] = kmC[2 + i * 6];
                            }

                            //return to 4kcenter
                            break;
                            
                        }

                    // Hospital
                    case 'e':
                        /*
                         * 4kmon are all healed. The player does not move.
                         */
                        for (i = 0; i < 4; i++) {
                            kmC[3 + i * 6] = kmC[2 + i * 6];
                        }
                        t = System.nanoTime();
                        t2 = t;
                        g.setColor(grey[0]);
                        g.fillRect(0, (MH + 4) * SH, MW * SW * 2, 4 * SH);
                        g.setColor(grey[1]);
                        g.drawString("4kmon" + " healed", 6, (MH + 4) * SH - 20);
                        g2.drawImage(image, 0, 0, MW * SW * 2, (MH + 4) * SH, null);
                        for (i = 0; i < 15; i++) {
                            while (t2 - t <= 1000000000) {

                                t2 = System.nanoTime();
                                //g.drawImage(image, 0, 0, MW * SW * 2 , (MH + 4) * SH , null);

                            }
                        }
                        g.clearRect(0, (MH + 4) * SH, MW * SW * 2, 4 * SH);
                        g2.drawImage(image, 0, 0, MW * SW * 2, (MH + 4) * SH, null);

                        break;

                    //  Rock                       
                    case 'f':
                        /*
                         * The player encounters a rock barrier and does not
                         * move
                         */
                        if (pl == 5 && pl2 == 137) {

                            map[pl2] = 'a';
                            map[pl]--;
                            pl = pl2;
                            map[pl]++;
                            lv++;
                        } else {
//                            //g = (Graphics2D) image.getGraphics();
//                            t = System.nanoTime();
//                            t2 = t;
//                            g.setColor(grey[0]);
//                            g.fillRect(0, (MH + 4) * SH , MW * SW * 2 , 4 * SH );
//                            g.setColor(grey[1]);
//                            g.drawString("Me! Use phase through rock attck!", 6, (MH + 4) * SH  - 20);
//                            g.drawString("... OUCH!", 6, (MH + 4) * SH  - 10);
//                            g2.drawImage(image, 0, 0, MW * SW * 2 , (MH + 4) * SH , null);
//                            for (i = 0; i < 15; i++) {
//                                while (t2 - t <= 1000000000) {
//
//                                    t2 = System.nanoTime();
//                                    //g.drawImage(image, 0, 0, MW * SW * 2 , (MH + 4) * SH , null);
//
//                                }
//                            }
//                            g.clearRect(0, (MH + 4) * SH , MW * SW * 2 , 4 * SH );
//                            g2.drawImage(image, 0, 0, MW * SW * 2 , (MH + 4) * SH , null);
                        }

                        break;

                    // NPC (Trainer)
                    case 'g':
                        /*
                         * The player does not move. Launch a battle with the
                         * 4kmon trainer
                         */
                        if (pl == 137 && pl2 == 5) {
                            if (lv <= 1) {
                                map[137] = 'g' + 1;
                            }
                            map[pl2] = 'a';
                            map[pl]--;
                            pl = pl2;
                            map[pl]++;
                            lv--;

                        } else {

                            /*
                             * Player moves into tall grass, there is a 1 in 5
                             * chance that a wild 4kmon wil be encountered/
                             */

                            wkm = new int[6];
                            //Random 4kmon
                            wkm[0] = lv + 2; //Name of 4kmon
                            wkm[1] = (10 + 3 + (lv * 5))*10; // Experience (level) determined by lv on map
                            wkm[2] = (5 + 3 + 2 * (wkm[1] / 10)); //HP = HPBonus + 3 + level (10xp) * 2
                            wkm[3] = wkm[2]; // Current HP
                            wkm[4] = 3 + 4 + 3 * (wkm[1]/10); //Attack
                            wkm[5] = 1 + 2 + 1 * (wkm[1]/10); //Defend
//                            g = (Graphics2D) imageb.getGraphics();
                            g.setBackground(grey[0]);
                            g.setColor(grey[1]);

                            pause = true;
                            while (pause) {
                                g.clearRect(0, 0, MW * SW * 2, (MH + 4) * SH);
                                g.drawString("Enemy trainer wants to fight!", 20, 20);

                                g.drawString("Press enter...", (MW * SW * 2) - 90, (MH * SH) - 20);
                                if (g2 == null) {
                                    g2 = (Graphics2D) getGraphics();
                                    requestFocus();
                                } else {
                                    g2.drawImage(image, 0, 0, MW * SW * 2, (MH + 4) * SH, null);
                                }
                            }
                            
                            turn = true;
                            battle1 = true;

                            //g = (Graphics2D) imageb.getGraphics();

                            while (wkm[3] > 0 && (kmC[3] > 0 || kmC[9] > 0 || kmC[17] > 0 || kmC[23] > 0)) {
                                /*
                                 * Battle with wild 4kmon
                                 */

                                g.clearRect(0, 0, MW * SW * 2, (MH + 4) * SH);
                                g.drawRect(10, 200, 90, 15);
                                g.drawString(names[8 + kmC[0 + ord * 6]], 14, 212);

//                                //If pokemon is evolved allow second attack
//                                if (kmC[0 + ord*6] > 4) {
//                                    g.drawRect(110, 200, 90, 15);
//                                    g.drawString(names[kmC[0 + ord * 6] + 3], 114, 212);
//                                }

                                //throw 4kball
                                g.drawRect(10, 230, 60, 15);
                                g.drawString("4kball" + ball, 14, 242);

                                // Switch 4kmon
                                g.drawRect(80, 230, 80, 15);
                                g.drawString("Switch" + "4kmon", 84, 242);

                                // Run
                                g.drawRect(170, 230, 40, 15);
                                g.drawString("Run", 174, 242);

                                g.drawString(names[wkm[0] - 1] + " \t Lv: " + wkm[1] / 10, 90, 20);
                                g.drawString("HP: " + wkm[3], 120, 40);
                                g.drawString(names[kmC[0 + (ord * 6)] - 1] + " \t Lv: " + kmC[1 + ord * 6] / 10, 20, 160);
                                g.drawString("HP: " + kmC[3 + (ord * 6)], 20, 180);
                                g.drawImage(psprites[wkm[0] - 1], 20, 20, SW * 4, PH * 2, null);
                                g.drawImage(psprites[kmC[0 + (ord * 6)] - 1], 160, 110, SW * 4, PH * 2, null);

                                // Enemy moves


                                if (g2 == null) {
                                    g2 = (Graphics2D) getGraphics();
                                    requestFocus();
                                } else {
                                    g2.drawImage(image, 0, 0, MW * SW * 2, (MH + 4) * SH, null);
                                }
                                
                                switch (act) {
                                    case 0:     //Enemy moves (attacks)
                                        if(!turn)
                                        {
                                            kmC[3 + 6 * ord] -= 2; //For now just remove 2 health regardless
                                            t = System.nanoTime();
                                            t2 = t;
                                            
                                                while (t2 - t <= 500000000) {
                                                    t2 = System.nanoTime();
                                                }
                                            t = System.nanoTime();
                                            t2 = t;
                                            g.drawString(names[wkm[0] - 1] + " uses ", 20, (MH + 4) * SH - 15);
                                            g.drawString(names[8 + wkm[0]], 20, (MH + 4) * SH - 5);
                                            g2.drawImage(image, 0, 0, MW * SW * 2, (MH + 4) * SH, null);
                                            for (i = 0; i < 3; i++) {
                                                while (t2 - t <= 1000000000) {
                                                    t2 = System.nanoTime();
                                                }
                                            }
                                            if(kmC[3 + 6 * ord] > 0)
                                            {
                                            turn = true;
                                            }
                                            else
                                            {                                               
                                                kmC[3 + 6 * ord] = 0;
                                                select2 = true;
                                                act = 4;
                                            }
                                        }
                                        break;
                                    case 1:     //You attack
                                        wkm[3] -= 10;
                                        t = System.nanoTime();
                                        t2 = t;
                                        for (i = 0; i < 3; i++) {
                                            while (t2 - t <= 1000000000) {
                                                g.drawString(names[kmC[0 + ord * 6] - 1] + " uses ", 20, (MH + 4) * SH - 15);
                                                g.drawString(names[8 + kmC[0 + ord * 6]], 20, (MH + 4) * SH - 5);
                                                t2 = System.nanoTime();
                                                g2.drawImage(image, 0, 0, MW * SW * 2, (MH + 4) * SH, null);

                                            }
                                        }
                                        turn = false;
                                        act = 0;
                                        break;
//                                    case 2:
//                                        wkm[3] -= 5;
//                                        t = System.nanoTime();
//                                        t2 = t;
//                                        for (i = 0; i < 3; i++) {
//                                            while (t2 - t <= 1000000000) {
//                                                g.drawString( names[kmC[0 + ord * 6] - 1] + " uses " + names[kmC[0 + ord * 6] + 3], 20, (MH + 4) * SH  - 20);
//                                                t2 = System.nanoTime();
//                                                g2.drawImage(image, 0, 0, MW * SW * 2 , (MH + 4) * SH , null);
//
//                                            }
//                                        }
//                                        act = 0;
//                                        break;
                                    case 3:
                                        ball--;
                                        act = 0;
                                        g.drawString("You" + " throw a " + "4kball", 20, (MH + 4) * SH - 20);
                                        for (i = 0; i < 9; i++) {
                                            t = System.nanoTime();
                                            t2 = t;
                                            while (t2 - t <= 300000000) {
                                                g.drawString("?", 20 + (10 * i), (MH + 4) * SH - 10);
                                                g2.drawImage(image, 0, 0, MW * SW * 2, (MH + 4) * SH, null);
                                                t2 = System.nanoTime();
                                            }


                                        }

                                        t = System.nanoTime();
                                        t2 = t;
                                        g.setColor(grey[0]);
                                        g.fillRect(0, (((MH + 4) * SH) - (2 * SH)), MW * SW * 2, 60);
                                        g.setColor(grey[1]);
                                        while (t2 - t <= 1000000000) {
                                            g.drawString("4kball" + " fails", 10, (MH + 4) * SH - 20);
                                            g2.drawImage(image, 0, 0, MW * SW * 2, (MH + 4) * SH, null);
                                            t2 = System.nanoTime();

                                        }
                                        turn = false;
                                        break;
                                    case 4:
                                        //Change the order of the 4kmon;
                                        while (select2) {
                                            select = true;
                                            g.clearRect(0, 0, MW * SW * 2, (MH + 4) * SH);

                                            g.drawString("Pick" + "4kmon", 20, 20);

                                            for (i = 0; i < 4; i++) {
                                                g.drawRect(10, 100 + 45 * i, 200, 30);
                                                if (kmC[0 + i * 6] != 0) {

                                                    g.drawString(names[kmC[0 + i * 6] - 1] + " \t Lv: " + kmC[1 + i * 6] / 10 + "\t HP: " + kmC[3 + i * 6] + " / " + kmC[2 + i * 6], 15, 120 + 45 * i);
                                                }


                                            }
                                            if (g2 == null) {
                                                g2 = (Graphics2D) getGraphics();
                                                requestFocus();
                                            } else {
                                                g2.drawImage(image, 0, 0, MW * SW * 2, (MH + 4) * SH, null);
                                            }


                                            do {
                                                if (kmC[0 + sel * 6] == 0 && kmC[3 + sel * 6] > 0) {
                                                    select = true;
                                                }

                                            } while (select && select2);
                                            if(ord != sel)
                                            {
                                            turn = !turn;
                                            }
                                            ord = sel;
                                            select2 = false;
                                        }
                                        act = 0;
                                        break;
                                    case 5:
                                        act = 0; //Can't run from 4kmon trainer battles
                                        turn = false;
                                        break;

                                }

                            }

                            g2.clearRect(0, 0, MW * SW * 2, (MH + 4) * SH);
                            if (g2 == null) {
                                g2 = (Graphics2D) getGraphics();
                                requestFocus();
                            } else {
                                g2.drawImage(image, 0, 0, MW * SW * 2, (MH + 4) * SH, null);
                            }
                            if (wkm[3] <= 0) {
                                ball += 3;
                                for(i = 0; i < (kmC[1 + ord * 6] + 8)/10 - (kmC[1 + ord * 6])/10; i++)
                                {
                                kmC[2 + ord * 6] += 2;
                                kmC[4 + ord * 6] += 3;
                                }
                                kmC[1 + ord * 6] += 8;
                                if (kmC[1 + ord * 6] / 10 >= 10 && kmC[0 + ord * 6] < 5) {
                                    kmC[0 + ord * 6] += 5;
                                }
                                map[pl]--;
                                map[pl2] = 'a'; //Once fought, do not allow to be fought again.
                                pl = pl2;
                                map[pl]++;
                                win[lv] = true;

                            break;

                        } else // All player's 4kmon are dead
                        {
                            
                            lv = 0;
                            map[pl]--;
                            pl = 112;
                            map[pl]++;
                            for (i = 0; i < 4; i++) {
                            kmC[3 + i * 6] = kmC[2 + i * 6];
                            }

                            //return to 4kcenter
                            break;
                            
                        }
                            }

                            g = (Graphics2D) image.getGraphics();
                            if (g2 == null) {
                                g2 = (Graphics2D) getGraphics();
                                requestFocus();
                            } else {
                                g2.drawImage(image, 0, 0, MW * SW * 2, (MH + 4) * SH, null);
                            }

                            battle1 = false;

                        
                        break;
                }



                if (!win[lv]) {
                    map[5] = 'g';
                }
                if (lv > 0) {
                    map[114] = 'f';

                } else {
                    map[114] = 'e';
                    map[137] = 'f';

                }

                /*
                 * We want to print our map, for now, the map is just printed to
                 * the System.out as the contents of the integer array that is
                 * the map.
                 *
                 * To make it easier to read, the part of the map that is path
                 * will appear as a " "
                 */



                for (i = 0; i < MW * (MH + 4); i++) {
                    if (i < MW * MH) {
                        g.drawImage(msprites[map[i] - 'a'], null, (i % MW) * 2 * SW, (i / MW) * SH);
                    } else {
                        g.drawImage(msprites[0], null, (i % MW) * 2 * SW, (i / MW) * SH);
                    }

                }

                g.setColor(grey[0]);
                g.fillRect(0, (MH + 4) * SH, MW * SW * 2, SH * 4);
                g.setColor(grey[1]);
                g.drawString("4kball" + ball, 4, (MH + 4) * SH - 60);
//                g.drawRect(0, (((MH + 4) * SH) - 58) , ((MW * SW * 2) - 2) , ((SH * 2) + 10) );
//                g.drawString("Status: ", 4, (MH + 4) * SH  - 40);


                move = false;
            }

            if (g2 == null) {
                g2 = (Graphics2D) getGraphics();
                requestFocus();
            } else {
                g2.drawImage(image, 0, 0, MW * SW * 2, (MH + 4) * SH, null);
            }


        }
    }

    public boolean handleEvent(Event e) {
        if (select) {
            //System.out.print("Select is true");
            if (e.id == Event.MOUSE_DOWN) {
                //System.out.println((200 + e.x - (10 )) / (200 ) + ", " + (((e.y - (100 )) / (15 ))));

                if ((200 + e.x - (10)) / (200) == 1 && ((((e.y - (100)) / (15))) == 0 || ((((e.y - (100)) / (15)))) == 1)) {
                    sel = 0;
                    //  System.out.println("Selected " + sel);
                    select = false;
                }
                if ((200 + e.x - (10)) / (200) == 1 && (((((e.y - (100)) / (15)))) == 3 || ((((e.y - (100)) / (15)))) == 4)) {
                    sel = 1;
                    //                    System.out.println("Selected " + sel);
                    select = false;
                }
                if ((200 + e.x - (10)) / (200) == 1 && ((((e.y - (100)) / (15))) == 6 || (((e.y - (100)) / (15))) == 7)) {
                    sel = 2;
                    //                    System.out.println("Selected " + sel);
                    select = false;
                }
                if ((200 + e.x - (10)) / (200) == 1 && ((((e.y - (100)) / (15))) == 9 || (((e.y - (100)) / (15))) == 10)) {
                    sel = 3;
                    //                    System.out.println("Selected " + sel);
                    select = false;
                }
            }
        }

        if (pause) {
            if (e.id == Event.KEY_PRESS) {
                if (e.key == 10) {
                    pause = false;
                }
            }
        }


        if (battle1 && turn) {
            if (e.id == Event.MOUSE_DOWN) {
                //System.out.println("" + e.x + ", " + e.y);
                //System.out.println(e.x + "\t , \t" + e.y);
                if ((e.x >= 10 && e.x <= 100) && (e.y - 200) / (15) == 0) {
                    //level 1 attack
                    act = 1;
                }
//                if ((e.x >= 110 && e.x <= 200)  && (e.y - 200 ) / ( 15) == 0 && kmC[1 + 6 * ord] / 10 > 10) {
//                    //level 2 attack
//                    act = 2;
//                }
                if ((e.x >= 10 && e.x <= 70) && (e.y - 230) / (15) == 0 && ball > 0) {
                    //Throw 4kball
                    if (ball > 0) {
                        act = 3;
                    }
                }
                if ((e.x >= 80 && e.x <= 160) && (e.y - 230) / (15) == 0) {
                    //switch 4kmon
                    select2 = true;
                    act = 4;
                }
                if ((e.x >= 170 && e.x <= 210) && (e.y - 230) / (15) == 0) {
                    //Run
                    act = 5;
                }

            }
        } else {
            odd = !odd;

            if (odd) //When key is hit, it is being registered once.  This way only every other event is 'handled'
            {
                switch (e.key) {
                    case 1006: //Left
                        pl2 = ((pl / MW) * MW) + ((pl + MW - 1) % MW);
                        move = true;
                        break;
                    case 1007: //Right
                        pl2 = ((pl / MW) * MW) + ((pl + 1) % MW);
                        move = true;
                        break;
                    case 1004: //Up
                        /*
                         * An earlier version of this code, resulted in an error
                         * suggesting Java does not perform modul arithmetic on
                         * negative numbers. To remedy this problem, instead of
                         * subtracting 12, add 132.
                         *
                         */
                        pl2 = (pl + ((MH - 1) * MW)) % (MH * MW);
                        move = true;
                        break;
                    case 1005: //Down
                        //map[pl]--;
                        pl2 = (pl + MW) % (MH * MW);
                        move = true;
                        break;
                }
            }


        }

        //Add code for random encounter or other encounter.


        return true;

    }
}
