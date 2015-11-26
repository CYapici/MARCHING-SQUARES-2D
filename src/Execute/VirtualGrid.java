package Execute;

import java.util.ArrayList;
import java.util.List;

import Abstracts.Voxel2D;
import Abstracts.Template.VoxelMotifTypeA;
import Models.Pentagon;
import Models.Quad;
import Models.Triangle;
import Utils.Vector3;
/**
 * CORE OF MAIN IMPLEMENTATION
 * @author Cagatay Yapici
 *
 */
public class VirtualGrid {

	public Vector3 Position;
	public int resolution;
	public VirtualGrid xNeighbour, yNeighbour, xyNeighbour;
	private Voxel2D[] voxels;
	private float voxelSize, gridSize;
	private List<Vector3> vertices;
	private List<Integer> triangles;
	private Voxel2D xValue, yValue, tValue;
 
	public void Initialize(int resolution, float size) {
		this.resolution = resolution;
		gridSize = size;
		voxelSize = size / resolution;
		voxels = new Voxel2D[resolution * resolution];

		xValue = new Voxel2D();
		yValue = new Voxel2D();
		tValue = new Voxel2D();

		for (int i = 0, y = 0; y < resolution; y++) {
			for (int x = 0; x < resolution; x++, i++)
				CreateVoxel(i, x, y);
		}

		vertices = new ArrayList<Vector3>();
		triangles = new ArrayList<Integer>();

		StartTriangulation();
	}

	private void CreateVoxel(int i, int x, int y) {
		voxels[i] = new Voxel2D(x, y, voxelSize);
	}

	/**
	 * IMPLEMENTATION OF LOGIC
	 * 
	 * @param stencil
	 * @return
	 */
	public List<Vector3[]> Process(VoxelMotifTypeA stencil) {

		int xStart = stencil.XStart;
		if (xStart < 0) {
			xStart = 0;
		}
		int xEnd = stencil.XEnd;
		if (xEnd >= resolution) {
			xEnd = resolution - 1;
		}
		int yStart = stencil.YStart;
		if (yStart < 0) {
			yStart = 0;
		}
		int yEnd = stencil.YEnd;
		if (yEnd >= resolution) {
			yEnd = resolution - 1;
		}

		for (int y = yStart; y <= yEnd; y++) {
			int i = y * resolution + xStart;
			for (int x = xStart; x <= xEnd; x++, i++) {
				voxels[i].state = stencil.Apply(x, y, voxels[i].state);
			}
		}

		return StartTriangulation();
	}

	/**
	 * 
	 * 
	 * @return
	 */
	private List<Vector3[]> StartTriangulation() {
		// TO DO IMPLEMENT OTHER METHODS HERE EXCEPT TRIANGULATION
		// SetVoxelColors();
		return Triangulate();
	}

	/**
	 * MAIN TRIANGULATION METHOD
	 * 
	 * @return
	 */
	private List<Vector3[]> Triangulate() {
		List<Vector3[]> vectorSet = new ArrayList<Vector3[]>();
		vertices.clear();
		triangles.clear();

		if (xNeighbour != null)
			xValue.XNeighbourOf(xNeighbour.voxels[0], gridSize);

		List<Vector3[]> result = TriangulateCellRows();
		for (Vector3[] list : result) {
			vectorSet.add(list);
		}
		// if gap exists and neighbour is not null
		if (yNeighbour != null) {
			result = TriangulateGapRows();
			if (result.size() > 0) {
				for (Vector3[] list : result) {
					vectorSet.add(list);
				}
			}

		}
		return vectorSet;

	}

