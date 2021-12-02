package com.koreait.basic.dao;

import com.koreait.basic.DbUtils;
import com.koreait.basic.user.model.LoginResult;
import com.koreait.basic.user.model.UserEntity;
import org.apache.catalina.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class UserDAO {
    public static void main(String[] args) {
        UserEntity param = new UserEntity();
        param.setUid("asdasd");
        param.setUpw("12345");
        LoginResult login = login(param);
        System.out.println(login.getResult());
        System.out.println(login.getLoginUser());
    }
    public static int join(UserEntity entity) {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "INSERT INTO t_user (uid, upw, nm, gender) VALUES (?, ?, ?, ?) ";
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setString(1, entity.getUid());
            ps.setString(2, entity.getUpw());
            ps.setString(3, entity.getNm());
            ps.setInt(4, entity.getGender());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps);
        }
        return 0;
    }

    public static LoginResult login(UserEntity entity) {
        LoginResult lr = null;
        int result = 0;
        UserEntity vo = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = " SELECT upw, iuser, nm, gender FROM t_user WHERE uid = ? ";
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setString(1, entity.getUid());
            rs = ps.executeQuery();
            if(rs.next()) {
                String dbPw = rs.getString("upw");
                if(BCrypt.checkpw(entity.getUpw(), dbPw)) {
                    result = 1;
                    vo = new UserEntity();
                    vo.setIuser(rs.getInt("iuser"));
                    vo.setUid(entity.getUid());
                    vo.setNm(rs.getString("nm"));
                    vo.setGender(rs.getInt("gender"));
                } else {
                    result = 3;
                }
            } else {
                result = 2;
            }
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps, rs);
        }
        return new LoginResult(result, vo);
    }
}
