/*
 * This file is part of SpoutcraftPlugin.
 *
 * Copyright (c) 2011 Spout LLC <http://www.spout.org/>
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
/*
 * This file is part of SpoutPlugin.
 *
 * Copyright (c) 2011 Spout LLC <http://www.spout.org/>
 * SpoutPlugin is licensed under the GNU Lesser General Public License.
 *
 * SpoutPlugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SpoutPlugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.getspout.spoutapi.chunkstore;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

import org.bukkit.World;

public class ChunkStore {
	HashMap<UUID, HashMap<Long, SimpleRegionFile>> regionFiles = new HashMap<UUID, HashMap<Long, SimpleRegionFile>>();

	public void closeAll() {
		for (UUID uid : regionFiles.keySet()) {
			HashMap<Long, SimpleRegionFile> worldRegions = regionFiles.get(uid);
			Iterator<SimpleRegionFile> itr = worldRegions.values().iterator();
			while (itr.hasNext()) {
				SimpleRegionFile rf = itr.next();
				if (rf != null) {
					rf.close();
					itr.remove();
				}
			}
		}
		regionFiles.clear();
	}

	public ChunkMetaData readChunkMetaData(World world, int x, int z) throws IOException {
		SimpleRegionFile rf = getSimpleRegionFile(world, x, z);
		InputStream in = rf.getInputStream(x, z);
		if (in == null) {
			return null;
		}
		ObjectInputStream objectStream = new ObjectInputStream(in);
		try {
			Object o = objectStream.readObject();
			if (o instanceof ChunkMetaData) {
				return (ChunkMetaData) o;
			} else {
				throw new RuntimeException("Wrong class type read for chunk meta data for " + x + ", " + z);
			}
		} catch (IOException e) {
			// Assume the format changed
			return null;
			//throw new RuntimeException("Unable to process chunk meta data for " + x + ", " + z, e);
		} catch (ClassNotFoundException e) {
			// Assume the format changed
			//System.out.println("[SpoutPlugin] is Unable to find serialized class for " + x + ", " + z + ", " + e.getMessage());
			return null;
			//throw new RuntimeException("Unable to find serialized class for " + x + ", " + z, e);
		}
	}

	public void writeChunkMetaData(World world, int x, int z, ChunkMetaData data) {
		if (!data.isDirty()) {
			return;
		}
		try {
			SimpleRegionFile rf = getSimpleRegionFile(world, x, z);
			ObjectOutputStream objectStream = new ObjectOutputStream(rf.getOutputStream(x, z));
			objectStream.writeObject(data);
			objectStream.flush();
			objectStream.close();
			data.setDirty(false);
		} catch (IOException e) {
			throw new RuntimeException("Unable to write chunk meta data for " + x + ", " + z, e);
		}
	}

	public void closeChunkMetaData(World world, int x, int z) {
		SimpleRegionFile rf = getSimpleRegionFile(world, x, z);
		if (rf != null) {
			rf.close();
		}
	}

	private SimpleRegionFile getSimpleRegionFile(World world, int x, int z) {
		File directory = new File(world.getWorldFolder(), "spout_meta");

		directory.mkdirs();

		UUID key = world.getUID();

		HashMap<Long, SimpleRegionFile> worldRegions = regionFiles.get(key);

		if (worldRegions == null) {
			worldRegions = new HashMap<Long, SimpleRegionFile>();
			regionFiles.put(key, worldRegions);
		}

		int rx = x >> 5;
		int rz = z >> 5;

		long key2 = (((long) rx) << 32) | (((long) rz) & 0xFFFFFFFFL);

		SimpleRegionFile regionFile = worldRegions.get(key2);

		if (regionFile == null) {
			File file = new File(directory, "spout_" + rx + "_" + rz + "_.spm");
			regionFile = new SimpleRegionFile(file, rx, rz);
			worldRegions.put(key2, regionFile);
		}

		return regionFile;
	}
}
