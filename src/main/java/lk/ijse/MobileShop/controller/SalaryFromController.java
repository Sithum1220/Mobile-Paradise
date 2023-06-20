package lk.ijse.MobileShop.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lk.ijse.MobileShop.db.DBConnection;
import lk.ijse.MobileShop.dto.Employee;
import lk.ijse.MobileShop.dto.Salary;
import lk.ijse.MobileShop.model.EmployeeAttendanceModel;
import lk.ijse.MobileShop.model.EmployeeModel;
import lk.ijse.MobileShop.model.EmployeeSalaryModel;
import lk.ijse.MobileShop.utill.DateTimeUtil;
import lk.ijse.MobileShop.utill.RegexUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.ResourceBundle;

public class SalaryFromController implements Initializable {
    public JFXComboBox cmbIds;
    public Text txtSalary;
    public JFXTextField txtBonus;
    public JFXTextField txtDSalary;
    public Text txtAttendance;
    public Text txtName;
    public VBox vBox;
    public JFXButton btnDone;
    double salary;

    private void loadIds() {
        try {
            cmbIds.getItems().clear();
            ArrayList<String> ids = EmployeeModel.getAllId();
            System.out.println("Employee_ids" + ids);
            ArrayList<String> aIds = EmployeeSalaryModel.getAllEmpId();
            System.out.println("Salary_Employee_Id" + aIds);
            for (int i = 0; i < ids.size(); i++) {
                for (int j = 0; j < aIds.size(); j++) {
                    if (ids.get(i).equals(aIds.get(j))) {
                        ids.remove(i);
                    }
                }
            }
            cmbIds.getItems().addAll(ids);

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void sendEmail(String salaryId, String mail) {
        String to = mail;
        System.out.println("Process to Mail");
        String from = "mobileparadise.hikkaduwa@gmail.com";
        final String username = "mobileparadise.hikkaduwa";//change accordingly
        final String password = "tqmyflgojwiecbkz";//change accordingly

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.host", "smtp.gmail.com");


        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject("Your Monthly Salary Statment");

            // This mail has 2 part, the BODY and the embedded image
            MimeMultipart multipart = new MimeMultipart("related");

            // first part (the html)
            BodyPart messageBodyPart = new MimeBodyPart();
            String htmlText = "<h1 style=\"color: deepskyblue;text-align: center;font-weight: bold\">MOBILE PARADISE</h1>\n" +
                    "<p style=\"text-align: center\">Below is your salary receipt for this month.\n" +
                    "<h1 style=\"font-size: 20px;text-align: center\">Thank you for Working with us ! </h1>";
            messageBodyPart.setContent(htmlText, "text/html");
            // add it
            multipart.addBodyPart(messageBodyPart);

           /* // second part (the image)
            messageBodyPart = new MimeBodyPart();
            System.out.println("1");
            DataSource fds = new FileDataSource(
                    "/Users/sithumimesh/Documents/CustomerBill/CustomerBill" +salaryId+".pdf");

            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "<pdf>");*/

            MimeBodyPart messageBodyPart2 = new MimeBodyPart();
            String filename = "/Users/sithumimesh/Documents/mobileshopSalary/Salary" + salaryId + ".pdf";//change accordingly
            DataSource source = new FileDataSource(filename);
            messageBodyPart2.setDataHandler(new DataHandler(source));
            messageBodyPart2.setFileName(filename);

            // add image to the multipart
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(messageBodyPart2);

            // put everything together
            message.setContent(multipart);
            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private static void printPdf(String salaryId, String mail) {
        String fileNameJrxml = "/Users/sithumimesh/Documents/MobileShopNew/src/main/resources/asses/report/Salary.jrxml";
//        String fileNameJrxml = "/Users/sithumimesh/Downloads/MobileShopNew/src/main/resources/asses/report/CustomeBill.jrxml";
        String fileNamePdf = "/Users/sithumimesh/Documents/mobileshopSalary/Salary" + salaryId + ".pdf";

        try {
            JasperDesign jasperDesign = JRXmlLoader.load(fileNameJrxml);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            String first_language = "Java";
            String second_language = "Structured text";
            HashMap hm = new HashMap();
            hm.put("id", salaryId);

            JasperPrint jprint = (JasperPrint) JasperFillManager.fillReport(jasperReport, hm, DBConnection.getInstance().getConnection());
            JasperExportManager.exportReportToPdfFile(jprint, fileNamePdf);
            System.out.println("Successfully completed the export");

            sendEmail(salaryId, mail);

        } catch (Exception e) {
            System.out.println("fuck");
            System.out.print("Exception:" + e);
        }
    }


    public void btnAddOnAction(ActionEvent actionEvent) {
        String id=id();
        try {
            Salary salary1 = new Salary(

                    (String) cmbIds.getValue(),
                    id(),
                    txtSalary.getText(),
                    txtAttendance.getText(),
                    DateTimeUtil.dateNow(),
                    DateTimeUtil.timeNow()

            );
            if (EmployeeSalaryModel.add(salary1)) {
                new Alert(Alert.AlertType.INFORMATION, "Ok").show();
                loadIds();
                loadAllSalaryId();
                txtSalary.setText("");
                txtDSalary.setText("");
                txtName.setText("");
                txtAttendance.setText("00");
                txtBonus.setText("");

                cmbIds.getItems().clear();

                Thread thread=new Thread(() -> {
                    try {
                        Salary salary = EmployeeSalaryModel.getData(salary1.getSalary_id());
                        System.out.println("Salary id - "+salary.getSalary_id());

                        Employee employee = EmployeeModel.getData(salary.getEmployee_id());
                        System.out.println("email - "+employee.getEmail());
                        printPdf(salary.getSalary_id(), employee.getEmail());
                    } catch (SQLException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                });
                thread.start();

            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private String id() {
        try {
            ArrayList<String> allId = EmployeeSalaryModel.getAllId();
            String lastId = null;
            for (int i = 0; i < allId.size(); i++) {
                lastId = allId.get(i);
                System.out.println(allId.get(i));
            }
            try {
                String[] e00s = lastId.split("S00");
                int idIndex = Integer.parseInt(e00s[1]);
                idIndex++;
                System.out.println(idIndex);
                return "S00" + idIndex;
            } catch (Exception e) {
                return "S001";
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadAllSalaryId();
        loadIds();
    }

    public void cmbEmployeeIdsOnAction(ActionEvent actionEvent) {
        try {
            Employee data = EmployeeModel.getData((String) cmbIds.getValue());
            txtName.setText(data.getFirst_name() + " " + data.getLast_name());
            txtAttendance.setText(EmployeeAttendanceModel.getEmloyeeCount((String) cmbIds.getValue()));
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadAllSalaryId() {
        vBox.getChildren().clear();
        ArrayList<String> ids = null;
        try {
            ids = EmployeeSalaryModel.getAllId();
            for (int i = 0; i < ids.size(); i++) {
                loasAllSalaryData(ids.get(i));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void loasAllSalaryData(String id) {
        try {
            FXMLLoader loader = new FXMLLoader(SalaryBarFormController.class.getResource("/view/SalaryBarForm.fxml"));
            Parent root = loader.load();
            SalaryBarFormController controller = loader.getController();
            controller.setData(id);
            vBox.getChildren().add(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void DailySalaryOnKetRelease(KeyEvent keyEvent) {
        RegexUtil.regex(btnDone, txtDSalary, txtDSalary.getText(), "^(\\d+\\.\\d{1,2})$", "-fx-text-fill: white");
        System.out.println("D salary "+txtDSalary.getText());
        if (txtDSalary.getText().equals("")){
            System.out.println("in the if");
            txtSalary.setText("0.0");
            salary=0.0;
        }else {
            if(salary<=0){
                salary+=Double.parseDouble(txtDSalary.getText()) * Integer.parseInt(txtAttendance.getText());
            }else {
                salary=0;
                salary+=Double.parseDouble(txtDSalary.getText()) * Integer.parseInt(txtAttendance.getText());
                if (!txtBonus.getText().equals("")){
                    salary+=Double.parseDouble(txtBonus.getText());
                }
            }
        }
        System.err.println(salary);
        txtSalary.setText(String.valueOf(salary));
    }

    public void BonusOnKetRelease(KeyEvent keyEvent) {
        RegexUtil.regex(btnDone, txtBonus, txtBonus.getText(), "^(\\d+\\.\\d{1,2})$", "-fx-text-fill: white");

        double netSalary=salary;
        /*double total=0.0;

        txtSalary.setText(String.valueOf(Double.parseDouble(txtSalary.getText()) + Double.parseDouble(txtBonus.getText())));*/

        if (txtBonus.getText().equals("")){
            System.out.println("in the if");
            //txtBonus.setText("0.0");
            netSalary=salary;
        }else {
            if(netSalary<=0){
                netSalary+=Double.parseDouble(txtBonus.getText());
            }else {
                netSalary= salary;
                netSalary+=Double.parseDouble(txtBonus.getText());

            }
        }
        txtSalary.setText(String.valueOf(netSalary));
        System.err.println(salary);
    }
}
