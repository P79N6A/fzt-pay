package cn.yesway.pay.center.util;

import java.util.Random;

public class RandomUtils {
	public static String randomLetter(int count) {
		if (count < 1) {
			System.out.println("invalid random number count: " + count);
			return null;
		}
		String str = "";
		String arr = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
		for (int i = 0; i < count; i++) {
			int rnd = Math.abs(new Random().nextInt(62));
			str += arr.substring(rnd, rnd + 1);
		}
		return str;
	}
	
	public static void main(String[] args) {
		System.out.println(randomLetter(12));
	}
}
