//package utilities.email;
//
//import okhttp3.Address;
//import org.aspectj.bridge.Message;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import utilities.date_time.DateTimeUtils;
//import utilities.config.RunConfig;
//import utilities.email.core.GmailSessionFactory;
//import utilities.email.enums.ActionButton;
//import utilities.email.enums.Subject;
//import utilities.email.exceptions.EmailNotFoundException;
//
//import javax.lang.model.util.Elements;
//import javax.mail.*;
//import javax.mail.search.FlagTerm;
//import javax.security.auth.Subject;
//import javax.swing.text.Document;
//import javax.swing.text.Element;
//import java.util.*;
//
//import static javax.mail.Flags.Flag.SEEN;
//import static utilities.date_time.DateTimeUtils.waitFor;
//
//public class EmailReceiver {
//
//    private final Address[] fromAddress;
//    private final String from;
//    private final String subject;
//    private final String toList;
//    private final String ccList;
//    private final String sentDate;
//    private final String contentType;
//    private final String messageHtmlBody;
//
//    private EmailReceiver(Address[] fromAddress, String from, String subject, String toList, String ccList, String sentDate, String contentType, String messageHtmlBody) {
//        this.fromAddress = fromAddress;
//        this.from = from;
//        this.subject = subject;
//        this.toList = toList;
//        this.ccList = ccList;
//        this.sentDate = sentDate;
//        this.contentType = contentType;
//        this.messageHtmlBody = messageHtmlBody;
//    }
//
//    /**
//     *
//     * @param subject email subject
//     * @param sentTo on what address email was sent (in most cases email alias)
//     * @param timeoutInSeconds
//     * @param emailAddress email account address
//     * @param emailPassword password for the email account
//     * @return
//     */
//    public static EmailReceiver fetchEmailWith(Subject subject, String sentTo, int timeoutInSeconds, String emailAddress, String emailPassword) {
//
//        int timeout = timeoutInSeconds;
//        String protocol = RunConfig.get().getStaff().getEmailAddress().getProtocol();
//        String host = RunConfig.get().getStaff().getEmailAddress().getHost();
//        String port = RunConfig.get().getStaff().getEmailAddress().getPort();
//        Properties properties = getServerProperties(protocol, host, port);
//        Session session = GmailSessionFactory.createSession(properties);
//
//        Message[] unreadMessages = null;
//        Address[] fromAddress = null;
//        String from = "";
//        String subj = "";
//        String to = "";
//        String cc = "";
//        String sentDate = "";
//        String contentType = "";
//        String messageHtmlBody = "";
//
//        Message message = null;
//        Store store = null;
//        Folder folderInbox = null;
//
//        try {
//            // connects to the message store
//            store = session.getStore(protocol);
//            store.connect(emailAddress, emailPassword);
//
//            while (timeout > 0) {
//                // opens the inbox folder
//                folderInbox = store.getFolder("INBOX");
//                folderInbox.open(Folder.READ_WRITE);
//
//                if (unreadMessages != null && message != null) {
//                    break;
//                }
//                // fetches new messages from server
//                unreadMessages = folderInbox.search(new FlagTerm(new Flags(SEEN), false));
//                timeout--;
//                if (timeout == 0 && unreadMessages.length == 0) {
//                    throw new EmailNotFoundException(String.format("New messages were not found in [%s] seconds.", timeoutInSeconds));
//                }
//                message = getEmailMessage(unreadMessages, subject, sentTo);
//                waitFor(1);
//            }
//            if (message == null) {
//                folderInbox.close();
//                store.close();
//                GmailSessionFactory.closeSession();
//            }
//            if (message == null) {
//                throw new EmailNotFoundException(String.format("Email message with subject: %s, was not found in %s seconds.", subject.getSubjectText(), timeoutInSeconds));
//            }
//            fromAddress = message.getFrom();
//            from = fromAddress[0].toString();
//            subj = message.getSubject();
//            to = parseAddresses(message.getRecipients(Message.RecipientType.TO));
//            cc = parseAddresses(message.getRecipients(Message.RecipientType.CC));
//            sentDate = message.getSentDate().toString();
//            contentType = message.getContentType();
//            messageHtmlBody = extractBody(message);
//            folderInbox.close();
//            store.close();
//            GmailSessionFactory.closeSession();
//
//        } catch (NoSuchProviderException ex) {
//            System.out.println("No provider for protocol: " + protocol);
//            ex.printStackTrace();
//            GmailSessionFactory.closeSession();
//        } catch (MessagingException ex) {
//            System.out.println("Could not connect to the message store");
//            GmailSessionFactory.closeSession();
//            ex.printStackTrace();
//        }
//        return new EmailReceiver(fromAddress, from, subj, to, cc, sentDate, contentType, messageHtmlBody);
//    }
//
//    public String getEmailButton(ActionButton button) {
//        Optional<String> actionButton = extractHyperLinks(this.messageHtmlBody).stream().filter(e -> e.contains(button.getPartialPathText())).findFirst();
//        return actionButton.orElseThrow(() -> new EmailNotFoundException("Action button was not found in email body."));
//    }
//
//    public Address[] getFromAddress() {
//        return fromAddress;
//    }
//
//    public String getFrom() {
//        return from;
//    }
//
//    public String getSubject() {
//        return subject;
//    }
//
//    public String getToList() {
//        return toList;
//    }
//
//    public String getCcList() {
//        return ccList;
//    }
//
//    public String getSentDate() {
//        return sentDate;
//    }
//
//    public String getContentType() {
//        return contentType;
//    }
//
//    public String getMessageHtmlBody() {
//        return messageHtmlBody;
//    }
//
//    /*
//    Helper private static methods for retrieving data from an Email
//     */
//
//    private static String extractBody(Message message) {
//        String messageContent = "";
//        try {
//            String contentType = message.getContentType();
//            if (contentType.contains("text/plain") || contentType.contains("TEXT/HTML")) {
//                Object content = message.getContent();
//                if (content != null) {
//                    messageContent = content.toString();
//                }
//            }
//        } catch (Exception ex) {
//            messageContent = "[Error downloading content]";
//            ex.printStackTrace();
//        }
//        return messageContent;
//    }
//
//    private static Message getEmailMessage(Message[] inboxMessages, Subject subject, String to) {
//        Message message = null;
//        if (inboxMessages.length != 0) {
//            try {
//                for (Message m : inboxMessages) {
//                    if (m.getSubject().equals(subject.getSubjectText())
//                            && parseAddresses(m.getRecipients(Message.RecipientType.TO)).contains(to)
//                            && DateTimeUtils.timestampsEqualWithTolerance(m.getSentDate(), 3)) {
//                        // set flag seen
//                        m.setFlag(Flags.Flag.SEEN, true);
//                        message = m;
//                        break;
//                    }
//                }
//            } catch (MessagingException ex) {
//                System.out.println("Could not connect to the message store");
//                ex.printStackTrace();
//            }
//        }
//        return message;
//    }
//
//    private static Message getEmailMessage(Message[] inboxMessages, Subject subject) {
//        Message message = null;
//        if (inboxMessages.length != 0) {
//            try {
//                for (Message m : inboxMessages) {
//                    if (m.getSubject().equals(subject.getSubjectText())
//                            && DateTimeUtils.timestampsEqualWithTolerance(m.getSentDate(), 1)) {
//                        // set flag seen
//                        m.setFlag(Flags.Flag.SEEN, true);
//                        message = m;
//                        break;
//                    }
//                }
//            } catch (MessagingException ex) {
//                System.out.println("Could not connect to the message store");
//                ex.printStackTrace();
//            }
//        }
//        return message;
//    }
//
//    /**
//     * Returns a list of addresses in String format separated by comma
//     *
//     * @param address an array of Address objects
//     * @return a string represents a list of addresses
//     */
//    private static String parseAddresses(Address[] address) {
//        String listAddress = "";
//
//        if (address != null) {
//            for (int i = 0; i < address.length; i++) {
//                listAddress += address[i].toString() + ", ";
//            }
//        }
//        if (listAddress.length() > 1) {
//            listAddress = listAddress.substring(0, listAddress.length() - 2);
//        }
//        return listAddress;
//    }
//
//    private static List<String> extractHyperLinks(String body) {
//        Set<String> links = new HashSet<>();
//        Document document = Jsoup.parse(body);
//        Elements elements = document.select("a[href]");
//
//        for (Element e : elements) {
//            if (e.attr("href").startsWith("https")) {
//                links.add(e.attr("href"));
//            }
//        }
//        return new ArrayList<>(links);
//    }
//
//    /**
//     * Returns a Properties object which is configured for a POP3/IMAP server
//     *
//     * @param protocol either "imap" or "pop3"
//     * @param host
//     * @param port
//     * @return a Properties object
//     */
//    private static Properties getServerProperties(String protocol, String host, String port) {
//        Properties properties = new Properties();
//        // server setting
//        properties.put(String.format("mail.%s.host", protocol), host);
//        properties.put(String.format("mail.%s.port", protocol), port);
//        // SSL setting
//        properties.setProperty(String.format("mail.%s.socketFactory.class", protocol), "javax.net.ssl.SSLSocketFactory");
//        properties.setProperty(String.format("mail.%s.socketFactory.fallback", protocol), "false");
//        properties.setProperty(String.format("mail.%s.socketFactory.port", protocol), String.valueOf(port));
//
//        // IMAPStore connection pool
//        properties.setProperty("mail.imap.connectionpoolsize", "15");
//        properties.setProperty("mail.imap.connectionpooltimeout", "2400000");
//        return properties;
//    }
//}