package inputs;

public enum Keys {
	UP('w'), DOWN('s'), LEFT('a'), RIGHT('d'), RELOAD('r'), ONE('1'), TWO('2'), THREE('3'), FOUR('4'), FIVE('5'), SIX('6'), SEVEN('7'), EIGHT('8'), NINE('9'), ZERO('0'), INTERACT('e');

	private char key;

	private Keys(char setKey) {
		    this.key = setKey;
		  }

	public char getKey() {
		return key;
	}
}
