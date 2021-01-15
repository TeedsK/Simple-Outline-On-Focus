
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.BorderLayout;
import java.awt.Font;
@SuppressWarnings("serial")
/** 
 * Creates a JTextField that contains placeHolder text
 * Custom alpha values and animation if JTextField is in focus\
 * 
 * @author Teeds - Theo K
 */
public class PlaceholderTextField extends JTextField implements MouseListener, FocusListener, KeyListener {
    int TextAlpha = 120;
    boolean FocusedAllowed = true;
    boolean focused = false;
    String OriginalPlaceholder = "";
    int placeHolderTextAlpha = 100;
    int minimumTextAlpha = 170;
    int maximumTextAlpha = 255;
    boolean controlActive = false;
    int incrementAmount = 10;
    Color foreground = new Color(0, 0, 0);

    /**
     * Creates the placeholder object
     * @param placeHolder String for the desired placeholder text
     */
    public PlaceholderTextField(String placeHolder) {
        super();
        this.OriginalPlaceholder = placeHolder;
        setText(placeHolder);
        setCaretColor(new Color(0, 0, 0));
        addFocusListener(this);
        addMouseListener(this);
        addKeyListener(this);
        setForeground(new Color(0,0,0,placeHolderTextAlpha));
        setLayout(new BorderLayout());
    }

    /**
     * Creates the placeholder object
     * @param placeHolder String for the desired placeholder text
     * @param foreground the wanted foreground of the JTextField 
     */
    public PlaceholderTextField(String placeHolder, Color foreground) {
        super();
        this.foreground = foreground;
        this.OriginalPlaceholder = placeHolder;
        setText(placeHolder);
        setCaretColor(new Color(0, 0, 0));
        addFocusListener(this);
        addMouseListener(this);
        addKeyListener(this);
        setForeground(new Color(foreground.getRed(),foreground.getGreen(),foreground.getBlue(),placeHolderTextAlpha));
        setLayout(new BorderLayout());
    }

    /**
     * Change the foreground color
     * @param foreground the wanted foreground of the JTextField
     */
    public void setForegroundColor(Color foreground) {
        this.foreground = foreground;
        setForeground(new Color(foreground.getRed(), foreground.getGreen(), foreground.getBlue(), TextAlpha));
    }

    /**
     * Get the placeholder text
     * @return the place holder text
     */
    public String getPlaceHolderText() {
        return OriginalPlaceholder;
    }

    /**
     * Change the maximum Alpha the JTextField can achieve
     * @param Alpha the desired Alpha
     */
    public void setMaximumAlpha(int Alpha) {
        maximumTextAlpha = Alpha;
    }

    /**
     * Change the minimum Alpha the JTextField can achieve
     * @param Alpha the desired Alpha
     */
    public void setMinimumAlpha(int Alpha) {
        minimumTextAlpha = Alpha;
    }

    /**
     * Change the Alpha the JTextField when placeholder is active
     * @param Alpha the desired Alpha
     */
    public void setPlaceHolderAlpha(int Alpha) {
        placeHolderTextAlpha = Alpha;
    }

    /**
     * Change the font of the JTextField
     * @param location The location of the Font File
     * @param size The size of the font
     */
    public void ChangeFont(String location, float size) {
        Font f = null;
        try {
            f = Font.createFont(Font.TRUETYPE_FONT, new File(location));
        } catch (Exception err1) {}
        if(f != null) {
            setFont(f.deriveFont(Font.PLAIN, size));
        }
    }

    /**
     * Increases the alpha of the JTextField to the maximum possible
     */
    public void IncreaseTextAlphaToMaximum() {
        changeAlphaValue(true, maximumTextAlpha);
    }

    /**
     * Decreases the Alpha of the JtextField to the minimum if the placeholder is replaced with real text
     */
    public void DecreaseTextAlphaToMinimum() {
        changeAlphaValue(false, minimumTextAlpha);
    }

    /**
     * Decreaes the Alpha of the JTextField to the bare minimum 
     */
    public void DecreaseTextAlphaToPlaceHolder() {
        changeAlphaValue(false, placeHolderTextAlpha);
    }

    
    /**
     * Check if arrow keys were pressed
     * @param e Key Event
     * @return if arrow keys are active
     */
    private boolean arrowKeys(KeyEvent e) {
        return (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN
                || e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT);
    }

    /**
     * Checks if delete or backspace were pressed
     * @param e Key Event
     * @return if Control button, Delete, or backspace button are active
     */
    private boolean otherChecks(KeyEvent e) {
        return (e.isControlDown() || e.getKeyChar() == KeyEvent.VK_DELETE || e.getKeyChar() == KeyEvent.VK_BACK_SPACE);
    }

    /**
     * Changes the Alpha value
     * @param increase boolean if you are increasing alpha or decreasing
     * @param wantedAlpha the desired alpha amount
     */
    public void changeAlphaValue(boolean increase, int wantedAlpha) {
        Thread t = new Thread() {
            public void run() {
                focused = increase;
                while (focused == increase && TextAlpha != wantedAlpha) {
                    if (increase) {
                        TextAlpha += incrementAmount;
                    } else {
                        TextAlpha -= incrementAmount;
                    }
                    if (Math.abs(wantedAlpha - TextAlpha) <= 10) {
                        TextAlpha = wantedAlpha;
                    }
                    setForeground(new Color(foreground.getRed(), foreground.getGreen(), foreground.getBlue(), TextAlpha));
                    try {
                        sleep(7);
                    } catch (Exception err1) {
                    }
                }
            }
        };
        t.start();
    }

    @Override
    public void focusGained(FocusEvent e) {
        IncreaseTextAlphaToMaximum();
    }

    /**
     * Changes alpha levels when the focus is lost
     */
    @Override
    public void focusLost(FocusEvent e) {
        if(getText().equals(OriginalPlaceholder)) {
            DecreaseTextAlphaToPlaceHolder();
        } else {
            DecreaseTextAlphaToMinimum();
        }
    }

    /**
     * Removes the placeholder text if a letter is typed
     * if all text is deleted, it sets the text to the Placeholder
     */
    @Override
    public void keyTyped(KeyEvent e) {
        if (getText().equals(OriginalPlaceholder) && !otherChecks(e)) {
            setText("");
        } else if (getText().equals("")) {
            setText(OriginalPlaceholder);
            setCaretPosition(0);
        }
    }

    @Override

    /**
     * Checks if placeholder is active and ignores if arrow keys, or delete buttons are pressed
     * Watches out for a control V (pasting text)
     */
    public void keyPressed(KeyEvent e) {
        if(getText().equals(OriginalPlaceholder)) {
            controlActive = e.isControlDown();
            if(controlActive && e.getKeyCode() == KeyEvent.VK_V) {
                setText("");
            } else if(otherChecks(e)) {
                e.consume();
            } else if(arrowKeys(e)) {
                e.consume();
                setText(OriginalPlaceholder);
                setCaretPosition(0);
            }
        }
        
    }


    @Override
    public void keyReleased(KeyEvent e) {}

    /**
     * Sets the caret position to 0 if placeholder is active
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (getText().equals(OriginalPlaceholder)) {
            setCaretPosition(0);
        }
    }

    /**
     * Sets the caret position to 0 if placeholder is active
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (getText().equals(OriginalPlaceholder)) {
            setCaretPosition(0);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
