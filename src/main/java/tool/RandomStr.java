package tool;

public class RandomStr {
	
	public static String getRandomStr() {
		String randomStr ="";
		int value = (int) (Math.random() * 1000000);
		for (int i = 0; i < 6; i++) {
			int temp = value % 10;
			int addTemp = (int) (Math.random() * 18) + 64;
			if (temp % 2 == 0) {
				randomStr += (char) (temp + addTemp);
			} else {
				randomStr += temp;
			}
			value /= 10;
		}
		return randomStr;
	}
	
	public static void main(String[] args) {
		System.out.println(getRandomStr());
	}
}
