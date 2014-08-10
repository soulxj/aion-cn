/**
 * This file is part of aion-emu <aion-emu.com>.
 *
 *  aion-emu is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-emu is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-emu.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author -Nemesiss-
 */
public class SM_L2AUTH_LOGIN_CHECK extends AionServerPacket {

	/**
	 * True if client is authed.
	 */
	private final boolean ok;
	private final String accountName;
	private static byte[] data;

	static{
        data = hex2Byte("00000000000001" +
                "01010000000000000000000502020000" +
                "000703030000000904040000000B0505" +
                "0000000000000000000F060600000000" +
                "00000000001307070000001508081609" +
                "09170A0A180B0B190C0C1A0D0D1B3232" +
                "00000000000000000000000000000000" +
                "00000000000000000000000000000000" +
                "000000000000002920202A2121000000" +
                "0000002D22222E23232F242430252531" +
                "2626322727330E0E340F0F3510103611" +
                "113712123813133914143A1515000000" +
                "0000000000000000003F303000000041" +
                "31310000000000000000000000000000" +
                "00000000000000000000000000000000" +
                "4C33334D34344E353500000050282851" +
                "16165229290000000000000000000000" +
                "00000000582A2A592C2C5A2D2D5B2E2E" +
                "5C2F2F00000000000000000000000000" +
                "00000000000000000000006517176618" +
                "18671919681A1A691B1B6A1C1C6B1D1D" +
                "6C1E1E6D1F1F00000000000000000000" +
                "0000000000732B2B0000000000000000" +
                "00000000000000000000000000000000" +
                "00000000000000000000000000000001" +
                "01010502020703030904040B05050F06" +
                "06130707150808160909170A0A180B0B" +
                "190C0C1A0D0D330E0E340F0F35101036" +
                "11113712123813133914143A15155116" +
                "16651717661818671919681A1A691B1B" +
                "6A1C1C6B1D1D6C1E1E6D1F1F2920202A" +
                "21212D22222E23232F24243025253126" +
                "26322727502828522929582A2A732B2B" +
                "592C2C5A2D2D5B2E2E5C2F2F3F303041" +
                "31311B32324C33334D34344E35350000" +
                "00000000000000000000000000000000" +
                "000000000000000000000000910010AB" +
                "D7170100804628070100F0888F060100" +
                "103527070100205C2707010010161D0D" +
                "020030641D0D0300203D1D0D010050B2" +
                "1D0D0100408B1D0D010070001E0D0100" +
                "10B9FE1E010090E4512A01009011832B" +
                "0100107BEA2A0100104EB9290100B050" +
                "E31100003018E2110000304513130000" +
                "406C13130000B0AE7A120000C0D57A12" +
                "000010CAE111000010F712130000201E" +
                "13130000E0F21413000090607A120000" +
                "A0877A120000400E7C12000060FEE411" +
                "0000C0CAEA110000608DE21100009002" +
                "E311000080DBE2110000E0C5E3110000" +
                "E0DCF41100002079F5110000C077E311" +
                "0000C08EF4110000002BF5110000D09E" +
                "E3110000D0B5F41100001052F5110000" +
                "5066E211000070B4E2110000E036E611" +
                "00007007EA11000030FAE6110000A00B" +
                "E81100004021E7110000B032E8110000" +
                "0085E6110000D080E81100009055EA11" +
                "0000E06BF21100004056F311000080F2" +
                "F3110000103BE41100007025E5110000" +
                "902F14130000C0A41413000060BA1313" +
                "0000D0CB14130000509313130000802E" +
                "EA110000A05614130000800814130000" +
                "B07D14130000F0191513000050D7E411" +
                "0000C059E8110000A09AE5110000B0C1" +
                "E51100003089E41100009073E5110000" +
                "A040F4110000807EC423010000BAF211" +
                "000000727B12000010ACE611000090E4" +
                "E71100007096E7110000606FE7110000" +
                "80BDE711000060E0E911000050B9E911" +
                "0000507DF3110000A07CEA11000060A4" +
                "F311000000F6E811000070CBF3110000" +
                "40E5F0110000B0A3EA11000040C7F511" +
                "00009019F41100002008F3110000B067" +
                "F4110000F092F211000050EEF5110000" +
                "403FE2110000E0237B120000F04A7B12" +
                "000030E77B120000D0FC7A1200001099" +
                "7B12000020C07B12000040B0E4110000" +
                "F05DE611000000D8ED110000A05EEF11" +
                "0000A029E3110000E0A7E8110000705A" +
                "F11100008081F111000090A8F1110000" +
                "500CF11100006033F111000070E11313" +
                "0000804CE51100000014E4110000F0EC" +
                "E3110000306BE91100004092E9110000" +
                "2044E9110000F0CEE8110000101DE911" +
                "00006051EC11000010E1F2110000C0E8" +
                "E5110000909E8E060100A0C58E060100" +
                "2094C323020030BBC32301005009C423" +
                "01006030C42301007057C4230100907F" +
                "840C0200B0CD840C0300A0A6840C0100" +
                "E042850C0100C0F4840C0100D01B850C" +
                "01009022661E010040E2C3230100106D" +
                "C3230100");
	}

	/**
	 * Constructs new <tt>SM_L2AUTH_LOGIN_CHECK </tt> packet
	 * 
	 * @param ok
	 */
	public SM_L2AUTH_LOGIN_CHECK(boolean ok, String accountName) {
		this.ok = ok;
		this.accountName = accountName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void writeImpl(AionConnection con) {
		writeD(ok ? 0x00 : 0x01);
		writeB(data);
		writeS(accountName);
	}

	private static byte[] hex2Byte(String str) {
		byte[] bytes = new byte[str.length() / 2];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(str.substring(2 * i, 2 * i + 2), 16);
		}
		return bytes;
	}
}