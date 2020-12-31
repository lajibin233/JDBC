package JDBCutil;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Update { //通用增删改考虑事务 链接从外部传入，
    public static void update(Connection con,String sql, Object... arg_s) {

        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            for (int i = 0; i < arg_s.length; i++) {
                ps.setObject(i + 1, arg_s[i]);
            }
            ps.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.closeResource(null, ps);
        }
    }
}
