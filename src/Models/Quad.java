package Models;

import Utils.Vector3;
/**
 * 
 * @author Cagatay Yapici
 *
 */
public class Quad {
	private Vector3 point1;
	private Vector3 point2;
	private Vector3 point3;
	private Vector3 point4;

	public Quad(Vector3 a1, Vector3 a2, Vector3 a3, Vector3 a4) {

		point1 = a1;
		point2 = a2;
		point3 = a3;
		point4 = a4;
	}
}
