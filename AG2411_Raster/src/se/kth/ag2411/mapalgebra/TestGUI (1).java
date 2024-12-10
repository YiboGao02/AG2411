package se.kth.ag2411.mapalgebra;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.Font;
import java.awt.GridLayout;

public class TestGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel panel;
    private JComboBox<String> currentComboBox;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                TestGUI frame = new TestGUI();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public TestGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1200, 800);
        setTitle("Dream Map");
        getContentPane().setLayout(null);
        initializeComponents();


        // Image Label
        JLabel lblNewLabel = new JLabel();
        lblNewLabel.setBounds(6, 6, 1188, 175);
        getContentPane().add(lblNewLabel);
        ImageIcon icon = new ImageIcon("/Users/evelinafischer/eclipse-workspace/AG2411_Raster/src/se/Dream-Map-2024-12-05.png");
        Image img = icon.getImage();
        lblNewLabel.setIcon(new ImageIcon(img.getScaledInstance(lblNewLabel.getWidth(), lblNewLabel.getHeight(), Image.SCALE_SMOOTH)));
        
        JPanel panel_1 = new JPanel();
        panel_1.setForeground(new Color(255, 230, 247));
        panel_1.setBackground(new Color(244, 0, 161));
        panel_1.setBounds(6, 617, 291, 149);
        getContentPane().add(panel_1);
        
        JPanel panel_2 = new JPanel();
        panel_2.setBackground(new Color(244, 0, 161));
        panel_2.setBounds(291, 606, 903, 160);
        panel_2.setLayout(null); // Use null layout for absolute positioning
        getContentPane().add(panel_2);

        // Add an image to the panel
        JLabel imageLabel = new JLabel();
        
        // Define padding to allow the panel background to show
        int padding = 10;
        imageLabel.setBounds(padding, padding, panel_2.getWidth() - 2 * padding, panel_2.getHeight() - 2 * padding); // Set the size of the label with padding

        // Load the image
        ImageIcon panelImageIcon = new ImageIcon("/Users/evelinafischer/eclipse-workspace/AG2411_Raster/src/se/Barbie.jpg");
        Image scaledImage = panelImageIcon.getImage().getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));

        // Add the label to the panel
        panel_2.add(imageLabel);
        
        JPanel panel_3 = new JPanel();
        panel_3.setBackground(new Color(244, 0, 161));
        panel_3.setBounds(291, 165, 903, 452);
        getContentPane().add(panel_3);


    }

    private JPanel operationPanel; // Panel for operation-specific components

    private void initializeComponents() {
        panel = new JPanel();
        panel.setBackground(new Color(244, 0, 161));
        panel.setBounds(6, 181, 291, 436);
        getContentPane().add(panel);
        panel.setLayout(null);

        // Buttons
        JButton localButton = new JButton("Local");
        localButton.setFont(new Font("DecoType Naskh", Font.PLAIN, 13));
        localButton.setBounds(6, 10, 90, 26);
        panel.add(localButton);

        JButton focalButton = new JButton("Focal");
        focalButton.setBounds(98, 10, 90, 26);
        panel.add(focalButton);

        JButton zonalButton = new JButton("Zonal");
        zonalButton.setBounds(190, 10, 90, 26);
        panel.add(zonalButton);

        // Panel for dynamic operation components
        operationPanel = new JPanel();
        operationPanel.setBackground(new Color(255, 230, 247));
        operationPanel.setBounds(6, 50, 279, 373);
        operationPanel.setLayout(null);
        panel.add(operationPanel);

        // Event listeners for buttons
        localButton.addActionListener(e -> updateComboBox("local"));
        focalButton.addActionListener(e -> updateComboBox("focal"));
        zonalButton.addActionListener(e -> updateComboBox("zonal"));
        
        // Add the GAME button
        JButton gameButton = new JButton("GAME");
        gameButton.setForeground(new Color(0, 0, 0)); // Black text
        gameButton.setBounds(10, 605, 286, 155); // Big button size
        gameButton.setBackground(new Color(255, 230, 247)); // Light pink background
        gameButton.setFont(gameButton.getFont().deriveFont(36f)); // Bigger font (24 points)
        getContentPane().add(gameButton);
        
        // Add an icon to the button
        ImageIcon gameIcon = new ImageIcon("/Users/evelinafischer/eclipse-workspace/AG2411_Raster/src/se/game_icon.png"); // Provide the correct path to your image
        Image gameImage = gameIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Scale the icon to fit the button
        gameButton.setIcon(new ImageIcon(gameImage));

        // Align the icon and text properly
        gameButton.setHorizontalTextPosition(SwingConstants.CENTER);
        gameButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        

        // Add the button to the content pane
        getContentPane().add(gameButton);

        // Add ActionListener to open the new window
        gameButton.addActionListener(e -> openGameWindow());
        
    }

    private void updateComboBox(String type) {
        // Clear the operationPanel
        operationPanel.removeAll();

        // Add a new combo box with relevant options
        currentComboBox = new JComboBox<>();
        switch (type) {
            case "local":
                currentComboBox.addItem("Select local operation");
                currentComboBox.addItem("Local Sum");
                currentComboBox.addItem("Local Difference");
                currentComboBox.addItem("Local Product");
                currentComboBox.addItem("Local Mean");
                currentComboBox.addItem("Local Variety");
                addLocalComponents();
                break;
            case "focal":
                currentComboBox.addItem("Select focal operation");
                currentComboBox.addItem("Focal Sum");
                currentComboBox.addItem("Focal Mean");
                currentComboBox.addItem("Focal Variety");
                currentComboBox.addItem("Focal Maximum");
                currentComboBox.addItem("Focal Minimum");
                currentComboBox.addItem("Focal Slope");
                addFocalComponents();
                break;
            case "zonal":
                currentComboBox.addItem("Select zonal operation");
                currentComboBox.addItem("Zonal Sum");
                currentComboBox.addItem("Zonal Mean");
                currentComboBox.addItem("Zonal Variety");
                currentComboBox.addItem("Zonal Maximum");
                currentComboBox.addItem("Zonal Minimum");
                currentComboBox.addItem("Zonal Majority");
                addZonalComponents();
                break;
        }

        currentComboBox.setBounds(6, 10, 180, 30);
        operationPanel.add(currentComboBox);

        // Refresh the operationPanel
        operationPanel.revalidate();
        operationPanel.repaint();
    }

    private void addLocalComponents() {
    	// Layer 1 components
        JLabel layerLabel1 = new JLabel("Layer 1:");
        layerLabel1.setBounds(6, 50, 60, 30);
        operationPanel.add(layerLabel1);

        JTextField layerInputField1 = new JTextField();
        layerInputField1.setBounds(70, 50, 120, 30);
        operationPanel.add(layerInputField1);

        JButton browseButton1 = new JButton("Browse");
        browseButton1.setBounds(200, 50, 80, 30);
        operationPanel.add(browseButton1);

        browseButton1.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                layerInputField1.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });
        
        // Layer 2 components
        JLabel layerLabel2 = new JLabel("Layer 2:");
        layerLabel2.setBounds(6, 100, 60, 30); // Positioned below Layer 1
        operationPanel.add(layerLabel2);

        JTextField layerInputField2 = new JTextField();
        layerInputField2.setBounds(70, 100, 120, 30); // Positioned below Layer 1 input field
        operationPanel.add(layerInputField2);

        JButton browseButton2 = new JButton("Browse");
        browseButton2.setBounds(200, 100, 80, 30); // Positioned below Layer 1 browse button
        operationPanel.add(browseButton2);

        browseButton2.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                layerInputField2.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        // Add Run button
        JButton runButton = new JButton("Run");
        runButton.setBounds(200, 150, 80, 30); // Positioned below Layer 2 components
        operationPanel.add(runButton);

        runButton.addActionListener(e -> {
            System.out.println("Local operation executed.");
        });
        
        // Add Clear button
        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(130, 150, 80, 30); // Positioned next to the Run button
        operationPanel.add(clearButton);

        clearButton.addActionListener(e -> {
            layerInputField1.setText(""); // Clear Layer 1 input field
            layerInputField2.setText(""); // Clear Layer 2 input field
        });
    }

    private void addFocalComponents() {
    	JLabel layerLabel = new JLabel("Layer:");
        layerLabel.setBounds(6, 50, 60, 30);
        operationPanel.add(layerLabel);

        JTextField layerInputField = new JTextField();
        layerInputField.setBounds(70, 50, 120, 30);
        operationPanel.add(layerInputField);

        JButton browseButton = new JButton("Browse");
        browseButton.setBounds(200, 50, 80, 30);
        operationPanel.add(browseButton);

        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                layerInputField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });
        
        // Add radius label
        JLabel radiusLabel = new JLabel("Radius:");
        radiusLabel.setBounds(6, 100, 60, 30);
        operationPanel.add(radiusLabel); // Use operationPanel here

        // Add radius input field
        JTextField radiusInputField = new JTextField();
        radiusInputField.setBounds(70, 100, 120, 30);
        operationPanel.add(radiusInputField); // Use operationPanel here
        
        // Add Radio Buttons
        JRadioButton circleButton = new JRadioButton("Circle");
        circleButton.setBounds(6, 140, 80, 30);
        operationPanel.add(circleButton);

        JRadioButton rectangleButton = new JRadioButton("Rectangle");
        rectangleButton.setBounds(100, 140, 100, 30);
        operationPanel.add(rectangleButton);

        // Group the radio buttons to allow only one selection
        ButtonGroup shapeGroup = new ButtonGroup();
        shapeGroup.add(circleButton);
        shapeGroup.add(rectangleButton);
        
        // Add Run button
        JButton runButton = new JButton("Run");
        runButton.setBounds(200, 180, 80, 30);
        operationPanel.add(runButton);

        runButton.addActionListener(e -> {
            System.out.println("Zonal operation executed.");
        });
        
        // Add Clear button
        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(130, 180, 80, 30); // Positioned next to the Run button
        operationPanel.add(clearButton);

        clearButton.addActionListener(e -> {
            layerInputField.setText(""); // Clear the layer input field
            radiusInputField.setText(""); // Clear the radius input field
        });


    }

    private void addZonalComponents() {
        JLabel layerLabel1 = new JLabel("Layer:");
        layerLabel1.setBounds(6, 50, 60, 30);
        operationPanel.add(layerLabel1);

        JTextField layerInputField1 = new JTextField();
        layerInputField1.setBounds(70, 50, 120, 30);
        operationPanel.add(layerInputField1);
        
        JButton browseLayerButton = new JButton("Browse");
        browseLayerButton.setBounds(200, 50, 80, 30);
        operationPanel.add(browseLayerButton);

        // Action Listener for "Browse" button to select a file for the Layer
        browseLayerButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                layerInputField1.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        JLabel layerLabel2 = new JLabel("Zone:");
        layerLabel2.setBounds(6, 100, 60, 30);
        operationPanel.add(layerLabel2);

        JTextField layerInputField2 = new JTextField();
        layerInputField2.setBounds(70, 100, 120, 30);
        operationPanel.add(layerInputField2);
        
        JButton browseZonalLayerButton = new JButton("Browse");
        browseZonalLayerButton.setBounds(200, 100, 80, 30);
        operationPanel.add(browseZonalLayerButton);

        // Action Listener for "Browse" button to select a file for the Zonal Layer
        browseZonalLayerButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                layerInputField2.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        JButton runButton = new JButton("Run");
        runButton.setBounds(200, 150, 80, 30);
        operationPanel.add(runButton);

        runButton.addActionListener(e -> {
            System.out.println("Zonal operation executed.");
        });
        
        // Add Clear button
        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(130, 150, 80, 30); // Positioned next to the Run button
        operationPanel.add(clearButton);

        clearButton.addActionListener(e -> {
            layerInputField1.setText(""); // Clear Layer 1 input field
            layerInputField2.setText(""); // Clear Layer 2 input field
        });
    }
    
    private void openGameWindow() {
        JFrame gameWindow = new JFrame("Game Window");
        gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gameWindow.setBounds(200, 200, 600, 400);
        gameWindow.getContentPane().setLayout(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel("Are you ready to play?", SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(20f));
        gameWindow.getContentPane().add(titleLabel, BorderLayout.NORTH);

        // Create panel_2
        JPanel panel_2 = new JPanel();
        panel_2.setLayout(null);  // Use null layout for absolute positioning (adjust as needed)
        gameWindow.getContentPane().add(panel_2, BorderLayout.CENTER);

        // Add a listener to ensure the image is loaded after the window is shown
        SwingUtilities.invokeLater(() -> {
            // Image Label for the panel image
            JLabel imageLabel = new JLabel();
            int padding = 10;  // Padding to give some space between the panel edge and the image

            // Set bounds based on panel size after it's been realized
            int panelWidth = panel_2.getWidth();
            int panelHeight = panel_2.getHeight();
            imageLabel.setBounds(padding, padding, panelWidth - 2 * padding, panelHeight - 2 * padding);

            // Load and scale the image for the panel
            ImageIcon panelImageIcon = new ImageIcon("/Users/evelinafischer/eclipse-workspace/AG2411_Raster/src/se/game_window.jpg");
            Image panelScaledImage = panelImageIcon.getImage().getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(panelScaledImage));

            // Add the image label to panel_2
            panel_2.add(imageLabel);

            // Revalidate and repaint the panel to ensure everything is displayed correctly
            panel_2.revalidate();
            panel_2.repaint();
        });

        // Bottom panel for file selection buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(2, 2, 10, 10));

        // File labels
        JLabel valueFileLabel = new JLabel("");
        JLabel zoneFileLabel = new JLabel("");

        // Buttons to select files
        JButton selectValueFileButton = new JButton("Select Value File");
        selectValueFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(gameWindow);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    valueFileLabel.setText("Value File: " + selectedFile.getAbsolutePath());
                }
            }
        });

        JButton selectZoneFileButton = new JButton("Select Zone File");
        selectZoneFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(gameWindow);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    zoneFileLabel.setText("Zone File: " + selectedFile.getAbsolutePath());
                }
            }
        });

        // Add components to the bottom panel
        bottomPanel.add(selectValueFileButton);
        bottomPanel.add(selectZoneFileButton);
        bottomPanel.add(valueFileLabel);
        bottomPanel.add(zoneFileLabel);

        // Add the bottom panel to the game window
        gameWindow.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        // Display the window
        gameWindow.setVisible(true);
    }
}