import java.util.Scanner;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;

class Problem1 {
   static final int LIMIT = 10000000;
   static double[] r = new double[LIMIT];
   static double[] g = new double[LIMIT];
   static double[] b = new double[LIMIT];

   public static void main(String[] args) throws IOException {
      if (args.length < 1)
         return;

      Scanner sc = new Scanner(new File(args[0]));

      String cluster = sc.nextLine();
      String[] l = cluster.split(", ");
      System.out.println("Clusters: ");
      for (int i = 0; i < l.length; i += 3) {
         System.out.printf("Cluster %d: (", i / 3);
         for (int j = 0; j < 3; ++j) {
            System.out.print(l[i + j] + " ");
         }
         System.out.println(")");
      }

      int counter = 0;
      while (sc.hasNext()) {
         String line = sc.nextLine();
         String[] d = line.split(", ");
         r[counter] = Double.parseDouble(d[0]);
         g[counter] = Double.parseDouble(d[1]);
         b[counter] = Double.parseDouble(d[2]);
         System.out.printf("pixel %d: ", counter);
         System.out.print(r[counter] + " ");
         System.out.print(g[counter] + " ");
         System.out.print(b[counter] + " ");
         System.out.println();
         ++counter;
      }
   }
}
