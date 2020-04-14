package com.cherry.thomas.app.scripter.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.lang.Float;

public class ReversePolishNotation extends Stack<Float>
{
    //Stack stack = new Stack<Float>()
    String ERR_STACK_EMPTY = "Stack is empty: %s\n";

    /** process commands like:
    <code>3:2 5 * + print ; 20 / print</code>
    */
    public void calculate (String raw)
    {
        if (raw==null) {return;}
        raw = raw.trim();
        if (raw.isEmpty()){return;}
        
        //1. split out multiple commands
        String[] sentence_list = raw.split(";");
        for (String sentence: sentence_list)
        {
            //2. split out each "word" ; commands or numbers
            sentence = sentence.trim();
            String[] words = sentence.split(" ");
            for (String word: words)
            {
                //3. words can be repeated with the :# syntax
                String operand = word;
                int times = 1;
                String[] parts = word.split(":");
                if (parts.length==2)
                {
                    operand = parts[0];
                    try
                    {
                        times = Integer.parseInt(parts[1]);
                    } catch (java.lang.NumberFormatException nfe)
                    {//not a number, assume 1
                        times = 1;
                    }
                }
                //4. operate on the word a set number of times
                for (int c=0; c<times; ++c)
                {
                    //5. if a number, then push, otherwise it's a command
                    try
                    {
                        Float value = new Float(operand);
                        this.push(value);
                    } catch (java.lang.NumberFormatException nfe)
                    {//not a number, must be a command
                        command(operand);
                    }
                }
            }
        }
    }

    void command(String command)
    {
        if (command==null){return;}
        command = command.trim();
        if (command.isEmpty()){return;}
        try
        {
            switch (command)
            {
                case "+": addTwo(); break;
                case "-": minusTwo(); break;
                case "*": multiplyTwo(); break;
                case "/": divideTwo(); break;
                case "print": print(); break;
                case "clear": this.clear(); break;
                case "drop":this.pop(); break;
            }
        }
        catch (java.util.EmptyStackException ese)
        {
            System.err.printf(ERR_STACK_EMPTY, ese.toString());
        }
    }

    void addTwo()
    {
        Float x = this.pop();
        Float y = this.pop();
        Float ans = new Float(y.floatValue() + x.floatValue());
        this.push(ans);
    }

    void minusTwo()
    {
        Float x = this.pop();
        Float y = this.pop();
        Float ans = new Float(y.floatValue() - x.floatValue());
        this.push(ans);
    }

    void multiplyTwo()
    {
        Float x = this.pop();
        Float y = this.pop();
        Float ans = new Float(y.floatValue() * x.floatValue());
        this.push(ans);
    }

    void divideTwo()
    {
        Float x = this.pop();
        Float y = this.pop();
        if (y.floatValue()==0.0)
        {
            System.err.printf("can not divide by zero.\n");
        }
        else
        {
            Float ans = new Float(y.floatValue() / x.floatValue());
            this.push(ans);
        }
    }
    
    void print()
    {
        System.out.printf("%s\n", this.toString());
    }

}
