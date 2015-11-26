package Abstracts.Template;

/**
 * 
 * MOUSE POINTING MOTIF
 * CREATE MORE TEMPLATES LIKE THIS CLASS
 * 
 * @author Cagatay Yapici
 *
 */
public class VoxelMotifTypeA {

	protected boolean fillType;

	protected int centerX, centerY, radius;

	public int XStart;

	public int XEnd;

	public int YStart;

	public int YEnd;

	public void Initialize(boolean fillType, int radius) {
		this.fillType = fillType;
		this.radius = radius;
	}

	public void SetCenter(int x, int y) {

		centerX = x;
		centerY = y;

		XStart = centerX - radius;

		XEnd = centerX + radius;

		YStart = centerY - radius;

		YEnd = centerY + radius;

	}

	public boolean Apply(int x, int y, boolean voxel) {
		return fillType;
	}
}
