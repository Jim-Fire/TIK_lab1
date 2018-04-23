import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
/**
 * Created by Jim on 04.04.2018.
 */
public class Helper {
    public BufferedImage getGrayImgModel(BufferedImage img){
        int width = img.getWidth(),
            height = img.getHeight(),
            tempRGB;
        Color c;
        BufferedImage res = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                c = new Color(img.getRGB(i,j));
                tempRGB = (c.getRed() + c.getGreen() + c.getBlue())/3;
                c = new Color(tempRGB,tempRGB,tempRGB);
                res.setRGB(i,j,c.getRGB());
            }
        }
        return res;
    }
    public ArrayList<String[]> getMassagesList(ArrayList<String> list){
        ArrayList<String[]> result = new ArrayList<String[]>();
        ArrayList<String> memory = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            if(memory.contains(list.get(i))){
                int index = getIndexList(result,list.get(i));
                //System.out.println("index "+index+ " list value "+ list.get(i));
                String[] resTemp = new String[2];
                resTemp[0] = result.get(index)[0];
                resTemp[1] = String.valueOf(1+Integer.parseInt(result.get(index)[1]));
                result.set(index,resTemp);
            }else {
                String[] st = new String[2];
                st[0] = list.get(i);st[1] = "1";
                result.add(st);
                memory.add(list.get(i));
                //System.out.println("Current res list: ");
                //for (String[] s:result) {
                //    System.out.println("[v: "+ s[0]+ ", j: "+ s[1]+"]");
                //}
            }
        }
        return result;
    }
    public ArrayList<String[]> convertFrequencyToChance(ArrayList<String[]> list){
        double s = 0;
        for (int i = 0; i < list.size(); i++) {
            s+=Integer.parseInt(list.get(i)[1]);
        }
        System.out.println("s= "+s);
        for (int i = 0; i < list.size(); i++) {
            String[] arr = new String[3];
            arr[0] = list.get(i)[0];
            double temp= Double.parseDouble(list.get(i)[1])/s;
            arr[1] = Double.toString(temp);
            arr[2] = "";
            list.set(i,arr);
        }
        return list;
    }
    public double getEntropy(ArrayList<String[]> list){
        double result = 0;
        for (int i = 0; i < list.size(); i++) {
            result-=Double.parseDouble(list.get(i)[1])*Math.log(Double.parseDouble(list.get(i)[1]));
        }
        return result;
    }
    public int huffmanCoding(ArrayList<String[]> list, int lenth){
        ArrayList<Leaf> net = new ArrayList<Leaf>();
        for (int i = 0; i < list.size(); i++) {
            ArrayList<String[]> li = new ArrayList<String[]>();
            li.add(list.get(i));
            net.add(new Leaf(li));
        }
        while(net.size()>1){
            int oneIndex = getMinimalIndexLeaf(net);
            net.get(oneIndex).appendBinary("1");
            Leaf fLeaf = net.get(oneIndex);
            net.remove(oneIndex);
            int zeroIndex = getMinimalIndexLeaf(net);
            net.get(zeroIndex).appendBinary("0");
            Leaf sLeaf = net.get(zeroIndex);
            net.remove(zeroIndex);
            net.add(Leaf.getNewLeaf(fLeaf,sLeaf));
        }
        int solid = 0;
        for (int i = 0; i < net.size(); i++) {
            System.out.println("Leafs: ");
            Leaf l = net.get(i);
            ArrayList<String[]> params = l.getParams();

            solid = 0;
            for (int j = 0; j < params.size(); j++) {
                params.get(j)[1]= Double.toString(lenth*Double.parseDouble(params.get(j)[1]));
            }
            for (int j = 0; j < params.size(); j++) {
                System.out.println("Code: "+ params.get(j)[0]+ ", Chance: "+ params.get(j)[1]+ ", huffmanCode: "+ params.get(j)[2]);
            }
            for (int j = 0; j < params.size(); j++) {
                solid+=Double.parseDouble(params.get(j)[1])* params.get(j)[2].length();
            }
            System.out.println("Solidlen: " + solid);
        }
        return solid;
    }
    public int getMinimalIndexLeaf(ArrayList<Leaf> net){
        double minimal=1000;
        int index = 0;
        for (int i = 0; i < net.size(); i++) {
            if(net.get(i).getWeight()<=minimal){
                minimal = net.get(i).getWeight();
                index = i;
            }
        }
        return index;
    }
    private static int getIndexList(ArrayList<String[]> list, String value){
        //System.out.println("size "+list.size());
        for (int i = 0; i < list.size(); i++) {
            //System.out.println("element "+i+"|"+list.get(i)[0]);
            if(list.get(i)[0].equals(value)){return i;}
        }
        return -1;
    }
}
