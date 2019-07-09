// Given a 2D matrix with prices and a budget, find the biggest rectangular
// area you can purchase with the given budget.
public class RealEstateQuestion {
  public int findBiggestArea(int[][] prices, int budget) {
    int[][] precomputedSums = generatePreComputedPrices(prices);
    int biggestArea = Integer.MIN_VALUE;

    // First two loops determine the top-left corner of rectangles.
    for (int x = 0; x < prices.length; x++) {
      for (int y = 0; y < prices[x].length; y++) {

        // The latter two loops determine the width and height of the
        // rectangles.
        for (int width = 1; width <= prices.length - x; width++) {
          for (int height = 1; height <= prices[x].length - y; height++) {

            // Optimized version of calculating the rectangle using the
            // pre-computed sums.
            int currentPrice = calculatePriceFromPrecomputedSum(
                precomputedSums, x, y, width, height);
            if (currentPrice <= budget) {
              int currentArea = width * height;
              biggestArea = Integer.max(currentArea, biggestArea);
            }
          }
        }
      }
    }

    return biggestArea;
  }

  private int calculatePriceFromPrecomputedSum(
      int[][] precomputedSums, int x, int y, int width, int height) {
    int totalPrice = precomputedSums[x + width - 1][y + height - 1];
    if (x > 0) totalPrice -= precomputedSums[x - 1][y];
    if (y > 0) totalPrice -= precomputedSums[x][y - 1];
    if (x > 0 && y > 0) totalPrice += precomputedSums[x - 1][y - 1];

    return totalPrice;
  }

  private int[][] generatePreComputedPrices(int[][] prices) {
    int[][] preComputedPrices = new int[prices.length][prices[0].length];

    // Pre-compute the first row:
    preComputedPrices[0][0] = prices[0][0];
    for (int x = 1; x < preComputedPrices.length; x++) {
      preComputedPrices[x][0] = preComputedPrices[x - 1][0] + prices[x][0];
    }

    // Pre-compute the first column
    for (int y = 1; y < preComputedPrices[0].length; y++) {
      preComputedPrices[0][y] = preComputedPrices[0][y - 1] + prices[0][y];
    }

    // Pre-compute the rest
    for (int x = 1; x < preComputedPrices.length; x++) {
      for (int y = 1; y < preComputedPrices[x].length; y++) {
        preComputedPrices[x][y] =
            preComputedPrices[x - 1][y]
                + preComputedPrices[x][y - 1]
                - preComputedPrices[x - 1][y - 1]
                + prices[x][y];
      }
    }

    return preComputedPrices;
  }

  public static void main(String[] args) {
    int[][] prices = {
        {1, 0, 0},
        {1, 0, 0},
        {1, 0, 0}
    };

    int biggestArea = new RealEstateQuestion()
        .findBiggestArea(prices, /*budget=*/ 3);
    System.out.println("Biggest Area: " + biggestArea);
  }
}
