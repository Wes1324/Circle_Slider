import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CircleSlider {
    private JPanel panelOne;
    private CirclePanel circleOne;
    private JTextArea numberBox;

    public static void main(String [] args) {
        new CircleSlider().go();
    }

    private void go() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel q1 = new JLabel("How happy do you feel?");
        q1.setPreferredSize(new Dimension(150,40));

//		JTextField numberBox = new JTextField("0");
        numberBox = new JTextArea("0");
        numberBox.setEditable(false);

        JPanel topPanel = new JPanel();
        topPanel.add(q1);
        topPanel.add(numberBox);

        circleOne = new CirclePanel();
        circleOne.setPreferredSize(new Dimension(300,300));
        circleOne.addMouseListener(new myMouseListener());
        circleOne.addMouseMotionListener(new myMouseListener());

        panelOne = new JPanel();
        panelOne.setLayout(new BoxLayout(panelOne,BoxLayout.Y_AXIS));

        panelOne.add(topPanel);
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
            int newTextFieldValue = circleOne.calcNewSize(mouseLocation.getX(),mouseLocation.getY());
//			System.out.println(newTextFieldValue);
            if(newTextFieldValue < 11) {
                numberBox.setText(" " + newTextFieldValue + " ");
            }
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

    int calcNewSize(double Xnow, double Ynow) {
        int currentX = (int) Xnow;
        int currentY = (int) Ynow;
        displacement = (int) Math.abs(Math.hypot(xStart-currentX, yStart-currentY));
//		System.out.println(displacement);
        resizeAndCallRepaint();
        return displacement/10;
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