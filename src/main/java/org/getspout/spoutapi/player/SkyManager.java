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
package org.getspout.spoutapi.player;

import org.getspout.spoutapi.ClientOnly;
import org.getspout.spoutapi.gui.Color;

public interface SkyManager {
	/**
	 * Gets the y-axis height that cloud tops are rendered at for the given player
	 * @param player
	 * @return height
	 */
	@ClientOnly
	public int getCloudHeight(SpoutPlayer player);

	/**
	 * Sets the y-axis heigh that cloud tops are rendered at for the given player
	 * @param player
	 * @param y      axis level to render the cloud top at
	 */
	@ClientOnly
	public void setCloudHeight(SpoutPlayer player, int y);

	/**
	 * Is true if the clouds are visible for the given player
	 * @param player
	 * @return true if clouds are visible
	 */
	@ClientOnly
	public boolean isCloudsVisible(SpoutPlayer player);

	/**
	 * Enables or disables the rendering of clouds for the given player
	 * @param player
	 * @param visible
	 */
	@ClientOnly
	public void setCloudsVisible(SpoutPlayer player, boolean visible);

	/**
	 * Gets the frequency of stars overhead at night. The default frequency is 1500.
	 * Higher frequencies cause more stars, lower, less
	 * @param player to get the frequency for
	 * @return frequency
	 */
	@ClientOnly
	public int getStarFrequency(SpoutPlayer player);

	/**
	 * Sets the frequency of stars overhead at night for the given player
	 * @param player    to set the frequency for
	 * @param frequency
	 */
	@ClientOnly
	public void setStarFrequency(SpoutPlayer player, int frequency);

	/**
	 * Is true if the stars are visible for the given player player
	 * @param player
	 * @return if the stars are visible
	 */
	@ClientOnly
	public boolean isStarsVisible(SpoutPlayer player);

	/**
	 * Enables or disables the rendering of stars for the given player
	 * @param player
	 * @param visible
	 */
	@ClientOnly
	public void setStarsVisible(SpoutPlayer player, boolean visible);

	/**
	 * Gets the percent size of the sun, relative to the default size.
	 * 100 percent is default size. 200 percent is double size. 50 percent is half size.
	 * @param player to get the size for
	 * @return percent size of the sun
	 */
	@ClientOnly
	public int getSunSizePercent(SpoutPlayer player);

	/**
	 * Sets the percent size of the sun, relative to the default size.
	 * 100 percent is the default size. 200 percent is double size. 50 percent is half size.
	 * @param player  to set the size for
	 * @param percent to set
	 */
	@ClientOnly
	public void setSunSizePercent(SpoutPlayer player, int percent);

	/**
	 * Is true if the sun will ever render
	 * @param player to check
	 * @return true if the sun will ever render
	 */
	@ClientOnly
	public boolean isSunVisible(SpoutPlayer player);

	/**
	 * Enables or disables rendering of the sun during daytime
	 * @param player
	 * @param visible
	 */
	@ClientOnly
	public void setSunVisible(SpoutPlayer player, boolean visible);

	/**
	 * Gets the custom url of the custom sun texture, or null if no custom texture is set
	 * @param player who has the custom texture
	 * @return url of the custom texture
	 */
	@ClientOnly
	public String getSunTextureUrl(SpoutPlayer player);

	/**
	 * Sets the texture of the sun to the picture in the given format, or if the url is null, resets the sun to the default texture
	 * The texture must be a square png to render correctly (e.g 32x32, 64x64, etc)
	 * @param player to set the custom texture of
	 * @param Url    of the texture
	 */
	@ClientOnly
	public void setSunTextureUrl(SpoutPlayer player, String url);

	/**
	 * Gets the size percent of the moon, relative to the default size.
	 * 100 percent is the default size. 200 percent is double size. 50 percent is half size.
	 * @param player to get the size from
	 * @return percent size
	 */
	public int getMoonSizePercent(SpoutPlayer player);

	/**
	 * Sets the percent size of the moon, relative to the default size.
	 * 100 percent is the default size. 200 percent is double size. 50 percent is half size.
	 * @param player  to set the size for
	 * @param percent to set
	 */
	@ClientOnly
	public void setMoonSizePercent(SpoutPlayer player, int percent);

	/**
	 * Is true if the moon will ever render
	 * @param player to check
	 * @return true if the moon will ever render
	 */
	@ClientOnly
	public boolean isMoonVisible(SpoutPlayer player);

	/**
	 * Enables or disables rendering of the moon during nighttime
	 * @param player
	 * @param visible
	 */
	@ClientOnly
	public void setMoonVisible(SpoutPlayer player, boolean visible);

	/**
	 * Gets the custom url of the custom moon texture, or null if no custom texture is set
	 * @param player who has the custom texture
	 * @return url of the custom texture
	 */
	@ClientOnly
	public String getMoonTextureUrl(SpoutPlayer player);

	/**
	 * Sets the texture of the moon to the picture in the given format, or if the url is null, resets the moon to the default texture
	 * The texture must be a square png to render correctly (e.g 32x32, 64x64, etc)
	 * @param player to set the custom texture of
	 * @param Url    of the texture
	 */
	@ClientOnly
	public void setMoonTextureUrl(SpoutPlayer player, String url);

	/**
	 * Sets the sky color for the player
	 * @param player
	 * @param skyColor
	 */
	@ClientOnly
	public void setSkyColor(SpoutPlayer player, Color skyColor);

	/**
	 * @param player
	 * @return the set sky color of given player
	 * @warning the return value can be null!
	 */
	@ClientOnly
	public Color getSkyColor(SpoutPlayer player);

	/**
	 * Sets the fog color for the player
	 * @param player
	 * @param fogColor
	 */
	@ClientOnly
	public void setFogColor(SpoutPlayer player, Color fogColor);

	/**
	 * @param player
	 * @return the set fog color of given player.
	 * @warning the return value can be null!
	 */
	@ClientOnly
	public Color getFogColor(SpoutPlayer player);

	/**
	 * Sets the cloud color for the player
	 * @param player
	 * @param cloudColor
	 */
	@ClientOnly
	public void setCloudColor(SpoutPlayer player, Color cloudColor);

	/**
	 * @param player
	 * @return the set cloud color of the player
	 * @warning the return value can be null!
	 */
	@ClientOnly
	public Color getCloudColor(SpoutPlayer player);
}
