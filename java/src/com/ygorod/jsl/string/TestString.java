package com.ygorod.jsl.string;

import java.util.Random;

public class TestString {
	public String generateRandomString(int strLength) {
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		Random random = new Random();
		/*
		 * StringBuilder buffer = new StringBuilder(targetStringLength); for (int i = 0;
		 * i < targetStringLength; i++) { int randomLimitedInt = leftLimit + (int)
		 * (random.nextFloat() * (rightLimit - leftLimit + 1)); buffer.append((char)
		 * randomLimitedInt); } String generatedString = buffer.toString();
		 */

		String generatedString = random.ints(leftLimit, rightLimit + 1).limit(strLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		// System.out.println(generatedString);
		return generatedString;
	}

}
