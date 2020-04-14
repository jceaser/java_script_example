package com.cherry.thomas.app.scripter.model;

public class GlobalObject implements Global
{
    String name = "Default";

    public String getName(){return "name";}
    public void setName(String name)
    {
        if (name!=null && !name.isEmpty()){this.name = name;}
    }
    public String getValue(){return this.name;}
    public String getData(){return "0 2 3 4 5 9";} 
    public String convert(String raw)
    {
        String clean = raw.replaceAll("[^\\w-]", "_");
        return clean;
    }

}
