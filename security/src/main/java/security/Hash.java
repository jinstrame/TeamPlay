package security;


import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;

import java.security.MessageDigest;


@EqualsAndHashCode
public class Hash {

    private String hash;

    public Hash(String input){
        calcHash(input.getBytes());
    }

    public Hash(String input, String salt){
        byte[] argument = input.getBytes();
        byte[] saltBytes = salt.getBytes();
        xorBytes(argument, saltBytes);

        calcHash(argument);
    }


    @SneakyThrows
    private void calcHash(byte[] argument){
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(argument);
        byte[] mdbytes = md.digest();

        StringBuffer hexString = new StringBuffer();
        for (int i=0;i<mdbytes.length;i++) {
            hexString.append(Integer.toHexString(0xFF & mdbytes[i]));
        }

        hash = hexString.toString();
    }

    private void xorBytes(byte[] array1, byte[] array2){
        int length = (array1.length > array2.length) ? array1.length : array2.length;
        for (int i = 0; i < length; i++) {
            array1[i] ^= array2[i];
        }
    }

    public String toHexString(){
        return hash;
    }

}
