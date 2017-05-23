import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileWriter;

public class CircleSlider {

    private JPanel panelOne;
    private CirclePanel circleOne;
    private JTextArea numberBox;
    private String circle1question;

    public static void main(String [] args) {
        new CircleSlider().go();
    }

    private void go() {

        circle1question = JOptionPane.showInputDialog("insert question to go above first circle");

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel q1 = new JLabel(circle1question);
        q1.setPreferredSize(new Dimension(150,40));

        numberBox = new JTextArea("0");
        numberBox.setEditable(false);

        JPanel topPanel = new JPanel();
        topPanel.add(q1);
        topPanel.add(numberBox);

        circleOne = new CirclePanel();
        circleOne.setPreferredSize(new Dimension(280,280));
        circleOne.addMouseListener(new myMouseListener());
        circleOne.addMouseMotionListener(new myMouseListener());

        JButton submitButton = new JButton("Submit");
//		submitButton.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        submitButton.addActionListener(new myButtonListener());

        panelOne = new JPanel();
        panelOne.setLayout(new BoxLayout(panelOne,BoxLayout.Y_AXIS));

        panelOne.add(topPanel);
        panelOne.add(circleOne);
        panelOne.add(submitButton);

        frame.setContentPane(panelOne);

        frame.pack();
//		Rectangle screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
//		frame.setSize(screen.width, screen.height);
        frame.setVisible(true);
//		System.out.println(frame.getWidth() +", " + frame.getHeight());
        frame.setResizable(false);

    }

    class myButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            try {
                FileWriter writer = new FileWriter("circleOutput.csv");
                writer.write(circle1question + ",");
                writer.write(numberBox.getText());
                writer.flush();
            } catch(Exception ex) {
                System.out.println("Setting up writer and/or writing lines failed");
                ex.printStackTrace();
            }
        }
    }

    class myMouseListener extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            Point mouseLocation = panelOne.getMousePosition();
            circleOne.setStart(mouseLocation.getX(), mouseLocation.getY());
        }

        public void mouseDragged(MouseEvent e) {
            Point mouseLocation = panelOne.getMousePosition();
            int newTextFieldValue = circleOne.calcNewSize(mouseLocation.getX(),mouseLocation.getY());
            if(newTextFieldValue < 250) {
                int divide = newTextFieldValue/19;
                if(divide < 11) {
                    numberBox.setText(Integer.toString(divide));
                }
            }
        }
    }
}

class CirclePanel extends JPanel {

    private int xStart;
    private int yStart;
    private int displacement;
    private int circleSize = 15;
    private int xCoord = 130;
    private int yCoord = 130;

    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0,0,this.getWidth(),this.getHeight());
        g.setColor(Color.GREEN);
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
        resizeAndCallRepaint();
        return displacement;
    }

    private void resizeAndCallRepaint() {
        if(displacement < 230) {
            circleSize = 15 + displacement;
            xCoord = 130 - displacement/2;
            yCoord = 130 - displacement/2;
        }
        this.repaint();
    }
}