//import com.sun.tools.javac.comp.Enter;

import java.util.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.plaf.basic.BasicTreeUI;
import java.awt.*;
import java.math.*;

/**Convert class is to take the arabic input of letters and convert them to the roman format
 * @see     JFrame
 * JTextArea's are initialized along with the other variables used in this class
 */
public class Convert extends JFrame  {
    private JTextArea ar2ro = new JTextArea("Arabic");//user chooses whether ar2ro is used or ro2ar
    private JTextArea ro2ar = new JTextArea("Roman");
    private char[] userInput, romanNumber;
    private int[] numOnly;
    private int ten2the, numK, numHundred, numTen, numOne, hundred5, ten5, one5, roNum, k5, temp;

    /**Convert() constructor is to initialize the JFrame and start adding to it, adding also the KeyListener for
     * when ENTER is pressed. When this happens, the KeyListener assigns the roman TextArea to be the return value of the
     * method arabicToRoman. KeyListener also resets ar2ro JTextArea to be empty
     * @Override    if keyPressed -> ENTER, call 'arabicToRoman
     * no return type, since in constructor
     */
    public Convert(){
        super("Arabic <-> Roman");
        setLayout(new FlowLayout());
        Container arRo = super.getContentPane();
        ar2ro.setColumns(10);
        ar2ro.setRows(0);
        ro2ar.setColumns(20);
        ro2ar.setMargin(new Insets(5,5,5,5));
        arRo.add(ar2ro);
        arRo.add(ro2ar);
        ar2ro.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
            if( keyEvent.getKeyChar() == KeyEvent.VK_ENTER){
                ro2ar.setText(arabicToRoman());
                ar2ro.setText("");

//                ar2ro.getText().charAt('\0'-1)
            }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });
        ro2ar.setEditable(false);                               //User is unable to write in the area that produces the output of this program
        ar2ro.setMargin(new Insets(5,5,5,5));
        //ro2ar.setText(arabicToRoman());

    }

    /**arabicToRoman is the method that actually converts the numbers from arabic format to roman numerals
     * The method starts off initializing variables that are used repeatedly throughout the run-time of the program.
     * after the program retrieves input data, it goes through the input one character at a time, ignoring spaces and
     * carriage returns. The majority of this code is repetitive, just making sure that the roman numerals are formatted
     * correctly, using ascii values. After the number of each space is recorded, romanNumber[] stores converted characters to
     * be read into result
     * @return      result -> final conversion in String form
     *
     */
    public String arabicToRoman() {
        ten2the = ar2ro.getText().length();         //gets number of characters in text area
        System.out.println("From Text area: " + ten2the);
        userInput = new char[ten2the];
        numOnly = new int[4];                   //array to hold the user input
//reads in user input for arabic number
        String result = "";
        numK = 0;
        numHundred = 0;
        numTen = 0;
        numOne = 0;
        roNum = 0;
        userInput = ar2ro.getText().toCharArray();
        for (int i = 0; i < userInput.length; i++) {
            if(userInput[i] == 13 || userInput[i] == 0){     //ignore carriage return
                //Do nothing
                i+=1;
            }
            if(userInput[i] >= 48 && userInput[i] <= 57) {
                numOnly[roNum] = userInput[i];
                switch (roNum) {
                    case 0:
                        numK = numOnly[roNum];
                        if (numK == 57) {
                            numK++;
                        } else if (numK >= 52) {                         //If num is greater than 4,
                            if(numK == 52) {
                                numK = 59;
                            }else{k5 = 1; numK -=5;}
                        }
                        break;
                    case 1:
                        numHundred = numOnly[roNum];
                        if (numHundred == 57) {
                            numHundred++;
                        } else if (numHundred >= 52) {                         //If num is greater than 5,
                            if(numHundred == 52) {
                                numHundred = 59;
                                hundred5 = 0;
                            }else{hundred5 = 1; numHundred -=5;}
                        }
                        break;
                    case 2:
                        numTen = numOnly[roNum];
                        if (numTen == 57) {
                            numTen++;
                        } else if (numTen >= 52) {//4
                            if(numTen == 52) {
                                numTen = 59;
                            }else{ten5 = 1; numTen -=5;}
                        }
                        break;
                    case 3:
                        numOne = numOnly[roNum];
                        if (numOne == 57) {         //9
                            numOne++;
                        } else if (numOne >= 52) {      // 4
                            if(numOne == 52) {
                                numOne = 59;
                            }else{ numOne -= 5; one5 = 1;}
                        }
                }roNum++;
            }
        }
        if(roNum == 4 ) {
            if (numK == 58) {numK--;}
            else if(k5 == 1){ numK += 5; k5 --;}
        }
        else if(roNum == 3){         //flip
            numOne = numTen;
            numTen = numHundred;
            numHundred = numK;
            numK = 48;
            one5 = ten5;
            ten5 = hundred5;
            hundred5 = k5;
        }else if(roNum == 2){
            numTen = numK;
            numOne = numHundred;
            numK = 48;
            numHundred = 48;
            one5 = hundred5;
            ten5 = k5;
            one5 = hundred5;
        }else if(roNum == 1){
            numOne = numK;
            numK = 48;
            numHundred = 48;
            numTen = 48;
            one5 = k5;
        }
        numOne -= 48;
        numTen -= 48;
        numHundred -= 48;           //get out of ascii
        numK -= 48;
        romanNumber = new char[50];     //initialize an array large enough to hold roman numeral values
        for(int j = 0; j < romanNumber.length; j++){
            if(numK > j){              //makes sure all of the 'M's get loaded for each thousand input by user
                romanNumber[j] = 77;
            }else if(numHundred > 0 || hundred5 > 0){
                if(numHundred == 10){               //if hundreds place has a 9, format roman code correctly
                    romanNumber[j] = 67;
                    romanNumber[j+1] = 77;
                    j += 1;
                    numHundred = 0;
                    hundred5 = 0;
                }else if(numHundred == 11){                      //format 4 correctly
                    romanNumber[j] = 67;
                    romanNumber[j+1] = 68;
                    j+=1;
                    numHundred = 0;
                    hundred5--;
                }
                else if(hundred5 > 0) {         //if 5 hundred, put a D
                    romanNumber[j] = 68;
                    hundred5--;                 //decrement to 0 meaning there are no more 5 hundreds
                }else if(numHundred > 0){romanNumber[j] = 67; numHundred--;}//if "normal" just add the singular hundred to array

            }else if(numTen > 0 || ten5 > 0){
                if(numTen == 10){
                    romanNumber[j] = (char)88;          // 'X' in ascii, for 10
                    romanNumber[j+1] = (char)67;          // 'C' in ascii, for 100, adding to 90
                    j += 1;
                    numTen = 0;
                }else if(numTen == 11){                      //format 4 correctly
                romanNumber[j] = (char)88;              //'X'
                romanNumber[j+1] = (char)76;            //'L'
                j+=1;
                numTen = 0;
                ten5--;
                }else if(ten5 > 0){
                    romanNumber[j] = (char)76;// 'L' in ascii
                    ten5--;
                }else if(numTen > 0){romanNumber[j] = (char)88; numTen--;} //'X'

            }else if(numOne > 0 || one5 > 0){
                if(numOne == 10) {                          //format 9 correctly
                    romanNumber[j] = (char) 73;             //'I'
                    romanNumber[j+1] = (char) 88;             //'X'
                    j += 1;
                    numOne = 0;
                    one5--;

                }else if( numOne == 11){                      //format 4 correctly
                    romanNumber[j] = (char)73;
                    romanNumber[j+1] = (char)86;
                    j+=1;
                    numOne = 0;
                    one5--;
                }
                else if(one5 > 0){
                    romanNumber[j] = (char)86;              //'V'
                    one5--;
//                    j+=1;
//                    while(numOne > 0){
//                        romanNumber[j] = 73;
//                        j+=1;
//                        numOne--;
//                    }
                }else if(numOne > 0) { romanNumber[j] = (char)73; numOne--;    }        //'I'
            }


        }//end for loop
        for(int z = 0; z <romanNumber.length; z++){         //add roman numeral values to result to be displayed on ro2ar TextArea
            if(romanNumber[z] != 0) {
                result += romanNumber[z];
            }else{z = romanNumber.length;}
        }
        return result;
    }
}
