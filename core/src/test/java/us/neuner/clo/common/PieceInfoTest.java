package us.neuner.clo.common;

import static org.junit.Assert.*;

import org.junit.Test;

public class PieceInfoTest {

	@Test
	public void testIsSuspectScarlet() {
		Boolean result = PieceInfo.isSuspect(GameEntityId.MissScarlet);
		org.junit.Assert.assertTrue(result);
	}

	@Test
	public void testIsSuspectFailure() {
		Boolean result = PieceInfo.isSuspect(GameEntityId.MissScarletStart);
		org.junit.Assert.assertFalse(result);
	}
}
