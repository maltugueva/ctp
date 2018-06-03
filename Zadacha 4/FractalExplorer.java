import java.awt.geom.Rectangle2D;
import java.awt.BorderLayout;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.filechooser.FileNameExtensionFilter;

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
    private FractalGenerator MyGeneratorT;
    private FractalGenerator MyGeneratorM;
    private FractalGenerator MyGeneratorB;
    private Rectangle2D.Double MyRange = new Rectangle2D.Double(0, 0, 0, 0);
    private JComboBox Box = new JComboBox();
    private Button B = new Button("Reset");
    private Button S = new Button("Save");

    FractalExplorer(int NewSize) {
        MySize = NewSize;
        MyImage = new JImageDisplay(NewSize, NewSize);
        MyGenerator = new Tricorn();
        MyGeneratorT = new Tricorn();
        MyGeneratorM = new Mandelbrot();
        MyGeneratorB = new BurningShip();
        MyGenerator.getInitialRange(MyRange);
    }

    public void createAndShowGUI() {
        JFrame MyFrame = new JFrame();
        MyFrame.setLayout(new BorderLayout());


        JLabel MyLable = new JLabel("FRACTAL");
        JPanel MyPanel = new JPanel();
        JPanel MyPanel2 = new JPanel();
        MyPanel.add(MyLable);
        MyPanel.add(Box);
        MyFrame.add(MyPanel, BorderLayout.NORTH);
        Box.addItem(MyGeneratorT);
        Box.addItem(MyGeneratorM);
        Box.addItem(MyGeneratorB);
        MyPanel2.add(B);
        MyPanel2.add(S);
        MyFrame.add(MyPanel2, BorderLayout.SOUTH);
        MyFrame.add(MyImage, BorderLayout.CENTER);
        MyFrame.setDefaultCloseOperation(MyFrame.EXIT_ON_CLOSE);
        MyFrame.pack();
        MyFrame.setVisible(true);
        MyEventListener handler2 = new MyEventListener();
        MyFrame.setResizable(false);
        MouseMyAdapter handler = new MouseMyAdapter();
        MyImage.addMouseListener(handler);
        B.addActionListener(handler2);
        S.addActionListener(handler2);
        Box.addActionListener(handler2);
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
            if (e.getSource().equals(B)) {
                MyGenerator.getInitialRange(MyRange);
            }
            if (e.getSource().equals(S)) {
                JFileChooser ChooseFile = new JFileChooser();
                FileNameExtensionFilter FileFilter = new FileNameExtensionFilter("PNG Images", "png");
                ChooseFile.setFileFilter(FileFilter);
                ChooseFile.setAcceptAllFileFilterUsed(false);
                if (ChooseFile.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    try {
                        File file = ChooseFile.getSelectedFile();
                        String PathM = file.getPath();
                        file = new File(PathM + ".png");
                        ImageIO.write(MyImage.getImage(), "png", file);
                    } catch (IOException err) {
                        JOptionPane.showMessageDialog(null, "error");
                    }

                }
            }
            if (e.getSource().equals(Box)) {
                String H = Box.getSelectedItem().toString();
                if (H.equals("Mandelbrot")) {
                    MyGenerator = MyGeneratorM;
                }
                if (H.equals("Tricorn")) {
                    MyGenerator = MyGeneratorT;
                }
                if (H.equals("BurningShip")) {
                    MyGenerator = MyGeneratorB;
                }
                MyGenerator.getInitialRange(MyRange);
            }
            drawFractal();
        }
    }
}