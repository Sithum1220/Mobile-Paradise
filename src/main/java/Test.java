import lk.ijse.MobileShop.db.DBConnection;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

//public class Test{
//    public static void main(String[] args) {
////        try {
////            int countOfMonth= Integer.parseInt("12");
//////            Order order = OrderModel.getData(txtOrderId.getText());
////
//////            String date = order.getDate();
////            String date = "2021-04-13";
////            String[] split = date.split("-");
////
////            int incrementCountOfMonth=0;
////            int month= Integer.parseInt(split[1]);
////            int year =Integer.parseInt(split[0]);
////
////
////            for (int i = 0; i < countOfMonth; i++) {
////
//////                System.out.println(year+"-"+month+"-"+split[2]);
////                if (month<=12){
////                    month++;
////                    if (month==13){
////                        month=01;
////                        year++;
////                    }
////                }else {
////                    year++;
////                    month=01;
////                }
////            }
////
////            String dateNow=  DateTimeUtil.dateNow();
////            String[] dateSplit = dateNow.split("-");
////
//////            System.out.println(Integer.parseInt(dateSplit[0])<=year);
//////            System.out.println(Integer.parseInt(dateSplit[1])<= month );
//////            System.out.println(Integer.parseInt(split[2])<Integer.parseInt(dateSplit[2]));
////
////            if (Integer.parseInt(dateSplit[0])<=year & Integer.parseInt(dateSplit[1])<= month ){
////
////                if ( Integer.parseInt(dateSplit[0])==year & Integer.parseInt(dateSplit[1])== month ){
////
////                    if( Integer.parseInt(split[2])<Integer.parseInt(dateSplit[2])){
////                        System.out.println("year == month == but Date Is < OK");
////                    }else {
////                        System.out.println("year != month != but Date Is !< error");
////                    }
////
////                }else {
////                    System.out.println("year > month > OK");
////                }
////
////            }else {
////                System.out.println("error");
////            }
////
////            System.out.println(year+"-"+month+"-"+split[2]);
////        }catch (NumberFormatException e){
////
////        }
//
//
//        /*ArrayList<String> list=new ArrayList<>();
//        list.add("1");
//        list.add("2");
//        list.add("5");
//        list.add("4");
//        list.add("3");
//        for (int i = 0 ; i < list.size(); i++) {
//            for (int j = i ; j < list.size(); j++) {
//                if (Integer.parseInt(list.get(j))>Integer.parseInt(list.get(i))){
//                    int max=Integer.parseInt(list.get(i));
//                    list.set(i,list.get(j));
//                    list.set(j, String.valueOf(max));
//                }
//            }
//        }
//        System.out.println("maxCount : "+list);*/
//
//      /*  ArrayList<String[][]>list=new ArrayList<>();
//
//        String[][] s=new String[1][2];
//
//        s[0][0]="0";
//        s[0][1]="1";
//
//        list.add(s);
//
//        System.out.println( list.get(0)[0][0]);
//*/
//
//       /* // Recipient's email ID needs to be mentioned.
//        String to = "sasindu.malshan03262001@gmail.com";
//
//        String from = "mobileparadise.hikkaduwa@gmail.com";
//        final String username = "mobileparadise.hikkaduwa";//change accordingly
//        final String password = "tqmyflgojwiecbkz";//change accordingly
//
//        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.port", "587");
//        props.put("mail.smtp.host", "smtp.gmail.com");
//
//
//        Session session = Session.getInstance(props,
//                new javax.mail.Authenticator() {
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(username, password);
//                    }
//                });
//
//        try {
//
//            // Create a default MimeMessage object.
//            Message message = new MimeMessage(session);
//
//            // Set From: header field of the header.
//            message.setFrom(new InternetAddress(from));
//
//            // Set To: header field of the header.
//            message.setRecipients(Message.RecipientType.TO,
//                    InternetAddress.parse(to));
//
//            // Set Subject: header field
//            message.setSubject("The Bill Of Your Order");
//
//            // This mail has 2 part, the BODY and the embedded image
//            MimeMultipart multipart = new MimeMultipart("related");
//
//            // first part (the html)
//            BodyPart messageBodyPart = new MimeBodyPart();
//            String htmlText = "<h1 style=\"color: deepskyblue;text-align: center;font-weight: bold\">MOBILE PARADISE</h1>\n" +
//                    "<p style=\"text-align: center\">If you come to our company, you can repair mobile phones, mobile phone accessories and\n" +
//                    "    mobile phones with a very high quality guarantee.</p>\n" +
//                    "<p style=\"text-align: center\">The mobile phone accessories you buy from us will not be accepted back as we have checked them in your presence and we will not be responsible for any damage caused to the mobile phone and accessories by you.</p>\n" +
//                    "<h1 style=\"font-size: 20px;text-align: center\">Thank you for shoping with us ! </h1>";
//            messageBodyPart.setContent(htmlText, "text/html");
//            // add it
//            multipart.addBodyPart(messageBodyPart);
//
//            // second part (the image)
//            messageBodyPart = new MimeBodyPart();
//            DataSource fds = new FileDataSource(
//                    "C:\\Users\\Sasindu Malshan\\Downloads\\MobileShop 2023-04-23\\MobileShop\\src\\main\\resources\\img\\A.jpg");
//
//            messageBodyPart.setDataHandler(new DataHandler(fds));
//            messageBodyPart.setHeader("Content-ID", "<image>");
//
//            // add image to the multipart
//            multipart.addBodyPart(messageBodyPart);
//
//            // put everything together
//            message.setContent(multipart);
//            // Send message
//            Transport.send(message);
//
//            System.out.println("Sent message successfully....");
//
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }
//*/
//
//
//        //
////
////        try {
////            JasperReport jasperReport = JasperCompileManager.compileReport(resource);
////            JasperPrint print = JasperFillManager.fillReport(jasperReport, map, DBConnection.getDbConnection().getConnection());
////            //            JasperPrintManager.printReport(jasperPrint,true);
////            JasperViewer.viewReport(print);
////
////
////        } catch (JRException | NullPointerException e) {
////            throw new RuntimeException(e);
////        } catch (SQLException | ClassNotFoundException throwables) {
////            throwables.printStackTrace();
////        }
////        String fileNameJrxml = "C:\\Users\\Sasindu Malshan\\Downloads\\MobileShop 2023-04-23\\MobileShop\\src\\main\\resources\\asses\\report\\";
////        String fileNamePdf = "C:\\Users\\Sasindu Malshan\\Downloads\\MobileShop 2023-04-23\\MobileShop\\src\\main\\resources\\asses\\report\\";
////
////        try {
////            System.out.println("Loading the .JRMXML file ....");
////            JasperDesign jasperDesign = JRXmlLoader.load(fileNameJrxml);
////            System.out.println("Compiling the .JRMXML file to .JASPER file....");
////            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
////            String first_language = "Java";
////            String second_language = "Structured text";
////            HashMap hm = new HashMap();
////            hm.put("Cid", "C1");
////
////            System.out.println("filling parameters to .JASPER file....");
////            JasperPrint jprint = (JasperPrint) JasperFillManager.fillReport(jasperReport, hm, DBConnection.getInstance().getConnection());
////            System.out.println("exporting the JASPER file to PDF file....");
////            JasperExportManager.exportReportToPdfFile(jprint, fileNamePdf);
////            System.out.println("Successfully completed the export");
////
////        } catch (Exception e) {
////            System.out.print("Exception:" + e);
////        }
////
////    }
//
////    private static ServletContext getServletContext() {
////        return null;
////    }
//
//     /*   try {
//            JasperReport jasperReport = JasperCompileManager.compileReport("/Users/sithumimesh/Downloads/MobileShopNew/src/main/resources/asses/report/CustomeBill.jrxml");
//            JRDataSource jrDataSource = new JRBeanCollectionDataSource(Arrays.asList(new Object()));
//
//            HashMap hm = new HashMap();
//            hm.put("id", "O001");
//
//            Connection connection = DBConnection.getInstance().getConnection();
//            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hm, connection);
//            JasperViewer.viewReport(jasperPrint, false);
////
//        } catch (JRException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//*/
//
//        printPdf("O001","sithumimesh82@gmail.com");
//
//    }

