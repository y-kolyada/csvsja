package com.ygorod.sql.connect.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.ygorod.util.DbUtils;
import java.util.ArrayList;
import java.util.ListIterator;

final class History {
	String part_number;
	String part_name;
	String hist_user;
//	LocalDateTime hist_change;
	String hist_change_date;
	String hist_var_name;
	String hist_old_value;
	String hist_new_value;
}

public class TestSqlConnect {
	private ArrayList<History> MSSqlConnect() {
		ArrayList<History> histList = new ArrayList<History>();

		try (Connection conn = DbUtils.getBi2Connection()) {
			if (conn != null) {
				System.out.println("Connected to MS SQL server");

				String sql = 
						  "SELECT " + "  part.nummer, " + "  part.namebspr, " + "  hist_tab.ybearb, "
						+ "  hist_tab.ydatum, " + "  hist_tab.yvar, " + "  hist_tab.ywertalt, hist_tab.ywertneu "
						+ "FROM " + "  dbo.Änderungshistorie$Änderungshistorie_TAB hist_tab "
						+ "  LEFT JOIN dbo.Änderungshistorie$Änderungshistorie hist ON hist.id = hist_tab.id "
						+ "  LEFT JOIN dbo.Part$Product part ON part.id = hist.yid " 
						+ "WHERE "
						+ "  hist.yid LIKE '(%,2,%)' " 
						+ "  AND hist_tab.ydatum > ? " 
						+ "  AND yvar LIKE (?) "
						+ "ORDER BY hist.yid asc, hist_tab.ydatum desc, hist_tab.yvar asc ";

				try (PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY)) {
					ps.setTimestamp(1, java.sql.Timestamp.valueOf("2020-01-01 00:00:00"));
//					ps.setString(2, "epr");
					ps.setString(2, "%");

					ResultSet rs = ps.executeQuery();
					int rowcount = 0;
					if (rs.last()) {
						rowcount = rs.getRow();
						rs.beforeFirst();
					}
					Integer i = 0;

					while (rs.next()) {
						History hist = new History();
						hist.part_number = rs.getString(1);
						hist.part_name = rs.getString(2);
						hist.hist_user = rs.getString(3);
						hist.hist_change_date = rs.getTimestamp(4).toString(); // toLocalDateTime();
						hist.hist_var_name = rs.getString(5);
						hist.hist_old_value = rs.getString(6);
						hist.hist_new_value = rs.getString(7);
						histList.add(hist);
					}

					ListIterator<History> iterator = histList.listIterator();
					History hist = new History();
					i = 0;
					while (iterator.hasNext()) {
						hist = iterator.next();
						System.out.println("Change date = " + hist.hist_change_date + "\t" + "User = " + hist.hist_user
								+ "\t" + "Variable = " + String.format("%-20s", hist.hist_var_name) + "\t"
								+ "Old value = " + String.format("%-15s", hist.hist_old_value) + "\t" + "New value = "
								+ String.format("%-15s", hist.hist_new_value) + "\t" + "Part number = "
								+ hist.part_number + "\t" + "Part name = " + hist.part_name + "\t");
						if (++i == 11) {
							break;
						}
					}

					System.out.println("Processed: " + i + " records from " + rowcount);
					rs.close();					
				} catch (SQLException e) {
					System.out.println("Failure in SQL execution");
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			System.out.println("Failed to connect to MS SQL server");
			e.printStackTrace();
		} 

		return histList;
	}

	private void PostgreSqlConnect() {
		try (Connection conn = DbUtils.getPostgreConnection()) {
			if (conn != null) {
				System.out.println("Connected to PostgreSQL server");

				String sql = "SELECT * FROM employee";
				try (PreparedStatement ps = conn.prepareStatement(sql)) {
					ResultSet rs = ps.executeQuery();
					Integer i = 0;
					while (rs.next()) {
						System.out.println("id = " + rs.getInt(1) + "\t" + "name = " + rs.getString(2));
						if (++i == 10) {
							break;
						}												
					}
					System.out.println("Processed: " + i + " records");
					rs.close();
				} catch (SQLException e) {
					System.out.println("Failure in SQL execution");
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			System.out.println("Failed to connect to PostgreSQL server");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws SQLException {
		TestSqlConnect sqlConnect = new TestSqlConnect();
		// sqlConnect.PostgreSqlConnect();
		sqlConnect.MSSqlConnect();
	}

}
