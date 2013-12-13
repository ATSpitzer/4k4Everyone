/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Fools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;

/**
 *
 * @author andrewspitzer
 */
public class SpriteMaker {
static String picture;
final static String SPRITEKEY = "abcdefghijklmnopqrstuvwxyz";
        
    public static void main(String[] args) {
        String sprite = "";
        ArrayList<Integer> spriteColors;
        spriteColors = new ArrayList<Integer>();
        Scanner sc = new Scanner(System.in);
        if(sc.hasNextLine())
        {
            picture = sc.nextLine();
        }
        BufferedImage img = null;
        try {
        img = ImageIO.read(new File(picture));
        int w = img.getWidth();
        int h = img.getHeight();
        System.out.println(picture +"Width: " + w + "Height " + h +":\n\n");        
        for(int i = 0; i < w; i++) {
            for(int j = 0; j < h; j++) {
                Integer c = new Integer(img.getRGB(i, j));
                if(!spriteColors.contains(c)) {
                    spriteColors.add(c);
                }
                sprite += SPRITEKEY.charAt(spriteColors.indexOf(c));
                
            }
            sprite += "\"\n\t+\"";
        }
        
        System.out.println("\n\n" + sprite + "\n\n");
        
        for(Integer x : spriteColors) {
            System.out.println(SPRITEKEY.charAt(spriteColors.indexOf(x)) + ":\t" + x);
        }
        
        } catch (IOException e) {
            System.out.println("\n" +picture + " not found\n");
        }

    }
    
}
