package Execute;

import java.util.ArrayList;
import java.util.List;

import Abstracts.Template.VoxelMotifTypeA;
import Utils.Vector3;
/**
 * 
 * USE THIS CLASS AS A CLIENT
 * @author Cagatay Yapici
 *
 */
public class main {
	private String[] fillTypeNames = { "Filled", "Empty" };
	private String[] radiusNames = { "0", "1", "2", "3", "4", "5" };
	private String[] stencilNames = { "Square", "Circle" };

	public static float size = 2;

	public static int voxelResolution = 8;
	public static int chunkResolution = 2;

	public VirtualGrid voxelGridPrefab;

	private static VirtualGrid[] chunks;

	private static float chunkSize;
	private static float voxelSize;
	private static float halfSize;

	private static int fillTypeIndex;
	private static int radiusIndex;
	private static int stencilIndex;

	private static VoxelMotifTypeA[] stencils = { new VoxelMotifTypeA() };

	// public static void main(String[] args) {
	//
	// halfSize = size * 0.5f;
	// chunkSize = size / chunkResolution;
	// voxelSize = chunkSize / voxelResolution;
	// chunks = new VoxelGrid[chunkResolution * chunkResolution];
	//
	// for (int i = 0, y = 0; y < chunkResolution; y++) {
	// for (int x = 0; x < chunkResolution; x++, i++)
	// CreateChunk(i, x, y);
	// }
	//
	// List<List<Vector3>> anan = EditVoxels(new Vector3(-.9f, .9f, 0));
	//
	// int bp = 1;
	// }
	public static void main(String[] args) {
		InitChunks();
		List<Vector3[]> results = EditVoxels(new Vector3(0, 0, 0));
		int bp = 1;
	}

	public static void InitChunks() {
		halfSize = size * 0.5f;
		chunkSize = size / chunkResolution;
		voxelSize = chunkSize / voxelResolution;
		chunks = new VirtualGrid[chunkResolution * chunkResolution];

		for (int i = 0, y = 0; y < chunkResolution; y++) {
			for (int x = 0; x < chunkResolution; x++, i++)
				CreateChunk(i, x, y);
		}
	}

	// List<List<Vector3>> anan = EditVoxels(new Vector3(-.9f, .9f, 0));
	public static List<Vector3[]> EditVoxels(Vector3 point) {
		System.out.println("input is " + point.x + "/" + point.y);
		System.out.println("///");

		List<Vector3[]> resultSet = new ArrayList<Vector3[]>();
		List<Vector3[]> temp;
		int centerX = (int) ((point.x + halfSize) / voxelSize);
		int centerY = (int) ((point.y + halfSize) / voxelSize);

		int xStart = (centerX - radiusIndex - 1) / voxelResolution;
		if (xStart < 0) {
			xStart = 0;
		}
		int xEnd = (centerX + radiusIndex) / voxelResolution;
		if (xEnd >= chunkResolution) {
			xEnd = chunkResolution - 1;
		}
		int yStart = (centerY - radiusIndex - 1) / voxelResolution;
		if (yStart < 0) {
			yStart = 0;
		}
		int yEnd = (centerY + radiusIndex) / voxelResolution;
		if (yEnd >= chunkResolution) {
			yEnd = chunkResolution - 1;
		}

		VoxelMotifTypeA activeStencil = stencils[stencilIndex];
		activeStencil.Initialize(fillTypeIndex == 0, radiusIndex);
		int voxelYOffset = yEnd * voxelResolution;

		for (int y = yEnd; y >= yStart; y--) {
			int i = y * chunkResolution + xEnd;
			int voxelXOffset = xEnd * voxelResolution;
			for (int x = xEnd; x >= xStart; x--, i--) {

				activeStencil.SetCenter(centerX - voxelXOffset, centerY
						- voxelYOffset);
				temp = chunks[i].Process(activeStencil);
				if (temp != null) {
					for (Vector3[] list : temp)
						resultSet.add(list);

				}
				voxelXOffset -= voxelResolution;
			}
			voxelYOffset -= voxelResolution;
		}
		return resultSet;
	}

	private static void CreateChunk(int i, int x, int y) {

		VirtualGrid chunk = new VirtualGrid();
		chunk.Initialize(voxelResolution, chunkSize);
		chunk.Position = new Vector3(x * chunkSize - halfSize, y * chunkSize
				- halfSize, 0);
		chunks[i] = chunk;
		if (x > 0)
			chunks[i - 1].xNeighbour = chunk;

		if (y > 0) {
			chunks[i - chunkResolution].yNeighbour = chunk;
			if (x > 0)
				chunks[i - chunkResolution - 1].xyNeighbour = chunk;
		}
	}

}
