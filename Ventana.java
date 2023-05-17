
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JLabel;





public class Ventana extends JFrame implements Runnable, KeyListener{

    private int width, height;
    private int widthP, heightP;
    private int x, y;
    private MyGraphicsComp panel;
    private Thread loop;
    private JLabel label;

    public Ventana(){
        width = 1000;
        height = 750;
        widthP = width;
        heightP = height-50;
        x = 0;
        y = 50;
        label = new JLabel();
        panel = new MyGraphicsComp(widthP, heightP, x, y);
        setFrameSettings();
        setVisible(true);
        this.setLabel();
        this.addKeyListener(this);
        this.add(panel);
        this.loop = new Thread(panel);
        loop.start();
        this.loop = new Thread(this);
        loop.start();
    }

    
    public void setFrameSettings(){
        this.setLayout(null);
        this.setBounds(50, 50 , width, height);
        this.setTitle("myGraphics");
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        
    }

    public void setLabel(){
        this.label.setBounds(25, 25, 100, 20);
        this.label.setText("Score: ");
        this.label.setVisible(true);
        this.add(label);
    }
    public void updateLabel(){
        label.setText("Score: "+panel.getScore());
    }
    public static void main(String[] args) {
        new Ventana();
        

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Se ejecuta cuando se presiona una tecla
        if(e.getKeyCode()==KeyEvent.VK_A){
            panel.setLookingRight(true);
            panel.setWalkings(true);
        }
        if(e.getKeyCode()==KeyEvent.VK_D){
            panel.setLookingRight(false);
            panel.setWalkings(true);
        }
        if(e.getKeyCode()==KeyEvent.VK_W){
            panel.initJumping();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Se ejecuta cuando una tecla es liberada
        if(e.getKeyCode()==KeyEvent.VK_D||e.getKeyCode()==KeyEvent.VK_A){
            
            panel.setWalkings(false);
        }
    }


    @Override
    public void run() {
        while(true){
            updateLabel();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        }
        
    }
}

