package TESTS;

import static org.junit.Assert.fail;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import Execute.main;
import Utils.Vector3;

/**
 * The Test Class using JUNIT
 * 
 * @author Cagatay Yapici
 *
 *         TEST CASES cell type 1 addTriangle 0.0625,0.0625,0.0/
 *         0.0625,0.125,0.0/ 0.125,0.0625,0.0 cell type 2 addTriangle
 *         1.0625,0.0625,0.0/ 1.0,0.0625,0.0/ 1.0625,0.125,0.0 cell type 4
 *         addTriangle 0.0625,1.0625,0.0/ 0.125,1.0625,0.0/ 0.0625,1.0,0.0 cell
 *         type 8 addTriangle 1.0625,1.0625,0.0/ 1.0625,1.0,0.0/ 1.0,1.0625,0.0
 */
public class LogicTest {

	@Test
	public void case1() {
		//
		// Arrange

		main.InitChunks();

		// // Act
		List<Vector3[]> results = main.EditVoxels(new Vector3(0, 0, 0));
		// 0.0625
		// 0.0625
		// 0.0
		// //Assert
		Assert.assertEquals(results.get(0), new Vector3[] { new Vector3(
				0.0625f, 0.0625f, 0.0f) });

		if (results.size() != 4) {
			fail("wrong result size..");
		}

	}
}
