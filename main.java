import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
public class main {
	static Scanner read = new Scanner(System.in);
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static void main(String[] args) {
		
		// step 1: generate keys
		long [] keys = RSA.generate_keys();
		long e = keys[0];
		long n = keys[1];
		long d = keys[2];

		
		// step 2: read message from user
		System.out.println("Enter your message (without spaces):");
		String message = read.next();


		// step 3: encrypt message
		long [] ct = RSA.encrypt(message, e, n);
		
		// step 4: decrypt message using the private key (d, n)
		RSA.decrypt(ct, d, n);	
		
	}
	
}
