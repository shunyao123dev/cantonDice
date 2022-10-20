package comp1110.ass2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class T {
    public static void main(String[] args) {
        String s="";
        System.out.println(s.length());
        String[] S = s.split(",");
        System.out.println(Arrays.toString(S));
        System.out.println(S);
        String[] s1 = {};
        System.out.println(s1.length);
        List<String> s2 = new ArrayList<>(Arrays.asList(s1));
        System.out.println(s2);
        s2.add("R1");
        System.out.println(s2.get(0));
        List<String> s3 = new ArrayList<>();
        System.out.println(s3);
    }
}
