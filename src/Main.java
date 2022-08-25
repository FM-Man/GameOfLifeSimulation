import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;



public class Main {
    public static ImageIcon alive = new ImageIcon("red.png");
    public static ImageIcon dead  = new ImageIcon("white.png");
    public static final int IDEAL_DIMENSION = 20;
    public static boolean state[][] = new boolean[IDEAL_DIMENSION][IDEAL_DIMENSION];
    public static JPanel panel = new JPanel();

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {

        panel.setLayout(null);

        Scanner sc = new Scanner(new File("initState.txt"));

        int index=0;
        while (sc.hasNextInt()){
            int a = sc.nextInt();
            state[index/IDEAL_DIMENSION][index%IDEAL_DIMENSION] = a != 0;
            index++;
        }

//        drawPanel(state);

        print(state);
        JFrame frame;

        frame = new JFrame("idk");
        frame.setSize(300,300);
        frame.add(panel);
        JButton button = new JButton("next State");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    state = play(state);
                    drawPanel(state);
                    print(state);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        button.setBounds(0,0,200,50);
        panel.add(button);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);



//        System.out.println();

        for(int i = 0; i<100000; i++){


            Thread.sleep(500);
//            frame.setVisible(false);
        }
    }

    public static void drawPanel(boolean[][] state){
        JLabel[][] labels = new JLabel[IDEAL_DIMENSION][IDEAL_DIMENSION];
        for (int i=0; i<labels.length; i++) {
            for (int j=0; j<labels[1].length; j++) {
                labels[i][j] = state[i][j] ? new JLabel(alive):new JLabel(dead);
                labels[i][j].setBounds(IDEAL_DIMENSION*i,IDEAL_DIMENSION*j,IDEAL_DIMENSION,IDEAL_DIMENSION);
                panel.add(labels[i][j]);
            }
        }
    }

    public static boolean[][] play(boolean[][] state) throws InterruptedException {
        boolean[][] newState =new  boolean[IDEAL_DIMENSION][IDEAL_DIMENSION];
        for (int i=0; i< state.length; i++){
            for(int j=0; j< state[i].length; j++){
                int count = 0;
                count+= state[(i-1+IDEAL_DIMENSION)%IDEAL_DIMENSION][(j-1+IDEAL_DIMENSION)% IDEAL_DIMENSION]? 1: 0;
                count+= state[(i-1+IDEAL_DIMENSION)%IDEAL_DIMENSION][(j)%IDEAL_DIMENSION]? 1: 0;
                count+= state[(i-1+IDEAL_DIMENSION)%IDEAL_DIMENSION][(j+1)%IDEAL_DIMENSION]? 1: 0;
                count+= state[(i)%IDEAL_DIMENSION][(j-1+IDEAL_DIMENSION)%IDEAL_DIMENSION]? 1: 0;
                count+= state[(i)%IDEAL_DIMENSION][(j+1)%IDEAL_DIMENSION]? 1: 0;
                count+= state[(i+1)%IDEAL_DIMENSION][(j-1+IDEAL_DIMENSION)%IDEAL_DIMENSION]? 1: 0;
                count+= state[(i+1)%IDEAL_DIMENSION][(j)%IDEAL_DIMENSION]? 1: 0;
                count+= state[(i+1)%IDEAL_DIMENSION][(j+1)%IDEAL_DIMENSION]? 1: 0;

                if(state[i][j] && (count<2 || count>3))
                    newState[i][j] = false;
                else if(!state[i][j] && count==3)
                    newState[i][j] = true;
                else
                    newState[i][j] = state[i][j];
            }
        }


        return newState;
    }

    public static void print(boolean[][] state){
        for (boolean[] arr:state){
            for (boolean b:arr){
                if (b) System.out.print("█ ");
                else System.out.print("  ");
            }
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
        }
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

}
