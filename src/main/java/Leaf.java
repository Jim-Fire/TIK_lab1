import java.util.ArrayList;
/**
 * Created by Jim on 05.04.2018.
 */
public class Leaf {
    private double weight=0;
    private ArrayList<String[]> params;
    public Leaf( ArrayList<String[]> params){//значение частота код
        for (int i = 0; i < params.size(); i++) {
            weight+=Double.parseDouble(params.get(i)[1]);
        }
        this.params = params;
    }
    public ArrayList<String[]> getParams() {
        return params;
    }
    public double getWeight() {
        return weight;
    }
    public void appendBinary(String s){
        for (int i = 0; i < params.size(); i++) {
            params.get(i)[2] += s;
        }
    }
    public static Leaf getNewLeaf(Leaf firstLeaf,Leaf secondLeaf){
       return new Leaf(joinArrays(firstLeaf.getParams(),secondLeaf.getParams()));
    }
    private static ArrayList<String[]> joinArrays( ArrayList<String[]> f,  ArrayList<String[]> s){
        ArrayList<String[]> res = new ArrayList<String[]>();
        for (int i = 0; i < f.size(); i++) {
            res.add(f.get(i));
        }
        for (int i = 0; i < s.size(); i++) {
            res.add(s.get(i));
        }
        return res;
    }
}
