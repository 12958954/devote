Testing is very important to the developers at Devote. As the product has matured, we have placed a larger emphasis on Test-Driven Development and developed a comprehensive suite of both automated and manual tests.

## Automated Tests
Our automated tests help to break down functionality of different components of our system so that they may be tested independently as well as in conjunction (i.e., unit testing and integration testing). For instance, the correctness of our database commands is checked with a mock table and standard SQL queries, which functions we test with 100% coverage. On the other hand, the Model which stores the state of our application is tested rigorously and by making calls to other components of our system (e.g., our database interface), we are able to do thorough integration testing. As seen in the figure below, when calculating the test coverage of the project, the only method not tested in the Model is a init() function which is necessary to initialize the model together with the view. Additionally, it is not possible to automate testing the notifyObservers() function in the Subject interface as it is connected to the view.   

![Screenshot_2022-04-05_at_13.13.51](uploads/cf8ca109fc9f4d8013a4b011f3465ffe/Screenshot_2022-04-05_at_13.13.51.png)

## Manual Tests
The classes that are not covered by automated tests (those connected to the view) still had to be tested, so we chose to do it manually. For our manual tests, we have a comprehensive list of checks we perform by visual inspection. These changes are significantly harder to automate and include checks of:

- HTMLEditor features (e.g., italics, bold, underline, font size, and highlighting)
- NoteView custom features (e.g., code block formatting and checklists)
- Search is working correctly 
- Changes to the database are shown in the DirectoryView (title, in right folder)
- Changes to the database are shown in the NoteView (content, title)
- Correct folder names are shown as possible to Move notes to
- All buttons and menu items are connected to the correct action

## User Tests
To ensure there was no confusion with design choices such as the mapping of buttons, menu item names and actions, and what functionality was prioritized we used user testing, where a few people in our user group were consulted. Additionally, letting users outside the development team test the application also helped with our manual testing.