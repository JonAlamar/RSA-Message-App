import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

/* This MainMenu class is the starting GUI from which the user is able to
Register, Login, Message, or Exit the application. This MainMenu class
has action listeners on each button that will guide the user to the
next GUI. */

public class MainMenu extends JFrame {
    JButton registerButton, loginButton, messageButton;
    private JButton exitButton;
    private JPanel panel;
    private User loggedInUser;

    private List<User> users;

    public MainMenu() {

        // Makes Main Menu panel visible
        super("Main Menu");
        this.setContentPane(panel);
        this.pack();
        this.setVisible(true);

        // Attaching method references to each button, so when clicked, performs method
        registerButton.addActionListener(this::registerGui);
        loginButton.addActionListener(this::loginGui);
        messageButton.addActionListener(this::messageGui);
        exitButton.addActionListener(this::exit);

        setLocationRelativeTo(null);
    }

    /* Method executed when Register Button is clicked, will open new RegisterGui instance
      and pass a reference to method that will receive the list of registered users
     */
    private void registerGui(ActionEvent e) {
        RegisterGui registerGui = new RegisterGui(this::receiveUserRegistration);
    }

    /* Method executed when Login Button is clicked, will open new LoginGui instance
    and pass a reference to method that will receive the logged-in user
     */
    private void loginGui(ActionEvent e) {
        LoginGui loginGui = new LoginGui(users, this::receiveUserLogin);
    }

    /* Method executed when Message Button is clicked, will open new MessageGui instance
    and passes the list of registered users and the current logged-in user
     */
    private void messageGui(ActionEvent e) {
        MessageGui messageGui = new MessageGui(users, loggedInUser);
    }

    // Method that will close the application when exit button is clicked
    private void exit(ActionEvent e) {
        this.dispose();
    }

    // Using Observer pattern to retrieve registered users, this method is passed when Registering users
    private void receiveUserRegistration(List<User> createdUsers) { // going to have all 3 users that are created
        // set that to a field variable 'users'
        this.users = createdUsers;
        JOptionPane.showMessageDialog(panel, "Thank you for registering " + users.size() + " users");
    }

    // Using Observer pattern to retrieve the logged-in user, this method is passed when Logging in
    private void receiveUserLogin(User loggedInUser) {
        this.loggedInUser = loggedInUser;
        JOptionPane.showMessageDialog(panel, "Thank you for logging in " + loggedInUser.getUserName());
    }

}
