package pack.Palindromes;
import java.util.Scanner;

public class Palindromes {
    public static void main(String[] args) {
        Scanner in=new Scanner(System.in);
        System.out.print("Введите слова:");
        int i=0;
        while (in.hasNext()) {
            String s=in.next();
            System.out.print(String.valueOf(isPalindrome(s)) +" ");
            i++;
        }
    }
    public static String reverseString(String s) {
        String s1="";
        for (int i=s.length()-1; i>=0; i--)
            s1+=s.charAt(i);
        return s1;
    }
    public static boolean isPalindrome(String s) {
        if (s.equals(reverseString(s)))
            return true;
        else return false;
    }
}
