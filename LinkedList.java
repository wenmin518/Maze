/**
  *Wenmin He
  *Program 5
  *CPE-103
  */

import java.io.*;
import java.util.Iterator;

public class LinkedList implements Cloneable
{

   private class Node
   {
      private Object item;
      private Node next;
      private Node prev;

      private Node(Object x)
      {
         item = x;
         next = null;
         prev = null;
      }
   }

   // Self explanatory instance variables
   private Node first;
   private Node last;
   private int count;

   // Default Constructor
   public LinkedList()
   {
      first = null;
      last = null;
      count = 0;
   }

   // Insert the given object at the beginning of the list.
   public void addFirst(Object item)
   {
      // Supply details as in the assignment description]
      count++;
      Node n = new Node(item);
      if (last == null) {last = n;}
      
      if (first == null) {first = n;} 

      else 
      {
         first.prev = n;
         n.next = first;
         first = n;
      }	
   }

   // Insert the given object at the end of the list.
   public void addLast(Object item)
   {
      count++;
      Node n = new Node(item);
      if (first == null) {first = n;}

      if (last == null) {last = n;} 

      else 
      {
         last.next = n;
         n.prev = last;
         last = n;
      }
   }

   // Return the number of items in the list
   public int length(){return count;}

   // Determine if the list contains no items
   public boolean isEmpty()
   {
      // Supply details as in the assignment description
      Node p = first;
      if (p == null)
         return true;

      for (int i = 0; i < count; i++) {
         if (p.item != null) {return false;}
         p = p.next;
      }
      return true;
   }

   // (Virtually) remove all items from the list
   public void clear()
   {
      first = null;
      last = null;
      count = 0;
   }

   // Determine if the list contains the given item
   public boolean contains(Object item)
   {
      Node p = first;
      if (p == null) {return false;}
      for (int i = 0; i < count; i++) 
      {
         if (item.equals(p.item)) {return true;}
         p = p.next;
      }
      return false;
   }

   // Remove first item on the list and return it
   
   public Object removeFirst()
   {
      // Supply details as in the assignment description
      if (this.isEmpty()) {throw new Error("The LinkedList is empty");}
      Object obj = first.item;
      if (first == last) 
      {
         first = null;
         last = null;
         count = 0;
      } 

      else 
      {
         count--;  
         first = first.next;
         first.prev = null;     
      }
      return obj;
   }

   // Remove last item on the list and return it
   public Object removeLast()
   {
      // Supply details as in the assignment description
      if (this.isEmpty()) {throw new Error("The LinkedList is empty");}

      Object obj = last.item;
      
      if (first == last) 
      {
         last = null;
         first = null;
         count = 0;
      }

      else
      {
         count--;
         last = last.prev;
         last.next = null;
      }

      return obj;
   }
   
   // Determine if two LinkedLists are equal    
   public boolean equals(Object obj)
   {
      // Supply details as in the assignment description

      if (obj== null)
         return false;
      if (!(obj instanceof LinkedList)) {return false;}

      LinkedList list = (LinkedList) obj;

      if (this.count != list.count){return false;}

      Node p = this.first; 
      Node q = list.first;

      while( p!= null && q != null)
      {
         if(!(p.item.equals(q.item))) {return false;}
         if(p.item == null) {return false;}
         p = p.next;
         q = q.next;
      }

      return true;
   }

   //remoe object in the linkedlist
   public boolean remove(Object obj)
	{
      Node p = first;
   	for(int i = 0; i < this.length(); i++)
   	{
   		if(obj.equals(p.item))
   		{
   			if(p == first) {this.removeFirst();}
   			else if( p == last) {this.removeLast();}
            else
   			{
   				p.prev.next = p.next;
   				p.next.prev = p.prev;
   			}
   			return true;
   		}

   		p = p.next;
   	}
   	return false;
   }

   //copy the linkedList
	public Object clone()
	{
		LinkedList theClone = new LinkedList();
		Node p = this.first;
		
		while( p != null)
		{
			theClone.addLast(p.item);
			p=p.next;
		}

		return theClone;
	}

	public Iterator iterator()
	{
		return new itr();
	}

	public class itr implements Iterator
	{
		private Node current;
		public itr() {current = first;}


   	public boolean hasNext()
   	{
   		if(current == null) {return false;}			
         return true;
   	}

   	public void remove() {throw new UnsupportedOperationException();}

   	public Object next()
   	{
   		if(current == null) {throw new Error(" No items in the list.");}			
   		Object temp = current.item;
   		current = current.next;
   		return temp;
   	}
   }
	
}


