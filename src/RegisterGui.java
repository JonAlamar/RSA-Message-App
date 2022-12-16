import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* This RegisterGui class will register new users and add them to a list of User objects. When the user
types in a username and password and presses enter or the register button, the user will be added to
the list. When the window is closed, the completed user list will be passed back to the MainMenu.
 */
public class RegisterGui extends JFrame{

    private final Consumer<List<User>> consumer; // void someFunction(List<User>);
    JPanel panel;
    JLabel userLabel, passwordLabel, successLabel;
    JTextField userText, passwordText;
    JButton registerButton;


    // An arraylist is created to store the registered users
    protected List<User> users = new ArrayList<>();

    public RegisterGui(Consumer<List<User>> consumer) {
        super("Register");
        this.consumer = consumer;

        // Creating gui and setting dimensions
        panel = new JPanel();
        this.setSize(800, 400);
        this.add(panel);
        panel.setLayout(null);

        // Creating username label
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
        text field. This will instantiate a new User object and passes the username and password
        so that the User constructor will set the username and password.
         */
        passwordText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                users.add(new User(userText.getText(), passwordText.getText()));
                successLabel.setText("Success!");
                userText.setText("");
                passwordText.setText("");
            }
        });
        panel.add(passwordText);

        // Create register button
        registerButton = new JButton("Register");
        registerButton.setBounds(10, 80, 100, 25);

        // Action listener will be triggered when the Register Button is clicked and onLogin method will run
        registerButton.addActionListener(this::onLogin);
        panel.add(registerButton);

        // Create success label
        successLabel = new JLabel("");
        successLabel.setBounds(10, 110, 300, 25);
        panel.add(successLabel);


        /* When the register window is closed, the users will be added to the consumer
        list and given back to MainMenu.
         */
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                consumer.accept(users);
                }
        });

        setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /* Action Listener method will trigger when the user clicks the register button. This will
        then loop through each registered user and compare the username and hashed password.
        In order for the user to login successfully, they must have both a matching username
        and a hashed salt. If the user types in an incorrect username or password 3 times, they
        will be exited from the Login Gui to prevent an attack. If there is a matched user, it
        will be added the consumer list to be passed back to the Main Menu.
         */
    private void onLogin(ActionEvent e) {
        users.add(new User(userText.getText(), passwordText.getText()));
        successLabel.setText("Success!");
        userText.setText("");
        passwordText.setText("");
    }

}

