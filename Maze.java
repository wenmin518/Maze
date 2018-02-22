/**
  *Wenmin He
  *Program 5
  *CPE-103
  */
import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;
import java.util.Iterator;
import javax.swing.*;
import java.util.Scanner;
import java.io.File;
import java.util.*;


public class Maze extends Applet {
   
   // array that holds the maze info
   private int[][] maze;

   //number of rows and columns in the maze
   private int rows, cols;

   // initial size of maze - if bigger may go off window
   private final int MAXROWS = 20;
   private final int MAXCOLS = 30;
   
   // size of each block in pixels
   private final int blkSize = 20;
   
   //inner class that displays the maze
   private MazeCanvas mazeField;

   // everything is put on this panel   
   private JPanel mazePanel;

   // label, textfield, string, and load button for the file
   private JLabel fileLabel, messageLabel;
   private JTextField fileText;
   String fileName;
   String message = "Massage...";
   private JButton fileButton;
   private JButton goButton;
   private JFrame frame;
   //this listener object responds to button events
   private ButtonActionListener buttonListener;
   private MouseEventListener mouseListener;

   //these two instance variables controls the the mouse inputs
   private boolean firstClick = false;
   private boolean secondClick = false;
   private boolean buttonClick = false;
   private boolean firstButtonClick = false;
   private boolean gameEnd = false;
   private int startPosition;
   private int endingPosition;

   //Instance variables to keep track of the colors
   private final int START = 1;
   private final int END = 2;
   private final int PATH = 3;
   private final int WALL = -1;
   private final int CLEAR = 0;

   //Instance variables of the Graphs
   private Graph g;
   private int[] pre;
   private int nextStep;
   private int randomBlock;
   private int[] vertex;

   // this method sets up the canvas and starts it off
   public void init() {
      System.out.println("Maze started"); // goes to console 
      
      mouseListener = new MouseEventListener();
      buttonListener = new ButtonActionListener();
               
      // the mazePanel is the panel that contains the maze interfaces, including
      // the buttons and output display
      mazePanel = new JPanel();
      // Y_AXIS layout places components from top to bottom, in order of adding
      mazePanel.setLayout(new BoxLayout(mazePanel, BoxLayout.Y_AXIS));
      
      // components for loading the filename
      fileLabel = new JLabel("File name:");
      mazePanel.add(fileLabel);
      fileText = new JTextField("", 20);
      mazePanel.add(fileText);
      fileButton = new JButton("Load File");
      mazePanel.add(fileButton);
      fileButton.addActionListener(buttonListener);

      
      // this is where the maze is drawn
      // if you add more to this layout after the mazeField, 
      //   it may be below the bottom of the window, depending on window size
      mazeField = new MazeCanvas();
      mazePanel.add(mazeField);
      mazeField.addMouseListener(mouseListener);

      goButton = new JButton("Go");
      mazePanel.add(goButton);
      goButton.addActionListener(buttonListener);

      messageLabel = new JLabel("");
      messageLabel.setText(message);
      mazePanel.add(messageLabel);
      // now add the maze panel to the applet
      add(mazePanel);


   }
   
   // this object is triggered whenever a button is clicked
   private class ButtonActionListener implements ActionListener 
   {
      public void actionPerformed(ActionEvent event) 
      {
         try
         {
            // find out which button was clicked 
            Object source = event.getSource();
            if (source == fileButton)
            {
               fileName = fileText.getText();
               makeMaze(fileName);

               //reset the all the clicks
               firstClick = false;
               secondClick = false;
               buttonClick = false;
               firstButtonClick = false;
               gameEnd = false;
               messageLabel.setText("Message...");
               g = new Graph(fileName);
            }

            if(source == goButton && !gameEnd)
            {
               messageLabel.setText("path : " + g.fewestEdgePath(startPosition, endingPosition));

               vertex = g.ver;  
               if(g.isPath(startPosition, endingPosition) == false)
               {
                  //Display message
                  messageLabel.setText("Game Over");
               }

               //------------------------------------------------
               //  The drawing of the Panel when click go button
               //------------------------------------------------
               else
               {
                  if(!buttonClick)
                  {  
                     pre = g.getprevious(startPosition, endingPosition);

                     //Draw the path from the start to the end
                     for(int i = 0; i < pre.length-1; i ++)
                     {  
                        maze[pre[i]/cols][pre[i]%cols] = PATH;                  
                     }


                     if(firstButtonClick)
                     {
                        nextStep = pre[pre.length-2];
                        // Paint the next starting position in them aze
                        startPosition = nextStep;
                        maze[startPosition / cols][startPosition % cols] = START;
                     }

                     mazeField.paint(mazeField.getGraphics());

                     //set clicks to perform next button action
                     buttonClick = true;
                     firstButtonClick = true;

                     //Game ends when the starting block is within 8 step from end block 
                     if(pre.length < 8)
                     {
                        mazeField.paint(mazeField.getGraphics());
                        gameEnd = true;
                        messageLabel.setText("We Have A Winner");
                     }

                  }
   
                  else if(buttonClick)
                  {
                     //generating random block
                     Random rand = new Random();
                     int randNum = rand.nextInt(pre.length-2);
                     randomBlock = pre[randNum];

                     //Change the vertex and make new adj materis for shortest path
                     vertex[randomBlock] = -1;
                     g.ver = vertex;
                     g.makeadj(vertex, cols, cols*rows);

                     //drawing the wall
                     maze[randomBlock / cols][randomBlock % cols] = WALL;
                     mazeField.paint(mazeField.getGraphics()); 

                     //reset click to perform the next action
                     buttonClick = false;

                     //Clear the old path
                     for(int i = 0; i < pre.length; i++)
                     {
                        if(i != randNum)
                        {
                           maze[pre[i] / cols][pre[i] % cols] = CLEAR;
                        }
                     }
                  }
               }
            }
         }
         catch(Exception e)
         {
            if(firstClick == false) {messageLabel.setText("No input files");}
            else {messageLabel.setText("Error occured");}
         }
      } 
   }

