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
package org.getspout.spoutapi.block.design;

public class Vertex {
	private SubTexture texture;
	private int index;
	private int quad;
	private float x;
	private float y;
	private float z;
	private int tx;
	private int ty;

	private Vertex(int index, int quad, float x, float y, float z) {
		if (index < 0 || index > 3) {
			throw new IllegalArgumentException("Invalid vertex index: " + index);
		}
		this.index = index;
		this.quad = quad;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vertex(int index, int quad, float x, float y, float z, SubTexture texture) {
		this(index, quad, x, y, z);

		this.setSubTexture(texture);
	}

	public Vertex(int index, int quad, float x, float y, float z, int tx, int ty) {
		this(index, quad, x, y, z);
		this.tx = tx;
		this.ty = ty;
	}

	public Vertex setSubTexture(SubTexture texture) {
		this.texture = texture;

		switch (this.index) {
			case 0:
				this.tx = texture.getXTopLoc();
				this.ty = texture.getYLoc();
				break;
			case 1:
				this.tx = texture.getXTopLoc();
				this.ty = texture.getYTopLoc();
				break;
			case 2:
				this.tx = texture.getXLoc();
				this.ty = texture.getYTopLoc();
				break;
			case 3:
				this.tx = texture.getXLoc();
				this.ty = texture.getYLoc();
		}

		return this;
	}

	public int getIndex() {
		return index;
	}

	public SubTexture getSubTexture() {
		return texture;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	public int getTextureX() {
		return tx;
	}

	public int getTextureY() {
		return ty;
	}

	public int getTextureWidth() {
		return texture.getParent().getWidth();
	}

	public int getTextureHeight() {
		return texture.getParent().getHeight();
	}

	public int getQuadNum() {
		return quad;
	}
}
