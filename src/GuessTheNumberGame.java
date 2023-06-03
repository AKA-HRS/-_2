import javax.swing.*;
import java.awt.*;


public class GuessTheNumberGame extends JFrame {
    private int targetNumber;
    private int attempts;
    private int maxAttempts;
    private JLabel messageLabel;
    private JTextField guessTextField;
    private JButton guessButton;
    private JButton quitButton;

    public GuessTheNumberGame() {
        int minRange = getRangeInput("Enter the minimum number:");
        int maxRange = getRangeInput("Enter the maximum number:");
        this.targetNumber = generateRandomNumber(minRange, maxRange);
        this.attempts = 0;
        this.maxAttempts = 5;

        setTitle("Guess the Number");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        messageLabel = new JLabel("Guess a number between " + minRange + " and " + maxRange + ":");
        guessTextField = new JTextField(10);
        guessButton = new JButton("Guess");
        quitButton = new JButton("Quit");

        guessButton.addActionListener(e -> checkGuess());
        quitButton.addActionListener(e -> quitGame());

        setLayout(new FlowLayout());

        add(messageLabel);
        add(guessTextField);
        add(guessButton);
        add(quitButton);
    }

    private int getRangeInput(String message) {
        int range = 0;
        boolean validInput = false;
        while (!validInput) {
            String input = JOptionPane.showInputDialog(this, message);
            if (input == null) {
                System.exit(0);
            }
            try {
                range = Integer.parseInt(input);
                validInput = true;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input! Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return range;
    }

    private void checkGuess() {
        try {
            String guessText = guessTextField.getText();
            if (guessText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int guess = Integer.parseInt(guessText);
            attempts++;

            if (guess == targetNumber) {
                JOptionPane.showMessageDialog(this, "Congratulations! You guessed the number in " + attempts + " attempts.", "Winner", JOptionPane.INFORMATION_MESSAGE);
                resetGame();
            } else if (guess < targetNumber) {
                JOptionPane.showMessageDialog(this, "Too low! Try again.", "Wrong Guess", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Too high! Try again.", "Wrong Guess", JOptionPane.WARNING_MESSAGE);
            }

            if (attempts >= maxAttempts) {
                JOptionPane.showMessageDialog(this, "You reached the maximum number of attempts. The number was " + targetNumber + ".", "Game Over", JOptionPane.ERROR_MESSAGE);
                resetGame();
            }

            guessTextField.setText("");
            guessTextField.requestFocus();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input! Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetGame() {
        int minRange = getRangeInput("Enter the minimum number:");
        int maxRange = getRangeInput("Enter the maximum number:");
        targetNumber = generateRandomNumber(minRange, maxRange);
        attempts = 0;
    }

    private void quitGame() {
        int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit the game?", "Quit", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private int generateRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GuessTheNumberGame game = new GuessTheNumberGame();
            game.setVisible(true);
        });
    }
}
