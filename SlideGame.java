import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created on 11/7/14.
 */
public class SlideGame {
	private int[][] array;
	private JButton[][] buttonArray;

    // Creates the Frame and Panel and fills with buttons
	private SlideGame(int rows, int columns) {
		array = new int[rows][columns];
		buttonArray = new JButton[rows][columns];
        JPanel board = new JPanel(new GridLayout(rows, columns));

        for(int row = 0; row < array.length; row++) {
            for (int column = 0; column < array[0].length; column++) {
                buttonArray[row][column] = new JButton();
				board.add(buttonArray[row][column]);
			}
		}

        addListeners();
        generateNew();
        refreshButtons();

		JFrame frame = new JFrame("SlideGame");
		frame.getContentPane().add(board, BorderLayout.CENTER);
		frame.pack();
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

    // Iterates over array and move non zero numbers to left, adding if equal
    private boolean slideLeft(int[][] array, int row) {
        int lastPos = -1;
        boolean slid = false;
        for(int i = 0; i < array[row].length; i++) {
            if (array[row][i] != 0) {
                if (lastPos >= 0 && array[row][lastPos] == array[row][i]) {
                    array[row][lastPos] += array[row][i];
                    array[row][i] = 0;
                    slid = true;
                } else if (++lastPos != i) {
                    array[row][lastPos] = array[row][i];
                    array[row][i] = 0;
                    slid = true;
                }
            }
        }
        return slid;
    }

    // Same thing but right
    private boolean slideRight(int[][] array, int row) {
        int lastPos = array[0].length;
        boolean slid = false;
        for(int i = lastPos-1; i >= 0; i--) {
            if (array[row][i] != 0) {
                if (lastPos < array[0].length && array[row][lastPos] == array[row][i]) {
                    array[row][lastPos] += array[row][i];
                    array[row][i] = 0;
                    slid = true;
                } else if (--lastPos != i) {
                    array[row][lastPos] = array[row][i];
                    array[row][i] = 0;
                    slid = true;
                }
            }
        }
        return slid;
    }

    // same but up
    private boolean slideUp(int[][] array, int column) {
        int lastPos = -1;
        boolean slid = false;
        for(int i = 0; i < array.length; i++) {
            if (array[i][column] != 0) {
                if (lastPos >= 0 && array[lastPos][column] == array[i][column]) {
                    array[lastPos][column] += array[i][column];
                    array[i][column] = 0;
                    slid = true;
                } else if (++lastPos != i) {
                    array[lastPos][column] = array[i][column];
                    array[i][column] = 0;
                    slid = true;
                }
            }
        }
        return slid;
    }

    //down
    private boolean slideDown(int[][] array, int column) {
        int lastPos = array[0].length;
        boolean slid = false;
        for(int i = lastPos-1; i >= 0; i--) {
            if (array[i][column] != 0) {
                if (lastPos < array[0].length && array[lastPos][column] == array[i][column]) {
                    array[lastPos][column] += array[i][column];
                    array[i][column] = 0;
                    slid = true;
                } else if (--lastPos != i) {
                    array[lastPos][column] = array[i][column];
                    array[i][column] = 0;
                    slid = true;
                }
            }
        }
        return slid;
    }

    // iterates diagonally
    private boolean slideUpLeft(int[][] array, int row, int column) {
        boolean slid = false;
        int lastRow = row-1;
        int lastColumn = column-1;
        for(int r = row, c = column; r < array.length && c < array[0].length; r++, c++){
            if (array[r][c] != 0) {
                if (lastRow >= 0 && lastColumn >= 0 && array[lastRow][lastColumn] == array[r][c]) {
                    array[lastRow][lastColumn] += array[r][c];
                    array[r][c] = 0;
                    slid = true;
                } else {
                    lastColumn++;
                    lastRow++;
                    if (lastRow != r) {
                        array[lastRow][lastColumn] = array[r][c];
                        array[r][c] = 0;
                        slid = true;
                    }
                }
            }
        }

        return slid;
    }

    // diagonally down right
    private boolean slideDownRight(int[][] array, int row, int column) {
        boolean slid = false;
        int lastRow = array.length - column;
        int lastColumn = array[0].length - row;
        for(int r = lastRow - 1, c = lastColumn - 1; r >= 0 && c >= 0; r--, c--){
            if (array[r][c] != 0) {
                if (lastRow < array.length && lastColumn < array[0].length
                        && array[lastRow][lastColumn] == array[r][c]) {
                    array[lastRow][lastColumn] += array[r][c];
                    array[r][c] = 0;
                    slid = true;
                } else {
                    lastColumn--;
                    lastRow--;
                    if (lastRow != r) {
                        array[lastRow][lastColumn] = array[r][c];
                        array[r][c] = 0;
                        slid = true;
                    }
                }
            }
        }

        return slid;
    }

    // same but up right
    private boolean slideUpRight(int[][] array, int row, int column) {
        boolean slid = false;
        int lastRow = row-1;
        int lastColumn = column + 1;
        for(int r = lastRow + 1, c = lastColumn - 1; r < array.length && c >= 0; r++, c--){
            if (array[r][c] != 0) {
                if (lastRow >= 0 && lastColumn < array[0].length
                        && array[lastRow][lastColumn] == array[r][c]) {
                    array[lastRow][lastColumn] += array[r][c];
                    array[r][c] = 0;
                    slid = true;
                } else {
                    lastColumn--;
                    lastRow++;
                    if (lastRow != r) {
                        array[lastRow][lastColumn] = array[r][c];
                        array[r][c] = 0;
                        slid = true;
                    }
                }
            }
        }

        return slid;
    }

    // down left
    private boolean slideDownLeft(int[][] array, int row, int column) {
        boolean slid = false;
        int lastRow = row + 1;
        int lastColumn = column - 1;
        for(int r = lastRow - 1, c = lastColumn + 1; r >= 0 && c < array[0].length; r--, c++){
            if (array[r][c] != 0) {
                if (lastRow < array.length && lastColumn >= 0
                        && array[lastRow][lastColumn] == array[r][c]) {
                    array[lastRow][lastColumn] += array[r][c];
                    array[r][c] = 0;
                    slid = true;
                } else {
                    lastColumn++;
                    lastRow--;
                    if (lastRow != r) {
                        array[lastRow][lastColumn] = array[r][c];
                        array[r][c] = 0;
                        slid = true;
                    }
                }
            }
        }

        return slid;
    }

    // reads the int array and update button labels
    private void refreshButtons() {
        for(int row = 0; row < array.length; row++) {
            for (int column = 0; column < array[0].length; column++) {
                if(array[row][column] == 0) {
                    buttonArray[row][column].setText("");
                } else {
                    buttonArray[row][column].setText(Integer.toString(array[row][column]));
                }
            }
        }
    }

    // create a new 1 in a empty spot
    private void generateNew() {
        //count empty positions
        int count = 0;
        for(int[] integers : array) {
            for(int i : integers) {
                if(i == 0)
                    count++;
            }
        }

        //generate random position for new number
        int newPos = (int)(Math.random() * count);
        System.out.println(newPos);

        //iterate over array and places the new int
        count = -1;
        for(int row = 0; row < array.length; row++) {
            for(int column = 0; column < array[0].length; column++) {
                if(array[row][column] == 0) {
                    count++;
                    if(count == newPos) {
                        array[row][column] = 1;
                        return;
                    }
                }
            }
        }
    }

    // add listeners to every button
    private void addListeners() {
        buttonArray[0][0].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean slid = false;
                for (int i = 0; i < array.length; i++) {
                    if (slideUpLeft(array, i, 0))
                        slid = true;
                }
                for (int i = 1; i < array[0].length; i++) {
                    if (slideUpLeft(array, 0, i))
                        slid = true;
                }
                if (slid) {
                    generateNew();
                    refreshButtons();
                }
            }
        });

        buttonArray[array.length-1][array[0].length-1].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean slid = false;
                for (int i = 0; i < array.length; i++) {
                    if (slideDownRight(array, i, 0))
                        slid = true;
                }
                for (int i = 1; i < array[0].length; i++) {
                    if (slideDownRight(array, 0, i))
                        slid = true;
                }
                if (slid) {
                    generateNew();
                    refreshButtons();
                }
            }
        });

        for(int i = 1; i < array[0].length -1; i++) {
            buttonArray[0][i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    boolean slid = false;
                    for (int i = 0; i < array.length; i++) {
                        if (slideUp(array, i))
                            slid = true;
                    }
                    if (slid) {
                        generateNew();
                        refreshButtons();
                    }
                }
            });

            buttonArray[array.length-1][i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    boolean slid = false;
                    for (int i = 0; i < array.length; i++) {
                        if (slideDown(array, i))
                            slid = true;
                    }
                    if (slid) {
                        generateNew();
                        refreshButtons();
                    }
                }
            });
        }

        for(int i = 1; i < array.length -1; i++) {
            buttonArray[i][0].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    boolean slid = false;
                    for (int i = 0; i < array.length; i++) {
                        if (slideLeft(array, i))
                            slid = true;
                    }
                    if (slid) {
                        generateNew();
                        refreshButtons();
                    }
                }
            });

            buttonArray[i][array[0].length-1].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    boolean slid = false;
                    for (int i = 0; i < array.length; i++) {
                        if (slideRight(array, i))
                            slid = true;
                    }
                    if (slid) {
                        generateNew();
                        refreshButtons();
                    }
                }
            });
        }

        buttonArray[0][array[0].length-1].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("YES");
                boolean slid = false;
                for (int i = 0; i < array.length; i++) {
                    if (slideUpRight(array, i, array[0].length - 1))
                        slid = true;
                }
                for (int i = 1; i < array[0].length; i++) {
                    if (slideUpRight(array, 0, i))
                        slid = true;
                }
                if (slid) {
                    generateNew();
                    refreshButtons();
                }
            }
        });
        buttonArray[array.length-1][0].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("NO");
                boolean slid = false;
                for (int i = 0; i < array.length; i++) {
                    if (slideDownLeft(array, i, 0))
                        slid = true;
                }
                for (int i = 1; i < array[0].length; i++) {
                    if (slideDownLeft(array, array.length - 1, i))
                        slid = true;
                }
                if (slid) {
                    generateNew();
                    refreshButtons();
                }
            }
        });
    }

    // Main method
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		}
		catch (Exception e) {}

		new SlideGame(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
	}
}
