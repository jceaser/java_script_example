function main()
{
    //make some basic interactions with an object
    print("Name: " + global.getName())
    print("Value: " + global.getValue())

    //send data from one object to another
    rpn.calculate(global.getData());
    rpn.calculate("print");
    
    //pass a simple value to the next script
    return rpn.size()
}
