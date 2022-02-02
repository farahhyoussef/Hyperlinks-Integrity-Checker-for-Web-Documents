/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment_4_oop;

import java.io.IOException;

public class ThreadValidate extends Thread {

    private String link;
    private int depth;
    private int maxDepth;
private String title;
    public ThreadValidate(String link,String title, int depth, int maxDepth) {
        this.link = link;
        this.depth = depth;
        this.maxDepth = maxDepth;
        this.title=title;
    }

    public void run() {

        try {
            Validation.validate(link,title, depth, maxDepth);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(ThreadValidate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            java.util.logging.Logger.getLogger(ThreadValidate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

    }
}
