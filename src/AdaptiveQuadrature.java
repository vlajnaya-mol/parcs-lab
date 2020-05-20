import java.util.*;
import java.io.*;
import parcs.*;

public class AdaptiveQuadrature  implements AM{
    static double f(double x) {
	x = x + 0.25;
	//System.out.println(x);
	double temp = x;
	double res = 0;
        for (int k = 1; k < 300000; k++){
        	x = x * 0.99999999;
		res += Math.exp(x - 7) / Math.sqrt(2 * Math.PI);
	}
    	return res;
}

    static double trapezoid(double a, double b, int N) {
        double h = (b - a) / N;
        double sum = 0.5 *  h * (f(a) + f(b));
        for (int k = 1; k < N; k++)
            sum = sum + h * f(a + h*k);
        return sum;
    }

    static double adaptive(double a, double b) {
        System.out.println("\nStartAdaptive");
	double area  = trapezoid(a , b , 100);
        double check = trapezoid(a , b ,  50);
        if (Math.abs(area - check) > 0.0000001) {
            double m = (a + b) / 2;
            area = adaptive(a, m) + adaptive(m, b);
        }
        return area;
    }
    
    public void run(AMInfo info) {
        System.out.println("\nStart receiving data");
	double a = info.parent.readDouble();
	double b = info.parent.readDouble();//2.0;
	long startTime = System.nanoTime();
        System.out.println("\nData received! "+Double.toString(a)+" "+Double.toString(b));
	double res = adaptive(a, b);
        
        System.out.println("\nAdaptive res: " + Double.toString(res));
	info.parent.write(res);
        double estimatedTime = (double) (System.nanoTime() - startTime) / 1000000000;
	System.out.println("\nTime spent in node: " + estimatedTime);

	}
}
