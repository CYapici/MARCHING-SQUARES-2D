package Utils;
/**
 * POJO2
 * @author Cagatay Yapici
 *
 */
public class Vector3 {

	public float x;
	public float y;
	public float z;

	public Vector3(float a, float b, float c) {
		x = a;
		y = b;
		z = c;
	}

	public Vector3(Vector2 V) {
		x = V.x;
		y = V.y;
		z = 0;
	}
}
