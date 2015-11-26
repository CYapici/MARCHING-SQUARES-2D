package Abstracts;

import Utils.Vector2;
/**
 * VO
 * @author Cagatay Yapici
 *
 */
public class Voxel2D {

	public boolean state;
	public Vector2 position;
	public Vector2 xEdgePosition, yEdgePosition;

	public Voxel2D(int x, int y, float size) {

		float val1 = (x + 0.5f) * size;
		float val2 = (y + 0.5f) * size;
		position = new Vector2();
		position = new Vector2(val1, val2);

		xEdgePosition = new Vector2(val1, val2);
		xEdgePosition.x += size * 0.5f;
		System.out.println("xEdgePosition.x" + xEdgePosition.x);
		yEdgePosition = new Vector2(val1, val2);
		yEdgePosition.y += size * 0.5f;
		System.out.println("yEdgePosition.y" + yEdgePosition.y);

	}

	public Voxel2D() {
	}

	public void XNeighbourOf(Voxel2D voxel, float offset) {
		state = voxel.state;
		position = voxel.position;
		xEdgePosition = voxel.xEdgePosition;
		yEdgePosition = voxel.yEdgePosition;
		float temp = position.x + offset;
		position = new Vector2(temp, position.y);
		temp = xEdgePosition.x + offset;
		xEdgePosition = new Vector2(temp, xEdgePosition.y);
		temp = yEdgePosition.x + offset;
		yEdgePosition = new Vector2(temp, yEdgePosition.y);
	}

	public void YNeighbourOf(Voxel2D voxel, float offset) {
		state = voxel.state;
		position = voxel.position;
		xEdgePosition = voxel.xEdgePosition;
		yEdgePosition = voxel.yEdgePosition;
		float temp = position.y + offset;
		position = new Vector2(position.x, temp);
		temp = xEdgePosition.y + offset;
		xEdgePosition = new Vector2(xEdgePosition.x, temp);
		temp = yEdgePosition.y + offset;
		yEdgePosition = new Vector2(yEdgePosition.x, temp);
	}

	public void XYNeightbourOf(Voxel2D voxel, float offset) {
		state = voxel.state;
		position = voxel.position;
		xEdgePosition = voxel.xEdgePosition;
		yEdgePosition = voxel.yEdgePosition;
		float tempXValue = position.x + offset;
		float tempYValue = position.y + offset;
		position = new Vector2(tempXValue, tempYValue);
		tempXValue = xEdgePosition.x + offset;
		tempYValue = xEdgePosition.y + offset;
		xEdgePosition = new Vector2(tempXValue, tempYValue);
		tempXValue = yEdgePosition.x + offset;
		tempYValue = yEdgePosition.y + offset;
		yEdgePosition = new Vector2(tempXValue, tempYValue);
	}
}