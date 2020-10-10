package ru.ncedu.wortellen.trickytasks2.guesstheword;
import java.util.Scanner;
import java.io.*;

public class Game {
    private String[] bank = new String[1];
    private String word; //слово, выбранное из банка
    private char[] word_ch; // его представление в виде массива char
    private char[] hidden_word; // "маска" этого слова
    private int trial = 0; // попытки
    private int check = 0; // проверка, если слово было угадано посимвольно

    public void Start() throws FileNotFoundException { // начало игры
        BankFromFile();
        word = ChooseWord();
        System.out.println("Yo, it's guess the word game!");
        System.out.println("Enter your name");
        Scanner in = new Scanner(System.in);
        ToResultsFile("Player: "+in.nextLine()+"  guessed the word \""+word+"\"");
        word_ch = word.toCharArray();
        hidden_word = new char[word_ch.length];
        for (int i = 0;i< word_ch.length;i++)
        {
            hidden_word[i]='_';
        }
        Process();
    }
    private void Process(){ // процесс игры
        PrintWord(hidden_word);
        Scanner in = new Scanner(System.in);
        while (true){
            System.out.println("Key in one character or your guess word: ");
            trial++;
            String test = in.next();
            if (test.length()==1)
            {
                char[] tmpp =test.toCharArray();
                for (int i = 0;i< word_ch.length;i++) {
                    if (word_ch[i] == tmpp[0]) {
                        hidden_word[i] = word_ch[i];
                        check++;
                    }
                    if (check==word_ch.length){
                        System.out.println("Congratulations!!!");
                        System.out.println("You got in " + trial +" trials");
                        ToResultsFile(" in "+trial+" trials\n");
                        System.exit(0);
                    }
                }
                System.out.print("Trial "+trial+": ");
                PrintWord(hidden_word);
            }
            else {
                if(word.equals(test)) {
                    System.out.println("Congratulations!!!");
                    System.out.println("You got in " + trial +" trials");
                    ToResultsFile(" in "+trial+" trials\n");
                    System.exit(0);
                }
                else {
                    ToResultsFile( " and lost the game ");
                    System.out.println("You lost the game D:\n");
                    System.exit(0);
                }
            }

        }

    }
    private String ChooseWord (){ //рандомный выбор слова
        int tmp = (int) (Math.random()*bank.length);
        return bank[tmp];
    }
    private void PrintWord(char [] w){ //вывод массива символов
        for (int i = 0;i< w.length;i++)
        {
            System.out.print(w[i]);
        }
        System.out.println("");
    }
    private void BankFromFile() throws FileNotFoundException{
        File file = new File("Bank.txt");
        Scanner scanner= new Scanner(file);
        while (scanner.hasNextLine()){
            bank[bank.length-1]=scanner.nextLine();
            if(scanner.hasNextLine())
                bank = extendArraySize(bank);
        }
    }
    private  String[] extendArraySize(String [] array){ //увеличение размера массива на 1
        String [] temp = array.clone();
        array = new String[array.length + 1];
        System.arraycopy(temp, 0, array, 0, temp.length);
        return array;
    }
    private void ToResultsFile(String s){ // запись в файл
        try(FileWriter writer = new FileWriter("Results.txt", true))
        {
            writer.write(s);
            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }

    }
}
