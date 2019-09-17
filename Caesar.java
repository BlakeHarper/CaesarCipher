//Usage: java Caesar <shift> <input file> <output file>
import java.util.*;
import java.io.*;

public class Caesar {

    public static void main(String[] args) {
    	ArrayList<String> fileInput = readInput(args[1]);
    	System.out.println("Would you like to encrypt or decrypt?");
    	Scanner sc = new Scanner(System.in);
    	String usage = sc.next();
    	while (true) {
    		if (usage.equals("encrypt") || usage.equals("decrypt")) {
    			break;
    		}
    		else {
    			System.out.println("Please enter either 'encrypt' or 'decrypt'");
    			usage = sc.next();
    		}
    	}
    	System.out.println();
    	ArrayList<ArrayList<Character>> encryptedOutput = encrypt(Integer.parseInt(args[0]), fileInput, usage);
    	try {
    		outputEncryption(encryptedOutput, args[2]);
    	}
    	catch (ArrayIndexOutOfBoundsException e) {
    		outputEncryption(encryptedOutput, "");
    	}

    }

    public static ArrayList<ArrayList<Character>> encrypt(int shift, ArrayList<String> fileInput, String usage) {
    	ArrayList<ArrayList<Character>> encryption = new ArrayList<ArrayList<Character>>();

    	if (usage.equals("decrypt")) {
    		shift *= -1;
    	}

    	for (int i=0; i<fileInput.size(); i++) {
    		ArrayList<Character> encryptedLine = new ArrayList<Character>();

    		for (int j=0; j<fileInput.get(i).length(); j++) {
    			int ascii = (int) fileInput.get(i).charAt(j);
    			ascii += shift;
    			while (ascii > 126) ascii -= 95;
    			while (ascii < 32) ascii += 95;
    			encryptedLine.add((char)ascii);
    		}
    		encryption.add(encryptedLine);
    	}
    	return encryption;
    }

    public static ArrayList<String> readInput(String inputFileName) {
    	ArrayList<String> file = new ArrayList<String>();

    	try {
    		BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
    		BufferedReader length = new BufferedReader(new FileReader(inputFileName)); //possibly fix this using .reset()
    		while (length.readLine() != null) file.add(reader.readLine());
    		reader.close();
    		length.close();
    	}
    	catch (IOException ex) {
    		System.out.println("The file provided for input was not found or was not readable. Please enter a valid file name:");
    		String attemptedCorrection = userCorrections();
    		file = readInput(attemptedCorrection);
    	}
    	finally {
    		return file;
    	}
    }

    public static void outputEncryption(ArrayList<ArrayList<Character>> encryptedOutput, String outputFileName) {
    	try {
    		PrintWriter writer = new PrintWriter(outputFileName, "UTF-8");

    		for (int i=0; i<encryptedOutput.size(); i++) {
	    		for (int j=0; j<encryptedOutput.get(i).size(); j++) {
	    			writer.print(encryptedOutput.get(i).get(j));
	    		}
	    		writer.println();
    		}
    		writer.close();
    	}
    	catch (IOException ex) {
    		for (int i=0; i<encryptedOutput.size(); i++) {
	    		for (int j=0; j<encryptedOutput.get(i).size(); j++) {
	    			System.out.print(encryptedOutput.get(i).get(j));
	    		}
	    		System.out.println();
    		}
    	}
    }

    public static String userCorrections() {
    	Scanner scan = new Scanner(System.in);
    	String attemptedCorrection = scan.next();
    	return attemptedCorrection;
    }
}