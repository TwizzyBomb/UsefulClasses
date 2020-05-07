/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package usefulClasses;
import java.io.*;
import java.util.*;
/**
 *
 * @author User
 */
public class CSVStringTokenizer {
    static String tempFilePath = "C:\\Users\\User\\Downloads\\csvdownload.csv";
    static String fileName;//used in Buffered reader conditional which builds arrays
    
    public static void main(String[] args) throws FileNotFoundException, IOException{
        // TODO code application logic here

        //try { 
        FileReader fr = new FileReader(tempFilePath);
        BufferedReader br = new BufferedReader(fr);

        StringTokenizer st = null;
        int lineNumber = 0, tokenNumber = 0;
        final String delimiter = ",";
        //Sets string fileName to the line in the csv file, (to be delimited by commas later)
        //and in the same step checks if this line is equal to null as the exit condition of the loop
        for(lineNumber = 1; (fileName = br.readLine()) != null; lineNumber++){

            System.out.println(fileName);

            //break comma separated line using ","
            st = new StringTokenizer(fileName, delimiter);
            while (st.hasMoreTokens()) {
                    tokenNumber++;
                    System.out.println("Line # " + lineNumber
                            + ", Token # " + tokenNumber
                            + ", Token : " + st.nextToken());
            }
            //reset token number
            tokenNumber = 0;
        }

        //System.out.println(fileName);
        //return fileName;

    }
}