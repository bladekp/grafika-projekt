package virtualcamera;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 *
 * @author bladekp
 */
public class WirtualnaKamera extends JFrame {
    
    static Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static JTextField TF;
	
    public static void main(String[] args){
        JFrame F = new JFrame();
	Ekran ScreenObject = new Ekran();
	F.add(ScreenObject);
	F.setUndecorated(true);
	F.setSize(ScreenSize);
        F.setVisible(true);
        F.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
}
