/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.TicketDTO;
import utils.DBUtils;

/**
 *
 * @author Tung Nguyen
 */
public class TicketDAO {

    public TicketDAO() {
    }

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public boolean createTicket(TicketDTO ticket) {
        boolean isCreated = false;
        String sql = "INSERT INTO [dbo].[Tickets] (BookingID, ScheduleSeatID, PassengerName, PassengerPhone, TicketCode, Status) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, ticket.getBookingID());
            ps.setInt(2, ticket.getScheduleSeatID());
            ps.setString(3, ticket.getPassengerName());
            ps.setString(4, ticket.getPassengerPhone());
            ps.setString(5, ticket.getTicketCode());
            ps.setString(6, ticket.getStatus());

            int rowAffected = ps.executeUpdate();
            isCreated = rowAffected > 0;
        } catch (Exception e) {
            System.out.println("Lỗi khi tạo ticket mới vào DB: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBUtils.closeResources(conn, ps);
        }
        return isCreated;
    }

    public TicketDTO getTicketByPhoneNCode(String phone, String code) {
        TicketDTO ticket = null;
        String sql = "SELECT * FROM [dbo].[Tickets] WHERE PassengerPhone = ? AND TicketCode = ?";

        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, phone);
            ps.setString(2, code);
            rs = ps.executeQuery();

            if (rs.next()) {
                ticket = new TicketDTO();
                ticket.setTicketID(rs.getInt("TicketID"));
                ticket.setBookingID(rs.getInt("BookingID"));
                ticket.setScheduleSeatID(rs.getInt("ScheduleSeatID"));
                ticket.setPassengerName(rs.getString("PassengerName"));
                ticket.setPassengerPhone(rs.getString("PassengerPhone"));
                ticket.setStatus(rs.getString("Status"));
            }
        } catch (Exception e) {
            System.out.println("Lỗi hệ thống khi tìm ticket! " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBUtils.closeResources(conn, ps, rs);
        }
        return ticket;
    }

    public List<TicketDTO> getBoughtTicket(int scheduleid) {
        List<TicketDTO> list = new ArrayList<>();
        String sql = "SELECT \n"
                + "    s.ScheduleID,t.BookingID,t.ScheduleSeatID,\n"
                + "    s.DepartureTime,\n"
                + "    s.ArrivalTime,\n"
                + "    s.Status AS ScheduleStatus,\n"
                + "    t.TicketID,\n"
                + "    t.PassengerName,\n"
                + "    t.PassengerPhone,\n"
                + "    t.TicketCode,\n"
                + "    t.Status\n"
                + "FROM [dbo].[Schedules] s\n"
                + "JOIN [dbo].[ScheduleSeats] ss ON s.ScheduleID = ss.ScheduleID\n"
                + "JOIN [dbo].[Tickets] t ON ss.ScheduleSeatID = t.ScheduleSeatID\n"
                + "WHERE s.ScheduleID = ? ";
        TicketDTO ticket=null;
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, scheduleid);
            rs = ps.executeQuery();

            if (rs.next()) {
                ticket = new TicketDTO();
                ticket.setTicketID(rs.getInt("TicketID"));
                ticket.setBookingID(rs.getInt("BookingID"));
                ticket.setScheduleSeatID(rs.getInt("ScheduleSeatID"));
                ticket.setPassengerName(rs.getString("PassengerName"));
                ticket.setPassengerPhone(rs.getString("PassengerPhone"));
                ticket.setStatus(rs.getString("Status"));
                
                list.add(ticket);
            }
        } catch (Exception e) {
            System.out.println("Lỗi hệ thống khi tìm ticket! " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBUtils.closeResources(conn, ps, rs);
        }
        
        return list;
    }
//    public static void main(String[] args) {
//        TicketDAO tdao= new TicketDAO();
//        List<TicketDTO> list= tdao.getBoughtTicket(1);
//        if(list.isEmpty()){
//            System.out.println("false");
//        }else{
//            System.out.println("true");
//        }
//    }
}
