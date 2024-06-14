package utils;

import java.util.Random;

public class RandomValues {
	
	
	private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMERIC = "0123456789";
    private static final String[] DOMAINS = {"example.com", "mail.com", "test.com", "random.org", "email.net"};
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static String generateRandomEmail() {
        String username = generateRandomString(8);  // Generate a random username with 8 characters
        String domain = DOMAINS[new Random().nextInt(DOMAINS.length)];
        return username + "@" + domain;
    }

    private static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        String characters = CHARACTERS + NUMERIC;

        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }
    
    public static String generateRandomName() {
        Random random = new Random();
        StringBuilder name = new StringBuilder();

        // Generate pattern "XAXAAXAX"
        for (int i = 0; i < 8; i++) {
            // Append a random uppercase letter for each position
            name.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }

        return name.toString();
    }
    
    public static String generateRandomPhoneNumber() {
        Random random = new Random();
        
        // Generate random numbers for each part of the phone number
        int firstPart = random.nextInt(900) + 100;   // First part: 100-999
        int secondPart = random.nextInt(900) + 100;  // Second part: 100-999
        int thirdPart = random.nextInt(10000);       // Third part: 0000-9999

        // Format the third part to ensure it has 4 digits (e.g., 123 becomes 0123)
        String formattedThirdPart = String.format("%04d", thirdPart);

        // Combine parts to form the complete phone number
        return firstPart + "-" + secondPart + "-" + formattedThirdPart;
    }

}