	private List<Vector3[]> TriangulateCellRows() {
		List<Vector3[]> vectorSet = new ArrayList<Vector3[]>();
		int cells = resolution - 1;
		List<Vector3[]> temporary;
		for (int i = 0, y = 0; y < cells; y++, i++) {
			for (int x = 0; x < cells; x++, i++) {

				temporary = MarchingSquaresNonGeneric(voxels[i], voxels[i + 1],
						voxels[i + resolution], voxels[i + resolution + 1]);

				if (temporary != null) {
					for (Vector3[] listItem : temporary) {
						vectorSet.add(listItem);
					}

				}

			}
			if (xNeighbour != null) {
				temporary = TriangulateGapCells(i);

				if (temporary != null) {
					for (Vector3[] listItem : temporary) {
						vectorSet.add(listItem);
					}

				}

			}

		}

		return vectorSet;

	}

	private List<Vector3[]> TriangulateGapCells(int i) {

		Voxel2D dummySwap = tValue;
		dummySwap.XNeighbourOf(xNeighbour.voxels[i + 1], gridSize);
		tValue = xValue;
		xValue = dummySwap;

		return MarchingSquaresNonGeneric(voxels[i], tValue, voxels[i
				+ resolution], xValue);
	}

	private List<Vector3[]> TriangulateGapRows() {
		List<Vector3[]> vectorSet = new ArrayList<Vector3[]>();
		List<Vector3[]> temporary;

		yValue.YNeighbourOf(yNeighbour.voxels[0], gridSize);
		int cells = resolution - 1;
		int offset = cells * resolution;

		for (int x = 0; x < cells; x++) {
			Voxel2D dummySwap = tValue;
			dummySwap.YNeighbourOf(yNeighbour.voxels[x + 1], gridSize);
			tValue = yValue;
			yValue = dummySwap;

			temporary = MarchingSquaresNonGeneric(voxels[x + offset], voxels[x
					+ offset + 1], tValue, yValue);
			if (temporary != null)
				for (Vector3[] listItem : temporary) {
					vectorSet.add(listItem);
				}
		}

		if (xNeighbour != null) {
			tValue.XYNeightbourOf(xyNeighbour.voxels[0], gridSize);

			temporary = MarchingSquaresNonGeneric(voxels[voxels.length - 1],
					xValue, yValue, tValue);
			if (temporary != null)
				for (Vector3[] listItem : temporary) {
					vectorSet.add(listItem);
				}

		}

		return vectorSet;
	}

	/**
	 * 
	 * The fifteen cases of the marching squares algorithm NON GENERIC METHOD 4
	 * AJAX CALLS
	 * 
	 * @param the
	 *            voxel a
	 * @param the
	 *            voxel b
	 * @param the
	 *            voxel c
	 * @param the
	 *            voxel d
	 * @return
	 */

