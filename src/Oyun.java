import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

class Ates {

    private int x;
    private int y;

    public Ates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}


public class Oyun extends JPanel implements KeyListener, ActionListener {

    Timer timer = new Timer(1, this);

    private int gecen_sure = 0;
    private int harcanan_ates = 0;

    private BufferedImage image, enemy;

    private ArrayList<Ates> atesler = new ArrayList<Ates>();

    private int atesdirY = 5;

    private int topX = 0;

    private int topdirX = 2;

    private int uzayGemisiX = 0;

    private int dirUzayX = 20;

    public boolean kontrolEt() {

        for (Ates ates : atesler) {

            if (new Rectangle(ates.getX(), ates.getY(), 10, 20).intersects(new Rectangle(topX, 0, 10, 20))) {
                return true;
            }
        }
        return false;
    }


    public Oyun() {
        try {
            image = ImageIO.read(new FileImageInputStream(new File("uzaygemisi2.png")));
            enemy = ImageIO.read(new FileImageInputStream(new File("enemy.png")));
        } catch (IOException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        }
        setBackground(Color.BLACK);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        gecen_sure += 20;
        g.drawImage(enemy, topX, 0, enemy.getWidth() / 20, enemy.getHeight() / 20, this);
        g.drawImage(image, uzayGemisiX, 490, image.getWidth() / 10, image.getHeight() / 10, this);
        atesler.removeIf(ates -> ates.getY() < 0);
        g.setColor(Color.RED);

        for (Ates ates : atesler) {
            g.fillRect(ates.getX(), ates.getY(), 10, 20);
        }

        if (kontrolEt()) {

            timer.stop();
            String message = "KAZANDINIZ!\n" +
                    "Harcadığınız Ateş: " + harcanan_ates +
                    "\nGeçen Süre: " + gecen_sure / 1000.0 + " saniye";
            JOptionPane.showMessageDialog(this, message);
            System.exit(0);

        }
    }


    @Override
    public void repaint() {
        super.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();

        if (c == KeyEvent.VK_LEFT) {
            if (uzayGemisiX <= 0) {
                uzayGemisiX = 0;
            } else {
                uzayGemisiX -= dirUzayX;
            }

        } else if (c == KeyEvent.VK_RIGHT) {
            if (uzayGemisiX >= 740) {
                uzayGemisiX = 740;
            } else {
                uzayGemisiX += dirUzayX;
            }
        } else if (c == KeyEvent.VK_CONTROL) {
            atesler.add(new Ates(uzayGemisiX + 15, 470));
            harcanan_ates++;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Ates ates : atesler) {
            ates.setY(ates.getY() - atesdirY);
        }
        topX += topdirX;
        if (topX >= 750) {
            topdirX = -topdirX;
        }
        if (topX <= 0) {
            topdirX = -topdirX;
        }
        repaint();

    }

}
