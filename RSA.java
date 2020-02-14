import java.lang.Math;
import java.util.*;

public class RSA {

	public static long[] extended_Euclid (long p, long q) {
	
		long list[] = {1, 0, p};

		if (q % p == 0)
			return list;
		else {
			list = extended_Euclid (q % p, p);
			long x = list[0];
			list[0] = list[1] - q / p * x;
			list[1] = x;
			return list;
		}			
	}

	public static long find_inverse (long a, long m) {

		long l[] = extended_Euclid(a, m);
		
		if(l[2] == 1)
			return l[0] % m;
		else
			return -1;

	}

	public static int random_prime(){

		int num = 0; 
		Random rand = new Random();
		num = rand.nextInt(1000) + 1; 
		while (!isPrime(num))
			num = rand.nextInt(1000) + 1;   
		return num;
	}

	public static boolean isPrime (int p) {

		// Corner case 
		if (p <= 1) 
			return false; 
		if(p == 2)
			return true;
		// Check from 2 to p / 2 
		for (int i = 2; i <= p / 2; i++) 
			if (p % i == 0) 
				return false; 

		return true;
	}

	public static long[] generate_keys() {
		long p = random_prime();
		long q = random_prime();
		long n, phi, e, d = 0;

		while(p == q) {
			p = random_prime();
			q = random_prime();
		}

		n = p * q;
		phi = (p - 1) * (q - 1);

		long gcdp = 0;
		e = (long)(Math.random() * phi + 1);
		while(gcdp != 1) {
			e++;
			if(e > phi)
				e = (long)(Math.random() * phi + 1);
			gcdp = extended_Euclid(e, phi)[2];
		}
		d = find_inverse(e, phi);
		if(d < 0)
			d += phi;
		long result[] = {e, n, d};
		
		System.out.printf("Generated key: e = %d n = %d * %d d = %d \n\n", e, p, q, d);
		
		return result;
	}

	public static long modular_exponentiation(long b, long n, long m) {
		
		if(m == 1)
			return 0;
		
		// r is the result
		long r = 1;
		b = b % m;
		
		while(n > 0) {
			if(n % 2 == 1)
				r = (r * b) % m;
			n /= 2;
			b = (long) Math.pow(b, 2) % m;
		}
		return r;
	}

	public static long string_to_int(String text) {
		
		String ch = "",Conc="";
		int ca = 0;
		for(int i = 0; i < text.length(); i++) {
			char a = text.charAt(i);
			ca = ((int)a) - 55;
			ch = ca + "";
			Conc = Conc + ch;
		}

		return Long.parseLong(Conc);
	}

	public static String int_to_String(long inttext) {
		
		String text = "", SL = inttext + "";
		if(SL.length() % 2 == 1)
			SL = "0" + SL;
		for(int i = 2; i <= SL.length(); i += 2) {
			String tmp = SL.substring(i - 2, i);
			int c = Integer.parseInt(tmp) + 55;
			text += ((char) c);
		}
		return text;
	}

	public static long[] encrypt(String plaintext, long e, long n){
		
		plaintext = plaintext.toUpperCase();
		System.out.printf("\nOriginal massege: %s \n\nEncrypt: \n\n", plaintext);
		
		int a = 0, b = 2;
		String sc;

		long ll[] = null;

		if((plaintext.length() % 2 == 0)){
			ll = new long[plaintext.length() / 2];
			for(int i = 0; i < ll.length; i++) {
				sc = plaintext.substring(a, b);
				long m = string_to_int(sc); 
				ll[i] = modular_exponentiation(m, e, n);
				System.out.printf("%s -> %d -> %d\n", sc, m, ll[i]);
				a = b;
				b = b + 2;
			}
		} else {
			ll = new long[(plaintext.length() / 2) + 1];
			int i;
			for(i = 0; i < plaintext.length() / 2; i++){
				sc = plaintext.substring(a, b);
				long m = string_to_int(sc); 
				ll[i] = modular_exponentiation(m, e, n);
				System.out.printf("%s -> %d -> %d\n", sc, m, ll[i]);
				a = b;
				b = b + 2;
			}
			sc = plaintext.substring(a, b - 1);
			long m = string_to_int(sc);
			ll[i] = modular_exponentiation(m, e, n);
			System.out.printf("%s -> %d -> %d\n", sc, m, ll[i]);
		}
		return ll;
	}


	public static String decrypt(long[] ciphertext,long d,long n) {

		System.out.printf("\nDecrypt: \n\n");
		
		String text = "";
		
		for(int i = 0; i < ciphertext.length; i++) {
			long ct = ciphertext[i];
			long me = modular_exponentiation(ct, d, n);
			String s = int_to_String(me);
			text += s;
			System.out.printf("%d -> %d -> %s\n", ct, me, s);
			
		}
		
		System.out.printf("\nYour massege after decryption: %s", text);
		return text;

	}

}
