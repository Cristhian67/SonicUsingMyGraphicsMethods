import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import javax.lang.model.util.ElementScanner14;
import javax.script.CompiledScript;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MyGraphicsComp extends JPanel implements Runnable{

    private int width, height;
    private int Px, Py;
    private int x, y;
    private int yj;
    private int xmin, ymin, xmax, ymax;
    private int yFloor;
    private int yColi;
    private int score;
    private double acc;
    private BufferedImage buffer;
    private BufferedImage bufferBackground1;
    private BufferedImage pixelBuffer;
    private boolean LookingRight;
    private boolean jumping;
    private boolean falling;
    private boolean walking;
    private boolean ring1;
    private boolean ring2;
    private boolean ring3;
    private static Color blueBody = new Color(16, 108, 190);
    private static Color skinColor = new Color(221, 190, 125);
    private static Color glovesColor = new Color(190, 190, 190);
    private static Color redShoes = new Color(189, 17, 61);
    




    public MyGraphicsComp(int width, int height, int Px, int Py) {
        this.width = width;
        this.height = height;
        this.Px = Px;
        this.Py = Py;
        this.buffer = null;
        this.xmin = 0;
        this.ymin = 0;
        this.xmax = width-1;
        this.ymax = height-1;
        this.x = width-60;
        this.y = 100;
        this.yj = 0;
        this.yFloor = 600;
        this.yColi = yFloor;
        this.acc = 0;
        this.score = 0;
        this.ring1 = true;
        this.ring2 = true;
        this.ring3 = true;
        this.LookingRight = false;
        this.jumping = false;
        this.walking = false;
        this.pixelBuffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        this.buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.bufferBackground1 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Thread soundtrack = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    // Obtén un Clip de audio
                    Clip clip = AudioSystem.getClip();
        
                    // Carga el archivo de audio desde una ubicación
                    javax.sound.sampled.AudioInputStream inputStream = AudioSystem.getAudioInputStream( 
                        MyGraphicsComp.class.getResourceAsStream("soundtrack.wav"));
        
                    // Abre el Clip de audio y carga los datos del archivo
                    clip.open(inputStream);
        
                    // Reproduce el audio
                    clip.start();
        
                    // Espera hasta que termine la reproducción
                    while (!clip.isRunning())
                        Thread.sleep(10);
                    while (clip.isRunning())
                        Thread.sleep(10);
        
                    // Cierra el Clip de audio
                    clip.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
        });
        soundtrack.start();
        setPanelSettings();
        setBuffer();

    }

    public void setPanelSettings() {
        Color blueSky = new Color(133, 246, 234);
        this.setBounds(Px, Py, width, height);
        this.setBackground( blueSky);
        this.setLayout(null);
        this.setVisible(true);
        
    }
    
    public int drawBackground(BufferedImage image){
        buffer = null;
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        buffer.getGraphics().drawImage(image, 0, 0, this);
        
        return 1;
    }
    public void ringSound(){
        Thread ringsound = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    // Obtén un Clip de audio
                    Clip clip = AudioSystem.getClip();
        
                    // Carga el archivo de audio desde una ubicación
                    javax.sound.sampled.AudioInputStream inputStream = AudioSystem.getAudioInputStream( 
                        MyGraphicsComp.class.getResourceAsStream("ring.wav"));
        
                    // Abre el Clip de audio y carga los datos del archivo
                    clip.open(inputStream);
        
                    // Reproduce el audio
                    clip.start();
        
                    // Espera hasta que termine la reproducción
                    while (!clip.isRunning())
                        Thread.sleep(10);
                    while (clip.isRunning())
                        Thread.sleep(10);
        
                    // Cierra el Clip de audio
                    clip.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
        });
        ringsound.start();
    }
    public int setBuffer(){
        Color green1 = new Color(33, 189, 25);
        Color green2 = new Color(35, 135, 100 );
        Color green3 = new Color(35, 115, 110 );
        Color blueSea = new Color(67, 83, 184);
        Color blueSky = new Color(0, 132, 225 );
        Color dirt = new Color(181, 153, 108 );
        myDrawLine(0, yFloor, width, yFloor, 0, green1);
        myDrawLine(0, yFloor-200, width, yFloor-200, 0, blueSky);
        FloodFill(1, yFloor+1, green1);
        FloodFill(1, yFloor-1, blueSea);
        FloodFill(1, yFloor-502, blueSky);
        myDrawLine(510, yFloor-200, 546, yFloor-300, 0, green2);
        myDrawLine(546, yFloor-300, 589, yFloor-200, 0, green2);
        FloodFill(546, yFloor-295, green2);
        myDrawLine(751, yFloor-200, 810, yFloor-300, 0, green2);
        myDrawLine(810, yFloor-300, 880, yFloor-200, 0, green2);
        FloodFill(810, yFloor-295, green2);

        myDrawRectangle(0, yFloor-300, 400, yFloor, 0, green3);
        FloodFill(5, yFloor-5, green3);
        FloodFill(5, yFloor-280, green3);
        

        

        myDrawRectangle(500, yFloor-210, width-200, yFloor-190, 0, dirt);
        FloodFill(503, yFloor-208, dirt);
        FloodFill(543, yFloor-208, dirt);
        FloodFill(603, yFloor-208, dirt);
        FloodFill(780, yFloor-208, dirt);
        FloodFill(503, yFloor-198, dirt);

        myDrawRectangle(700, yFloor-100, width-1, yFloor-80, 0, green3);
        FloodFill(703, yFloor-93, green3);

        bufferBackground1 = buffer;
        buffer = null;
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        return 1;
    }

    public int getScore(){
        return this.score;
    }
    public void drawRings(){
        if(ring1){
            myDrawCircunference(50, yFloor-330, 15, 5, Color.YELLOW);
        }
        if(ring2){
            myDrawCircunference(100, yFloor-330, 15, 5, Color.YELLOW);
        }
        if(ring3){
            myDrawCircunference(150, yFloor-330, 15, 5, Color.YELLOW);
        }
    }

    public void checkRings(){
        if((x-50<=40&&y-(yFloor-330)<50)&&ring1){
            ring1=false;
            ringSound();
            score=score+100;
            
        }
        if((x-100<=40&&y-(yFloor-330)<50)&&ring2){
            ring2=false;
            ringSound();
            score=score+100;
        }
        if((x-150<=40&&y-(yFloor-330)<50)&&ring3){
            ring3=false;
            ringSound();
            score=score+100;
        }
    }

    public void updateCollisions(){
        if(x+50>0&&x-50<400&&y<yFloor-300){
            yColi=yFloor-300;
            
            
        }
        else if(x+50>500 && x-50 < width-200 && y < yFloor-190 && y> yFloor-250){
            yColi= yFloor-210;
            
            
        }
        else if(x+50>700 && x-50<width-1 && y<yFloor-80 && y > yFloor-110){
            yColi=yFloor-100;
            
            
        }
        else{
            falling=true;
            yColi=yFloor;
            
            
            
        }
        
    }

    public int createSonicRepose(int x, int y){
        if(walking) {
            myDrawCircunference(x, y-36, 35, 0, blueBody);
            FloodFill(x, y-37, blueBody);
            FloodFill(x, y-4, blueBody);
            FloodFill(x, y-70, blueBody);
            FloodFill(x-30, y-37, blueBody);
            FloodFill(x+30, y-37, blueBody);
        }else{
        if(LookingRight){
            myDrawElipse(x-5, y-50, 14, 8, skinColor);//
            FloodFill(x-5, y-44, skinColor);//
            myDrawElipse(x-13, y-60, 6, 8, Color.white);//
            FloodFill(x-13, y-60, Color.WHITE);//
            myDrawElipse(x+1, y-60, 6, 8, Color.white);//
            FloodFill(x+1, y-60, Color.white);//
            myDrawCircunference(x-2, y-34, 8, 0, skinColor);//
            FloodFill(x-2, y-34, skinColor);//
            myDrawCircunference(x-3, y-28, 8, 0, glovesColor);//
            FloodFill(x-5, y-26, glovesColor);//
            myDrawCircunference(x+14, y-28, 8, 0, glovesColor);//
            FloodFill(x+14, y-28, glovesColor);//
            myDrawElipse(x+7, y-10, 7, 4, glovesColor);//
            FloodFill(x+12, y-10, glovesColor);//
            myDrawElipse(x-1, y-12, 6, 3, glovesColor);//
            FloodFill(x-1, y-12, glovesColor);//

            myDrawLine(x-17, y-67, x-9, y-75, 0, blueBody);//
            myDrawLine(x-9, y-75, x+15, y-81, 0, blueBody);//
            myDrawLine(x+15, y-81, x+23, y-81, 0, blueBody);//
            myDrawLine(x+23, y-81, x+33, y-75, 0, blueBody);//
            myDrawLine(x+33, y-76, x+21, y-69, 0, blueBody);//
            myDrawLine(x+21, y-69, x+38, y-58, 0, blueBody);//
            myDrawLine(x+38, y-58, x+19, y-47, 0, blueBody);//
            myDrawLine(x+19, y-47, x+28, y-37, 0, blueBody);//
            myDrawLine(x+28, y-37, x+9, y-46, 0, blueBody);//
            myDrawLine(x+9, y-46, x+21, y-34, 0, blueBody);//

            myDrawLine(x+11, y-22, x+11, y-13, 0, blueBody);//
            myDrawLine(x+14, y-9, x+18, y, 0, redShoes);//
            myDrawLine(x+18, y, x, y, 0, redShoes);//
            myDrawLine(x, y, x-21, y, 0, redShoes);//
            myDrawLine(x, y, x+2, y-8, 0, redShoes);//
            myDrawLine(x-21, y, x-21, y-6, 0, redShoes);//
            myDrawLine(x-21, y-6, x-6, y-12, 0, redShoes);//

            myDrawLine(x+5, y-14, x+4, y-28, 0, blueBody);//
            //myDrawLine(x+2, y-15, x+2, y-27, 0, blueBody);//
            myDrawLine(x-4, y-15, x-4, y-26, 0, blueBody);//

            myDrawLine(x-9, y-52, x-15, y-55, 4, Color.BLACK);//
            myDrawLine(x-11, y-57, x-11, y-61, 3, Color.BLACK);//
            myDrawLine(x, y-55, x, y-61, 3, Color.BLACK);//
            
            FloodFill(x+26, y-76, blueBody);//
            FloodFill(x+35, y-58, blueBody);//
            FloodFill(x+20, y-43, blueBody);//
            FloodFill(x+15, y-38, blueBody);//
            FloodFill(x+9, y-18, blueBody);//
            FloodFill(x, y-18, blueBody);//
            FloodFill(x, y-22, blueBody);//
            FloodFill(x+16, y-1, redShoes);//
            FloodFill(x-1, y-1, redShoes);//
        }else{
            myDrawElipse(x+5, y-50, 14, 8, skinColor);
            FloodFill(x-1, y-50, skinColor);
            myDrawElipse(x+13, y-60, 6, 8, Color.white);
            FloodFill(x+8, y-60, Color.WHITE);
            myDrawElipse(x-1, y-60, 6, 8, Color.white);
            FloodFill(x-4, y-60, Color.white);
            FloodFill(x-1, y-54, Color.white);
            myDrawCircunference(x+2, y-34, 8, 0, skinColor);
            FloodFill(x-4, y-34, skinColor);
            myDrawCircunference(x+3, y-28, 8, 0, glovesColor);
            FloodFill(x-4, y-26, glovesColor);
            myDrawCircunference(x-14, y-28, 8, 0, glovesColor);
            FloodFill(x-20, y-28, glovesColor);
            myDrawElipse(x-7, y-10, 7, 4, glovesColor);
            FloodFill(x-12, y-10, glovesColor);
            myDrawElipse(x+1, y-12, 6, 3, glovesColor);
            FloodFill(x+1, y-12, glovesColor);

            myDrawLine(x+17, y-67, x+9, y-75, 0, blueBody);
            myDrawLine(x+9, y-75, x-15, y-81, 0, blueBody);
            myDrawLine(x-15, y-81, x-23, y-81, 0, blueBody);
            myDrawLine(x-23, y-81, x-33, y-75, 0, blueBody);
            myDrawLine(x-33, y-76, x-21, y-69, 0, blueBody);
            myDrawLine(x-21, y-69, x-38, y-58, 0, blueBody);
            myDrawLine(x-38, y-58, x-19, y-47, 0, blueBody);
            myDrawLine(x-19, y-47, x-28, y-37, 0, blueBody);
            myDrawLine(x-28, y-37, x-9, y-46, 0, blueBody);
            myDrawLine(x-9, y-46, x-21, y-34, 0, blueBody);

            myDrawLine(x-11, y-22, x-11, y-13, 0, blueBody);
            myDrawLine(x-14, y-9, x-18, y, 0, redShoes);
            myDrawLine(x-18, y, x, y, 0, redShoes);
            myDrawLine(x, y, x+21, y, 0, redShoes);
            myDrawLine(x, y, x-2, y-8, 0, redShoes);
            myDrawLine(x+21, y, x+21, y-6, 0, redShoes);
            myDrawLine(x+21, y-6, x+6, y-12, 0, redShoes);

            myDrawLine(x-5, y-14, x-4, y-28, 0, blueBody);
            //myDrawLine(x-2, y-15, x-2, y-27, 0, blueBody);
            myDrawLine(x+4, y-15, x+4, y-26, 0, blueBody);

            myDrawLine(x+9, y-52, x+15, y-55, 4, Color.BLACK);
            myDrawLine(x+11, y-57, x+11, y-61, 3, Color.BLACK);
            myDrawLine(x, y-55, x, y-61, 3, Color.BLACK);
            
            FloodFill(x-26, y-76, blueBody);
            FloodFill(x-35, y-58, blueBody);
            FloodFill(x-20, y-43, blueBody);
            FloodFill(x-15, y-38, blueBody);
            FloodFill(x-9, y-18, blueBody);
            FloodFill(x, y-18, blueBody);
            //FloodFill(x, y-22, blueBody);
            FloodFill(x-16, y-1, redShoes);
            FloodFill(x+1, y-1, redShoes);
        }}
        
        


        return 1;
    }
    public void setWalkings(boolean walking) {
        this.walking = walking;
    }
    public int sonicWalking(){
        int velocity = 5;
        if(!walking){
            return 0;
        }
        if(x-49<xmin){
            x++;
            return 0;
        }
        if(x+49>xmax){
            x--;
            return 0;
        }
        if(!LookingRight){
            x+=1*velocity;
        }
        else{
            x-=1*velocity;
        }
        

        return 1;
    }
    public void sonicFalling(){
        if((y<yColi-1)&&!jumping&&falling){
        this.acc = 2;
        this.acc = this.acc * this.acc;
        this.y=(int) Math.round(this.y+(8.0*this.acc));
        
        }
        if(this.y>this.yColi-1){
            acc = 0.0;
            this.y=this.yColi-1;
            falling = false;
        }
    }
    public void sonicJumping(){
        if(jumping){
            this.falling = false;
            this.acc = 16;
        this.acc = this.acc / 2;
        this.y=(int) Math.round(this.y-(2.0*this.acc));
        if(this.y<=this.yj-160){
            this.jumping = false;
            this.falling = true;
        }}
        
    }
    public void initJumping(){
        this.jumping = true;
    }
    public void setLookingRight(boolean right){
        this.LookingRight = right;
    }
    public int myDrawElipse(int Xc, int Yc, int rx, int ry, Color c){
        int x = 0;
        int y = 0;
        if((Xc+rx< xmin&&Xc-rx < xmin) || (Yc+ry<ymin&&Yc-ry < ymin)){
            
            return 0;
        }
        if((Xc+rx > xmax&&Xc-rx > xmax) || (Yc+ry > ymax&&Yc-ry > ymax)){
            
            return 0;
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            
            e.printStackTrace();
        }
        
        while(x<= rx){
            y = (int) Math.round(ry*Math.sqrt(1-((double) x * x )/ (rx*rx)));
            putPixel(Xc+x, Yc+y, c);
            putPixel(Xc-x, Yc-y, c);
            putPixel(Xc-x, Yc+y, c);
            putPixel(Xc+x, Yc-y, c);
            x++;

        }
        while(y<= ry){
            x = (int) Math.round(rx*Math.sqrt(1-((double) y * y )/ (ry*ry)));
            putPixel(Xc+x, Yc+y, c);
            putPixel(Xc-x, Yc-y, c);
            putPixel(Xc-x, Yc+y, c);
            putPixel(Xc+x, Yc-y, c);
            y++;
            

        }
        return 1;
    }
    
    public int myDrawCircunference(int Xc, int Yc, int radius , int thickness, Color c){
        int x = 0;
        int y = radius;
        double pk = 3-(2*radius);
        if((Xc+radius< xmin&&Xc-radius < xmin) || (Yc+radius<ymin&&Yc-radius < ymin)){
            
            return 0;
        }
        if((Xc+radius > xmax&&Xc-radius > xmax) || (Yc+radius > ymax&&Yc-radius > ymax)){
            
            return 0;
        }
        pk = (double) 5/4-radius;
        while(x<=y){
            putPixel(Xc+x, Yc+y, c);
            putPixel(Xc+y, Yc+x, c);
            putPixel(Xc-x, Yc-y, c);
            putPixel(Xc-y, Yc-x, c);
            putPixel(Xc-x, Yc+y, c);
            putPixel(Xc-y, Yc+x, c);
            putPixel(Xc+x, Yc-y, c);
            putPixel(Xc+y, Yc-x, c);
            putPixel(Xc+x, Yc+y, c);
            putPixel(Xc+y, Yc+x, c);
            putPixel(Xc-x, Yc-y, c);
            putPixel(Xc-y, Yc-x, c);
            putPixel(Xc-x, Yc+y, c);
            putPixel(Xc-y, Yc+x, c);
            putPixel(Xc+x, Yc-y, c);
            putPixel(Xc+y, Yc-x, c);
            for(int j = 0; j<thickness ; j++){

                putPixel(Xc+x+j, Yc+y+j, c);
                putPixel(Xc+y+j, Yc+x+j, c);
                putPixel(Xc-x+j, Yc-y+j, c);
                putPixel(Xc-y+j, Yc-x+j, c);
                putPixel(Xc-x+j, Yc+y+j, c);
                putPixel(Xc-y+j, Yc+x+j, c);
                putPixel(Xc+x+j, Yc-y+j, c);
                putPixel(Xc+y+j, Yc-x+j, c);
            }
            for(int j = 0; j<thickness ; j++){

                putPixel(Xc+x, Yc+y+j, c);
                putPixel(Xc+y, Yc+x+j, c);
                putPixel(Xc-x, Yc-y+j, c);
                putPixel(Xc-y, Yc-x+j, c);
                putPixel(Xc-x, Yc+y+j, c);
                putPixel(Xc-y, Yc+x+j, c);
                putPixel(Xc+x, Yc-y+j, c);
                putPixel(Xc+y, Yc-x+j, c);
            }
            for(int j = 0; j<thickness ; j++){

                putPixel(Xc+x, Yc+y, c);
                putPixel(Xc+y+j, Yc+x, c);
                putPixel(Xc-x+j, Yc-y, c);
                putPixel(Xc-y+j, Yc-x, c);
                putPixel(Xc-x+j, Yc+y, c);
                putPixel(Xc-y+j, Yc+x, c);
                putPixel(Xc+x+j, Yc-y, c);
                putPixel(Xc+y+j, Yc-x, c);
            }

            if(pk<0){
                
                
                pk = (double) pk + 2*(x) +3;
                
                
            }
            else if(pk>=0){
                
                y--;
                
                pk = (double) pk + 2*(x) - 2*(y) +5;
                
            }
            x++;
            
            
        }

        return 1;
    }

    public int myDrawDashedCircunference(int Xc, int Yc, int radius, int thickness,String mask, Color c){

        int x = 0;
        int y = radius;
        int iM = 0;
        int numMask = mask.length();
        double pk = 3-(2*radius);
        if((Xc+radius< xmin&&Xc-radius < xmin) || (Yc+radius<ymin&&Yc-radius < ymin)){
            
            return 0;
        }
        if((Xc+radius > xmax&&Xc-radius > xmax) || (Yc+radius > ymax&&Yc-radius > ymax)){
            
            return 0;
        }
        pk = (double) 5/4-radius;
        while(x<=y){
            if(iM==numMask){
                iM=0;
            }
            if(mask.charAt(iM)=='1'){
            putPixel(Xc+x, Yc+y, c);
            putPixel(Xc+y, Yc+x, c);
            putPixel(Xc-x, Yc-y, c);
            putPixel(Xc-y, Yc-x, c);
            putPixel(Xc-x, Yc+y, c);
            putPixel(Xc-y, Yc+x, c);
            putPixel(Xc+x, Yc-y, c);
            putPixel(Xc+y, Yc-x, c);
            for(int j = 0; j<thickness ; j++){

                putPixel(Xc+x, Yc+y+j, c);
                putPixel(Xc+y, Yc+x+j, c);
                putPixel(Xc-x, Yc-y+j, c);
                putPixel(Xc-y, Yc-x+j, c);
                putPixel(Xc-x, Yc+y+j, c);
                putPixel(Xc-y, Yc+x+j, c);
                putPixel(Xc+x, Yc-y+j, c);
                putPixel(Xc+y, Yc-x+j, c);

                putPixel(Xc+x+j, Yc+y, c);
                putPixel(Xc+y+j, Yc+x, c);
                putPixel(Xc-x+j, Yc-y, c);
                putPixel(Xc-y+j, Yc-x, c);
                putPixel(Xc-x+j, Yc+y, c);
                putPixel(Xc-y+j, Yc+x, c);
                putPixel(Xc+x+j, Yc-y, c);
                putPixel(Xc+y+j, Yc-x, c);
            }
            for(int j = 0; j<thickness ; j++){

                putPixel(Xc+x, Yc+y+j, c);
                putPixel(Xc+y, Yc+x+j, c);
                putPixel(Xc-x, Yc-y+j, c);
                putPixel(Xc-y, Yc-x+j, c);
                putPixel(Xc-x, Yc+y+j, c);
                putPixel(Xc-y, Yc+x+j, c);
                putPixel(Xc+x, Yc-y+j, c);
                putPixel(Xc+y, Yc-x+j, c);
            }
            for(int j = 0; j<thickness ; j++){

                putPixel(Xc+x, Yc+y, c);
                putPixel(Xc+y+j, Yc+x, c);
                putPixel(Xc-x+j, Yc-y, c);
                putPixel(Xc-y+j, Yc-x, c);
                putPixel(Xc-x+j, Yc+y, c);
                putPixel(Xc-y+j, Yc+x, c);
                putPixel(Xc+x+j, Yc-y, c);
                putPixel(Xc+y+j, Yc-x, c);
            }
            }
            
            

            if(pk<0){
                
                
                pk = (double) pk + 2*(x) +3;
                
                
            }
            else if(pk>=0){
                
                y--;
                
                pk = (double) pk + 2*(x) - 2*(y) +5;
                
            }
            x++;
            iM++;
            
        }
        return 1;
    }
    
    public void myDrawRectangle(int x0, int y0, int x1, int y1, int thickness, Color c){
        myDrawLine(x0, y0, x0, y1, thickness, c);
        myDrawLine(x0, y0, x1, y0, thickness, c);
        myDrawLine(x1, y0, x1, y1, thickness, c);
        myDrawLine(x0, y1, x1, y1, thickness , c);
    }
    


    public int myDrawDashedLine(int x0, int y0, int x1, int y1,int thickness , String mask, Color c){
        
        double dx= (x1-x0);
        double dy= (y1-y0) ;
        //comprobacion de la zona dibujable (x y y min y max)
        //double relXY = dx/dy;
    
        double relXY = dx/dy;
        double relYX = dy/dx;
        if(y0 < ymin && y1 < ymin){
            return 0;
        }
        if(y0 > ymax && y1 > ymax){
            return 0;
        }
        if(x0 < xmin && x1 < xmin){
            return 0;
        }
        if(x0 > xmax && x1 > xmax){
            return 0;
        }

        if (y0 < ymin){
            
            y0 = ymin;

            if(dy!=0&& dx!=0){
            x0=(int) Math.round(relXY*y0);
            }
        }
        if (y0 > ymax){
            
            
            y0 = ymax;
            if(dy!=0&& dx!=0){
            x0=(int) Math.round(relXY*y0);
            }
        }
        if (y1 < ymin){
            
            
            y1 = ymin;
            if(dy!=0&& dx!=0){
            x1=(int) Math.round(relXY*y1);
            }

        }
        if (y1 > ymax){
            
            
            y1 = ymax;
            if(dy!=0&& dx!=0){
            x1=(int) Math.round(relXY*y1);
            }
        }

        if (x0 < xmin){
            
            
            x0 = xmin;

            if(dx!=0&& dy!=0){
            y0=(int) Math.round(relYX*x0);
        }
            
        }
        if (x0 > xmax){
            
            
            x0 = xmax;
            if(dx!=0&& dy!=0){
                
            y0=(int) Math.round(relYX*x0);
            }
        }
        if (x1 < xmin){
            
            
            x1 = xmin;
            if(dx!=0&& dy!=0){
                
                
            y1=(int) Math.round(relYX*x1);
            }

        }
        
        if (x1 > xmax){
            
            
            x1 = xmax;
            if(dx!=0&& dy!=0){
            y1=(int) Math.round(relYX*x1);
        }
        }

        dx= x1-x0 ;
        dy= y1-y0 ;
        float x = 0;
        float y = 0;
        int steps = 0;
        int numMask = mask.length();
        int iM = 0;
        float xinc = 0;
        float yinc = 0;
        int xf=0, yf=0;
        
        if(Math.abs( dx ) > Math.abs( dy )){
            
            steps =(int) Math.abs(dx);
        }
        else{
            steps =(int) Math.abs(dy);
        }

        xinc =(float) dx / steps;
        yinc = (float) dy / steps;
        //System.out.println(+dx+" "+dy+" "+yinc+" "+steps);
        x = x0;
        y = y0;
        
        for(int k = 1; k<steps ; k++){
            //System.out.println("Pintando Linea "+x+" "+y+" ");
            if( iM >= numMask){
                iM=0;
            }
            x = x + xinc;
            y = y + yinc;
            if(mask.charAt(iM)=='1'){
                
                xf=(int) Math.round(x);
                yf = (int) Math.round(y);
                putPixel(xf, yf, c);
                for(int j = 0; j<thickness ; j++){
                    putPixel((xf+j), (yf), c);
                    putPixel((xf), (yf+j), c);
                    putPixel((xf+j), (yf+j), c);
                }
                //System.out.println(mask.charAt(iM));
            }
            iM++;
        }
        return 1;
    }
    
    public int myDrawLine(int x0, int y0, int x1, int y1, int thickness, Color c){
        double dx= x1-x0;
        double dy= y1-y0 ;
        double relXY = dx/dy;
        double relYX = dy/dx;
        

        if(y0 < ymin && y1 < ymin){
            return 0;
        }
        if(y0 > ymax && y1 > ymax){
            return 0;
        }
        if(x0 < xmin && x1 < xmin){
            return 0;
        }
        if(x0 > xmax && x1 > xmax){
            return 0;
        }
        if (y0 < ymin){
            
            y0 = ymin;

            if(dy!=0&& dx!=0){
            x0=(int) Math.round(relXY*y0);
            }
        }
        if (y0 > ymax){
            
            
            y0 = ymax;
            if(dy!=0&& dx!=0){
            x0=(int) Math.round(relXY*y0);
            }
        }
        if (y1 < ymin){
            
            
            y1 = ymin;
            if(dy!=0&& dx!=0){
            x1=(int) Math.round(relXY*y1);
            }

        }
        if (y1 > ymax){
            
            
            y1 = ymax;
            if(dy!=0&& dx!=0){
            x1=(int) Math.round(relXY*y1);
            }
        }

        if (x0 < xmin){
            
            
            x0 = xmin;

            if(dx!=0&& dy!=0){
            y0=(int) Math.round(relYX*x0);
        }
            
        }
        if (x0 > xmax){
            
            
            x0 = xmax;
            if(dx!=0&& dy!=0){
                
            y0=(int) Math.round(relYX*x0);
            }
        }
        if (x1 < xmin){
            
            
            x1 = xmin;
            if(dx!=0&& dy!=0){
                
                
            y1=(int) Math.round(relYX*x1);
            }

        }
        
        if (x1 > xmax){
            
            
            x1 = xmax;
            if(dx!=0&& dy!=0){
            y1=(int) Math.round(relYX*x1);
        }
        }

        dx= x1-x0 ;
        dy= y1-y0 ;
        float x = 0;
        float y = 0;
        int steps = 0;
        float xinc = 0;
        float yinc = 0;
        int xf=0, yf=0;
        putPixel(x0, y0, c);
        
        if(Math.abs( dx ) > Math.abs( dy )){
            
            steps =(int) Math.abs(dx);
        }
        else{
            steps =(int) Math.abs(dy);
        }

        xinc =(float) dx / steps;
        yinc = (float) dy / steps;
        //System.out.println(+dx+" "+dy+" "+yinc+" "+steps);
        x = x0;
        y = y0;
        
        
        for(int k = 1; k<=steps ; k++){
            //System.out.println("Pintando Linea "+x+" "+y+" ");
            x =  x + xinc;
            y =  y + yinc;
            xf=(int) Math.round(x);
            yf = (int) Math.round(y);
            putPixel(xf, yf, c);
            for(int j = 0; j<thickness ; j++){
            putPixel((xf+j), (yf), c);
            putPixel((xf), (yf+j), c);
            }
        }
        
    return 1;
    }

    public void scanLine(int x, int y, Color c){
        int yk = 0;
        int xk = 0;
        int yj = 0;
        int xj = 0;
        int fyk = 0;
        int fxk = 0;
        int fyj = 0;
        int fxj = 0;
        int[] eq, com, it;
        int red, green, blue;
        red = c.getRed();
        green = c.getGreen();
        blue = c.getBlue();
        it = new int[3];
        eq = new int[3];
        com = new int[3];
        com[0] = buffer.getColorModel().getRed(buffer.getRGB(x,y));
        com[1] = buffer.getColorModel().getGreen(buffer.getRGB(x, y));
        com[2] = buffer.getColorModel().getBlue(buffer.getRGB(x, y));
        eq[0] = red;
        eq[1] = green;
        eq[2] = blue;
        putPixel(x, y, c);
        xk=1; 
        xj=-1;
            while ((x+xk)<width || x+xj>0){
                it[0] = buffer.getColorModel().getRed(buffer.getRGB(x+xk,y+yk));
                it[1] = buffer.getColorModel().getGreen(buffer.getRGB(x+xk, y+yk));
                it[2] = buffer.getColorModel().getBlue(buffer.getRGB(x+xk, y+yk));
                if(Arrays.equals(it,com)){
                    
                    putPixel(x+xk, y+yk, c);
                    xk++;
                    if(x+xk>width){
                        xk --;
                    }
                }
                else{
                    fxk = 1;
                }
                it[0] = buffer.getColorModel().getRed(buffer.getRGB(x+xj,y+yk));
                it[1] = buffer.getColorModel().getGreen(buffer.getRGB(x+xj, y+yk));
                it[2] = buffer.getColorModel().getBlue(buffer.getRGB(x+xj, y+yk));
                if(Arrays.equals(it,com)){
                    
                    putPixel(x+xj, y+yk, c);
                    
                    xj--;
                    if(x+xj<0){
                        xj++;
                    }
                }
                else{
                    fxj = 1;
                }
                
                if(fxj==1&&fxk==1)
                {
                    break;
                }
                
    
            }
            yk=1;
            yj=-1;
            xk=0; 
            xj=0;
            while ((y+yk)<height || y+yj>0){
                it[0] = buffer.getColorModel().getRed(buffer.getRGB(x,y+yk));
                it[1] = buffer.getColorModel().getGreen(buffer.getRGB(x, y+yk));
                it[2] = buffer.getColorModel().getBlue(buffer.getRGB(x, y+yk));
                if(Arrays.equals(it,com)){
                    xk=1; 
                    xj=-1;
                    fxk=0;
                    fxj=0;
                    putPixel(x, y+yk, c);
                    while ((x+xk)<width || x+xj>0){
                        
                        it[0] = buffer.getColorModel().getRed(buffer.getRGB(x+xk,y+yk));
                        it[1] = buffer.getColorModel().getGreen(buffer.getRGB(x+xk, y+yk));
                        it[2] = buffer.getColorModel().getBlue(buffer.getRGB(x+xk, y+yk));
                        if(Arrays.equals(it,com)){
                            
                            putPixel(x+xk, y+yk, c);
                            xk++;
                            if(x+xk>=width){
                                xk --;
                            }
                        }
                        else{
                            fxk = 1;
                        }
                        it[0] = buffer.getColorModel().getRed(buffer.getRGB(x+xj,y+yk));
                        it[1] = buffer.getColorModel().getGreen(buffer.getRGB(x+xj, y+yk));
                        it[2] = buffer.getColorModel().getBlue(buffer.getRGB(x+xj, y+yk));
                        if(Arrays.equals(it,com)){
                            
                            putPixel(x+xj, y+yk, c);
                            
                            xj--;
                            if(x+xj<0){
                                xj++;
                            }
                        }
                        else{
                            fxj = 1;
                        }
                        
                        if(fxj==1&&fxk==1)
                        {
                            
                            break;
                            
                        }
                        
            
                    }
                    yk++;
                    if(y+yk>=height){
                        yk --;
                    }
                }
                else{
                    fyk = 1;
                }
                xk=0; 
                xj=0;
                it[0] = buffer.getColorModel().getRed(buffer.getRGB(x,y+yj));
                it[1] = buffer.getColorModel().getGreen(buffer.getRGB(x, y+yj));
                it[2] = buffer.getColorModel().getBlue(buffer.getRGB(x, y+yj));
                if(Arrays.equals(it,com)){
                    xk=1; 
                    xj=-1;
                    fxk=0;
                    fxj=0;
                    putPixel(x, y+yj, c);
                    while ((x+xk)<width || x+xj>0){
                        it[0] = buffer.getColorModel().getRed(buffer.getRGB(x+xk,y+yj));
                        it[1] = buffer.getColorModel().getGreen(buffer.getRGB(x+xk, y+yj));
                        it[2] = buffer.getColorModel().getBlue(buffer.getRGB(x+xk, y+yj));
                        if(Arrays.equals(it,com)){
                            
                            putPixel(x+xk, y+yj, c);
                            xk++;
                            if(x+xk>width){
                                xk --;
                            }
                        }
                        else{
                            fxk = 1;
                        }
                        it[0] = buffer.getColorModel().getRed(buffer.getRGB(x+xj,y+yj));
                        it[1] = buffer.getColorModel().getGreen(buffer.getRGB(x+xj, y+yj));
                        it[2] = buffer.getColorModel().getBlue(buffer.getRGB(x+xj, y+yj));
                        if(Arrays.equals(it,com)){
                            
                            putPixel(x+xj, y+yj, c);
                            
                            xj--;
                            if(x+xj<0){
                                xj++;
                            }
                        }
                        else{
                            fxj = 1;
                        }
                        
                        if(fxj==1&&fxk==1)
                        {
                            
                            break;
                        }
                        
            
                    }
                    yj--;
                    if(y+yj<0){
                        yj++;
                    }
                }
                else{
                    fyj = 1;
                }
                
                if(fyj==1&&fyk==1)
                {
                    
                    break;
                }
                
    
            }


    }

    public int FloodFill(int x, int y, Color c){
        int[] eq, com;
        int red, green, blue;
        red = c.getRed();
        green = c.getGreen();
        blue = c.getBlue();
        eq = new int[3];
        com = new int[3];
        if(x > xmax-1 || y > ymax-1||x < xmin || y < ymin){
            return 0;
        }
        com[0] = buffer.getColorModel().getRed(buffer.getRGB(x,y));
        com[1] = buffer.getColorModel().getGreen(buffer.getRGB(x, y));
        com[2] = buffer.getColorModel().getBlue(buffer.getRGB(x, y));
        eq[0] = red;
        eq[1] = green;
        eq[2] = blue;
        putPixel(x, y, c);
        FloodFillRec(x, y, com, eq, c);
        
        return 1;
    }
    public int FloodFillRec(int x, int y,int[] com, int[] eq, Color c){
        if((x==width-1||x<0||y==height-1||y<0)){
            return 1;
            
        }
        else{
                FloodFillD(x, y+1, com, eq, c);
                FloodFillL(x-1, y, com, eq, c);
                FloodFillU(x, y-1, com, eq, c);
                FloodFillR(x+1, y, com, eq, c);
        }
        
        return 1;
    }
    public int FloodFillR(int x, int y,int[] com, int[] eq, Color c){
        int[] it = new int[3];
        
        if((x>=width-1||x<0||y>=height-1||y<0)){
            return 1;
        }
        else{
            it[0] = buffer.getColorModel().getRed(buffer.getRGB(x,y));
            it[1] = buffer.getColorModel().getGreen(buffer.getRGB(x, y));
            it[2] = buffer.getColorModel().getBlue(buffer.getRGB(x, y));
        if(!Arrays.equals(it, eq)&& Arrays.equals(it, com))
        {
            putPixel(x, y, c);
            
            FloodFillD(x, y+1, com, eq, c);
            FloodFillU(x, y-1, com, eq, c);
            FloodFillR(x+1, y, com, eq, c);
            }
            else{
                return 1;
            }
        }
        return 1;

    }
    public int FloodFillL(int x, int y,int[] com, int[] eq, Color c){
        int[] it = new int[3];
        
        if((x>=width-1||x<0||y>=height-1||y<0)){
            return 1;
            
        }
        
        else{
        it[0] = buffer.getColorModel().getRed(buffer.getRGB(x,y));
        it[1] = buffer.getColorModel().getGreen(buffer.getRGB(x, y));
        it[2] = buffer.getColorModel().getBlue(buffer.getRGB(x, y));
        if(!Arrays.equals(it, eq) && Arrays.equals(it, com))
        {
            putPixel(x, y, c);
            
            FloodFillR(x+1, y, com, eq, c);
            FloodFillU(x, y-1, com, eq, c);
            FloodFillL(x-1, y, com, eq, c); 

            }
            else{
                return 1;
            }
        }
        return 1;

    }
    public int FloodFillU(int x, int y,int[] com, int[] eq, Color c){
        int[] it = new int[3];
        
        if((x>=width-1||x<0||y>=height-1||y<0)){
            return 1;
            
        }
        else{
            it[0] = buffer.getColorModel().getRed(buffer.getRGB(x,y));
            it[1] = buffer.getColorModel().getGreen(buffer.getRGB(x, y));
            it[2] = buffer.getColorModel().getBlue(buffer.getRGB(x, y));
        if(!Arrays.equals(it, eq) && Arrays.equals(it, com))
        {
            putPixel(x, y, c);
            
            FloodFillR(x+1, y, com, eq, c);
            FloodFillU(x, y-1, com, eq, c);
            FloodFillL(x-1, y, com, eq, c);
            
            }
            else{
                return 1;
            }
        }
        return 1;

    }
    
    public int FloodFillD(int x, int y,int[] com, int[] eq, Color c){
        int[] it = new int[3];
        
        if((x>=width-1||x<0||y>=height-1||y<0)){
            return 1;
            
        }
        else{
            it[0] = buffer.getColorModel().getRed(buffer.getRGB(x,y));
            it[1] = buffer.getColorModel().getGreen(buffer.getRGB(x, y));
            it[2] = buffer.getColorModel().getBlue(buffer.getRGB(x, y));
        if(!Arrays.equals(it, eq) && Arrays.equals(it, com) )
        {
            putPixel(x, y, c);
                FloodFillD(x, y+1, com, eq, c);
                FloodFillL(x-1, y, com, eq, c);
                FloodFillR(x+1, y, com, eq, c);

    
            }
            else{
                return 1;
            }
        }
        return 1;

    }


    public void putLayer(int x0, int y0, int x1, int y1){

        if(x0<0){
            x0 = 0;
        }
        if(y0 < 0){
            y0 = 0;
        }
        if(x1>width){
            x1 = width-1;
        }
        if(y1>height){
            y1 = height-1;
        }
        
        xmin = x0;
        ymin = y0;
        xmax = x1;
        ymax = y1;
    }


    public int putPixel(int x, int y, Color c){
        int red, green, blue;
        int[] eq;
        if(x < xmin){
            return 0;
        }
        if(x > xmax){
            return 0;
        }
        if(y < ymin){
            return 0;
        }
        if(y > ymax){
            return 0;
        }
        eq = new int[3];
        red = c.getRed();
        green = c.getGreen();
        blue = c.getBlue();
        eq[0] = red;
        eq[1] = green;
        eq[2] = blue;
        //System.out.println("Pintando Pïxel "+x+" "+y+" ");
        pixelBuffer.setRGB(0, 0, c.getRGB());
        buffer.getGraphics().drawImage(pixelBuffer, x, y, this);

        return 1;
    }
    public void Comes(){
        myDrawCircunference(410, 350, 45, 0 , Color.PINK);
        myDrawCircunference(490, 350, 45, 0 , Color.PINK);
        myDrawRectangle(410, 350, 490, 100, 0, Color.PINK);
        myDrawCircunference(450, 100, 45, 0 , Color.PINK);
        FloodFill(410+1, 350+1, Color.PINK);
        FloodFill(490+1, 350+1, Color.PINK);
        FloodFill(450+1, 100+1, Color.PINK);
        FloodFill(410-1, 350-1, Color.PINK);
        FloodFill(490-1, 350-1, Color.PINK);
        FloodFill(450-1, 100-1, Color.PINK);
        FloodFill(450-1, 100+1, Color.PINK);
        FloodFill(409-1, 100+1, Color.PINK);
        FloodFill(490+2, 100+3, Color.PINK);
        FloodFill(450, 225, Color.PINK);
        FloodFill(411, 349, Color.PINK);
        FloodFill(450, 351, Color.PINK);
        FloodFill(450, 349, Color.PINK);
        
    }

    @Override
    public void run() {
        yj=y;
        this.initJumping();
        while(true) {
            
            this.drawBackground(bufferBackground1);
            if(jumping) {
                sonicJumping();
            }else{
                yj=y;
                
            }
            sonicFalling();
            updateCollisions();
            this.sonicWalking();
            this.createSonicRepose(this.x, this.y);
            this.checkRings();
            this.drawRings();
            this.getGraphics().drawImage(buffer, 0, 0, this);
            
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
            }
            
        }
        
    }


}
