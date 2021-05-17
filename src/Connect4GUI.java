// Import statements
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileFilter;

public class Connect4GUI extends JFrame implements ActionListener {
  // Create extra global variables
  boolean compNextTurn = true, firstTimePlaying = true, firstTimeSaving = true;
  int h = 6, w = 7, xPos = 0, yPos = 0;
  char[][] grid = new char[w + 1][h + 1];

  // ImageIcon(s)
  ImageIcon emptyImg = new ImageIcon(".\\images\\empty.png");
  ImageIcon compImg = new ImageIcon(".\\images\\green.png");
  ImageIcon playerImg = new ImageIcon(".\\images\\red.png");
  ImageIcon applicationImg = new ImageIcon(".\\images\\applicationIcon.png");

  // JButton(s)
  JButton btnNewGame, btnLoadGame, btnReturn, btnSave, btnInstructions;
  JButton[] btnCol = new JButton[w];

  // JComboBox(s)
  JComboBox JCBfirstTurnOptions, JCBheight, JCBwidth;

  // JLabel(s)
  JLabel[][] gridGUI = new JLabel[w + 1][h + 1];
  JLabel lblTitle, lblIntimidation, lblHeight, lblWidth, lblFirstTurn, lblInstructions;

  // Container(s)
  Container pane;

  // Color(s)
  Color lightBlue = new Color(22, 41, 224), darkBlue = new Color(3, 14, 142);

  public Connect4GUI() {
    super("Connect 4"); // Title bar name

    pane = getContentPane(); // Initialize the Container
    setIconImage(applicationImg.getImage()); // Set the IconImage (image in title bar and task bar)
    createMainPageInterface(); // Create main page interface

    setVisible(true); // Show the frame
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Make program close when exit
  }