	public List<Vector3[]> MarchingSquaresNonGeneric(Voxel2D a, Voxel2D b,
			Voxel2D c, Voxel2D d) {
		List<Vector3[]> retList = new ArrayList<Vector3[]>();
		int cellType = 0;
		if (a.state) {
			cellType |= 1;
		}
		if (b.state) {
			cellType |= 2;
		}
		if (c.state) {
			cellType |= 4;
		}
		if (d.state) {
			cellType |= 8;
		}

		if (cellType != 0) {
			System.out.println("cell type " + cellType);
		}

		switch (cellType) {
		case 0:
			return null;
		case 1:

			retList.add(new Vector3[] { new Vector3(a.position),
					new Vector3(a.yEdgePosition), new Vector3(a.xEdgePosition) });

			createTriangle(new Vector3(a.position), new Vector3(a.yEdgePosition),
					new Vector3(a.xEdgePosition));
			break;
		case 2:
			retList.add(new Vector3[] { new Vector3(b.position),
					new Vector3(a.xEdgePosition), new Vector3(b.yEdgePosition) });
			createTriangle(new Vector3(b.position), new Vector3(a.xEdgePosition),
					new Vector3(b.yEdgePosition));

			break;
		case 3:
			retList.add(new Vector3[] { new Vector3(a.position),
					new Vector3(a.yEdgePosition), new Vector3(b.yEdgePosition),
					new Vector3(b.position) });
			createQuad(new Vector3(a.position), new Vector3(a.yEdgePosition),
					new Vector3(b.yEdgePosition), new Vector3(b.position));
			break;
		case 4:
			retList.add(new Vector3[] { new Vector3(c.position),
					new Vector3(c.xEdgePosition), new Vector3(a.yEdgePosition) });

			createTriangle(new Vector3(c.position), new Vector3(c.xEdgePosition),
					new Vector3(a.yEdgePosition));
			break;
		case 5:
			retList.add(new Vector3[] { new Vector3(a.position),
					new Vector3(c.position), new Vector3(c.xEdgePosition),
					new Vector3(a.xEdgePosition) });
			createQuad(new Vector3(a.position), new Vector3(c.position),
					new Vector3(c.xEdgePosition), new Vector3(a.xEdgePosition));
			break;
		case 6:
			retList.add(new Vector3[] { new Vector3(b.position),
					new Vector3(a.xEdgePosition), new Vector3(b.yEdgePosition) });
			retList.add(new Vector3[] { new Vector3(c.position),
					new Vector3(c.xEdgePosition), new Vector3(a.yEdgePosition) });

			createTriangle(new Vector3(b.position), new Vector3(a.xEdgePosition),
					new Vector3(b.yEdgePosition));
			createTriangle(new Vector3(c.position), new Vector3(c.xEdgePosition),
					new Vector3(a.yEdgePosition));
			break;
		case 7:
			retList.add(new Vector3[] { new Vector3(a.position),
					new Vector3(c.position), new Vector3(c.xEdgePosition),
					new Vector3(b.yEdgePosition), new Vector3(b.position) });
			// retList.add(new Vector3[]{new Vector3(a.position), new Vector3(
			// c.position), new Vector3(c.xEdgePosition), new Vector3(
			// b.yEdgePosition), new Vector3(b.position)));

			createPentagon(new Vector3(a.position), new Vector3(c.position),
					new Vector3(c.xEdgePosition), new Vector3(b.yEdgePosition),
					new Vector3(b.position));
			// addPentagon(new Vector3(a.position), new Vector3(c.position),
			// new Vector3(c.xEdgePosition), new Vector3(b.yEdgePosition),
			// new Vector3(b.position));
			break;
		case 8:

			retList.add(new Vector3[] { new Vector3(d.position),
					new Vector3(b.yEdgePosition), new Vector3(c.xEdgePosition) });

			createTriangle(new Vector3(d.position), new Vector3(b.yEdgePosition),
					new Vector3(c.xEdgePosition));
			break;
		case 9:

			retList.add(new Vector3[] { new Vector3(a.position),
					new Vector3(a.yEdgePosition), new Vector3(a.xEdgePosition) });

			retList.add(new Vector3[] { new Vector3(d.position),
					new Vector3(b.yEdgePosition), new Vector3(c.xEdgePosition) });

			createTriangle(new Vector3(a.position), new Vector3(a.yEdgePosition),
					new Vector3(a.xEdgePosition));
			createTriangle(new Vector3(d.position), new Vector3(b.yEdgePosition),
					new Vector3(c.xEdgePosition));
			break;
		case 10:
			retList.add(new Vector3[] { new Vector3(a.xEdgePosition),
					new Vector3(c.xEdgePosition), new Vector3(d.position),
					new Vector3(b.position) });

			createQuad(new Vector3(a.xEdgePosition), new Vector3(c.xEdgePosition),
					new Vector3(d.position), new Vector3(b.position));
			break;
		case 11:
			// retList.add(new Vector3[]{ ) );
			retList.add(new Vector3[] { new Vector3(b.position),
					new Vector3(a.position), new Vector3(a.yEdgePosition),
					new Vector3(c.xEdgePosition), new Vector3(d.position) });

			createPentagon(new Vector3(b.position), new Vector3(a.position),
					new Vector3(a.yEdgePosition), new Vector3(c.xEdgePosition),
					new Vector3(d.position));
			break;
		case 12:
			retList.add(new Vector3[] { new Vector3(a.yEdgePosition),
					new Vector3(c.position), new Vector3(d.position),
					new Vector3(b.yEdgePosition) });

			createQuad(new Vector3(a.yEdgePosition), new Vector3(c.position),
					new Vector3(d.position), new Vector3(b.yEdgePosition));
			break;
		case 13:
			retList.add(new Vector3[] { new Vector3(c.position),
					new Vector3(d.position), new Vector3(b.yEdgePosition),
					new Vector3(a.xEdgePosition), new Vector3(a.position) });

			createPentagon(new Vector3(c.position), new Vector3(d.position),
					new Vector3(b.yEdgePosition), new Vector3(a.xEdgePosition),
					new Vector3(a.position));
			break;
		case 14:
			retList.add(new Vector3[] { new Vector3(d.position),
					new Vector3(b.position), new Vector3(a.xEdgePosition),
					new Vector3(a.yEdgePosition), new Vector3(c.position) });

			createPentagon(new Vector3(d.position), new Vector3(b.position),
					new Vector3(a.xEdgePosition), new Vector3(a.yEdgePosition),
					new Vector3(c.position));
			break;
		case 15:
			retList.add(new Vector3[] { new Vector3(a.position),
					new Vector3(c.position), new Vector3(d.position),
					new Vector3(b.position) });
			// retList = new Vector3[]{new Vector3(a.position), new Vector3(
			// c.position), new Vector3(d.position), new Vector3(
			// b.position));
			createQuad(new Vector3(a.position), new Vector3(c.position),
					new Vector3(d.position), new Vector3(b.position));

			break;
		}
		return retList;
	}

