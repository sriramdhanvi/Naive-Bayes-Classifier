/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package naivebayes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Sriram
 */
public class TrainingData {
    File folder;
    File[] Examples = null;
    ArrayList<String> Vocab;
    HashMap<File,String> datahm;
    int i=0;
    String[] targetValue = {"like","dislike","like","dislike","dislike","like"};
    File file = null;
    FileReader fr =  null;
    BufferedReader br = null;
    String[] result = null;
    
   TrainingData(){
        folder = new File("C:\\Users\\Sriram\\Documents\\NetBeansProjects\\NaiveBayes\\DataSet");
        Examples = folder.listFiles();
       
        Vocab = new ArrayList<String>();
        datahm = new HashMap<File,String>();
    }

    ArrayList<String> initVocab()
    {
      for(File f : Examples)
      {
        if(f.isFile())
        {
            datahm.put(f,targetValue[(i)%6]);
            i++;
            try{
            file = new File(f.getAbsolutePath());
            
            fr = new FileReader(file);
            br  = new BufferedReader(fr);
            String str;
            while((str = br.readLine()) != null)
            {
             result = str.split(" ");
             int flag = 0;
             for(int i=0;i<result.length;i++)
             {
                for(int j=0;j<Vocab.size();j++)
                {
                    if(result[i].equals(Vocab.get(j)))
                        flag = 1;
                }
                if(flag == 0)
                    Vocab.add(result[i]);
             }
            }
            
             }catch(FileNotFoundException e){e.printStackTrace();}catch(IOException e){e.printStackTrace();}
        }
      }
      return Vocab;
    }

}
