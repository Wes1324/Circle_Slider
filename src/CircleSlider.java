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
        q1.setBorder(BorderFactory.createLineBorder(Color.black, 2));

        circleOne = new CirclePanel();
        circleOne.setPreferredSize(new Dimension(300,300));
        circleOne.setBorder(BorderFactory.createLineBorder(Color.green, 2));

        panelOne = new JPanel();
        panelOne.setLayout(new BoxLayout(panelOne,BoxLayout.Y_AXIS));
        panelOne.setBorder(BorderFactory.createLineBorder(Color.red, 2));
        panelOne.addMouseListener(new myMouseListener());
        panelOne.addMouseMotionListener(new myMouseListener());

        panelOne.add(q1);
//		q1.setHorizontalAlignment(JLabel.CENTER);

        panelOne.add(circleOne);

        frame.setContentPane(panelOne);

        frame.pack();
        frame.setVisible(true);
    }

    class myMouseListener extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            Point mouseLocation = panelOne.getMousePosition();
            circleOne.setStart(mouseLocation.getX(), mouseLocation.getY());
            System.out.println("START POINT SENT TO CIRCLE PANEL CLASS");
        }

        public void mouseDragged(MouseEvent e) {
            Point mouseLocation = panelOne.getMousePosition();
            circleOne.callRepaint(mouseLocation.getX(),mouseLocation.getY());
            System.out.println(circleOne.increaseFactor);
        }

    }
}

class CirclePanel extends JPanel {

    private double xStart;
    private double yStart;
    double increaseFactor;
    private int circleSize = 40;

    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0,0,this.getWidth(),this.getHeight());
        g.setColor(Color.BLACK);
        g.fillOval(130,115,circleSize,circleSize);
    }

    void setStart(double xS, double yS) {
        xStart = xS;
        yStart = yS;
    }

    void callRepaint(double xLoc, double yLoc) {
        double xMove = Math.abs(xStart - xLoc);
        double yMove = Math.abs(yStart - yLoc);
        increaseFactor = (Math.abs(Math.hypot(xLoc, yLoc)));
        if(increaseFactor < 300) {
            circleSize = (int) increaseFactor;
        }
//		xCoord -= increaseFactor/2;
//		yCoord -= increaseFactor/2;
        this.repaint();
    }
}

//			System.out.println(mouseLocation.getX() + ", " + mouseLocation.getY());