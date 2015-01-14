/**
 * This file is part of MerchantsAPI.
 * 
 * Copyright (c) 2014, Cybermaxke
 * 
 * MerchantsAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * MerchantsAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with MerchantsAPI. If not, see <http://www.gnu.org/licenses/>.
 */
package me.cybermaxke.merchants.api;

public class Merchants {
	private static MerchantAPI instance;

	/**
	 * Gets the instance of the merchants api.
	 * 
	 * @return the instance
	 */
	public static MerchantAPI get() {
		return instance;
	}

	/**
	 * Sets the instance of the merchants api.
	 * 
	 * @param api the instance
	 */
	public static void set(MerchantAPI api) {
		if (instance != null) {
			throw new IllegalStateException("The merchants api can only be set once!");
		}

		instance = api;
	}
}
