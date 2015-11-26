// == MARCHING SQUARES 2D JAVA IMPLEMENTATION (c) Cagatay Yapici, 2015 // cagatayyapici@windowslive.com
THIS IS A FREE SOFTWARE AND CAN BE DISTRIBUTED TO ANY TYPE OF PURPOSE
MARCHING SQUARES 2D Alpha.001
TRIANGULATION USING MARCHING SQUARES ALGORITHM
https://en.wikipedia.org/wiki/Marching_squares

You can see the web implementation in http://alpha-001-dot-cagatayyapici-polygonization.appspot.com/

THE PROJECT HAS BEEN TESTED AND WORKS FINE WITH JAVA 

The project Structure:

The Project simply process triangulation with marching squares algorithm. There are 3 type of abstractions in code

1- there is 4 chunks and sub chunks in project subchunks are simply 2D voxels they have state Vector2 position and edge positions 

2- there is a virtual stencil which has area information and shape Abstracts.Template

3- the virtual grid consists of chunks consist of voxels with the dicreasing hierarcy

ALGORITHM check & edit 

***if neighbours are available create shapes using their points with appropriate 15 cases

check- the algorithm checks the mouse click   and looking at its neighbours if their states are true editing them as false 

edit- the 15 cases of marching cubes generates triangulation as triangle , quad and pentagon . other shapes eg hexagon drives from double triangles...

 Structure Design  :
 
The project has 2 different implementations including Generic Programming 

1-Marching Squares Generic Class returns the pojo models ( TRIANGLE QUAD PENTAGON AS A GENERIC LIST )

2-Virtual Grid Class has a non generic 15 cased method which returns Vector2d of Lists.

Returning Pointer

For AJAX CALLS and non generic implementation  List<Vector3[]> data structure is prefered instead of List<List<Vector3> because of 15 predefined marching square cases.
 
Special Data Pointers 

Since java does not have struct , the vector classes created in Utils package are simple pointers having 2 or 3 dimensional constructors.

Testing 

The Projects main method  has been tested with Junit .

Conversion

Can easily be converted to C# (UnityEngine Lib or XNA) since there is vertex and triangle lists in Virtual Grid Class 

EXECUTION:

Example usage in The main class of  Execute package must have following methods  

    InitChunks();
		List<Vector3[]> results = EditVoxels(new Vector3(0, 0, 0));
		
www.cagatayyapici.com

