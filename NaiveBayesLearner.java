/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package naivebayes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.StrictMath.log10;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 *
 * @author Sriram
 */
public class NaiveBayesLearner {

    /**
     * @param args the command line arguments
     */
        ArrayList<String> Vocabulary = new ArrayList<String>();
        String[] V = new String[2];
        ArrayList<File> likefolder;
        ArrayList<File> dislikefolder;
        File likeFile;
        File dislikeFile;
        File file = null;
        FileReader fr =  null;
        double like_prob,dislike_prob;
        BufferedReader br = null;  
        HashMap<String,Double> likehm;
        HashMap<String,Double> dislikehm;
        
        NaiveBayesLearner(){
            likeFile = new File("C:\\Users\\Sriram\\Documents\\NetBeansProjects\\NaiveBayes\\likefile.txt");
            dislikeFile = new File("C:\\Users\\Sriram\\Documents\\NetBeansProjects\\NaiveBayes\\dislikefile.txt");
            likefolder = new ArrayList<File>();
            dislikefolder = new ArrayList<File>();
            V = new String[] {"like","dislike"};
             
            likehm = new HashMap<String,Double>();
            dislikehm = new HashMap<String,Double>();
        }
       
    
    public void learn(File[] Examples) {
    
        TrainingData ex = new TrainingData();
        Vocabulary = ex.initVocab(); 
        
       for(String v : V)
       {
          if(v.equals("like"))
          {
             for(File f : Examples)
             {
                 
                 if(ex.datahm.get(f).equals(v))
                 {
                  
                  likefolder.add(f);
                 }
             }
                  like_prob = (double)likefolder.size()/Examples.length;
                 
                  mergeFiles(likefolder,likeFile);
                  
                  int n=0;
                  try{
                     n = wordCount(likeFile);
                     
                  }catch(FileNotFoundException e){e.printStackTrace();}
                  for(String w : Vocabulary)
                   {
                    String str;
                    String[] res;
                    int nk = 0;
                    try{
                      fr = new FileReader(likeFile);
                      br = new BufferedReader(fr);
                        while((str = br.readLine()) != null)
                         {
                           res = str.split(" ");
                           for(int h=0;h<res.length;h++)
                            {
                              if(res[h].equals(w))
                                  nk++;
                            }
                         }
                       }catch(Exception e){e.printStackTrace();}
                  
                    double p = (double)(nk+1)/(n+Vocabulary.size());
                    //System.out.println("p : "+p);
                    Double d = new Double(p);
                    likehm.put(w,d);
                  //}
                // }
                 }  
         
          
          }
          else if(v.equals("dislike"))
          {
             for(File f : Examples)
             {
                 if(ex.datahm.get(f).equals(v))
                 {
                  dislikefolder.add(f);
                 }
             }
                  dislike_prob = (double)dislikefolder.size()/Examples.length;
                  
                  mergeFiles(dislikefolder,dislikeFile);
                  int n=0;
                  try{
                     n = wordCount(dislikeFile);
                  }catch(FileNotFoundException e){e.printStackTrace();}
                  for(String w : Vocabulary)
                   {
                    String str;
                    String[] res;
                    int nk = 0;
                    try{
                      fr = new FileReader(dislikeFile);
                      br = new BufferedReader(fr);
                        while((str = br.readLine()) != null)
                         {
                           res = str.split(" ");
                           for(int h=0;h<res.length;h++)
                            {
                              if(res[h].equals(w))
                                  nk++;
                            }
                         }
                       }catch(Exception e){e.printStackTrace();}
                  
                    double p = (double)(nk+1)/(n+Vocabulary.size());
                    Double d = new Double(p);
                    dislikehm.put(w,d);
                  }
                 }
            }
         // }
       //}
    }
        
        // TODO code application logic here
   private void mergeFiles(ArrayList<File> folder, File mergedFile)
    {
        
        FileWriter fstream = null;
		BufferedWriter out = null;
		try {
			fstream = new FileWriter(mergedFile, true);
			 out = new BufferedWriter(fstream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
 
		for (File f : folder) {
			
			FileInputStream fis;
			try {
				fis = new FileInputStream(f);
				BufferedReader in = new BufferedReader(new InputStreamReader(fis));
 
				String aLine;
				while ((aLine = in.readLine()) != null) {
					out.write(aLine);
					out.newLine();
				}
 
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
 
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
              
    }
 private int wordCount(File file)throws FileNotFoundException
 {
     int count = 0;
     FileReader fr =  null;
     BufferedReader br = null;
     fr = new FileReader(file);
     br = new BufferedReader(fr);
     String str;
     String[] res;
     try{
       while((str = br.readLine()) != null)
        {
         
         res = str.split(" ");
         count = count+res.length;
        }
     }catch(Exception e){e.printStackTrace();}
     return count;
 }
 public String naiveBayesClassifier(File testDoc)throws FileNotFoundException
 {
     String prediction = "dislike(international politics)";
    
     ArrayList<String> wordList = new ArrayList<String>();
     int count=1;
     br = new BufferedReader(new FileReader(testDoc));
     String line;
     String[] res;
     
     Double d;
     try{
         while((line = br.readLine()) != null)
         {
           res = line.split(" ");
     
           for(int i=0;i<res.length;i++)
           {
            for(String s : Vocabulary)
            {
                if(s.equals(res[i]))
                {
                 wordList.add(res[i]);
                 
                 break;
                }
            }
            count++;
           }
         }
        }
     catch(IOException e){e.printStackTrace();}
     double tlike,tdislike;
    
     tlike = log10(like_prob*(like_pie(wordList)));
     tdislike = log10(dislike_prob*(dislike_pie(wordList)));
     //System.out.println(tlike);
     //System.out.println(tdislike);
     if(tlike > tdislike)
         prediction = "like(cricket)";
     return prediction;
 }
 private double like_pie(ArrayList<String> wordList)
 {
     int i;
     double prob = 1;
     
     for(String w : wordList)
     {
       
       prob = prob*(likehm.get(w).doubleValue());
       
     }
     
     return prob;
 }
 private double dislike_pie(ArrayList<String> wordList)
 {
     
     double prob = 1;
     for(String w : wordList)
     {
       
       prob = prob*(dislikehm.get(w).doubleValue());
     }
     
     return prob;
 }
}

