package painting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * import java.awt.event.MouseMotionListener;
 * import java.awt.event.MouseMotionAdapter;
 */
public class Main {

    public static void main(String[] args) {

            SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        System.out.println("Created GUI on EDT? " +
                SwingUtilities.isEventDispatchThread());
        JFrame f = new JFrame("Swing Paint Demo");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new MyPanel());
        f.setSize(250, 250);
        f.setVisible(true);
    }

}

class MyPanel extends JPanel {

    RedSquare redSquare = new RedSquare();
    JButton b2;
    Icon middleButtonIcon = new SquareIcon();

    public MyPanel() {

//        setBorder(BorderFactory.createLineBorder(Color.black));

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                moveSquare(e.getX(), e.getY());
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                moveSquare(e.getX(), e.getY());
            }
        });

        b2 = new JButton("Middle");
        b2.setIcon(middleButtonIcon);

        b2.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
//                b2.setEnabled(false);
                System.out.println(b2.isOpaque());
                JPopupMenu jPopupMenu = new JPopupMenu();
                b2.setComponentPopupMenu(jPopupMenu);

            }
        });
        b2.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("K"), "do sth");
        Action anAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("kupa");
            }
        };
        b2.getActionMap().put("do sth", anAction );
//        KeyListener KeyListener = null;
        add(b2);
    }

    private void moveSquare(int x, int y) {

        // Current square state, stored as final variables
        // to avoid repeat invocations of the same methods.
        final int CURR_X = redSquare.getX();
        final int CURR_Y = redSquare.getY();
        final int CURR_W = redSquare.getWidth();
        final int CURR_H = redSquare.getHeight();
        final int OFFSET = 1;

        if ((CURR_X != x) || (CURR_Y != y)) {

            // The square is moving, repaint background
            // over the old square location.
            repaint(CURR_X, CURR_Y, CURR_W + OFFSET, CURR_H + OFFSET);

            // Update coordinates.
            redSquare.setX(x);
            redSquare.setY(y);

            // Repaint the square at the new location.
            repaint(redSquare.getX(), redSquare.getY(),
                    redSquare.getWidth() + OFFSET,
                    redSquare.getHeight() + OFFSET);
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(250, 200);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString("This is my custom Panel!", 10, 20);
        for (int i = 0; i < 5; i++) {
//            g.drawRect(i, i, 250, 100);
            g.drawLine(0, i, 250, 100 + i);
            g.drawLine(250, i, 0 , 100 + i);
            g.drawLine(0, i, 250 , i);
            g.drawLine(250, i, 0 , 100 + i);

        }

        redSquare.paintSquare(g);
    }
}

class RedSquare {

    private int xPos = 50;
    private int yPos = 50;
    private final int width = 20;
    private final int height = 20;

    public void setX(int xPos) {
        this.xPos = xPos;
    }

    public int getX() {
        return xPos;
    }

    public void setY(int yPos) {
        this.yPos = yPos;
    }

    public int getY() {
        return yPos;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void paintSquare(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(xPos, yPos, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(xPos, yPos, width, height);
    }
}

class SquareIcon implements Icon {
    private static final int SIZE = 10;

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        if (c.isEnabled()) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.GRAY);
        }

        g.fillRect(x, y, SIZE, SIZE);
        System.out.println(c.isEnabled());
    }

    @Override
    public int getIconWidth() {
        return SIZE;
    }

    @Override
    public int getIconHeight() {
        return SIZE;
    }
}