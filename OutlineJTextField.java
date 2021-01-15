import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.Graphics;
import java.awt.RenderingHints;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.BorderLayout;
public class OutlineJTextField extends JPanel implements FocusListener {
    JTextField field;
    Color background = new Color(255, 255, 255);
    Color outline = new Color(255, 255, 255);
    int round = 0;
    int alpha = 0;
    boolean focused = false;
    int increment = 10;
    float outlineSize = 2.3f;
    public OutlineJTextField(String text) {
        this.field = new JTextField(text);
        setup();
    }

    public OutlineJTextField(String text, Color background) {
        this.field = new JTextField(text);
        this.background = background;
        setup();
    }

    public OutlineJTextField(String text, Color background, Color outline) {
        this.field = new JTextField(text);
        this.background = background;
        this.outline = outline;
        setup();
    }

    public OutlineJTextField(String text, Color background, Color outline, int roundness) {
        this.field = new JTextField(text);
        this.background = background;
        this.outline = outline;
        this.round = roundness;
        setup();
    }

    public OutlineJTextField(JTextField placeHolderField) {
        this.field = placeHolderField;
        setup();
    }

    public OutlineJTextField(JTextField placeHolderField, Color background) {
        this.field = placeHolderField;
        this.background = background;
        setup();
    }

    public OutlineJTextField(JTextField placeHolderField, Color background, Color outline) {
        this.field = placeHolderField;
        this.background = background;
        this.outline = outline;
        setup();
    }

    public OutlineJTextField(JTextField placeHolderField, Color background, Color outline, int roundness) {
        this.field = placeHolderField;
        this.background = background;
        this.outline = outline;
        this.round = roundness;
        setup();
    }
    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(background);
        graphics.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, round, round);
        
        graphics.setStroke(new BasicStroke(outlineSize));
        graphics.setColor(new Color(outline.getRed(), outline.getGreen(), outline.getBlue(), alpha));
        graphics.drawRoundRect(2, 2, getWidth() - 4, getHeight() - 4, round, round);
    }

    private void setup() {
        this.setOpaque(false);
        this.field.addFocusListener(this);
        this.setLayout(new BorderLayout());
        this.add(this.field, BorderLayout.CENTER);
    }

    public void FadeOutlineIn() {
        changeAlpha(true, 255);
    }

    public void FadeOutlineOut() {
        changeAlpha(false, 0);
    }

    private void update() {
        revalidate();
        repaint();
    }

    private void changeAlpha(boolean increase, int wantedAlpha) {
        Thread t = new Thread() {
            public void run() {
                focused = increase;
                while (focused == increase && alpha != wantedAlpha) {
                    if (increase) {
                        alpha += increment;
                    } else {
                        alpha -= increment;
                    }
                    if (Math.abs(wantedAlpha - alpha) <= 10) {
                        alpha = wantedAlpha;
                    }
                    update();
                    try {
                        sleep(7);
                    } catch (Exception err1) {
                    }
                }
            }
        };
        t.start();
    }

    public void setOutlineStroke(float size) {
        this.outlineSize = size;
    }

    @Override
    public void focusGained(FocusEvent e) {
        FadeOutlineIn();
    }

    @Override
    public void focusLost(FocusEvent e) {
        FadeOutlineOut();
    }

    
}
