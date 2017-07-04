import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

class Pin {
    private static final int Interval = 180;

    static CharSequence computePin(String mPin) throws Exception {
		Mac mac = Mac.getInstance("HMACSHA1");
		mac.init(new SecretKeySpec(Base32.decode(mPin), ""));
		return new PasscodeGenerator(mac, 6, Interval).generateTimeoutCode();
    }

	public static void main(String[] args) throws Exception {
		if(args.length < 1) {
			System.out.println("Usage: java Pin key");
			return;
		}

		String key = args[0];
		if(key.length() != 16) {
			System.out.println("Invalid key length, shoud be 16 chars");
			return;
		}

		System.out.println(computePin(key));
	}
}
