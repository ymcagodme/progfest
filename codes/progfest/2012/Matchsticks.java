import java.util.Scanner;

class Matchsticks {
   static int[] cost = {6, 2, 5, 5, 4, 5, 6, 3, 7, 5};

   public static void main(String[] args) {
      int x = 0;
      int y = 0;
      int limit = 10;
      while (3 * x < limit) {
         y = 0;
         while(5 * y < limit) {
            if (3 * x + 5 * y > 10)
               break;
            System.out.printf("(%d, %d) ", x, y);
            ++y;
         }
         ++x;
      }
   }
}
