package game;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
//        JFrame frame = new JFrame("Nurikabe Game");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        NurikabeBoardPanel nurikabeBoardPanel = new NurikabeBoardPanel();
        NurikabeSolver nurikabeSolver = new NurikabeSolver();
        NurikabeBoardPanel nurikabeBoardPanel1 = new NurikabeBoardPanel();
        System.out.println(nurikabeBoardPanel1.nurikabeBoardPanel);

        // Utworzenie instancji klasy MainWindow i dodanie jej do okna
//        NurikabeMainWindow mainWindow = new NurikabeMainWindow(nurikabeSolver);
//        frame.add(mainWindow);

        // Ustawienie rozmiaru okna i wyświetlenie go
//        frame.setSize(800, 800);
//        frame.setVisible(true);
    }
}