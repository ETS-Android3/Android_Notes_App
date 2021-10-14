# Android_Notes_App
This app allows the creation and maintenance of personal notes. Any number of notes are allowed (including no notes at all). 
Notes are made up of a title, a note text, and a last-update time.

Notes is saved to (and loaded from) the internal file system in JSON format. If no file is found upon loading, 
the application starts with no existing notes and no errors. (A new JSON file would then be created when new notes are saved).

JSON file loading happens in the onCreate(). Saving happens whenever a new note is added or a note is deleted.

A Note class (with title, note text, and last save date) is created to represent each individual note in the application.

The application is made up of 3 activities. These are described below:
1) Main Activity 
2) Edit Activity
3) About Activity