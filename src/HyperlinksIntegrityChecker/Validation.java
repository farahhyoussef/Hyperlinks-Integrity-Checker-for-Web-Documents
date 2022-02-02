/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment_4_oop;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JOptionPane;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Validation {

      
    
static HashSet<String> visitedLinks = new HashSet<>();

    public static boolean validateURL(String link) throws IOException {

        try {
            Jsoup.connect(link).timeout(300000).get();

        } catch (Exception ex) {  
            return false;
        }
        return true;
    }

     static int size;
    static ExecutorService es;
    
    public static void set_num_threads(){ es = Executors.newFixedThreadPool(size);}
    static int numValid=0, numInvalid=0, i=0;
    static Elements e;
    static double time;
    static int num=0;
    static long startTime;
    public static void validate(String link,String title, int currentdepth, int totaldepth) throws IOException, InterruptedException {
        long  endTime,l=1000;
        
       
        ThreadValidate t1 = null;
        
        
        if (validateURL(link)&& !visitedLinks.contains(link)) {
            visitedLinks.add(link);
            System.out.println("Valid Link: " + link);
            System.out.println("text:" + title);
            System.out.println("");
            numValid++;

            if (currentdepth == totaldepth) {
                
                if (e==null ||numValid + numInvalid - 1 == num )//all links are checked 
                {
                    if(e==null)
                    {
                        JOptionPane.showMessageDialog(null,"Root Link Is Valid");
                    }else{
                    numValid--;
                   endTime = System.currentTimeMillis();
                   time=(double)(endTime-startTime)/l;
                   System.out.println("--------------------------------------------------");
                   new Result().setVisible(true);
                    }
                   
                }
                return;
            }
            Document doc = Jsoup.connect(link).timeout(300000).get();
            e = doc.select("a[href]"); // extract only links
            System.err.println("Number of Links:" + e.size());
             num=e.size();
            URL u = new URL(link);
            for (i = 0; i < e.size(); i++) {
                String x = e.get(i).attr("href");
                 String y = e.get(i).text();
                String baseLINK = u.getProtocol() + "://" + u.getHost();
                System.out.println("x "+x);
                System.out.println("u.getProtocol()" + u.getProtocol());
                System.out.println("u.getHost()" + u.getHost());
                
                if (!x.startsWith("http")) {
                    x = baseLINK + x;
                }
                t1 = new ThreadValidate(x, y,currentdepth + 1, totaldepth);
                es.execute(t1);
                

            }
        } else {
            
            System.err.println("Invalid Link " + link);
            numInvalid++;
           
            if (e == null || numValid + numInvalid - 1 == e.size())//all links are checked 
            {
                if(e==null)
                {
                    JOptionPane.showMessageDialog(null, "Root Link Is Invalid!");
                }else{
                
                if(numValid!=0)
                numValid--;
                   endTime = System.currentTimeMillis();
                   time=(double)(endTime-startTime)/l;
                System.out.println("--------------------------------------------------");
                new Result().setVisible(true);
            }
            }

        }

    }

}
