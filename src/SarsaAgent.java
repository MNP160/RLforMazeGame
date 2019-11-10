import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SarsaAgent extends Hero implements IAgent {

    private final double learningRate = 0.2;
    private final double eagerness = 0.8; //close to 0--looking towards close future; close to 1-- looking to distant future
    private final double E=1;  //greediness
    private final double discountFactor=0.5;


    private final int mazeHeight = 14;
    private final int mazeWidth = 14;
    private final int statesCount = mazeHeight * mazeWidth;

    private final int finishReward = 100;
    private final int penalty = -10;
    private final int moveReward = 20;

    private int[][] rewardMatrix; //used for reward lookup
   private   double[][] qMatrix;  //used for storing Q-values
    private char[][] mazeMap;
    private int x;
    private int y;




    void printQMatrix() {
        System.out.println("Printing Q Matrix for SARSA:");

        for (int i = 0; i < qMatrix.length; i++) {
            System.out.print("From state "+i + ": ");
            for (int j = 0; j < qMatrix[i].length; j++) {
                System.out.printf("%6.2f ", qMatrix[i][j]);
            }
            System.out.println();
        }
    }
    public void prepareRequiredData() {

        File file = new File("C:\\Users\\mpene\\Desktop\\GameImages\\mazeMap.txt");
        rewardMatrix = new int[statesCount][statesCount];
        qMatrix = new double[statesCount][statesCount];
        mazeMap = new char[mazeHeight][mazeWidth];

        try (FileInputStream fs = new FileInputStream(file)) {

            x = 0;
            y = 0;


            int n;

            while ((n = fs.read()) != -1) {
                char c = (char) n;

                if (c != 'g' && c != 'c' && c != 'w') {
                    continue;
                }
                mazeMap[x][y] = c;
                y++;
                if (y == mazeWidth) {
                    y = 0;
                    x++;
                }
            }


            for (int k = 0; k < statesCount; k++) {
                x = k / mazeWidth;
                y = k - x * mazeWidth;


                for (int s = 0; s < statesCount; s++) {
                    rewardMatrix[k][s] = -1;
                }

                if (mazeMap[x][y] != 'c') {


                    int left = y - 1;
                    if (left >= 0) {
                        int target = x * mazeWidth + left;
                        if (mazeMap[x][left] == 'g') {
                            rewardMatrix[k][target] = moveReward;
                        } else if (mazeMap[x][left] == 'c') {
                            rewardMatrix[k][target] = finishReward;
                        } else {
                            rewardMatrix[k][target] = penalty;
                        }


                    }

                    int right = y + 1;
                    if (right < mazeWidth) {
                        int target = x * mazeWidth + right;

                        if (mazeMap[x][right] == 'g') {
                            rewardMatrix[k][target] = moveReward;
                        } else if (mazeMap[x][right] == 'c') {
                            rewardMatrix[k][target] = finishReward;
                        } else {
                            rewardMatrix[k][target] = penalty;
                        }


                    }

                    int up = x - 1;
                    if (up >= 0) {

                        int target = up * mazeWidth + y;
                        if (mazeMap[up][y] == 'g') {
                            rewardMatrix[k][target] = moveReward;
                        } else if (mazeMap[up][y] == 'c') {
                            rewardMatrix[k][target] = finishReward;
                        } else {
                            rewardMatrix[k][target] = penalty;
                        }


                    }
                    int down = x + 1;

                    if (down < mazeHeight) {

                        int target = down * mazeWidth + y;

                        if (mazeMap[down][y] == 'g') {
                            rewardMatrix[k][target] = moveReward;
                        } else if (mazeMap[down][y] == 'c') {
                            rewardMatrix[k][target] = finishReward;
                        } else {
                            rewardMatrix[k][target] = penalty;
                        }

                    }

                }


            }
            printRewardMatrix();
        } catch (IOException ex) {
            ex.printStackTrace();

        }

    }


    public boolean isFinish(int state) {
        int i = state / mazeWidth;
        int j = state - i * mazeWidth;

        return mazeMap[i][j] == 'c';

    }

    public ArrayList<Integer> possibleActions(int state) {
        ArrayList<Integer> actions = new ArrayList<>();
        for (int i = 0; i < statesCount; i++) {
            if (rewardMatrix[state][i] != -1) {
                actions.add(i);
            }
        }
        return actions;
        //return actions.stream().mapToInt(i -> i).toArray();
    }

    /*public double maximalQ(int followingState) {
        int[] actions = possibleActions(followingState);
        double max = Double.NEGATIVE_INFINITY;
        for (int next : actions) {
            double value = qMatrix[followingState][next];

            if (value > max) {
                max = value;
            }
        }
        return max;
    }*/



    void runSARSA(){
        Random rand = new Random();
        // int counter=0;
       // for(int i=0; i<2; i++){

            int current = rand.nextInt(statesCount);
          //  System.out.println("current:"+current);
        int action=selectAction(current);
            //boolean finish=false;
            boolean finish = (current == 180);
            while(!finish){
               // System.out.println("Beggining while loop ");

               // System.out.println("action:"+action);

                ArrayList<Integer> actions = possibleActions(current);
                int reward=rewardMatrix[current][action];
               //System.out.println("reward:"+reward);
                int index=rand.nextInt(actions.size());
                int next=actions.get(index);
             //  System.out.println("nextState:"+next);

                int nAction=selectAction(next);
              //  System.out.println("nextAction:"+nAction);
              //  if(actionp==-1) break;
                qMatrix[current][action]=qMatrix[current][action]+learningRate*(reward+discountFactor*qMatrix[next][nAction]-qMatrix[current][action]);
                /*if(current==180){
                    break;
                }*/
                current=next;
                action=nAction;
                finish = (current == 180);


            }
           // counter++;
           // System.out.println("Number of total iterations: "+ counter );
       // }



   }


    int selectAction(int state){
        Random rand = new Random();


        boolean bestPolicy=(rand.nextDouble()<E);

        if(bestPolicy){
            double max=-1;
            int bestAction=1;
             int action=0;
            ArrayList<Integer> actions=possibleActions(state);
            for(int i=0; i<actions.size(); i++){
                 action=actions.get(i);
                if(qMatrix[state][action]>max){
                    max=qMatrix[state][action];
                    bestAction=action;

                }
            }
          //  System.out.println("returning best action "+ bestAction+" action:"+action);
            return bestAction;
        }
        else{
            ArrayList<Integer> actions=possibleActions(state);
            int randomChoice=actions.get(rand.nextInt(actions.size()));

           // System.out.println("returning random choice" + actions.size()+" random choice = "+randomChoice);
            return randomChoice;
        }
    }
    void printRewardMatrix() {
        System.out.print("Printing Reward Matrix:");

        for (int i = 0; i < rewardMatrix.length; i++) {
            for (int j = 0; j < rewardMatrix[i].length; j++) {
                System.out.printf("%4s", rewardMatrix[i][j]);
            }
            System.out.println();
        }

    }



}
