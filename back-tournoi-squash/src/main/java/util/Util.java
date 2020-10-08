package util;

import java.lang.Character;

public class Util {

    public String crypt(String s, int cpt){
       String result = "";
       for(int i = 1; i <= s.length(); i++){
           Character ca = new Character(s.charAt(i-1));
           int p = 0;
           if(i%3==0)
               p = ca+8*i;
           else if(i%3==1)
               p = ca-2*i;
           else if(i%3==2)
               p = ca+3*i;
           result+= (char)p;
       }
       if(cpt < 2)
           result+=crypt(result,cpt+1);
       return result;
    }

    public String decrypt(String s){
       String result = "";
       for(int i = 1; i <= (s.length()/3); i++){
           Character ca = new Character(s.charAt(i-1));
           int p = 0;
           if(i%3==0)
               p = ca-8*i;
           else if(i%3==1)
               p = ca+2*i;
           else if(i%3==2)
               p = ca-3*i;
           result+= (char)p;
       }
       return result;
    }
}
