import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;

public class CircleSlider {

    private JTextArea numberBox1;
    private JTextArea numberBox2;
    private JTextArea numberBox3;
    private JTextField name;
    private JTextField firstQ;
    private JTextField secondQ;
    private JTextField thirdQ;

    public static void main(String [] args) {
        new CircleSlider().go();
    }

    private void go() {

        name = new JTextField(15);
        firstQ = new JTextField(15);
        secondQ = new JTextField(15);
        thirdQ = new JTextField(15);

        JPanel dialogPanel = new JPanel();
        BoxLayout dialogBoxLayout = new BoxLayout(dialogPanel,BoxLayout.Y_AXIS);
        dialogPanel.setLayout(dialogBoxLayout);

        dialogPanel.add(new JLabel("Insert user's name:"));
        dialogPanel.add(name);
        dialogPanel.add(new JLabel("Insert first question:"));
        dialogPanel.add(firstQ);
        dialogPanel.add(new JLabel("Insert second question:"));
        dialogPanel.add(secondQ);
        dialogPanel.add(new JLabel("Insert third question:"));
        dialogPanel.add(thirdQ);

        int dialogSelection = JOptionPane.showConfirmDialog(null, dialogPanel,"Program set-up",JOptionPane.OK_CANCEL_OPTION);

        if(dialogSelection == JOptionPane.OK_OPTION) {

            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel mainPanel = new JPanel(new BorderLayout(5,5));
//			mainPanel.setBorder(BorderFactory.createLineBorder(Color.RED));

            JLabel q1 = new JLabel(firstQ.getText());
            q1.setPreferredSize(new Dimension(150,40));
//			q1.setBorder(BorderFactory.createLineBorder(Color.YELLOW));

            JLabel q2 = new JLabel(secondQ.getText());
            q2.setPreferredSize(new Dimension(150,40));
//			q2.setBorder(BorderFactory.createLineBorder(Color.YELLOW));

            JLabel q3 = new JLabel(thirdQ.getText());
            q3.setPreferredSize(new Dimension(150,40));
//			q3.setBorder(BorderFactory.createLineBorder(Color.YELLOW));

            numberBox1 = new JTextArea("0");
            numberBox1.setFont(numberBox1.getFont().deriveFont(Font.BOLD,14f));
            numberBox1.setBackground(new Color(238,238,238));
            numberBox1.setEditable(false);
//			numberBox1.setBorder(BorderFactory.createLineBorder(Color.ORANGE));

            numberBox2 = new JTextArea("0");
            numberBox2.setFont(numberBox2.getFont().deriveFont(Font.BOLD,14f));
            numberBox2.setBackground(new Color(238,238,238));
            numberBox2.setEditable(false);
//			numberBox2.setBorder(BorderFactory.createLineBorder(Color.ORANGE));

            numberBox3 = new JTextArea("0");
            numberBox3.setFont(numberBox3.getFont().deriveFont(Font.BOLD,14f));
            numberBox3.setBackground(new Color(238,238,238));
            numberBox3.setEditable(false);
//			numberBox3.setBorder(BorderFactory.createLineBorder(Color.ORANGE));

            JPanel topPanel1 = new JPanel();
//			topPanel1.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            JPanel topPanel2 = new JPanel();
//			topPanel2.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            JPanel topPanel3 = new JPanel();
//			topPanel3.setBorder(BorderFactory.createLineBorder(Color.GREEN));

            CirclePanel circleOne = new CirclePanel(Color.RED);
//			circleOne.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            circleOne.addMouseListener(new MyMouseListener(numberBox1, circleOne));
            circleOne.addMouseMotionListener(new MyMouseListener(numberBox1, circleOne));

            CirclePanel circleTwo = new CirclePanel(Color.YELLOW);
//			circleTwo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            circleTwo.addMouseListener(new MyMouseListener(numberBox2, circleTwo));
            circleTwo.addMouseMotionListener(new MyMouseListener(numberBox2, circleTwo));

            CirclePanel circleThree = new CirclePanel(Color.BLUE);
//			circleThree.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            circleThree.addMouseListener(new MyMouseListener(numberBox3, circleThree));
            circleThree.addMouseMotionListener(new MyMouseListener(numberBox3, circleThree));

            JButton submitButton = new JButton("Submit");
            submitButton.addActionListener(new MyButtonListener());

            JPanel panelOne = new JPanel();
//			panelOne.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            JPanel panelTwo = new JPanel();
//			panelTwo.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            JPanel panelThree = new JPanel();
//			panelThree.setBorder(BorderFactory.createLineBorder(Color.BLUE));

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
//			panelTwo.add(submitButton);

            panelThree.add(topPanel3);
            panelThree.add(circleThree);

            mainPanel.add(panelOne,BorderLayout.WEST);
            mainPanel.add(panelTwo,BorderLayout.CENTER);
            mainPanel.add(panelThree,BorderLayout.EAST);
            mainPanel.add(submitButton,BorderLayout.SOUTH);

            frame.setContentPane(mainPanel);

            frame.pack();
//			Rectangle screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
//			frame.setSize(screen.width, screen.height);
            frame.setVisible(true);
//			System.out.println(frame.getWidth() +", " + frame.getHeight());
            frame.setResizable(false);
        }
    }

    class MyButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            try {
                File outputFile = new File("Circle outputs.csv");
                FileWriter writer = new FileWriter(outputFile,true);

                writer.write(name.getText() + "\n");

                Date now = new Date();

//				writer.write(String.format("%tc", now) + "\n");
                writer.write(String.format("%ta %<te %<tb %<tY %<tT", now) + "\n");
                writer.write(firstQ.getText() + "," + numberBox1.getText() + "\n");
                writer.write(secondQ.getText() + "," + numberBox2.getText() + "\n");
                writer.write(thirdQ.getText() + "," + numberBox3.getText() + "\n" +"\n");
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

//		public void mouseReleased(MouseEvent e) {
//			circle.addDisplacements();
//		}
    }
}

class CirclePanel extends JPanel {

    private int xStart;
    private int yStart;
    private int displacement = 0;
    private int circleSize = 15;
    private int xCoord = 130;
    private int yCoord = 130;
    private Color circleColor;

    CirclePanel(Color Colour) {
        setPreferredSize(new Dimension(280,280));
        circleColor = Colour;
    }

    public void paintComponent(Graphics g) {
        g.setColor(new Color(238,238,238));
        g.fillRect(0,0,this.getWidth(),this.getHeight());
        g.setColor(circleColor);
        g.fillOval(xCoord,yCoord,circleSize,circleSize);
    }

    void setStart(double xS, double yS) {
        System.out.println(xStart + ", " + yStart);
        xStart = (int) xS;
        yStart = (int) yS;
        System.out.println(xStart + ", " + yStart);
    }

    int calcNewSize(double Xnow, double Ynow) {
        int currentX = (int) Xnow;
        int currentY = (int) Ynow;
        displacement = (int) Math.abs(Math.hypot(xStart-currentX, yStart-currentY));
        System.out.println("displacement: " + displacement + ", circle size: " + circleSize);
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