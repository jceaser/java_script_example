package com.cherry.thomas.app.scripter;

import com.cherry.thomas.app.scripter.model.Global;
import com.cherry.thomas.app.scripter.model.GlobalObject;
import com.cherry.thomas.app.scripter.model.ReversePolishNotation;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import javax.script.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

/**
 * Run the application, load a script and execute it against the application 
 * objects
 * @author thomas.cherry@gmail.com
 */
public class App
{
    /**********************************/
    // #mark - Parameters

    /** display a help message */
    @Parameter(names = "--help", help = true)
    private boolean help;
    
    @Parameter (names={"-e", "--engine"},
        description="Name of the java scripting engine to use")
    String engine_name = "nashorn";

    @Parameter (names={"-s", "--script"}, description="script to run")
    List<String> scripts = new ArrayList<>();

    @Parameter (names={"-", "--standard-in"},
        description="read script from standard in")
    boolean read_standard_in = false;

    /**********************************/
    // #mark - Properties

    /** declare this at the app level so that multiple calls will cascade */
    ScriptEngine engine = null;

    /** a Plain Old Java Object used across all scripts */
    public Global global = new GlobalObject();

    /** A calculator for use in the scripting environment */
    public ReversePolishNotation rpn = new ReversePolishNotation();

    /**********************************/
    // #mark - functions

    public static void main( String[] args )
    {
        App app = new App();
        try{JCommander.newBuilder().addObject(app).build().parse(args);}
        catch (com.beust.jcommander.ParameterException e)
        {
            e.usage();
            System.exit(0);
        }

        Object last = new String("");

        //run all the scripts from the command line
        for (String script: app.scripts)
        {
           try
            {
                Reader input = new FileReader(script);
                last = app.runScript(input, last);
            } catch ( java.io.FileNotFoundException fnfe) {
                System.err.printf("no file: %s\n", fnfe.toString());
            } catch ( java.io.IOException ioe) {
                System.err.printf("io exception: %s\n", ioe.toString());
            }
        }

        //check the standard input for a script
        if (app.read_standard_in)
        {
            InputStreamReader in = new InputStreamReader(System.in);
            Reader input = new BufferedReader(in);
            last = app.runScript(input, last);
        }
    }
    
    /**********************************/
    // #mark - Methods

    /**
    with a given script, run it in the engine
    */
    Object runScript(Reader input, Object last)
    {
        Object ret = new String("");
        
        //short cuts out of here
        if (input==null) {return ret;}
        
        if (this.engine==null) {
            //first time here, create the engine
            ScriptEngineManager manager = new ScriptEngineManager();
            this.engine = manager.getEngineByName(this.engine_name);
        }
        
        //variables for the scripting env
        engine.put("rpn", this.rpn);
        engine.put("global", this.global);
        engine.put("last", last);

        try
        {
            engine.eval(input);
            Invocable inv = (Invocable) engine;
            try
            {
                ret = inv.invokeFunction("main", "no options");
                //System.out.printf("return value: %s\n", ret.toString());
            } catch(java.lang.NoSuchMethodException nsme)
            {
                System.err.printf("No such method: %s\n", nsme.toString());
            }
        } catch (javax.script.ScriptException se)
        {
            System.err.printf("script error: %s\n", se.toString());
        }
        return ret;
    }

}
