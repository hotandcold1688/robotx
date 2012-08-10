package com.macyou.robot.index;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Assert;
import org.junit.Test;

/**
 * a tool test DB , should not include in test integration
 * 
 * @author zili.dengzl
 * 
 */
public class MysqlDBTest {
	@Test
	public void testDB() throws Exception {
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;

		Class.forName("org.gjt.mm.mysql.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "123456");

		String sql = "delete from  robot_knowledge where id = 9999999";
		ps = conn.prepareStatement(sql);
		int ret = ps.executeUpdate();
		Assert.assertEquals(1, ret);

		sql = "insert into robot_knowledge (id,index_id) values (9999999,123456)";
		ps = conn.prepareStatement(sql);
		ret = ps.executeUpdate();
		Assert.assertEquals(1, ret);

		sql = "select * from robot_knowledge where id = 9999999";
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		rs.next();
		Assert.assertEquals("123456", rs.getString("index_id"));

	}

}
