# Software-ontwerp

NEW:
Class diagram: edit: https://creately.com/app?diagid=jt5ucv541
               view: https://creately.com/diagram/jt5ucv541




OLD:
domain.model : https://creately.com/diagram/jsmbnb3b2/Qvwf82to9yGRjXDHSXkiQTgFjsc%3D

ui.model : https://creately.com/diagram/jsom4dp21/TZ6Fvy2uSQYEVxPkdzT3zDQ7yA%3D

mvc : https://creately.com/diagram/jsonzdt01/298WBZGvvsVCZMpoVVk5xQITXlA%3D


Roles
|
+- DESIGN : dries & mauro
+- TESTING : steven
+- DOMAIN : laurens

Are you using GRASP?
|
+- where are you using it and explain why.
+- design decisions in terms of GRASP

Defensive programming :
|
+- Client of the public interface of a class cannot bring the object of that class, or objects of connected classes, into an inconsistent state.

Out of scope :
|
+- Persistent storage
+- Security
+- Multi-threading
+- Networking


Testing : 
test every use case (comment steps)
|
+- not only succes stories
+- mind the test coverage (Eclemma)
   +- report the evaluation of the test coverage.

UML :
|
+- Currently using creatly.com

Deliverables:
|
+- One guy uploads
   +- group14.zip
     +- Javadoc
     +- UML Diagrams
     +- Src (without git files) -> see svn export or assignment pdf.
     +- System.jar

Presentation :
|
+- Explain/ discuss the high level design of the software using GRASP (why?)
+- Explain/ discuss most interesting parts of the system on a lower level
+- Discuss extensibility, how is it moduled, change scenario's (e.g. additional domain concepts).
+- Discuss the tests, explain what why
+- Overview (project management wise)
   +- Hours of work [ group work | individual work | study]
+- Add a slide with the roles coresponding each iteration.

DEADLINES :
|
+- Zip (see deliverables) [ 15 March 2019 6 pm ]
+- P2P assessment (read assignment pdf) [ 17 March 2019 6 pm ]

++++++++++++++++++++++++++++++++++++++++++++
++ TABLR +++++++++++++++++++++++++++++++++++
++++++++++++++++++++++++++++++++++++++++++++

3 Modes
|
+- Table Mode
	Shows list of names of the created tables (tabular view).
	- Create + Delete tables
	- Edit tablenames
	- Open table >> Table Design Mode (if no columns) otherwise >> Table Rows Mode

+- Table Design Mode
	Shows list of columns of certain table (tabular view).
	Shows per column :
	- Blanks allowed
	- Default value
	IF Blanks allowed + type = bool + default = blank >> greyed out checkbox
	- Add columns
	- Remove columns
	- Edit columns
	SHORTKEY (to enter Table Rows Mode) : Ctrl + Enter

+- Table Rows Mode
	Shows list of rows of certain table (tabular view).
	Shows per row :
	- Value of that row for each shown column
	- Values of type that are bools >> checkbox
	IF row value for some column is blank + type=bool >> greyed-out checkbox.
	- Add rows
	- Remove rows
	- Edit rows
	SHORTKEY (to enter Table Design Mode) : Ctrl + Enter


Dragging RHS of columns resizes the selected column.
Any changes to the layout of the tabular view should be remembered when the user changes modes.


++++++++++++++++++++++++++++++++++++++++++++
++ USE CASES +++++++++++++++++++++++++++++++
++++++++++++++++++++++++++++++++++++++++++++

------ CREATE TABLE ------------------------
|
+- Prereq : Table is in Table Mode ( can be entered from Table Design or Table Rows Mode by ESC )

+- Main Succes Scenario : 
   +- 1. Double clicks below the list of tables.
      2. A new table is added to the list with name "Table.." -> name should be unique. Has initially no columns and no rows.


------ EDIT TABLE ------------------------
|
+- Prereq : In Table Mode ( can be entered from Table Design or Table Rows Mode by ESC )

+- Main Succes Scenario :
   +- 1. User clicks table name.
      2. System shows cursor after table name.
      3. Edit by BACKSPACE to remove chars at end of table name.
      4. Shows updated table name (live) during update. IF name is empty or not unique >> show RED border.
      5. ENTER or click OUTSIDE table >> finish editing.
      6. Table shows name.	
+- Extensions
   +- 5a. ESC to cancel editing.
	1. Table name 