  public void createMainPageInterface() {
    // Pane(s)
    pane.removeAll(); // Remove all components from our Container
    pane.setLayout(new GridLayout(1, 1)); // 1x1 GridLayout

    // JLabel(s)
    lblTitle = new JLabel("Welcome to Connect 4", SwingConstants.CENTER); // Center text
    lblTitle.setFont(new Font("Motorwerk", Font.PLAIN, 55)); // Motorwek plain font, 50 pixels
    lblTitle.setForeground(Color.WHITE); // Set text color to white

    lblIntimidation = new JLabel("...prepare to fight against the unbeatable AI...", SwingConstants.CENTER); // Center text
    lblIntimidation.setFont(new Font("Motorwerk", Font.PLAIN, 25)); // Motorwek bold font, 25 pixels
    lblIntimidation.setForeground(Color.WHITE); // Set text color to white

    lblHeight = new JLabel("Height of Game:"); // Initialize our JLabel with text
    lblHeight.setForeground(Color.WHITE); // Set text color to white

    lblWidth = new JLabel("Width of Game:"); // Initialize our JLabel with text
    lblWidth.setForeground(Color.WHITE); // Set text color to white

    lblFirstTurn = new JLabel("First Turn:"); // Initialize our JLabel with text
    lblFirstTurn.setForeground(Color.WHITE); // Set text color to white

    // JButton(s)
    btnNewGame = new JButton("New Game"); // Initialize our JButton with text
    btnNewGame.setBackground(darkBlue); // Set the background color to our defined darkBlue Color()
    btnNewGame.setForeground(Color.WHITE); // Set text color to white
    btnNewGame.addActionListener(this); // addActionListner to button

    btnLoadGame = new JButton("Load Game"); // Initialize our JButton with text
    btnLoadGame.setBackground(darkBlue); // Set the background color to our defined darkBlue Color()
    btnLoadGame.setForeground(Color.WHITE); // Set text color to white
    btnLoadGame.addActionListener(this); // addActionListner to button

    btnInstructions = new JButton("Instructions"); // Initialize our JButton with text
    btnInstructions.setBackground(darkBlue); // Set the background color to our defined darkBlue Color()
    btnInstructions.setForeground(Color.WHITE); // Set text color to white
    btnInstructions.addActionListener(this); // addActionListner to button

    // DefaultListCellRenderer(s)
    DefaultListCellRenderer dlcr = new DefaultListCellRenderer(); // Will be used to format JComboBox cells
    dlcr.setHorizontalAlignment(DefaultListCellRenderer.CENTER); // Set the setting to center

    // JComboBox(s)
    JCBfirstTurnOptions = new JComboBox(new String[]{"Computer", "Player"}); // Options: Computer, Player
    JCBfirstTurnOptions.setBackground(darkBlue); // Set the background color to our defined darkBlue Color()
    JCBfirstTurnOptions.setForeground(Color.WHITE); // Set text color to white
    JCBfirstTurnOptions.setRenderer(dlcr);

    JCBheight = new JComboBox(new String[]{"4", "5", "6", "7", "8", "9", "10"}); // Options: 4, 5, 6, 7, 8, 9, 10
    JCBheight.setSelectedIndex(2); // Set default options as element 2: 6
    JCBheight.setBackground(darkBlue); // Set the background color to our defined darkBlue Color()
    JCBheight.setForeground(Color.WHITE); // Set text color to white
    JCBheight.setRenderer(dlcr);

    JCBwidth = new JComboBox(new String[]{"4", "5", "6", "7", "8", "9", "10"}); // Options: 4, 5, 6, 7, 8, 9, 10
    JCBwidth.setSelectedIndex(3); // Set default options as element 3: 7
    JCBwidth.setBackground(darkBlue); // Set the background color to our defined darkBlue Color()
    JCBwidth.setForeground(Color.WHITE); // Set text color to white
    JCBwidth.setRenderer(dlcr);

    // JPanel(s)
    JPanel mainPanel = new JPanel(); // New JPanel()
    mainPanel.setLayout(new GridLayout(2, 1)); // 2x1 GridLayout
    mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // 10px margin on all sides
    mainPanel.setBackground(darkBlue); // Set the background color to our defined darkBlue Color()

    JPanel titleBar = new JPanel(); // New JPanel()
    titleBar.setLayout(new GridLayout(3, 1)); // 3x1 GridLayout
    titleBar.add(new JLabel()); // Add blank JLabel()
    titleBar.add(lblTitle); // Add title
    titleBar.add(lblIntimidation); // Add intimidation
    titleBar.setBackground(lightBlue); // Set the background color to our defined lightBlue Color()

    mainPanel.add(titleBar); // Add our titles to JPanel()

    JPanel buttons = new JPanel(); // New JPanel()
    buttons.setLayout(new GridLayout(5, 1, 0, 5)); // 5x1 GridLayout with 5 pixels vertical margins
    buttons.setBorder(new EmptyBorder(10, 10, 10, 10)); // 10px margin on all sides
    buttons.setBackground(lightBlue); // Set the background color to our defined lightBlue Color()

    JPanel newGameOptions = new JPanel(); // New JPanel()
    newGameOptions.setLayout(new GridLayout(1, 3, 15, 0)); // 1x3 GridLayout 15 pixels horizontal padding
    newGameOptions.setBorder(new EmptyBorder(10, 10, 10, 10)); // 10px margin on all sides
    newGameOptions.setBackground(lightBlue); // Set the background color to our defined lightBlue Color()

    JPanel heightOptions = new JPanel(); // New JPanel()
    heightOptions.setLayout(new GridLayout(1, 2)); // 1x2 GridLayout
    heightOptions.add(lblHeight); // Add info for user
    heightOptions.add(JCBheight); // Add our JComboBox
    heightOptions.setBackground(lightBlue); // Set the background color to our defined lightBlue Color()

    JPanel widthOptions = new JPanel(); // New JPanel()
    widthOptions.setLayout(new GridLayout(1, 2)); // 1x2 GridLayout
    widthOptions.add(lblWidth); // Add info for user
    widthOptions.add(JCBwidth); // Add our JComboBox
    widthOptions.setBackground(lightBlue); // Set the background color to our defined lightBlue Color()

    JPanel firstOptions = new JPanel(); // New JPanel()
    firstOptions.setLayout(new GridLayout(1, 2)); // 1x2 GridLayout
    firstOptions.add(lblFirstTurn); // Add info for user
    firstOptions.add(JCBfirstTurnOptions); // Add our JComboBox
    firstOptions.setBackground(lightBlue); // Set the background color to our defined lightBlue Color()

    newGameOptions.add(heightOptions); // Add our component to JPanel()
    newGameOptions.add(widthOptions); // Add our component to JPanel()
    newGameOptions.add(firstOptions); // Add our component to JPanel()


    buttons.add(new JLabel("")); // Add blank JLabel()
    buttons.add(newGameOptions); // Add our component to JPanel()
    buttons.add(btnNewGame); // Add our component to JPanel()
    buttons.add(btnLoadGame); // Add our component to JPanel()
    buttons.add(btnInstructions); // Add our component to JPanel()

    mainPanel.add(buttons); // Add our buttons to JPanel()

    pane.add(mainPanel); // Add our mainPanel to the 1x1 GridLayout

    setSize(700, 600); // Set frame size for main page interface
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // Get dimensions of monitor
    setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2); // Set location of frame in center