//    private static void sendEmail(String orderId, String mail) {
//        String to = mail;
//        System.out.println("Process to Mail");
//        String from = "mobileparadise.hikkaduwa@gmail.com";
//        final String username = "mobileparadise.hikkaduwa";//change accordingly
//        final String password = "tqmyflgojwiecbkz";//change accordingly
//
//        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.port", "587");
//        props.put("mail.smtp.host", "smtp.gmail.com");
//
//
//        Session session = Session.getInstance(props,
//                new javax.mail.Authenticator() {
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(username, password);
//                    }
//                });
//
//        try {
//
//            // Create a default MimeMessage object.
//            Message message = new MimeMessage(session);
//
//            // Set From: header field of the header.
//            message.setFrom(new InternetAddress(from));
//
//            // Set To: header field of the header.
//            message.setRecipients(Message.RecipientType.TO,
//                    InternetAddress.parse(to));
//
//            // Set Subject: header field
//            message.setSubject("The Bill Of Your Order");
//
//            // This mail has 2 part, the BODY and the embedded image
//            MimeMultipart multipart = new MimeMultipart("related");
//
//            // first part (the html)
//            BodyPart messageBodyPart = new MimeBodyPart();
//            String htmlText = "<h1 style=\"color: deepskyblue;text-align: center;font-weight: bold\">MOBILE PARADISE</h1>\n" +
//                    "<p style=\"text-align: center\">If you come to our company, you can repair mobile phones, mobile phone accessories and\n" +
//                    "    mobile phones with a very high quality guarantee.</p>\n" +
//                    "<p style=\"text-align: center\">The mobile phone accessories you buy from us will not be accepted back as we have checked them in your presence and we will not be responsible for any damage caused to the mobile phone and accessories by you.</p>\n" +
//                    "<h1 style=\"font-size: 20px;text-align: center\">Thank you for shoping with us ! </h1>";
//            messageBodyPart.setContent(htmlText, "text/html");
//            // add it
//            multipart.addBodyPart(messageBodyPart);
//
//            // second part (the image)
//            messageBodyPart = new MimeBodyPart();
//            System.out.println("1");
//            DataSource fds = new FileDataSource(
//                    "/Users/sithumimesh/Documents/CustomerBill/CustomerBill" +orderId);
//
//            messageBodyPart.setDataHandler(new DataHandler(fds));
//            messageBodyPart.setHeader("Content-ID", "<pdf>");
//
//            // add image to the multipart
//            multipart.addBodyPart(messageBodyPart);
//
//            // put everything together
//            message.setContent(multipart);
//            // Send message
//            Transport.send(message);
//
//            System.out.println("Sent message successfully....");
//
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }
//    }
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;