   private class MouseEventListener implements MouseListener
   {
      public void mouseClicked(MouseEvent e)
      {
         // location on the mazeCanvas where mouse was clicked
         // upper-left is (0,0)
         
         int startX = e.getX();
         int xpos = startX / blkSize;
         int startY = e.getY();
         int ypos = startY / blkSize;

         //-----------------------------------
         //Starting and ending location set up
         //-----------------------------------

         if(!firstClick)
         {
            if(maze[ypos][xpos] == -1) 
            {
               messageLabel.setText("Can not put Startiing block on the wall");
            }

            else
            {
               //Draw and set up the starting location
               maze[ypos][xpos] = START;
               startPosition = ypos * cols + xpos;
               firstClick = true;
               mazeField.paint(mazeField.getGraphics());
            }
         }

         else if(firstClick && !secondClick)
         {

            if(maze[ypos][xpos] == -1) 
            {
               messageLabel.setText("Can not put the Ending block on the wall");
            }

            else
            {  
               //draw and set up the ending location
               maze[ypos][xpos] = END;
               endingPosition = ypos* cols + xpos;
               secondClick = true;
               mazeField.paint(mazeField.getGraphics());
            }
         }
         
     }
      
      public void mousePressed(MouseEvent e) { }
      public void mouseReleased(MouseEvent e) { }
      public void mouseEntered(MouseEvent e) { }
      public void mouseExited(MouseEvent e) { }
   }

   public boolean makeMaze(String fileName)
   {
      try
      {
         Scanner scanner = new Scanner(new File(fileName));
         rows = scanner.nextInt();
         cols = scanner.nextInt();
         maze = new int[rows][cols];
         //fill out maze matrix
         for(int i=0; i<rows; i++)
         {
            for(int j=0; j<cols; j++)
            {
               maze[i][j] = scanner.nextInt();
            }
         }
         // now draw it
         mazeField.paint(mazeField.getGraphics());
         return true;
      }
      catch(Exception e)
      {
         return false;
      }
   }
           
   class MazeCanvas extends Canvas {
      // this class paints the output window 
       
     // the constructor sets it up
      MazeCanvas() {
         rows = MAXROWS;
         cols = MAXCOLS;
         maze = new int[MAXROWS][MAXCOLS];
         setSize(cols*blkSize, rows*blkSize);
         setBackground(Color.white);

      }
       
      public void paint(Graphics g)
      {
         g.setColor(Color.white);
         g.fillRect(0, 0, cols*blkSize, rows*blkSize);
         
         for (int i=0; i<rows; i++)
         { 
            for (int j=0; j<cols; j++)
            {
               if (maze[i][j] == WALL)
               {
                  // location is a wall
                  g.setColor(Color.black);
               }
               else if(maze[i][j] == START)
               {
                  // when the location is the starting position
                  g.setColor(Color.green);
               }
               else if(maze[i][j] == END)
               {
                  //when location is ending position
                  g.setColor(Color.red);
               }
               else if(maze[i][j] == PATH)
               {
                  //when location is a part of path
                  g.setColor(Color.yellow);
               }
               else
               {
                  // location is clear
                  g.setColor(Color.white);
               }
               // draw the location
               g.fillRect(j*blkSize, i*blkSize, blkSize, blkSize);
            }
         }
      }
   }
}
