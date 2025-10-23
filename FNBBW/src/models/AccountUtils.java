package models;

import java.util.Random;

/**
 * Utility class for generating account numbers and assigning branches.
 */
public class AccountUtils {
    private static final Random random = new Random();

    public static String generateAccountNo() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int block = 1000 + random.nextInt(9000);
            sb.append(block);
            if (i < 3) sb.append(" ");
        }
        return sb.toString();
    }

    public static String assignBranch(String accountType) {
        switch (accountType.toLowerCase()) {
            case "savings": return "Savings Hub";
            case "investment": return "Investment Center";
            case "cheque": return "Payroll Branch";
            default: return "Main Branch";
        }
    }
}
