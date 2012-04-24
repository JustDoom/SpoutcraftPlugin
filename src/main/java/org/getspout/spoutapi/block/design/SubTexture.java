/*
 * This file is part of SpoutPluginAPI (http://www.spout.org/).
 *
 * SpoutPluginAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SpoutPluginAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.getspout.spoutapi.block.design;

public class SubTexture {
	Texture parent;
	int xLoc;
	int yLoc;
	int xTopLoc;
	int yTopLoc;

	/**
	 * Creates a new SubTexture from the parent Texture
	 * @param parent     texture
	 * @param xLoc       left X location
	 * @param yLoc       bottom Y location
	 * @param spriteSize width and height of sprite
	 */
	public SubTexture(Texture parent, int xLoc, int yLoc, int spriteSize) {
		this.parent = parent;
		this.xLoc = xLoc;
		this.xTopLoc = xLoc + spriteSize;
		this.yLoc = yLoc;
		this.yTopLoc = yLoc + spriteSize;
	}

	/**
	 * Gets the left-sided X of this subtexture
	 * @return xLoc
	 */
	public int getXLoc() {
		return xLoc;
	}

	/*
	 * Gets the bottom-sided y of this subtexture
	 * @return yLoc
	 */
	public int getYLoc() {
		return yLoc;
	}

	/**
	 * Gets the right-sided x of this subtexture
	 * @return xTopLoc
	 */
	public int getXTopLoc() {
		return xTopLoc;
	}

	/**
	 * Gets the top-sided y of this subtexture
	 * @return yTopLoc
	 */
	public int getYTopLoc() {
		return yTopLoc;
	}

	/**
	 * Gets the parent texture of this subtexture
	 * @return parent Texture
	 */
	public Texture getParent() {
		return parent;
	}
}
