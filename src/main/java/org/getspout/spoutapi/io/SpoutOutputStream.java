/*
 * This file is part of SpoutcraftPlugin.
 *
 * Copyright (c) 2011 SpoutcraftDev <http://spoutcraft.org//>
 * SpoutcraftPlugin is licensed under the GNU Lesser General Public License.
 *
 * SpoutcraftPlugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SpoutcraftPlugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.getspout.spoutapi.io;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.UUID;
import java.util.zip.GZIPOutputStream;

import net.minecraft.server.v1_6_R3.NBTBase;
import net.minecraft.server.v1_6_R3.NBTTagCompound;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_6_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.material.Material;

public class SpoutOutputStream extends OutputStream {
	ByteBuffer buffer = ByteBuffer.allocate(256);

	public SpoutOutputStream() {
	}

	public void writeBlock(Block block) {
		this.writeInt(block.getX());
		this.writeInt(block.getY());
		this.writeInt(block.getZ());
		this.writeLong(block.getWorld().getUID().getLeastSignificantBits());
		this.writeLong(block.getWorld().getUID().getMostSignificantBits());
	}

	public void writeLocation(Location location) {
		this.writeDouble(location.getX());
		this.writeDouble(location.getY());
		this.writeDouble(location.getZ());
		this.writeFloat(location.getPitch());
		this.writeFloat(location.getYaw());
		this.writeLong(location.getWorld().getUID().getLeastSignificantBits());
		this.writeLong(location.getWorld().getUID().getMostSignificantBits());
	}

	public void writeVector(Vector vector) {
		this.writeDouble(vector.getX());
		this.writeDouble(vector.getY());
		this.writeDouble(vector.getZ());
	}

	public void writeItemStack(ItemStack stack) throws IOException {
		if (stack == null) {
			throw new IOException("Attempt made to send null ItemStack to the client!");
		}
		final net.minecraft.server.v1_6_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
		writeInt(nmsStack.getItem().id);
		write((byte) nmsStack.count);

		// TODO: In Bukkit 1.6.4, j is Item.getItemDamage
		writeShort((short) nmsStack.j());

		// TODO: In Bukkit 1.6.4, n is Item.isDamageable
		// TODO: In Bukkit 1.6.4, s is Item.getShareTag
		if (nmsStack.getItem().n() || nmsStack.getItem().s()) {
			writeNBTTagCompound(nmsStack.getTag());
		}
	}

	public void writeMaterial(Material material) {
		this.writeInt(material.getRawId());
		this.writeShort((short) material.getRawData());
	}

	public void writeUUID(UUID uuid) {
		this.writeLong(uuid.getLeastSignificantBits());
		this.writeLong(uuid.getMostSignificantBits());
	}

	@Override
	public void write(byte[] b) {
		while (buffer.remaining() < b.length) {
			expand();
		}
		buffer.put(b);
	}

	@Override
	public void write(byte[] b, int len, int off) {
		while (buffer.remaining() < b.length) {
			expand();
		}
		buffer.put(b, len, off);
	}

	@Override
	public void write(int b) {
		if (buffer.remaining() < 1) {
			expand();
		}
		buffer.put((byte) b);
	}

	public void writeShort(short s) {
		if (buffer.remaining() < 2) {
			expand();
		}
		buffer.putShort(s);
	}

	public void writeInt(int i) {
		if (buffer.remaining() < 4) {
			expand();
		}
		buffer.putInt(i);
	}

	public void writeLong(long l) {
		if (buffer.remaining() < 8) {
			expand();
		}
		buffer.putLong(l);
	}

	public void writeFloat(float f) {
		if (buffer.remaining() < 4) {
			expand();
		}
		buffer.putFloat(f);
	}

	public void writeDouble(double d) {
		if (buffer.remaining() < 8) {
			expand();
		}
		buffer.putDouble(d);
	}

	public void writeChar(char ch) {
		if (buffer.remaining() < 2) {
			expand();
		}
		buffer.putChar(ch);
	}

	public void writeBoolean(boolean b) {
		write(b ? 1 : 0);
	}

	public void writeString(String s) {
		while (buffer.remaining() < (2 + s.length() * 2)) {
			expand();
		}
		buffer.putShort((short) s.length());
		for (int i = 0; i < s.length(); i++) {
			buffer.putChar(s.charAt(i));
		}
	}

	public static final byte FLAG_COLORINVALID = 1;
	public static final byte FLAG_COLOROVERRIDE = 2;

	public void writeColor(Color c) {
		byte flags = 0x0;

		if (c.getRedF() == -1F) {
			flags |= FLAG_COLORINVALID;
		} else if (c.getRedF() == -2F) {
			flags |= FLAG_COLOROVERRIDE;
		}

		write(flags);
		writeInt(c.toInt());
	}

	public ByteBuffer getRawBuffer() {
		return buffer;
	}

	private void expand() {
		ByteBuffer replacement = ByteBuffer.allocate(buffer.capacity() * 2);
		replacement.put(buffer.array());
		replacement.position(buffer.position());
		buffer = replacement;
	}

	public void writeNBTTagCompound(NBTTagCompound compound) throws IOException {
		if (compound == null) {
			throw new IOException("Attempt made to send null NBTTagCompound to the client!");
		}
		final byte[] compressed = compress(compound);
		if (compressed.length > Short.MAX_VALUE) {
			throw new IOException("NBTTagCompound is too large to be sent to the client!");
		}
		if (compressed.length == 0) {
			throw new IOException("Attempt made to send zero length NBTTagCompound to the client!");
		}
		writeShort((short) compressed.length);
		write(compressed);
	}

	private byte[] compress(NBTBase base) throws IOException {
		final ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();

		try (DataOutputStream dataoutputstream = new DataOutputStream(new GZIPOutputStream(bytearrayoutputstream))) {
			dataoutputstream.writeByte(base.getTypeId());
			if (base.getTypeId() != 0) {
				dataoutputstream.writeUTF("");

				final Method nbtWrite = base.getClass().getDeclaredMethod("write", new Class[] {DataOutput.class});
				nbtWrite.setAccessible(true);
				nbtWrite.invoke(base, dataoutputstream);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bytearrayoutputstream.toByteArray();
	}
}
