// Given a 2D matrix with prices and a budget, find the biggest rectangular
// area you can purchase with the given budget.
public class RealEstateQuestion {

  // Brute-force approach solution.
  private int solve(int[][] prices, int budget) {
    int biggestArea = Integer.MIN_VALUE;

    // First two loops determine the top-left corner of the rectangles.
    for (int x = 0; x < prices.length; x++) {
      for (int y = 0; y < prices[x].length; y++) {

        // The following two loops determine the width & height of the rectangles.
        for (int width = 1; width <= prices.length - x; width++) {
          for (int height = 1; height <= prices[x].length - y; height++) {

            // Compute the price of the given rectangle &
            // Determine whether to update the max area or not.
            int currentPrice = calculatePrice(prices, x, y, width, height);
            if (currentPrice <= budget) {
              int currentArea = width * height;
              biggestArea = Integer.max(biggestArea, currentArea);
            }
          }
        }
      }
    }

    return biggestArea;
  }

  private int calculatePrice(int[][] prices, int x, int y, int width, int height) {
    int sum = 0;
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        sum += prices[x + i][y + j];
      }
    }

    return sum;
  }

  private int solveV2(int[][] prices, int budget) {

    int[][] precomputedPrices = generatePrecomputedPrices(prices);
    int biggestArea = Integer.MIN_VALUE;

    // First two loops determine the top-left corner of the rectangles.
    for (int x = 0; x < prices.length; x++) {
      for (int y = 0; y < prices[x].length; y++) {

        // The following two loops determine the width & height of the rectangles.
        for (int width = 1; width <= prices.length - x; width++) {
          for (int height = 1; height <= prices[x].length - y; height++) {

            // Compute the price of the given rectangle &
            // Determine whether to update the max area or not.
            int currentPrice = calculatePriceV2(precomputedPrices, x, y, width, height);
            if (currentPrice <= budget) {
              int currentArea = width * height;
              biggestArea = Integer.max(biggestArea, currentArea);
            }
          }
        }
      }
    }

    return biggestArea;
  }

  private int calculatePriceV2(int[][] precomputedPrices, int x, int y, int width, int height) {
    int totalPrice = precomputedPrices[x + width - 1][y + height - 1];
    if (x > 0) totalPrice -= precomputedPrices[x - 1][y + height - 1];
    if (y > 0) totalPrice -= precomputedPrices[x + width - 1][y - 1];
    if (x > 0 && y > 0) totalPrice += precomputedPrices[x - 1][y - 1];

    return totalPrice;
  }

  private int[][] generatePrecomputedPrices(int[][] prices) {
    int[][] precomputedPrices = new int[prices.length][prices[0].length];

    // Pre-compute the first row.
    precomputedPrices[0][0] = prices[0][0];
    for (int x = 1; x < precomputedPrices.length; x++) {
      precomputedPrices[x][0] = precomputedPrices[x - 1][0] + prices[x][0];
    }

    // Pre-compute the first column
    for (int y = 1; y < precomputedPrices[0].length; y++) {
      precomputedPrices[0][y] = precomputedPrices[0][y - 1] + prices[0][y];
    }

    // Pre-compute the rest.
    for (int x = 1; x < precomputedPrices.length; x++) {
      for (int y = 1; y < precomputedPrices[x].length; y++) {
        precomputedPrices[x][y] =
            precomputedPrices[x - 1][y]
                + precomputedPrices[x][y - 1]
                - precomputedPrices[x - 1][y - 1]
                + prices[x][y];
      }
    }

    return precomputedPrices;
  }

  public static void main(String[] args) {
    int[][] prices = {
      {1, 0, 0},
      {1, 0, 0},
      {1, 0, 0}
    };

    int biggestArea = new RealEstateQuestion().solve(prices, /*budget=*/ 4);
    System.out.println("Biggest Area: " + biggestArea);
  }
}
