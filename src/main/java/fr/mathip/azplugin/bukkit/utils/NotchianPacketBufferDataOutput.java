package fr.mathip.azplugin.bukkit.utils;

import java.io.DataOutput;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import pactify.client.api.mcprotocol.NotchianPacketBuffer;

@RequiredArgsConstructor

public final class NotchianPacketBufferDataOutput implements DataOutput {

    private final NotchianPacketBuffer buf;

    @Override
    public void write(int b) {
        buf.writeByte((byte) b);
    }

    @Override
    public void write(byte[] b) {
        buf.writeBytes(b);
    }

    @Override
    public void write(byte[] b, int off, int len) {
        buf.writeBytes(b, off, len);
    }

    @Override
    public void writeBoolean(boolean v) {
        buf.writeBoolean(v);
    }

    @Override
    public void writeByte(int v) {
        buf.writeByte(v);
    }

    @Override
    public void writeShort(int v) {
        buf.writeShort(v);
    }

    @Override
    public void writeInt(int v) {
        buf.writeInt(v);
    }

    @Override
    public void writeLong(long v) {
        buf.writeLong(v);
    }

    @Override
    public void writeFloat(float v) {
        buf.writeFloat(v);
    }

    @Override
    public void writeDouble(double v) {
        buf.writeDouble(v);
    }

    @Override
    public void writeChar(int v) {
        buf.writeShort(v);
    }

    @Override
    public void writeBytes(String s) {
        byte[] b = s.getBytes(StandardCharsets.US_ASCII);
        buf.writeBytes(b);
    }

    @Override
    public void writeChars(String s) {
        int len = s.length();
        for (int i = 0; i < len; ++i) {
            writeChar(s.charAt(i));
        }
    }

    @Override
    public void writeUTF(String s) {
        if (s == null) {
            throw new NullPointerException();
        }

        int strlen = s.length();
        int utflen = 0;

        for (int i = 0; i < strlen; i++) {
            int c = s.charAt(i);

            if (c >= 0x0001 && c <= 0x007F) {
                utflen += 1;
            } else if (c == 0x0000 || c <= 0x07FF) {
                utflen += 2;
            } else {
                utflen += 3;
            }
        }

        if (utflen > 65535) {
            throw new IllegalArgumentException("encoded string too long: " + utflen + " bytes");
        }

        writeShort(utflen);

        for (int i = 0; i < strlen; i++) {
            int c = s.charAt(i);

            if (c >= 0x0001 && c <= 0x007F) {
                buf.writeByte(c);
            } else if (c == 0x0000) {
                buf.writeByte(0xC0);
                buf.writeByte(0x80);
            } else if (c <= 0x07FF) {
                buf.writeByte(0xC0 | (c >> 6));
                buf.writeByte(0x80 | (c & 0x3F));
            } else {
                buf.writeByte(0xE0 | (c >> 12));
                buf.writeByte(0x80 | ((c >> 6) & 0x3F));
                buf.writeByte(0x80 | (c & 0x3F));
            }
        }
    }

}
