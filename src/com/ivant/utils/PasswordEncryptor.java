
/*
 * Copyright 1997-2008 Markus Hahn.
 * For licensing information please read the README.TXT and contact the author. 
 */

package com.ivant.utils;

public class PasswordEncryptor
{
	
	public String encrypt(String str){
		// create a 40bit test key
        byte[] testKey = new byte[5];
        for (int i = 0; i < testKey.length; i++)
        {
            testKey[i] = (byte) (i + 1);
        }

        // do the key setup
        BlowfishECB bfe = new BlowfishECB(testKey, 0, testKey.length);
        
        byte[] tempbuf = str.getBytes();

        // align the data to the next block border
        byte[] msgbuf;
        int rest = tempbuf.length % Blowfish.BLOCKSIZE;

        msgbuf = new byte[(tempbuf.length - rest) + 
                          (0 == rest ? 0 : Blowfish.BLOCKSIZE)];

        System.arraycopy(tempbuf, 0, msgbuf, 0, tempbuf.length);

        for (int i = tempbuf.length; i < msgbuf.length; i++)
        {
            // pad with spaces; zeros are a better solution when you need to
            // actually strip of the padding data later on (in our case it
            // wouldn't be printable though)
            msgbuf[i] = ' ';    
        }

        // ECB encryption
        bfe.encrypt(msgbuf, 0, msgbuf, 0, msgbuf.length);

        //str = BinConverter.bytesToHexStr(msgbuf);
     
		return (new String(msgbuf));		
	}
	
	public String decrypt(String str){
		// create a 40bit test key
        byte[] testKey = new byte[5];
        for (int i = 0; i < testKey.length; i++)
        {
            testKey[i] = (byte) (i + 1);
        }

        // do the key setup
        BlowfishECB bfe = new BlowfishECB(testKey, 0, testKey.length);
        
        byte[] tempbuf = str.getBytes();

        // align the data to the next block border
        byte[] msgbuf;
        int rest = tempbuf.length % Blowfish.BLOCKSIZE;

        msgbuf = new byte[(tempbuf.length - rest) + 
                          (0 == rest ? 0 : Blowfish.BLOCKSIZE)];

        System.arraycopy(tempbuf, 0, msgbuf, 0, tempbuf.length);

        for (int i = tempbuf.length; i < msgbuf.length; i++)
        {
            // pad with spaces; zeros are a better solution when you need to
            // actually strip of the padding data later on (in our case it
            // wouldn't be printable though)
            msgbuf[i] = ' ';    
        }

        // ECB decryption
        bfe.decrypt(msgbuf, 0, msgbuf, 0, msgbuf.length);
        return(new String(msgbuf));
	}
	
    /*public static void main(String args[]) throws Throwable
    {
    	String input;
    	String encrypted,decrypted;
        System.out.print("something to encrypt please >");
        System.out.flush();
        
        input = new LineNumberReader(new InputStreamReader(System.in)).readLine();
        
        encrypted = encrypt(input);
        decrypted = decrypt(encrypted);
        
        System.out.println("Encrypted: s" + encrypted);
        System.out.println("Decrypted: " + decrypted);
    }*/
}
