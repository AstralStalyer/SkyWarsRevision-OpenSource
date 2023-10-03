package cn.rmc.skywars.util.database;


import cn.rmc.skywars.SkyWars;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.configuration.ConfigurationSection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLCore extends DataBaseCore
{
    private static String driverName = "com.mysql.jdbc.Driver";
    private String username;
    private String password;
    private Connection connection;
    private String url;
    private HikariDataSource ds;
    public MySQLCore(ConfigurationSection cfg)
    {
        this(cfg.getString("ip"), cfg.getInt("port"), cfg.getString("database"),
                cfg.getString("username"), cfg.getString("password"));
    }

    public MySQLCore(String host, int port, String dbname, String username,
                     String password)
    {
        url = ("jdbc:mysql://" + host + ":" + port + "/" + dbname);
        this.username = username;
        this.password = password;
        //创建连接池参数
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);

        config.addDataSourceProperty("connectionTimeout", "10000"); // 连接超时：1秒
        config.addDataSourceProperty("idleTimeout", "60000"); // 空闲超时：60秒
        config.addDataSourceProperty("maximumPoolSize", "20"); // 最大连接数：10


        try {
            ds = new HikariDataSource(config);//使用参数创建连接池
        }catch (Exception e){
            SkyWars.getInstance().getLogger().warning("无法连接 原因: "+e.getMessage());
        }
//        try
//        {
//            Class.forName(driverName).newInstance();
//        }
//        catch (Exception e)
//        {
//            System.out.println("数据库初始化失败 请检查驱动 " + driverName + " 是否存在!");
//        }
    }
    public HikariConfig getDs(){//外部获取连接池
        return ds;
    }
    public Connection getDbCon() throws SQLException {//外部获取连接
        return ds.getConnection();
    }

    @Override
    public boolean createTables(String tableName, KeyValue fields, String conditions)
            throws SQLException
    {
        String sql = "CREATE TABLE IF NOT EXISTS `" + tableName + "` ( "
                + fields.toCreateString()
                + (conditions == null ? ""
                : new StringBuilder(" , ").append(conditions).toString())
                + " ) ENGINE = MyISAM DEFAULT CHARSET=GBK;";
        return execute(sql);
    }

    @Override
    public Connection getConnection()
    {
        try
        {
            if ((connection != null) && (!connection.isClosed()))
            {
                return connection;
            }
            connection = DriverManager.getConnection(url, username, password);
            return connection;
        }
        catch (SQLException e)
        {
            System.out.println("数据库操作出错: " + e.getMessage());
            System.out.println("登录URL: " + url);
            System.out.println("登录账户: " + username);
            System.out.println("登录密码: " + password);
        }
        return null;
    }
}
