import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;





public class Flappybird extends JPanel implements ActionListener,KeyListener{
    
    int boardwidth = 360;
    int boardhieght = 640 ;

    //images
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    // bird 
    int birdx = boardwidth/8;
    int birdy = boardhieght/2;
    int birdwidth = 34 ;
    int birdheight =24;

    class bird{
        int y = birdy;
        int x = birdx;
        int width = birdwidth;
        int height = birdheight ;
        Image Img;
        bird(Image Img){
            this.Img = Img;

        }
    }
    // pipes
    int pipex = boardwidth;
    int pipey = 0;
    int pipewidth = 64;
    int pipeheight = 512;

    class pipe{
        int x = pipex;
        int y = pipey;
        int width = pipewidth;
        int height = pipeheight;
        Image Img;
        boolean passed = false;

        pipe(Image Img){
            this.Img = Img;
        }
    }

    


    // gamelogic 
    bird bird;
    int velocityX = -4;
    int velocityY = 0;
    int gravity = 1;

    ArrayList<pipe>pipes;
    Random random = new Random();

    Timer gameLoop;
    Timer placepipesTimer;
    boolean gameover= false;
    double score = 0;

    Flappybird(){
        setPreferredSize(new Dimension(boardwidth,boardhieght));
                // setBackground(Color.BLUE);
                setFocusable(true);
                addKeyListener(this);
            backgroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
             birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
             topPipeImg = new ImageIcon(getClass().getResource("./topPipe.png")).getImage();
             bottomPipeImg = new ImageIcon(getClass().getResource("./bottomPipe.png")).getImage();
                // bird
             bird = new bird(birdImg);

             pipes = new ArrayList<pipe>();
            //  place ppipestimer
            placepipesTimer = new Timer(1500,new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    placepipes();
                }
            });
            placepipesTimer.start();
            //  ?
            gameLoop = new Timer(1000/60,this);
            gameLoop.start();
                           
    }

    public void placepipes(){
        int Randompipey = (int)(pipey-pipeheight/4 - Math.random()*(pipeheight)/2);
        int openingspace = boardhieght/4;
        pipe topPipe = new pipe(topPipeImg);
        topPipe.y =Randompipey;
        pipes.add(topPipe);

        pipe bottomPipe = new pipe(bottomPipeImg);
         bottomPipe.y = topPipe.y + pipeheight + openingspace;
        pipes.add(bottomPipe);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }
    public void draw(Graphics g){
        
        // backgound
        g.drawImage(backgroundImg,0,0,boardwidth,boardhieght,null);

        // bird 
        g.drawImage(bird.Img, bird.x, bird.y, bird.width, bird.height, null);

        for(int i = 0; i < pipes.size(); i++){
            pipe pipe = pipes.get(i);
            g.drawImage(pipe.Img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }
        g.setColor(Color.white);
        g.setFont(new Font("arial", Font.PLAIN, 32));
        if (gameover){
            g.drawString ("gameover:" + String.valueOf((int)score),10,35);
        }
        else {
            g.drawString (String.valueOf((int) score),10,35);
        }
    }

    
    public void move(){
        velocityY += gravity;
        bird.y += velocityY; 
        bird.y = Math.max(bird.y,0);
// ?\ pipes 
        for (int i =0; i <pipes.size(); i++){
            pipe pipe = pipes.get(i);
            pipe.x += velocityX;

            if (pipe.passed && bird.x > pipe.x + pipewidth){
                pipe.passed = true;
                score += 0.5;
            }

            if (collision(bird, pipe)){
                gameover = true;
            }
        }
        if (bird.y > boardhieght){
            gameover= true;
        }
    }
    public boolean collision(bird a, pipe b){
        return a.x < b.x + b.width &&
        a.x+ a.width > b.x &&
        a.y <b.y +b. height &&
        a.y + a.height >b.y;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        move(); 
        repaint();
        if (gameover){
            placepipesTimer.stop();
            gameLoop.stop();
        }
        
    }
   
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()== KeyEvent.VK_SPACE){
            velocityY = -9;
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
  
    
}
