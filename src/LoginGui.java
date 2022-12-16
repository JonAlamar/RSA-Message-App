import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Consumer;

/* This LoginGui class is the Gui that will be generated when the user clicks the Login button
in the Main Menu. The Login Gui is responsible for taking in user input for username and password,
and then checking that against the registered users. The Login Gui will compare username, and then hash the
given password with the salt of each registered user in order to find a match. Once a user is successfully
logged in, the logged-in user will be passed back to the Main Menu
*/

public class LoginGui extends JFrame {

    private final Consumer<User> consumer;
    JPanel panel;
    JLabel userLabel, passwordLabel, successLabel;
    JTextField userText, passwordText;
    JButton loginButton;
    int lockout = 0;
    boolean userNotFound = true;

    protected List<User> users;

    public LoginGui(List<User> users, Consumer<User> consumer) {

        super("Login");
        this.consumer = consumer;
        this.users = users;

        // Creating gui and setting dimensions
        panel = new JPanel();
        this.setSize(800, 400);
        this.add(panel);
        panel.setLayout(null);

        // Creating user label
        userLabel = new JLabel("User");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        // Creating username text field
        userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        // Creating password label
        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        // Creating password text field
        passwordText = new JPasswordField();
        passwordText.setBounds(100, 50, 165, 25);

        /* Action Listener method will trigger when the user presses "enter" in the password
        text field. This will then loop through each registered user and compare the username
        and hashed password. In order for the user to login successfully, they must have both
        a matching username and a hashed salt. If the user types in an incorrect username or
        password 3 times, they will be exited from the Login Gui to prevent an attack. If there
        is a matched user, it will be added the consumer list to be passed back to the Main Menu.
         */
        passwordText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Checking each user for matching username and hashed password
                for (int i = 0; i < users.size(); i++) {
                    if ((users.get(i).getUserName().equals(userText.getText())) && users.get(i).getSaltedHash().equals((User.generateSaltedPassword(passwordText.getText(), users.get(i).getSalt())))) {
                        consumer.accept(users.get(i));
                        userNotFound = false;
                    }
                }

                // If typed incorrectly, gives a warning and to try again
                if (userNotFound) {
                    JOptionPane.showMessageDialog(panel, "Username or password is incorrect");
                    lockout++;
                }

                // If typed incorrectly 3 times, user will be exited from the Login Gui
                if (lockout == 3) {
                    JOptionPane.showMessageDialog(panel, "For security reasons, you have been locked out for 8 seconds");
                    panel.setVisible(false);
                    try {
                        Thread.sleep(8000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    panel.setVisible(true);
                    lockout = 0;
                }
                userText.setText("");
                passwordText.setText("");
            }
        });
        panel.add(passwordText);

        // Create login button and add an action listener when the button is clicked
        loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);

        // Action listener will be triggered when the Login Button is clicked and verifyLogin method will run
        loginButton.addActionListener(this::verifyLogin);
        panel.add(loginButton);

        // Create success label, this will appear if login is successful
        successLabel = new JLabel("");
        successLabel.setBounds(10, 110, 300, 25);
        panel.add(successLabel);
        setLocationRelativeTo(null);
        this.setVisible(true);
    }

        /* Action Listener method will trigger when the user clicks the login button. This will
        then loop through each registered user and compare the username and hashed password.
        In order for the user to login successfully, they must have both a matching username
        and a hashed salt. If the user types in an incorrect username or password 3 times, they
        will be exited from the Login Gui to prevent an attack. If there is a matched user, it
        will be added the consumer list to be passed back to the Main Menu.
         */
    private void verifyLogin(ActionEvent e) {

        for (int i = 0; i < users.size(); i++) {
            if ((users.get(i).getUserName().equals(userText.getText())) && users.get(i).getSaltedHash().equals((User.generateSaltedPassword(passwordText.getText(), users.get(i).getSalt())))) {
                consumer.accept(users.get(i));
            }
        }

        // If typed incorrectly, gives a warning and to try again
        if (userNotFound) {
            JOptionPane.showMessageDialog(panel, "Username or password is incorrect, please try again");
            lockout++;
        }

        // If typed incorrectly 3 times, user will be exited from the Login Gui
        if (lockout == 3) {
            JOptionPane.showMessageDialog(panel, "For security reasons, you have been locked out for 8 seconds");
            panel.setVisible(false);
            try {
                Thread.sleep(8000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            panel.setVisible(true);
            lockout = 0;
        }
        // Empty the text from the username and password fields
        userText.setText("");
        passwordText.setText("");
        }
    }


