package com.koreait.basic.dao;

import com.koreait.basic.DbUtils;
import com.koreait.basic.board.model.BoardEntity;
import com.koreait.basic.board.model.BoardVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {
    public static int insBoard(BoardEntity entity) {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "INSERT INTO t_board (title, writer, ctnt) VALUES (?, ?, ?) ";
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setString(1, entity.getTitle());
            ps.setInt(2, entity.getWriter());
            ps.setString(3, entity.getCtnt());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DbUtils.close(con, ps);
        }
        return 0;
    }
    public static List<BoardVO> selBoardList() {
        List<BoardVO> list = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT A.iboard, A.title, A.writer, A.rdt, A.hit, B.nm as writerNm" +
                " FROM t_board A " +
                " INNER JOIN t_user B" +
                " ON A.writer = B.iuser" +
                " ORDER BY A.iboard DESC ";
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {
                int iboard = rs.getInt("iboard");
                String title =rs.getString("title");
                int writer = rs.getInt("writer");
                int hit = rs.getInt("hit");
                String rdt = rs.getString("rdt");
                String writerNm = rs.getString("writerNm");
                BoardVO vo = BoardVO.builder()
                        .iboard(iboard)
                        .title(title)
                        .writer(writer)
                        .hit(hit)
                        .rdt(rdt)
                        .writerNm(writerNm).build();
                list.add(vo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps, rs);
        }
        return list;
    }
}
