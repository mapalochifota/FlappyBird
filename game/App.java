import javax.swing.*;

public class App{
    public static void main(String[] args){
        
       int boardwith = 360;
       int boardhieght = 640;

       JFrame frame = new JFrame("floppy bird");
       frame.setVisible(true);
       frame.setSize(boardwith,boardhieght);
       frame.setLocationRelativeTo(null);
       frame.setVisible(true);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


       Flappybird flappybird = new Flappybird();
       frame.add(flappybird);
       frame.pack();
       flappybird.requestFocus();
       frame.setVisible(true);        
    }
}