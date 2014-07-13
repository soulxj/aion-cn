/*
 * This file is part of aion-lightning <aion-lightning.com>.
 *
 *  aion-lightning is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-lightning is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-lightning.  If not, see <http://www.gnu.org/licenses/>.
 */
package mysql5;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.gameserver.dao.InGameShopLogDAO;
import com.aionemu.gameserver.dao.MySQL5DAOUtils;


/**
 * @author ViAl
 *
 */
public class MySQL5InGameShopLogDAO extends InGameShopLogDAO {

	private static final Logger log = LoggerFactory.getLogger(InGameShopLogDAO.class);
	private static final String INSERT_QUERY = "INSERT INTO `ingameshop_log` (`transaction_type`, `transaction_date`, `payer_name`, `payer_account_name`, `receiver_name`, `item_id`, `item_count`, `item_price`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	@Override
	public void log(String transactionType, Timestamp transactionDate, String payerName, String payerAccountName, String receiverName,
		int itemId, long itemCount, long itemPrice) {
		Connection conn = null; 
		try {
			conn = DatabaseFactory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(INSERT_QUERY);
			stmt.setString(1, transactionType);
			stmt.setTimestamp(2, transactionDate);
			stmt.setString(3, payerName);
			stmt.setString(4, payerAccountName);
			stmt.setString(5, receiverName);
			stmt.setInt(6, itemId);
			stmt.setLong(7, itemCount);
			stmt.setLong(8, itemPrice);
			stmt.executeUpdate();
			stmt.close();
		}
		catch (SQLException e) {
			log.error("Error while inserting ingameshop log. " + e);
		}
		finally {
			DatabaseFactory.close(conn);
		}
	}
	
	@Override
	public boolean supports(String s, int i, int i1) {
		return MySQL5DAOUtils.supports(s, i, i1);
	}
}
