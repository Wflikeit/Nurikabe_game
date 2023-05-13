package game;

public class NurikabeBoardPanel {
    // zakladamy, ze ma juz podany rozmiar kxk
    int k = 5;
    int[][] nurikabeBoardPanel = new int[k][k];

    /**
     * 0 - means that a cell is blank if
     * 1 - means that a cell is black
     * 2 - means that a cell is white
     */
    public NurikabeBoardPanel() {
        k = k - 1;
        for (int i = 0; i <= k; i++) {
            for (int j = 0; j <= k; j++) {
                nurikabeBoardPanel[i][j] = 0;
                System.out.println(nurikabeBoardPanel[i][j]);
//                System.out.println(i);
//                System.out.println(j);
            }
        }
    }
    private void generateBoard(){}
    private void loadStatusFromFile(){

    }
    private void saveStatusToFile(){

    }

    public static void main(String[] args) {
    }
}
