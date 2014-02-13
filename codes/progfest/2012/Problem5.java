import java.util.Scanner;

class Problem5 {
   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);

      while (sc.hasNext()) {
         int n = sc.nextInt();

         int move = 0;
         int[][] grid = new int[n][n];

         // Init grid with all 0
         for (int row = 0; row < grid.length; ++row) {
            for (int col = 0; col < grid.length; ++col) {
               grid[row][col] = 0;
            }
         }

         // loop through each stone
         for (int i = 0; i < n; ++i) {

            // convert to 0-index based
            int stoneX = sc.nextInt() - 1;
            int stoneY = sc.nextInt() - 1;

            for (int row = 0; row < grid.length; ++row) {
               for (int col = 0; col < grid.length; ++col) {
                  grid[row][col] += ( Math.abs(row - stoneX) + Math.abs(col - stoneY) );
               }
            }
         }

         for (int row = 0; row < grid.length; ++row) {
            for (int col = 0; col < grid.length; ++col) {
               System.out.printf("%3d", grid[row][col]);
            }
            System.out.println();
         }
      }
   }
}
