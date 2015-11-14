/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package naivebayes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 *
 * @author Sriram
 */
public class NaiveBayes {
    public static void main(String[] args)throws FileNotFoundException
    {
        String prediction = "unpredictable due to some reason...";
        TrainingData exm = new TrainingData();
        NaiveBayesLearner nbl = new NaiveBayesLearner();
        nbl.learn(exm.Examples);
        
        File testDoc = new File("C:\\Users\\Sriram\\Documents\\NetBeansProjects\\TestDocs_NaiveBayes\\testfile.txt");
        try{
          prediction = nbl.naiveBayesClassifier(testDoc);
        }catch(FileNotFoundException e){e.printStackTrace();}
        System.out.printf("the classification for the new document is %s \n",prediction);
        PrintWriter like_writer = new PrintWriter(new File("C:\\Users\\Sriram\\Documents\\NetBeansProjects\\NaiveBayes\\likefile.txt"));
        like_writer.print("");
        like_writer.close();
       
        PrintWriter dislike_writer = new PrintWriter(new File("C:\\Users\\Sriram\\Documents\\NetBeansProjects\\NaiveBayes\\dislikefile.txt"));
        dislike_writer.print("");
        dislike_writer.close();
    }
}