	private Triangle createTriangle(Vector3 a, Vector3 b, Vector3 c) {
		System.out.println("addTriangle " + a.x + "," + a.y + "," + a.z
				+ "/    " + b.x + "," + b.y + "," + b.z + "/   " + c.x + ","
				+ c.y + "," + c.z);
		int vertexIndex = vertices.size();
		vertices.add(a);
		vertices.add(b);
		vertices.add(c);
		triangles.add(vertexIndex);
		triangles.add(vertexIndex + 1);
		triangles.add(vertexIndex + 2);
		return new Triangle(a, b, c);
	}

	private Quad createQuad(Vector3 a, Vector3 b, Vector3 c, Vector3 d) {
		System.out.println("addQuad " + a + b + c + d);
		int vertexIndex = vertices.size();
		vertices.add(a);
		vertices.add(b);
		vertices.add(c);
		vertices.add(d);
		triangles.add(vertexIndex);
		triangles.add(vertexIndex + 1);
		triangles.add(vertexIndex + 2);
		triangles.add(vertexIndex);
		triangles.add(vertexIndex + 2);
		triangles.add(vertexIndex + 3);
		return new Quad(a, b, c, d);
	}

	private Pentagon createPentagon(Vector3 a, Vector3 b, Vector3 c, Vector3 d,
			Vector3 e) {
		System.out.println("addPentagon " + a + b + c + d + e);
		int vertexIndex = vertices.size();
		vertices.add(a);
		vertices.add(b);
		vertices.add(c);
		vertices.add(d);
		vertices.add(e);
		triangles.add(vertexIndex);
		triangles.add(vertexIndex + 1);
		triangles.add(vertexIndex + 2);
		triangles.add(vertexIndex);
		triangles.add(vertexIndex + 2);
		triangles.add(vertexIndex + 3);
		triangles.add(vertexIndex);
		triangles.add(vertexIndex + 3);
		triangles.add(vertexIndex + 4);
		return new Pentagon(a, b, c, d, e);
	}

	/**
	 * IMPLEMENT YOUR COLOR VARIATION LOGIC HERE
	 */
	private void SetColorOfVoxels() {
		// TO DO IMPLEMENT YOUR COLOR LOGIC HERE
		// EG Material[] voxelMaterials
		// voxelMaterials[i].color = voxels[i].state ? Colors.Red
	}
}
