import javax.swing.*;
import java.awt.event.ActionEvent;
import java.math.BigInteger;
import java.util.List;

/* The MessageGui class is responsible for being the driver for sending and
receiving messages encrypted with RSA. The MessageGui constructor receives a
list of registered users, and the current logged-in user. From there the logged-in
user can send messages to other users using the drop-down menu. The logged-in user can
also check their inbox and decrypt messages they have received.
 */
public class MessageGui extends JFrame{
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JButton encryptButton;
    private JButton sendButton;
    private JComboBox comboBox1;
    private JLabel sendToButton;
    private JPanel panel;
    private JButton decryptButton;

    protected List<User> users;
    protected User chosenUser;
    protected User loggedInUser;

    public MessageGui(List<User> users, User loggedInUser) {
        super("Message");
        this.users = users;
        this.loggedInUser = loggedInUser;
        this.setContentPane(panel);
        this.pack();
        setLocationRelativeTo(null);
        textArea1.setLineWrap(true);
        textArea1.setWrapStyleWord(true);
        textArea2.setLineWrap(true);
        textArea2.setWrapStyleWord(true);
        decryptButton.addActionListener(this::decrypt);
        this.setVisible(true);

        // This loop iterates through the list of registered users and adds to dropdown menu.
        for (int i = 0; i < users.size(); i++) {
            comboBox1.addItem(users.get(i).getUserName());
        }

        // Sets default selection to index 0, which is blank.
        comboBox1.setSelectedItem(0);

        // Adds an action listener to the dropdown selection menu.
        comboBox1.addActionListener(this::pickRecipient);

        /* If the current logged-in user has anything in their
        inbox, it will be displayed in the first text area
         */
        if (loggedInUser.getMessageInbox() != null) {
            textArea1.setText(loggedInUser.getMessageInbox());
        }
    }

    /* This action listener method will be triggered when there is a selection
    when there is a selection from the dropdown menu. Depending on which user
    if chosen from the dropdown menu, this will affect the encrypt and send
    button action listeners to use the appropriate public key and recipient
     */
    private void pickRecipient(ActionEvent e) {
        int userIndex = comboBox1.getSelectedIndex();
        chosenUser = users.get(userIndex-1);
        System.out.println(chosenUser.getUserName() + " has been selected");
        encryptButton.addActionListener(this::encrypt);
        sendButton.addActionListener(this::send);
            }

    /* When encrypt button is clicked, the lower text area will be RSA encrypted using
    the chosen user's public key
     */
    private void encrypt(ActionEvent e) {
        textArea2.setText(RSA.encrypt(textArea2.getText(), chosenUser.getN(), chosenUser.getE()).toString());
    }

    /* When decrypt button is clicked, the upper text area will be RSA decrypted using
    the current logged-in user's private key
     */
    private void decrypt(ActionEvent e) {
        BigInteger cipher = new BigInteger(textArea1.getText());
        textArea1.setText(RSA.decrypt(cipher, loggedInUser.getN(), loggedInUser.getD()));
    }

    /* When the send button is pressed, the encrypted text will be sent to the chosen
    users inbox, which they will be able to view upon logging into their own account.
     */
    private void send(ActionEvent e) {
        chosenUser.setMessageInbox(textArea2.getText());
        textArea2.setText("");
    }
        }