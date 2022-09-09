## High-level Architecture

We initially came up with a very common high-level architecture consisting of 4 layers, the Presentation Layer, the Business Layer, the Persistence Layer and the Database Layer. The Presentation Layer is implemented in FXML format, which the user directly interacts with. An event occurs when the user performs an action and it invokes the corresponding function in the Business Layer with its logic. Furthermore, the Business Layer communicates with the Persistence Layer, which saves application data between sessions. Over a certain period of time or when a particular event happens (for example, the application shuts down), the Persistence Layer passes application data to the Database Layer to store the data permanently. As you can see, since our requirements do exactly the same thing above, i.e. interacting with users, handling all events' logic and storing notes and related data, this high-level architecture fits our project the best.

![image](uploads/c852c6fcf8c199eec0dc47b19079f12a/image.png)

However, the architecture did not end up with the same diagram that we started with. The difference is that the Persistence Layer and the Business Layer are combined into a single Business Layer. This is because the way we implement the Persistence Layer is to store application data in the data field of each class, which also handles the application logic.

## Design Patterns

- **Model–view–controller**: it perfectly fits our high-level architecture. The Presentation Layer as the view creates an event that triggers the controller that modifies the model based on the application logic in the Business Layer.

- **Observer pattern**: it enforces a form of MVC. The model is the publisher and the view components are the subscribers. After changes are made in the model, it will notify the view to update its contents.

- **Adapter Pattern**: it is to allow communications between application data in object representation and the database.

![image](uploads/85f3f892d75ad82a920805ef758097ec/image.png)

At the end of the project, MVC and the observer pattern are useful and implemented. However, the adapter pattern is not quite useful since we do the communication with the database directly from the model by the SQL queries. It is a bit of an overkill to use an adapter pattern here.

## UML-diagram
The resulting UML diagram is shown below, where the observer pattern can be seen in the interfaces Observer and Subject, and the MVC where the view is inheriting from the Observer, and the model the Subject, and the controller that communicates information between them. Additionally, the layers also become clear, where the presentation layer is the view, the business layer is the controller, and then the persistence layer is the model which uses the MySql class to communicate with the database. 

![UML_cs398](uploads/d2306ede32ee1677772e1ce2d73c09b6/UML_cs398.png)