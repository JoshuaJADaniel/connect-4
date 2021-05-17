// Import statements
import java.util.List;
import java.util.ArrayList;

public class Connect4 {
  // Returns a boolean denoting whether the game is over
  public static boolean validateWin(char[][] grid, int xPos, int yPos, int w, int h) {
    if (grid[xPos][yPos] == '-') return false; // No player has played the square

    // HORIZONTAL
    int minX = xPos - 3; // Minimum pivot point 3 units left
    if (minX < 1) minX = 1; // Minimum pivot point is not on the board

    // Pivot is current x-position or left
    int maxX = Math.min(xPos, w - 3);

    // Run through all pivot points
    for (int x = minX; x <= maxX; ++x) {
      // Check 4 horizontal points belong to single player
      if (grid[x][yPos] == grid[x + 1][yPos] &&
          grid[x + 1][yPos] == grid[x + 2][yPos] &&
          grid[x + 2][yPos] == grid[x + 3][yPos]) {
        return true; // Game is over
      }
    }

    // VERTICAL
    // The block must be higher than 3 units
    if (yPos > 3) {
      // Check if 4 blocks down belong to single player
      if (grid[xPos][yPos] == grid[xPos][yPos - 1] &&
          grid[xPos][yPos - 1] == grid[xPos][yPos - 2] &&
          grid[xPos][yPos - 2] == grid[xPos][yPos - 3]) {
        return true; // Game is over
      }
    }

    // SLOPE INCREASING DIAGONAL
    int minD = -Math.min(xPos - 1, yPos - 1); // Minimum displacement right
    if (minD < -3) minD = -3; // Displacement should not be more than -3 units right

    int maxD = 0; // Maximum displacement right
    int upperD = Math.min(w - xPos, h - yPos); // Distance up and to the right
    if (upperD < 3) maxD = -(3 - upperD); // Reduce max displacement if there is not enough squares

    // Run through all pivot points
    for (int i = minD; i <= maxD; ++i) {
      // Check if all 4 blocks diagonally belong to single player
      if (grid[xPos + i][yPos + i] == grid[xPos + i + 1][yPos + i + 1] &&
          grid[xPos + i + 1][yPos + i + 1] == grid[xPos + i + 2][yPos + i + 2] &&
          grid[xPos + i + 2][yPos + i + 2] == grid[xPos + i + 3][yPos + i + 3]) {
        return true; // Game is over
      }
    }

    // SLOPE DECREASING DIAGONAL
    maxD = 0; // Maximum displacement left
    upperD = Math.min(xPos - 1, h - yPos); // Distance up and to the left
    if (upperD < 3) maxD = -(3 - upperD); // Reduce max displacement if there is not enough squares

    minD = -Math.min(w - xPos, yPos - 1); // Minimum displacement left
    if (minD < -3) minD = -3; // Shouldn't be lower than -3

    for (int i = minD; i <= maxD; ++i) { // Run through all pivot points
      // Check if all 4 blocks diagonally belong to single player
      if (grid[xPos - i][yPos + i] == grid[xPos - i - 1][yPos + i + 1] &&
          grid[xPos - i - 1][yPos + i + 1] == grid[xPos - i - 2][yPos + i + 2] &&
          grid[xPos - i - 2][yPos + i + 2] == grid[xPos - i - 3][yPos + i + 3]) {
        return true; // Game is over
      }
    }

    return false; // Game is not over
  }

  // Returns an integer denoting which column (1 indexed) to play in
  public static int compPlay(char[][] grid, char player, int w, int h) {
    int[][] moveScores = new int[w][2]; // Store scores for every move and move ID

    // Set everything to min value and set the move ID
    for (int i = 0; i < moveScores.length; ++i) {
      moveScores[i][0] = Integer.MIN_VALUE;
      moveScores[i][1] = i + 1;
    }

    for (int xPos = 1; xPos <= w; ++xPos) { // Loop over every column
      for (int yPos = 1; yPos <= h; ++yPos) { // Loop over every row
        if (grid[xPos][yPos] == '-') { // If row is empty
          moveScores[xPos - 1] = new int[] {
            evaluatePosition(grid, player, xPos, yPos, w, h),
            xPos
          }; // Evaluate the move
          break; // Get out of loop
        }
      }
    }

    // Bubble sort all moves that we just evaluated and the move ID
    for (int i = 0; i < w - 1; ++i) { // Iterate through every element up will last one
      for (int j = i; j < w - i - 1; ++j) { // Iterate again through every element that is unsorted except last one
        if (moveScores[j][0] > moveScores[j + 1][0]) { // If the move scores of the current is greater than the next one
          int temp1, temp2, temp3, temp4; // Create integers to store values in flip

          // Store information of both arrays
          temp1 = moveScores[j][0];
          temp2 = moveScores[j][1];

          temp3 = moveScores[j + 1][0];
          temp4 = moveScores[j + 1][1];

          // Assign the values, except reversed
          moveScores[j][0] = temp3;
          moveScores[j][1] = temp4;

          moveScores[j + 1][0] = temp1;
          moveScores[j + 1][1] = temp2;
        }
      }
    }

    List returnMoves = new ArrayList(); // Array list to store all high and equal moves

    for (int i = w - 1; i > 0; --i) { // Start at top of the array and work downwards
      returnMoves.add(Integer.toString(moveScores[i][1])); // Add the move ID to the list

      if (moveScores[i][0] != moveScores[i - 1][0]) { // If the next move score is not the same as the current
        break; // Break;
      }
    }

    // If moves are scored equally, choose random one
    int returnIndex = (int) (Math.random() * returnMoves.size());
    Object returnVal = returnMoves.get(returnIndex);

    return Integer.parseInt((String) returnMoves.get(returnIndex)); // Return column to play
  }

