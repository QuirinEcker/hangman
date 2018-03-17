


import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Ecker_HangMan {

    private static final int WORD_POSITION = 22;
    private static String falseChars = "";
    private static Terminal terminal;
    private static String correctWord = "";

    public static void main(String[] args) throws IOException {
        terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);
        TextGraphics tg = screen.newTextGraphics();
        screen.setCursorPosition(null);
        screen.startScreen();

        String secretWord;

        secretWord = readSolution(screen, tg);
        printInterface(secretWord.length(), tg);

        screen.refresh();

        initHits(secretWord, screen, tg);

        screen.refresh();
        screen.readInput();
        screen.stopScreen();
    }

    private static String readSolution(Screen screen, TextGraphics tg) throws IOException {

        String secretWord = "";
        KeyStroke keyPressed;
        String inputString = "Please enter the secret word in lower case and press 'Enter' afterwards";

        tg.putString(2,2, inputString);
        screen.refresh();
        keyPressed = screen.readInput();
        while (keyPressed.getKeyType() != KeyType.Enter){
            secretWord += keyPressed.getCharacter();
            keyPressed = screen.readInput();
        }
        deleteReadSolution(tg, inputString);
        return secretWord;
    }

    private static void deleteReadSolution(TextGraphics tg, String inputString) {
        for (int i = 0; i < inputString.length(); i++){
            tg.putString(2 + i,2," ");
        }
    }

    private static void printInterface(int secretWordDigits , TextGraphics tg) throws IOException {
        tg.putString(20, WORD_POSITION, "Word: ");
        for (int i = 0; i < secretWordDigits; i++) {
            tg.putString(26 + i * 2, WORD_POSITION, "_");
        }
        tg.putString(20, WORD_POSITION + 1, "Misses: ");
    }

    private static void initHits(String secretWord , Screen screen, TextGraphics tg) throws IOException{
        KeyStroke keyPressed;
        char currentKeyPress;
        keyPressed = screen.readInput();
        int numberOfFails = 0;

        while (keyPressed.getKeyType() != KeyType.Escape) {
            boolean foundCorrectChar = false;

            currentKeyPress = keyPressed.getCharacter();
            for (int i = 0; i < secretWord.length(); i++){
                if (currentKeyPress == secretWord.charAt(i)) {
                    printCorrectChar(secretWord, i, currentKeyPress, tg);
                    foundCorrectChar = true;
                }
            }
            if (foundCorrectChar == false) {
                numberOfFails++;
                drawFalseDrawing(numberOfFails, tg, currentKeyPress, screen);
            }
            screen.refresh();
            keyPressed = screen.readInput();

        }
    }

    private static void drawFalseDrawing(int numberOfFails, TextGraphics tg, char currenKeyPress, Screen screen) throws IOException {
        printFalseChar(tg, currenKeyPress);

        scanDefeat(numberOfFails, tg);
        switch (numberOfFails) {
            case 1:
                drawGround(tg);
                break;
            case 2:
                drawVerticalBeam(tg);
                break;
            case 3:
                drawhorizontalBeam(tg);
                break;
            case 4:
                drawVerticalBeam2(tg);
                break;
            case 5:
                drawHead(tg);
                break;
            case 6:
                drawBody(tg);
                break;
            case 7:
                drawHands(tg);
                break;
            case 8:
                drawLegs(tg, screen);
                break;
        }
    }

    private static void scanDefeat(int numberOfFails, TextGraphics tg) {
        if (numberOfFails == 8) {
            tg.putString(20, WORD_POSITION + 1, "The Man is dead, you lost!");
        }
    }

    private static void printFalseChar(TextGraphics tg, char currenKeyPress) {
        falseChars += currenKeyPress + ",";
        tg.putString(28, WORD_POSITION + 1, falseChars);
    }

    private static void printCorrectChar(String secretWord, int secretWordDigi, char correctKeyPress, TextGraphics tg) {
        secretWordDigi *= 2;
        correctWord += correctKeyPress;
        tg.putString(20 + 6 + secretWordDigi, WORD_POSITION, "" + correctKeyPress);
        scanWin(secretWord, tg);
    }

    private static void scanWin(String secretWord, TextGraphics tg) {
        if (correctWord.length() == secretWord.length()) {
            tg.putString(20, WORD_POSITION + 1, "Congratulations, you won!");
        }
    }

    private static void drawGround(TextGraphics tg) throws IOException {
        tg.drawRectangle(new TerminalPosition(20, 20), new TerminalSize(30, 1), Symbols.BLOCK_SOLID);
    }

    private static void drawVerticalBeam(TextGraphics tg) {
        tg.drawRectangle(new TerminalPosition(45, 5), new TerminalSize(1, 15), Symbols.BLOCK_SOLID);
    }

    private static void drawhorizontalBeam(TextGraphics tg) {
        tg.drawRectangle(new TerminalPosition(28, 5), new TerminalSize(21, 1), Symbols.BLOCK_SOLID);
    }

    private static void drawVerticalBeam2(TextGraphics tg) {
        tg.drawRectangle(new TerminalPosition(28, 5), new TerminalSize(1, 2), Symbols.BLOCK_SOLID);
    }

    private static void drawHead(TextGraphics tg){
        tg.drawRectangle(new TerminalPosition(26, 7), new TerminalSize(5, 3), Symbols.WHITE_CIRCLE);
    }

    private static void drawBody(TextGraphics tg) {
        tg.drawRectangle(new TerminalPosition(28,10), new TerminalSize(1, 4), Symbols.BOLD_SINGLE_LINE_VERTICAL);
    }

    private static void drawHands(TextGraphics tg) {
        tg.drawRectangle(new TerminalPosition(26, 11), new TerminalSize(2, 1), Symbols.BOLD_SINGLE_LINE_HORIZONTAL);
        tg.drawRectangle(new TerminalPosition(29, 11), new TerminalSize(2, 1), Symbols.BOLD_SINGLE_LINE_HORIZONTAL);
    }

    private static void drawLegs(TextGraphics tg, Screen screen) throws  IOException {
        tg.drawLine(27, 14,24,17, '/');
        tg.drawLine(29, 14,32,17, '\\');
    }
}
