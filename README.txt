Overview of the source code (for assignment tasks, see Aflevering2.txt)

Package dk.sdu.mmmi.opn.assignment2: A simple system for maintaining a
catalogue of items, where the quantity of each item can be modified.  A
GUI provides access to the system.

ICatalogue: generic interface of a catalogue
IEntry: generic interface of a single entry in the catalogue, comprising
  a product and the quantity of this product
Product: a product in the system, has a name and a price
CatalogueImpl, EntryImpl: implementations of the corresponding interfaces
ClientGUI: a GUI for using the entire system (more below)
ClientController: a controller class encapsulating the logic layer of the
  application
  
A video walkthough of the application is available on the youtube channel
of the course.  To run the program, start the GUI.  The catalogue is 
populated by semi-random items.  To increase or decrease the number of a
given item, enters its name top-right and the change below, and press the
corresponding button (on the right).  To search for product beginning with
a specific prefix, enter this prefix in the lower part of the window and
press "search" (use "reset" to remove and return to the catalogue display).
