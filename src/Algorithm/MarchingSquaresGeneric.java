package Algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Abstracts.Voxel2D;
import Models.Pentagon;
import Models.Quad;
import Models.Triangle;
import Utils.Vector3;
/**
 * THE GENERIC IMPLEMENTATION WITH TYPE CASTING
 * 
 * @author Cagatay Yapici
 *
 * @param <T>
 */
public class MarchingSquaresGeneric<T> {
	public static MarchingSquaresGeneric marchingsquares = new MarchingSquaresGeneric();

	List<Vector3> vertices;
	List<Integer> triangles;

	public MarchingSquaresGeneric() {
	};

	public MarchingSquaresGeneric(List<Vector3> _vertices, List<Integer> _triangles) {
		vertices = _vertices;
		triangles = _triangles;

	}

	public void InitAlgorithm(List<Vector3> _vertices, List<Integer> _triangles) {
		vertices = _vertices;
		triangles = _triangles;

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

	public List<List<Vector3>> MarchingSquaresNonGeneric(Voxel2D a, Voxel2D b,
			Voxel2D c, Voxel2D d) {
		List<List<Vector3>> retList = new ArrayList<List<Vector3>>();
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

		switch (cellType) {
		case 0:
			return null;
		case 1:
			retList.add(Arrays.asList(new Vector3(a.position), new Vector3(
					a.yEdgePosition), new Vector3(a.xEdgePosition)));

			addTriangle(new Vector3(a.position), new Vector3(a.yEdgePosition),
					new Vector3(a.xEdgePosition));
			break;
		case 2:
			retList.add(Arrays.asList(new Vector3(b.position), new Vector3(
					a.xEdgePosition), new Vector3(b.yEdgePosition)));
			addTriangle(new Vector3(b.position), new Vector3(a.xEdgePosition),
					new Vector3(b.yEdgePosition));

			break;
		case 3:
			retList.add(Arrays.asList(new Vector3(a.position), new Vector3(
					a.yEdgePosition), new Vector3(b.yEdgePosition),
					new Vector3(b.position)));
			addQuad(new Vector3(a.position), new Vector3(a.yEdgePosition),
					new Vector3(b.yEdgePosition), new Vector3(b.position));
			break;
		case 4:
			retList.add(Arrays.asList(new Vector3(c.position), new Vector3(
					c.xEdgePosition), new Vector3(a.yEdgePosition)));
			addTriangle(new Vector3(c.position), new Vector3(c.xEdgePosition),
					new Vector3(a.yEdgePosition));
			break;
		case 5:
			retList.add(Arrays.asList(new Vector3(a.position), new Vector3(
					c.position), new Vector3(c.xEdgePosition), new Vector3(
					a.xEdgePosition)));
			addQuad(new Vector3(a.position), new Vector3(c.position),
					new Vector3(c.xEdgePosition), new Vector3(a.xEdgePosition));
			break;
		case 6:
			retList.add(Arrays.asList(new Vector3(b.position), new Vector3(
					a.xEdgePosition), new Vector3(b.yEdgePosition)));
			retList.add(Arrays.asList(new Vector3(c.position), new Vector3(
					c.xEdgePosition), new Vector3(a.yEdgePosition)));

			addTriangle(new Vector3(b.position), new Vector3(a.xEdgePosition),
					new Vector3(b.yEdgePosition));
			addTriangle(new Vector3(c.position), new Vector3(c.xEdgePosition),
					new Vector3(a.yEdgePosition));
			break;
		case 7:
			retList.add(Arrays.asList(new Vector3(a.position), new Vector3(
					c.position), new Vector3(c.xEdgePosition), new Vector3(
					b.yEdgePosition), new Vector3(b.position)));
			retList.add(Arrays.asList(new Vector3(a.position), new Vector3(
					c.position), new Vector3(c.xEdgePosition), new Vector3(
					b.yEdgePosition), new Vector3(b.position)));

			addPentagon(new Vector3(a.position), new Vector3(c.position),
					new Vector3(c.xEdgePosition), new Vector3(b.yEdgePosition),
					new Vector3(b.position));
			addPentagon(new Vector3(a.position), new Vector3(c.position),
					new Vector3(c.xEdgePosition), new Vector3(b.yEdgePosition),
					new Vector3(b.position));
			break;
		case 8:

			retList.add(Arrays.asList(new Vector3(d.position), new Vector3(
					b.yEdgePosition), new Vector3(c.xEdgePosition)));

			addTriangle(new Vector3(d.position), new Vector3(b.yEdgePosition),
					new Vector3(c.xEdgePosition));
			break;
		case 9:

			retList.add(Arrays.asList(new Vector3(a.position), new Vector3(
					a.yEdgePosition), new Vector3(a.xEdgePosition)));

			retList.add(Arrays.asList(new Vector3(d.position), new Vector3(
					b.yEdgePosition), new Vector3(c.xEdgePosition)));

			addTriangle(new Vector3(a.position), new Vector3(a.yEdgePosition),
					new Vector3(a.xEdgePosition));
			addTriangle(new Vector3(d.position), new Vector3(b.yEdgePosition),
					new Vector3(c.xEdgePosition));
			break;
		case 10:
			retList.add(Arrays.asList(new Vector3(a.xEdgePosition),
					new Vector3(c.xEdgePosition), new Vector3(d.position),
					new Vector3(b.position)));

			addQuad(new Vector3(a.xEdgePosition), new Vector3(c.xEdgePosition),
					new Vector3(d.position), new Vector3(b.position));
			break;
		case 11:
			// retList.add(Arrays.asList( ) );
			Arrays.asList(new Vector3(b.position), new Vector3(a.position),
					new Vector3(a.yEdgePosition), new Vector3(c.xEdgePosition),
					new Vector3(d.position));

			addPentagon(new Vector3(b.position), new Vector3(a.position),
					new Vector3(a.yEdgePosition), new Vector3(c.xEdgePosition),
					new Vector3(d.position));
			break;
		case 12:
			retList.add(Arrays.asList(new Vector3(a.yEdgePosition),
					new Vector3(c.position), new Vector3(d.position),
					new Vector3(b.yEdgePosition)));

			addQuad(new Vector3(a.yEdgePosition), new Vector3(c.position),
					new Vector3(d.position), new Vector3(b.yEdgePosition));
			break;
		case 13:
			retList.add(Arrays.asList(new Vector3(c.position), new Vector3(
					d.position), new Vector3(b.yEdgePosition), new Vector3(
					a.xEdgePosition), new Vector3(a.position)));

			addPentagon(new Vector3(c.position), new Vector3(d.position),
					new Vector3(b.yEdgePosition), new Vector3(a.xEdgePosition),
					new Vector3(a.position));
			break;
		case 14:
			retList.add(Arrays.asList(new Vector3(d.position), new Vector3(
					b.position), new Vector3(a.xEdgePosition), new Vector3(
					a.yEdgePosition), new Vector3(c.position)));

			addPentagon(new Vector3(d.position), new Vector3(b.position),
					new Vector3(a.xEdgePosition), new Vector3(a.yEdgePosition),
					new Vector3(c.position));
			break;
		case 15:
			retList.add(Arrays.asList(new Vector3(a.position), new Vector3(
					c.position), new Vector3(d.position), new Vector3(
					b.position)));
			// retList = Arrays.asList(new Vector3(a.position), new Vector3(
			// c.position), new Vector3(d.position), new Vector3(
			// b.position));
			addQuad(new Vector3(a.position), new Vector3(c.position),
					new Vector3(d.position), new Vector3(b.position));

			break;
		}
		return retList;
	}

	/**
	 * The fifteen cases of the marching squares algorithm
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
	public List<T> MarchingSquares(Voxel2D a, Voxel2D b, Voxel2D c, Voxel2D d) {

		List<T> geoShapes = new ArrayList<T>();

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

		switch (cellType) {
		case 0:
			return null;
		case 1:

			geoShapes.add((T) addTriangle(new Vector3(a.position), new Vector3(
					a.yEdgePosition), new Vector3(a.xEdgePosition)));
			break;
		case 2:
			geoShapes.add((T) addTriangle(new Vector3(b.position), new Vector3(
					a.xEdgePosition), new Vector3(b.yEdgePosition)));

			break;
		case 3:
			// geoShapes.add();
			geoShapes.add((T) addQuad(new Vector3(a.position), new Vector3(
					a.yEdgePosition), new Vector3(b.yEdgePosition),
					new Vector3(b.position)));

			break;
		case 4:
			geoShapes.add((T) addTriangle(new Vector3(c.position), new Vector3(
					c.xEdgePosition), new Vector3(a.yEdgePosition)));

			break;
		case 5:
			geoShapes.add((T) addQuad(new Vector3(a.position), new Vector3(
					c.position), new Vector3(c.xEdgePosition), new Vector3(
					a.xEdgePosition)));

			break;
		case 6:

			geoShapes.add((T) addTriangle(new Vector3(b.position), new Vector3(
					a.xEdgePosition), new Vector3(b.yEdgePosition)));
			geoShapes.add((T) addTriangle(new Vector3(c.position), new Vector3(
					c.xEdgePosition), new Vector3(a.yEdgePosition)));

			break;
		case 7:

			geoShapes.add((T) addPentagon(new Vector3(a.position), new Vector3(
					c.position), new Vector3(c.xEdgePosition), new Vector3(
					b.yEdgePosition), new Vector3(b.position)));
			geoShapes.add((T) addPentagon(new Vector3(a.position), new Vector3(
					c.position), new Vector3(c.xEdgePosition), new Vector3(
					b.yEdgePosition), new Vector3(b.position)));

			break;
		case 8:
			geoShapes.add((T) addTriangle(new Vector3(d.position), new Vector3(
					b.yEdgePosition), new Vector3(c.xEdgePosition)));

			break;
		case 9:

			geoShapes.add((T) addTriangle(new Vector3(a.position), new Vector3(
					a.yEdgePosition), new Vector3(a.xEdgePosition)));
			geoShapes.add((T) addTriangle(new Vector3(d.position), new Vector3(
					b.yEdgePosition), new Vector3(c.xEdgePosition)));

			break;
		case 10:
			geoShapes.add((T) addQuad(new Vector3(a.xEdgePosition),
					new Vector3(c.xEdgePosition), new Vector3(d.position),
					new Vector3(b.position)));
			break;
		case 11:
			geoShapes.add((T) addPentagon(new Vector3(b.position), new Vector3(
					a.position), new Vector3(a.yEdgePosition), new Vector3(
					c.xEdgePosition), new Vector3(d.position)));

			break;
		case 12:
			geoShapes.add((T) addQuad(new Vector3(a.yEdgePosition),
					new Vector3(c.position), new Vector3(d.position),
					new Vector3(b.yEdgePosition)));

			break;
		case 13:
			geoShapes.add((T) addPentagon(new Vector3(c.position), new Vector3(
					d.position), new Vector3(b.yEdgePosition), new Vector3(
					a.xEdgePosition), new Vector3(a.position)));

			break;
		case 14:
			geoShapes.add((T) addPentagon(new Vector3(d.position), new Vector3(
					b.position), new Vector3(a.xEdgePosition), new Vector3(
					a.yEdgePosition), new Vector3(c.position)));

			break;
		case 15:
			geoShapes.add((T) addQuad(new Vector3(a.position), new Vector3(
					c.position), new Vector3(d.position), new Vector3(
					b.position)));

			break;
		}

		return geoShapes;
	}

	private void MarchingSquaresTest(Voxel2D a, Voxel2D b, Voxel2D c, Voxel2D d) {
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
		switch (cellType) {
		case 0:
			return;
		case 1:
			addTriangle(new Vector3(a.position), new Vector3(a.yEdgePosition),
					new Vector3(a.xEdgePosition));
			break;
		case 2:
			addTriangle(new Vector3(b.position), new Vector3(a.xEdgePosition),
					new Vector3(b.yEdgePosition));

			break;
		case 3:
			addQuad(new Vector3(a.position), new Vector3(a.yEdgePosition),
					new Vector3(b.yEdgePosition), new Vector3(b.position));
			break;
		case 4:
			addTriangle(new Vector3(c.position), new Vector3(c.xEdgePosition),
					new Vector3(a.yEdgePosition));
			break;
		case 5:
			addQuad(new Vector3(a.position), new Vector3(c.position),
					new Vector3(c.xEdgePosition), new Vector3(a.xEdgePosition));
			break;
		case 6:
			addTriangle(new Vector3(b.position), new Vector3(a.xEdgePosition),
					new Vector3(b.yEdgePosition));
			addTriangle(new Vector3(c.position), new Vector3(c.xEdgePosition),
					new Vector3(a.yEdgePosition));
			break;
		case 7:

			addPentagon(new Vector3(a.position), new Vector3(c.position),
					new Vector3(c.xEdgePosition), new Vector3(b.yEdgePosition),
					new Vector3(b.position));
			addPentagon(new Vector3(a.position), new Vector3(c.position),
					new Vector3(c.xEdgePosition), new Vector3(b.yEdgePosition),
					new Vector3(b.position));
			break;
		case 8:
			addTriangle(new Vector3(d.position), new Vector3(b.yEdgePosition),
					new Vector3(c.xEdgePosition));
			break;
		case 9:
			addTriangle(new Vector3(a.position), new Vector3(a.yEdgePosition),
					new Vector3(a.xEdgePosition));
			addTriangle(new Vector3(d.position), new Vector3(b.yEdgePosition),
					new Vector3(c.xEdgePosition));
			break;
		case 10:
			addQuad(new Vector3(a.xEdgePosition), new Vector3(c.xEdgePosition),
					new Vector3(d.position), new Vector3(b.position));
			break;
		case 11:
			addPentagon(new Vector3(b.position), new Vector3(a.position),
					new Vector3(a.yEdgePosition), new Vector3(c.xEdgePosition),
					new Vector3(d.position));
			break;
		case 12:
			addQuad(new Vector3(a.yEdgePosition), new Vector3(c.position),
					new Vector3(d.position), new Vector3(b.yEdgePosition));
			break;
		case 13:
			addPentagon(new Vector3(c.position), new Vector3(d.position),
					new Vector3(b.yEdgePosition), new Vector3(a.xEdgePosition),
					new Vector3(a.position));
			break;
		case 14:
			addPentagon(new Vector3(d.position), new Vector3(b.position),
					new Vector3(a.xEdgePosition), new Vector3(a.yEdgePosition),
					new Vector3(c.position));
			break;
		case 15:
			addQuad(new Vector3(a.position), new Vector3(c.position),
					new Vector3(d.position), new Vector3(b.position));

			break;
		}
	}

	private Triangle addTriangle(Vector3 a, Vector3 b, Vector3 c) {
		System.out.println("addTriangle" + a.x + "," + a.y + "," + a.z
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

	private Quad addQuad(Vector3 a, Vector3 b, Vector3 c, Vector3 d) {
		System.out.println("addQuad" + a + b + c + d);
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

	private Pentagon addPentagon(Vector3 a, Vector3 b, Vector3 c, Vector3 d,
			Vector3 e) {
		System.out.println("addPentagon" + a + b + c + d + e);
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
}
