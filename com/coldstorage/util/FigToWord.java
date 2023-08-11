package com.coldstorage.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class FigToWord
{
/*    String[] unitdo ={"", " One", " Two", " Three", " Four", " Five",
       " Six", " Seven", " Eight", " Nine", " Ten", " Eleven", " Twelve",
       " Thirteen", " Fourteen", " Fifteen",  " Sixteen", " Seventeen", 
       " Eighteen", " Nineteen"};
    String[] tens =  {"", "Ten", " Twenty", " Thirty", " Forty", " Fifty",
       " Sixty", " Seventy", " Eighty"," Ninety"};
    String[] digit = {"", " Hundred", " Thousand", " Lakh", " Crore"};
*/

    String[] unitdo ={"", " ऐक ", " दो ", " तीन ", " चार ", " पाँच ",
    	       " छे ", " सात ", " आठ ", " नौ ", " दस ", " ग्यारह ", " बारह ",
    	       " तेरह ", " चौदह ", " पन्द्रह "," सोलह ", " सत्रह ", 
    	       " अठारह ", " उन्नीस ", " बीस ", " इक्कीस ", " बाईस ", " तेईस ", " चोवीस ", " पच्चीस ", " छब्बीस ", " सत्ताईस ",
       	       " अट्ठाईस ", " उनतीस ", " तीस ", " इकतीस ", " बत्तीस ", " तैंतीस ", " चौंतीस ", " पैंतीस ", " छत्तीस ", " सैंतीस ",
       	       "अड़तीस ", " उनतालीस ", " चालीस ", " इकतालीस ", " बयालीस ", " तैंतालीस ", " चौवालीस ", " पैंतालीस ", " छियालीस ", " सैंतालीस ",
       	       " अड़तालीस ", " उनचास ", " पचास ", " इक्यावन ", " बावन ", " तिरेपन ", " चौवन ", " पचपन ", " छप्पन ", " सत्तावन ",
       	       " अट्ठावन ", " उनसठ ", " साठ ", " इकसठ ", " बासठ ", " तिरेसठ ", " चौंसठ ", " पैंसठ ", " छियासठ ", " सड़सठ ",
    		   " अड़सठ ", " उनहत्तर ", " सत्तर  ", " इकहत्तर ", " बहत्तर ", " तिहत्तर ", " चौहत्तर ", " पचहत्तर ", " छिहत्तर ", " सतहत्तर ",
    		   " अठहत्तर ", " उनासी ", " अस्सी  ", " इक्यासी ", " बयासी ", " तिरासी ", " चौरासी ", " पचासी ", " छियासी ", " सत्तासी ",
    		   " अट्ठासी ", " नवासी ", " नब्बे  ", " इक्यानबे ", " बानबे ", " तिरानबे ", " चौरानबे ", " पंचानबे ", " छियानबे ", " सत्तानबे ", " अट्ठानबे ", " निन्यानबे "};

    
    String[] tens =  {"", "दस ", " बीस ", " तीस ", " चालीस ", " पचास ",
    	       " साहट ", " सत्तर ", " अस्सी "," नब्बे "};

    String[] digit = {"", " सौ ", " हजार ", " लाख ", " करोड़ "};
    
    
 
    
    int r;
   String Str;
   NumberFormat nf;

    
    //Count the number of digits in the input number
    int numberCount(int num)
    {
        int cnt=0;

        while (num>0)
        {
          r = num%10;
          cnt++;
          num = num / 10;
        }
        	System.out.println("cnt ki value "+cnt);
          return cnt;
    }


    //Function for Conversion of two digit

    String twonum(int numq)
    {
         int numr, nq;
         String ltr="";

         nq = numq / 10;
         numr = numq % 10;

         System.out.println("value of nq in numq "+numq);
         System.out.println("value of nq in twonum "+nq);
         System.out.println("value of numr in twonum "+numr);
/*         if (numq>19)
           {
         ltr=ltr+tens[nq]+unitdo[numr];
           }
         else
           {
         ltr = ltr+unitdo[numq];
           }
*/
         
         ltr = ltr+unitdo[numq];
         
         System.out.println(numr+" "+ltr+" "+numq);
         return ltr;
    }

    //Function for Conversion of three digit

    String threenum(int numq)
    {
           int numr, nq;
           String ltr = "";

           nq = numq / 100;
           numr = numq % 100;
           
           System.out.println("value of nq in numq "+numq);
           System.out.println("value of nq in tHREEnum "+nq);
           System.out.println("value of numr in tHREEum "+numr);


           if (numr == 0)
            {
            ltr = ltr + unitdo[nq]+digit[1];
             }
           else
            {
            ltr = ltr +unitdo[nq]+digit[1]+twonum(numr);
            }
           
           System.out.println("THREE DIGIT "+numr+" "+ltr+" "+nq);
           return ltr;

    }



    public String numWord(double amt)
    {

    	
        //Defining variables q is quotient, r is remainder
//    	Str="Rupees ";
    	Str="रुपए  ";
    	nf = new DecimalFormat("0.00");
        int num=0,paise=0;
        String amtt = nf.format(amt);
        

          int dot=0;
          
          int len = amtt.length();
          dot = amtt.indexOf(".");

          num=Integer.parseInt(amtt.substring(0, dot));
          
        if (num <= 0) System.out.println("Zero or Negative number not for conversion");

        System.out.println("value of num in line 127 "+num);
        prepWord(num);
        
        paise=Integer.parseInt(amtt.substring(dot+1, len));
        if (paise!=0)
        {
//        	Str = Str+" and Paise";
        	Str = Str+" और पैसे ";
        	prepWord(paise);
        }
//        return Str+" Only.";
      return Str+" सिर्फ .";
    
   
    }
    
    
    public void prepWord(int num)
    {
        int len, q=0, r=0;
        String ltr = " ";
        FigToWord n = new FigToWord();
        
    	while (num>0)
        {

           len = n.numberCount(num);

           System.out.println("value of len "+len);
           //Take the length of the number and do letter conversion

           switch (len)

           {
           		case 9:
           		case 8:
                        q=num/10000000;
                        r=num%10000000;
                        ltr = n.twonum(q);
                        Str = Str+ltr+n.digit[4];
                        System.out.println("case 8 "+Str);
                        num = r;
                        break;

                case 7:
                case 6:
                        q=num/100000;
                        r=num%100000;
                        ltr = n.twonum(q);
                        Str = Str+ltr+n.digit[3];
                        System.out.println("case 6 "+Str);
                        num = r;
                        break;

                case 5:
                case 4:

                		 System.out.println("value of numm "+num);
                         q=num/1000;
                         r=num%1000;
                         ltr = n.twonum(q);
                         Str= Str+ltr+n.digit[2];
                         System.out.println("case 4 "+Str);

                         num = r;
                         break;

                case 3:

                		  System.out.println("value of numm in case3 "+num);
                          if (len == 3)
                              r = num;
                          ltr = n.threenum(r);
                          Str = Str + ltr;
                          System.out.println("case 3 "+Str);

                          num = 0;
                          break;

                case 2:

                         ltr = n.twonum(num);
                         Str = Str + ltr;
                         System.out.println("case 2 "+Str);

                         num=0;
                         break;

                case 1:
                         Str = Str + n.unitdo[num];
                         System.out.println("case 1 "+Str);

                         num=0;
                         break;
                default:

                        num=0;
                        System.out.println("Exceeding Crore....No conversion");
                        System.exit(1);


            }
          }

       }


 	   
    
    
}
