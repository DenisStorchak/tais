package ua.org.tees.yarosh.tais.attendance.fprint;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @author Timur Yarosh
 *         Date: 20.03.14
 *         Time: 0:06
 */
@Service
@Scope("singleton")
public class FPrintScannerExpectationHolder {
    private static final String ALREADY_EXPECTING_MESSAGE = "Holder already expecting fprint scanner in %s auditory. " +
            "You must reset holder before add new expectation";
    private volatile boolean expects;
    private volatile String auditory;

    public boolean expect(String auditory) throws ExpectationEnabledException {
        if (!expects) {
            expects = true;
            this.auditory = auditory;
            return true;
        }
        throw new ExpectationEnabledException(String.format(ALREADY_EXPECTING_MESSAGE, auditory));
    }

    public String getExpectingScannerAuditory() {
        return auditory;
    }

    public void reset() {
        expects = false;
        auditory = null;
    }
}
