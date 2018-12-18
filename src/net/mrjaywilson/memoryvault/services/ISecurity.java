package net.mrjaywilson.memoryvault.services;

import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;

public interface ISecurity {
	public String generateSecurePassword(int length);
	public void generateKeys();
	public Key loadPrivateKey();
	public Key loadPublicKey();
	public void saveKeys(Key pvtKey, Key pubKey);
	public String encrypt(String text, PrivateKey key);
	public String decrypt(String text, PublicKey key);
	public boolean verifyPassword(String pkey);
}
