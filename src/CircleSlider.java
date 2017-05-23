import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileWriter;

public class CircleSlider {

    private JTextArea numberBox1;
    private JTextArea numberBox2;
    private JTextArea numberBox3;
    private String circle1question;

    public static void main(String [] args) {
        new CircleSlider().go();
    }

    private void go() {

        circle1question = JOptionPane.showInputDialog("insert question to go above first circle");

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.RED));

        JLabel q1 = new JLabel(circle1question);
        q1.setPreferredSize(new Dimension(150,40));
        q1.setBorder(BorderFactory.createLineBorder(Color.YELLOW));

        JLabel q2 = new JLabel(circle1question);
        q2.setPreferredSize(new Dimension(150,40));
        q2.setBorder(BorderFactory.createLineBorder(Color.YELLOW));

        JLabel q3 = new JLabel(circle1question);
        q3.setPreferredSize(new Dimension(150,40));
        q3.setBorder(BorderFactory.createLineBorder(Color.YELLOW));

        numberBox1 = new JTextArea("0");
        numberBox1.setEditable(false);
        numberBox1.setBorder(BorderFactory.createLineBorder(Color.ORANGE));

        numberBox2 = new JTextArea("0");
        numberBox2.setEditable(false);
        numberBox2.setBorder(BorderFactory.createLineBorder(Color.ORANGE));

        numberBox3 = new JTextArea("0");
        numberBox3.setEditable(false);
        numberBox3.setBorder(BorderFactory.createLineBorder(Color.ORANGE));

        JPanel topPanel1 = new JPanel();
        topPanel1.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        JPanel topPanel2 = new JPanel();
        topPanel2.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        JPanel topPanel3 = new JPanel();
        topPanel3.setBorder(BorderFactory.createLineBorder(Color.GREEN));

        CirclePanel circleOne = new CirclePanel();
        circleOne.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        circleOne.addMouseListener(new MyMouseListener(numberBox1, circleOne));
        circleOne.addMouseMotionListener(new MyMouseListener(numberBox1, circleOne));

        CirclePanel circleTwo = new CirclePanel();
        circleTwo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        circleTwo.addMouseListener(new MyMouseListener(numberBox2, circleTwo));
        circleTwo.addMouseMotionListener(new MyMouseListener(numberBox2, circleTwo));

        CirclePanel circleThree = new CirclePanel();
        circleThree.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        circleThree.addMouseListener(new MyMouseListener(numberBox3, circleThree));
        circleThree.addMouseMotionListener(new MyMouseListener(numberBox3, circleThree));

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new MyButtonListener());

        JPanel panelOne = new JPanel();
        panelOne.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        JPanel panelTwo = new JPanel();
        panelTwo.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        JPanel panelThree = new JPanel();
        panelThree.setBorder(BorderFactory.createLineBorder(Color.BLUE));

        BoxLayout BLOne = new BoxLayout(panelOne,BoxLayout.Y_AXIS);
        BoxLayout BLTwo = new BoxLayout(panelTwo,BoxLayout.Y_AXIS);
        BoxLayout BLThree = new BoxLayout(panelThree,BoxLayout.Y_AXIS);

        panelOne.setLayout(BLOne);
        panelTwo.setLayout(BLTwo);
        panelThree.setLayout(BLThree);

        topPanel1.add(q1);
        topPanel1.add(numberBox1);
        topPanel2.add(q2);
        topPanel2.add(numberBox2);
        topPanel3.add(q3);
        topPanel3.add(numberBox3);

        panelOne.add(topPanel1);
        panelOne.add(circleOne);

        panelTwo.add(topPanel2);
        panelTwo.add(circleTwo);
//		panelTwo.add(submitButton);

        panelThree.add(topPanel3);
        panelThree.add(circleThree);

        mainPanel.add(panelOne);
        mainPanel.add(panelTwo);
        mainPanel.add(panelThree);
//		mainPanel.add(submitButton);

        frame.setContentPane(mainPanel);

        frame.pack();
//		Rectangle screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
//		frame.setSize(screen.width, screen.height);
        frame.setVisible(true);
//		System.out.println(frame.getWidth() +", " + frame.getHeight());
        frame.setResizable(false);

    }

    class MyButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            try {
                FileWriter writer = new FileWriter("circleOutput.csv");
                writer.write(circle1question + ",");
                writer.write(numberBox1.getText());
                writer.write(numberBox2.getText());
                writer.write(numberBox3.getText());
                writer.flush();
            } catch(Exception ex) {
                System.out.println("Setting up writer and/or writing lines failed");
                ex.printStackTrace();
            }
        }
    }

    class MyMouseListener extends MouseAdapter {

        JTextArea numberBox;
        CirclePanel circle;

        MyMouseListener(JTextArea sent, CirclePanel sent2) {
            numberBox = sent;
            circle = sent2;
        }

        public void mousePressed(MouseEvent e) {
            Point mouseLocation = circle.getMousePosition();
            circle.setStart(mouseLocation.getX(), mouseLocation.getY());
        }

        public void mouseDragged(MouseEvent e) {
            Point mouseLocation = circle.getMousePosition();
            int newTextFieldValue = circle.calcNewSize(mouseLocation.getX(),mouseLocation.getY());
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

    CirclePanel() {
        setPreferredSize(new Dimension(280,280));
    }

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