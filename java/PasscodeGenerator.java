import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import javax.crypto.Mac;

public class PasscodeGenerator {
    private static final int ADJACENT_INTERVALS = 1;
    static final int INTERVAL = 180;
    private static final int PASS_CODE_LENGTH = 6;
    private static final int PIN_MODULO = (int)1e6;
    private IntervalClock clock;
    private final int codeLength;
    private final int intervalPeriod;
    private final Signer signer;

    interface Signer {
        byte[] sign(byte[] bArr) throws GeneralSecurityException;
    }

    interface IntervalClock {
        long getCurrentInterval();

        int getIntervalPeriod();
    }

	class MySigner implements Signer {
		Mac valmac;

		MySigner(Mac mac) {
			this.valmac = mac;
		}

		public byte[] sign(byte[] data) {
			return this.valmac.doFinal(data);
		}
	}

    class C00552 implements IntervalClock {
        C00552() {
        }

        public long getCurrentInterval() {
            return (System.currentTimeMillis() / 1000) / ((long) getIntervalPeriod());
        }

        public int getIntervalPeriod() {
            return PasscodeGenerator.this.intervalPeriod;
        }
    }

    public PasscodeGenerator(Mac mac, int passCodeLength, int interval) {
        this.clock = new C00552();
        this.signer = new MySigner(mac);
        this.codeLength = passCodeLength;
        this.intervalPeriod = interval;
    }

    public PasscodeGenerator(Signer signer, int passCodeLength, int interval) {
        this.clock = new C00552();
        this.signer = signer;
        this.codeLength = passCodeLength;
        this.intervalPeriod = interval;
    }

    private String padOutput(int value) {
        String result = Integer.toString(value);
        for (int i = result.length(); i < this.codeLength; i++) {
            result = "0" + result;
        }
        return result;
    }

    public String generateTimeoutCode() throws GeneralSecurityException {
        return generateResponseCode(this.clock.getCurrentInterval());
    }

    public String generateResponseCode(long challenge) throws GeneralSecurityException {
        return generateResponseCode(ByteBuffer.allocate(8).putLong(challenge).array());
    }

    public String generateResponseCode(byte[] challenge) throws GeneralSecurityException {
        byte[] hash = this.signer.sign(challenge);
        return padOutput((hashToInt(hash, hash[hash.length - 1] & 15) & Integer.MAX_VALUE) % PIN_MODULO);
    }

    private int hashToInt(byte[] bytes, int start) {
        try {
            return new DataInputStream(new ByteArrayInputStream(bytes, start, bytes.length - start)).readInt();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public boolean verifyResponseCode(long challenge, String response) throws GeneralSecurityException {
        return generateResponseCode(challenge).equals(response);
    }

    public boolean verifyTimeoutCode(String timeoutCode) throws GeneralSecurityException {
        return verifyTimeoutCode(timeoutCode, 1, 1);
    }

    public boolean verifyTimeoutCode(String timeoutCode, int pastIntervals, int futureIntervals) throws GeneralSecurityException {
        long currentInterval = this.clock.getCurrentInterval();
        if (generateResponseCode(currentInterval).equals(timeoutCode)) {
            return true;
        }
        int i;
        for (i = 1; i <= pastIntervals; i++) {
            if (generateResponseCode(currentInterval - ((long) i)).equals(timeoutCode)) {
                return true;
            }
        }
        for (i = 1; i <= futureIntervals; i++) {
            if (generateResponseCode(((long) i) + currentInterval).equals(timeoutCode)) {
                return true;
            }
        }
        return false;
    }
}
