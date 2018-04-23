import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
/**
 * Created by Jim on 04.04.2018.
 */
public class TIK {
    public TIK(){
        lab1();
    }
    public void lab1(){
        try {
            Helper h = new Helper();
            String t = Integer.toBinaryString(255);
            System.out.println(t);
            BufferedImage grayimg = h.getGrayImgModel(ImageIO.read(new File("img.jpg")));
            int width = grayimg.getWidth(),
                    height = grayimg.getHeight();
            Color c;
            ArrayList<Integer> pixels = new ArrayList<Integer>();
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    c = new Color(grayimg.getRGB(i,j));
                    pixels.add(c.getRed());
                }
            }
            ArrayList<String> binaryPixels = new ArrayList<String>();
            for (int el:pixels) {
                binaryPixels.add(Integer.toBinaryString(el));
            }
            for (int i =0;i<binaryPixels.size();i++) {
                System.out.println("Index: "+i+", value: "+ binaryPixels.get(i));
            }
            double solidlen = 0;
            for (int i = 0; i <binaryPixels.size(); i++) {
                solidlen+=binaryPixels.get(i).length();
            }
            System.out.println("Solid length: "+ solidlen);
            ArrayList<String[]> massages = h.getMassagesList(binaryPixels);
            h.convertFrequencyToChance(massages);
            printArray(massages);
            double entropy = h.getEntropy(massages);
            System.out.println("Entropy: "+ entropy);

            double codedlen = h.huffmanCoding(massages, binaryPixels.size());
            System.out.println("Compression efficiency: " + (solidlen/codedlen));
            ImageIO.write(grayimg, "jpg", new File("result.jpg"));
        }catch (IOException e){e.printStackTrace();}
    }
    public static void printArray(ArrayList<String[]> list){
        for (int i = 0; i <list.size(); i++) {
            System.out.println("Value: "+list.get(i)[0]+", chance: "+ list.get(i)[1]);
        }
    }
}
