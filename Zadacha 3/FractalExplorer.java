import java.awt.geom.Rectangle2D;
import java.awt.BorderLayout;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FractalExplorer {
    public static void main(String[] args) {
        FractalExplorer MyExplorer = new FractalExplorer(500);
        MyExplorer.createAndShowGUI();
        MyExplorer.drawFractal();
    }

    private int MyDispalySize;
    private int MySize;
    private JImageDisplay MyImage;
    private FractalGenerator MyGenerator;
    private Rectangle2D.Double MyRange = new Rectangle2D.Double(0, 0, 0, 0);

    FractalExplorer(int NewSize) {
        MySize = NewSize;
        MyImage = new JImageDisplay(NewSize, NewSize);
        MyGenerator = new Mandelbrot();
        MyGenerator.getInitialRange(MyRange);
    }

    public void createAndShowGUI() {
        JFrame MyFrame = new JFrame();
        MyFrame.setLayout(new BorderLayout());
        Button B = new Button("Reset");
        MyFrame.add(B, BorderLayout.SOUTH);
        MyFrame.add(MyImage, BorderLayout.CENTER);
        MyFrame.setDefaultCloseOperation(MyFrame.EXIT_ON_CLOSE);
        MyFrame.pack();
        MyFrame.setVisible(true);
        MyFrame.setResizable(false);
        MouseMyAdapter handler = new MouseMyAdapter();
        MyEventListener handler2 = new MyEventListener();
        MyImage.addMouseListener(handler);
        B.addActionListener(handler2);
    }

    public void drawFractal() {
        for (int x = 0; x < MySize; x++) {
            for (int y = 0; y < MySize; y++) {
                double X = FractalGenerator.getCoord(MyRange.x, MyRange.x + MyRange.width, MySize, x);
                double Y = FractalGenerator.getCoord(MyRange.y, MyRange.y + MyRange.height, MySize, y);
                int iteration = MyGenerator.numIterations(X, Y);
                int ColoR = 0;
                if (iteration != -1) {
                    float hue = 0.7f + (float) iteration / 200f;
                    ColoR = Color.HSBtoRGB(hue, 1f, 1f);
                }
                MyImage.drawPixel(x, y, ColoR);
            }
        }
        MyImage.repaint();
    }

    public class MouseMyAdapter extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {

            MyGenerator.recenterAndZoomRange(MyRange, FractalGenerator.getCoord(MyRange.x, MyRange.x + MyRange.width, MySize, e.getX()), FractalGenerator.getCoord(MyRange.y, MyRange.y + MyRange.height, MySize, e.getY()), 0.2);
            drawFractal();
        }
    }

    public class MyEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            MyGenerator.getInitialRange(MyRange);
            drawFractal();
        }
    }
}