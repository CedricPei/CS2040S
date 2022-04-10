import java.util.HashMap;
import java.util.Random;

public class MarkovModel {
	int order;
	HashMap<String, int[]> temp;
	HashMap<String, Integer> kGram;

	private final Random generator = new Random();
	public static final char NOCHARACTER = (char) 0;

	public MarkovModel(int order, long seed) {
		this.order = order;
		this.temp = new HashMap<>();
		this.kGram = new HashMap<>();
		generator.setSeed(seed);
	}

	public void initializeText(String text) {
		int k = order;

		for (int i = 0; i < text.length() - k; i++) {
			String focus = text.substring(i, i + k);
			int cha = text.charAt(i + k);

			if (temp.containsKey(focus)) {
				int[] arr = temp.get(focus);
				arr[cha] += 1;
				temp.put(focus, arr);
				kGram.put(focus, kGram.get(focus) + 1);
			} else {
				temp.put(focus, new int[256]);
				int[] arr = temp.get(focus);
				arr[cha] += 1;
				temp.put(focus, arr);
				kGram.put(focus, 1);
			}
		}
	}

	public int getFrequency(String kgram) {
		if (kgram.length() == order) return kGram.getOrDefault(kgram, 0);
		else                         return 0;
	}

	public int getFrequency(String kgram, char c) {
		if (kgram.length() == order && temp.containsKey(kgram)) return temp.get(kgram)[c];
		else                                                    return 0;
	}

	public char nextCharacter(String kgram) {
		/*
		Time Complexity O(n).
		if (temp.containsKey(kgram)) {
			int[] arr = temp.get(kgram);
			ArrayList<Character> list = new ArrayList<Character>();
			for(int i = 0; i < 256; i++) {
				if (arr[i] != 0) {
					for (int j = 0; j < arr[i]; j++) {
						list.add((char) i);
					}
				}
			}
			return list.get(generator.nextInt(list.size()));
		}
		 */
		
		//Time Complexity O(1).
		if (temp.containsKey(kgram)) {
			int rand = generator.nextInt(getFrequency(kgram));
			int[] freq = temp.get(kgram);
			int cnt = 0;

			for (int i = 1; i < 256; i++) {
				int cur = freq[i];
				if (rand <= cnt + cur - 1)   return (char) i;
				cnt += cur;
			}
			return NOCHARACTER;
		}
		return NOCHARACTER;
	}
}
