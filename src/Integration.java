import java.util.List;
import java.util.ArrayList;
import java.util.*;
import java.io.*;
import parcs.*;

public class Integration{
    public static void main(String[] args) {
        Const.parseArgs(args);
        task curtask = new task();
        curtask.addJarFile("AdaptiveQuadrature.jar");
        (new Integration()).run(new AMInfo(curtask, (channel)null));
        curtask.end();
    }
    
    public void run(AMInfo info) {
        System.out.println("\nRun started!");
        int num_slaves = 3;
	double a;
	double b;
        List<point> points = new ArrayList<point>();
        List<channel> channels = new ArrayList<channel>();
        long startTime = System.nanoTime();
	for (int i=0; i<num_slaves; i++) {
            points.add(info.createPoint());
            channels.add(points.get(i).createChannel());
            points.get(i).execute("AdaptiveQuadrature");
            System.out.println("\nChannel created!");
            a = (double) i / num_slaves;
	    channels.get(i).write(a);
            b = (double) (i+1) / num_slaves;
	    channels.get(i).write(b);//(i + 1) / num_slaves);
            System.out.println("\nChannel written!");
        }

        double res = 0;
        for (int i=0; i<num_slaves; i++) {
            res = res + channels.get(i).readDouble();
	    System.out.println("\nReceived result from channel!");
        }
        double estimatedTime = (double) (System.nanoTime() - startTime) / 1000000000;
	System.out.println("\nTime total: " + estimatedTime);
        System.out.println("\nResult: " + Double.toString(res));
    }

}
