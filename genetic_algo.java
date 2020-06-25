import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class genetic_algo {
    public static int[] init_1(){
        Random r = new Random();
        int[] arr = new int[4];
        System.out.printf("랜덤으로 선택된 arr");
        for (int i = 0; i < 4; i++) {
            arr[i] = r.nextInt(31)+1;
            System.out.printf(" %d", arr[i]);
        }
        return arr;
    }

    public static int[] init_2() {
        Random r = new Random();
        int[] arr2 = new int[4];
        System.out.printf("\n랜덤으로 선택된 arr2");
        for(int i=0; i<4; i++) {
            arr2[i] = r.nextInt(31)+1;
            System.out.printf(" %d", arr2[i]);
        }
        return arr2;
    }


    public static int MSE(int w, int z ,int[] x, int[] y){
        int sum;
        int MSE = 0;

        for (int i = 0; i < x.length; i++) {
            sum = y[i]-(w*x[i]+z);
            MSE = MSE + sum*sum ;
        }

        return MSE;
    }

    public static int[] selection( int[] x, int[] y, int[] w, int[] z) {
        int sum=0;
        int[] f = new int[w.length];
        double[] ratio = new double[w.length];

        for (int i = 0; i < w.length; i++) {
            f[i] = MSE(w[i],z[i],x,y);
            sum += f[i];
        }
        for (int i = 0; i < w.length; i++) {
            f[i] = sum-f[i];
        }
        sum=0;
        for (int i = 0; i < w.length; i++) {
            sum += f[i];
        }

        for (int i = 0; i < ratio.length; i++) {
            if(i==0) ratio[i] = (double)(f[i])/(double)sum;
            else ratio[i] = ratio[i-1]+((double)(f[i])/(double)sum);
        }
        int[] sx = new int[w.length*2];

        Random r = new Random();

        for (int i = 0; i < w.length; i++) {
            double p = r.nextInt();
            if(p<ratio[0]){
                sx[i] = w[0];
                sx[i+w.length] = z[0];
            }
            else if(p<ratio[1]){
                sx[i] = w[1];
                sx[i+w.length] = z[1];
            }
            else if(p<ratio[2]){
                sx[i] = w[2];
                sx[i+w.length] = z[2];
            }
            else {
                sx[i] = w[3];
                sx[i+w.length] = z[3];
            }
        }
        return sx;
    }

    public static String int2String(String x) {
        return String.format("%8s", x).replace(' ', '0');
    }

    public static String[] crossover(int[] x) {
        String[] arr = new String[x.length];
        for(int i=0; i<x.length; i+=2) {
            String bit1 = int2String(Integer.toBinaryString(x[i]));
            String bit2 = int2String(Integer.toBinaryString(x[i+1]));

            arr[i] = bit1.substring(0, 2) + bit2.substring(2,5);
            arr[i+1] = bit2.substring(0, 2) + bit1.substring(2,5);
        }

        return arr;
    }

    public static int invert(String x) {
        Random r = new Random();
        int a = Integer.parseInt(x,2);
        for(int i=0; i<x.length(); i++) {
            double p = (double)1/ (double)32;
            if(r.nextDouble() < p) {
                a = 1 << i ^ a;
            }
        }
        return a;
    }

    public static int[] mutation(String[] x) {
        int[] arr = new int[x.length];
        for (int i=0; i<x.length; i++) {
            arr[i] = invert(x[i]);
        }
        return arr;
    }

    public static void main(String[] args) {

        int[] pp = init_1();
        int[] qq = init_2();


        int[] x = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
        int[] y = {4,5,8,10,12,13,16,18,20,22,23,25,28,29,31,33,36,35,38,41};


        int[] re = new int[pp.length];
        double min = 3000.0;
        int re1=0;
        int re2=0;

        for(int i=0; i<1000; i++) {
            int[] sx = selection(pp,qq,x,y);
            String[] cx = crossover(sx);
            int[] mx = mutation(cx);

            for (int j = 0; j <pp.length ; j++) {
                pp[j] = mx[j];
                qq[j] = mx[j+pp.length];
            }
            for(int j = 0; j <pp.length; j++) {
                re[j] = MSE(pp[j],qq[j],x,y);
                if(min>re[j]){
                    min = re[j];
                    re1 = pp[j];
                    re2 = qq[j];
                }
            }
        }
        System.out.println("\n유전알고리즘의 회귀식 y = " +re1+"x + "+ re2);
    }
}