class Test {
     /*   public static void main(String [] args)
        {
            String to=null;//change accordingly
            final String user="mobileparadise.hikkaduwa";//change accordingly
            final String password="tqmyflgojwiecbkz";//change accordingly

            //1) get the session object
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.host", "smtp.gmail.com");

            Session session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(user,password);    }   });

            //2) compose message
            try{
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(user));
                message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
                message.setSubject("Message Aleart");

                //3) create MimeBodyPart object and set your message text
                BodyPart messageBodyPart1 = new MimeBodyPart();
                messageBodyPart1.setText("This is message body");

                //4) create new MimeBodyPart object and set DataHandler object to this object
                MimeBodyPart messageBodyPart2 = new MimeBodyPart();
//                String filename = "/Users/sithumimesh/Documents/CustomerBill/CustomerBill"+orderId+".pdf";//change accordingly
//                DataSource source = new FileDataSource(filename);
//                messageBodyPart2.setDataHandler(new DataHandler(source));
//                messageBodyPart2.setFileName(filename);

                //5) create Multipart object and add MimeBodyPart objects to this object
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart1);
                multipart.addBodyPart(messageBodyPart2);

                //6) set the multiplart object to the message object
                message.setContent(multipart );

                //7) send message
                Transport.send(message);
                System.out.println("message sent....");

            }catch (MessagingException ex) {ex.printStackTrace();}
        }
    }*/
//    private static void printPdf(String orderId, String mail) {
//        String fileNameJrxml = "/Users/sithumimesh/Downloads/MobileShopNew/src/main/resources/asses/report/CustomeBill.jrxml";
//        String fileNamePdf = "/Users/sithumimesh/Documents/CustomerBill/CustomerBill" + orderId;
//
//        try {
//            JasperDesign jasperDesign = JRXmlLoader.load(fileNameJrxml);
//            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
//            String first_language = "Java";
//            String second_language = "Structured text";
//            HashMap hm = new HashMap();
//            hm.put("id", orderId);
//
//            JasperPrint jprint = (JasperPrint) JasperFillManager.fillReport(jasperReport, hm, DBConnection.getInstance().getConnection());
//            JasperExportManager.exportReportToPdfFile(jprint,fileNamePdf);
//            System.out.println("Successfully completed the export");
//
//            sendEmail(orderId, mail);
//
//        } catch (Exception e) {
//            System.out.println("fuck");
//            System.out.print("Exception:" + e);
//        }
//    }
//
//}


//    public static void main(String[] args) {
//
//            String to = "sithumimesh82@gmail.com";
//            System.out.println("Process to Mail");
//            String from = "mobileparadise.hikkaduwa@gmail.com";
//            final String username = "mobileparadise.hikkaduwa";//change accordingly
//            final String password = "tqmyflgojwiecbkz";//change accordingly
//
//            Properties props = new Properties();
//            props.put("mail.smtp.auth", "true");
//            props.put("mail.smtp.starttls.enable", "true");
//            props.put("mail.smtp.port", "587");
//            props.put("mail.smtp.host", "smtp.gmail.com");
//
//
//            Session session = Session.getInstance(props,
//                    new javax.mail.Authenticator() {
//                        protected PasswordAuthentication getPasswordAuthentication() {
//                            return new PasswordAuthentication(username, password);
//                        }
//                    });
//
//            try {
//
//                // Create a default MimeMessage object.
//                Message message = new MimeMessage(session);
//
//                // Set From: header field of the header.
//                message.setFrom(new InternetAddress(from));
//
//                // Set To: header field of the header.
//                message.setRecipients(Message.RecipientType.TO,
//                        InternetAddress.parse(to));
//
//                // Set Subject: header field
//                message.setSubject("The Bill Of Your Order");
//
//                // This mail has 2 part, the BODY and the embedded image
//                MimeMultipart multipart = new MimeMultipart("related");
//
//                // first part (the html)
//                BodyPart messageBodyPart = new MimeBodyPart();
//                String htmlText = "<h1 style=\"color: deepskyblue;text-align: center;font-weight: bold\">MOBILE PARADISE</h1>\n" +
//                        "<p style=\"text-align: center\">If you come to our company, you can repair mobile phones, mobile phone accessories and\n" +
//                        "    mobile phones with a very high quality guarantee.</p>\n" +
//                        "<p style=\"text-align: center\">The mobile phone accessories you buy from us will not be accepted back as we have checked them in your presence and we will not be responsible for any damage caused to the mobile phone and accessories by you.</p>\n" +
//                        "<h1 style=\"font-size: 20px;text-align: center\">Thank you for shoping with us ! </h1>";
//                messageBodyPart.setContent(htmlText, "text/html");
//                // add it
//                multipart.addBodyPart(messageBodyPart);
//
//           /* // second part (the image)
//            messageBodyPart = new MimeBodyPart();
//            System.out.println("1");
//            DataSource fds = new FileDataSource(
//                    "/Users/sithumimesh/Documents/CustomerBill/CustomerBill" +orderId+".pdf");
//
//            messageBodyPart.setDataHandler(new DataHandler(fds));
//            messageBodyPart.setHeader("Content-ID", "<pdf>");*/
//
//                MimeBodyPart messageBodyPart2 = new MimeBodyPart();
//                String filename = "/Users/sithumimesh/Documents/CustomerBill/CustomerBillO001.pdf";//change accordingly
//                DataSource source = new FileDataSource(filename);
//                messageBodyPart2.setDataHandler(new DataHandler(source));
//                messageBodyPart2.setFileName(filename);
//
//                // add image to the multipart
//                multipart.addBodyPart(messageBodyPart);
//                multipart.addBodyPart(messageBodyPart2);
//
//                // put everything together
//                message.setContent(multipart);
//                // Send message
//                Transport.send(message);
//
//                System.out.println("Sent message successfully....");
//
//            } catch (MessagingException e) {
//                throw new RuntimeException(e);
//            }
//    }

    public static void main(String[] args) {
//
//        Thread thread=new Thread(() -> {
//          ss("1");
//        });
//        thread.start();
//
//        Thread thread2=new Thread(() -> {
//            ss("2");
//        });
//        thread2.start();
//
//
//    }
//
//    private static synchronized void ss (String s){
//        for (int i = 0; i < 1000; i++) {
//            System.out.println(s+" : "+i);
//        }
//    }
        Human human = new Navishka();


    }

}
class Navishka extends Human{
    @Override
    void talk(){

    }
}

class Human{
    void talk(){

    }
}


