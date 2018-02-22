# Maze

The purpose of this project is to use the dijkstra shortest path finding algorithm and draw it out

## Getting Started



### Prerequisites

I am using appletviewer from Java, so java must be installed before running program


## Running the program
first, clone this repo
then, running the program using
```
appletviewer Maze.html
```

## File required
There is a default test.txt that you can use that draws the wall(-1) and available path(0) on the Component with defined length and width. You can define it yourself too

## What the file and image look like
What the test.txt looks like

![alt text](https://github.com/wenmin518/Maze/blob/master/text.png)


And the corresponding UI component

![alt text](https://github.com/wenmin518/Maze/blob/master/img.png)

### use new.txt if you want to use the full screen
```
type new.txt in the white space and click on load
```

## Rules
### You can click around the white space to pick the starting location, and the second click will be the endlocation
### Then click on go, the shortest path will be drawn
### the second click will have a black wall randomly placed on the path
### another click will allow the starting box to move on the drawn path
### and keep going
### If less than 8 steps are apart from the starting to ending box, then congrats you WIN!!!
### The game losses if there is no path between the starting and ending box.
