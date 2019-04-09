# Iteration 2

## Documents

- Class diagram: https://creately.com/app?diagid=jt5ucv541
- Sequence diagram: https://drive.google.com/open?id=1TU7be4tzfudrFzkHFQOawKa7VE5z5UXW

## Hand In

Zip "groupXX.zip" containing:

- doc: folder containing the Javadoc documentation of entire system.
- diagrams: folder containing:
  - UML diagrams
  - behavioural diagrams to illustrate every use case.

- src: folder containing the source code of the system.

- system.jar: an executable JAR file of your system.

- **Exclude version management files from the zip.**

  

## Tablr

The goal of the project is to develop a desktop database application.
**In this iteration, the application’s user interface changes from being modebased to being subwindow-based.
The application’s user interface consists of a single top-level window, whose
content area shows zero or more rectangular subwindows on a gray background.
Each subwindow cosists of a border, a title bar, a Close button (--a horizontal
and vertical scrollbar--) and a content area. The user can resize a subwindow by
dragging its borders or its corners, and move it by dragging its title bar, as usual.
The user can close a subwindow by clicking its Close button. 
(--If the height of the content being shown by a subwindow is not greater than 
the height of the subwindow’s content area, its vertical scrollbar is shown as disabled. 
Otherwise, the vertical scrollbar shows a scroll button whose height is such that the ratio of
the height of the scroll button to the height of the scrollbar equals the ratio of
the height of the content area to the height of the content. The user can drag
the scroll button or click the scrollbar above or below the scroll button to make
another part of the content visible in the content area, as usual. The horizontal
scrollbar functions analogously.--)
At any point, if there is at least one subwindow, one subwindow is the active
subwindow. It is shown in front of all other subwindows and it receives keyboard
input, and it is distinguished visually from the other subwindows. Interacting with
any subwindow using the mouse makes that subwindow the active subwindow. If
the active subwindow is closed, and any subwindows remain, of those subwindows
the one that was active most recently becomes again the active subwindow.**

**There are three kinds of subwindows:**



- **A** Tables **subwindow** shows the list of the names of the tables that the
  user has created so far, in a tabular view. The user can create and delete
  tables, edit their name, and open a table. The latter **creates a new** Table
  Design **subwindow** (if the table has no columns) or Table Rows **subwindow**
  (otherwise). **The user can press Ctrl+T at any time to create a new Tables
  subwindow.**

- **A**Table Design **subwindow** shows the list of the columns of a particular
    table, in a tabular view. For each column, the name, the type, whether
    blanks are allowed, and the default value are shown. Whether blanks are
    allowed is shown in the form of a checkbox. The type is String, Email,
    Boolean, or Integer. A column’s default value is shown as a checkbox if the
    column’s type is Boolean, and in textual form otherwise. If a column allows
    blanks and its type is Boolean and its default value is blank, this is shown
    as a grayed-out checkbox. The user can add and remove columns and edit
    their characteristics. The user can press Ctrl+Enter to **create a new** Table
    Rows **subwindow** for the current table.

- **A** Table Rows **subwindow** shows the list of the rows of a particular table,
    in a tabular view. For each row, the value of that row for each of the
    table’s columns is shown. Values for columns of type Boolean are shown
    as checkboxes; other values are shown in textual form. If a row’s value for
    some column is blank and the column’s type is Boolean, this is shown as
    a grayed-out checkbox. The user can add and remove rows and edit their
    values for the table’s columns. The user can press Ctrl+Enter to **create a
    new** Table Design **subwindow** for the current table.

    **A subwindow’s title bar shows the kind of subwindow, as well as, for a Table
    Design or Table Rows subwindow, the name of the table.
    Any changes made through one subwindow are immediately visible in all other
    subwindows showing the same information. However, invalid data entered in one
    subwindow do not propagate to other subwindows. Furthermore, if, for some
    piece of data, an invalid value was entered in one subwindow, and subsequently
    a valid value is entered in another subwindow, the valid value propagates to all
    subwindows showing the same information, including the ones showing an invalid
    value; the valid value overwrites any invalid values and any such invalid values
    are lost.** 

    In any **subwindow** , the width of any of the columns shown in the tabular view
    can be modified by dragging the column header’s right-hand border. Any changes
    made in this way to the layout of the tabular view **are immediately visible in all
    subwindows showing the same information**; for example, if the width of a column
    is changed in **a** Table Rows **subwindow** for a particular table, that column will
    **immediately** have the new width **in any current or future** Table Rows **subwindow**
    for that table. In other words, a separate tabular view layout is associated with
    each table’s Table Rows **view**, as well as with each table’s Table Design view, as
    well as with the Tables **view**.
    **A tabular view’s logical height includes some space below the table where the
    user can double-click to add new rows.**

    

  

  ## Use Cases

  ### 1. Create Table:

  **Main Success Scenario:**

  1. The user double-clicks below the list of tables in a Tables subwindow.

  2. A new table is added to the list. Its name is TableN, where N is such
     that the name is different from the names of the existing tables. The
     new table initially has no columns and no rows.

     

  ### 2. Edit Table

  **Main Success Scenario:**

  1. The user clicks a table name **in a Tables subwindow.**

  2. The system shows a cursor after the table name.

  3. The user edits the name by pressing Backspace to remove characters
     from the end of the table name, and character keys to add characters
     to the end of the table name.

  4. The system shows the updated table name as it is being edited. If
     the current name is empty or equal to the name of another table, the
     system shows a red border around the table name to indicate that it
     is invalid.

  5. The user presses Enter or clicks outside the table name **in the content
     area of the same subwindow** to finish editing the table name.

  6. The system shows the new table name.

     

  ### 3. Delete Table

  **Main Success Scenario:**

  1. The user clicks the margin to the left of a table name **in a Tables
     subwindow.**
  2. The system indicates that the table is now selected. **Note: at any
     one point in time, different tables may be selected in different Tables
     subwindows.**
  3. The user presses the Delete key.
  4. The system removes the table and shows the updated list of tables.

