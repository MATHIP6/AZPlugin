package fr.mathip.azplugin;

/*
 * This file is part of ViaVersion - https://github.com/ViaVersion/ViaVersion
 * Copyright (C) 2016-2022 ViaVersion and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.exception.InformativeException;
import com.viaversion.viaversion.protocols.v1_17_1to1_18.Protocol1_17_1To1_18;
import com.viaversion.viaversion.protocols.v1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ServerboundPackets1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.storage.EntityTracker1_9;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.Protocol1_9_1To1_9_3;

public class ShieldPackets {
    public static void register(Protocol protocol) {
        PlayersService playersService = PactifyAPI.players();
        protocol.registerServerbound(ServerboundPackets1_9.USE_ITEM, null, new PacketHandler() {
            @Override
            public void handle(PacketWrapper packetWrapper) throws InformativeException {
                int hand = packetWrapper.read(Types.VAR_INT);
                // Wipe the input buffer
                packetWrapper.clearInputBuffer();
                // First set this packet ID to Block placement
                packetWrapper.setId(0x08);
                packetWrapper.write(Types.BLOCK_POSITION1_8, new BlockPosition(-1, (short) -1, -1));
                packetWrapper.write(Types.UNSIGNED_BYTE, (short) 255);
                // Write item in hand
                Item item = Protocol1_8To1_9.getHandItem(packetWrapper.user());
                // Blocking patch
                if (Via.getConfig().isShieldBlocking()) {
                    System.out.println("ShieldBlocking");
                    EntityTracker1_9 tracker = packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);

                    // Check if the shield is already there or if we have to give it here
                    boolean showShieldWhenSwordInHand = Via.getConfig().isShowShieldWhenSwordInHand();

                    // Method to identify the sword in hand
                    boolean isSword = showShieldWhenSwordInHand ? tracker.hasSwordInHand()
                            : item != null && Protocol1_9To1_8.isSword(item.identifier());

                    if (isSword) {
                        if (hand == 0) {
                            if (!tracker.isBlocking()) {
                                tracker.setBlocking(true);

                                // Check if the shield is already in the offhand + Check if player is player has launcher
                                if (!showShieldWhenSwordInHand && tracker.getItemInSecondHand() == null && !playersService.hasLauncher(wrapper.user().getProtocolInfo().getUuid())) {
                                    // Set shield in offhand when interacting with main hand
                                    Item shield = new DataItem(442, (byte) 1, (short) 0, null);
                                    tracker.setSecondHand(shield);
                                }
                            }
                        }

                        // Use the main hand to trigger the blocking
                        boolean blockUsingMainHand = Via.getConfig().isNoDelayShieldBlocking()
                                && !showShieldWhenSwordInHand;

                        if (blockUsingMainHand && hand == 1 || !blockUsingMainHand && hand == 0) {
                            wrapper.cancel();
                        }
                    } else {
                        if (!showShieldWhenSwordInHand) {
                            // Remove the shield from the offhand
                            tracker.setSecondHand(null);
                        }
                        tracker.setBlocking(false);
                    }
                }
                wrapper.write(Type.ITEM, item);

                wrapper.write(Type.UNSIGNED_BYTE, (short) 0);
                wrapper.write(Type.UNSIGNED_BYTE, (short) 0);
                wrapper.write(Type.UNSIGNED_BYTE, (short) 0);
            }
        } {
            @Override
            public void registerMap() {
                handler(new PacketHandler() {
                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {

                    }
                });

            }
        },true);

    }

}*/
