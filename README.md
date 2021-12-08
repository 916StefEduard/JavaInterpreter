# JavaInterpreter
For college homework, I had to implement the basic features of a Java Interpreter. 
I had to develop an execution stack and a system table which keeps track of all variables and their values.
IfStatement, AssignStatement and CompareStatement, just to name a few have been coded from scratch,as well all the data types.
.Java heap and thread statements have also been implemented.The coded Interpreter takes a 
chained value of those statements and computes and prints the correct result. (Also prints the steps taken) 


![image](https://user-images.githubusercontent.com/72076037/145272745-a43ee153-850c-4342-9c15-00897137fcdf.png)

This code will execute the statements and put them on the Execution Stack. Then in the main class of the project
they will be executed one by one each time the current statement that is executed is printed on the screen as well
as the SystemTable(table of variables with their assigned values), HeapTable(with their heap values and addresses),
FileTable(the name of files that are opened).

![image](https://user-images.githubusercontent.com/72076037/145274983-2ec3f34a-90cd-4d7a-bdfb-6fc2ca487ffc.png)
![image](https://user-images.githubusercontent.com/72076037/145275197-af3e47be-7f27-43df-ade1-c467b84bb18d.png)

The statement above contains the fork statement which will split the program into 2 threads, one which contains the 
statements in the fork and the other which has the next statements outside of the fork statement.

