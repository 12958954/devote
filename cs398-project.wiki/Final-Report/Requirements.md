## Baseline Definition

- Insert images
- Code snippet: Format code. Later: Syntax highlighting (in in-house; just for Kotlin else consider a library for multiple languages)
- Add title to notes. Rename a note
- AutoSave note. If the user has entered data and exits the application, you should never just discard and - lose the data – prompt the user to save or discard it. Also save button for deliberate save. Later: automatic save
- Create checklists
- Create bullet points


## Functional Requirements

- Menu items for all major functions -> Search for functions under Help
- Make window resizable. Ideally, you should save the size and position of the window, or other application properties, and restore it when it’s relaunched.
- Spell checking -> Different languages 
- Format text: Size, Font, Bold/Italics, etc.
- Remove formatting when pasting text into document
- Shapes and UML diagrams
- Store notes in folders. Export into different formats including its own importable format
- Support keyboard shortcuts (e.g. hotkeys)


## Non-Functional Requirements

- The application should be easy and fast to launch (goal < 400ms).
- The application should be able to handle large amounts of data (display large amount of notes e.g., ~100, render notes with ~10 images in under 1 second (goal < 400ms)).
- The spell check should match the geographical location of the user/computer language.
- Functionality should be discoverable i.e. users should be able to easily figure out what they can and cannot do, will do user testing to ensure this.