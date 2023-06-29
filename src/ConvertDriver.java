import javax.swing.*;
import java.io.*;

public class ConvertDriver {
    public static void main(String[] args) {
        Convert arabicToRoman = new Convert();
        arabicToRoman.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        arabicToRoman.setSize(500, 500);
        arabicToRoman.setVisible(true);
    }
}