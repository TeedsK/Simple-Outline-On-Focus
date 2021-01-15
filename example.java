import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class example {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JPanel background = new JPanel();
        background.setBackground(new Color(0,0,0));
        background.setLayout(new BoxLayout(background, BoxLayout.X_AXIS));
        frame.setMinimumSize(new Dimension(500,500));
        frame.add(new PlaceholderTextField("test"));
        frame.add(background);
        background.add(Box.createHorizontalGlue());
        PlaceholderTextField field = new PlaceholderTextField("Placeholder Text", new Color(255,255,255));
        field.setMaximumSize(new Dimension(300,40));
        field.setPreferredSize(new Dimension(300,400));
        field.setOpaque(false);
        field.setBorder(null);
        field.setCaretColor(new Color(255,255,255));
        OutlineJTextField test = new OutlineJTextField(field, new Color(60, 68, 70), new Color(158, 184, 193), 5);
        test.setMaximumSize(new Dimension(300,40));
        test.setPreferredSize(new Dimension(300,400));
        test.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 10));
        background.add(test);
        background.add(Box.createHorizontalGlue());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