    // Make sure everything is updated
    validate();
    repaint();
  }

  public void createInstructionInterface() {
    // Container(s)
    pane.removeAll(); // Remove all components from our Container
    pane.setLayout(new GridLayout(1, 1)); // 1x1 GridLayout

    // JPanel(s)
    JPanel mainPanel = new JPanel(); // New JPanel()
    mainPanel.setLayout(new BorderLayout(0, 5)); // New BorderLayout()
    mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // 10px margin on all sides
    mainPanel.setBackground(darkBlue); // Set the background color to our defined darkBlue Color()

    JPanel instructionPanel = new JPanel(); // New JPanel() to hold the instructions
    instructionPanel.setLayout(new GridLayout(1, 1)); // Set it to a 1x1 GridLayout
    instructionPanel.setBackground(lightBlue); // Set the background color to our defined lightBlue Color()

    // Create the instructions string formatted partially with HTML
    String instructions = "<html>";
    instructions += "<center>";
    instructions += "Connect 4 is the popular connection game where you drop pieces<br>";
    instructions += "into a standard 6x7 grid. The goal of the game is to line up 4<br>";
    instructions += "of your pieces in a row either horizontally, vertically, or<br>";
    instructions += "diagonally. Although usually played against a human, this game<br>";
    instructions += "is played against the AI computer player.<br>";
    instructions += "<br>";
    instructions += "To start a new game, go back to the main menu to select your game<br>";
    instructions += "height, width, and who gets the first turn before selecting 'New Game'.<br>";
    instructions += "<br>";
    instructions += "OR<br>";
    instructions += "<br>";
    instructions += "To load a previously saved game, go back to the main menu and select<br>";
    instructions += "the 'Load Game' button to load a stored game.<br>";
    instructions += "<br>";
    instructions += "Enjoy!";
    instructions += "</center>";
    instructions += "</html>";

    // JLabel(s)
    lblInstructions = new JLabel(instructions, SwingConstants.CENTER); // Intialize our JLabel() with the instructions and center the text
    lblInstructions.setFont(new Font("", Font.PLAIN, 18)); // Default plain font, 18px
    lblInstructions.setForeground(Color.WHITE); // Set text color to white

    // JButton(s)
    btnReturn = new JButton("Return to Main Page");
    btnReturn.setForeground(Color.WHITE); // Set text color to white
    btnReturn.setBackground(darkBlue); // Set the background color to our defined darkBlue Color()
    btnReturn.addActionListener(this); // addActionListner to JButton()

    instructionPanel.add(lblInstructions, SwingConstants.CENTER); // Add the JPanel to the center

    mainPanel.add(instructionPanel, BorderLayout.CENTER); // Add our mainPanel to the center of the BorderLayout
    mainPanel.add(btnReturn, BorderLayout.SOUTH); // Add our button to the bottom of the panel

    pane.add(mainPanel); // Add our mainPanel to the 1x1 GridLayout

    setSize(700, 600); // Set frame size for game based on options entered
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // Get dimensions of monitor
    setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2); // Set location of frame in center

    // Make sure everything is updated
    validate();
    repaint();
  }

  public void createNewGameInterface() {
    // Container(s)
    pane.removeAll(); // Remove all components from our Container
    pane.setLayout(new GridLayout(1, 1)); // 1x1 GridLayout

    // JButton(s)
    btnCol = new JButton[w]; // Initialize our JButton row

    btnReturn = new JButton("Return to Main Page"); // Initialize our JButton()
    btnReturn.setForeground(Color.WHITE); // Set text color to white
    btnReturn.setBackground(darkBlue); // Set the background color to our defined darkBlue Color()
    btnReturn.addActionListener(this); // addActionListener to JButton()

    btnSave = new JButton("Save Game"); // Initialize our JButton()
    btnSave.setForeground(Color.WHITE); // Set text color to white
    btnSave.setBackground(darkBlue); // Set the background color to our defined darkBlue Color()
    btnSave.addActionListener(this); // addActionListener to JButton()

    // JPanel(s)
    JPanel mainPanel = new JPanel(); // New JPanel()
    mainPanel.setLayout(new BorderLayout(0, 5)); // New BorderLayout()
    mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // 10px margin on all sides
    mainPanel.setBackground(darkBlue); // Set the background color to our defined darkBlue Color()

    JPanel userOptions = new JPanel(); // New JPanel()
    userOptions.setLayout(new GridLayout(1, 2, 5, 0)); // 1x2 GridLayout
    userOptions.add(btnReturn); // Add our return button to the user options
    userOptions.add(btnSave); // Add our save button to the user options
    userOptions.setBackground(darkBlue); // Set the background color to our defined darkBlue Color()

    mainPanel.add(userOptions, BorderLayout.NORTH); // Add our userOptions to top of the mainPanel

    JPanel gridPanel = new JPanel(); // New JPanel()
    gridPanel.setLayout(new GridLayout(h, w)); // hxw GridLayout to hold all the Connect 4 pieces
    gridPanel.setBorder(new EmptyBorder(10, 0, 0, 0)); // 10px padding on top
    gridPanel.setBackground(lightBlue);  // Set the background color to our defined lightBlue Color()

    // JLabel(s)
    gridGUI = new JLabel[w + 1][h + 1]; // New JLabel[][] 2 dimensional array

    // Loop over every element
    for (int y = h; y >= 1; --y) {
      for (int x = 1; x <= w; ++x) {
        gridGUI[x][y] = new JLabel(emptyImg); // Create JLabel with only emptyImg ImageIcon()

        // Populate grid with proper values/images/icons
        if (grid[x][y] == 'X') {
          gridGUI[x][y] = new JLabel(compImg);
        } else if (grid[x][y] == 'O') {
          gridGUI[x][y] = new JLabel(playerImg);
        }

        gridPanel.add(gridGUI[x][y]); // Add it to the JPanel
      }
    }

    mainPanel.add(gridPanel, BorderLayout.CENTER); // Add our gridPanel to center of the mainPanel

    // JPanel(s)
    JPanel buttonPanel = new JPanel(); // New JPanel()
    buttonPanel.setLayout(new GridLayout(1, w, 5, 5)); // 1xw GridLayout to hold user column options
    buttonPanel.setBackground(darkBlue); // Set the background color to our defined darkBlue Color()

    // JButton(s)
    for (int i = 0; i < w; ++i) {
      btnCol[i] = new JButton(Integer.toString(i + 1)); // Add the column ID to the text
      btnCol[i].setFont(new Font("Sans Serif", Font.PLAIN, 15)); // Set the font to Sans Serif plain, 15px
      btnCol[i].setForeground(Color.WHITE); // Set text color to white
      btnCol[i].setBackground(darkBlue); // Set the background color to our defined darkBlue Color()
      btnCol[i].addActionListener(this); // addActionListener to JButton()
      buttonPanel.add(btnCol[i]); // Add our button to the buttonPanel
    }

    mainPanel.add(buttonPanel, BorderLayout.SOUTH); // Add our buttonPanel to button of the mainPanel

    pane.add(mainPanel); // Add our mainPanel to the 1x1 GridLayout


    setSize(w * 100, h * 120); // Set frame size for game based on options entered
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // Get dimensions of monitor
    setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2); // Set location of frame in center

    // Make sure everything is updated
    validate();
    repaint();
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == btnNewGame) {
      // Set height
      h = Integer.parseInt((String) JCBheight.getSelectedItem());
      w = Integer.parseInt((String) JCBwidth.getSelectedItem());

      // Set first turn boolean
      if (JCBfirstTurnOptions.getSelectedItem().equals("Computer")) {
        compNextTurn = true;
      } else {
        compNextTurn = false;
      }

      // Create char grid
      grid = new char[w + 1][h + 1];

      // Initialize grid
      for (int x = 1; x <= w; ++x) {
        for (int y = 1; y <= h; ++y) {
          grid[x][y] = '-'; // '-' denotes an empty square
        }
      }

      JOptionPane.showMessageDialog(null, "Creating new game\n\nHeight: " + h + " units\nWidth: " + w + " units\nAI Turn First: " + compNextTurn); // Display message for user
      createNewGameInterface(); // Create new game interface

      // Let computer make first move if it was selected
      if (compNextTurn) {
        compMakeMove();
      }

      if (firstTimePlaying) { // First time user is playing
        JOptionPane.showMessageDialog(null, "Welcome to Connect 4\n\nTo play, please select one of the buttons at the bottom of the screen\nto drop your piece in the specified column.\n\nEnjoy!"); // Display message for user
        firstTimePlaying = false; // Make sure the message isn't seen again
      }

      return; // Exit the function
    } else if (e.getSource() == btnInstructions) {
      JOptionPane.showMessageDialog(null, "Entering Instructions Page"); // Display message for user
      createInstructionInterface(); // Call the function to display the instructions interface

      return; // Exit the function
    } else if (e.getSource() == btnSave) {
      if (firstTimeSaving) {
        JOptionPane.showMessageDialog(null, "To save, either select an already created text file\n\nOR\n\nEnter a file name to save as before selecting 'Open'"); // Display message for user
        firstTimeSaving = false;
      }

      String userDirLocation = System.getProperty("user.dir"); // Gets the program execution location as a string
      File userDir = new File(userDirLocation); // Create a file using the file location

      JFileChooser fc = new JFileChooser(userDir); // New file chooser
      fc.setAcceptAllFileFilterUsed(false); // Make user only use specified filter
      fc.setFileFilter(new TextFileFilter()); // Make sure user only uses .txt files

      int returnVal = fc.showOpenDialog(this); // Open the JFileChooser

      if (returnVal == JFileChooser.APPROVE_OPTION) { // If they have selected a file
        String writeFileLocation = fc.getSelectedFile().toString(); // Convert selection to string

        // Make sure the selection ends with .txt
        if (writeFileLocation.length() <= 4 || !writeFileLocation.substring(writeFileLocation.length() - 4).equals(".txt")) {
          writeFileLocation += ".txt"; // If not, we will add it ourselves
        }

        try {
          PrintWriter fw = new PrintWriter(new FileWriter(writeFileLocation)); // Create PrintWriter to write to file user entered

          fw.println(w); // Add game width
          fw.println(h); // Add game height

          // Write the grid to the file
          for (int y = h; y >= 1; --y) {
            for (int x = 1; x <= w; ++x) {
              fw.print(grid[x][y]);
            }
            fw.println();
          }

          fw.close(); // Close the file
        } catch (Exception all) {
          JOptionPane.showMessageDialog(null, "Unexpected error while writing to file", "Error", JOptionPane.ERROR_MESSAGE); // Display error message

          return; // Exit the function
        }
      } else {
        return; // Exit the function since they did not approve the option
      }

      JOptionPane.showMessageDialog(null, "File successfully written"); // Display success message to user

      return; // Exit the function
    } else if (e.getSource() == btnReturn) {
      JOptionPane.showMessageDialog(null, "Returning to Main Page"); // Display message for user
      createMainPageInterface(); // Get back to main page

      return; // Exit the function
    } else if (e.getSource() == btnLoadGame) {
      String userDirLocation = System.getProperty("user.dir"); // Gets the program execution location as a string
      File userDir = new File(userDirLocation); // Create a file using the file location

      JFileChooser fc = new JFileChooser(userDir); // New file chooser
      fc.setAcceptAllFileFilterUsed(false); // Make user only use specified filter
      fc.setFileFilter(new TextFileFilter()); // Make sure user only uses .txt files

      int returnVal = fc.showOpenDialog(this); // Open the JFileChooser

      if (returnVal == JFileChooser.APPROVE_OPTION) { // If they have selected a file
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); // Let user select both files and directories
        String file = fc.getSelectedFile().toString(); // Get the selected file
        try {
          BufferedReader fr = new BufferedReader(new FileReader(file)); // Open their file

          // Read needed values
          w = Integer.parseInt(fr.readLine());
          h = Integer.parseInt(fr.readLine());

          grid = new char[w + 1][h + 1]; // Intialize our grid to store the valuse

          compNextTurn = false; // Make sure the next turn is for the player

          // Read the values into our array
          for (int y = h; y >= 1; --y) {
            String line = fr.readLine();

            for (int x = 1; x <= w; ++x) {
              grid[x][y] = line.charAt(x - 1);
            }
          }

          fr.close(); // Close the file

          JOptionPane.showMessageDialog(null, "Loading stored game\n\nHeight: " + h + "\nWidth: " + w + "\nAI Turn First: " + compNextTurn); // Display message for user
          createNewGameInterface(); // Create the game

          if (firstTimePlaying) { // First time user is playing
            JOptionPane.showMessageDialog(null, "Welcome to Connect 4\n\nTo play, please select one of the buttons at the bottom of the screen\nto drop your piece in the specified column.\n\nEnjoy!"); // Display message for user
            firstTimePlaying = false; // Make sure the message isn't seen again
          }
        } catch (Exception all) {
          JOptionPane.showMessageDialog(null, "An error occured reading your data file", "Error", JOptionPane.ERROR_MESSAGE);

          return; // Exit the function
        }
      }

      return; // Exit the function
    }

    // Stores which column was selected
    int xIn = -1;

    // Linear search for the e.getSource() in JButton[] btnCol
    for (int i = 0; i < w; ++i) { // Iterate over every possible btnCol JButton
      if (e.getSource() == btnCol[i]) { // If the e.getSource() matches any button
        xIn = i + 1; // Store the index of the button
        break; // Break out of the loop since we have found the JButton()
      }
    }

    // Negative one indicates that no column button was clicked
    if (xIn != -1) {
      // Check if the column is full
      if (grid[xIn][h] != '-') {
        JOptionPane.showMessageDialog(null, "Select a valid column"); // Let user know to click a valid column

        return; // Exit the function
      }

      // Place 'O' in proper position denoting the player
      for (int y = 1; y <= h; ++y) {
        if (grid[xIn][y] == '-') {
          grid[xIn][y] = 'O';
          xPos = xIn;
          yPos = y;
          break;
        }
      }

      // Make all buttons unclickable
      for (int i = 0; i < w; ++i) {
        btnCol[i].setEnabled(false);
      }

      btnReturn.setEnabled(false);
      btnSave.setEnabled(false);

      // Define timer
      Timer animate = new Timer(200, new ActionListener() { // Run every 200 milliseconds
        int timerY = h; // Variable specific to timer

        public void actionPerformed(ActionEvent evt) {
          if (timerY == h) { // Just started the animation
            gridGUI[xPos][timerY].setIcon(playerImg); // Set the current hole to the player img
          } else if (timerY >= yPos) { // Already started the timer
            gridGUI[xPos][timerY + 1].setIcon(emptyImg); // Set the hole above to empty
            gridGUI[xPos][timerY].setIcon(playerImg); // Set the current hole to the player img
          }

          if (timerY == yPos) { // We have reached the final position
            ((Timer) evt.getSource()).stop(); // Convert our ActionEvent to a timer and stop it

            // Make all buttons clickable again
            for (int i = 0; i < w; ++i) {
              btnCol[i].setEnabled(true);
            }

            btnReturn.setEnabled(true);
            btnSave.setEnabled(true);

            // Check if player wins
            if (Connect4.validateWin(grid, xPos, yPos, w, h)) {
              JOptionPane.showMessageDialog(null, "Player wins!"); // Display message for user
              JOptionPane.showMessageDialog(null, "Returning to Main Page"); // Display message for user
              createMainPageInterface(); // Return to main screen
              return; // Exit the function
            }

            // Check if its a draw
            if (Connect4.validateDraw(grid, w, h)) {
              JOptionPane.showMessageDialog(null, "It's a draw!"); // Display message for user
              JOptionPane.showMessageDialog(null, "Returning to Main Page"); // Display message for user
              createMainPageInterface(); // Return to main screen
              return; // Exit the function
            }

            // Otherwise, let computer play
            compMakeMove();
          }

          --timerY; // Drop one position below
        }
      });

      // Start timer
      animate.start();
    }
  }

  public void compMakeMove() {
    // Get the initial computer decision
    int compDecision = Connect4.compPlay(grid, 'X', w, h);

    // Determine which square is empty in column computer selected
    for (int y = 1; y <= h; ++y) {
      if (grid[compDecision][y] == '-') {
        grid[compDecision][y] = 'X';
        xPos = compDecision;
        yPos = y;
        break;
      }
    }

    // Make all buttons regular font and unclickable
    for (int i = 0; i < w; ++i) {
      btnCol[i].setFont(new Font("Sans Serif", Font.PLAIN, 15));
      btnCol[i].setEnabled(false);
    }

    btnReturn.setEnabled(false);
    btnSave.setEnabled(false);

    // Bold computer's decision
    btnCol[compDecision - 1].setFont(new Font("Sans Serif", Font.BOLD, 20));

    Timer animate = new Timer(200, new ActionListener() {  // Run every 200 milliseconds
      int timerY = h;

      public void actionPerformed(ActionEvent evt) {
        if (timerY == h) { // Just started the animation
          gridGUI[xPos][timerY].setIcon(compImg); // Set the current hole to the computer img
        } else if (timerY >= yPos) { // Already started the timer
          gridGUI[xPos][timerY + 1].setIcon(emptyImg); // Set the hole above to the player img
          gridGUI[xPos][timerY].setIcon(compImg); // Set the current hole to the computer img
        }

        if (timerY == yPos) { // We have reached the final position
          ((Timer) evt.getSource()).stop(); // Convert our ActionEvent to a timer and stop it

          // Make all buttons clickable
          for (int i = 0; i < w; ++i) {
            btnCol[i].setEnabled(true);
          }

          btnReturn.setEnabled(true);
          btnSave.setEnabled(true);

          // Check if computer wins
          if (Connect4.validateWin(grid, xPos, yPos, w, h)) {
            JOptionPane.showMessageDialog(null, "Computer wins!"); // Display message for user
            JOptionPane.showMessageDialog(null, "Returning to Main Page"); // Display message for user
            createMainPageInterface(); // Return to main screen
            return; // Exit the function
          }

          // Check if its a draw
          if (Connect4.validateDraw(grid, w, h)) {
            JOptionPane.showMessageDialog(null, "It's a draw!"); // Display message for user
            JOptionPane.showMessageDialog(null, "Returning to Main Page"); // Display message for user
            createMainPageInterface(); // Return to main screen
            return; // Exit the function
          }
        }

        --timerY; // Drop one position below
      }
    });

    // Start timer
    animate.start();
  }

  public static void main(String[] args) { // Main program
    new Connect4GUI(); // Create our interface
  }
}

class TextFileFilter extends FileFilter { // Create a new class which extends FileFilter (from javax.swing.filechooser) to be used along with JFileChooser
  public boolean accept(File f) { // Required function to determine whether the File, named fileInput in this case will be accepted which is denoted by a boolean
    if (f.getName().toLowerCase().endsWith(".txt")) { // Check if the extension is .txt
      return true; // Return true since we accept the file
    }

    return false; // Return false since we do not accept the file
  }

  public String getDescription() {
    return "Text Files"; // Return what our label will say in the JFileChooser
  }
}
