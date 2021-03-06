
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Snake {    

    // GUI components
    private JPanel board;
    private JButton[] snakeBodyPart;
    private JButton bonusfood;
    private JTextArea scoreViewer;
    private int score_pluser=5;

    // Constants
    private final int SNAKE_RUNNING_SPEED_FASTEST = 25;
    private final int SNAKE_RUNNING_SPEED_FASTER = 50;
    private final int SNAKE_RUNNING_SPEED_FAST = 100;
    private final int SNAKE_RUNNING_SPEED_NORMAL = 120;
    private final int BOARD_WIDTH = 500;
    private final int BOARD_HEIGHT = 250;
    private final int SCORE_BOARD_HEIGHT = 20;
    private final int SNAKE_LENGTH_DEFAULT = 4;
    private final int SNAKE_BODY_PART_SQURE = 10;   
    private final int BONUS_FOOD_SQURE = 15;
    private final Point INIT_POINT = new Point(100, 150);    

    // Others values
    private enum GAME_TYPE {

        NO_MAZE, BORDER, TUNNEL
    };
    private int selectedSpeed = SNAKE_RUNNING_SPEED_FASTER;
    private GAME_TYPE selectedGameType = GAME_TYPE.NO_MAZE;
    private int totalBodyPart;
    private int directionX;
    private int directionY;
    private int score;
    private Point pointOfBonusFood = new Point();
    private boolean isRunningLeft;
    private boolean isRunningRight;
    private boolean isRunningUp;
    private boolean isRunningDown;
    private boolean isBonusFoodAvailable;
    private boolean isRunning;
    private Random random = new Random();

    Snake() {
        //initialize all variables.
        resetDefaultValues();
        // initialize GUI.
        init();
        // Create Initial body of a snake.
        createInitSnake(Color.BLACK);
        // Initialize Thread.
        isRunning = true;
        createThread();
    }

    public void init() {
        JFrame frame = new JFrame("Snake");
        frame.setSize(500, 330);

        //Create Menue bar with functions
        setJMenueBar(frame);
        // Start of UI design
        JPanel scorePanel = new JPanel();
        scoreViewer = new JTextArea("Score ==>" + score);
        scoreViewer.setEnabled(false);
        scoreViewer.setBackground(Color.BLACK);

        board = new JPanel();
        board.setLayout(null);
        board.setBounds(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        board.setBackground(Color.WHITE);
        scorePanel.setLayout(new GridLayout(0, 1));
        scorePanel.setBounds(0, BOARD_HEIGHT, BOARD_WIDTH, SCORE_BOARD_HEIGHT);
        scorePanel.setBackground(Color.RED);
        scorePanel.add(scoreViewer); // will contain score board

        frame.getContentPane().setLayout(null);
        frame.getContentPane().add(board);
        frame.getContentPane().add(scorePanel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                snakeKeyPressed(e);
            }
        });
        frame.setResizable(false);
    }

    public void setJMenueBar(JFrame frame) {

        JMenuBar mymbar = new JMenuBar();

        JMenu game = new JMenu("Game");
        JMenuItem newgame = new JMenuItem("New Game");
        JMenuItem exit = new JMenuItem("Exit");
        newgame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        game.add(newgame);
        game.addSeparator();
        game.add(exit);
        mymbar.add(game);

        JMenu type = new JMenu("Type");
        JMenuItem noMaze = new JMenuItem("No Maze");
        noMaze.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedGameType = GAME_TYPE.NO_MAZE;
                startNewGame();
            }
        });
        JMenuItem border = new JMenuItem("Border Maze");
        border.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedGameType = GAME_TYPE.BORDER;
                startNewGame();
            }
        });
        type.add(noMaze);
        type.add(border);
        mymbar.add(type);

        JMenu level = new JMenu("Level");
        JMenuItem beginner = new JMenuItem("Beginner");
        beginner.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedSpeed = SNAKE_RUNNING_SPEED_NORMAL;
                score_pluser = 3;
                startNewGame();
            }
        });
        JMenuItem normal = new JMenuItem("Normal");
        normal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedSpeed = SNAKE_RUNNING_SPEED_FAST;
                score_pluser = 5;
                startNewGame();
            }
        });
        JMenuItem expert = new JMenuItem("Expert");
        expert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedSpeed = SNAKE_RUNNING_SPEED_FASTER;
                score_pluser = 7;
                startNewGame();
            }
        });
        JMenuItem excellent = new JMenuItem("Excellent");
        excellent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedSpeed = SNAKE_RUNNING_SPEED_FASTEST;
                score_pluser = 9;
                startNewGame();
            }
        });
        level.add(beginner);
        level.add(normal);
        level.add(expert);
        level.add(excellent);
        mymbar.add(level);

        JMenu color = new JMenu("Color");

        JMenuItem white = new JMenuItem("White");
        white.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame2(Color.WHITE);
            }
        });
        
        JMenuItem red = new JMenuItem("Red");
        red.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame2(Color.RED);
            }
        });
        
        JMenuItem blue = new JMenuItem("Blue");
        blue.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startNewGame2(Color.BLUE);
            }
        });
        JMenuItem pink = new JMenuItem("Pink");
        pink.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startNewGame2(Color.PINK);
            }
        });
        
        JMenuItem yellow = new JMenuItem("Yellow");
        yellow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startNewGame2(Color.YELLOW);
            }
        });
        
        JMenuItem orange = new JMenuItem("Orange");
        orange.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startNewGame2(Color.ORANGE);
            }
        });
        
        JMenuItem green = new JMenuItem("Green");
        green.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startNewGame2(Color.GREEN);
            }
        });
        
        JMenuItem def = new JMenuItem("Default");
        def.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startNewGame2(Color.BLACK);
            }
        });
        
        color.add(white);
        color.add(red);
        color.add(blue);
        color.add(pink);
        color.add(yellow);
        color.add(orange);
        color.add(green);
        color.add(def);
        mymbar.add(color);
        
        JMenu bg = new JMenu("Background");

        JMenuItem blackBG = new JMenuItem("Black");
        blackBG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.setBackground(Color.BLACK);
            }
        });
        JMenuItem blueBG = new JMenuItem("Blue");
        blueBG.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.setBackground(Color.BLUE);
            }
        });
        JMenuItem cyanBG = new JMenuItem("Cyan");
        cyanBG.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.setBackground(Color.CYAN);
            }
        });
        
        JMenuItem greyBG = new JMenuItem("Grey");
        greyBG.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.setBackground(Color.GRAY);
            }
        });
        
        JMenuItem redBG = new JMenuItem("Red");
        redBG.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.setBackground(Color.RED);
            }
        });
        
        JMenuItem orangeBG = new JMenuItem("Orange");
        orangeBG.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.setBackground(Color.ORANGE);
            }
        });
        
        JMenuItem pinkBG = new JMenuItem("Pink");
        pinkBG.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.setBackground(Color.PINK);
            }
        });
        
        JMenuItem greenBG = new JMenuItem("Green");
        greenBG.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.setBackground(Color.GREEN);
            }
        });
        
        JMenuItem yellowBG = new JMenuItem("Yellow");
        yellowBG.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.setBackground(Color.YELLOW);
            }
        });
        
        JMenuItem whiteBG = new JMenuItem("White");
        whiteBG.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.setBackground(Color.WHITE);
            }
        });

        bg.add(blackBG);
        bg.add(blueBG);
        bg.add(cyanBG);
        bg.add(greyBG);
        bg.add(redBG);
        bg.add(orangeBG);
        bg.add(pinkBG);
        bg.add(greenBG);
        bg.add(yellowBG);
        bg.add(whiteBG);       
        mymbar.add(bg);

        JMenu help = new JMenu("Help");
        JMenuItem creator = new JMenuItem("Creator");
        JMenuItem instruction = new JMenuItem("Instraction");
        creator.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Author: Abdullah al hasb\nVersion: 1.0.1 \n Blog: http://imhasib.wordpress.com/");
            }
        });

        help.add(creator);
        help.add(instruction);
        mymbar.add(help);

        frame.setJMenuBar(mymbar);
    }

    public void resetDefaultValues() {
        snakeBodyPart = new JButton[2000];
        totalBodyPart = SNAKE_LENGTH_DEFAULT;
        directionX = SNAKE_BODY_PART_SQURE;
        directionY = 0;
        score = 0;        
        
        isRunningLeft = false;
        isRunningRight = true;
        isRunningUp = true;
        isRunningDown = true;
        isBonusFoodAvailable = false;
    }

    void startNewGame() {
        resetDefaultValues();
        board.removeAll();
        createInitSnake(Color.BLACK);
        scoreViewer.setText("Score==>" + score);
        isRunning = true;
    }

    void startNewGame2(Color c) {
        resetDefaultValues();
        board.removeAll();
        createInitSnake(c);
        scoreViewer.setText("Score==>" + score);
        isRunning = true;
    }

    /**
     * This method is responsible to initialize the snake with four body part.
     */
    public void createInitSnake(Color c) {
        // Location of the snake's head.
        int x = (int) INIT_POINT.getX();
        int y = (int) INIT_POINT.getY();

        // Initially the snake has three body part.
        for (int i = 0; i < totalBodyPart; i++) {
            snakeBodyPart[i] = new JButton();
            snakeBodyPart[i].setBounds(x, y, SNAKE_BODY_PART_SQURE, SNAKE_BODY_PART_SQURE);                        
            snakeBodyPart[i].setBackground(c);
            board.add(snakeBodyPart[i]);
            // Set location of the next body part of the snake.
            x = x - SNAKE_BODY_PART_SQURE;
        }

        // Create food.
        createFood();
    }

    /**
     * This method is responsible to create food of a snake. The most last part
     * of this snake is treated as a food, which has not become a body part of
     * the snake yet. This food will be the body part if and only if when snake
     * head will touch it.
     */
    void createFood() {
        int randomX = SNAKE_BODY_PART_SQURE + (SNAKE_BODY_PART_SQURE * random.nextInt(48));
        int randomY = SNAKE_BODY_PART_SQURE + (SNAKE_BODY_PART_SQURE * random.nextInt(23));

        snakeBodyPart[totalBodyPart] = new JButton();
        snakeBodyPart[totalBodyPart].setEnabled(false);
        snakeBodyPart[totalBodyPart].setBounds(randomX, randomY, SNAKE_BODY_PART_SQURE, SNAKE_BODY_PART_SQURE);
        Random random = new Random();
        int red = random.nextInt(255);
        int green = random.nextInt(255);
        int blue = random.nextInt(255);
        Color randomColor = new Color(red,green,blue);
        snakeBodyPart[totalBodyPart].setBackground(randomColor);
        board.add(snakeBodyPart[totalBodyPart]);

        totalBodyPart++;
    }

    private void createBonusFood() {
        bonusfood = new JButton();
        bonusfood.setEnabled(false);
        //Set location of the bonus food.
        int bonusFoodLocX = SNAKE_BODY_PART_SQURE * random.nextInt(50);
        int bonusFoodLocY = SNAKE_BODY_PART_SQURE * random.nextInt(25);

        bonusfood.setBounds(bonusFoodLocX, bonusFoodLocY, BONUS_FOOD_SQURE, BONUS_FOOD_SQURE);
        pointOfBonusFood = bonusfood.getLocation();
        board.add(bonusfood);
        isBonusFoodAvailable = true;
    }

    /**
     * Process next step of the snake. And decide what should be done.
     */
    void processNextStep() {
        boolean isBorderTouched = false;
        // Generate new location of snake head.
        int newHeadLocX = (int) snakeBodyPart[0].getLocation().getX() + directionX;
        int newHeadLocY = (int) snakeBodyPart[0].getLocation().getY() + directionY;

        // Most last part of the snake is food.
        int foodLocX = (int) snakeBodyPart[totalBodyPart - 1].getLocation().getX();
        int foodLocY = (int) snakeBodyPart[totalBodyPart - 1].getLocation().getY();

        // Check does snake cross the border of the board?
        if (newHeadLocX > BOARD_WIDTH - SNAKE_BODY_PART_SQURE) {
            newHeadLocX = 0;
            isBorderTouched = true;
        } else if (newHeadLocX <= 0) {
            newHeadLocX = BOARD_WIDTH - SNAKE_BODY_PART_SQURE;
            isBorderTouched = true;
        } else if (newHeadLocY > BOARD_HEIGHT - SNAKE_BODY_PART_SQURE) {
            newHeadLocY = 0;
            isBorderTouched = true;
        } else if (newHeadLocY <= 0) {
            newHeadLocY = BOARD_HEIGHT - SNAKE_BODY_PART_SQURE;
            isBorderTouched = true;
        }

        // Check has snake touched the food?
        if (newHeadLocX == foodLocX && newHeadLocY == foodLocY) {
            // Set score.
            score += this.score_pluser;
            scoreViewer.setText("Score==>" + score);

            // Check bonus food should be given or not?
            if (score % 50 == 0 && !isBonusFoodAvailable) {
                createBonusFood();
            }
            // Create new food.
            createFood();
        }

        // Check has snake touched the bonus food?
        if (isBonusFoodAvailable
                && pointOfBonusFood.x <= newHeadLocX
                && pointOfBonusFood.y <= newHeadLocY
                && (pointOfBonusFood.x + SNAKE_BODY_PART_SQURE) >= newHeadLocX
                && (pointOfBonusFood.y + SNAKE_BODY_PART_SQURE) >= newHeadLocY) {
            board.remove(bonusfood);
            score += 100;
            scoreViewer.setText("Score ==>" + score);
            isBonusFoodAvailable = false;
        }

        // Check is game over?
        if (isGameOver(isBorderTouched, newHeadLocX, newHeadLocY)) {
            scoreViewer.setText("GAME OVER	" + score);
            isRunning = false;
            return;
        } else {
            // Move the whole snake body to forword.
            moveSnakeForword(newHeadLocX, newHeadLocY);
        }

        board.repaint();
    }

    /**
     * This method is responsible to detect is game over or not? Game should be
     * over while snake is touched by any maze or by itself. If any one want to
     * add new type just declare new GAME_TYPE enum value and put logic in this
     * method.
     *
     * @param isBorderTouched
     * @param headLocX
     * @param headLocY
     * @return
     */
    private boolean isGameOver(boolean isBorderTouched, int headLocX, int headLocY) {
        switch (selectedGameType) {
            case BORDER:
                if (isBorderTouched) {
                    return true;
                }
                break;
            case TUNNEL:
                // TODO put logic here...
                throw new UnsupportedOperationException();
            default:
                break;
        }

        for (int i = SNAKE_LENGTH_DEFAULT; i < totalBodyPart - 2; i++) {
            Point partLoc = snakeBodyPart[i].getLocation();
            System.out.println("(" + partLoc.x + ", " + partLoc.y + ")  (" + headLocX + ", " + headLocY + ")");
            if (partLoc.equals(new Point(headLocX, headLocY))) {
                return true;
            }
        }

        return false;
    }

    /**
     * Every body part should be placed to location of the front part. For
     * example if part:0(100,150) , part: 1(90, 150), part:2(80,150) and new
     * head location (110,150) then, Location of part:2 should be (80,150) to
     * (90,150), part:1 will be (90,150) to (100,150) and part:3 will be
     * (100,150) to (110,150) This movement process should be start from the
     * last part to first part. We must avoid the food that means most last body
     * part of the snake. Notice that we write (totalBodyPart - 2) instead of
     * (totalBodyPart - 1). (totalBodyPart - 1) means food and (totalBodyPart -
     * 2) means tail.
     *
     * @param headLocX
     * @param headLocY
     */
    public void moveSnakeForword(int headLocX, int headLocY) {
        for (int i = totalBodyPart - 2; i > 0; i--) {
            Point frontBodyPartPoint = snakeBodyPart[i - 1].getLocation();
            snakeBodyPart[i].setLocation(frontBodyPartPoint);
        }
        snakeBodyPart[0].setBounds(headLocX, headLocY, SNAKE_BODY_PART_SQURE, SNAKE_BODY_PART_SQURE);
    }

    public void snakeKeyPressed(KeyEvent e) {
        // snake should move to left when player pressed left arrow
        if (isRunningLeft == true && e.getKeyCode() == 37) {
            directionX = -SNAKE_BODY_PART_SQURE; // means snake move right to left by 10 pixel
            directionY = 0;
            isRunningRight = false;     // means snake cant move from left to right
            isRunningUp = true;         // means snake can move from down to up
            isRunningDown = true;       // means snake can move from up to down
        }
        // snake should move to up when player pressed up arrow
        if (isRunningUp == true && e.getKeyCode() == 38) {
            directionX = 0;
            directionY = -SNAKE_BODY_PART_SQURE; // means snake move from down to up by 10 pixel
            isRunningDown = false;     // means snake can move from up to down
            isRunningRight = true;     // means snake can move from left to right
            isRunningLeft = true;      // means snake can move from right to left
        }
        // snake should move to right when player pressed right arrow
        if (isRunningRight == true && e.getKeyCode() == 39) {
            directionX = +SNAKE_BODY_PART_SQURE; // means snake move from left to right by 10 pixel
            directionY = 0;
            isRunningLeft = false;
            isRunningUp = true;
            isRunningDown = true;
        }
        // snake should move to down when player pressed down arrow
        if (isRunningDown == true && e.getKeyCode() == 40) {
            directionX = 0;
            directionY = +SNAKE_BODY_PART_SQURE; // means snake move from left to right by 10 pixel
            isRunningUp = false;
            isRunningRight = true;
            isRunningLeft = true;
        }
    }

    private void createThread() {
        // start thread
        Thread thread = new Thread(new Runnable() {

            public void run() {
                runIt();
            }
        });
        thread.start(); // go to runIt() method
    }

    public void runIt() {
        while (true) {
            if (isRunning) {
                // Process what should be next step of the snake.
                processNextStep();
                try {
                    Thread.sleep(selectedSpeed);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        }
    }
}
