


import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.security.Key;


import static com.googlecode.lanterna.input.KeyType.Escape;

public class Ecker_HangMan {

    static String misses = "";
    static String hits = "";
    static Terminal terminal;

    public static void main(String[] args) throws IOException {
        terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);
        TextGraphics tg = screen.newTextGraphics();

        String secretWord;
        screen.setCursorPosition(null);
        screen.startScreen();

        secretWord = readSolution(screen, tg);

        initHits(secretWord);

        KeyStroke keyPressed;

        keyPressed = screen.readInput();
    }

    private static String readSolution(Screen screen, TextGraphics tg) throws IOException {

        String secretWord = "";
        boolean keepruning = true;
        KeyStroke keyPressed;

        tg.putString(2,2, "Please enter the secret word in lower case and press 'Enter' afterwards");
        screen.refresh();
        keyPressed = screen.readInput();
        while (keyPressed.getKeyType() != KeyType.Enter){
            secretWord += keyPressed.getCharacter();
            keyPressed = screen.readInput();
        }
        tg.putString(2 ,3, secretWord);

        return secretWord;
    }

    private static void initHits(String secretWord){
    }

    private static int nextGuess(Screen screen, TextGraphics tg, String secretWord, int faultNum) {


        return faultNum;
    }
}
