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

public class Graph
{
	public int adj[][];
   public int numVertices;
   public int rows;
   public int cols;
	public int[] previous;
	private boolean[] visited;
   public int[] ver;
   public Graph(String fileName)
   {
      try
      {
         //Read in the information
         Scanner scanner = new Scanner(new File(fileName));
         rows = scanner.nextInt();
         cols = scanner.nextInt();
         numVertices = rows * cols;
         adj = new int[numVertices][numVertices];
         previous = new int[numVertices];
         visited = new boolean[numVertices];
         ver = new int[numVertices];
         

         for(int k = 0 ; k < numVertices; k++)
         {
            ver[k] = scanner.nextInt();
         }

         makeadj(ver, cols, numVertices);
      }

      catch(Exception e)
      {
         System.out.println("Error occured");
      }

   }

   public void addEdge(int from,int to)
   {
      adj[from][to] = 1;
      adj[to][from] = 1;
   }

   public void makeadj(int[] vertex, int cols, int ver)
   {
      adj = new int[ver][ver];
      for(int i = 0; i < numVertices; i++)
      {
         //starting the possible path
         if(vertex[i] == 0)
         {   
            //check for the top of the current position
            if(i - cols > -1)
            {
               if(vertex[i - cols] == 0) addEdge(i, i-cols);

            }
            //check for the left of the current position
            if((i + 1) % cols != 0)
            {
               if(vertex[i + 1] == 0 ) addEdge(i, i + 1);
            }               
         }
      }
   }


   public boolean isPath(int start, int stop)
   {
      for(int i = 0; i < numVertices; i ++)
      {
         visited[i] = false;
      }
      visit(start);
      if(visited[stop] == true)
      {
         return true;
      }
      return false;
   }


   private void visit(int v)
   {
      visited[v] = true;
      for(int i =0; i < numVertices; i++)
      {
         if(adj[v][i] != 0 && visited[i] == false)
	     	{
	      	visit(i);
	     	}
      }
   }

   //Breath first search to get the shortest path
   public int fewestEdgePath(int start, int stop) 
   {
      for(int i = 0; i < numVertices; i++)
      {
         previous[i] = -1;
         visited[i] = false;
      }

      LinkedList l = new LinkedList();
      int p;
      int pathLength = 0; 
      l.addLast(start);
      visited[start] = true;

      while (!(l.isEmpty() ) ) 
      {
         p = (int) l.removeFirst();

         if (p == stop) 
         {
            //Keep track of the previous Vertex
            while (previous[p] != -1) 
            {
               p = previous[p];
               pathLength++;
            }
            return pathLength;
         }
      
         for (int i = 0; i < numVertices; i++) 
         {
            if (adj[p][i] != 0 && visited[i] != true) 
            {
               l.addLast(i);
               previous[i] = p;
               visited[i] = true;
            }
         }
      }
      System.out.println("Path : " + pathLength );
      return pathLength;
      // throw new Error("No such path exists");
   }

   //Keep the previous elements from start to stop in an array
   public int[] getprevious(int start, int stop)
   {
      int [] pre = new int[fewestEdgePath(start,stop)];
      int i = 0 ;
      int p = stop;
      while (previous[p] != -1) 
      {
         p = previous[p];
         pre[i] = p;
         i++;
      }
      
      return pre;
   }

}
