
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JaroWinklerDistance_TransPermitted jwd = new JaroWinklerDistance_TransPermitted();
		System.out.println(jwd.getDistance("a", "abcde"));
		System.out.println(jwd.getDistance("ab", "abcde"));
		System.out.println(jwd.getDistance("abc", "abcde"));
		System.out.println(jwd.getDistance("abcd", "abcde"));
		System.out.println(jwd.getDistance("abcde", "abcde"));
		System.out.println(jwd.getDistance("abcdef", "abcde"));
		System.out.println(jwd.getDistance("abcdefg", "abcde"));
		System.out.println(jwd.getDistance("abcdefgh", "abcde"));
		System.out.println(jwd.getDistance("abcdefghi", "abcde"));
		System.out.println(jwd.getDistance("abcdefghij", "jihgfedcba"));
		System.out.println(jwd.getDistance("abcdefghij", "jihgfedcba"));
	}

}
