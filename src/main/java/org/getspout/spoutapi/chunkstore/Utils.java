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
package org.getspout.spoutapi.chunkstore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;

public class Utils {
	private static ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
	private static ObjectOutputStream objectOutput;
	private static ByteArrayInputStream byteInput;
	private static ObjectInputStream objectInput;

	public static byte[] serialize(Serializable o) {
		try {
			byteOutput.reset();
			objectOutput = new ObjectOutputStream(byteOutput);
			objectOutput.writeObject(o);
			objectOutput.flush();
			byteOutput.flush();
			byte[] b = byteOutput.toByteArray();
			return b;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Serializable deserialize(byte[] array) {
		if (array == null) {
			return null;
		}

		try {
			return deserializeRaw(array);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Serializable deserializeRaw(byte[] array) throws IOException, ClassNotFoundException {
		byteInput = new ByteArrayInputStream(array);
		objectInput = new ObjectInputStream(byteInput);
		Serializable o = (Serializable) objectInput.readObject();
		return o;
	}

	public static void safeSerialize(ObjectOutputStream out, Serializable data) throws IOException {
		if (data == null) {
			out.writeBoolean(false);
			return;
		} else {
			out.writeBoolean(true);
		}

		byte[] serialData;

		if (data instanceof SerializedData) {
			SerializedData dummyData = (SerializedData) data;
			serialData = dummyData.serialData;
		} else {
			serialData = serialize(data);
			;
		}

		out.writeInt(serialData.length);

		out.write(serialData);
	}

	private static byte[] buffer = new byte[1024];

	public static Serializable safeDeserialize(ObjectInputStream in) throws IOException {
		if (!in.readBoolean()) {
			return null;
		}

		int length = in.readInt();

		if (length > buffer.length - 4) {
			buffer = new byte[length];
		}

		in.read(buffer, 0, length);
		buffer[length] = (byte) 0xFF;
		buffer[length + 1] = (byte) 0xFF;
		buffer[length + 2] = (byte) 0xFF;
		buffer[length + 3] = (byte) 0xFF;

		Serializable object;

		try {
			object = deserializeRaw(buffer);
		} catch (ClassNotFoundException cnfe) {
			SerializedData dummy = new SerializedData();
			dummy.serialData = Arrays.copyOf(buffer, length);
			object = dummy;
		}

		return object;
	}

	public static class SerializedData implements Serializable {
		private static final long serialVersionUID = 1L;
		byte[] serialData = null;
	}
}