  // Returns an integer denoting how many points that certain move will achieve (the "brain" of the computer player)
  public static int evaluatePosition(char[][] grid, char player, int xPos, int yPos, int w, int h) {
    int score = 0;

    // CENTER POINTS - add points if the coin is being played in center
    if (w % 2 == 0) { // Width is even
      if (xPos == w / 2 || xPos == w / 2 + 1) {
        score += 10;
      }
    } else { // Width is not even
      if (xPos == w / 2 + 1) {
        score += 10;
      }
    }

    // HORIZONTAL POINTS
    int minX = xPos - 3; // Minimum distance to the left

    if (minX < 1) { // Minimum distance is not on the grid
      minX = 1;
    }

    int maxX = xPos;
    if (xPos > w - 3) { // Max distance will be over the grid
      maxX = w - 3;
    }

    for (int x = minX; x <= maxX; ++x) { // Iterate through all positions
      boolean nextIter = false; // Boolean indicating whether the 4 digit line is clear

      for (int i = x; i <= x + 3; ++i) { // Check if all positions are empty or our piece
        if (grid[i][yPos] != player && grid[i][yPos] != '-') { // Other player piece
          nextIter = true;
          break; // Break out of loop
        }
      }

      if (nextIter) { // The 4 digit line is not clear
        continue; // Go to next position
      }

      int usefulCharCount = 1; // Used to store the number of player pieces

      for (int i = x; i <= x + 3; ++i) {
        if (grid[i][yPos] == player) {
          ++usefulCharCount; // If we see our own piece, we increase the occurences
        }
      }

      int points = checkPoints(usefulCharCount); // Store the points based on occurences

      if (points == Integer.MAX_VALUE) { // Winning move
        return points; // Return points since it is already a winning move
      } else {
        score += points; // Add our points
      }
    }

    // VERTICAL POINTS
    int minY = yPos - 3; // Minimum distance down
    if (minY < 1) { // Minimum distance is too low
      minY = 1;
    }

    int maxY = yPos;
    if (maxY > h - 3) { // Max distance will be too high
      maxY = h - 3;
    }

    for (int y = minY; y <= maxY; ++y) { // Iterate through all positions
      boolean nextIter = false; // Boolean indicating whether the 4 digit line is clear

      for (int i = y; i <= y + 3; ++i) { // Check if all positions are empty or our piece
        if (grid[xPos][i] != player && grid[xPos][i] != '-') { // Other player piece
          nextIter = true;
          break; // Break out of loop
        }
      }

      if (nextIter) { // The 4 digit line is not clear
        continue; // Go to next position
      }

      int usefulCharCount = 1; // Used to store the number of player pieces

      for (int i = y; i <= y + 3; ++i) {
        if (grid[xPos][i] == player) {
          ++usefulCharCount; // If we see our own piece, we increase the occurences
        }
      }

      int points = checkPoints(usefulCharCount); // Store the points based on occurences

      if (points == Integer.MAX_VALUE) { // Winning move
        return points; // Return points since it is already a winning move
      } else {
        score += points; // Add our points
      }
    }

    // SLOPE INCREASING DIAGONAL
    int minD = -Math.min(xPos - 1, yPos - 1); // Distance available downward and left
    if (minD < -3) { // Max distance available should be max 3 units down
      minD = -3;
    }

    int maxD = 0;
    int upperD = Math.min(w - xPos, h - yPos); // Distance available upward and right
    if (upperD < 3) { // Max distance available should be max 3 units up
      maxD = -(3 - upperD);
    }

    for (int i = minD; i <= maxD; ++i) { // Iterate through all positions
      boolean nextIter = false; // Boolean indicating whether the 4 digit line is clear

      for (int j = i; j <= i + 3; ++j) { // Check if all positions are empty or our piece
        if (grid[xPos + j][yPos + j] != player && grid[xPos + j][yPos + j] != '-') { // Other player piece
          nextIter = true;
          break; // Break out of loop
        }
      }

      if (nextIter) { // The 4 digit line is not clear
        continue; // Go to next position
      }

      int usefulCharCount = 1; // Used to store the number of player pieces

      for (int j = i; j <= i + 3; ++j) {
        if (grid[xPos + j][yPos + j] == player) {
          ++usefulCharCount; // If we see our own piece, we increase the occurences
        }
      }

      int points = checkPoints(usefulCharCount); // Store the points based on occurences

      if (points == Integer.MAX_VALUE) { // Winning move
        return points; // Return points since it is already a winning move
      } else {
        score += points; // Add our points
      }
    }

    // SLOPE DECREASING DIAGONAL
    maxD = 0;
    upperD = Math.min(xPos - 1, h - yPos); // Distance available downward and right
    if (upperD < 3) { // Max distance available should be max 3 units up
      maxD = -(3 - upperD);
    }

    minD = -Math.min(w - xPos, yPos - 1); // Distance available upward and left
    if (minD < -3) { // Max distance available should be max 3 units down
      minD = -3;
    }

    for (int i = minD; i <= maxD; ++i) { // Iterate through all positions
      boolean nextIter = false;  // Boolean indicating whether the 4 digit line is clear

      for (int j = i; j <= i + 3; ++j) { // Check if all positions are empty or our piece
        if (grid[xPos - j][yPos + j] != player && grid[xPos - j][yPos + j] != '-') { // Other player piece
          nextIter = true;
          break; // Break out of loop
        }
      }

      if (nextIter) { // The 4 digit line is not clear
        continue; // Go to next position
      }

      int usefulCharCount = 1; // Used to store the number of player pieces

      for (int j = i; j <= i + 3; ++j) {
        if (grid[xPos - j][yPos + j] == player) {
          ++usefulCharCount; // If we see our own piece, we increase the occurences
        }
      }

      int points = checkPoints(usefulCharCount); // Store the points based on occurences

      if (points == Integer.MAX_VALUE) { // Winning move
        return points; // Return points since it is already a winning move
      } else {
        score += points; // Add our points
      }
    }

    // OTHER PLAYER WILL WIN IF NOT PLAYED
    char[][] gridClone = new char[w + 1][h + 1]; // Will store a clone of the input grid

    // Copy all values of the grid
    for (int i = 1; i <= w; i++) {
      for (int j = 1; j <= h; ++j) {
        gridClone[i][j] = grid[i][j];
      }
    }

    if (player == 'X') {
      gridClone[xPos][yPos] = 'O'; // Add the opposite player chip into the grid

      if (Connect4.validateWin(gridClone, xPos, yPos, w, h)) { // Check if it is winning for other player
        return Integer.MAX_VALUE / 2; // Highly value this move
      }
    } else {
      gridClone[xPos][yPos] = 'X'; // Add the opposite player chip into the grid

      if (Connect4.validateWin(gridClone, xPos, yPos, w, h)) { // Check if it is winning for other player
        return Integer.MAX_VALUE / 2; // Highly value this move
      }
    }

    // OTHER PLAYER WILL WIN IF PLAYED
    if (yPos != h) { // Make sure there is still space to place piece
      // Copy all values of the grid
      for (int i = 1; i <= w; i++) {
        for (int j = 1; j <= h; ++j) {
          gridClone[i][j] = grid[i][j];
        }
      }

      if (player == 'X') {
        gridClone[xPos][yPos + 1] = 'O'; // Add opposite player chip one piece ahead of our own into grid

        if (Connect4.validateWin(gridClone, xPos, yPos + 1, w, h)) { // Check if it is winning for other player
          return Integer.MIN_VALUE / 2; // Make the move unfavourable
        }
      } else {
        gridClone[xPos][yPos + 1] = 'X';// Add opposite player chip one piece ahead of our own into grid

        if (Connect4.validateWin(gridClone, xPos, yPos + 1, w, h)) { // Check if it is winning for other player
          return Integer.MIN_VALUE / 2; // Make the move unfavourable
        }
      }
    }

    return score; // Return our final evaluation of the move
  }

  // Returns integer which represents how much points are awarded in regards to the number of occurences of chips
  public static int checkPoints(int occurences) {
    // Return the points based on occurences
    if (occurences == 4) return Integer.MAX_VALUE; // This is a high number since 4 occurences indicates a four in a row
    else if (occurences == 3) return 7;
    else if (occurences == 2) return 3;
    else if (occurences == 1) return 1;
    else return -1;
  }

  // Returns boolean indicating whether the game is a draw based on the grid input
  public static boolean validateDraw(char[][] grid, int w, int h) {
    for (int x = 1; x <= w; ++x) {
      if (grid[x][h] == '-') { // If any of the sections are open, return false
        return false;
      }
    }

    return true; // No open moves
  }
}
