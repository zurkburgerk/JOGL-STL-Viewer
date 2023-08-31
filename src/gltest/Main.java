package gltest;

import javax.swing.*;
import java.io.File;
import java.awt.event.*;

public class Main {

	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Simple File Picker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton pickButton = new JButton("Pick a File");

        //button on click
        pickButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(frame);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String filePath = selectedFile.getAbsolutePath();
                    frame.dispose();
                    //start stl viewer
                    new STLViewer("STL Viewer", filePath);
                }
            }
        });

        JPanel panel = new JPanel();
        panel.add(pickButton);

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
	}

}
