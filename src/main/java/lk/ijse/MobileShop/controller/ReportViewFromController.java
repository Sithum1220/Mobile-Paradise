package lk.ijse.MobileShop.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import lk.ijse.MobileShop.db.DBConnection;
import lk.ijse.MobileShop.dto.CustomerOrderDetails;
import lk.ijse.MobileShop.model.*;
import lk.ijse.MobileShop.utill.DateTimeUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class ReportViewFromController implements Initializable {

    public JFXButton newSupplier;
    public JFXComboBox cmbYear;
    public JFXComboBox cmbMonth;
    public JFXComboBox cmbReport;
    public LineChart chart;
    public NumberAxis YAxis;
    public BarChart barChart;

    public RadioButton item;
    public ToggleGroup report;
    public RadioButton attendance;
    public RadioButton reportLP;
    public NumberAxis YAxisN;

    private void setEmployeeAttendance() {

        YAxisN.setAnimated(false);
        YAxisN.setTickMarkVisible(false);
        barChart.getData().clear();

        XYChart.Series series = new XYChart.Series();

        try {
            ArrayList<String> allId = EmployeeModel.getAllId();
            ArrayList<String> count = new ArrayList<>();
            for (int i = 0; i < allId.size(); i++) {
                String dateCount = EmployeeAttendanceModel.getAttendanceCountThisMonth(allId.get(i));
                count.add(dateCount);
            }

            for (int i = 0; i < count.size(); i++) {
                for (int j = i; j < count.size(); j++) {
                    if (Integer.parseInt(count.get(j)) > Integer.parseInt(count.get(i))) {
                        int max = Integer.parseInt(count.get(i));
                        count.set(i, count.get(j));
                        count.set(j, String.valueOf(max));

                        String id = allId.get(i);
                        allId.set(i, allId.get(j));
                        allId.set(j, id);

                    }
                }
            }
            System.out.println("maxCount : " + count);


            int index = allId.size() >= 5 ? 5 : allId.size();
            for (int i = 0; i < index; i++) {
                series.getData().add(new XYChart.Data(allId.get(i), Integer.valueOf(count.get(i))));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        barChart.getData().add(series);
    }

    public void BtnCustomerOrderOnAction(ActionEvent actionEvent) {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport("/Users/sithumimesh/Documents/IJSE/Idea Project/MobileShop/src/main/resources/asses/report/CustomerOrder.jrxml");
            JRDataSource jrDataSource = new JRBeanCollectionDataSource(List.of(new Object()));

            HashMap hm = new HashMap();


            Connection connection = DBConnection.getInstance().getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hm, connection);
            JasperViewer.viewReport(jasperPrint, false);
//
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void cmbYearOnAction(ActionEvent actionEvent) {
        chart.getData().clear();

        String[] months = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        for (int i = 0; i < months.length; i++) {
            if (months.equals(cmbMonth.getValue())) {
                setSupplierOrders(String.valueOf(i), (String) cmbYear.getValue());
                setCustomerOrders(String.valueOf(i), (String) cmbYear.getValue());
            }
        }
        chart.getXAxis().setAutoRanging(true);
        YAxis.setAnimated(false);
        YAxis.setTickMarkVisible(false);
    }

    public void cmbMonthOnAction(ActionEvent actionEvent) {

        String[] months = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        for (int i = 0; i < months.length; i++) {

            if (months[i].equals(cmbMonth.getValue())) {
                int ii = i;
                ii++;
                setSeries(setCustomerOrders(String.valueOf(ii), (String) cmbYear.getValue()), setSupplierOrders(String.valueOf(ii), (String) cmbYear.getValue()));
            }
        }


    }

    private void setMostSoldItems() {
        barChart.getData().clear();
        XYChart.Series series = new XYChart.Series();

        try {
            ArrayList<String> itemIds = ItemModel.getItemIds();
            ArrayList<String> orderIds = OrderModel.getMonthlyAllId();
            ArrayList<String[][]> details = new ArrayList<>();


          /*  for (int i = 0; i < itemIds.size(); i++) {
                System.out.println(itemIds.get(i));
            }*/
            String[][] d;
            for (int i = 0; i < itemIds.size(); i++) {
                d = new String[1][2];
                d[0][0] = itemIds.get(i);
                details.add(d);
            }
            for (int i = 0; i < orderIds.size(); i++) {
                ArrayList<CustomerOrderDetails> customerOrderDetails = OrderDetailsModel.get(orderIds.get(i));
                for (int j = 0; j < customerOrderDetails.size(); j++) {
                    for (int k = 0; k < details.size(); k++) {
                        if (customerOrderDetails.get(j).getItem_id().equals(details.get(k)[0][0])) {
                            if (details.get(k)[0][1] == null) {
                                details.get(k)[0][1] = "0";
                            }
                            System.out.println("customerOrderDetails.get(j).getQty() : " + Integer.valueOf(customerOrderDetails.get(j).getQty()) + Integer.valueOf(details.get(k)[0][1]));
                            details.get(k)[0][1] = Integer.valueOf(details.get(k)[0][1]) + customerOrderDetails.get(j).getQty();
                        }
                    }

                }
            }

            for (int i = 0; i < details.size(); i++) {
                if (details.get(i)[0][1] == null) {
                    details.get(i)[0][1] = "0";
                }
            }

            for (int i = 0; i < details.size(); i++) {
                for (int j = i; j < details.size(); j++) {
                    if (Integer.parseInt(details.get(j)[0][1]) > Integer.parseInt(details.get(i)[0][1])) {
                        String[][] max = new String[1][2];
                        max[0][0] = details.get(i)[0][0];
                        max[0][1] = details.get(i)[0][1];
                        String[][] Max = max;

                        String[][] min = new String[1][2];
                        min[0][0] = details.get(j)[0][0];
                        min[0][1] = details.get(j)[0][1];
                        details.set(i, min);
                        details.set(j, max);

                    }
                }
            }


            int index = details.size() >= 5 ? 5 : details.size();
            for (int i = 0; i < index; i++) {
                //  System.out.println("Item Code : "+details.get(i)[0][0]+"  Qty : "+details.get(i)[0][1]);
                series.getData().add(new XYChart.Data(details.get(i)[0][0], Integer.valueOf(details.get(i)[0][1])));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        barChart.getData().add(series);
    }

    private void setSeries(XYChart.Series customerOrders, XYChart.Series supplierOrders) {
        System.out.println(customerOrders.toString());
        chart.getData().clear();
        YAxis.setAnimated(false);
        YAxis.setTickMarkVisible(false);
        chart.getData().addAll(customerOrders, supplierOrders);
        chart.getXAxis().setAutoRanging(true);
    }

    private void setMonth() {
//        String [] months={"January","February","March","April","May","June","July", "August","September", "October", "November","December"};
        String[] months = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        cmbMonth.getItems().addAll(months);
    }

    private void setYear() {
        try {
            ArrayList<String> year = SupplierModel.getYear();
            cmbYear.getItems().addAll(year);
        } catch (SQLException | ClassNotFoundException throwables) {
        }
    }

    private XYChart.Series setCustomerOrders(String month, String year) {
        System.out.println(month);

        XYChart.Series series = new XYChart.Series();
        series.setName("Customer Order ");

        double Value = 0;

//        int days = DateTimeUtil.getDays(Integer.parseInt(year), Integer.parseInt(month));
        int days = DateTimeUtil.getDays(Integer.parseInt(year), Integer.parseInt(month));

        for (int i = 1; i <= days; i++) {
            try {
                Value = CustomerModel.getOrders(i, month);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }

            series.getData().add(new XYChart.Data(String.valueOf(i), Value));
        }
        return series;
    }

    private XYChart.Series setSupplierOrders(String month, String year) {
        XYChart.Series supplierSeries = new XYChart.Series();
        supplierSeries.setName("Supplier Orders");
        double Value = 0;

//        int days = DateTimeUtil.getDays(Integer.parseInt(year), Integer.parseInt(month));
        int days = DateTimeUtil.getDays(Integer.parseInt(year), Integer.parseInt(month));
        System.out.println("days : " + days);
        for (int i = 1; i <= days; i++) {
            String date = String.valueOf(i);
            String processDate = null;

            char[] chars = date.toCharArray();

            if (chars.length == 2) {
                processDate = date;
            } else {
                processDate = "0" + date;
            }

            try {

//                Value = SupplierModel.getOrders(processDate,month);
                Value = SupplierModel.getOrders(processDate, month);

            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
            supplierSeries.getData().add(new XYChart.Data(String.valueOf(i), Value));
        }
        return supplierSeries;
    }

    public void ItemOnAction(ActionEvent actionEvent) {
        setChart();
    }

    public void AttendanceOnAction(ActionEvent actionEvent) {
        setChart();
    }

    public void repotOnAction(ActionEvent actionEvent) {
        setChart();
    }

    private void setChart() {
        if (reportLP.isSelected()) {
            String[] months = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
            for (int i = 0; i < months.length; i++) {

                if (months[i].equals(cmbMonth.getValue())) {
                    int ii = i;
                    ii++;
                    setSeries(setCustomerOrders(String.valueOf(ii), (String) cmbYear.getValue()), setSupplierOrders(String.valueOf(ii), (String) cmbYear.getValue()));
                }
            }
            cmbYear.setVisible(true);
            cmbMonth.setVisible(true);
            barChart.setVisible(false);
            chart.setVisible(true);
        } else if (item.isSelected()) {
            setMostSoldItems();
            cmbYear.setVisible(false);
            cmbMonth.setVisible(false);
            barChart.setVisible(true);
            chart.setVisible(false);
        } else if (attendance.isSelected()) {
            setEmployeeAttendance();
            cmbYear.setVisible(false);
            cmbMonth.setVisible(false);
            barChart.setVisible(true);
            chart.setVisible(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setYear();
        setMonth();

        String s = DateTimeUtil.dateNow();
        String[] split = s.split("-");

        cmbYear.getSelectionModel().select(split[0]);
        String month = DateTimeUtil.monthNow();
        System.out.println(month);
        cmbMonth.getSelectionModel().select(month);


        setChart();


    }

    public void btnTodayEmployeeOnAction(ActionEvent event) {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport("/Users/sithumimesh/Downloads/MobileShopNew/src/main/resources/asses/report/todayEmployeeCentral.jrxml");
            JRDataSource jrDataSource = new JRBeanCollectionDataSource(List.of(new Object()));

            HashMap hm = new HashMap();

            hm.put("date",DateTimeUtil.dateNow());
            Connection connection = DBConnection.getInstance().getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hm, connection);
            JasperViewer.viewReport(jasperPrint, false);
//
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
