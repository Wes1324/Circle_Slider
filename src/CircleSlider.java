   /*
    * A questionnaire application that allows users to quantitatively answer questions set by someone else by
    * dragging circles to change their sizes.
    * The person setting the questions must run the program and set the questions in the 'Program set-up' dialog
    * before the user uses the program.
    * When the user submits their answers, they will be output to a CSV file that can be manipulated in excel.
    *
    * Wesley Coffin-Jones 22/6/16
    */

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

    private JFrame frame;
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

            frame = new JFrame();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            JPanel mainPanel = new JPanel(new BorderLayout(5,5));
//			mainPanel.setBorder(BorderFactory.createLineBorder(Color.RED));			//BORDER

            JTextArea q1 = new JTextArea(firstQ.getText());
//			q1.setPreferredSize(new Dimension(230,50));
//			q1.setBorder(BorderFactory.createLineBorder(Color.YELLOW));			//BORDER
            q1.setLineWrap(true);
            q1.setWrapStyleWord(true);
            q1.setBackground(new Color(238,238,238));
            q1.setFont(q1.getFont().deriveFont(Font.BOLD,14f));
            q1.setEditable(false);

            JScrollPane q1Pane = new JScrollPane(q1,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            q1Pane.setPreferredSize(new Dimension(230,50));

            JTextArea q2 = new JTextArea(secondQ.getText());
//			q2.setPreferredSize(new Dimension(230,50));
//			q2.setBorder(BorderFactory.createLineBorder(Color.YELLOW));			//BORDER
            q2.setLineWrap(true);
            q2.setWrapStyleWord(true);
            q2.setBackground(new Color(238,238,238));
            q2.setFont(q2.getFont().deriveFont(Font.BOLD,14f));
            q2.setEditable(false);

            JScrollPane q2Pane = new JScrollPane(q2,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            q2Pane.setPreferredSize(new Dimension(230,50));

            JTextArea q3 = new JTextArea(thirdQ.getText());
//			q3.setPreferredSize(new Dimension(150,40));
//			q3.setBorder(BorderFactory.createLineBorder(Color.YELLOW));			//BORDER
            q3.setLineWrap(true);
            q3.setWrapStyleWord(true);
            q3.setBackground(new Color(238,238,238));
            q3.setFont(q3.getFont().deriveFont(Font.BOLD,14f));
            q3.setEditable(false);

            JScrollPane q3Pane = new JScrollPane(q3,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            q3Pane.setPreferredSize(new Dimension(230,50));

            numberBox1 = new JTextArea("0/10");
            numberBox1.setFont(numberBox1.getFont().deriveFont(Font.BOLD,14f));
            numberBox1.setBackground(new Color(238,238,238));
            numberBox1.setEditable(false);
//			numberBox1.setBorder(BorderFactory.createLineBorder(Color.ORANGE));		//BORDER

            numberBox2 = new JTextArea("0/10");
            numberBox2.setFont(numberBox2.getFont().deriveFont(Font.BOLD,14f));
            numberBox2.setBackground(new Color(238,238,238));
            numberBox2.setEditable(false);
//			numberBox2.setBorder(BorderFactory.createLineBorder(Color.ORANGE));		//BORDER

            numberBox3 = new JTextArea("0/10");
            numberBox3.setFont(numberBox3.getFont().deriveFont(Font.BOLD,14f));
            numberBox3.setBackground(new Color(238,238,238));
            numberBox3.setEditable(false);
//			numberBox3.setBorder(BorderFactory.createLineBorder(Color.ORANGE));		//BORDER

            JPanel topPanel1 = new JPanel();
//			topPanel1.setBorder(BorderFactory.createLineBorder(Color.GREEN));		//BORDER
            JPanel topPanel2 = new JPanel();
//			topPanel2.setBorder(BorderFactory.createLineBorder(Color.GREEN));		//BORDER
            JPanel topPanel3 = new JPanel();
//			topPanel3.setBorder(BorderFactory.createLineBorder(Color.GREEN));		//BORDER

            CirclePanel circleOne = new CirclePanel(Color.RED);
//			circleOne.setBorder(BorderFactory.createLineBorder(Color.BLACK));		//BORDER
            circleOne.addMouseListener(new MyMouseListener(numberBox1, circleOne));
            circleOne.addMouseMotionListener(new MyMouseListener(numberBox1, circleOne));

            CirclePanel circleTwo = new CirclePanel(Color.YELLOW);
//			circleTwo.setBorder(BorderFactory.createLineBorder(Color.BLACK));		//BORDER
            circleTwo.addMouseListener(new MyMouseListener(numberBox2, circleTwo));
            circleTwo.addMouseMotionListener(new MyMouseListener(numberBox2, circleTwo));

            CirclePanel circleThree = new CirclePanel(Color.BLUE);
//			circleThree.setBorder(BorderFactory.createLineBorder(Color.BLACK));		//BORDER
            circleThree.addMouseListener(new MyMouseListener(numberBox3, circleThree));
            circleThree.addMouseMotionListener(new MyMouseListener(numberBox3, circleThree));

            JButton submitButton = new JButton("Submit");
            submitButton.addActionListener(new MyButtonListener());

            JPanel panelOne = new JPanel();
//			panelOne.setBorder(BorderFactory.createLineBorder(Color.BLUE));			//BORDER
            JPanel panelTwo = new JPanel();
//			panelTwo.setBorder(BorderFactory.createLineBorder(Color.BLUE));			//BORDER
            JPanel panelThree = new JPanel();
//			panelThree.setBorder(BorderFactory.createLineBorder(Color.BLUE));		//BORDER

            BoxLayout BLOne = new BoxLayout(panelOne,BoxLayout.Y_AXIS);
            BoxLayout BLTwo = new BoxLayout(panelTwo,BoxLayout.Y_AXIS);
            BoxLayout BLThree = new BoxLayout(panelThree,BoxLayout.Y_AXIS);

            panelOne.setLayout(BLOne);
            panelTwo.setLayout(BLTwo);
            panelThree.setLayout(BLThree);

            topPanel1.add(q1Pane);
            topPanel1.add(numberBox1);
            topPanel2.add(q2Pane);
            topPanel2.add(numberBox2);
            topPanel3.add(q3Pane);
            topPanel3.add(numberBox3);

            panelOne.add(topPanel1);
            panelOne.add(circleOne);

            panelTwo.add(topPanel2);
            panelTwo.add(circleTwo);

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
            int confirm = JOptionPane.showConfirmDialog(frame,"You are about to send your answers","Confirm send",JOptionPane.OK_CANCEL_OPTION);
            if(confirm == JOptionPane.OK_OPTION) {

                JPanel confirmPage = new JPanel();
                BorderLayout confirmPageLayout = new BorderLayout(10,10);
                confirmPage.setLayout(confirmPageLayout);
                JLabel confirmMessage;

                try {
                    File outputFile = new File("Circle outputs.csv");
                    FileWriter writer = new FileWriter(outputFile,true);

                    writer.write(name.getText() + "\n");

                    Date now = new Date();

                    String [] splitFirstBox = numberBox1.getText().split("/");
                    String justNumber1 = splitFirstBox[0];
                    String [] splitSecondBox = numberBox2.getText().split("/");
                    String justNumber2 = splitSecondBox[0];
                    String [] splitThirdBox = numberBox3.getText().split("/");
                    String justNumber3 = splitThirdBox[0];

                    writer.write(String.format("%ta %<te %<tb %<tY %<tT", now) + "\n");
                    writer.write(firstQ.getText() + "," + justNumber1 + "\n");
                    writer.write(secondQ.getText() + "," + justNumber2 + "\n");
                    writer.write(thirdQ.getText() + "," + justNumber3 + "\n" +"\n");
                    writer.flush();

                    confirmMessage = new JLabel("Your responses have been sent. Thank you",JLabel.CENTER);

                } catch(Exception ex) {
                    System.out.println("Setting up writer and/or writing lines failed");
                    confirmMessage = new JLabel("Error. Your responses HAVE NOT been sent. Please make sure the output excel spreadsheet for this program is not open. Thank you",JLabel.CENTER);
                    ex.printStackTrace();
                }

                confirmPage.add(confirmMessage,BorderLayout.CENTER);
                frame.setContentPane(confirmPage);
                frame.validate();
                frame.repaint();
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
            int newTextFieldValue = 0;
            if(mouseLocation != null) {
                newTextFieldValue = circle.calcNewSize(mouseLocation.getX(), mouseLocation.getY());
            } else {
                System.out.println("Circle can grow no more for this mouse drag. Try releasing mouse and dragging again");
            }
            if(newTextFieldValue != 0 && newTextFieldValue < 250) {
                int divide = newTextFieldValue/19;
                if(divide <= 10 && divide >= 0) {
                    numberBox.setText(Integer.toString(divide) + "/10");
                }
            }
        }
    }
}

class CirclePanel extends JPanel {

    private int xStart;
    private int yStart;
    private int totalDisplacement;
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
        int displacementForThisDrag = (int) Math.hypot(xStart-currentX, yStart-currentY);
        System.out.println("total displacement: " + totalDisplacement + ", this displacement: " + displacementForThisDrag + ", circle size: " + circleSize);
        int firstClickDistance = (int) Math.hypot(xStart-130,yStart-130);   //Distance from the original click of this drag to top, left corner of original unchanged circle
        int currentDragDistance = (int) Math.hypot(currentX-130,currentY-130);  //Distance from the current position of mouse during drag to top, left corner of original unchanged circle
        if(currentDragDistance>firstClickDistance) {
            totalDisplacement += displacementForThisDrag;
        }
        if(currentDragDistance<firstClickDistance) {
            totalDisplacement -= displacementForThisDrag;
        }
        xStart = currentX;
        yStart = currentY;
        resizeAndCallRepaint();
        return totalDisplacement;
    }

    private void resizeAndCallRepaint() {
        if(totalDisplacement < 230) {
            circleSize = 15 + totalDisplacement;
            xCoord = 130 - totalDisplacement/2;
            yCoord = 130 - totalDisplacement/2;
        }
        this.repaint();
    }
}