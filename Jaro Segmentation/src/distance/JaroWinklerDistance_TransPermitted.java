package distance;
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Similarity measure for short strings such as person names.
 * <p>
 * @see <a href="http://en.wikipedia.org/wiki/Jaro%E2%80%93Winkler_distance">http://en.wikipedia.org/wiki/Jaro%E2%80%93Winkler_distance</a>
 */
public class JaroWinklerDistance_TransPermitted {

	float threshold = 0.7f;

	/**
	 * Creates a new distance metric with the default threshold
	 * for the Jaro Winkler bonus (0.7)
	 * @see #setThreshold(float)
	 */
	public JaroWinklerDistance_TransPermitted() {}

	private int[] matches(ArrayList<String> s1, ArrayList<String> s2) {
		ArrayList<String> max, min;
		if (s1.size() > s2.size()) {
			max = s1;
			min = s2;
		} else {
			max = s2;
			min = s1;
		}
		int range = max.size(); //Math.max(max.size() / 2 - 1, 0);
		int[] matchIndexes = new int[min.size()];
		Arrays.fill(matchIndexes, -1);
		boolean[] matchFlags = new boolean[max.size()];
		int matches = 0;
		for (int mi = 0; mi < min.size(); mi++) {
			String c1 = min.get(mi);
			for (int xi = Math.max(mi - range, 0), xn = Math.min(mi + range + 1, max.size()); xi < xn; xi++) {
				if (!matchFlags[xi] && c1.equals(max.get(xi))) {
					matchIndexes[mi] = xi;
					matchFlags[xi] = true;
					matches++;
					break;
				}
			}
		}
		String[] ms1 = new String[matches];
		String[] ms2 = new String[matches];
		for (int i = 0, si = 0; i < min.size(); i++) {
			if (matchIndexes[i] != -1) {
				ms1[si] = min.get(i);
				si++;
			}
		}
		for (int i = 0, si = 0; i < max.size(); i++) {
			if (matchFlags[i]) {
				ms2[si] = max.get(i);
				si++;
			}
		}
		int transpositions = 0;
		for (int mi = 0; mi < ms1.length; mi++) {
			if (!ms1[mi].equals(ms2[mi])) {
				transpositions++;
			}
		}
		int prefix = 0;
		for (int mi = 0; mi < min.size(); mi++) {
			if (s1.get(mi).equals(s2.get(mi))) {
				prefix++;
			} else {
				break;
			}
		}
//		return new int[] { matches, transpositions / 2, prefix, max.size() };
		return new int[] { matches, 0, prefix, max.size() };
	}

	public float getDistance(ArrayList<String> s1, ArrayList<String> s2) {
		int[] mtp = matches(s1, s2);
		float m = mtp[0];
		if (m == 0) {
			return 0f;
		}
		float j = ((m / s1.size() + m / s2.size() + (m - mtp[1]) / m)) / 3;
		float jw = j < getThreshold() ? j : j + Math.min(0.1f, 1f / mtp[3]) * mtp[2]
				* (1 - j);
		return jw;
	}

	/**
	 * Sets the threshold used to determine when Winkler bonus should be used.
	 * Set to a negative value to get the Jaro distance.
	 * @param threshold the new value of the threshold
	 */
	public void setThreshold(float threshold) {
		this.threshold = threshold;
	}

	/**
	 * Returns the current value of the threshold used for adding the Winkler bonus.
	 * The default value is 0.7.
	 * @return the current value of the threshold
	 */
	public float getThreshold() {
		return threshold;
	}

	@Override
	public int hashCode() {
		return 113 * Float.floatToIntBits(threshold) * getClass().hashCode();
	}

//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj) return true;
//		if (null == obj || getClass() != obj.getClass()) return false;
//
//		JaroWinklerDistance o = (JaroWinklerDistance)obj;
//		return (Float.floatToIntBits(o.threshold) 
//				== Float.floatToIntBits(this.threshold));
//	}

	@Override
	public String toString() {
		return "jarowinkler(" + threshold + ")";
	}


}