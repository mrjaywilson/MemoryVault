package net.mrjaywilson.memoryvault.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class SecurityService implements ISecurity {

	// Private members
	private Key pubKey;
	private Key pvtKey;
	
	@Override
	public String generateSecurePassword(int length) {

		String newPassword = "";
		Random rnd;

		String alphaDictionary[] = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
				"q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
		String specialDictionary[] = { "@", "#", "$", "%", "^", "&", "*" };
		String digitDictionary[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };

		if (length < 8) {
			length = 8;
		}

		int div = length / 4;

		String temp[] = new String[length];

		int counter = 0;

		while (((div * 4) + counter) < length) {
			counter++;
		}

		int j;
		for (j = 0; j < length; j++) {
			rnd = new Random();
			
			// divide evenly upper and lower case
			if (j < 3) {
				temp[j] = alphaDictionary[rnd.nextInt(26)].toUpperCase();
			} else if (j < (((div * 2) + counter))){
				temp[j] = alphaDictionary[rnd.nextInt(26)].toLowerCase();
			} else if (j < (length - div)) {
				temp[j] = specialDictionary[rnd.nextInt(7)];
			} else if (j < length) {
				temp[j] = digitDictionary[rnd.nextInt(10)];
			}
		}
		
		Collections.shuffle(Arrays.asList(temp));

		for (int i = 0; i < length; i++) {
			newPassword += temp[i].toString();
		}

		return newPassword;
	}

	/**
	 * Method to generate keys for encryption.
	 */
	@Override
	public void generateKeys() {
		File dir = new File(System.getProperty("user.dir") + "\\keys");

		// Check if files exist. If they do, prompt user to verify they want to
		// overwrite files. If they do and do not have a backup of their keys,
		// they will lose access to their password database.
		File checkPvtKey = new File(dir.toString() + "\\.key");
		File checkPubKey = new File(dir.toString() + "\\.key");

		if (checkPvtKey.exists() && checkPubKey.exists()) {
			Alert alert = new Alert(AlertType.WARNING,
					"You are about to overwrite you keys. If you do not have a backup of your keys to import, you will lose access to your database permanently. Press 'ok' to confirm you would like to generate new keys and risk losing access to your current database. Click 'cancel' to stop this process.",
					ButtonType.OK, ButtonType.CANCEL);

			Optional<ButtonType> result = alert.showAndWait();

			// If the user cancels, exit the method and do not overwrite the
			// keys.
			if (result.isPresent() && result.get() == ButtonType.CANCEL) {
				System.out.println("Cancel pressed.");
				alert = new Alert(AlertType.CONFIRMATION, "Process Aborted.", ButtonType.OK);

				alert.showAndWait();
				return;
			}
		}

		try {

			// Create instance of generator
			KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");

			// Initialize the generator with a keysize.
			// Note: 1024 or 2048
			keygen.initialize(2048);

			// Generate the keys
			KeyPair keyPair = keygen.generateKeyPair();

			// Temp store the keys
			pubKey = keyPair.getPublic();
			pvtKey = keyPair.getPrivate();

			System.out.println("Private key format: " + pvtKey.getFormat());
			System.out.println("Public key format: " + pubKey.getFormat());

			this.saveKeys(pvtKey, pubKey);

		} catch (NoSuchAlgorithmException e) {
			Alert alert = new Alert(AlertType.ERROR,
					"Failed to generate encryption keys. See log under help menu for details.", ButtonType.OK);

			alert.showAndWait();
			// Remove comments when API is added to program.
			// log(e.printStackTrace());
		} finally {
			// add success note to log
		}

	}

	/**
	 * Method to load file and return the private key.
	 */
	@Override
	public PrivateKey loadPrivateKey() {
		Path path = Paths.get(System.getProperty("user.dir") + "\\keys\\.key");

		try {
			// Read the file
			byte[] bytes = Files.readAllBytes(path);

			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return keyFactory.generatePrivate(keySpec);
		} catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {

			// Remove comments when API is added to program.
			// log(e.printStackTrace());
		} finally {
			// log success
		}

		return null;
	}

	/**
	 * Method to load file and return the public key
	 */
	@Override
	public PublicKey loadPublicKey() {
		// Load the Public Key
		Path path = Paths.get(System.getProperty("user.dir") + "\\keys\\.pub");

		try {
			// Read the file
			byte[] bytes = Files.readAllBytes(path);

			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return keyFactory.generatePublic(keySpec);
		} catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {

			// Remove comments when API is added to program.
			// log(e.printStackTrace());
		} finally {
			// log success
		}

		return null;
	}

	/**
	 * Method to save keys to file
	 */
	@Override
	public void saveKeys(Key pvtKey, Key pubKey) {
		// Load current working directory and
		// Define a new folder
		File dir = new File(System.getProperty("user.dir") + "\\keys");

		// Check if the folder exists, if not, create it
		if (!dir.exists()) {
			dir.mkdir();
		}

		// Save keys
		try (FileOutputStream outPrivate = new FileOutputStream(dir.toString() + "\\.key", false);
				FileOutputStream outPublic = new FileOutputStream(dir.toString() + "\\.pub", false)) {

			// Write keys to file and place in folders
			outPrivate.write(pvtKey.getEncoded());
			outPublic.write(pubKey.getEncoded());

		} catch (IOException e) {
			Alert alert = new Alert(AlertType.ERROR, "Could not save key(s). Please see log for details.",
					ButtonType.OK);

			alert.showAndWait();

			// Remove comments when API is added to program.
			// log(e.printStackTrace());
		} finally {
			// Add success note to log.
		}
	}

	@Override
	public String encrypt(String text, PrivateKey key) {
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			
			return Base64.encodeBase64String(cipher.doFinal(text.getBytes("UTF-8")));
		} catch (
				NoSuchAlgorithmException |
				NoSuchPaddingException |
				UnsupportedEncodingException |
				IllegalBlockSizeException |
				BadPaddingException |
				InvalidKeyException e) {
			
			Alert alert = new Alert(
					AlertType.ERROR,
					"An error occurred attempting to encrypt. Please see log for more details.",
					ButtonType.OK);

			alert.showAndWait();
			// log e
		}
		return null;
	}

	@Override
	public String decrypt(String text, PublicKey key) {
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, key);
			
			return new String(cipher.doFinal(Base64.decodeBase64(text)), "UTF-8");
		} catch (
				InvalidKeyException |
				UnsupportedEncodingException |
				IllegalBlockSizeException |
				BadPaddingException |
				NoSuchAlgorithmException | 
				NoSuchPaddingException e) {
			
			Alert alert = new Alert(
					AlertType.ERROR,
					"An error occurred attempting to decrypt. Please see log for more details.",
					ButtonType.OK);

			alert.showAndWait();
			// log e
		}
		
		return null;
	}

	@Override
	public boolean verifyPassword(String pkey) {
		// TODO Auto-generated method stub
		return false;
	}
}