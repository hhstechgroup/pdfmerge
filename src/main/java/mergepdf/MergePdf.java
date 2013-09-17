package mergepdf;

import java.io.*;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.util.PDFMergerUtility;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class MergePdf implements ActionListener {
    private JFrame frame;
    private JButton buttonBrowse1, buttonBrowse2, buttonMerge;
    private JTextField textFieldSource, textFieldDestination;
    private JLabel sourceLabel, labelDestination, workingLabel, doneLabel;
    private JFileChooser fileChooser;
    String path1, path2;

    void createframe() {
        frame = new JFrame("pdf merging");
        fileChooser = new JFileChooser();
        sourceLabel = new JLabel("Source");
        workingLabel = new JLabel("Merging...");
        doneLabel = new JLabel("Done");
        frame.add(sourceLabel);
        sourceLabel.setBounds(40, 40, 160, 20);
        textFieldSource = new JTextField();
        frame.add(textFieldSource);
        doneLabel.setBounds(100, 120, 60, 20);
        workingLabel.setBounds(100, 150, 100, 20);
        doneLabel.setVisible(false);
        workingLabel.setVisible(false);
        frame.add(workingLabel);
        frame.add(doneLabel);

        textFieldSource.setBounds(120, 40, 140, 21);
        buttonBrowse1 = new JButton("Browse");
        frame.add(buttonBrowse1);
        buttonBrowse1.setBounds(270, 40, 90, 20);
        labelDestination = new JLabel("Dest");
        frame.add(labelDestination);
        labelDestination.setBounds(40, 70, 80, 20);
        textFieldDestination = new JTextField();
        frame.add(textFieldDestination);
        textFieldDestination.setBounds(120, 70, 140, 21);
        buttonBrowse2 = new JButton("Browse");

        frame.add(buttonBrowse2);
        buttonBrowse2.setBounds(270, 70, 90, 20);
        buttonMerge = new JButton("Merge");
        frame.add(buttonMerge);
        buttonMerge.setBounds(135, 100, 90, 20);
        frame.setSize(400, 200);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buttonBrowse1.addActionListener(this);
        buttonBrowse2.addActionListener(this);
        buttonMerge.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonBrowse1) {
            workingLabel.setVisible(false);
            doneLabel.setVisible(false);
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnval = fileChooser.showOpenDialog(frame);
            path1 = fileChooser.getSelectedFile().toString();
            path1 = path1.replace('\\', '/');

            textFieldSource.setText(path1);
        }
        if (e.getSource() == buttonBrowse2) {
            workingLabel.setVisible(false);
            doneLabel.setVisible(false);
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnval = fileChooser.showOpenDialog(frame);
            path2 = fileChooser.getSelectedFile().toString();
            path2 = path2.replace('\\', '/');

            textFieldDestination.setText(path2);
        }
        if (e.getSource() == buttonMerge) {
            workingLabel.setVisible(true);
            search(path1, path2);
            doneLabel.setVisible(true);
            workingLabel.setVisible(false);

        }
    }


    public void search(String s1, String s2) {
        if (s1 == null) {
            return;
        }
        if (s2 == null) {
            s2 = s1;
        }
        s2 = s2 + "/result.pdf";
        System.out.println(s1);
        System.out.println(s2);
        File f1 = new File(s1);
        String[] listfile1 = f1.list();
        Arrays.sort(listfile1);
        PDFMergerUtility utility = new PDFMergerUtility();
        for (int i = 0; i < listfile1.length; i++) {
            System.out.println(listfile1[i]);
            if (listfile1[i].toLowerCase().endsWith(".pdf")) {
                utility.addSource(s1 + "/" + listfile1[i]);
            }

        }
        utility.setDestinationFileName(s2);
        try {
            utility.mergeDocuments();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (COSVisitorException e) {
            e.printStackTrace();
        }
        doneLabel.setText(utility.getDestinationFileName());
    }


    public static void main(String[] args) {
        MergePdf mpdf = new MergePdf();
        mpdf.createframe();
    }

}


