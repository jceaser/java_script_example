function main()
{
    count = Number(last.toString())

    //make some basic calls to an object
    print("Last value was: " + count)
    print("Name: " + global.getName())
    print("Value: " + global.getValue())
    print("Hash: " + global.convert("; variable = test[1]") )

    //build a command based on the last call
    avg_command = "+:" + String(count-1) + " " + String(count) + " /"
    print("Running: " + avg_command)
    rpn.calculate(avg_command);
    print("Answer: " + rpn.peek())

    return rpn.peek()
}
