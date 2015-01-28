package distance;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JaroWinklerDistance jwd = new JaroWinklerDistance();
		JaroWinklerDistance_TransPermitted jwd2 = new JaroWinklerDistance_TransPermitted();

//		JaroDistance jd = new JaroDistance();
//		JaroWinklerDistance jwd = new JaroWinklerDistance();
//		System.out.println(jwd.getDistance("a", "abcde"));
//		System.out.println(jwd.getDistance("ab", "abcde"));
//		System.out.println(jwd.getDistance("abc", "abcde"));
//		System.out.println(jwd.getDistance("abcd", "abcde"));
//		System.out.println(jwd.getDistance("abcde", "abcde"));
//		System.out.println(jwd.getDistance("abcdef", "abcde"));
//		System.out.println(jwd.getDistance("abcdefg", "abcde"));
//		System.out.println(jwd.getDistance("abcdefgh", "abcde"));
//		System.out.println(jwd.getDistance("abcdefghi", "abcde"));
//		System.out.println(jwd.getDistance("abcdefghij", "jihgfedcba"));
//		System.out.println(jwd.getDistance("abcdefghij", "jihgfedcba"));
		
		ArrayList<String> tmp1 = new ArrayList<String>();
		ArrayList<String> tmp2 = new ArrayList<String>();
		ArrayList<String> tmp3 = new ArrayList<String>();
		ArrayList<String> tmp4 = new ArrayList<String>();
		
		tmp1.add("a");
		tmp1.add("b");
		tmp1.add("c");
		tmp1.add("d");
		tmp1.add("e");
		
		tmp2.add("a");
		tmp2.add("b");
		tmp2.add("e");
		tmp2.add("d");
		tmp2.add("c");
//		tmp2.add("e");
//		tmp2.add("d");
//		tmp2.add("k");
//		tmp2.add("j");
//		tmp2.add("a");
		
		System.out.println(jwd2.getDistance(tmp1, tmp2));
		
//		System.out.println(jwd.getDistance("abcde", "abcde"));
//		System.out.println(jwd.getDistance("abcdef", "abcde"));
//		System.out.println(jwd.getDistance("abcdefg", "abcde"));
//		System.out.println(jd.getDistance("abcd", "abcde"));
//		System.out.println(jd.getDistance("abcde", "abcde"));
//		System.out.println(jd.getDistance("abcdef", "abcde"));
//		System.out.println(jd.getDistance("abcdefg", "abcde"));
		
	}

}
