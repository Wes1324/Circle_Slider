import javax.swing.*;
import java.awt.*;

public class CircleSlider {

    public static void main(String [] args) {
        new CircleSlider().go();
    }

    private void go() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        CirclePanel panelOne = new CirclePanel();
        CirclePanel panelTwo = new CirclePanel();

        frame.getContentPane().add(panelOne);
        frame.getContentPane().add(panelTwo);

        frame.setSize(800,300);
        frame.setVisible(true);
    }
}

class CirclePanel extends JPanel {

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fillOval(50,35,100,100);

    }
}
