import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class main {
    
    
    public static String compress (String message, ArrayList<Pair> myList){
        //==============
       double compressedMessage =0;
        for (int i = 0; i < message.length() - 1; i++) {
            for (int j = 0; j < myList.size(); j++) {
                if (message.charAt(i) == myList.get(j).symbol) {
                    myList.get(myList.size() - 1).proibability = myList.get(j).proibability;
                    myList.get(0).proibability = myList.get(j - 1).proibability;
                    for (int k = 1; k < myList.size() - 1; k++) {
                    
                        myList.get(k).proibability = myList.get(0).proibability + ((myList.get(myList.size() - 1).proibability - myList.get(0).proibability) * myList.get(k).mainProbabilty);
                    }
                    break;
                }
            }
            if ((i + 1) >= message.length() - 1) {
                for (int j = 0; j < myList.size(); j++) {
                    if (message.charAt(i+1) == myList.get(j).symbol) {
                        //System.out.println("value is "+(myList.get(j).proibability+myList.get(j-1).proibability)/2);
                        compressedMessage=(myList.get(j).proibability+myList.get(j-1).proibability)/2;
                    }
                }
            }
        }
    
    
       return Double.toString(compressedMessage);
    }
    
    
    public static String deCompress (double compMessage, int interations)
    {
        String result="";
        Pair pair0 = new Pair();
        Pair pair1 = new Pair();
        Pair pair2 = new Pair();
        Pair pair3 = new Pair();
        double probabiltyA = 0.8;
        double probabilityB = 0.02;
        double probabilityC = 0.18;
        pair0.symbol = 'ʶ';
        pair0.proibability = 0;
        pair1.mainProbabilty = 0;
        pair1.symbol = 'A';
        pair1.proibability = probabiltyA;
        pair1.mainProbabilty = probabiltyA;
        pair2.symbol = 'B';
        pair2.proibability = pair1.proibability + probabilityB;
        pair2.mainProbabilty = pair1.proibability + probabilityB;
        pair3.symbol = 'C';
        pair3.proibability = pair2.proibability + probabilityC;
        pair3.mainProbabilty = pair2.proibability + probabilityC;
        ArrayList<Pair> myList = new ArrayList<Pair>();
        myList.add(pair0);
        myList.add(pair1);
        myList.add(pair2);
        myList.add(pair3);
        
        for (int i = 0; i <interations ; i++) {
            for (int j = 0; j <myList.size() ; j++) {
                if (myList.get(j).proibability<compMessage && compMessage<myList.get(j+1).proibability)
                {
                  
                    result+=myList.get(j+1).symbol;
                    myList.get(myList.size() - 1).proibability = myList.get(j+1).proibability;
                    myList.get(0).proibability = myList.get(j).proibability;
                    for (int k = 1; k < myList.size() - 1; k++) {
                    
                        myList.get(k).proibability = myList.get(0).proibability + ((myList.get(myList.size() - 1).proibability - myList.get(0).proibability) * myList.get(k).mainProbabilty);
                    }
                    break;
                }
            }
        }
        return result;
    }
    public static String readFromFile(String path) throws IOException {
        String content = new Scanner(new File(path)).useDelimiter("\\Z").next();
        System.out.println("content is"+content);
        return content;
    }
    public static void saveTofile(String content,int iterations,String probabilties,String path) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path+".txt"));
        writer.write(content+"-"+probabilties+"-"+iterations);
        writer.close();
    }
    
    public static ArrayList<Pair> prepareTable (String input){
        System.out.println("onceAgain");
        ArrayList <Pair> mytable = new ArrayList<Pair>();
        Pair pair0 = new Pair();
        pair0.symbol = 'ʶ';
        pair0.proibability = 0;
        mytable.add(pair0);
        String record[] = input.split("\n");
        for (int i = 0; i <record.length ; i++) {
            String m[] = record[i].split(" ");
            Pair pair = new Pair();
            pair.symbol=m[0].charAt(0);
            pair.proibability=Double.parseDouble(m[1]);
            pair.mainProbabilty=Double.parseDouble(m[1]);
            mytable.add(pair);
        }
        for (int i = 1; i <mytable.size() ; i++) {
            mytable.get(i).proibability=mytable.get(i).proibability+ mytable.get(i-1).proibability;
            mytable.get(i).mainProbabilty=mytable.get(i).mainProbabilty+ mytable.get(i-1).mainProbabilty;
    
        }
        return mytable;
    }
    public static void main(String args[]) {
    
        JFrame f=new JFrame("Arithmetic Encoder and Decoder");
        JButton btnReadFromFile=new JButton("read from text file");
        JButton btnSaveToFromFile=new JButton("Save to text file");
        JLabel inputLabel = new JLabel("Enter Message here");
        JLabel interationLabel = new JLabel("Number of Iterations");
        JLabel probabilityLabel = new JLabel("Enter each character and corresponding probability");
        JButton btnDecompress=new JButton("Decompress");
        JButton btnCompress=new JButton("Compress");
        JTextField compressFiels = new JTextField();
        JTextField numberOfIterations = new JTextField();
        JTextPane probailityBox= new JTextPane();
        JLabel finalResutl=new JLabel("The result is:");
    
        int x=70;
        int xx=700;
        inputLabel.setBounds(100,35,250,60);
        interationLabel.setBounds(380,35,250,60);
        probabilityLabel.setBounds(100,160,350,60);
        probailityBox.setBounds(100,210,250,90);
        numberOfIterations.setBounds(380,80,130,30);
        compressFiels.setBounds(100,80,250,30);
        finalResutl.setBounds(100,140,400,30);
        finalResutl.setFont(finalResutl.getFont().deriveFont(20f));
        btnReadFromFile.setBounds(xx,80,140,30);
        btnSaveToFromFile.setBounds(xx,110,140,30);
        btnDecompress.setBounds(xx,x+100,110,30);
        btnCompress.setBounds(xx,200+x,110,30);
        f.add(btnSaveToFromFile);
        f.getContentPane().add(finalResutl);
        f.add(inputLabel);
        f.add(probabilityLabel);
        f.add(interationLabel);
        f.add(probailityBox);
        f.add(numberOfIterations);
        f.add(compressFiels);
        f.add(btnReadFromFile);
        f.add(btnDecompress);
        f.add(btnCompress);
        f.setSize(900,400);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        btnDecompress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
              
                finalResutl.setText("The result is : "+deCompress(Double.parseDouble(compressFiels.getText()) ,Integer.parseInt(numberOfIterations.getText())));
            
            }
        });
        btnCompress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("called");
            
                finalResutl.setText("The result is : "+compress(compressFiels.getText(),prepareTable(probailityBox.getText().toString())));
            }
        });
        btnReadFromFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                int returnValue = jfc.showOpenDialog(null);
                FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt");
                jfc.setFileFilter(filter);
            
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jfc.getSelectedFile();
                    try {
                        String unBarsed=readFromFile(selectedFile.getAbsolutePath());
                        String barsed[] = unBarsed.split("-");
                      
                      //  System.out.println(barsed[0]);
                        compressFiels.setText(barsed[0]);
                        probailityBox.setText(barsed[1]);
                       numberOfIterations.setText(barsed[2]);
                        
                        // compressFiels.setText();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    
        btnSaveToFromFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                jfc.setApproveButtonText("save");
                // Set the mnemonic
                jfc.setApproveButtonMnemonic('s');
                // Set the tool tip
                jfc.setApproveButtonToolTipText("save");
                int returnValue = jfc.showOpenDialog(null);
            
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jfc.getSelectedFile();
                
                    try {
                        saveTofile(finalResutl.getText().substring(16,finalResutl.getText().length()),compressFiels.getText().length(),probailityBox.getText(),selectedFile.getAbsolutePath());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            
            }
        });
    }
    
    
}