### 4. Open Table

**Main Success Scenario:***

1. The user double-clicks a table name **in a Tables subwindow**.

2. The system **creates a new** Table Design subwindow for the doubleclicked table (if the table has no columns), or **a new** Table Rows **subwindow ** for the double-clicked table (otherwise).

### 5. Add Column

**Main Success Scenario:**

1. The user double-clicks below the list of columns **in a Table Design
   subwindow.**
2. The system adds a new column to the end of the list. Its name is
   ColumnN, where N is such that the column name is different from the
   names of the existing columns; its type is String; blanks are allowed;
   and the default value is blank (i.e. the empty string)2
   . The system
   shows the updated list of columns. All existing rows of the table get a
   blank value as the value for the new column.

### 6. Edit Column Characteristic

**Main Success Scenario:**

1. The user clicks the name of some column in a Table Design subwindow.
2. The system allows the user to edit the column name in the same way
   that it allows the user to edit a table name. A column name is valid
   if it is nonempty and different from the names of the other columns of
   the table.

**Extensions:**

1a. The user clicks the type of some column.

- If the type was String, it becomes Email. If it was Email, it becomes
  Boolean. If it was Boolean, it becomes Integer. If it was Integer, it
  becomes String. However, if the new type is such that the column’s
  default value or the value of some row of the table for this column
  is now invalid, a red border is shown around the type and the user
  must click the type again before they can do anything else in the
  application.

1b. The user clicks the checkbox indicating whether blanks are allowed for
some column.

- If the checkbox was checked, it now becomes unchecked; otherwise,
  it becomes checked. However, if the column’s default value was
  blank or the value of any of the table’s rows for this column was
  blank and blanks are now no longer allowed, a red border is shown
  around the checkbox and the user must click the checkbox again
  before they can do anything else in the **subwindow.**

1c. The user clicks the default value for some column.

- If the column’s type is Boolean and the column allows blanks,
  then if the default value was True, it becomes False; if it was False,
  it becomes blank; and if it was blank, it becomes True. If the
  column’s type is Boolean and the column does not allow blanks,
  then if the default value was True, it becomes False, and if it was
  False, it becomes True. If the column’s type is not Boolean, then
  the system allows the user to edit the value in the same way that
  it allows the user to edit a table name. A default value is valid
  if either the default value is blank and the column allows blanks,
  or the column’s type is String and the value is nonempty, or the
  column’s type is Email and the default value contains exactly one
  @ character, or the column’s type is Integer and the default value
  is the decimal representation (with no extraneous leading zeros) of
  an integer.

### 7. Delete Column

**Main Success Scenario:**

1. The user clicks the margin to the left of some column’s name **in a Table
   Design subwindow.**
2. The system indicates that the column is now selected.
3. The user presses the Delete key.
4. The system removes the column from the list of columns, and removes
   the value for the deleted column from all of the table’s rows. **This
   change is immediately visible in all subwindows that show the table’s
   design or rows.**

### 8. Add Row

**Main Success Scenario:**

1. The user double-clicks below the list of rows **in a Table Rows subwindow.**
2. The system adds a new row to the end of the table. Its value for each
   column is the column’s default value.

### 9. Edit Row Value

**Main Success Scenario:**

1. The user clicks the value of some row for some column **in a Table Rows
   subwindow.**
2. The system updates the value or allows the user to edit the value, in
   the same way that it updates the default value or allows the user to
   edit the default value when the user clicks a column’s default value in
   Table Design mode.

### 10. Delete Row

**Main Success Scenario:**

1. The user clicks the margin to the left of some row **in a Table Rows subwindow.**
   subwindow.
2. The system indicates that this row is now selected.
3. The user presses the Delete key.
4. The system removes the row from the table and shows the updated list
   of rows.
