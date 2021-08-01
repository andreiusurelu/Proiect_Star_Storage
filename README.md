# Proiect_Star_Storage

This project represents the solution of the internship admission homework for Star Storage programme. This homework means the implementation of a simplist management system of
products in a shop. This management consists in actions of buying and adding products.

Firstly the input file, which is of JSON type, will read the initial input, and will contain the initial stock of products, as well as the list of clients. Each product has a name, a category in which it belongs, a current quantity, a price for each unit and a maximum quantity that the shop can keep. Each client will have a name and a current balance.

For storing this info I have defined the classes "Consumer", which stores the username and balance of a client (from here and on I will be refering to clients as consumers, for
the sake of keeping the Client class's name).

The input needs to be correct, or the program will receive an error and will stop the program. For each product, the name and category has to be defined and made of alphabetical characters only, while quantity, price and maximum quantity have to be integers and strictly positive. Quantity cannot be larger than maximum quantity. For each consumer, the username has to be defined and made of alphabetical characters only, while balance has to be an integer and strictly positive.

Considering that this system can receive more types of commands in the future, in order to add and execute commands easier and more efficient, I have used the Command Design Pattern, with which I can dynamically make modifications to the stock and consumers, as well as having the option to keep track of the used commands and even have to option to undo or redo said command modifications. The defined components are Receiver, Invoker, Command and Client.

The Receiver class of this project will be "Shop", which will contain the data structures needed for storing the information on current stock and consumers. For stock information I have used a HashMap, for which the key will be the name of the category, and the value will be another HashMap, which will contain the products part of the said category. The inner HashMap will have the name of the product as a key, and the actual product as its value. The reason I am using an inner HashMap to save the products part of a category is to be easier to find a product by its name alone, and not having to go through all the products to find the specified name. For consumers I will have an ArrayList, which will keep track of the given list of consumers. It is important to mention that these structures must not have duplicates, that is a product or a consumer who already exists will not be either overidden or have a duplicate. The Receiver will perform the actions, and will be the actual component to perform an action dictated by a command. Because there will be only one Receiver object in the project, I have used the Singleton Design Pattern in order to limit it to one instance, as well as being accessible everywhere needed in the program.

The Invoker class of this project will be "Invoker", which will receive a command without knowing how it was implemented, and it will add it to the list of invoked commands and
execute it. The list will be useful for macro recording, undo or redo functionalities, as well as keeping track of the invoked commands or even delay their execution.

The Client class of this project will be "Client", which controls the command execution process. It will have the Invoker instance, to which it will pass the according command.
The Client will receive the actual input file path in its constructor to parse the file and extract the initial data. The Client will have the "executeAction" method, that will
have the name of the invoked command and an undefined number of arguments. It will firstly get the command by name with "getCommand", that will return the requested Command object. This is where new commands will be added to obtain their instance, which will then be executed by the Invoker.

The Command interface of this project will be "Command", which will have the method "execute", with the posibility to have a set of exceptions. Each class that implements the Command interface will store relevant information needed for executing the action that they define.

The homework requirements imply the implementation of a set of commands, which I will go through in detail. I have also created other commands which make the system easier to
use.

The current commands, that the user can call, are as follow:
  PRINT PRODUCTS CATEGORY ${CATEGORY_NAME} -> it will print all the products under the category "CATEGORY_NAME". For each product, on the first position there will be the current number, on the second the name of the product, on the third the quantity and on the fourth the price of the product. They will all be split by spaces. If the category
doesn't exist, a message stating this fact will be printed.
  PRINT PRODUCTS ALL -> it will print all the current products in stock. For each product, on the first position there will be the current number, on the second the name of the product, on the third the quantity, on the fouth its category and on the fifth the price.
  PRINT PRODUCTS ${PRODUCT_NAME} -> it will print the name, quantity and price of the product "PRODUCT_NAME". If the product doesn't exist, it will print an error message.
  PRINT CATEGORIES -> it will print all the current categories, split by a comma.
  BUY ${PRODUCT} ${QUANTITY} FOR ${USERNAME} -> it will simulate the buying process of a product, by updating the product's quantity and consumer's balance. If the product has
0 units left, it will print a message that the product is out of stock. If the consumer's balance is too low, it will print a message that the user cannot buy the product because he has an insufficient balance. If the requested quantity is too large, it will print a message that the user cannot buy the product because it has an insufficient stock. The quantity must be positive, and the product name and username must be alphabetical.
  REPLENISH ${PRODUCT} ${QUANTITY} -> it will replenish the product with the given quantity. If the new quantity would be higher than the max quantity of the product, it will print an error message. The product must exist, its name must be alphabetical, and the quantity must be positive.
  ADD NEW CATEGORY ${CATEGORY_NAME} -> it will add a new category of products. If it already exists, it will print an error message.
  ADD NEW PRODUCT ${NAME} ${CATEGORY} ${QUANTITY} ${PRICE} -> it will add a new product. If the category doesn't exist, the product already exists, or quantity or price are not positive, it will print an error message.
  REMOVE PRODUCT ${NAME} -> it will remove a product, that has its stock empty, else it will print an error message.
  PRINT DISPLAY_MODE -> it will print the current environment, either CONSOLE or FILE ${FILE_PATH}. At the start of the program, it will initially print CONSOLE until it's changed.
  SWITCH DISPLAY_MODE CONSOLE / FILE ${FILE_PATH} -> it will switch the output environment to either console or a given output file. If the file doesn't exist on the disk, it will create it and write in it, else it will append in it. The FILE_PATH must be a valid path to a file and it must have the write permission. I used the Strategy design pattern, in order to give the option to extend the available environments for printing, along with Factory design pattern, in order to obtain a specified instance of a "PrintStrategy".
  HELP -> it will print all the current commands, each having a concise description.
  EXPORT ${FILE_PATH.json} -> it will create a JSON file and print all the current contents of the Receiver object in a JSON file. The FILE_PATH must be a valid JSON file path, and it must have the write permission. If the file doesn't exist on the disk, it will create it and write in it, else it will override all the contents.
  EXIT -> it will exit the program, closing any connections to files.
  ADD NEW CLIENT ${USERNAME} ${BALANCE} -> it will add a new consumer to the list. It will print an error message if the consumer already exists, or if the username or balance are invalid.
  UNDO -> it will undo the last command, if there was a command executed and if the command has this option. Else it will do nothing.
  REDO -> it will redo the last command, if there is at least one undone command. Else it will do nothing.
  
