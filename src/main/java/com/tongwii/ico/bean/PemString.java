package com.tongwii.ico.bean;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import java.io.*;

/**
 * Created by dean on 2017/8/25.
 */
public class PemString {
    private PemObject pemObject;

    public PemString(String strcode) throws IOException {
        PemReader pemReader = new PemReader(new StringReader(strcode));
        try {
            this.pemObject = pemReader.readPemObject();
        } finally {
            pemReader.close();
        }
    }

    public PemObject getPemObject() {
        return pemObject;
    }
}
