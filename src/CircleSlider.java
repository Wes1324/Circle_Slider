import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CircleSlider {

    private JPanel panelOne;
    private CirclePanel circleOne;

    public static void main(String [] args) {
        new CircleSlider().go();
    }

    private void go() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel q1 = new JLabel("How happy do you feel?");
        q1.setPreferredSize(new Dimension(100,40));

        circleOne = new CirclePanel();
        circleOne.setPreferredSize(new Dimension(300,300));
        circleOne.addMouseListener(new myMouseListener());
        circleOne.addMouseMotionListener(new myMouseListener());

        panelOne = new JPanel();
        panelOne.setLayout(new BoxLayout(panelOne,BoxLayout.Y_AXIS));

        panelOne.add(q1);
        panelOne.add(circleOne);

        frame.setContentPane(panelOne);

        frame.pack();
        frame.setVisible(true);
    }

    class myMouseListener extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            Point mouseLocation = panelOne.getMousePosition();
            circleOne.setStart(mouseLocation.getX(), mouseLocation.getY());
        }

        public void mouseDragged(MouseEvent e) {
            Point mouseLocation = panelOne.getMousePosition();
            circleOne.calcNewSize(mouseLocation.getX(),mouseLocation.getY());
//			circleOne.callRepaint(mouseLocation.getX(),mouseLocation.getY());
        }
    }
}

class CirclePanel extends JPanel {

    private int xStart;
    private int yStart;
    private int displacement;
    private int circleSize = 40;
    private int xCoord = 130;
    private int yCoord = 115;

    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0,0,this.getWidth(),this.getHeight());
        g.setColor(Color.BLACK);
        g.fillOval(xCoord,yCoord,circleSize,circleSize);
    }

    void setStart(double xS, double yS) {
        xStart = (int) xS;
        yStart = (int) yS;
    }

    void calcNewSize(double Xnow, double Ynow) {
        int currentX = (int) Xnow;
        int currentY = (int) Ynow;
        displacement = (int) Math.abs(Math.hypot(xStart-currentX, yStart-currentY));
        System.out.println(displacement);
        resizeAndCallRepaint();
    }

    private void resizeAndCallRepaint() {
        if(displacement < 95) {
            circleSize = 40 + displacement;
            xCoord = 130 - displacement/2;
            yCoord = 115 - displacement/2;
        }
        this.repaint();
    }
}