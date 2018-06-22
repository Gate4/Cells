package cells.gui.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author mzak
 */
public class ECAPanel extends JPanel implements ComponentListener, ActionListener {

    public static final int MODE_BLANK = 0;
    public static final int MODE_SINGLE = 1;
    public static final int MODE_RANDOM = 2;

    public static final String DESC_MODE_BLANK = "Blank";
    public static final String DESC_MODE_SINGLE = "Single cell";
    public static final String DESC_MODE_RANDOM = "Random";

    protected BufferedImage frame;

    protected boolean[] pattern;
    protected boolean[] currentLine;

    protected boolean clean;

    protected int currentY;

    protected int mode;

    protected Timer timer;

    public ECAPanel() {
        this.addComponentListener(this);
        this.frame = new BufferedImage(getWidth() > 0 ? getWidth() : 1,
                getHeight() > 0 ? getHeight() : 1,
                BufferedImage.TYPE_BYTE_GRAY);
        this.pattern = new boolean[8];
        this.currentLine = new boolean[getWidth()];
        this.mode = MODE_RANDOM;
        this.currentY = 0;
        this.timer = new Timer(5, this);
        this.timer.setRepeats(true);
        this.timer.start();
        this.clean = true;
    }

    public void setPattern(int pattern) throws Exception {
        setPattern(intPatternToBooleanArray(pattern));
    }

    public boolean[] intPatternToBooleanArray(int pattern){
        boolean[] tempPattern = new boolean[8];
        if (pattern >= 0 && pattern <= 255) {
            for (int i = 7; i >= 0; i--) {
                tempPattern[i] = (pattern & (1 << i)) != 0;
            }
        }
        return tempPattern;
    }

    public void setPattern(boolean[] pattern) {
        this.pattern = pattern;
    }

    public void reset(int mode, boolean[] pattern) {
        clean();
        this.pattern = pattern;
        this.currentY = 0;
        this.mode = mode;
        currentLine = new boolean[getWidth() > 0 ? getWidth() : 1];
        if (mode == MODE_SINGLE) {
            currentLine[currentLine.length / 2] = true;
        } else if (mode == MODE_RANDOM) {
            for (int i = 0; i < currentLine.length; i++) {
                currentLine[i] = Math.random() < 0.5;
            }
        }
    }

    public void clean() {
        this.frame = new BufferedImage(getWidth() > 0 ? getWidth() : 1,
                getHeight() > 0 ? getHeight() : 1,
                BufferedImage.TYPE_BYTE_GRAY);
        this.clean = true;
    }

    public void nextFrame() {
        repaint();
        if (currentY < getHeight() - 1) {
            currentY++;
            boolean tmp[] = new boolean[currentLine.length];
            for (int i = 0; i < currentLine.length; i++) {
                tmp[i] = pattern[
                        ( (currentLine[i > 0 ? i - 1 : currentLine.length - 1] ? 1 : 0) * 4)
                        + ((currentLine[i] ? 1 : 0) * 2)
                        + (currentLine[i < currentLine.length - 1 ? i + 1 : 0] ? 1 : 0)];
            }
            this.currentLine = tmp;
        }
    }

    public void reset(int mode, int pattern){
        reset(mode, intPatternToBooleanArray(pattern));
    }

    @Override
    public void componentResized(ComponentEvent e) {
        reset(mode, pattern);
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        nextFrame();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g.create();
        Graphics2D g2DImg = frame.createGraphics();
        if (clean) {
            g2DImg.setColor(Color.BLACK);
            g2DImg.fillRect(0, 0, getWidth(), getHeight());
            clean = false;
        }
        g2DImg.setColor(Color.WHITE);
        for (int i = 0; i < currentLine.length; i++) {
            if (currentLine[i]) {
                g2DImg.drawLine(i, currentY, i, currentY);
            }
        }

        g2D.drawImage(frame, 0, 0, null);
    }

}
