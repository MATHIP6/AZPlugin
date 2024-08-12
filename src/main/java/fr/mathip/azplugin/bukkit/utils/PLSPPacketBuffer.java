package fr.mathip.azplugin.bukkit.utils;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import pactify.client.api.plsp.AbstractPLSPPacketBuffer;

public class PLSPPacketBuffer extends AbstractPLSPPacketBuffer<PLSPPacketBuffer> {
    private final ByteArrayDataOutput handle = ByteStreams.newDataOutput();

    @Override
    public PLSPPacketBuffer writeBytes(byte[] bytes) {
        this.handle.write(bytes);
        return this;
    }

    @Override
    public PLSPPacketBuffer writeBytes(byte[] bytes, int i, int i1) {
        this.handle.write(bytes, i, i1);
        return this;
    }

    @Override
    public PLSPPacketBuffer readBytes(byte[] bytes) {
        return null;
    }

    @Override
    public PLSPPacketBuffer readBytes(byte[] bytes, int i, int i1) {
        return null;
    }

    @Override
    public byte readByte() {
        return 0;
    }

    @Override
    public PLSPPacketBuffer writeByte(int i) {
        this.handle.write(i);
        return this;
    }

    public byte[] toBytes() {
        return this.handle.toByteArray();
    }
}
